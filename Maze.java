package sjsu.ocampo.cs146.project3;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.Stack;

public class Maze {

	Cell[][] maze; // 2D array to represent maze
	int size; // size of maze
	int dfsTime; // variable to keep track of DFS timer
	boolean endOfMazeDFS; // boolean check to terminate DFS

	// construct a maze of size n
	public Maze(int size) {
		this.size = size;
		endOfMazeDFS = false;
		maze = new Cell[size][size];
		
		// initializes each element with a Cell object
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				maze[i][j] = new Cell(i, j);
			}
		}	
		createMaze(); // generates randomized maze
	}
	
	// constructs a maze with seed for testing
	public Maze(int size, int seed) {
		this.size = size;
		endOfMazeDFS = false;
		maze = new Cell[size][size];
		
		// initializes each element with a Cell object
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				maze[i][j] = new Cell(i, j);
			}
		}	
		createMazeSEED(seed); // generates randomized maze
	}

	// creates a perfect, randomized maze
	public void createMaze() {
		Stack<Cell> s = new Stack<>(); // stack for algorithm use
		int totalCells = size * size; // counter to visit all nodes
		Cell currentCell = maze[0][0]; // starting cell at start of maze
		int visitedCells = 1; // counter

		while (visitedCells < totalCells) {
			ArrayList<Cell> neighboors = getNeighboors(currentCell); // list of neighbors

			if (neighboors.size() > 0) { // if the list has elements, continue
				Random r = new Random(); // random object
				int i = r.nextInt(neighboors.size()); // picks a random neighbor
				Cell neighboor = neighboors.get(i); // gets the neighbor

				if (getNorth(currentCell) == neighboor) { // if its a north neighbor
					currentCell.north = neighboor; // connect the two nodes
					neighboor.south = currentCell; 
				}

				else if (getSouth(currentCell) == neighboor) {
					currentCell.south = neighboor; // same for north, east, south, west
					neighboor.north = currentCell;
				}

				else if (getEast(currentCell) == neighboor) {
					currentCell.east = neighboor;
					neighboor.west = currentCell;
				}

				else if (getWest(currentCell) == neighboor) {
					currentCell.west = neighboor;
					neighboor.east = currentCell;
				}

				s.push(currentCell); // push current cell into stack
				currentCell = neighboor; // set the current cell to neighbor
				visitedCells++; // update counter
			}

			else {
				currentCell = s.pop(); // all cells are connected, pop cell out of stack
			}
		}
	}
	
	// Create maze with seed for testing
		public void createMazeSEED(int seed) {
			Stack<Cell> s = new Stack<>(); // stack for algorithm use
			int totalCells = size * size; // counter to visit all nodes
			Cell currentCell = maze[0][0]; // starting cell at start of maze
			int visitedCells = 1; // counter

			while (visitedCells < totalCells) {
				ArrayList<Cell> neighboors = getNeighboors(currentCell); // list of neighbors

				if (neighboors.size() > 0) { // if the list has elements, continue
					Random r = new Random(seed); // random object
					int i = r.nextInt(neighboors.size()); // picks a random neighbor
					Cell neighboor = neighboors.get(i); // gets the neighbor

					if (getNorth(currentCell) == neighboor) { // if its a north neighbor
						currentCell.north = neighboor; // connect the two nodes
						neighboor.south = currentCell; 
					}

					else if (getSouth(currentCell) == neighboor) {
						currentCell.south = neighboor; // same for north, east, south, west
						neighboor.north = currentCell;
					}

					else if (getEast(currentCell) == neighboor) {
						currentCell.east = neighboor;
						neighboor.west = currentCell;
					}

					else if (getWest(currentCell) == neighboor) {
						currentCell.west = neighboor;
						neighboor.east = currentCell;
					}

					s.push(currentCell); // push current cell into stack
					currentCell = neighboor; // set the current cell to neighbor
					visitedCells++; // update counter
				}

				else {
					currentCell = s.pop(); // all cells are connected, pop cell out of stack
				}
			}
		}

	// BFS algorithm to solve maze
	public void bfs() {
		Queue<Cell> q = new LinkedList<Cell>(); // Queue for algorithm
		Cell start = maze[0][0]; // start the maze beginning
		q.add(start); // add to queue

		int time = 0; // timer

		start.color = Color.GREY; // set color to grey
		start.bfsDistance = time; // update cell timer
		start.parent = null; // update cell parent
		
	

		while (!q.isEmpty()) { //while not empty
			Cell u = q.poll(); // get a cell from the queue
			if (u == maze[size - 1][size - 1]) { // if its the end of maze, terminate
				q.clear();
				break;
			}
			ArrayList<Cell> list = u.getNextRooms(); // gets list of connected cells
			for (Cell c : list) { // for each neighbor, update color, timer, and parent
				if (c.color == Color.WHITE) {
					c.color = Color.GREY;
					c.bfsDistance = u.bfsDistance + 1;
					c.parent = u;
					q.add(c);
				}
			}
			u.color = Color.BLACK; // no more neighbors to be found
		}
		shortestPathBFS(); // calculate the shortest path
	}

	// DFS algorithm
	public void dfs() {
		dfsTime = 0; // initialize timer

		// for each element, if it has not been visited, visit cell
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (maze[i][j].color == Color.WHITE) {
					if (endOfMazeDFS == false)
						dfsVisit(maze[i][j]);
				}
			}
		}
		shortestPathDFS();
	}

	// DFS algorithm helper
	public void dfsVisit(Cell u) {
		u.color = Color.GREY; // visited
		u.dfsStart = dfsTime++; // update timer
		
		if (endOfMazeDFS == true) { // terminate if end of maze
			return;
		}

		else {
			ArrayList<Cell> list = u.getNextRooms(); // gets next room
			for (Cell c : list) {
				if (c == maze[size - 1][size - 1]) { // terminates recursion at end of maze
					endOfMazeDFS = true;
					dfsVisit(c);
				}

				else if (c.color == Color.WHITE) { // visit all neighbors of current cell
					dfsVisit(c);
				}
			}
			u.color = Color.GREY; // update color
			u.dfsFinish = dfsTime++; // update timer
		}
	}

	// computes shortest path for DFS algorithm
	public void shortestPathDFS() {
		Cell current = maze[size - 1][size - 1];

		maze[0][0].path = true;

		while (current != maze[0][0]) {
			ArrayList<Cell> list = current.getNextRooms();
			current.path = true;
			current = list.get(0);

		}
	}

	// computers shortest path for BFS algorithm
	public void shortestPathBFS() {
		Cell current = maze[size - 1][size - 1];

		maze[0][0].path = true;

		while (current != maze[0][0]) {
			current.path = true;
			current = current.parent;
		}
	}

	
	// prints generated maze
	public void printMaze() {
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				Cell cell = maze[i][j];

				if (i == 0 && j == 0)
					System.out.print("+ ");
				else if (cell.north != null) {
					System.out.print("+ ");
				} 
				else
					System.out.print("+-");
			}

			System.out.println("+");
			for (int j = 0; j < size; j++) {
				Cell cell = maze[i][j];

				if (cell.west != null) {
					System.out.print("  ");
				} 
				else
					System.out.print("| ");
			}
			System.out.println("|");
		}

		for (int j = 0; j < size; j++) {
			if (j == size - 1)
				System.out.print("+ ");
			else
				System.out.print("+-");
		}
		System.out.println("+");
	}

	// prints BFS timers in cell
	public void printBFS() {
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				Cell cell = maze[i][j];
				if (i == 0 && j == 0)
					System.out.print("+ ");
				else if (cell.north != null) {
					System.out.print("+ ");
				} 
				else
					System.out.print("+-");
			}

			System.out.println("+");
			for (int j = 0; j < size; j++) {
				Cell cell = maze[i][j];

				if (cell.color != Color.WHITE) {
					if (cell.west != null) {
						System.out.print(" " + cell.bfsDistance % 10);
					} 
					else
						System.out.print("|" + cell.bfsDistance % 10);
				}

				else {
					if (cell.west != null) {
						System.out.print("  ");
					}
					else
						System.out.print("| ");
				}
			}
			System.out.println("|");
		}

		for (int j = 0; j < size; j++) {
			if (j == size - 1)
				System.out.print("+ ");
			else
				System.out.print("+-");
		}
		System.out.println("+");
	}

	// prints DFS timers in cell
	public void printDFS() {
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				Cell cell = maze[i][j];
				if (i == 0 && j == 0)
					System.out.print("+ ");
				else if (cell.north != null) {
					System.out.print("+ ");
				} 
				else
					System.out.print("+-");
			}

			System.out.println("+");
			for (int j = 0; j < size; j++) {
				Cell cell = maze[i][j];

				if (cell.color != Color.WHITE) {
					if (cell.west != null) {
						System.out.print(" " + cell.dfsStart % 10);
					} 
					else
						System.out.print("|" + cell.dfsStart % 10);
				}

				else {
					if (cell.west != null) {
						System.out.print("  ");
					} 
					else
						System.out.print("| ");
				}
			}
			System.out.println("|");
		}

		for (int j = 0; j < size; j++) {
			if (j == size - 1)
				System.out.print("+ ");
			else
				System.out.print("+-");
		}
		System.out.println("+");
	}

	// prints the solution to the maze in # characters
	public void printSolution() {
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				Cell cell = maze[i][j];

				if (i == 0 && j == 0)
					System.out.print("+ ");
				else if (cell.north != null) {
					System.out.print("+ ");
				} 
				else
					System.out.print("+-");
			}

			System.out.println("+");
			for (int j = 0; j < size; j++) {
				Cell cell = maze[i][j];

				if (cell.west != null) {
					if (cell.path == false)
						System.out.print("  ");
					else
						System.out.print(" #");
				} 
				else {
					if (cell.path == false)
						System.out.print("| ");
					else
						System.out.print("|#");
				}
			}
			System.out.println("|");
		}

		for (int j = 0; j < size; j++) {
			if (j == size - 1)
				System.out.print("+ ");
			else
				System.out.print("+-");
		}
		System.out.println("+");
	}

	// returns a list of all neighbors of cell that are not connected
	public ArrayList<Cell> getNeighboors(Cell c) {
		ArrayList<Cell> neighboors = new ArrayList<>();

		Cell north = getNorth(c);
		Cell south = getSouth(c);
		Cell east = getEast(c);
		Cell west = getWest(c);

		if (north != null && north.intact())
			neighboors.add(north);
		if (east != null && east.intact())
			neighboors.add(east);
		if (south != null && south.intact())
			neighboors.add(south);
		if (west != null && west.intact())
			neighboors.add(west);

		return neighboors;
	}

	// north
	public Cell getNorth(Cell c) {
		try {
			return maze[c.row - 1][c.col];
		} 
		catch (ArrayIndexOutOfBoundsException e) {
			return null;
		}
	}

	// gets the cell that is east of c
	public Cell getEast(Cell c) {
		try {
			return maze[c.row][c.col + 1];
		} 
		catch (ArrayIndexOutOfBoundsException e) {
			return null;
		}
	}

	// south
	public Cell getSouth(Cell c) {
		try {
			return maze[c.row + 1][c.col];
		} 
		catch (ArrayIndexOutOfBoundsException e) {
			return null;
		}
	}

	// west
	public Cell getWest(Cell c) {
		try {
			return maze[c.row][c.col - 1];
		} 
		catch (ArrayIndexOutOfBoundsException e) {
			return null;
		}
	}

	// clears cell variable to call different solver method on save maze
	public void clear() {	
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size ; j++) {
				Cell c = maze[i][j];
			
				c.color = Color.WHITE;				
				c.path = false;
			}
		}	
	}
	
	public void printWideMaze() {
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				Cell cell = maze[i][j];

				if (i == 0 && j == 0)
					System.out.print("+  ");
				else if (cell.north != null) {
					System.out.print("+  ");
				} 
				else
					System.out.print("+--");
			}

			System.out.println("+");
			for (int j = 0; j < size; j++) {
				Cell cell = maze[i][j];

				if (cell.west != null) {
					System.out.print("   ");
				} 
				else
					System.out.print("|  ");
			}
			System.out.println("|");
		}

		for (int j = 0; j < size; j++) {
			if (j == size - 1)
				System.out.print("+  ");
			else
				System.out.print("+--");
		}
		System.out.println("+");
	}
	
	public void printWideSolution() {
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				Cell cell = maze[i][j];

				if (i == 0 && j == 0)
					System.out.print("+  ");
				else if (cell.north != null) {
					System.out.print("+  ");
				} 
				else
					System.out.print("+--");
			}

			System.out.println("+");
			for (int j = 0; j < size; j++) {
				Cell cell = maze[i][j];

				if (cell.west != null) {
					if (cell.path == false)
						System.out.print("   ");
					else
						System.out.print(" # ");
				} 
				else {
					if (cell.path == false)
						System.out.print("|  ");
					else
						System.out.print("|# ");
				}
			}
			System.out.println("|");
		}

		for (int j = 0; j < size; j++) {
			if (j == size - 1)
				System.out.print("+  ");
			else
				System.out.print("+--");
		}
		System.out.println("+");
	}
	
	public static void main(String[] args) {
		Maze m = new Maze(10);
		m.printWideMaze();
		m.bfs();
		m.printBFS();
		}
}