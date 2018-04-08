import java.awt.List;
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

    private ArrayList<E> graph;

    /**
     * Instance variables and constructors
     */

    /**
     * {@inheritDoc}
     */
    @Override
    public E addVertex(E vertex) {
        try {
            for (E e : getAllVertices()) {
                if (e.equals(vertex)) {
                    throw new DuplicateKeyException();
                }
            }
            graph.add(vertex);
            return vertex;
        } catch (NullPointerException e) {
            return null;
        } catch (DuplicateKeyException e) {
            return null;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public E removeVertex(E vertex) {
        boolean remove = false;
        int index = 0;
        for (E e : getAllVertices()) {
            if (e.equals(vertex)) {
                graph.remove(index);
                remove = true;
            }
            index++;
        }
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
        ArrayList<E> iterable = new ArrayList<>();
        return iterable;
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
        if (adjacencyList.contains(vertex))
            return null;
        adjacencyList.add(vertex);
        return vertex;
    }

    public GraphNode removeEdge(GraphNode vertex) {
        if (adjacencyList.contains(vertex)) {
            adjacencyList.remove(vertex);
            return vertex;
        } else
            return null;
    }

    public ArrayList getEdges() {
        return adjacencyList;
    }
}
