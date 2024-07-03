package examples.multiset.tests;

import java.util.Locale;
import java.util.Optional;
import java.util.function.Predicate;

import org.jgrapht.GraphPath;

import examples.multiset.MultisetEdge;
import examples.multiset.MultisetHeuristic;
import examples.multiset.MultisetVertex;
import examples.multiset.data.MultisetData;
import examples.multiset.data.MultisetSolution;
import graphs.alg.BnB;
import graphs.alg.GreedyOnGraph;
import graphs.virtual.EGraph;
import graphs.virtual.EGraph.Type;
import path.EGraphPath.PathType;

public class TestBnB_multiset {

	public static void main(String[] args) {

		// Set up
		Locale.setDefault(Locale.of("en", "US"));

		for (Integer file_id = 0; file_id < 7; file_id++) {

			MultisetData.iniData("files/multiset.txt", file_id);
			System.out.println("=============");
			System.out.println("\tTest results " + file_id + "\n");
			
			MultisetData.toConsole();

			MultisetVertex start = MultisetVertex.initial();
			Predicate<MultisetVertex> goal = MultisetVertex.goal();

			// Graph

			System.out.println("\n#### BT Algorithm ####");
			
			// BnB Algorithm
			
			EGraph<MultisetVertex, MultisetEdge> graph =
					EGraph.virtual(start,goal,PathType.Sum, Type.Min)
					.edgeWeight(x -> x.weight())
					.greedyEdge(MultisetVertex::greedyEdge)
					.goalHasSolution(MultisetVertex.goalHasSolution())
					.heuristic(MultisetHeuristic::heuristic)
					.build();
			
			
			GreedyOnGraph<MultisetVertex, MultisetEdge> rr = GreedyOnGraph.of(graph);
			
			GraphPath<MultisetVertex, MultisetEdge> r = rr.path();
			
			System.out.println("Greedy = "+r.getWeight()+"  == "+MultisetVertex.getSolution(r));
			
			BnB<MultisetVertex, MultisetEdge, MultisetSolution> bta = BnB.of(graph,
					MultisetVertex::getSolution, null, null, true);

			if (rr.isSolution(r)) {
				bta = BnB.of(graph, MultisetVertex::getSolution, r.getWeight(), r, true);
			}
			Optional<GraphPath<MultisetVertex, MultisetEdge>> gp = bta.search();
			System.out.println(MultisetVertex.getSolution(gp.get()));
			
//			System.out.println(bta.path.getEdgeList().stream().map(x -> x.action())
//					.collect(Collectors.toList()));
			
			
//			GraphColors.toDot(bta.graph(), "files/multisetBTGraph.gv", 
//					v -> v.toGraph(),
//					e -> e.action().toString(), 
//					v -> GraphColors.colorIf(Color.red, MulticonjuntoVertex.goal().test(v)),
//					e -> GraphColors.colorIf(Color.red, bta.optimalPath.getEdgeList().contains(e)));

		}
	}

}

