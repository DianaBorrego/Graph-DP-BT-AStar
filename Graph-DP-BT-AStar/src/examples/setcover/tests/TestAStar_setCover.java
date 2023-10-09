package examples.setcover.tests;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import examples.setcover.SetCoverEdge;
import examples.setcover.SetCoverHeuristic;
import examples.setcover.SetCoverVertex;
import examples.setcover.data.SetCoverData;
import examples.setcover.data.SetCoverSolution;
import graphs.alg.AStar;
import graphs.virtual.EGraph;

public class TestAStar_setCover {
	
	public static void main(String[] args) {

	// Set up
	Locale.setDefault(Locale.of("en", "US"));

	for (Integer id_fichero = 1; id_fichero < 3; id_fichero++) {

		SetCoverData.iniDatos("files/setCover/setCover" + id_fichero + ".txt");
		System.out.println("\n\n>\tTest results " + id_fichero + "\n");

		SetCoverVertex start = SetCoverVertex.initial();

		// Graph
		
		EGraph<SetCoverVertex, SetCoverEdge> graph = 
				EGraph.virtual(start,SetCoverVertex.goal())
				.edgeWeight(x-> x.weight())
				.heuristic(SetCoverHeuristic::heuristic)
				.build();

		System.out.println("\n\n#### A* Algorithm ####");
		
		AStar<SetCoverVertex, SetCoverEdge,?> aStar = AStar.ofGreedy(graph);
		
		List<Integer> gp_as = aStar.search().get().getEdgeList().stream().map(x -> x.action())
				.collect(Collectors.toList()); // getEdgeList();
		SetCoverSolution s_as = SetCoverSolution.of(gp_as);
		System.out.println(s_as);
	}

	}
}
