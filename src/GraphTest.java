import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Junit test class to test class @see Graph that implements @see GraphADT interface
 *
 * @author sapan (sapan@cs.wisc.edu)
 */
public class GraphTest {

	private GraphADT<String> graph;
	
	private static List<String> vertices;
	
	private static int numOfVertices = 0;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		vertices = new ArrayList<>();
		vertices.add("at");
		vertices.add("it");
		vertices.add("cat");
		vertices.add("hat");
		vertices.add("hot");
		vertices.add("rat");
		vertices.add("heat");
		vertices.add("neat");
		vertices.add("major");
		vertices.add("wheat");
		vertices.add("streak");	
		vertices.add("husband");
		for (String vertex : vertices)
			numOfVertices++;
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		vertices = null;
		numOfVertices = 0;
	}

	@Before
	public void setUp() throws Exception {
		this.graph = new Graph<>();
	}

	@After
	public void tearDown() throws Exception {
		this.graph = null;
	}

	@Test
	public final void addVertexShouldNotAllowNullVertexAddition() {
		String vertex = this.graph.addVertex(null);
		assertEquals("return value when adding null vertex", null, vertex);
		
		int verticesCount = 0;
		for (String itrVertex : this.graph.getAllVertices()) {
			verticesCount++;
		}
		assertEquals("number of vertices in the graph", 0, verticesCount);
	}
	
	@Test
	public final void addVertexShouldAddUniqueVertices() {
		List<String> addedVertices = new ArrayList<>();
		for (String itrVertex1 : vertices) {
			String returnedVertex = this.graph.addVertex(itrVertex1);
			assertEquals("returned value doesn't match passed parameter", itrVertex1, returnedVertex);
			addedVertices.add(returnedVertex);
			int verticesCount = 0;
			for (String itrVertex2 : this.graph.getAllVertices()) {
				verticesCount++;
				assertEquals("added vertex value present in graph", true, addedVertices.contains(itrVertex2));
			}
			assertEquals("number of vertices in the graph", addedVertices.size(), verticesCount);		
		}
	}
	
	@Test
	public final void addVertexShouldNotAllowDuplicateVertexAddition() {
		String vertex1 = this.graph.addVertex(vertices.get(0));
		System.out.println("vertex1="+vertex1);
		String vertex2 = this.graph.addVertex(vertices.get(0));
		System.out.println("vertex2="+vertex2);
		assertEquals("return value when adding duplicate vertex", null, vertex2);
		
		int verticesCount = 0;
		for (String itrVertex : this.graph.getAllVertices())
			verticesCount++;
		assertEquals("number of vertices in the graph", 1, verticesCount);
	}

	@Test
	public final void addEdgeIfAnyOfTheVerticesDoesNotExist() {
		// setup the graph by adding few vertices
		int breakIndex = 6;
		List<String> toBeAddedVertices = new ArrayList<>(vertices.subList(0, breakIndex));
		List<String> notToBeAddedVertices = new ArrayList<>(vertices.subList(breakIndex, breakIndex+2));
		for (String itrVertex : toBeAddedVertices)
			this.graph.addVertex(itrVertex);
		
		assertEquals("return value if vertex1 doesn't exist", 
				false, this.graph.addEdge(notToBeAddedVertices.get(0), toBeAddedVertices.get(0)));		
		assertEquals("return value if vertex2 doesn't exist", 
				false, this.graph.addEdge(toBeAddedVertices.get(0), notToBeAddedVertices.get(0)));
		assertEquals("return value if both vertex1 and vertex2 doesn't exist", 
				false, this.graph.addEdge(notToBeAddedVertices.get(0), notToBeAddedVertices.get(1)));
		
		// check if any edge added
		int verticesCount = 0;
		for (String itrVertex : this.graph.getAllVertices()) {
			verticesCount++;
			int numOfEdges = 0;
			for (String itrNeighbor : this.graph.getNeighbors(itrVertex)) {
				numOfEdges++;
			}
			assertEquals(String.format("number of neighbors for vertex=%s", itrVertex), 0, numOfEdges);
		}
		assertEquals("number of vertices in the graph", breakIndex, verticesCount);
	}
	
	@Test
	public final void addEdgeIfBothVerticesExist() {
		// setup the graph by adding few vertices
		for (String itrVertex : vertices)
			this.graph.addVertex(itrVertex);
		
		assertEquals("return value if vertex1 equals vertex2", 
				false, this.graph.addEdge(vertices.get(0), vertices.get(0)));
		assertEquals("return value if vertex1 does not equal vertex2", 
				true, this.graph.addEdge(vertices.get(0), vertices.get(1)));
		
		// check if correct edges added
		int verticesCount = 0;
		for (String itrVertex : this.graph.getAllVertices()) {
			verticesCount++;
			int numOfEdges = 0;
			for (String itrNeighbor : this.graph.getNeighbors(itrVertex)) {
				numOfEdges++;
				if (itrVertex.equals(vertices.get(0)))
					assertEquals(String.format("neighbor of vertex=%s", itrVertex), vertices.get(1), itrNeighbor);
				else if (itrVertex.equals(vertices.get(1)))
					assertEquals(String.format("neighbor of vertex=%s", itrVertex), vertices.get(0), itrNeighbor);
			}
			if (itrVertex.equals(vertices.get(0)) || itrVertex.equals(vertices.get(1)))
				assertEquals(String.format("number of neighbors for vertex=%s", itrVertex), 1, numOfEdges);
			else
				assertEquals(String.format("number of neighbors for vertex=%s", itrVertex), 0, numOfEdges);
		}
		assertEquals("number of vertices in the graph", numOfVertices, verticesCount);
	}
	
	@Test
	public final void removeEdgeIfAnyOfTheVerticesDoesNotExist() {
		// setup the graph by adding few vertices and edges
		int breakIndex = 6;
		List<String> toBeAddedVertices = new ArrayList<>(vertices.subList(0, breakIndex));
		List<String> notToBeAddedVertices = new ArrayList<>(vertices.subList(breakIndex, breakIndex+2));
		for (String itrVertex : toBeAddedVertices)
			this.graph.addVertex(itrVertex);
		this.graph.addEdge(toBeAddedVertices.get(0), toBeAddedVertices.get(1));
		
		assertEquals("return value if vertex1 doesn't exist", 
				false, this.graph.removeEdge(notToBeAddedVertices.get(0), toBeAddedVertices.get(0)));		
		assertEquals("return value if vertex2 doesn't exist", 
				false, this.graph.removeEdge(toBeAddedVertices.get(0), notToBeAddedVertices.get(0)));
		assertEquals("return value if both vertex1 and vertex2 doesn't exist", 
				false, this.graph.removeEdge(notToBeAddedVertices.get(0), notToBeAddedVertices.get(1)));
		
		// check if any edge added
		int verticesCount = 0;
		for (String itrVertex : this.graph.getAllVertices()) {
			verticesCount++;
			int numOfEdges = 0;
			for (String itrNeighbor : this.graph.getNeighbors(itrVertex)) {
				numOfEdges++;
				if (itrVertex.equals(vertices.get(0)))
					assertEquals(String.format("neighbor of vertex=%s", itrVertex), vertices.get(1), itrNeighbor);
				else if (itrVertex.equals(vertices.get(1)))
					assertEquals(String.format("neighbor of vertex=%s", itrVertex), vertices.get(0), itrNeighbor);
			}
			if (itrVertex.equals(vertices.get(0)) || itrVertex.equals(vertices.get(1)))
				assertEquals(String.format("number of neighbors for vertex=%s", itrVertex), 1, numOfEdges);
			else
				assertEquals(String.format("number of neighbors for vertex=%s", itrVertex), 0, numOfEdges);
		}
		assertEquals("number of vertices in the graph", breakIndex, verticesCount);
	}
	
	@Test
	public final void removeEdgeIfBothVerticesExist() {
		// setup the graph by adding few vertices and edges
		for (String itrVertex : vertices)
			this.graph.addVertex(itrVertex);
		this.graph.addEdge(vertices.get(0), vertices.get(1));
		this.graph.addEdge(vertices.get(1), vertices.get(2));
		
		assertEquals("return value if vertex1 equals vertex2", 
				false, this.graph.removeEdge(vertices.get(0), vertices.get(0)));
		assertEquals("return value if vertex1 does not equal vertex2", 
				true, this.graph.removeEdge(vertices.get(0), vertices.get(1)));
		
		// check if correct edges added
		int verticesCount = 0;
		for (String itrVertex : this.graph.getAllVertices()) {
			verticesCount++;
			int numOfEdges = 0;
			for (String itrNeighbor : this.graph.getNeighbors(itrVertex)) {
				numOfEdges++;
				if (itrVertex.equals(vertices.get(1)))
					assertEquals(String.format("neighbor of vertex=%s", itrVertex), vertices.get(2), itrNeighbor);
				else if (itrVertex.equals(vertices.get(2)))
					assertEquals(String.format("neighbor of vertex=%s", itrVertex), vertices.get(1), itrNeighbor);
			}
			if (itrVertex.equals(vertices.get(1)) || itrVertex.equals(vertices.get(2)))
				assertEquals(String.format("number of neighbors for vertex=%s", itrVertex), 1, numOfEdges);
			else
				assertEquals(String.format("number of neighbors for vertex=%s", itrVertex), 0, numOfEdges);
		}
		assertEquals("number of vertices in the graph", numOfVertices, verticesCount);
	}
	
	@Test
	public final void isAdjacentIfAnyOfTheVerticesDoesNotExist() {
		// setup the graph by adding a few vertices and edges
		int breakIndex = 6;
		List<String> toBeAddedVertices = new ArrayList<>(vertices.subList(0, breakIndex));
		List<String> notToBeAddedVertices = new ArrayList<>(vertices.subList(breakIndex, breakIndex+2));
		for (String itrVertex : toBeAddedVertices)
			this.graph.addVertex(itrVertex);
		this.graph.addEdge(toBeAddedVertices.get(0), toBeAddedVertices.get(1));
		
		assertEquals("return value if vertex1 doesn't exist", 
				false, this.graph.isAdjacent(notToBeAddedVertices.get(0), toBeAddedVertices.get(0)));		
		assertEquals("return value if vertex2 doesn't exist", 
				false, this.graph.isAdjacent(toBeAddedVertices.get(0), notToBeAddedVertices.get(0)));
		assertEquals("return value if both vertex1 and vertex2 doesn't exist", 
				false, this.graph.isAdjacent(notToBeAddedVertices.get(0), notToBeAddedVertices.get(1)));
	}
	
	@Test
	public final void isAdjacentIfBothVerticesExist() {
		// setup the graph by adding a few vertices and edges
		for (String itrVertex : vertices)
			this.graph.addVertex(itrVertex);
		this.graph.addEdge(vertices.get(0), vertices.get(1));
		this.graph.addEdge(vertices.get(1), vertices.get(2));
		
		assertEquals("return value if vertex1 equals vertex2", 
				false, this.graph.isAdjacent(vertices.get(0), vertices.get(0)));
		assertEquals("return value if vertex1 does not equal vertex2, both are disconnected and vertex1 has a neighbor", 
				false, this.graph.isAdjacent(vertices.get(0), vertices.get(3)));
		assertEquals("return value if vertex1 does not equal vertex2, both are disconnected and vertex2 has a neighbor", 
				false, this.graph.isAdjacent(vertices.get(3), vertices.get(0)));
		assertEquals("return value if vertex1 does not equal vertex2, both are disconnected and both have a neighbor", 
				false, this.graph.isAdjacent(vertices.get(2), vertices.get(0)));
		assertEquals("return value if vertex1 does not equal vertex2 and both are connected", 
				true, this.graph.isAdjacent(vertices.get(0), vertices.get(1)));
	}
	
	@Test
	public final void removeVertexReturnsNull() {
		// setup the graph by adding a few vertices and edges
		int breakIndex = 6;
		List<String> toBeAddedVertices = new ArrayList<>(vertices.subList(0, breakIndex));
		List<String> notToBeAddedVertices = new ArrayList<>(vertices.subList(breakIndex, breakIndex+2));
		for (String itrVertex : toBeAddedVertices)
			this.graph.addVertex(itrVertex);
		this.graph.addEdge(toBeAddedVertices.get(0), toBeAddedVertices.get(1));
		
		String vertex = this.graph.removeVertex(null);
		assertEquals("return value if null passed to removeVertex", null, vertex);
		vertex = this.graph.removeVertex(notToBeAddedVertices.get(0));
		assertEquals("return value if vertex passed to removeVertex does not exist in graph", null, vertex);
		
		// check if graph affected
		int verticesCount = 0;
		for (String itrVertex : this.graph.getAllVertices()) {
			verticesCount++;
			int numOfEdges = 0;
			for (String itrNeighbor : this.graph.getNeighbors(itrVertex)) {
				numOfEdges++;
				if (itrVertex.equals(vertices.get(0)))
					assertEquals(String.format("neighbor of vertex=%s", itrVertex), vertices.get(1), itrNeighbor);
				else if (itrVertex.equals(vertices.get(1)))
					assertEquals(String.format("neighbor of vertex=%s", itrVertex), vertices.get(0), itrNeighbor);
			}
			if (itrVertex.equals(vertices.get(0)) || itrVertex.equals(vertices.get(1)))
				assertEquals(String.format("number of neighbors for vertex=%s", itrVertex), 1, numOfEdges);
			else
				assertEquals(String.format("number of neighbors for vertex=%s", itrVertex), 0, numOfEdges);
		}
		assertEquals("num of vertices in the graph", breakIndex, verticesCount);
	}
	
	@Test
	public final void removeVertexReturnsNonNull() {
		// setup the graph by adding a few vertices and edges
		for (String itrVertex : vertices)
			this.graph.addVertex(itrVertex);
		this.graph.addEdge(vertices.get(0), vertices.get(1));
		
		String vertex = this.graph.removeVertex(vertices.get(2));
		assertEquals("return value if vertex passed to removeVertex is disconnected", vertices.get(2), vertex);
		
		vertex = this.graph.removeVertex(vertices.get(0));
		assertEquals("return value if vertex passed to removeVertex is connected to another vertex", vertices.get(0), vertex);
		
		// check the graph
		int verticesCount = 0;
		for (String itrVertex : this.graph.getAllVertices()) {
			verticesCount++;
			int numOfEdges = 0;
			for (String itrNeighbor : this.graph.getNeighbors(itrVertex)) {
				numOfEdges++;
				assertEquals(String.format("presence of neighbor=%s of vertex=%s", itrNeighbor, itrVertex), 
						false, itrVertex.equals(vertices.get(0)) && itrNeighbor.equals(vertices.get(1)));
				assertEquals(String.format("presence of neighbor=%s of vertex=%s", itrNeighbor, itrVertex), 
						false, itrVertex.equals(vertices.get(1)) && itrNeighbor.equals(vertices.get(0)));
			}
			assertEquals(String.format("presence of vertex=%s", itrVertex), false, itrVertex.equals(vertices.get(0)));
			assertEquals(String.format("presence of vertex=%s", itrVertex), false, itrVertex.equals(vertices.get(2)));
		}
		assertEquals("num of vertices in the graph", numOfVertices-2, verticesCount);
	}

}
