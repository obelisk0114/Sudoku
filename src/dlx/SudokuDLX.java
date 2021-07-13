package dlx;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

public class SudokuDLX {
	private int board_size;
	private int sub_x_size;
	private int sub_y_size;
	
	private final int empty = -1; // 0
    private final int CONSTRAINTS = 4;
    private final int MIN_VALUE = 1;
    
    private int solutions = 0;
	
	public SudokuDLX(int[][] sudoku) {
		this(sudoku, (int) Math.sqrt(sudoku.length), (int) Math.sqrt(sudoku.length));
	}
	
	public SudokuDLX(int[][] sudoku, int sub_x_size, int sub_y_size) {
		board_size = sudoku.length;
		this.sub_x_size = sub_x_size;
		this.sub_y_size = sub_y_size;
	}
	
	// Checks whether `grid` represents a valid sudoku puzzle.
	// O's represent empty cells. 1...n represent numbers in the grid.
	protected boolean validateSudoku(int[][] grid, int sub_x_size, int sub_y_size) {
		if (sub_x_size * sub_y_size != grid.length)
			return false;
		
		for (int i = 0; i < grid.length; i++) {
			if (grid[i].length != grid.length)
				return false;

			for (int j = 0; j < grid[i].length; j++) {
				if (!(grid[i][j] == empty || 
						(grid[i][j] >= MIN_VALUE && grid[i][j] <= grid.length)))
					return false; // 0 means not filled in
			}
		}

		int N = grid.length;
		boolean[] b = new boolean[N + 1];

		// Check row
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (grid[i][j] == empty)
					continue;
				if (b[grid[i][j]])
					return false;
				
				// Mark as visited
				b[grid[i][j]] = true;
			}
			Arrays.fill(b, false);
		}

		// Check column
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (grid[j][i] == empty)
					continue;
				if (b[grid[j][i]])
					return false;
				
				// Mark as visited
				b[grid[j][i]] = true;
			}
			Arrays.fill(b, false);
		}

		// Check sub-region
		for (int i = 0; i < N; i += sub_y_size) {
			for (int j = 0; j < N; j += sub_x_size) {
				for (int d1 = 0; d1 < sub_y_size; d1++) {
					for (int d2 = 0; d2 < sub_x_size; d2++) {
						if (grid[i + d1][j + d2] == empty)
							continue;
						if (b[grid[i + d1][j + d2]])
							return false;
						
						// Mark as visited
						b[grid[i + d1][j + d2]] = true;
					}
				}
				Arrays.fill(b, false);
			}
		}
		return true;
	}

	// sudoku has numbers 1-9. A 0 indicates an empty cell that we will need to
    // fill in.
	private int[][] makeExactCoverGrid(int[][] sudoku) {
		int[][] R = sudokuExactCover();
		for (int i = 1; i <= board_size; i++) {
			for (int j = 1; j <= board_size; j++) {
				int n = sudoku[i - 1][j - 1];
				if (n != empty) { // zero out in the constraint board
					for (int num = MIN_VALUE; num <= board_size; num++) {
						if (num != n) {
							Arrays.fill(R[getIdx(i, j, num)], 0);
						}
					}
				}
			}
		}
		return R;
	}
	
	// Returns the base exact cover grid for a SUDOKU puzzle
	private int[][] sudokuExactCover() {
		int[][] R = new 
			int[board_size * board_size * board_size][board_size * board_size * CONSTRAINTS];

        int hBase = 0;

        // row-column constraints
		for (int r = 1; r <= board_size; r++) {
			for (int c = 1; c <= board_size; c++, hBase++) {
				for (int n = 1; n <= board_size; n++) {
					R[getIdx(r, c, n)][hBase] = 1;
				}
			}
		}

		// row-number constraints
		for (int r = 1; r <= board_size; r++) {
			for (int n = 1; n <= board_size; n++, hBase++) {
				for (int c1 = 1; c1 <= board_size; c1++) {
					R[getIdx(r, c1, n)][hBase] = 1;
				}
			}
		}

		// column-number constraints
		for (int c = 1; c <= board_size; c++) {
			for (int n = 1; n <= board_size; n++, hBase++) {
				for (int r1 = 1; r1 <= board_size; r1++) {
					R[getIdx(r1, c, n)][hBase] = 1;
				}
			}
		}

		// box-number constraints
		for (int br = 1; br <= board_size; br += sub_y_size) {
			for (int bc = 1; bc <= board_size; bc += sub_x_size) {
				for (int n = 1; n <= board_size; n++, hBase++) {
					for (int rDelta = 0; rDelta < sub_y_size; rDelta++) {
						for (int cDelta = 0; cDelta < sub_x_size; cDelta++) {
							R[getIdx(br + rDelta, bc + cDelta, n)][hBase] = 1;
						}
					}
				}
			}
		}

        return R;
    }
    
	// row [1,S], col [1,S], num [1,S]
	private int getIdx(int row, int col, int num) {
		return (row - 1) * board_size * board_size + (col - 1) * board_size + (num - 1);
	}
	
	// by myself
	int[][] makeExactCoverGridInEmpty(int[][] sudoku) {
		boolean[] filled = new boolean[board_size * board_size * CONSTRAINTS];
		List<List<Integer>> matrix = new ArrayList<>();
		for (int i = 0; i < sudoku.length; i++) {
			for (int j = 0; j < sudoku[i].length; j++) {
				if (sudoku[i][j] != empty) {
					List<Integer> list = new ArrayList<>();
					int[] columnIndice = this.getColumnIndice(i, j, sudoku[i][j]);
					int step = 0;
					
					for (int k = 0; k < filled.length; k++) {
						if (step < columnIndice.length && k == columnIndice[step]) {
							list.add(1);
							filled[k] = true;
							step++;
						}
						else {
							list.add(0);
						}
					}
					matrix.add(list);
				}
			}
		}
		
		for (int i = 0; i < sudoku.length; i++) {
			for (int j = 0; j < sudoku[i].length; j++) {
				if (sudoku[i][j] == empty) {
					for (int num = MIN_VALUE; num <= board_size; num++) {
						List<Integer> list = new ArrayList<>();
						int[] columnIndice = this.getColumnIndice(i, j, num);
						int step = 0;
						
						for (int k = 0; k < filled.length; k++) {
							if (step < columnIndice.length && k == columnIndice[step]) {
								if (filled[k]) {
									break;
								}
								
								list.add(1);
								step++;
							} 
							else {
								list.add(0);
							}
						}
						
						if (list.size() == filled.length)
							matrix.add(list);
					}
				}
			}
		}
		
		int[][] exactCoverMatrix = new int[matrix.size()][filled.length];
		for (int i = 0; i < matrix.size(); i++) {
			for (int j = 0; j < matrix.get(i).size(); j++) {
				exactCoverMatrix[i][j] = matrix.get(i).get(j);
			}
		}
		System.out.println("Matrix size = " + matrix.size());
		return exactCoverMatrix;
	}
	
	// by myself
	private int[] getColumnIndice(int row, int col, int num) {
		int[] columnIndice = new int[4];
		// row-column constraints
		columnIndice[0] = row * board_size + col;
		
		// row-number constraints
		columnIndice[1] = board_size * board_size + row * board_size + (num - 1);
		
		// column-number constraints
		columnIndice[2] = 2 * board_size * board_size + col * board_size + (num - 1);
		
		// box-number constraints
		int sub_r = row / sub_y_size;
		int sub_c = col / sub_x_size;
		int b = sub_r * sub_y_size + sub_c;
		columnIndice[3] = 3 * board_size * board_size + b * board_size + (num - 1);
		
		return columnIndice;
	}

	// starts printing ALL valid sudokus (empty matrix).
	protected void generateAllSolutions() {
		int[][] cover = sudokuExactCover();
		DancingLinks dlx = new DancingLinks(cover, new SudokuHandler(board_size));
		dlx.runSolver();
	}

	protected void runSolver(int[][] sudoku) {
		int[][] cover = makeExactCoverGrid(sudoku);
		//int[][] cover = makeExactCoverGridInEmpty(sudoku);
		DancingLinks dlx = new DancingLinks(cover, new SudokuHandler(board_size));
		dlx.runSolver();
		
		this.solutions = dlx.getSolutionsNumber();
	}
	
	public int solve(int[][] sudoku) {
		if (!validateSudoku(sudoku, sub_x_size, sub_y_size)) {
			throw new IllegalArgumentException("Error: Invalid sudoku.");
		}
		
		this.runSolver(sudoku);
		return solutions;
	}

}
