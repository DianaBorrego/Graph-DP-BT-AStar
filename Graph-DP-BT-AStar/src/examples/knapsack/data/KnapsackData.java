package examples.knapsack.data;


import java.util.Comparator;
import java.util.List;



import java.util.stream.Collectors;

import utils.Files2;


/**
 * <p> This class implements the type for solving the knapsack problem. The corresponding objects are generalized knapsack problems. </p>
 * <p> The properties of these problems are: </p>
 * <ul>
 * <li> Capacity
 * <li> Index 
 * <li> Available items (shared property)
 * </ul> 
 * 
 * 
 * 
 * @author Miguel Toro
 *
 */
public class KnapsackData {
	
	private static List<KnapsackObject> availableItems;
	private static Comparator<KnapsackObject> itemsOrder;
	public static Integer initialCapacity;
	public static Integer numberOfItems;
	public static Integer n;

	/**
	 * The method reads the input file and updates the ObjectsAvailable list, 
	 * which is sorted according to the natural order of the objects. 
	 * 
	 * @param file File containing the properties of the available items. One item per line
	 */
	public static void iniData(String file) {
		itemsOrder = Comparator.reverseOrder();
		availableItems = Files2.streamFromFile(file)
				.<KnapsackObject> map((String s) -> KnapsackObject.create(s))
				.sorted(itemsOrder)
				.collect(Collectors.<KnapsackObject> toList());
		numberOfItems = availableItems.size();
		n = numberOfItems;
	}
	
	public static List<KnapsackObject> getItems() {
		return availableItems;
	}
	
	public static Comparator<KnapsackObject> getItemsOrder() {
		return itemsOrder;
	}	

	public static KnapsackObject getItem(int index){
		return KnapsackData.getItems().get(index);
	}
	
	public static Integer getValue(int index){
		return KnapsackData.getItems().get(index).getValue();
	}
	
	public static Integer getWeight(int index){
		return KnapsackData.getItems().get(index).getWeight();
	}
	
	public static Integer getMaxNumberOfUnits(int index){
		return KnapsackData.getItems().get(index).getMaxNumberOfUnits();
	}
	
	public static Integer getNumberOfPossibleUnits(int index, Integer capacity){
		return Math.min(KnapsackData.getMaxNumberOfUnits(index),capacity/KnapsackData.getWeight(index));
	}
	
	public static Double getPossibleQuantity(int index, Double capacity){
		return Math.min(KnapsackData.getMaxNumberOfUnits(index),capacity/KnapsackData.getWeight(index));
	}
	
	public static Boolean constraints(Integer c) {
		return c >=0;
	}

	public static Integer getUpperBound(Integer index, Integer cr) {
		Double r = 0.;
		int ind = index;
		int n = getItems().size();
		Double remainingCapacity = (double)cr;
		Double nu =0.;	
		while(ind < n && remainingCapacity > 0) {	
			nu = getPossibleQuantity(ind,remainingCapacity);
			r = r+nu*getValue(ind);
			remainingCapacity = remainingCapacity-nu*getWeight(ind);			
			ind++;		
		} 
		return (int)Math.ceil(r);
	}
}
