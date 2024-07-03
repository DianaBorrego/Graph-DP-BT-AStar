package examples.knapsack.tests;

import static colors.GraphColors.all;
import static colors.GraphColors.colorIf;
import static colors.GraphColors.styleIf;

import java.util.Locale;
import java.util.Optional;
import java.util.function.Predicate;

import org.jgrapht.GraphPath;

import colors.GraphColors;
import colors.GraphColors.Color;
import colors.GraphColors.Style;
import graphs.alg.DPR;
import graphs.alg.GreedyOnGraph;
import graphs.virtual.EGraph;
import graphs.virtual.EGraph.Type;
import examples.knapsack.KnapsackEdge;
import examples.knapsack.KnapsackHeuristic;
import examples.knapsack.KnapsackVertex;
import examples.knapsack.data.KnapsackData;
import examples.knapsack.data.KnapsackSolution;
import path.EGraphPath.PathType;


public class TestDPR_knapsack {
	
	
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
		Double bv = path.getWeight();
		
		System.out.println("1 = "+bv);
		
		DPR<KnapsackVertex, KnapsackEdge, KnapsackSolution> ms = 
				DPR.of(graph,null,bv,path,true);
		
		
		Optional<GraphPath<KnapsackVertex, KnapsackEdge>>  sp = ms.search();
		GraphPath<KnapsackVertex, KnapsackEdge> s1 = sp.get();
		KnapsackSolution s = KnapsackVertex.getSolution(s1);
		System.out.println(s);
		
		Predicate<KnapsackVertex> pv = v->ms.optimalPath().get().getVertexList().contains(v);
		Predicate<KnapsackEdge> pe= e->ms.optimalPath().get().getEdgeList().contains(e);
		
		GraphColors.toDot(ms.outGraph,"files/knapsack/KnapsackDPRGraph.gv",
				v->String.format("(%d,%d)",v.index(),v.remainingCapacity()),
				e->e.action().toString(),
				v->all(colorIf(Color.red,pv.test(v)),styleIf(Style.bold,pv.test(v))),
				e->all(colorIf(Color.red,pe.test(e)),styleIf(Style.bold,pe.test(e)))
				);
	}

}
