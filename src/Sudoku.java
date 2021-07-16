
/*
 * http://oddest.nc.hcc.edu.tw/
 * http://sudokuspoiler.azurewebsites.net/Sudoku
 */

import java.util.List;
import java.util.ArrayList;

//import java.util.Comparator;

/*
 * Use backtracking
 * Too slow for 16x16 sudoku
 */

public class Sudoku {
	private List<int[]> empty = new ArrayList<int[]>();
	private int startNumber = 1;     // start from this
	private int sub_row_length;
	private int sub_col_length;
	
	private List<int[][]> solutionSets = new ArrayList<>();
	private int solutions = 0;
	
	public Sudoku (int n) {
		this.sub_row_length = (int) Math.sqrt(n);
		this.sub_col_length = (int) Math.sqrt(n);
		if (sub_row_length * sub_col_length != n) {
			throw new IllegalArgumentException("Error input");
		}
	}
	
	public Sudoku (int sub_row_length, int sub_col_length) {
		this.sub_row_length = sub_row_length;
		this.sub_col_length = sub_col_length;
	}
	
	public void solveSudoku(int[][] a) {
		initialize(a);
		solve(a, 0);
	}
	
	private void initialize(int[][] a) {
		if (!empty.isEmpty()) {
			empty.clear();
		}
		
		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < a[i].length; j++) {
				if (a[i][j] == -1) {
					List<Integer> remaining = findNumber(a, j, i, startNumber);
					
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
		
//		empty.sort(new Comparator<int[]>() {
//			public int compare(int[] o1, int[] o2) {
//				return o1.length - o2.length;
//			}
//		});
	}
	
	private void solve(int[][] a, int n) {
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
	public List<Integer> findNumber(int[][] a, int x, int y, int start) {
		int rectangleX = x / this.sub_row_length;
		int rectangleY = y / this.sub_col_length;
		
		boolean[] filled = new boolean[a.length + start];
		for (int i = 0; i < a.length; i++) {
			// Search row
			if (a[y][i] != -1) {
				filled[a[y][i]] = true;
			}
			// Search column
			if (a[i][x] != -1) {
				filled[a[i][x]] = true;
			}
		}
		
		// Search sub-region
		for (int j1 = 0; j1 < this.sub_col_length; j1++) {
			for (int j2 = 0; j2 < this.sub_row_length; j2++) {
				int xLabel = rectangleX * this.sub_row_length + j2;
				int yLabel = rectangleY * this.sub_col_length + j1;
				if (a[yLabel][xLabel] != -1) {
					filled[a[yLabel][xLabel]] = true;
				}
			}
		}
		
		List<Integer> remain = new ArrayList<>();
		for (int i = start; i < filled.length; i++) {
			if (!filled[i]) {
				remain.add(i);
			}
		}
		return remain;
	}
	
	public boolean checkTable(int[][] a, int x, int y, int value) {
		int squareX = x / this.sub_row_length;
		int squareY = y / this.sub_col_length;
		
		for (int i = 0; i < a.length; i++) {
			// Check row
			if (a[y][i] == value && i != x)
				return false;
			// Check column
			if (a[i][x] == value && i != y)
				return false;
		}
		
		// Check sub-region
		for (int j1 = 0; j1 < this.sub_col_length; j1++) {
			for (int j2 = 0; j2 < this.sub_row_length; j2++) {
				int xLabel = squareX * this.sub_row_length + j2;
				int yLabel = squareY * this.sub_col_length + j1;
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
		
		// Store the solution to solutionSets
		int[][] sol = new int[n.length][n[0].length];
		for (int i = 0; i < sol.length; i++) {
			for (int j = 0; j < sol[i].length; j++) {
				sol[i][j] = n[i][j];
			}
		}
		solutionSets.add(sol);
	}
	
	public List<int[][]> getSolutionSets() {
		return solutionSets;
	}
}
