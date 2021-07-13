package dlx;

import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;

public class DancingLinks {
	boolean verbose = true;

	private ColumnNode header;
	private int solutions = 0;
	private SolutionHandler handler;
	private List<DancingNode> answer;

	// Heart of the algorithm
	private void search(int k) {
		if (header.R == header) { // all the columns removed
			solutions++;
			if (verbose) {
				System.out.println("-----------------------------------------");
				System.out.println("Solution #" + solutions + "\n");
			}
			handler.handleSolution(answer);
			if (verbose) {
				System.out.println("-----------------------------------------");
			}
		} 
		else {
			ColumnNode c = selectColumnNodeHeuristic();
			c.cover();
			header.size--;

			for (DancingNode r = c.D; r != c; r = r.D) {
				answer.add(r);

				for (DancingNode j = r.R; j != r; j = j.R) {
					j.C.cover();
					header.size--;
				}

				search(k + 1);

				r = answer.remove(answer.size() - 1);
                //c = r.C;

				for (DancingNode j = r.L; j != r; j = j.L) {
					j.C.uncover();
					header.size++;
				}
			}
			c.uncover();
			header.size++;
		}
	}

	/***************** Column select strategy start *****************/
	
	ColumnNode selectColumnNodeNaive() {
		return (ColumnNode) header.R;
	}

	private ColumnNode selectColumnNodeHeuristic() {
		int min = Integer.MAX_VALUE;
		ColumnNode ret = null;
		for (ColumnNode c = (ColumnNode) header.R; c != header; c = (ColumnNode) c.R) {
			if (c.size < min) {
				min = c.size;
				ret = c;
			}
		}
		return ret;
	}
    
	// Ha, another Knuth algorithm
	ColumnNode selectColumnNodeRandom() { // select a column randomly
		ColumnNode ptr = (ColumnNode) header.R;
		ColumnNode ret = null;
		int c = 1;
		while (ptr != header) {
			if (Math.random() <= 1 / (double) c) {
				ret = ptr;
			}
			c++;
			ptr = (ColumnNode) ptr.R;
		}
		return ret;
	}
    
	ColumnNode selectColumnNodeNth(int n) {
		int go = n % header.size;
		ColumnNode ret = (ColumnNode) header.R;
		for (int i = 0; i < go; i++)
			ret = (ColumnNode) ret.R;
		return ret;
	}
	
	/***************** Column select strategy end *****************/

	// diagnostics to have a look at the board state
	public void printBoard() {
		System.out.println("Board Config: ");
		for (ColumnNode tmp = (ColumnNode) header.R; tmp != header; tmp = (ColumnNode) tmp.R) {
			for (DancingNode d = tmp.D; d != tmp; d = d.D) {
				String ret = d.C.name + " --> ";
				for (DancingNode i = d.R; i != d; i = i.R) {
					ret += i.C.name + " --> ";
				}
				System.out.println(ret);
			}
		}
	}

    // grid is a grid of 0s and 1s to solve the exact cover for
    // returns the root column header node
	private ColumnNode makeDLXBoard(int[][] grid) {
		final int COLS = grid[0].length;
		final int ROWS = grid.length;

		ColumnNode headerNode = new ColumnNode("header");
		ArrayList<ColumnNode> columnNodes = new ArrayList<>();

		for (int i = 0; i < COLS; i++) {
			ColumnNode n = new ColumnNode(Integer.toString(i));
			columnNodes.add(n);
			headerNode = (ColumnNode) headerNode.hookRight(n);
		}
		headerNode = headerNode.R.C;

		for (int i = 0; i < ROWS; i++) {
			DancingNode prev = null;
			for (int j = 0; j < COLS; j++) {
				if (grid[i][j] == 1) {
					ColumnNode col = columnNodes.get(j);
					DancingNode newNode = new DancingNode(col);
					if (prev == null)
						prev = newNode;
					
					col.U.hookDown(newNode);
					prev = prev.hookRight(newNode);
					col.size++;
				}
			}
		}

		headerNode.size = COLS;
        return headerNode;
    }

    // Grid consists solely of 1s and 0s. Undefined behavior otherwise
	public DancingLinks(int[][] grid) {
		this(grid, new DefaultHandler());
	}

	public DancingLinks(int[][] grid, SolutionHandler h) {
		header = makeDLXBoard(grid);
		handler = h;
	}

	public void runSolver() {
		solutions = 0;
		answer = new LinkedList<DancingNode>();
		search(0);
	}
	
	public int getSolutionsNumber() {
		return solutions;
	}

}
