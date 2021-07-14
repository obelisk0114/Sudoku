import java.util.Scanner;

import dlx.SudokuDLX;

public class SudokuSolver {
	public static int[][] readMatrixFromKeyboard(Scanner keyboard, String s) {
		System.out.println("Enter the size of " + s);
		int n = keyboard.nextInt();
		System.out.println("Enter the " + s);
		
		int[][] matrix = new int[n][n];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				matrix[i][j] = keyboard.nextInt();
			}
		}
		return matrix;
	}
	
	/**
	 * @param args
	 * 
	 * enter -1 if there is no number filled (empty).
	 */
	public static void main(String[] args) {	
		Scanner keyboard = new Scanner(System.in);
		Tools tools = new Tools(keyboard);
		
//		int[][] a = tools.readTXT("sudoku.txt");
		int[][] a = tools.keyboardStyle();
		
//		int[][] a = {{-1, -1, 14, 12, -1, -1, 7, 9, -1, 11, -1, -1, -1, -1, 13, -1},
//				{16, 1, -1, 15, -1, 11, 2, -1, -1, -1, -1, 12, -1, 10, 14, -1},
//				{-1, -1, 5, -1, -1, 16, -1, 3, 13, -1, -1, -1, 2, 6, -1, -1},
//				{6, -1, 13, 10, 14, 1, -1, 12, 7, -1, -1, 3, -1, -1, 11, -1},
//				{7, -1, 9, 14, 1, -1, -1, -1, -1, -1, -1, -1, 5, -1, 4, -1},
//				{-1, -1, -1, 2, 10, 3, -1, -1, 11, -1, -1, -1, -1, -1, -1, 6},
//				{1, -1, 4, -1, -1, 7, -1, 14, 6, -1, 10, -1, -1, -1, -1, 13},
//				{-1, -1, 8, 5, -1, 9, -1, -1, -1, 12, 15, -1, -1, 1, -1, -1},
//				{-1, -1, 2, -1, -1, 10, 14, -1, -1, -1, 13, -1, 7, 4, -1, -1},
//				{5, -1, -1, -1, -1, 13, -1, 16, 15, -1, 11, -1, -1, 3, -1, 1},
//				{13, -1, -1, -1, -1, -1, -1, 15, -1, -1, 12, 6, 14, -1, -1, -1},
//				{-1, 12, -1, 1, -1, -1, -1, -1, -1, -1, -1, 7, 10, 13, -1, 11},
//				{-1, 2, -1, -1, 6, -1, -1, 5, 10, -1, 3, 11, 13, 12, -1, 16},
//				{-1, -1, 15, 13, -1, -1, -1, 7, 8, -1, 14, -1, -1, 11, -1, -1},
//				{-1, 5, 10, -1, 16, -1, -1, -1, -1, 6, 1, -1, 15, -1, 3, 14},
//				{-1, 4, -1, -1, -1, -1, 8, -1, 9, 15, -1, -1, 1, 7, -1, -1}};
		
//		int[][] a = {{8, -1, -1, -1, -1, -1, -1, -1, -1},
//				{-1, -1, 3, 6, -1, -1, -1, -1, -1},
//				{-1, 7, -1, -1, 9, -1, 2, -1, -1},
//				{-1, 5, -1, -1, -1, 7, -1, -1, -1},
//				{-1, -1, -1, -1, 4, 5, 7, -1, -1},
//				{-1, -1, -1, 1, -1, -1, -1, 3, -1},
//				{-1, -1, 1, -1, -1, -1, -1, 6, 8},
//				{-1, -1, 8, 5, -1, -1, -1, 1, -1},
//				{-1, 9, -1, -1, -1, -1, 4, -1, -1}};
		
//		Sudoku sd = new Sudoku(a.length);
		
		int length = tools.getX_length();
		int width = tools.getY_length();
		SudokuDLX solver = new SudokuDLX(a, length, width);
		
		long preTime = System.currentTimeMillis();
		
//		sd.initialize(a);
//		sd.solve(a, 0);
//		int solution = sd.getSolutionsNumber();
		
		int solution = solver.solve(a);
		
		long aftTime = System.currentTimeMillis();
		long currentTime = aftTime - preTime;
		System.out.println("Total solutions : " + solution);
		System.out.println("Sudoku Run time : " + currentTime/1000. + " sec");
		
		boolean testResult = true;
		if (testResult) {
			System.out.println();
			int[][] result = readMatrixFromKeyboard(keyboard, "result");
			
			System.out.println();
			int[][] answer = readMatrixFromKeyboard(keyboard, "answer");
			
			System.out.println();
			System.out.println("Is it correct? " + tools.checkAnswer(result, answer));			
			
			System.out.println("\nValidate result ... ");
			System.out.println(tools.validateSolution(result, length, width));
		}
		
		
		keyboard.close();
	}

}
