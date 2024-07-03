package examples.setcover.tests;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

import org.jgrapht.GraphPath;

import colors.GraphColors;
import colors.GraphColors.Color;
import examples.setcover.SetCoverEdge;
import examples.setcover.SetCoverHeuristic;
import examples.setcover.SetCoverVertex;
import examples.setcover.data.SetCoverData;
import examples.setcover.data.SetCoverSolution;
import graphs.alg.DPR;
import graphs.virtual.EGraph;

public class TestDP_setCover {

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

			System.out.println("\n\n#### DP Algorithm ####");

			// Algoritmo PD
			DPR<SetCoverVertex, SetCoverEdge,?> pdr = 
					DPR.of(graph, null,
							SetCoverHeuristic.greedy(start,SetCoverData.NUM_SC),null,true);
			
			SetCoverSolution sv = SetCoverHeuristic.greedySolution(start,SetCoverData.NUM_SC);
			List<SetCoverEdge> le = SetCoverHeuristic.greedyPath(start,SetCoverData.NUM_SC);
			System.out.println("Sv = "+sv);
			Optional<GraphPath<SetCoverVertex, SetCoverEdge>> gp = pdr.search();
			System.out.println(gp);
			SetCoverSolution s_pdr;
			if (gp.isPresent()) {
				List<Integer> gp_pdr = gp.get().getEdgeList().stream().map(x -> x.action())
						.collect(Collectors.toList());
				s_pdr = SetCoverSolution.of(gp_pdr);
			} else { 				
				s_pdr = sv;
			}

			System.out.println(s_pdr);
			
			GraphColors.toDot(pdr.outGraph,"files/setCover/setCoverDPGraph.gv",
					v->v.toGraph(),
					e->e.action().toString(),
					v->GraphColors.colorIf(Color.red,SetCoverVertex.goal().test(v)),
					e->GraphColors.colorIf(Color.red,(gp.isPresent()?gp.get().getEdgeList():le).contains(e))
					);
		}
	}

}

