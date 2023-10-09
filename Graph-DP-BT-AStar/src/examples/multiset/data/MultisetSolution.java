package examples.multiset.data;


import java.util.List;

import utils.Multiset;

public record MultisetSolution(Integer difference, Multiset<Integer> ms, List<Integer> solution) implements Comparable<MultisetSolution>{
	
	public static MultisetSolution create(List<Integer> ls) {
		Integer difference  = MultisetData.SUM;
		Multiset<Integer> ms = Multiset.of();
		for(int i=0; i<ls.size(); i++) {
			ms.add(MultisetData.getNumbers().get(i), ls.get(i));
			difference -= MultisetData.getNumbers().get(i)*ls.get(i);
		}
		return new MultisetSolution(difference, ms, ls);
	}
	

	public Integer size() {
		return ms.size();
	}
	
	@Override
	public int compareTo(MultisetSolution other) {
		return this.size().compareTo(other.size());
	}

	@Override
	public String toString() {
		Integer n = 0;
		String msg = "";
		for (int i = 0; i < solution.size(); i++) {
			if (solution.get(i) > 1) {
				msg += MultisetData.getNumbers().get(i)+"*"+solution.get(i)+" + ";
			} else if (solution.get(i) == 1) {
				msg += MultisetData.getNumbers().get(i)+" + ";
			}
			n+=solution.get(i);
		}
		String aux = difference==0? "exact solution)":"approximate solution with difference="+difference+")";
		if(msg.isEmpty()) {
			msg = "No number was taken ("+aux+" -> Multiset = "+ms;
		} else {
			msg = msg.substring(0, msg.length() - 2)+" ("+n+" elems; "+aux+" -> Multiset = "+ms;
		}
		return msg;
	}

}

