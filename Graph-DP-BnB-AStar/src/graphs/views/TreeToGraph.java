package graphs.views;

import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleDirectedGraph;

import recursiveTypes.BinaryTree;
import recursiveTypes.BinaryTree.BEmpty;
import recursiveTypes.BinaryTree.BLeaf;
import recursiveTypes.BinaryTree.BTree;
import recursiveTypes.Tree;
import recursiveTypes.Tree.TEmpty;
import recursiveTypes.Tree.TLeaf;
import recursiveTypes.Tree.TNary;

public class TreeToGraph {

	public static <E> void addEdges(BinaryTree<E> tree, SimpleDirectedGraph<BinaryTree<E>, DefaultEdge> graph) {
		switch (tree) {
		case BEmpty<E> t -> {
		}
		case BLeaf<E> t -> {
		}
		case BTree<E> t -> {
			graph.addEdge(t, t.left());
			graph.addEdge(t, t.right());
			addEdges(t.left(), graph);
			addEdges(t.right(), graph);
		}
		}
		;
	}

	public static <E> SimpleDirectedGraph<BinaryTree<E>, DefaultEdge> toGraph(BinaryTree<E> tree) {
		SimpleDirectedGraph<BinaryTree<E>, DefaultEdge> graph = new SimpleDirectedGraph<>(null, () -> new DefaultEdge(),
				false);
		tree.byDepth().distinct().forEach(t -> graph.addVertex(t));
		addEdges(tree, graph);
		return graph;
	}

	public static <E> void addEdges(Tree<E> tree, SimpleDirectedGraph<Tree<E>, DefaultEdge> graph) {
		switch (tree) {
		case TEmpty<E> t -> {
		}
		case TLeaf<E> t -> {
		}
		case Tree<E> t -> t.children().stream().forEach(tt -> {
			graph.addEdge(t, tt);
			addEdges(tt, graph);
		});
		}
		;
	}

	public static <E> SimpleDirectedGraph<Tree<E>, DefaultEdge> toGraph(Tree<E> tree) {
		SimpleDirectedGraph<Tree<E>, DefaultEdge> graph = new SimpleDirectedGraph<>(null, () -> new DefaultEdge(),
				false);
		tree.byDepth().distinct().forEach(t -> graph.addVertex(t));
		addEdges(tree, graph);
		return graph;
	}

}
