package graphs.virtual;

public interface SimpleEdgeAction<V, A> extends graphs.SimpleEdge<V> {
	
	public static <V, A> SimpleEdgeAction<V, A> of(V c1, V c2, A action, Double weight) {
		return new ActionSimpleEdgeR<V, A>(c1, c2, action, weight);
	}
	
	A action();
	
	public static record ActionSimpleEdgeR<V,A>(V source, V target, A action, Double weight) 
	             implements SimpleEdgeAction<V, A> {}

}
