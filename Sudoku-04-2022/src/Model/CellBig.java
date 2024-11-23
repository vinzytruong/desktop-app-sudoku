package Model;

import java.util.ArrayList;

public class CellBig {
	private ArrayList<Cell> listCell;
	private final int n=9;
	public CellBig() {
		listCell = new ArrayList<Cell>();
		for(int i=0;i<n;i++) {
			Cell c=new Cell();
			listCell.add(c);
		}
	}
	public CellBig(CellBig cb) {
		this.listCell = new ArrayList<Cell>(cb.listCell);
		for(int i=0;i<n;i++) {
			Cell c=new Cell(cb.listCell.get(i));
			this.listCell.add(c);
		}
	}
	public void showCellBig() {
		for(int i=0;i<n;i++) {
			System.out.println(listCell.get(i).val);
		}
	}
	public static void main(String[] args) {
		CellBig cb=new CellBig();
		cb.showCellBig();
	}
	public ArrayList<Cell> getListCell() {
		return listCell;
	}
 
}
