package examples.setcover;


import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import examples.setcover.data.SetCoverData;
import utils.IntegerSet;
import utils.Set2;
import graphs.virtual.VirtualVertex;

public record SetCoverVertex(Integer index, IntegerSet coveredElements) 
    implements VirtualVertex<SetCoverVertex, SetCoverEdge, Integer>{
	
	public static SetCoverVertex of(Integer index, IntegerSet coveredElements) {
		return new SetCoverVertex(index,coveredElements);
	}
	
	public static SetCoverVertex initial() {
		return SetCoverVertex.of(0,IntegerSet.empty());
	}

	public static Predicate<SetCoverVertex> goal() {
		return v-> v.index == SetCoverData.NUM_SC;
	}
	
	public static SetCoverVertex copy(SetCoverVertex sv) {
		return SetCoverVertex.of(sv.index(),IntegerSet.copy(sv.coveredElements()));
	}
	
	public String toGraph() {
		return String.format("%s,%s)",SetCoverData.getName(this.index()),this.isUniverseCovered()?"Y":"N");
	}
	
    public Boolean isUniverseCovered() {
    	return this.coveredElements().equals(SetCoverData.universe());
    }
    
    public Boolean isUniverseCovered_after() {
    	Set<Integer>  s = Set2.union(this.coveredElements(),SetCoverData.getSet(this.index()));
    	return s.equals(SetCoverData.universe());
    }
    
    public Integer greedyAction() {
    	return this.actions().stream().max(Comparator.naturalOrder()).get();
    }
    
    public SetCoverEdge greedyEdge() {
    	return this.edge(this.greedyAction());
    }

	@Override
	public Boolean isValid() {
		return true;
	}
	
	@Override
	public List<Integer> actions() {
		List<Integer> r;
		if(this.index() == SetCoverData.NUM_SC) r = List.of();
		else if(this.index() == SetCoverData.NUM_SC-1) {
			if(this.isUniverseCovered()) r = List.of(0);
			else if(this.isUniverseCovered_after()) r = List.of(1);
			else r = List.of();
		} else {
			Set<Integer> s = Set2.difference(SetCoverData.getSet(this.index()),this.coveredElements());
			if(s.isEmpty()) r = List.of(0);
			else r = List.of(1,0);
		}
		return r;
	}
	
	@Override
	public SetCoverVertex neighbor(Integer a) {
		if(a==0) return SetCoverVertex.of(this.isUniverseCovered()?SetCoverData.NUM_SC:this.index()+1,this.coveredElements());
		else return SetCoverVertex.of(this.index()+1,this.coveredElements().addF(this.index()));
	}
	@Override
	public SetCoverEdge edge(Integer a) {
		return SetCoverEdge.of(this,this.neighbor(a),a);
	}
	
}
