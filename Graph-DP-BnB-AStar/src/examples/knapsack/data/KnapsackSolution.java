package examples.knapsack.data;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p> This class implements the type KnapsackSolution. </p>
 * <p> The properties of these objects are: </p>
 * <ul>
 * <li> Objects in the knapsack (basic property of type Multiset<KnapsackObject>;)
 * <li> Value (derived property)
 * <li> Weight (derived property)
 * </ul> 
 * 
 * 
 * 
 * @author Miguel Toro
 *
 */
public class KnapsackSolution implements Comparable<KnapsackSolution>{
	
	public static KnapsackSolution empty() {
		return new KnapsackSolution();
	}

	private Map<KnapsackObject,Integer> m;	

	private KnapsackSolution() {
		super();
		this.m = new HashMap<KnapsackObject,Integer>();
	}
	
	private KnapsackSolution(Map<KnapsackObject,Integer> m) {
		super();
		this.m = new HashMap<>(m);
	}
		
	public void add(KnapsackObject ob, int nu) {
		Integer n = nu;
		if(m.containsKey(ob)) n = nu+m.get(ob);			
		this.m.put(ob, n);	
	}
	
	public void remove(KnapsackObject ob, int nu) {
		this.m.put(ob, Math.max(m.get(ob)-nu,0));	
	}
	
	public KnapsackSolution copy() {
		return new KnapsackSolution(m);
	}
	
	public Integer count(KnapsackObject ob){
		return m.get(ob);
	}
	
	public Set<KnapsackObject> elements(){
		return m.keySet();
	}
	
	public Integer getValue() {	
		return m.keySet()
				.stream()
				.mapToInt(x->m.get(x)*x.getValue())
				.sum();
	}
	
	public Integer getWeight() {
		return m.keySet()
				.stream()
				.mapToInt(x->m.get(x)*x.getWeight())
				.sum();
	}
	
	public String getObjetos() {
		return KnapsackData.getItems()
				.stream()
			    .map(x->String.format("%s=%d",x,m.get(x)==null?0:m.get(x)))
			    .collect(Collectors.joining(", ","{","}"));
	}
	
	public String toString() {
		return String.format("valor = %d, peso = %d,\n%s", 
				getValue(),getWeight(),getObjetos()); 
	}

	@Override
	public int compareTo(KnapsackSolution sm) {
		return this.getValue().compareTo(sm.getValue());
	}	
}
