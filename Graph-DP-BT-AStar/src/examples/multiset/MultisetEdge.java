package examples.multiset;

import graphs.virtual.SimpleEdgeAction;

public record MultisetEdge(MultisetVertex source, MultisetVertex target,Integer action, Double weight) 
           implements SimpleEdgeAction<MultisetVertex, Integer> {

	public static MultisetEdge of(MultisetVertex c1, MultisetVertex c2, Integer action) {
		MultisetEdge a = new MultisetEdge(c1, c2, action, action * 1.0);
		return a;
	}

}
