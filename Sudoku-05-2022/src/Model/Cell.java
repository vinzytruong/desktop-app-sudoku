package Model;

public class Cell {

    public int row, col, val;
    private Number number;

    public Cell() {
        val = 0;
        row = 0;
        col = 0;
        number=new Number();
    }

    public Number getNumber() {
        return number;
    }

    public Cell(Cell c) {
        this.val = c.val;
        this.row = c.row;
        this.col = c.col;
        this.number = new Number(c.number);
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
