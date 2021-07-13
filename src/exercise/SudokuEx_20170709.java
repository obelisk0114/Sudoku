package exercise;

import java.util.Set;
import java.util.HashSet;
import java.util.Scanner;

/*
 * Use backtracking
 * Too slow for 16x16 sudoku
 */
public class SudokuEx_20170709 {
	// -1 denote empty
	public SudokuEx_20170709(int[][] table, int length, int width) {
		solve(table, 0, 0, length, width);
		//printAns(table);
	}
	
	public boolean solve(int[][] table, int x, int y, int length, int width) {
		if (y == table.length)
			return true;
		
		int nextRow = y;
		if (x == table[y].length - 1)
			nextRow = y + 1;
		
		int nextCol = x + 1;
		if (x == table[y].length - 1)
			nextCol = 0;
		
		if (table[y][x] != -1)
			return solve(table, nextCol, nextRow, length, width);
		
		for (int i = 1; i <= length * width; i++) {
			table[y][x] = i;
			
			if (valify(table, x, y, length, width) 
					&& solve(table, nextCol, nextRow, length, width)) {
				
				return true;
			}
		}
		
		table[y][x] = -1;
		return false;
	}
	
	public boolean valify(int[][] table, int x, int y, int length, int width) {
		Set<Integer> appear = new HashSet<Integer>(); 
		
		// Row
		for (int i = 0; i < table[y].length; i++) {
			if (table[y][i] != -1) {
				//if (table[y][i] == table[y][x] && i != x)
				if (!appear.add(table[y][i]))
					return false;				
			}
		}
		appear.clear();
		
		// Column
		for (int i = 0; i < table.length; i++) {
			if (table[i][x] != -1) {
				//if (table[i][x] == table[y][x] && i != y)
				if (!appear.add(table[i][x]))
					return false;				
			}
		}
		appear.clear();
		
		// Rectangle
		int xEnd = (x / length + 1) * length;
		int yEnd = (y / width + 1) * width;
		int xStart = xEnd - length; 
		int yStart = yEnd - width;
		for (int i = yStart; i < yEnd; i++) {
			for (int j = xStart; j < xEnd; j++) {
				if (table[i][j] != -1) {
					//if (table[i][j] == table[y][x] && (i != y && j != x))
					if (!appear.add(table[i][j]))
						return false;
				}
			}
		}
		
		return true;
	}
	
	public void printAns(int[][] table) {
		for (int i = 0; i < table.length; i++) {
			for (int j = 0; j < table[i].length; j++) {
				System.out.print(table[i][j] + " ");
			}
			System.out.println();
		}
	}
	
	public static void main(String[] args) {
		Scanner keyboard = new Scanner(System.in);
		
		System.out.println("Enter the length of sudoku rectangle");
		int length = keyboard.nextInt();
		System.out.println("Enter the width of sudoku rectangle");
		int width = keyboard.nextInt();
		System.out.println("Enter the size of sudoku rectangle");
		int n = keyboard.nextInt();
		
		System.out.println("Enter the sudoku");
		int[][] a = new int[n][n];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				a[i][j] = keyboard.nextInt();
			}
		}
		System.out.println();
		
		long preTime = System.currentTimeMillis();
		SudokuEx_20170709 sudoku20170709 = new SudokuEx_20170709(a, length, width);
		sudoku20170709.printAns(a);
		long aftTime = System.currentTimeMillis();
		long currentTime = aftTime - preTime;
		
		System.out.println("Sudoku Run time : " + currentTime/1000. + " sec");
		keyboard.close();
	}

}
