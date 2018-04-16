import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import com.sun.scenario.effect.impl.prism.ps.PPSBlend_ADDPeer;

// TODO: what happens if you look up shortest path when there is no path


/**
 * This class adds additional functionality to the graph as a whole.
 * 
 * Contains an instance variable, {@link #graph}, which stores information for all the vertices and
 * edges.
 * 
 * @see #populateGraph(String) - loads a dictionary of words as vertices in the graph. - finds
 *      possible edges between all pairs of vertices and adds these edges in the graph. - returns
 *      number of vertices added as Integer. - every call to this method will add to the existing
 *      graph. - this method needs to be invoked first for other methods on shortest path
 *      computation to work.
 * @see #shortestPathPrecomputation() - applies a shortest path algorithm to precompute data
 *      structures (that store shortest path data) - the shortest path data structures are used
 *      later to to quickly find the shortest path and distance between two vertices. - this method
 *      is called after any call to populateGraph. - It is not called again unless new graph
 *      information is added via populateGraph().
 * @see #getShortestPath(String, String) - returns a list of vertices that constitute the shortest
 *      path between two given vertices, computed using the precomputed data structures computed as
 *      part of {@link #shortestPathPrecomputation()}. - {@link #shortestPathPrecomputation()} must
 *      have been invoked once before invoking this method.
 * @see #getShortestDistance(String, String) - returns distance (number of edges) as an Integer for
 *      the shortest path between two given vertices - this is computed using the precomputed data
 *      structures computed as part of {@link #shortestPathPrecomputation()}. -
 *      {@link #shortestPathPrecomputation()} must have been invoked once before invoking this
 *      method.
 * 
 * @author Jessie Steckling (jsteckling@wisc.edu)
 * 
 */
public class GraphProcessor {

    /**
     * Graph which stores the dictionary words and their associated connections
     */
    private GraphADT<String> graph;
    private TreeMap<String, TreeMap<String, ArrayList<String>>> shortestPaths =
                    new TreeMap<String, TreeMap<String, ArrayList<String>>>(); // The ArrayList
                                                                               // contains
                                                                               // nodes that form a
                                                                               // path, including
                                                                               // the source
                                                                               // destination node

    class DijkstraTableRow implements Comparable<DijkstraTableRow> { // stores path information from
                                                                     // one of several nodes to a
                                                                     // specific destination
                                                                     // node

        public DijkstraTableRow(String destination) { // stores path information for specific source
                                                      // and desination nodes
            this.destination = destination;
            this.visited = false;
            this.totalWeight = Integer.MAX_VALUE;
            this.pred = null;
        }


        String destination;
        boolean visited;
        int totalWeight;
        String pred;

        @Override
        public int compareTo(DijkstraTableRow other) {
            return other.totalWeight - this.totalWeight;
        }
    }


    /**
     * Constructor for this class. Initializes instances variables to set the starting state of the
     * object
     */
    public GraphProcessor() {
        this.graph = new Graph<>();
        // The outer TreeMap DS assigns a source node key to a collection of paths from that node.
        // The inner Treemap DS assigns a desination node key to a single path from the source to
        // that destination.
        this.shortestPaths =
                        new TreeMap<String, TreeMap<String, ArrayList<String>>>();

    }

    /**
     * Builds a graph from the words in a file. Populate an internal graph, by adding words from the
     * dictionary as vertices and finding and adding the corresponding connections (edges) between
     * existing words.
     * 
     * Reads a word from the file and adds it as a vertex to a graph. Repeat for all words.
     * 
     * For all possible pairs of vertices, finds if the pair of vertices is adjacent
     * {@link WordProcessor#isAdjacent(String, String)} If a pair is adjacent, adds an undirected
     * and unweighted edge between the pair of vertices in the graph.
     * 
     * @param filepath file path to the dictionary
     * @return Integer the number of vertices (words) added
     */
    public Integer populateGraph(String filepath) {
        int vtxCount = 0;

        Stream<String> wordStream;
        try {
            wordStream = WordProcessor.getWordStream(filepath);
        } catch (IOException e) {
            e.printStackTrace();
            wordStream = Stream.empty();
        }

        List<String> wordList = wordStream.collect(Collectors.toList());

        int s = wordList.size();
        for (int i = 0; i < s; i++) { // for each word in the list
            String word1 = wordList.get(i);

            if (graph.addVertex(word1) != null) // add the word to the graph
                vtxCount++;

            // add an edge between the new vertex and each adjacent node in the graph
            for (int j = 0; j < i; j++) {
                String word2 = wordList.get(j);
                if (WordProcessor.isAdjacent(word1, word2)) {
                    graph.addEdge(word1, word2);
                }
            }
        }

        shortestPathPrecomputation();
        return vtxCount;
    }


    /**
     * Gets the list of words that create the shortest path between word1 and word2
     * 
     * Example: Given a dictionary, cat rat hat neat wheat kit shortest path between cat and wheat
     * is the following list of words: [cat, hat, heat, wheat]
     * 
     * @param word1 first word
     * @param word2 second word
     * @return List<String> list of the words
     */
    public List<String> getShortestPath(String word1, String word2) {
        if(word1.equals(word2)) return new ArrayList<String>();
        if (shortestPaths.containsKey(word1)
                        && shortestPaths.get(word1).containsKey(word2)) {
            return shortestPaths.get(word1).get(word2);
        } else {
            System.out.println("path not in shortestPaths");
            return new ArrayList<String>();
        }
        // System.out.println(" shortest path " + word1 + ", " + word2);
        // System.out.println(shortestPaths.size());
        // System.out.println(shortestPaths.get(word1).size());
        // System.out.println(shortestPaths.get(word1).get(word2).size());
    }

