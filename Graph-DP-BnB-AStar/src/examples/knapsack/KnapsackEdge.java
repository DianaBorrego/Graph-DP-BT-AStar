package examples.knapsack;

import graphs.virtual.SimpleEdgeAction;
import examples.knapsack.data.KnapsackData;

public record KnapsackEdge(KnapsackVertex source, KnapsackVertex target, Integer action, Double weight) 
                   implements SimpleEdgeAction<KnapsackVertex,Integer> {
	
	public static KnapsackEdge of(KnapsackVertex v1, KnapsackVertex v2, Integer a) {	
		Double w = a*KnapsackData.getValue(v1.index()).doubleValue();
		return new KnapsackEdge(v1,v2, a, w);
	}
	
}

