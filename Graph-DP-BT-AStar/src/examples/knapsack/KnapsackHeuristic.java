package examples.knapsack;

import java.util.function.Predicate;

import examples.knapsack.data.KnapsackData;

public class KnapsackHeuristic {
	
	public static Double heuristic(KnapsackVertex v1, Predicate<KnapsackVertex> goal, KnapsackVertex v2) {
		Double r = 0.;
//		while(!p.test(MochilaVertex.of(index, 0))) {
		int index = v1.index();
		Double remainingCapacity = v1.remainingCapacity()*1.0;
		while (index < KnapsackData.numberOfItems) {
			Double a = Math.min(remainingCapacity/KnapsackData.getWeight(index),KnapsackData.getMaxNumberOfUnits(index));
			r = r + a*KnapsackData.getValue(index);
			remainingCapacity = remainingCapacity-a*KnapsackData.getWeight(index);
			index = index+1;			
		}
		return  r;
	}

}
