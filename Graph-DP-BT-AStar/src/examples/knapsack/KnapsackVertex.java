package examples.knapsack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.*;

import org.jgrapht.GraphPath;

import utils.Preconditions;
import graphs.virtual.VirtualVertex;
import examples.knapsack.data.KnapsackSolution;
import examples.knapsack.data.KnapsackData;

public record KnapsackVertex(Integer index, Integer remainingCapacity)
          implements VirtualVertex<KnapsackVertex, KnapsackEdge, Integer> {

	public static KnapsackVertex initialVertex() {
		return of(0, initialCapacity);
	}
	
	public static KnapsackVertex copy(KnapsackVertex m) {
		return of(m.index, m.remainingCapacity);
	}
	
	public static KnapsackVertex of(int index, Integer remainingCapacity) {
		return new KnapsackVertex(index, remainingCapacity);
	}
	
	public static KnapsackVertex lastVertex() {
		return new KnapsackVertex(n, 0);
	}
	
	public static Predicate<KnapsackVertex> goal() {
		return v->v.index == KnapsackVertex.n;
	}

	public static Integer n = KnapsackData.numberOfItems;
	public static Integer initialCapacity;

	
	public static KnapsackSolution getSolution(GraphPath<KnapsackVertex, KnapsackEdge> path){
		return KnapsackVertex.getSolution(path.getEdgeList());
	}

	public static KnapsackSolution getSolution(List<KnapsackEdge> ls){
		KnapsackSolution s = KnapsackSolution.empty();
		ls.stream().forEach(e->s.add(KnapsackData.getItem(e.source().index),e.action().intValue()));
		return s;
	}

	@Override
	public Boolean isValid() {
		return index>=0 && index<=KnapsackData.getItems().size();
	}
	
	public KnapsackEdge greedyEdge() {
		Preconditions.checkElementIndex(index, KnapsackData.numberOfItems);
		Integer a = Math.min(this.remainingCapacity/KnapsackData.getWeight(index),KnapsackData.getMaxNumberOfUnits(index));
		return KnapsackEdge.of(this,this.neighbor(a), a);
	}
	
	public Integer greedyAction() {
		Preconditions.checkElementIndex(index, KnapsackData.numberOfItems);
		return Math.min(this.remainingCapacity/KnapsackData.getWeight(index),KnapsackData.getMaxNumberOfUnits(index));
	}
	
	public Double heuristicAction() {
		Preconditions.checkElementIndex(index, KnapsackData.numberOfItems);
		return Math.min(this.remainingCapacity.doubleValue()/KnapsackData.getWeight(index),KnapsackData.getMaxNumberOfUnits(index));
	}

	@Override
	public List<Integer> actions() {
		if(this.index == n) return new ArrayList<>();
		Integer nu = greedyAction().intValue();
		if(this.index == n-1) return new ArrayList<>(nu);
		List<Integer> alternatives = IntStream.rangeClosed(0,nu)
				.boxed()
				.collect(Collectors.toList());
		Collections.reverse(alternatives);
		return alternatives;
	}
	
	@Override
	public KnapsackVertex neighbor(Integer a) {
		KnapsackVertex r;
		Integer cr = remainingCapacity - a * KnapsackData.getWeight(index);
		if (this.index == KnapsackVertex.n - 1 || this.remainingCapacity == 0.) 
			r = KnapsackVertex.of(KnapsackVertex.n, cr);
		else r = KnapsackVertex.of(index + 1, cr);
		return r;
	}

	@Override
	public KnapsackEdge edge(Integer a) {
		KnapsackVertex v = this.neighbor(a);
		return KnapsackEdge.of(this,v,a);
	}
	
}

