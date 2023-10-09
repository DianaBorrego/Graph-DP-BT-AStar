package graphs.virtual;

import java.util.function.Predicate;

import org.jgrapht.graph.DirectedMultigraph;

import utils.Preconditions;
import graphs.virtual.EGraph.Type;
import path.EGraphPath.PathType;

public class EGraphBuilderVirtualMG<V, E> extends EGraphBuilderVirtual<VirtualVertexMG<V, E>, VirtualEdgeMG<V, E>> {

	private DirectedMultigraph<V, E> graph;

	public EGraphBuilderVirtualMG(DirectedMultigraph<V, E> graph, V startVertex, Predicate<V> goal, PathType pathType,
			Type type) {
		super(VirtualVertexMG.of(startVertex, null, graph), v -> goal.test(v.vertex()), pathType, type);
		this.graph = graph;
		Preconditions.checkArgument(graph.getType().isMultigraph(), "The graph must be Multigraph");
	}

	public DirectedMultigraph<V, E> graph() {
		return graph;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