    /**
     * Gets the distance of the shortest path between word1 and word2
     * 
     * Example: Given a dictionary, cat rat hat neat wheat kit distance of the shortest path between
     * cat and wheat, [cat, hat, heat, wheat] = 3 (the number of edges in the shortest path)
     * 
     * @param word1 first word
     * @param word2 second word
     * @return Integer distance
     */
    public Integer getShortestDistance(String word1, String word2) {
        if (getShortestPath(word1, word2).isEmpty()) {
            if (!word1.equals(word2)) {
                System.out.println("empty path: " + word1 + ", " + word2);
            }
            return 0;
        }
        return getShortestPath(word1, word2).size() - 1;

    }

    /**
     * Computes shortest paths and distances between all possible pairs of vertices. This method is
     * called after every set of updates in the graph to recompute the path information. Any
     * shortest path algorithm can be used (Djikstra's or Floyd-Warshall recommended).
     */
    public void shortestPathPrecomputation() {
        TreeMap<String, TreeMap<String, DijkstraTableRow>> dijkstraTables =
                        generateDijkstraTables();
        evaluateShortestPaths(dijkstraTables);

    }

    public TreeMap<String, TreeMap<String, DijkstraTableRow>> generateDijkstraTables() {
        TreeMap<String, TreeMap<String, DijkstraTableRow>> dijkstraTables =
                        new TreeMap<String, TreeMap<String, DijkstraTableRow>>();

        // each iteration creates a dijkstra table with a certain vertex as the startnode
        for (String source : graph.getAllVertices()) {

            TreeMap<String, DijkstraTableRow> dijkstraTableWithStartnode =
                            new TreeMap<String, DijkstraTableRow>();

            // step 1: initialize (in DijkstraTableRow constructor)
            dijkstraTableWithStartnode.put(source,
                            new DijkstraTableRow(source));
            for (String destination : graph.getAllVertices()) {
                dijkstraTableWithStartnode.put(destination,
                                new DijkstraTableRow(destination));
            }


            dijkstraTableWithStartnode.get(source).totalWeight = 0;

            java.util.PriorityQueue<DijkstraTableRow> pq =
                            new java.util.PriorityQueue<DijkstraTableRow>();
            pq.add(dijkstraTableWithStartnode.get(source));

            // step 2: update predecessors and total weights in dijkstra table
            while (!pq.isEmpty()) {
                DijkstraTableRow current = pq.remove(); // removes the minimum? ties?
                current.visited = true;


                for (String node : graph.getNeighbors(current.destination)) { // for each successor
                                                                              // of current
                    // if(!dijkstraTableWithStartnode.containsKey(node)) {
                    // System.out.println("err: " + source + ", to " + node);
                    // System.out.println(" adj: " + WordProcessor.isAdjacent(source, node));
                    // break;
                    // }
                    int tempTotalWeight = dijkstraTableWithStartnode
                                    .get(current.destination).totalWeight + 1; // increment so
                                                                               // represent weight
                    // between current and node

                    if (tempTotalWeight < dijkstraTableWithStartnode
                                    .get(node).totalWeight) { // total weight can be reduced
                        dijkstraTableWithStartnode.get(node).totalWeight =
                                        tempTotalWeight;
                        dijkstraTableWithStartnode.get(node).pred =
                                        current.destination;
                        if (source.equals("GIBBERS"))
                            System.out.println(source + " to " + node
                                            + " total weight update: "
                                            + tempTotalWeight + ", pred:"
                                            + current.destination);
                        if (!pq.contains(node))
                            pq.add(dijkstraTableWithStartnode.get(node));
                    }
                }

            }

            dijkstraTables.put(source, dijkstraTableWithStartnode);
        }
        return dijkstraTables;
    }

    public void evaluateShortestPaths(
                    TreeMap<String, TreeMap<String, DijkstraTableRow>> dijkstraTables) {


        for (String source : graph.getAllVertices()) { // for each source vertex
            TreeMap<String, DijkstraTableRow> dijkstraCur =
                            dijkstraTables.get(source); // dijkstraCur is the dijkstra table data
                                                        // with source as a start node
            TreeMap<String, ArrayList<String>> pathsFromVertex =
                            new TreeMap<String, ArrayList<String>>();

            for (String destination : graph.getAllVertices()) { // for each destination vertex


                ArrayList<String> path = new ArrayList<String>();
                path.add(destination);
                String pathNode = destination;


                while (!pathNode.equals(source)) { // iterates backwards through path to reach
                                                   // source

                    if (dijkstraCur.get(pathNode).pred == null) { // no availible path: return empty
                                                                  // list
                        // System.out.println("no availible path");
                        path = new ArrayList<String>();
                        break;
                    } else {
                        pathNode = dijkstraCur.get(pathNode).pred; // add predecessor to the
                                                                   // beginning of the path list
                        path.add(0, pathNode);
                    }
                }
                pathsFromVertex.put(destination, path);
            }
            this.shortestPaths.put(source, pathsFromVertex);
        }

    }

}
