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
    
    /**
     * Instance variables and constructors
     */

    /**
     * {@inheritDoc}
     */
    @Override
    public E addVertex(E vertex) {
        
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public E removeVertex(E vertex) {
        
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean addEdge(E vertex1, E vertex2) {
        
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
        
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Iterable<E> getAllVertices() {
        
    }

}

class GraphNode<T> {
	T vertex;
	ArrayList<GraphNode> adjacencyList;
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
