import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Undirected and unweighted graph implementation
 * 
 * @param <E>
 *            type of a vertex
 * 
 * @author sapan (sapan@cs.wisc.edu)
 * 
 */
public class Graph<E> implements GraphADT<E> {

	private ArrayList<GraphNode<E>> graph;

	/**
	 * Instance variables and constructors
	 */
	public Graph() {
		graph = new ArrayList<GraphNode<E>>();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public E addVertex(E vertex) {
		// check for null
		if (vertex == null)
			return null;
		// check for the vertex to already be in the graph
		for (GraphNode<E> e : graph) {
			if (e.getVertex().equals(vertex)) {
				return null;
			}
		}
		// if not add the vertex and return it
		graph.add(new GraphNode<E>(vertex));
		return vertex;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public E removeVertex(E vertex) {
		// check for a null vertex
		if (vertex == null)
			return null;
		GraphNode<E> node = null;
		// if there exists a node with the specified vertex set that node to the
		// temporary one
		for (GraphNode<E> e : graph) {
			if (e.getVertex().equals(vertex)) {
				node = e;
			}
		}
		// if a node was found then go through and deletes the edges from all of its
		// neighbors
		if (node != null) {
			for (E neighbor : getNeighbors(node.getVertex()))
				removeEdge(node.getVertex(), neighbor);
			graph.remove(node);
			return vertex;
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean addEdge(E vertex1, E vertex2) {
		try {
			boolean added = false;
			// check if both vertices are in the graph
			boolean contained = false;
			if (vertex1.equals(vertex2) || vertex1 == null || vertex2 == null) {
				return false;
			}
			for (GraphNode<E> e : graph) {
				if (e.getVertex().equals(vertex1)) {
					contained = true;
				}
				if (contained)
					for (GraphNode<E> n : graph) {
						if (n.getVertex().equals(vertex2)) {
							contained = true;
							break;
						} else
							contained = false;
					}
			}
			// if they are contained then add edges going from each vertex to the other
			if (contained) {
				for (GraphNode<E> e : graph) {

					if (e.getVertex().equals(vertex1)) {
						e.addEdge(vertex2);
					}
					if (e.getVertex().equals(vertex2)) {
						e.addEdge(vertex1);
					}
					added = true;
				}
			}
			return added;
		} catch (NullPointerException e) {
			return false;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean removeEdge(E vertex1, E vertex2) {
		boolean removed = false;
		// check if there exists both vertices to be removed
		boolean contained = false;
		if (vertex1.equals(vertex2) || vertex1 == null || vertex2 == null) {
			return false;
		}
		for (GraphNode<E> e : graph) {
			if (e.getVertex().equals(vertex1)) {
				contained = true;
			}
			if (contained)
				for (GraphNode<E> n : graph) {
					if (n.getVertex().equals(vertex2)) {
						contained = true;
						break;
					} else
						contained = false;
				}
		}
		// if they are contained then remove all edges between them
		if (contained) {
			for (GraphNode<E> e : graph) {
				if (e.getVertex().equals(vertex1) && e.getEdges().contains(vertex2)) {
					e.removeEdge(vertex2);
				} else if (e.getVertex().equals(vertex2) && e.getEdges().contains(vertex1)) {
					e.removeEdge(vertex1);
				}
			}
			// set the remove boolean to true
			removed = true;
		}
		// return the result
		return removed;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isAdjacent(E vertex1, E vertex2) {
		try {
			// check if both vertices are contained non-null and different
			boolean contained = false;
			if (vertex1.equals(vertex2) || vertex1 == null || vertex2 == null) {
				return false;
			}
			for (GraphNode<E> e : graph) {
				if (e.getVertex().equals(vertex1)) {
					contained = true;
				}
				if (contained)
					for (GraphNode<E> n : graph) {
						if (n.getVertex().equals(vertex2)) {
							contained = true;
							break;
						} else
							contained = false;
					}
			}
			// if they are in the graph then check if they have an edge between them
			if (contained) {
				for (GraphNode<E> e : graph)
					if (e.getVertex().equals(vertex1))
						if (e.getEdges().contains(vertex2))
							return true;
			}
			return false;
		} catch (NullPointerException e) {
			return false;
		}

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Iterable<E> getNeighbors(E vertex) {
		// check for null
		if (vertex == null)
			return null;
		try {
			// find the vertex
			for (GraphNode<E> e : graph)
				// once found, return its adjacency list
				if (e.getVertex().equals(vertex))
					return e.getEdges();
			return null;
		} catch (NullPointerException e) {
			return null;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Iterable<E> getAllVertices() {
		// create an array list
		ArrayList<E> vertices = new ArrayList<E>();
		// for each node add its vertex to the array list
		for (GraphNode<E> e : graph)
			vertices.add(e.getVertex());
		return vertices;
	}

}

class GraphNode<T> {
	private T vertex;
	private ArrayList<T> adjacencyList;

	/**
	 * constructs an empty GraphNode
	 */
	public GraphNode() {
		vertex = null;
		adjacencyList = new ArrayList<T>();
	}

	/**
	 * constructs a Graphnode with a specified vertex
	 * 
	 * @param vertex
	 *            the vertex of the new node
	 */
	public GraphNode(T vertex) {
		this.vertex = vertex;
		adjacencyList = new ArrayList<T>();
	}

	public T getVertex() {
		return vertex;
	}

	/**
	 * adds an edge between this node and another vertex
	 * 
	 * @param vertex
	 *            the destination of the edge
	 * @return the vertex passed in if an edge is created
	 */
	public T addEdge(T vertex) {
		if (adjacencyList.contains(vertex))
			return null;
		adjacencyList.add(vertex);
		return vertex;
	}

	/**
	 * removes an edge between this node and the vertex passed in
	 * 
	 * @param vertex
	 *            the destination of the edge to be removed
	 * @return the vertx of the destination of the target if it is removed
	 */
	public T removeEdge(T vertex) {
		if (adjacencyList.contains(vertex)) {
			adjacencyList.remove(vertex);
			return vertex;
		} else
			return null;
	}

	public ArrayList<T> getEdges() {
		return adjacencyList;
	}
}
