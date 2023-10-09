package examples.setcover;

import examples.setcover.data.SetCoverData;
import graphs.virtual.SimpleEdgeAction;

public record SetCoverEdge(SetCoverVertex source, SetCoverVertex target, Integer action, Double weight) 
			implements SimpleEdgeAction<SetCoverVertex,Integer> {

	public static SetCoverEdge of(SetCoverVertex c1, SetCoverVertex c2, Integer action) {
		Double w = (double) SetCoverData.getWeight(c1.index())*action;
		return new SetCoverEdge(c1, c2, action, w);
	}
}
