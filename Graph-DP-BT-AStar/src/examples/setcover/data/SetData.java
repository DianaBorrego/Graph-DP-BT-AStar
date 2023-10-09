package examples.setcover.data;


import java.util.Set;
import utils.Set2;

public record SetData(String name ,Double weight, Set<Integer> set) {
	
	public static SetData parse(String s) {
		String[] tokens = s.split("[=:]");
		return new SetData(
				tokens[0],
				Double.parseDouble(tokens[2].trim()), 
				Set2.parse(tokens[1].trim(), " ,{}",Integer::parseInt));
	}
	
	public Double unitWeight() {
		return this.weight()/this.set().size();
	}
}

