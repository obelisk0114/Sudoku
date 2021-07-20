import java.util.Scanner;

import java.util.List;
import java.util.Set;
import java.util.HashSet;

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
		System.out.println("\nUse dancing links (DLX).");
		SudokuDLX solver = new SudokuDLX(board, length, width);
		
		long preTime = System.currentTimeMillis();
		
		int solution = solver.solve(board);
		
		long aftTime = System.currentTimeMillis();
		long currentTime = aftTime - preTime;
		
		System.out.println("Total solutions : " + solution);
		System.out.println("Sudoku Run time : " + currentTime/1000. + " sec");
	}
	
	public static List<int[][]> naiveSolver(int[][] board, int length, int width) {
		System.out.println("\nUse naive sudoku solver.\n---------------------------");
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
	
	public static int decideDisableSolver(String s) {
		String processed = s.toLowerCase().replaceAll("_| |-", "");
		
		Set<String> disableDLXSets = new HashSet<>();
		Set<String> disableNaiveSets = new HashSet<>();
		
		disableDLXSets.add("dlx");
		disableDLXSets.add("dancinglinksx");
		disableDLXSets.add("dancinglinks");
		
		disableNaiveSets.add("naive");
		
		int result = -1;
		
		// disable DLX
		if (disableDLXSets.contains(processed)) {
			result = 0;
		}
		// disable Naive
		else if (disableNaiveSets.contains(processed)) {
			result = 1;
		}
		// others
		else {
			result = 2;
		}
		
		return result;
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
		String solutionPath = "";
		
		boolean keyboardReading = true;
		boolean enableNaiveSolver = true;
		boolean enableDLXSolver = true;
		
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
		// -d dlx : disable DLXSolver
		// sudoku.txt sudokuSolution.txt
		case 2:
			if (args[0].charAt(0) != '-') {
				board = tools.readTXT(args[0]);
				keyboardReading = false;
				
				solutionPath = args[1];
				break;
			}
			
		// -i sudoku.txt -o sudokuSolution.txt
		// -o sudokuSolution.txt -i sudoku.txt
		// -i sudoku.txt -d naive : disable NaiveSolver
		// -d dancinglinksx -i sudoku.txt : disable DLXSolver
		case 4:
			;
			
		// -i : input file
		// -o : output file
		// -d : disable solver
		case 6:
			String iArgument = "";
			String oArgument = "";
			String dArgument = "";
			
			for (int i = 0; i < args.length - 1; i++) {
				switch (args[i]) {
				case "-i":
					if (args[i + 1].charAt(0) != '-') {
						iArgument = args[i + 1];
					}
					i++;
					
					keyboardReading = false;
					break;
				case "-o":
					if (args[i + 1].charAt(0) != '-') {
						oArgument = args[i + 1];
					}
					i++;
					break;
				case "-d":
					if (args[i + 1].charAt(0) != '-') {
						dArgument = args[i + 1];
					}
					i++;
					break;
				default:
					String info = "Illegal arguments.\n-i input\n-o output\n-d disable";
					throw new IllegalArgumentException(info);
				}
			}
			
			if (!iArgument.isEmpty()) {
				board = tools.readTXT(iArgument);
				solutionPath = "sudokuSolution.txt";
			}
			else {
				board = readSudokuPuzzle(tools);
			}
			
			if (!oArgument.isEmpty()) {
				solutionPath = oArgument;
			}
			if (!dArgument.isEmpty()) {
				int decision = decideDisableSolver(dArgument);
				if (decision == 0) {
					enableDLXSolver = false;
				}
				else if (decision == 1) {
					enableNaiveSolver = false;
				}
				else {
					System.out.println("Illegal arguments. Disable naive solver");
					enableNaiveSolver = false;
				}
			}
			break;
		
		// default
		default:
			board = readSudokuPuzzle(tools);
		}
		
		int length = tools.getX_length();
		int width = tools.getY_length();
		
		if (length * width > 14) {
			enableNaiveSolver = false;
		}
		
		if (enableDLXSolver) {			
			dancingLinksXSolver(board, length, width);
		}
		
		if (enableNaiveSolver) {
			List<int[][]> solutionSets = naiveSolver(board, length, width);
			for (int[][] result : solutionSets) {
				if (!tools.validateSolution(result, length, width)) {
					System.out.println("\n\n\n !!! \n");
					System.out.println("This solution is incorrect. Check the solver");
					throw new RuntimeException("Wrong answer");
				}
			}

			if (!solutionPath.isEmpty()) {
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
