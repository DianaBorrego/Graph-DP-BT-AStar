package examples.setcover.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
//import java.util.TreeSet;
import java.util.stream.Collectors;

import utils.Files2;
import utils.String2;

public class SetCoverData {

	public static Integer NUM_SC, NUM_E;
	
	private static Set<Integer> universe;
	private static List<SetData> setData;

	public static void iniDatos(String fichero) {		
		SetCoverData.setData = new ArrayList<>();
		SetCoverData.universe = new HashSet<>();
		for(String linea: Files2.linesFromFile(fichero)) {
			SetData s = SetData.parse(linea);
			SetCoverData.setData.add(s);
			SetCoverData.universe.addAll(s.set());
		}
		SetCoverData.NUM_SC = setData.size();
		SetCoverData.NUM_E = universe.size();
		Collections.sort(SetCoverData.setData,Comparator.comparing(d->d.weight()));
	}
	
	public static List<SetData> setData() {
		return setData;
	}
	
	public static  Set<Integer> universe() {
		return universe;
	}
	
	public static Set<Integer> getSet(int index){
		return setData.get(index).set();
	}
	
	public static Double getWeight(int index){
		return setData.get(index).weight();
	}
	
	public static String getName(int index){
		return index == SetCoverData.NUM_SC? "_":setData.get(index).name();
	}
	
	public static void toConsole() {
		String2.toConsole(String.format("Universe U = %s\nSets S =  \n\t%s",
				SetCoverData.universe(),
				SetCoverData.setData().stream().map(d->d.toString()).collect(Collectors.joining("\n\t"))));		
	}
	
	// File reading test
	public static void main(String[] args) {
		// Set up
		Locale.setDefault(Locale.of("en", "US"));

		for (Integer file_id = 1; file_id < 3; file_id++) {

			SetCoverData.iniDatos("files/setCover/setCover" + file_id + ".txt");
			System.out.println("\n\n>\tTestResults " + file_id + "\n");
			SetCoverData.toConsole();
			
		}
	}
}

