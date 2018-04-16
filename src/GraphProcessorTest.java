import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;


public class GraphProcessorTest {
	
	private GraphProcessor gp;
	
	private static List<String> vertices;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		vertices = new ArrayList<>();
		try {
			List<String> words = 
					WordProcessor.getWordStream("word_list.txt").collect(Collectors.toList());
			for (String word : words) {
				if (!vertices.contains(word)) vertices.add(word);
			}
		} catch (IOException e) {
			System.out.println("Could not get word stream from word_list.txt");
		}
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		vertices = null;
	}

	@Before
	public void setUp() throws Exception {
		this.gp = new GraphProcessor();
		gp.populateGraph("word_list.txt");
	}

	@After
	public void tearDown() throws Exception {
		this.gp = null;
	}
	
	@Test
	public final void testGetWordStream() {
		for (String word : vertices) {
			System.out.println(word);
			assertEquals("Words were not properly trimmed", true, word.equals(word.trim()));
			assertEquals("Stream contained an empty string", false, word.equals(""));
			assertEquals("Words were not all uppercase", true, word.equals(word.toUpperCase()));
		}
	}
	
	@Test
	public final void testPopulateGraph() {
		int numVertices = gp.populateGraph("word_list.txt");
		assertEquals("number of vertices in graph doesn't match number of vertices added", 
				vertices.size(), numVertices);
	}
	
	@Test
	public final void getShortestPathSameWord() {
		for (String word : vertices) {
			List<String> shortestPath = gp.getShortestPath(word, word);
			assertEquals("shortest path vertex list was not empty", true, shortestPath.isEmpty());
		}
	}
	
	@Test
	public final void getShortestDistanceSameWord() {
		for (String word : vertices) {
			int pathLength = gp.getShortestDistance(word, word);
			assertEquals("shortest path length was not zero", 0, pathLength);
		}
	}
	
	@Test
	public final void getShortestPathAdjacentWords() {
		for (String word1 : vertices) {
			for (String word2 : vertices) {
				if (WordProcessor.isAdjacent(word1, word2)) {
					List<String> shortestPath = gp.getShortestPath(word1, word2);
					int pathLength = gp.getShortestDistance(word1, word2);
					assertEquals("shortest path length was not 1", 
							1, pathLength);
					assertEquals("shortest path was different than expected", 
							word2, shortestPath.get(0));
				}
			}
		}
	}
	
	@Test
	public final void getShortestPathDifferentWords() {
		List<String> expected = shortestPath("COMEDO", "CHARGE");
		List<String> actual = gp.getShortestPath("COMEDO", "CHARGE");
		for (int i = 0; i < expected.size(); ++i) {
			assertEquals("shortest path between \"COMEDO\" and \"CHARGE\""
					+ " differs at index " + i, expected.get(i), actual.get(i));
		}
		expected = shortestPath("CHARGE", "GIMLETS");
		actual = gp.getShortestPath("CHARGE", "GIMLETS");
		for (int i = 0; i < expected.size(); ++i) {
			assertEquals("shortest path between \"CHARGE\" and \"GIMLETS\""
					+ " differs at index " + i, expected.get(i), actual.get(i));
		}
		expected = shortestPath("BELLIES", "JOLLIES");
		actual = gp.getShortestPath("BELLIES", "JOLLIES");
		for (int i = 0; i < expected.size(); ++i) {
			assertEquals("shortest path between \"BELLIES\" and \"JOLLIES\" "
					+ "differs at index " + i, expected.get(i), actual.get(i));
		}
		expected = shortestPath("DEFINE", "SHINNY");
		actual = gp.getShortestPath("DEFINE", "SHINNY");
		for (int i = 0; i < expected.size(); ++i) {
			assertEquals("shortest path between \"DEFINE\" and \"SHINNY\" "
					+ "differs at index " + i, expected.get(i), actual.get(i));
		}
	}
	
	private List<String> shortestPath(String start, String finish) {
		Graph<String> graph = new Graph<String>();
		for (String word1 : vertices) {
			graph.addVertex(word1);
			for (String word2 : graph.getAllVertices()) {
				if (WordProcessor.isAdjacent(word1, word2)) graph.addEdge(word1, word2);
			}
		}
		ArrayDeque<String> queue = new ArrayDeque<String>();
		List<String> visited = new ArrayList<String>();
		Map<String, String> prev = new HashMap<String, String>();
		LinkedList<String> reversePath = new LinkedList<String>();
		List<String> shortestPath = new ArrayList<String>();
		String current = "";
	    visited.add(start);
	    queue.add(start);
	    while (!queue.isEmpty()) {
	        current = queue.remove();
		if (current.equals(finish)) break;
	        for (String child : graph.getNeighbors(current)) {
	        	
	            if (!visited.contains(child)){
	                visited.add(child);
	                queue.add(child);
	                prev.put(child, current);
	            } // end if k not visited
	        } // end for every successor k
	    } // end while queue not empty
	    if (!current.equals(finish)) return null;
	    for (String word = finish; word != null; word = prev.get(word)) {
	    	reversePath.add(word);
	    }
	    Iterator<String> descendingIterator = reversePath.descendingIterator();
	    while (descendingIterator.hasNext()) shortestPath.add(descendingIterator.next());
	    return shortestPath;
	}
	
	@Test
	public final void getShortestDistanceDifferentWords() {
		int pathLength = gp.getShortestDistance("COMEDO", "CHARGE");
		assertEquals("shortest path length between \"COMEDO\" and \"CHARGE\" "
				+ "was different than expected", 49, pathLength);
		pathLength = gp.getShortestDistance("CHARGE", "GIMLETS");
		assertEquals("shortest path length between \"CHARGE\" and \"GIMLETS\" "
				+ "was different than expected", 78, pathLength);
		pathLength = gp.getShortestDistance("BELLIES", "JOLLIES");
		assertEquals("shortest path length between \"BELLIES\" and \"JOLLIES\""
				+ " was different than expected", 2, pathLength);
		pathLength = gp.getShortestDistance("DEFINE", "SHINNY");
		assertEquals("shortest path length between \"DEFINE\" and \"SHINNY\" "
				+ "was different than expected", 26, pathLength);
	}
	
}
