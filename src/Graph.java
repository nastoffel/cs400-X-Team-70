import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * Undirected and unweighted graph implementation
 * 
 * @param <E> type of a vertex
 * 
 * @author sapan (sapan@cs.wisc.edu)
 * 
 */
public class Graph<E> implements GraphADT<E> {
    
	private ArrayList<GraphNode<E>> graph;

	/**
	 * Instance variables and constructors
	 */
	public Graph(){
		graph = new ArrayList<GraphNode<E>>();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public E addVertex(E vertex) {
		if(vertex == null)
			return null;
		for(GraphNode<E> e: graph) {
			if(e.getVertex().equals(vertex)) {
				return null;
			}
		}
		graph.add(new GraphNode<E>(vertex));
		return vertex;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public E removeVertex(E vertex) {
		if(vertex == null)
			return null;
		GraphNode<E> node = null;
		for(GraphNode<E> e: graph) {
			if(e.getVertex().equals(vertex)) {
				node = e;
			}
		}
		if(node != null) {
			for(E neighbor: getNeighbors(node.getVertex()))
				removeEdge(node.getVertex(), neighbor);
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
			boolean contained = false;
			if(vertex1.equals(vertex2)) {
				return false;
			}
			for (GraphNode<E> e : graph) {
				if(e.getVertex().equals(vertex1)) {
					contained = true;
					break;
				}else
					contained = false;
				for (GraphNode<E> n : graph) {
					if(e.getVertex().equals(vertex2)) {
						contained = true;
						break;
					}else
						contained = false;
				}
			}
			if(contained) {
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
		boolean added = false;
		if (hasVertex(vertex1) && hasVertex(vertex2)) {
			for (GraphNode<E> e : graph) {
				if (e.equals(vertex1)) {
					e.removeEdge(vertex2);
				}
				if (e.equals(vertex2)) {
					e.removeEdge(vertex1);
				}
			}
			added = true;
		}
		
		return added;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isAdjacent(E vertex1, E vertex2) {
		try {
			if(graph.contains(vertex1) && graph.contains(vertex2))
				for(GraphNode<E> e: graph)
					if(e.getVertex().equals(vertex1))
						if(e.getEdges().contains(vertex2))
							return true;
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
		if(vertex == null)
			return null;
		try {
				for(GraphNode<E> e: graph)
					if(e.getVertex().equals(vertex))
						return e.getEdges();
			return null;
		} catch (NullPointerException e){
			return null;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Iterable<E> getAllVertices() {
		ArrayList<E> vertices = new ArrayList<E>();
		for(GraphNode<E> e: graph)
			vertices.add(e.getVertex());
		return vertices;
	}
	
	private boolean hasVertex(E vertex) {
		for(GraphNode<E> e: graph)
			if(e.getVertex().equals(vertex))
				return true;
		return false;
	}
}

class GraphNode<T> {
	private T vertex;
	private ArrayList<T> adjacencyList;
	public GraphNode() {
		vertex = null;
		adjacencyList = new ArrayList<T>();
	}
	public GraphNode(T vertex) {
		this.vertex = vertex;
		adjacencyList = new ArrayList<T>();
	}

	public T getVertex() {
		return vertex;
	}

	public T addEdge(T vertex) {
		if(adjacencyList.contains(vertex))
			return null;
		adjacencyList.add(vertex);
		return vertex;
	}

	public T removeEdge(T vertex) {
		if(adjacencyList.contains(vertex)) {
			adjacencyList.remove(vertex);
			return vertex;
		}
		else
			return null;
	}

	public ArrayList<T> getEdges() {
		return adjacencyList;
	}
}
