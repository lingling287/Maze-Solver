package sjsu.ocampo.cs146.project3;

import java.util.ArrayList;
import java.util.Collections;

public class Cell implements Comparable<Cell>{

	Cell north, east, south, west; // Neighbors that cells are connected too
	int row,col; // rows and column values that the cell is in the maze
	
	Color color; // color for DFS and BFS algorithm
	
	int bfsDistance, dfsStart, dfsFinish; // timer counters for algorithms
	
	Cell parent; // parent of cell for BFS
	
	boolean path; // path to print solution
	
	// constructs cell with row and column variables
	public Cell(int row, int col) {
		this.row = row;
		this.col = col;
		
		color = Color.WHITE;
		
		path = false;
	}
	
	// checks to see if cell has no broken walls
	public boolean intact() {
		if (north != null) return false;
		if (east != null) return false;
		if (south != null) return false;
		if (west != null) return false;
		
		return true;
	}
	
	// returns a ordered list of rooms that are 
	// connected to the cell, based on DFS finish time
	public ArrayList<Cell> getNextRooms() {
		
		ArrayList<Cell> c = new ArrayList<>();
		if (north != null) c.add(north);
		if (east != null) c.add(east);
		if (south != null) c.add(south);
		if (west != null) c.add(west);

		Collections.sort(c);
		
		return c;
	}

	// compareTo function to sort list by DFS finish time
	public int compareTo(Cell that) {
		return that.dfsFinish - this.dfsFinish;
	}
}
