package examples.multiset;


import java.util.Locale;
import java.util.function.Predicate;

import org.jgrapht.GraphPath;

import examples.multiset.data.MultisetData;
import graphs.alg.Greedy;
import graphs.alg.GreedyOnGraph;
import graphs.virtual.EGraph;
import graphs.virtual.EGraph.Type;
import path.EGraphPath.PathType;


public class MultisetHeuristic {

	public static Double heuristic(MultisetVertex v1, Predicate<MultisetVertex> goal, MultisetVertex v2) {
		return hu(Md.of(v1.index(),(double)v1.remaining_amount()),
				v->v.index()==MultisetData.NUM_E|| v.cr()==0.);
	}
	
	public static record Md(Integer index, Double cr) {
		public static Md of(Integer index, Double cr) {
			return new Md(index,cr);
		}
		public Double heuristicAction() {
			return ((double)this.cr)/ MultisetData.getElement(this.index);
		}
		
		public Md next() {
			Double a = heuristicAction();
			return new Md(this.index()+1,this.cr()-MultisetData.getElement(this.index()) * a);
		}
		public Double weight() {
			if(this.index >= MultisetData.NUM_E) return 0.;
			return heuristicAction();
		}
	}

	public static Double hu(Md v1, Predicate<Md> goal) {	
		Greedy<Md> r = Greedy.of(v1,v->v.next(),goal);
		return r.stream().mapToDouble(v->v.weight()).sum();
	}
	
	
	public static void main(String[] args) {

		// Set up
		Locale.setDefault(Locale.of("en", "US"));

		for (Integer file_id = 0; file_id < 7; file_id++) {

			MultisetData.iniData("files/multiset.txt", file_id);
			System.out.println("\n\n>\tTest results " + file_id + "\n");
			
			MultisetData.toConsole();

			MultisetVertex start = MultisetVertex.initial();
//			Predicate<MulticonjuntoVertex> finalVertex = v -> MulticonjuntoVertex.goal(v);
		
			Predicate<MultisetVertex> goal = MultisetVertex.goal();

			// Graph
			
			EGraph<MultisetVertex, MultisetEdge> graph =
					EGraph.virtual(start,goal,PathType.Sum, Type.Min)
					.edgeWeight(x -> x.weight())
					.goalHasSolution(MultisetVertex.goalHasSolution())
					.heuristic(MultisetHeuristic::heuristic)
					.build();
			
			GraphPath<MultisetVertex, MultisetEdge> r = 
					GreedyOnGraph.of(graph,MultisetVertex::greedyEdge).path();
			
			System.out.println();
			System.out.println(MultisetVertex.goalHasSolution().test(r.getEndVertex()));
			System.out.println(r.getWeight());
			System.out.println(heuristic(start,MultisetVertex.goal(),null));
		}
	}

}

