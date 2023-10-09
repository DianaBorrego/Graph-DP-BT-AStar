package examples.knapsack.tests;


import java.util.Locale;

import org.jgrapht.GraphPath;
import org.jgrapht.graph.SimpleDirectedGraph;

import colors.GraphColors;
import colors.GraphColors.Color;
import examples.knapsack.KnapsackEdge;
import examples.knapsack.KnapsackHeuristic;
import examples.knapsack.KnapsackVertex;
import graphs.alg.AStar;
import graphs.virtual.EGraph;
import graphs.virtual.EGraph.Type;
import examples.knapsack.data.KnapsackData;
import examples.knapsack.data.KnapsackSolution;
import path.EGraphPath.PathType;

public class TestAStar_knapsack {

	
	public static void main(String[] args) {
		Locale.setDefault(Locale.of("en", "US"));
		KnapsackData.iniData("files/knapsack/KnapsackObjects.txt");
		KnapsackVertex.initialCapacity = 78;
		System.out.println(Double.MAX_VALUE);
		KnapsackVertex e1 = KnapsackVertex.initialVertex();
		
		EGraph<KnapsackVertex, KnapsackEdge> graph = 
				EGraph.virtual(e1,KnapsackVertex.goal(), PathType.Sum, Type.Max)
				.greedyEdge(KnapsackVertex::greedyEdge)
				.heuristic(KnapsackHeuristic::heuristic)
				.build();
		
		
//		GreedyOnGraph<KnapsackVertex, KnapsackEdge> rr = GreedyOnGraph.of(graph);
		
//		GraphPath<KnapsackVertex, KnapsackEdge> gp = rr.path();
		
//		System.out.println(gp.getWeight());
	
		AStar<KnapsackVertex, KnapsackEdge, KnapsackSolution> ms = AStar.ofGreedy(graph);
		
		GraphPath<KnapsackVertex, KnapsackEdge> path = ms.search().get();
		KnapsackSolution s = KnapsackVertex.getSolution(path);
		System.out.println(s);
		SimpleDirectedGraph<KnapsackVertex, KnapsackEdge> r = ms.outGraph();
		System.out.println(ms.tree.keySet().size());
		
		GraphColors.toDot(r,"files/knapsack/KnapsackAstarGraph.gv",
				v->String.format("((%d,%d)",v.index(),v.remainingCapacity()),
				e->e.action().toString(),
				v->GraphColors.colorIf(Color.red,path.getVertexList().contains(v)),
				e->GraphColors.colorIf(Color.red,path.getEdgeList().contains(e))
				);
	}

}
