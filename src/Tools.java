import java.util.Scanner;
import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

/*
 * http://stackoverflow.com/questions/5868369/how-to-read-a-large-text-file-line-by-line-using-java
 * http://stackoverflow.com/questions/12350248/java-difference-between-filewriter-and-bufferedwriter
 * http://stackoverflow.com/questions/908168/when-to-flush-a-bufferedwriter
 * http://stackoverflow.com/questions/20523247/java-file-exists-versus-file-isfile
 * http://www.cnblogs.com/0201zcr/p/5009975.html
 * http://www.cnblogs.com/qianbi/p/3378466.html
 * http://yhhuang1966.blogspot.com/2014/03/java_3503.html
 * http://stackoverflow.com/questions/10043209/convert-arraylist-into-2d-array-containing-varying-lengths-of-arrays
 */

public class Tools {
	private Scanner keyboard;
	private int x_length = -1;
	private int y_length = -1;
	
	public Tools() {
		this(new Scanner(System.in));
	}
	
	public Tools(Scanner keyboard) {
		this.keyboard = keyboard;
	}
	
	// Use keyboard to enter the sudoku problem
	public int[][] keyboardStyle() {
		System.out.println("Enter the x length of sudoku subsection");
		x_length = keyboard.nextInt();
		System.out.println("Enter the y length of sudoku subsection");
		y_length = keyboard.nextInt();
		System.out.println("Enter the sudoku");
		
		int n = x_length * y_length;
		int[][] matrix = new int[n][n];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				matrix[i][j] = keyboard.nextInt();
			}
		}
		return matrix;
	}
	
	// Read sudoku problem
	public int[][] readTXT(String s) {
		try {
			File file = new File(s);
			if (file.isFile() && file.exists()) {
				FileReader fr = new FileReader(file);
				BufferedReader reader = new BufferedReader(fr);
				String line;
				ArrayList<List<Integer>> tempA = new ArrayList<List<Integer>>(); 
				while ((line = reader.readLine()) != null) {
					line = line.replaceAll(", ", " ");
					String[] item = line.split(",| ");
					int n = item.length;
					
					switch (n) {
					case 1:
						if (x_length == -1)
							x_length = Integer.parseInt(item[0]);
						else if (y_length == -1)
							y_length = Integer.parseInt(item[0]);
						else {
							throw new IllegalArgumentException("Error: Invalid sudoku.");
						}
						break;
					case 2:
						if (x_length != -1 || y_length != -1)
							throw new IllegalArgumentException("Error: Invalid sudoku.");
						x_length = Integer.parseInt(item[0]);
						y_length = Integer.parseInt(item[1]);
						break;
					default:
						ArrayList<Integer> tmpRow = new ArrayList<Integer>(n);
						for (int i = 0; i < n; i++) {
							if (item[i].equals(".") || item[i].equals("-")) {
								tmpRow.add(-1);
							}
							else {
								int number = Integer.parseInt(item[i]);
								tmpRow.add(number);
							}
						}
						tempA.add(tmpRow);
					}
				}
				
				int[][] puzzle = new int[tempA.size()][tempA.get(0).size()];
				for (int i = 0; i < tempA.size(); i++) {
					for (int j = 0; j < tempA.get(i).size(); j++) {
						puzzle[i][j] = tempA.get(i).get(j);
					}
				}
				fr.close();
				reader.close();
				
				if (x_length == -1)
					x_length = (int) (Math.sqrt(puzzle.length));
				if (y_length == -1)
					y_length = (int) (Math.sqrt(puzzle.length));
				return puzzle;
			}
			else {
				throw new IllegalArgumentException("File does not exist.");
			}
		}
		catch (IOException e) {
            System.out.println("Reading error");
            e.printStackTrace();
        }
		
		// Exceptions
		return new int[0][0];
	}
	
	// Generate solution files
	public void outputTXT(int[][] table) {
		String name = "sudokuSolution.txt";
		outputTXT(table, name);
	}
	
	// Generate solution files
	public void outputTXT(List<int[][]> tables) {
		String name = "sudokuSolution.txt";
		outputTXT(tables, name);
	}

	// Generate solution files
	public void outputTXT(int[][] table, String fileName) {
		List<int[][]> tables = new ArrayList<>();
		tables.add(table);
		
		outputTXT(tables, fileName);
	}

	// Generate solution files
	public void outputTXT(List<int[][]> tables, String fileName) {
		try {
			File file = new File(fileName);
			if (file.isFile() && file.exists()) {
				System.out.println("Already exist a file with same name");
				throw new IllegalArgumentException("Already exist");
			}
			
			FileWriter fw = new FileWriter(file);
			BufferedWriter bw = new BufferedWriter(fw);
			
			for (int[][] table : tables) {				
				for (int i = 0; i < table.length; i++) {
					for (int j = 0; j < table[i].length; j++) {
						bw.write(table[i][j] + " ");
					}
					bw.newLine();
				}
				
				bw.flush();
				
				bw.newLine();
				bw.newLine();
			}
			bw.close();
			fw.close();
		}
		catch (Exception e) {
            System.out.println("Writing error");
            e.printStackTrace();
        }
	}
	
	public boolean checkAnswer(int[][] result, int[][] answer) {
		if (result.length != answer.length)
			return false;
		
		for (int i = 0; i < answer.length; i++) {
			if (result[i].length != answer[i].length)
				return false;
			
			for (int j = 0; j < answer[i].length; j++) {
				if (result[i][j] != answer[i][j])
					return false;
			}
		}
		return true;
	}
	
	public boolean validateSolution(int[][] grid, int sub_x_size, int sub_y_size) {
		int N = grid.length;
		boolean[] b = new boolean[N];
		
		int min = Integer.MAX_VALUE;
		for (int i : grid[0]) {
			min = Math.min(min, i);
		}
		
		// Check row
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < grid[i].length; j++) {
				int mapping = grid[i][j] - min;
				
				// outside the range
				if (mapping < 0 || mapping >= N)
					return false;
				
				// duplicate
				if (b[mapping])
					return false;

				// Mark as visited
				b[mapping] = true;
			}
			Arrays.fill(b, false);
		}
		
		// Check column
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < grid[i].length; j++) {
				int mapping = grid[j][i] - min;
				
				// outside the range
				if (mapping < 0 || mapping >= N)
					return false;
				
				// duplicate
				if (b[mapping])
					return false;

				// Mark as visited
				b[mapping] = true;
			}
			Arrays.fill(b, false);
		}
		
		// Check sub-region
		for (int i = 0; i < N; i += sub_y_size) {
			for (int j = 0; j < grid[i].length; j += sub_x_size) {
				
				for (int d1 = 0; d1 < sub_y_size; d1++) {
					for (int d2 = 0; d2 < sub_x_size; d2++) {
						int mapping = grid[i + d1][j + d2] - min;
						
						// outside the range
						if (mapping < 0 || mapping >= N)
							return false;
						
						// duplicate
						if (b[mapping])
							return false;

						// Mark as visited
						b[mapping] = true;
					}
				}
				
				Arrays.fill(b, false);
			}
		}
		return true;
	}
	
	public void setX_length(int x_length) {
		this.x_length = x_length;
	}
	
	public void setY_length(int y_length) {
		this.y_length = y_length;
	}
	
	public int getX_length() {
		return this.x_length;
	}
	
	public int getY_length() {
		return this.y_length;
	}

}
