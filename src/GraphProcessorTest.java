///////////////////////////////////////////////////////////////////////////////
//
// Title:           p4: Dictionary Graph
// Files:           GraphProcessorTest.java, GraphProcessor.java, 
//                  WordProcessor.java, Graph.java
// Semester:        Spring 2018
//
// Authors:         Josh Stoecker, Jessie Steckling, Nick Stoffel, 
//                  Stephen Squires III, Tyler Snoberger
// Email:           jstoecker@wisc.edu
// Lecturer's Name: Deb Deppeler
//
///////////////////////////////////////////////////////////////////////////////

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
 * @author Jessie Steckling
 * @author Nick Stoffel
 * @author Stephen Squires III
 * @author Tyler Snoberger
 */
public class GraphProcessorTest {
@@ -103,70 +107,24 @@ public final void getShortestDistanceSameWord() {
 	}
 	
 	/**
-	 * Tests that getShortestDistance() returns 2 for each pair of adjacent nodes.
-	 */
-	@Test
-	public final void getShortestDistanceAdjacentWords() {
-		for (String word1 : vertices) {
-		    for (String word2 : vertices) {
-			if (WordProcessor.isAdjacent(word1, word2)) {
-			    int actual = gp.getShortestDistance(word1, word2);
-			    assertEquals("Shortest path between adjacent words length was not 1",
-					    1, actual);
-			}
-		    }
-		}
-	    }
-
-	/**
-	 * Tests that getShortestPath() returns the correct composed of the start and end node for each pair of adjacent nodes.
+	 * Tests that getShortestPath() returns the correct path for all adjacent words
 	 */
 	@Test
 	public final void getShortestPathAdjacentWords() {
 		for (String word1 : vertices) {
-		    for (String word2 : vertices) {
-			if (WordProcessor.isAdjacent(word1, word2)) {
-			    List<String> actual = gp.getShortestPath(word1, word2);
-			    List<String> expected = new ArrayList<String>();
-			    expected.add(word1);
-			    expected.add(word2);
-
-			    assertEquals("Shortest path between adjacent words is not a list with the start and end node",
-					    expected, actual);
+			for (String word2 : vertices) {
+				if (WordProcessor.isAdjacent(word1, word2)) {
+					List<String> shortestPath = gp.getShortestPath(word1, word2);
+					int pathLength = shortestPath.size();
+					assertEquals("shortest path length was not 2", 
+							2, pathLength);
+					assertEquals("shortest path was different than expected", 
+							word2, shortestPath.get(1));
+				}
 			}
-		    }
 		}
 	}
 	
-	/**
-	 * Tests that getShortestDistance() returns correct path for non-adjacent words "Jellies" and "Flexing"
-	 */
-	@Test
-    	public final void getShortestDistanceForModerateLengthPath() {
-     	   int actualDistance = gp.getShortestDistance("JELLIES", "FLEXING");
-       		 assertEquals("Calculated path distance is not equal to the expected",
-                        12, actualDistance);
-    	}
-
-	/**
-	 * Tests that getShortestDistance() returns the correct distance for non-adjacent words "Jellies" and "Flexing"
-	 */
-    	@Test
-  	  public final void getShortestPathForModerateLengthPath() {
-     	 	List<String> actual = gp.getShortestPath("JELLIES", "FLEXING");
-      		String[] e = {"JELLIES", "JOLLIES", "COLLIES", "COLLINS", "COLLING",
-                        "COALING", "COAMING", "FOAMING", "FLAMING", "FLAKING",
-                        "FLUKING", "FLUXING", "FLEXING"};
-        	ArrayList<String> expected = new ArrayList<String>();
-        	for (String s : e)
-            		expected.add(s);
-        	for (int i = 0; i < expected.size(); ++i) {
-            		assertEquals("shortest path between \"JELLIES\" and \"FLEXING\""
-                            	+ " differs at index " + i, expected.get(i),
-                            	actual.get(i));
-        	}
-    	}
-	
 	/**
 	 * Tests that getShortestPath() returns correct path for non-adjacent words
 	 */
@@ -217,57 +175,4 @@ public final void getShortestDistanceDifferentWords() {
 				+ "was different than expected", 26, pathLength);
 	}
 	
-	/**
-	 * Tests that getShortestDistance() does not throw an exception for words without an existing path
-	 */
-	@Test
-    	public final void getShortestDistanceForNonexistantPathNoException() {
-		boolean threwException = false;
-		String exceptionMessage = "";
-		try {
-		    int pathLength = gp.getShortestDistance("VANE", "SILO");
-		} catch (RuntimeException e) {
-		    exceptionMessage = e.getMessage();
-		    threwException = true;
-		}
-		assertEquals("shortest distance between two nodes without a path threw an exception: " + exceptionMessage,
-				false, threwException);
-    	}
-    
-	/**
-	 * Tests that getShortestDistance() returns -1 for words without an existing path
-	 */
-    	@Test
-		public final void getShortestDistanceForNonexistant() {
-		int pathLength = gp.getShortestDistance("VANE", "SILO");
-		assertEquals("shortest distance between two nodes without a path threw an exception",
-				-1, pathLength);
-    	}
-
-	/**
-	 * Tests that getShortestPath() does not throw an exception for words without an existing path
-	 */
-    	@Test
-    	public final void getShortestPathForNonexistantPathNoException() {
-		boolean threwException = false;
-		String exceptionMessage = "";
-		try {
-		    List<String> path = gp.getShortestPath("VANE", "SILO");
-		    path.isEmpty();
-		} catch (RuntimeException e) {
-		    threwException = true;
-		    exceptionMessage = e.getMessage();
-		}
-		assertEquals("Shortest path between nodes without a path threw an exception: " + exceptionMessage, false, threwException);
-    	}
-
-	/**
-	 * Tests that getShortestPath() an empty list for words without an existing path
-	 */
-    	@Test
-    	public final void getShortestPathForNonexistantPath() {
-		List<String> actual = gp.getShortestPath("VANE", "SILO");
-		assertEquals("shortest path length between nodes which do not have a path is not an empty list", true, actual.isEmpty());
-    	}
-	
 }
