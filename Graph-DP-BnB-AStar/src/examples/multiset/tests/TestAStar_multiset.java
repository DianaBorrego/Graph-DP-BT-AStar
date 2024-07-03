package examples.multiset.tests;

import java.util.List;
import java.util.Locale;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.jgrapht.GraphPath;

import examples.multiset.MultisetEdge;
import examples.multiset.MultisetHeuristic;
import examples.multiset.MultisetVertex;
import examples.multiset.data.MultisetData;
import examples.multiset.data.MultisetSolution;
import graphs.alg.AStar;
import graphs.virtual.EGraph;
import graphs.virtual.EGraph.Type;
import path.EGraphPath.PathType;

public class TestAStar_multiset {

	public static void main(String[] args) {

		// Set up
		Locale.setDefault(Locale.of("en", "US"));

		for (Integer file_id = 0; file_id < 7; file_id++) {

			MultisetData.iniData("files/multiset.txt", file_id);
			System.out.println("\n\n>\tTest results " + file_id + "\n");

			MultisetVertex start = MultisetVertex.initial();
			Predicate<MultisetVertex> goal = MultisetVertex.goal();

			// Graph

			System.out.println("#### A* Algorithm ####");

			// A* Algorithm
			EGraph<MultisetVertex, MultisetEdge> graph =
					EGraph.virtual(start,goal,PathType.Sum, Type.Min)
					.edgeWeight(x -> x.weight())
					.goalHasSolution(MultisetVertex.goalHasSolution())
					.heuristic(MultisetHeuristic::heuristic)
					.build();
					
			AStar<MultisetVertex, MultisetEdge,?> aStar = AStar.ofGreedy(graph);
			
			GraphPath<MultisetVertex, MultisetEdge> gp = aStar.search().get();
			
			List<Integer> gp_as = gp.getEdgeList().stream().map(x -> x.action())
					.collect(Collectors.toList()); // getEdgeList();
			
			
			MultisetSolution s_as = MultisetSolution.create(gp_as);
			
			
			System.out.println(s_as);
			System.out.println(gp_as);

//			GraphColors.toDot(aStar.outGraph, "files/multisetAStarGraph.gv", 
//					v -> v.toGraph(),
//					e -> e.action().toString(), 
//					v -> GraphColors.colorIf(Color.red, MulticonjuntoVertex.goal().test(v)),
//					e -> GraphColors.colorIf(Color.red, gp.getEdgeList().contains(e)));
		}
	}

}
