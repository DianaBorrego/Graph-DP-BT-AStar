package examples.setcover;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.IntStream;

import examples.setcover.data.SetCoverData;
import examples.setcover.data.SetCoverSolution;
import utils.String2;

public class SetCoverHeuristic {

	public static Double heuristic(SetCoverVertex v1, Predicate<SetCoverVertex> goal, SetCoverVertex v2) {
		return heuristic2(v1, SetCoverData.NUM_SC);
	}
	
	public static Double greedy(SetCoverVertex vertex, Integer lastIndex) {
		Double peso = 0.;
		while(!vertex.actions().isEmpty() && vertex.index() < lastIndex && !vertex.isUniverseCovered()) {
			Integer a = vertex.greedyAction();
			peso += a*SetCoverData.getWeight(vertex.index());
			vertex = vertex.neighbor(a);
		}
		return peso;
	}
	
	public static SetCoverSolution greedySolution(SetCoverVertex vertex, Integer lastIndex) {
		Double weight = 0.;
		Set<String> s = new HashSet<>();
		Set<Integer> ss = new HashSet<>();
		while(!vertex.actions().isEmpty() && vertex.index() < lastIndex && !vertex.isUniverseCovered()) {
			Integer a = vertex.greedyAction();
			weight += a*SetCoverData.getWeight(vertex.index());
			if(a==1) {
				s.add(SetCoverData.getName(vertex.index()));
				ss.addAll(SetCoverData.getSet(vertex.index()));
			}
			vertex = vertex.neighbor(a);
		}
//		String2.toConsole(String.format("%s,\nSolution = %s",vertex,s));
		Boolean c = ss.equals(SetCoverData.universe());
		return new SetCoverSolution(weight,s,c);
	}
	
	
	public static List<SetCoverEdge> greedyPath(SetCoverVertex vertex, Integer lastIndex) {
		List<SetCoverEdge> ls = new ArrayList<>();
		while(!vertex.actions().isEmpty() && vertex.index() < lastIndex && !vertex.isUniverseCovered()) {
			Integer a = vertex.greedyAction();
			SetCoverEdge e = vertex.edge(a);
			ls.add(e);
			vertex = vertex.neighbor(a);
		}
		return ls;
	}
	
	public static Double heuristic0(SetCoverVertex vertex, Integer lastIndex) {
		return 0.;
	}

	public static Double heuristic1(SetCoverVertex vertex, Integer lastIndex) {
		if (vertex.isUniverseCovered() || greedy(vertex,lastIndex) == 0.)  return 0.;
		else return IntStream.range(vertex.index(),lastIndex)
				.mapToDouble(i->SetCoverData.getWeight(i))
				.min()
				.getAsDouble();
	}
	
	public static Double heuristic2(SetCoverVertex vertex, Integer lastIndex) {
		Double peso = 0.;
		while(!vertex.actions().isEmpty() && vertex.index() < lastIndex && !vertex.isUniverseCovered_after()) {
			Integer a = vertex.greedyAction();
			peso += a*SetCoverData.getWeight(vertex.index());
			vertex = vertex.neighbor(a);
		}
		return peso;
	}
	
	public static void main(String[] args) {

		
		Locale.setDefault(Locale.of("en", "US"));

		for (Integer file_id = 1; file_id < 3; file_id++) {

			SetCoverData.iniDatos("files/setCover/setCover" + file_id + ".txt");
			System.out.println("\n\n>\tTest results " + file_id + "\n");
//			DatosSubconjuntos.toConsole();
			
			SetCoverVertex start = SetCoverVertex.initial();
			
			String2.toConsole("Greedy solution = "+greedySolution(start,SetCoverData.NUM_SC).toString());
			String2.toConsole("Greedy = "+greedy(start,SetCoverData.NUM_SC).toString());
			String2.toConsole("H1 = "+heuristic1(start,SetCoverData.NUM_SC).toString());
			String2.toConsole("H2 = "+heuristic2(start,SetCoverData.NUM_SC).toString());
		}
	}

}

