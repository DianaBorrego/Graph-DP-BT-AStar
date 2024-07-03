package examples.multiset.tests;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.jgrapht.GraphPath;

import colors.GraphColors;
import colors.GraphColors.Color;
import examples.multiset.MultisetEdge;
import examples.multiset.MultisetHeuristic;
import examples.multiset.MultisetVertex;
import examples.multiset.data.MultisetData;
import examples.multiset.data.MultisetSolution;
import graphs.alg.PDR;
import graphs.alg.GreedyOnGraph;
import graphs.virtual.EGraph;
import graphs.virtual.EGraph.Type;
import path.EGraphPath.PathType;

public class TestPD_multiset {

	public static void main(String[] args) {

		// Set up
		Locale.setDefault(Locale.of("en", "US"));
		for (Integer file_id = 0; file_id < 7; file_id++) {

			MultisetData.iniData("files/multiset.txt", file_id);
			System.out.println("\n\n>\tTest results " + file_id + "\n");

			MultisetVertex start = MultisetVertex.initial();
			Predicate<MultisetVertex> goal = MultisetVertex.goal();

			// Graph
			System.out.println("\n\n#### DP Algorithm ####");

			// DP Algorithm
			
			EGraph<MultisetVertex, MultisetEdge> graph =
					EGraph.virtual(start,goal,PathType.Sum, Type.Min)
					.edgeWeight(x -> x.weight())
					.greedyEdge(MultisetVertex::greedyEdge)
					.goalHasSolution(MultisetVertex.goalHasSolution())
					.heuristic(MultisetHeuristic::heuristic)
					.build();
			
			
			GreedyOnGraph<MultisetVertex, MultisetEdge> rr = 
					GreedyOnGraph.of(graph);
			
			GraphPath<MultisetVertex, MultisetEdge> r = rr.path();
			
			System.out.println("Greedy = "+r.getWeight()+"  == "+MultisetVertex.getSolution(r));
			
			PDR<MultisetVertex, MultisetEdge, ?> pdr = PDR
					.of(graph, null,null, null, true);

			if (rr.isSolution(r)) {
				pdr = PDR.of(graph, null,r.getWeight(), r, true);
			}
			
			
			Optional<GraphPath<MultisetVertex, MultisetEdge>> gp = pdr.search();
			
			MultisetSolution s_pdr = null;
			
			if (gp.isPresent()) {
				System.out.println(gp.get().getEdgeList().stream().map(x -> x.action()).collect(Collectors.toList()));
				List<Integer> gp_pdr = gp.get().getEdgeList().stream().map(x -> x.action()).collect(Collectors.toList()); // getEdgeList();

				s_pdr = MultisetSolution.create(gp_pdr);

			} 
			
			
			System.out.println(s_pdr);
			
			
			GraphColors.toDot(pdr.outGraph, "files/multisetDPRGraph.gv", 
					v -> v.toGraph(),
					e -> e.action().toString(), 
					v -> GraphColors.colorIf(Color.red, MultisetVertex.goal().test(v)),
					e -> GraphColors.colorIf(Color.red, gp.isPresent()?gp.get().getEdgeList().contains(e):false));

		}
	}
}

