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
    
	private Set<GraphNode> graph;

    /**
     * Instance variables and constructors
     */

    /**
     * {@inheritDoc}
     */
    @Override
    public E addVertex(E vertex) {
        try {
            graph.add(new GraphNode(vertex));
            return vertex;
        } catch (NullPointerException e) {
            return null;
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public E removeVertex(E vertex) {
        boolean remove = false;
        remove = graph.remove(vertex);
        if (remove) {
            return vertex;
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean addEdge(E vertex1, E vertex2) {
        boolean added = false;
        if (graph.contains(vertex1) && graph.contains(vertex2)) {
            for (GraphNode<E> e : graph) {
                if (e.equals(vertex1)) {
                    e.addEdge(new GraphNode<E>(vertex2));
                }
                if (e.equals(vertex2)) {
                    e.addEdge(new GraphNode<E>(vertex1));
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
    public boolean removeEdge(E vertex1, E vertex2) {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isAdjacent(E vertex1, E vertex2) {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Iterable<E> getNeighbors(E vertex) {
    	try {
    	if(graph.contains(vertex))
    		for(GraphNode e: graph)
    			if(e.equals(vertex))
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
        return graph;
    }

}

class GraphNode<T> {
	private T vertex;
	private ArrayList<GraphNode> adjacencyList;
	public GraphNode() {
		vertex = null;
		adjacencyList = new ArrayList<GraphNode>();
	}
	public GraphNode(T vertex) {
		this.vertex = vertex;
		adjacencyList = new ArrayList<GraphNode>();
	}
	
	public GraphNode addEdge(GraphNode vertex) {
		if(adjacencyList.contains(vertex))
			return null;
		adjacencyList.add(vertex);
		return vertex;
	}
	
	public GraphNode removeEdge(GraphNode vertex) {
		if(adjacencyList.contains(vertex)) {
			adjacencyList.remove(vertex);
			return vertex;
		}
		else
			return null;
	}
	
	public ArrayList getEdges() {
		return adjacencyList;
	}
}
