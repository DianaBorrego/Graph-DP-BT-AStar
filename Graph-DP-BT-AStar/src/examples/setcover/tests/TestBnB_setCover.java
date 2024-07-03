package examples.setcover.tests;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

import org.jgrapht.GraphPath;

import colors.GraphColors;
import colors.GraphColors.Color;
import examples.setcover.SetCoverEdge;
import examples.setcover.SetCoverHeuristic;
import examples.setcover.SetCoverVertex;
import examples.setcover.data.SetCoverData;
import examples.setcover.data.SetCoverSolution;
import graphs.alg.BT;
import graphs.virtual.EGraph;

public class TestBT_setCover {

	public static void main(String[] args) {

		// Set up
		Locale.setDefault(Locale.of("en", "US"));

		for (Integer file_id = 1; file_id < 3; file_id++) {

			SetCoverData.iniDatos("files/setCover/setCover" + file_id + ".txt");
			System.out.println("\n\n>\tTest results " + file_id + "\n");

			SetCoverVertex start = SetCoverVertex.initial();

			// Graph

			EGraph<SetCoverVertex, SetCoverEdge> graph = 
					EGraph.virtual(start,SetCoverVertex.goal())
					.edgeWeight(x-> x.weight())
					.heuristic(SetCoverHeuristic::heuristic)
					.build();

			System.out.println("\n\n#### BT Algorithm ####");
			
			BT<SetCoverVertex, SetCoverEdge, SetCoverSolution> bta = 
				BT.of(graph, 
					SetCoverSolution::of, 
					SetCoverHeuristic.greedy(start,SetCoverData.NUM_SC),null,true);

			SetCoverSolution sv = SetCoverHeuristic.greedySolution(start,SetCoverData.NUM_SC);
			List<SetCoverEdge> le = SetCoverHeuristic.greedyPath(start,SetCoverData.NUM_SC);
			System.out.println("Sv = "+sv);
			Optional<GraphPath<SetCoverVertex, SetCoverEdge>> gp = bta.search();
			
			System.out.println(gp.isPresent()?SetCoverSolution.of(gp.get()):sv);
			List<SetCoverEdge> ls = bta.optimalPath != null?bta.optimalPath.getEdgeList():null;
			
			GraphColors.toDot(bta.outGraph(),"files/setCover/setCoverBTGraph.gv",
					v->v.toGraph(),
					e->e.action().toString(),
					v->GraphColors.colorIf(Color.red,SetCoverVertex.goal().test(v)),
					e->GraphColors.colorIf(Color.red,(bta.optimalPath != null?ls:le).contains(e))
					);
			
//			System.out.println(ls.stream().map(e->e.action()).toList());
		}
	}

}

