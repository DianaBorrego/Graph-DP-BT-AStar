package examples.multiset.data;


import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import utils.Files2;
import utils.String2;

public class MultisetData {

	public static int NUM_E, SUM;

	private static List<Integer> numbers;

	public static void iniData(String path, Integer line) {
		iniData(Files2.linesFromFile(path).get(line));
	}

	public static void iniData(String line) {
		String[] tokens = line.split(":");
		SUM = Integer.parseInt(tokens[1].trim());
		Set<Integer> nums = new HashSet<>();
		for (String x : tokens[0].split(",")) {
			nums.add(Integer.parseInt(x.trim()));
		}
		numbers = nums.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList());
		NUM_E = numbers.size();
	}

	public static List<Integer> getNumbers() {
		return numbers;
	}

	public static Integer getElement(int index) {
		return numbers.get(index);
	}

	public static Integer getMultiplicity(Integer i) {
		Integer res = 0;
		if (getElement(i) > 0 && SUM >= getElement(i)) {
			res = SUM / getElement(i);
		}
		return res;
	}

	public static void toConsole() {
		String2.toConsole("Input set: " + numbers + "\nTarget sum: " + SUM);
	}

	// File reading test
//	public static void main(String[] args) {
//		for (String linea : Files2.linesFromFile("ficheros/multiconjuntos.txt")) {
//			iniData(linea);
//			toConsole();
//		}
//	}
}

