package Model;

public class Cell {
	public int row,col,val;
	public Number valNum;
	public Cell() {
		val=0;
		row=0;
		col=0;
		valNum=new Number();
	}
	public Cell(Cell c) {
		this.val=c.val;
		this.row=c.row;
		this.col=c.col;
		this.valNum=new Number(c.valNum);
	}
	public int getVal() {
		return val;
	}
	public void setVal(int val) {
		this.val = val;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getCol() {
		return col;
	}

	public void setCol(int col) {
		this.col = col;
	}
}
