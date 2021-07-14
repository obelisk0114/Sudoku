
/*
 * http://oddest.nc.hcc.edu.tw/
 * http://sudokuspoiler.azurewebsites.net/Sudoku
 */

//import java.util.List;
import java.util.ArrayList;

import java.util.HashSet;
import java.util.Comparator;

/*
 * Use backtracking
 * Too slow for 16x16 sudoku
 */

public class Sudoku {
	private ArrayList<int[]> empty = new ArrayList<int[]>();
	private int solutions = 0;
	private int startNumber = 1;     // start from this
	private int squareEdge;
	
	public Sudoku (int n) {
		squareEdge = (int) Math.sqrt(n);
		if (squareEdge * squareEdge != n) {
			throw new IllegalArgumentException("Error input");
		}
	}
	
	public void initialize(int[][] a) {
		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < a[i].length; j++) {
				if (a[i][j] == -1) {
					HashSet<Integer> remaining = findNumber(a, j, i, startNumber);
					
					int[] tmp = new int[2 + remaining.size()];
					tmp[0] = j;
					tmp[1] = i;
					
					int index = 2;
					for (Integer element : remaining) {
						tmp[index] = element;
						index++;
					}
					empty.add(tmp);
				}
			}
		}
		
		empty.sort(new Comparator<int[]>() {
			public int compare(int[] o1, int[] o2) {
				return o1.length - o2.length;
			}
		});
	}
	
	public void solve(int[][] a, int n) {
		if (n == empty.size()) {
			printTable(a);
			solutions++;
			return;
		}
		
		for (int i = 2; i < empty.get(n).length; i++) {
			int x = empty.get(n)[0];
			int y = empty.get(n)[1];
			int value = empty.get(n)[i];
			if (checkTable(a, x, y, value)) {
				a[y][x] = value;
				solve(a, n + 1);
				a[y][x] = -1;
			}
		}
		
	}
	
	// find the possible numbers in this cell
	public HashSet<Integer> findNumber(int[][] a, int x, int y, int start) {
		int squareX = x / squareEdge;
		int squareY = y / squareEdge;
		
		HashSet<Integer> remain = new HashSet<Integer>();
		for (int i = start; i <= a.length + start - 1; i++) {
			remain.add(i);
		}
		for (int i = 0; i < a.length; i++) {
			// Search row
			if (a[y][i] != -1)
				remain.remove(a[y][i]);
			// Search column
			if (a[i][x] != -1)
				remain.remove(a[i][x]);
		}
		
		// Search square
		for (int j1 = 0; j1 < squareEdge; j1++) {
			for (int j2 = 0; j2 < squareEdge; j2++) {
				int xLabel = squareX * squareEdge + j2;
				int yLabel = squareY * squareEdge + j1;
				if (a[yLabel][xLabel] != -1)
					remain.remove(a[yLabel][xLabel]);
			}
		}
		return remain;
	}
	
	public boolean checkTable(int[][] a, int x, int y, int value) {
		int squareX = x / squareEdge;
		int squareY = y / squareEdge;
		for (int i = 0; i < a.length; i++) {
			// Check row
			if (a[y][i] == value && i != x)
				return false;
			// Check column
			if (a[i][x] == value && i != y)
				return false;
		}
		
		// Check square
		for (int j1 = 0; j1 < squareEdge; j1++) {
			for (int j2 = 0; j2 < squareEdge; j2++) {
				int xLabel = squareX * squareEdge + j2;
				int yLabel = squareY * squareEdge + j1;
				if ( a[yLabel][xLabel] == value && (xLabel != x || yLabel != y) )
					return false;
			}
		}
		return true;
	}
	
	public int getSolutionsNumber() {
		return solutions;
	}
	
	public void printTable(int[][] n) {
		for (int i = 0; i < n.length; i++) {
			for (int j = 0; j < n[i].length; j++) {
				System.out.print(n[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println();
	}
}
