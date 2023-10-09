package examples.knapsack.data;

/**
 * <p> This class implements the type KnapsackObject.</p>
 * <p> The properties of these problems are: </p>
 * <ul>
 * <li> Code
 * <li> Value
 * <li> Weight
 * <li> Maximum number of units
 * </ul> 
 * 
 * 
 * 
 * @author Miguel Toro
 *
 */
public class KnapsackObject implements Comparable<KnapsackObject>{
	
	public static KnapsackObject create(Integer value, Integer weight, Integer countMax) {
		return new KnapsackObject(value, weight, countMax);
	}

	/**
	 * @param s A line in a text file
	 * @return Constructs a knapsack object from a line in a file.
	 */
	public static KnapsackObject create(String s) {
		return new KnapsackObject(s);
	}
	
	private static Integer nCode = 0;
	
	private Integer code;
	private Integer value;
	private Integer weight;
	private Integer maxNumberOfUnits;
	
	KnapsackObject(Integer value, Integer weight, Integer countMax){
		this.code = nCode;
		nCode++;
		this.value = value;
		this.weight = weight;
		this.maxNumberOfUnits = countMax;
	}
	
	KnapsackObject(String s){		
		String[] v = s.split("[ ,]");
		Integer ne = v.length;
		if(ne != 3) throw new IllegalArgumentException("Inappropriate formatting in line "+s);	
		value = Integer.parseInt(v[0].trim());
		weight = Integer.parseInt(v[1].trim());
		maxNumberOfUnits = Integer.parseInt(v[2]);
		this.code = nCode;
		nCode++;
	}	
	
	public Integer getWeight() {
		return weight;
	}
	
	public Integer getValue() {
		return value;
	}
	
	public Integer getCode() {
		return code;
	}
	
	public Integer getMaxNumberOfUnits() {
		return maxNumberOfUnits;
	}
		
	public Double getValueToWeightRatio() {
		return ((double)value)/weight;
	}
	
	@Override
	public String toString() {
		return String.format("(%d,%d,%d,%.2f)",
				value,weight,maxNumberOfUnits,getValueToWeightRatio());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof KnapsackObject))
			return false;
		KnapsackObject other = (KnapsackObject) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		return true;
	}

	@Override
	public int compareTo(KnapsackObject o) {
		return this.getValueToWeightRatio().compareTo(o.getValueToWeightRatio());
	}
	
	
	
}
