import java.util.Scanner;

import java.util.List;

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
	
	public static void dancingLinksXSolver(int[][] board, int length, int width) {
		SudokuDLX solver = new SudokuDLX(board, length, width);
		
		long preTime = System.currentTimeMillis();
		
		int solution = solver.solve(board);
		
		long aftTime = System.currentTimeMillis();
		long currentTime = aftTime - preTime;
		
		System.out.println("Total solutions : " + solution);
		System.out.println("Sudoku Run time : " + currentTime/1000. + " sec");
	}
	
	public static List<int[][]> naiveSolver(int[][] board, int length, int width) {
		Sudoku sd = new Sudoku(length, width);

		long preTime = System.currentTimeMillis();

		sd.solveSudoku(board);
		int solution = sd.getSolutionsNumber();

		long aftTime = System.currentTimeMillis();
		long currentTime = aftTime - preTime;
		
		System.out.println("Total solutions : " + solution);
		System.out.println("Sudoku Run time : " + currentTime/1000. + " sec");
		
		return sd.getSolutionSets();
	}
	
	public static int[][] readSudokuPuzzle(Tools tools) {
//		int[][] board = tools.readTXT("sudoku.txt");
		int[][] board = tools.keyboardStyle();
		
		// hard coded test cases
		/*
		int[][] board = {{-1, -1, 14, 12, -1, -1, 7, 9, -1, 11, -1, -1, -1, -1, 13, -1},
				{16, 1, -1, 15, -1, 11, 2, -1, -1, -1, -1, 12, -1, 10, 14, -1},
				{-1, -1, 5, -1, -1, 16, -1, 3, 13, -1, -1, -1, 2, 6, -1, -1},
				{6, -1, 13, 10, 14, 1, -1, 12, 7, -1, -1, 3, -1, -1, 11, -1},
				{7, -1, 9, 14, 1, -1, -1, -1, -1, -1, -1, -1, 5, -1, 4, -1},
				{-1, -1, -1, 2, 10, 3, -1, -1, 11, -1, -1, -1, -1, -1, -1, 6},
				{1, -1, 4, -1, -1, 7, -1, 14, 6, -1, 10, -1, -1, -1, -1, 13},
				{-1, -1, 8, 5, -1, 9, -1, -1, -1, 12, 15, -1, -1, 1, -1, -1},
				{-1, -1, 2, -1, -1, 10, 14, -1, -1, -1, 13, -1, 7, 4, -1, -1},
				{5, -1, -1, -1, -1, 13, -1, 16, 15, -1, 11, -1, -1, 3, -1, 1},
				{13, -1, -1, -1, -1, -1, -1, 15, -1, -1, 12, 6, 14, -1, -1, -1},
				{-1, 12, -1, 1, -1, -1, -1, -1, -1, -1, -1, 7, 10, 13, -1, 11},
				{-1, 2, -1, -1, 6, -1, -1, 5, 10, -1, 3, 11, 13, 12, -1, 16},
				{-1, -1, 15, 13, -1, -1, -1, 7, 8, -1, 14, -1, -1, 11, -1, -1},
				{-1, 5, 10, -1, 16, -1, -1, -1, -1, 6, 1, -1, 15, -1, 3, 14},
				{-1, 4, -1, -1, -1, -1, 8, -1, 9, 15, -1, -1, 1, 7, -1, -1}};
		
		int[][] board = {{8, -1, -1, -1, -1, -1, -1, -1, -1},
				{-1, -1, 3, 6, -1, -1, -1, -1, -1},
				{-1, 7, -1, -1, 9, -1, 2, -1, -1},
				{-1, 5, -1, -1, -1, 7, -1, -1, -1},
				{-1, -1, -1, -1, 4, 5, 7, -1, -1},
				{-1, -1, -1, 1, -1, -1, -1, 3, -1},
				{-1, -1, 1, -1, -1, -1, -1, 6, 8},
				{-1, -1, 8, 5, -1, -1, -1, 1, -1},
				{-1, 9, -1, -1, -1, -1, 4, -1, -1}};
		*/
		
		return board;
	}
	
	/**
	 * @param args
	 * 
	 * enter -1 if there is no number filled (empty).
	 */
	public static void main(String[] args) {	
		Scanner keyboard = new Scanner(System.in);
		Tools tools = new Tools(keyboard);
		
		int[][] board;
		boolean generateSolutionFile = false;
		String solutionPath = "";
		boolean keyboardReading = true;
		
		switch (args.length) {
		case 0:
			board = readSudokuPuzzle(tools);
			break;
			
		// sudoku.txt
		case 1:
			board = tools.readTXT(args[0]);
			keyboardReading = false;
			break;
			
		// -i sudoku.txt : generate solution files
		// sudoku.txt sudokuSolution.txt
		case 2:
			if (args[0].charAt(0) == '-') {
				if (args[0].length() == 2 && args[0].charAt(1) == 'i') {
					board = tools.readTXT(args[1]);
					keyboardReading = false;
					
					generateSolutionFile = true;
				}
				else {
					System.out.println("Illegal arguments. Use keyboard reading");
					board = readSudokuPuzzle(tools);
				}
			}
			else {
				board = tools.readTXT(args[0]);
				keyboardReading = false;
				
				solutionPath = args[1];
				generateSolutionFile = true;
			}
			break;
			
		// -i sudoku.txt -o sudokuSolution.txt
		// -o sudokuSolution.txt -i sudoku.txt
		case 4:
			if (args[0].equals("-i") && args[2].equals("-o")) {
				board = tools.readTXT(args[1]);
				keyboardReading = false;
				
				solutionPath = args[3];
				generateSolutionFile = true;
			}
			else if (args[0].equals("-o") && args[2].equals("-i")) {
				board = tools.readTXT(args[3]);
				keyboardReading = false;
				
				solutionPath = args[1];
				generateSolutionFile = true;
			}
			else {
				System.out.println("Illegal arguments. Use keyboard reading");
				board = readSudokuPuzzle(tools);
			}
			break;
			
		// default
		default:
			board = readSudokuPuzzle(tools);
		}
		
		int length = tools.getX_length();
		int width = tools.getY_length();
		
		// dancingLinksXSolver(board, length, width);
		List<int[][]> solutionSets = naiveSolver(board, length, width);
		for (int[][] result : solutionSets) {
			if (!tools.validateSolution(result, length, width)) {
				System.out.println("\n\n\n !!! \n");
				System.out.println("This solution is incorrect. Check the solver");
				throw new RuntimeException("Wrong answer");
			}
		}
		
		if (generateSolutionFile) {
			if (solutionPath.isEmpty()) {
				tools.outputTXT(solutionSets);
			}
			else {
				tools.outputTXT(solutionSets, solutionPath);
			}
		}
		
		boolean testResult = keyboardReading;
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
