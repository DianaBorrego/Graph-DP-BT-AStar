package examples.robots.tests;

import java.util.Locale;
import java.util.Optional;

import org.jgrapht.GraphPath;

import examples.robots.RobotEdge;
import examples.robots.RobotSolution;
import examples.robots.RobotVertex;
import graphs.alg.PDR;
import graphs.virtual.EGraph;
import graphs.virtual.EGraph.Type;
import path.EGraphPath.PathType;


public class TestPDR_robots {

	public static void main(String[] args) {
		Locale.setDefault(Locale.of("en", "US"));
		RobotVertex v0 = RobotVertex.of();	
		
		EGraph<RobotVertex, RobotEdge> graph = 
				EGraph.virtual(v0,v->v.goal(), PathType.Sum, Type.Max)	
				.edgeWeight(e->e.weight())
				.heuristic((v1,p,v2)->3.*(RobotVertex.N-v1.t()))
				.build();
		
		
		PDR<RobotVertex,RobotEdge, RobotSolution> ms = PDR.of(graph,RobotSolution::of, null, null,true);
		
		Long t0 = System.nanoTime();
		Optional<GraphPath<RobotVertex,RobotEdge>> path = ms.search();
		Long t1 = System.nanoTime();
		System.out.println(t1-t0);
		System.out.println(RobotSolution.of(path.get()));
		
//		GraphColors.toDot(ms.outGraph(),"files/RobotsDPR.gv",
//				v->String.format("%d,%d",v.getX().get(3),v.getT()),
//				e->e.action().toString(),
//				v->GraphColors.colorIf(Color.red,v.goal()),
//				e->GraphColors.colorIf(Color.red,path.get().getEdgeList().contains(e))
//				);

	}

}
