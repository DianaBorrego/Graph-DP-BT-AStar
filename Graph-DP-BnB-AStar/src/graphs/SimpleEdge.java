package graphs;

import utils.Preconditions;

public interface SimpleEdge<V> {

	/**
	 * @param v1     vertex
	 * @param v2     vertex
	 * @param weight Weight of the edge
	 * @param <V>    Type of the vertices
	 * @return An edge between both vertices
	 */
	public static <V> SimpleEdge<V> of(V v1, V v2, Double weight) {
		return new SimpleEdgeR<V>(v1, v2, weight);
	}

	/**
	 * @param v1  vertex
	 * @param v2  vertex
	 * @param <V> Type of the vertices
	 * @return An edge between both vertices
	 */
	public static <V> SimpleEdge<V> of(V v1, V v2) {
		return new SimpleEdgeR<V>(v1, v2, 1.);
	}

	V source();

	V target();

	Double weight();

	/**
	 * @param v A vertex of the edge
	 * @return The other vertex of the edge
	 */

	public default V otherVertex(V v) {
		Preconditions.checkNotNull(v, "The vertex cannot be null");
		V r = null;
		if (v.equals(this.source()))
			r = this.target();
		else if (v.equals(this.target()))
			r = this.source();
		return r;
	}

	public static record SimpleEdgeR<V>(V source, V target, Double weight) implements SimpleEdge<V> {
		public String toString() {
			return String.format("(%s,%s,%.2f)", source, target, weight);
		}
	}

}
