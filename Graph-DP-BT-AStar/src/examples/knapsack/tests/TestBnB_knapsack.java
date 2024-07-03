package examples.knapsack.tests;

import java.util.Locale;
import java.util.Optional;

import org.jgrapht.GraphPath;

import colors.GraphColors;
import colors.GraphColors.Color;
import examples.knapsack.KnapsackEdge;
import examples.knapsack.KnapsackHeuristic;
import examples.knapsack.KnapsackVertex;
import examples.knapsack.data.KnapsackData;
import graphs.alg.BnB;
import graphs.alg.GreedyOnGraph;
import graphs.virtual.EGraph;
import graphs.virtual.EGraph.Type;
import examples.knapsack.data.KnapsackSolution;
import path.EGraphPath.PathType;



public class TestBnB_knapsack {


	public static void main(String[] args) {
		Locale.setDefault(Locale.of("en", "US"));
		KnapsackData.iniData("files/knapsack/KnapsackObjects.txt");
		KnapsackVertex.initialCapacity = 78;
		KnapsackVertex e1 = KnapsackVertex.initialVertex();
		
		EGraph<KnapsackVertex, KnapsackEdge> graph = 
				EGraph.virtual(e1,KnapsackVertex.goal(), PathType.Sum, Type.Max)
				.greedyEdge(KnapsackVertex::greedyEdge)
				.heuristic(KnapsackHeuristic::heuristic)
				.build();	
		
		GreedyOnGraph<KnapsackVertex, KnapsackEdge> rr = GreedyOnGraph.of(graph);
		
		GraphPath<KnapsackVertex, KnapsackEdge> path = rr.path();
		
//		KnapsackSolution s0 = KnapsackVertex.getSolucion(path);
//		
//		System.out.println(s0);
		System.out.println(path.getEdgeList().stream().map(e->e.action()).toList());
		
		BnB<KnapsackVertex,KnapsackEdge,KnapsackSolution> ms = BnB.of(
				graph,
				KnapsackVertex::getSolution,
				path.getWeight(),path,true);		
		
		Optional<GraphPath<KnapsackVertex, KnapsackEdge>> gp = ms.search();
//		KnapsackSolution s = KnapsackVertex.getSolucion(ms.optimalPath().get());
		System.out.println(KnapsackVertex.getSolution(gp.get()));
		System.out.println(ms.optimalPath().get().getEdgeList().stream().map(e->e.action()).toList());
			
		GraphPath<KnapsackVertex, KnapsackEdge> sp = gp.get();
		GraphColors.toDot(ms.outGraph(),"files/knapsack/KnapsackBTGraph2.gv",
				v->String.format("(%d,%d)",v.index(),v.remainingCapacity()),
				e->e.action().toString(),
				v->GraphColors.colorIf(Color.red,KnapsackVertex.goal().test(v)),
				e->GraphColors.colorIf(Color.red,sp.getEdgeList().contains(e))
				);
	}

}
