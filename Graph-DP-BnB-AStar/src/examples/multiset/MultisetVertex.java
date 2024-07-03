package examples.multiset;

import java.util.List;
import java.util.function.Predicate;

import org.jgrapht.GraphPath;

import examples.multiset.data.MultisetData;
import examples.multiset.data.MultisetSolution;
import utils.List2;
import graphs.virtual.VirtualVertex;

public record MultisetVertex(Integer index, Integer remaining_amount) implements VirtualVertex<MultisetVertex, MultisetEdge, Integer> {


	public static Integer numElements = MultisetData.NUM_E;

	public static MultisetVertex of(Integer i, Integer sr) {
		return  new MultisetVertex(i, sr);
	}
	
	public static MultisetVertex initial() {
		return  new MultisetVertex(0, MultisetData.SUM);
	}
	
	public static Predicate<MultisetVertex> goal() {
		return  v->v.index == MultisetData.NUM_E;
	}
	
	public static Predicate<MultisetVertex> goalHasSolution() {
		return  v->v.remaining_amount == 0;
	}

	
	public String toGraph() {
		return String.format("(%d,%d)", this.index, this.remaining_amount);
	}

	// Auxiliary methods

	public String toString() {
		return String.format("(%d,%d)", this.index, this.remaining_amount);
	}

	// Graph methods

	@Override
	public Boolean isValid() {
		return this.index >= 0 && this.index <= MultisetData.NUM_E && remaining_amount >= 0;
	}

	@Override
	public List<Integer> actions() {
		List<Integer> alternatives = List2.empty();
		if (this.index < MultisetData.NUM_E) {
			if (this.index == MultisetData.NUM_E - 1) {
				if (this.remaining_amount % MultisetData.getElement(this.index) == 0) {
					Integer max_div = this.integerAction();
					alternatives.add(max_div);
				}
			} else {
				Integer max_div = this.integerAction();
				alternatives = List2.rangeList(0, max_div + 1);
			}
		}	
		return alternatives = List2.reverse(alternatives);
	}

	@Override
	public MultisetVertex neighbor(Integer a) {
		Integer new_remaining_amount = remaining_amount - (MultisetData.getElement(this.index) * a);
		return MultisetVertex.of(this.index + 1, new_remaining_amount);
	}

	@Override
	public MultisetEdge edge(Integer a) {
		return MultisetEdge.of(this, this.neighbor(a), a);
	}
	
	public MultisetEdge greedyEdge() {
		return edge(integerAction());
	}

	public Integer integerAction() {
		return this.remaining_amount / MultisetData.getElement(this.index);
	}
	
	public Double doubleAction() {
		return ((double)this.remaining_amount)/ MultisetData.getElement(this.index);
	}

	public static MultisetSolution getSolution(GraphPath<MultisetVertex, MultisetEdge> path) {
		return MultisetVertex.getSolution(path.getEdgeList());
	}

	public static MultisetSolution getSolution(List<MultisetEdge> ls) {

		List<Integer> alternatives = List2.empty();

		for (MultisetEdge alternativa : ls) {
			alternatives.add(alternativa.action());
		}

		MultisetSolution s = MultisetSolution.create(alternatives);

		return s;
	}

}

