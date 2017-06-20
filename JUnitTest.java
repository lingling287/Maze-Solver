package sjsu.ocampo.cs146.project3;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;


public class JUnitTest {

	Maze m; // maze object
	
	@Before
	public void setUp() {
		m = new Maze(3,0); // maze of size 3 with seed of 0
	}
	
	// test the get neighbors method
	@Test
	public void testNeighboors() {
		assertEquals(m.getNorth(m.maze[0][0]), null);
		assertEquals(m.getEast(m.maze[0][0]), m.maze[0][1]);
		assertEquals(m.getSouth(m.maze[0][0]), m.maze[1][0]);
		assertEquals(m.getWest(m.maze[0][0]), null);
	}
	
	// test the get connect rooms method of cell
	@Test
	public void testCell() {	
		ArrayList<Cell> test = m.maze[0][0].getNextRooms();
		
		assertEquals(test.get(0), m.maze[1][0]);		
	}
	
	// test to check if cell is intact or not
	@Test
	public void testIntact() {		
		Cell cell = new Cell(0, 0);
		
		assertFalse(m.maze[1][1].intact());
		assertTrue(cell.intact());
	}
}