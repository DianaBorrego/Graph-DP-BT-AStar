package examples.setcover.data;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jgrapht.GraphPath;

import examples.setcover.SetCoverEdge;
import examples.setcover.SetCoverVertex;

public record SetCoverSolution(Double weight, Set<String> sets, Boolean covering) 
           implements Comparable<SetCoverSolution>{
	
	public static SetCoverSolution of(GraphPath<SetCoverVertex, SetCoverEdge> path) {
		List<Integer> la = path.getEdgeList().stream().map(e->e.action()).toList();
		return SetCoverSolution.of(la);
	}

	public static SetCoverSolution of(List<Integer> ls) {
		Set<String> cs = new HashSet<>();
		Double ps = 0.;
		Set<Integer> s = new HashSet<>();
		for (int i = 0; i < ls.size(); i++) {
			if (ls.get(i) == 1) {
				cs.add(SetCoverData.getName(i));
				ps += SetCoverData.getWeight(i);
				s.addAll(SetCoverData.getSet(i));
			}
		}
		Boolean c = s.equals(SetCoverData.universe());
		return new SetCoverSolution(ps,cs,c);
	}

	@Override
	public int compareTo(SetCoverSolution other) {
		return this.weight().compareTo(other.weight());
	}
	
	
	
}

