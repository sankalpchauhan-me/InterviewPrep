package maze;

import java.util.ArrayDeque;
import java.util.Queue;

//https://www.techiedelight.com/find-shortest-safe-route-field-sensors-present/
public class CrossFieldWithSensors {

	// queue node used in BFS
	static class Node {
		// (x, y) represents a position inside field
		// dist represent its minimum distance from the source
		int x, y, dist;

		Node(int x, int y, int dist) {
			this.x = x;
			this.y = y;
			this.dist = dist;
		}
	};

	// M x N field
	private static final int M = 10;
	private static final int N = 10;

	// Below arrays details all 4 possible movements from a cell
	// i.e. (top, right, bottom, left)
	private static final int row[] = { -1, 0, 0, 1 };
	private static final int col[] = { 0, -1, 1, 0 };

	// Function to check if it is safe to go to position (x, y)
	// from current position. The function returns false if (x, y)
	// is unsafe or it is already visited
	private static boolean isSafe(int[][] field, boolean visited[][], int x, int y) {
		return (field[x][y] == 1 && !visited[x][y]);
	}

	// Check if (x, y) is valid field coordinates
	// Note that we cannot go out of the field
	private static boolean isValid(int x, int y) {
		return (x < M && y < N && x >= 0 && y >= 0);
	}

	// Find minimum number of steps required to reach last column
	// from first column using BFS
	private static int BFS(int[][] field) {
		// stores if cell is visited or not
		boolean[][] visited = new boolean[M][N];

		// create an empty queue
		Queue<Node> q = new ArrayDeque<>();

		// enqueue all sources
		// process every cell of first column
		for (int r = 0; r < M; r++) {
			// if the cell is safe, mark it as visited and
			// enqueue it by assigning it distance as 0
			if (field[r][0] == 1) {
				q.add(new Node(r, 0, 0));
				visited[r][0] = true;
			}
		}

		// run till queue is not empty
		while (!q.isEmpty()) {
			// pop front node from queue and process it
			int i = q.peek().x;
			int j = q.peek().y;
			int dist = q.peek().dist;
			q.poll();

			// if destination is found, return minimum distance
			if (j == N - 1) {
				return dist;
			}

			// check for all 4 possible movements from current cell
			// and enqueue each valid movement
			for (int k = 0; k < 4; k++) {
				// Skip if location is invalid or visited or unsafe
				if (isValid(i + row[k], j + col[k]) && isSafe(field, visited, i + row[k], j + col[k])) {
					// mark it visited & push it into queue with +1 distance
					visited[i + row[k]][j + col[k]] = true;
					q.add(new Node(i + row[k], j + col[k], dist + 1));
				}
			}
		}
		return Integer.MAX_VALUE;
	}

	// Find Shortest Path from first column to last column in given field
	public static int shortestDistance(int[][] field) {
		// r[] and c[] details all 8 possible movements from a cell
		// (topleft, top, topRight, left,right,bottomLeft,bottom,bottomRight)
		int r[] = { -1, -1, -1, 0, 0, 1, 1, 1 };
		int c[] = { -1, 0, 1, -1, 1, -1, 0, 1 };

		// mark adjacent cells of sensors as unsafe
		for (int i = 0; i < M; i++) {
			for (int j = 0; j < N; j++) {
				// look for 8 possible movements from each cell,if the cell is a sensor mark
				// those 8 adjacent cells as unsafe
				for (int k = 0; k < 8; k++) {
					if (field[i][j] == 0 && isValid(i + r[k], j + c[k]) && field[i + r[k]][j + c[k]] == 1) {
						field[i + r[k]][j + c[k]] = Integer.MAX_VALUE;
					}
				}
			}
		}

		// update the field
		for (int i = 0; i < M; i++) {
			for (int j = 0; j < N; j++) {
				if (field[i][j] == Integer.MAX_VALUE) {
					field[i][j] = 0;
				}
			}
		}

		// call BFS and return shortest distance found by it
		return BFS(field);
	}

	// main function
	public static void main(String[] args) {
		int[][] field = { { 0, 1, 1, 1, 0, 1, 1, 1, 1, 1 }, { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
				{ 1, 1, 1, 1, 1, 1, 1, 1, 0, 1 }, { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 }, { 1, 1, 1, 1, 1, 0, 1, 1, 1, 1 },
				{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 }, { 1, 0, 1, 1, 1, 1, 1, 1, 1, 1 }, { 1, 1, 1, 1, 1, 1, 1, 1, 1, 0 },
				{ 1, 1, 1, 1, 1, 0, 1, 1, 1, 1 }, { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 } };

		int dist = shortestDistance(field);

		if (dist != Integer.MAX_VALUE) {
			System.out.print("The shortest safe path has length of " + dist);
		} else {
			System.out.print("No route is safe to reach destination");
		}
	}
}
