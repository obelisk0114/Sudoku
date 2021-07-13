package dlx;

public class DancingNode {
	DancingNode L, R, U, D;
	ColumnNode C;

	// hooks node n1 `below` current node
	DancingNode hookDown(DancingNode n1) {
		assert (this.C == n1.C);
		n1.D = this.D;
		n1.D.U = n1;
		n1.U = this;
		this.D = n1;
		return n1;
	}

	// hooks a node n1 to the right of `this` node
	DancingNode hookRight(DancingNode n1) {
		n1.R = this.R;
		n1.R.L = n1;
		n1.L = this;
		this.R = n1;
		return n1;
	}

	void unlinkLR() {
		this.L.R = this.R;
		this.R.L = this.L;
	}

	void relinkLR() {
		this.L.R = this.R.L = this;
	}

	void unlinkUD() {
		this.U.D = this.D;
		this.D.U = this.U;
	}

	void relinkUD() {
		this.U.D = this.D.U = this;
	}

	public DancingNode() {
		L = R = U = D = this;
	}

	public DancingNode(ColumnNode c) {
		this();
		C = c;
	}

}
