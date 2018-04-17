import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Test class for GraphProcessor.java; tests basic functionality of GraphProcessor
 * making sure that it can properly import words from a .txt file in the current
 * directory and connects the words in the graph properly
 * 
 * @author Josh Stoecker (jstoecker@wisc.edu)
 *
 */
public class GraphProcessorTest {
	
	private GraphProcessor gp;	//Holds instance of GraphProcessor for testing
	
	private static List<String> vertices;	//Holds all the vertices that should be in the graph
	
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
	
	/**
	 * Tests that WordProcessor.getWordStream() properly formats the strings
	 */
	@Test
	public final void testGetWordStream() {
		for (String word : vertices) {
			assertEquals("Words were not properly trimmed", true, word.equals(word.trim()));
			assertEquals("Stream contained an empty string", false, word.equals(""));
			assertEquals("Words were not all uppercase", true, word.equals(word.toUpperCase()));
		}
	}
	
	/**
	 * Tests that populateGraph() returns the number of vertices inserted
	 */
	@Test
	public final void testPopulateGraph() {
		gp = new GraphProcessor();
		int numVertices = gp.populateGraph("word_list.txt");
		assertEquals("number of vertices in graph doesn't match number of vertices added", 
				vertices.size(), numVertices);
	}
	
	/**
	 * Tests that getShortestPath() returns an empty list for the same word
	 */
	@Test
	public final void getShortestPathSameWord() {
		for (String word : vertices) {
			List<String> shortestPath = gp.getShortestPath(word, word);
			//System.out.println(shortestPath.get(0));
			//System.out.println();
			assertEquals("shortest path vertex list was not empty", true, shortestPath.isEmpty());
		}
	}
	
	/**
	 * Tests that getShortestDistance() returns zero for the same word
	 */
	@Test
	public final void getShortestDistanceSameWord() {
		for (String word : vertices) {
			int pathLength = gp.getShortestDistance(word, word);
			assertEquals("shortest path length was not zero", 0, pathLength);
		}
	}
	
	/**
	 * Tests that getShortestPath() returns the correct path for all adjacent words
	 */
	@Test
	public final void getShortestPathAdjacentWords() {
		for (String word1 : vertices) {
			for (String word2 : vertices) {
				if (WordProcessor.isAdjacent(word1, word2)) {
					List<String> shortestPath = gp.getShortestPath(word1, word2);
					int pathLength = shortestPath.size();
					assertEquals("shortest path length was not 1", 
							2, pathLength);
					assertEquals("shortest path was different than expected", 
							word2, shortestPath.get(1));
				}
			}
		}
	}
	
	/**
	 * Tests that getShortestPath() returns correct path for non-adjacent words
	 */
	@Test
	public final void getShortestPathDifferentWords() {
		try {
			List<String> expected = WordProcessor.getWordStream(
					"expected_path_comedo_charge.txt").collect(Collectors.toList());
			List<String> actual = gp.getShortestPath("COMEDO", "CHARGE");
			assertEquals("shortest path between \"COMEDO\" and \"CHARGE\" was different "
					+ "than expected", expected, actual);
			expected = WordProcessor.getWordStream("expected_path_charge_gimlets.txt").collect(
					Collectors.toList());
			actual = gp.getShortestPath("CHARGE", "GIMLETS");
			assertEquals("shortest path between \"CHARGE\" and \"GIMLETS\" "
					+ "was different than expected", expected, actual);
			expected = WordProcessor.getWordStream("expected_path_bellies_jollies.txt").collect(
					Collectors.toList());
			actual = gp.getShortestPath("BELLIES", "JOLLIES");
			assertEquals("shortest path between \"BELLIES\" and \"JOLLIES\" "
					+ "was different than expected", expected, actual);
			expected = WordProcessor.getWordStream("expected_path_define_shinny.txt").collect(
					Collectors.toList());
			actual = gp.getShortestPath("DEFINE", "SHINNY");
			assertEquals("shortest path between \"DEFINE\" and \"SHINNY\" "
					+ "was different than expected", expected, actual);
		} catch (IOException e) {
			System.out.println("Error loading one or more expected path files");
		}
	}
	
	/**
	 * Tests that the correct path length is returned by getShortestDistance() for non-adjacent words
	 */
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
