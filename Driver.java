package sjsu.ocampo.cs146.project3;

public class Driver {
	public static void main(String[] args) {
		
		for (int size = 4; size <= 10; size++) {
			Maze m = new Maze(size); // maze object
			
			System.out.println("Maze Size: " + size);
			
			m.printMaze();
			
			System.out.println("BFS:"); // Solves and prints maze
			
			m.bfs(); // does BFS algorithm
			m.printBFS(); // prints time for visited cell
			m.printSolution(); // prints solution
			
			m.clear(); // clears maze variables to prepare for DFS call
			
			System.out.println("DFS");
			m.dfs(); // DFS algorithm
			m.printDFS(); // prints time for visited cell
			m.printSolution(); // prints solution
			
			System.out.println();
			
			if (size == 8) size++; // skips maze of size 9 as per direction
		}
	}
}