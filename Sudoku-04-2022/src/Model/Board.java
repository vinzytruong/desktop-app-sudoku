package Model;

import java.util.Random;
import java.util.Scanner;
import java.util.stream.IntStream;

public class Board {
	private Cell matrix[][];
	private Cell cellExist;
	public static final int EASY = 4;
	public static final int MED = 5;
	public static final int DIF = 6;
	
	public Board() {
		matrix=new Cell[9][9];
		cellExist=new Cell();
		for(int i=0;i<9;i++) {
			for(int j=0;j<9;j++) {
				matrix[i][j]=new Cell();
				matrix[i][j].val=0;
			}
		}
	}

	public Board(Board b) {
		this.matrix = new Cell[9][9];
		this.cellExist=new Cell(b.cellExist);
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++)
				this.matrix[i][j] = new Cell(b.matrix[i][j]);
		}
	}
	
	public void showBoard() {
		for(int i=0;i<9;i++) {
			for(int j=0;j<9;j++) {
				System.out.print(matrix[i][j].val+" ");
			}
			System.out.println();
		}
	}
	
	public boolean checkFinish(Board b) {
		for(int i=0;i<9;i++)
			for(int j=0;j<9;j++)
				if(b.matrix[i][j].val==0) return false;
		return true;
	}

	public void markUsed(Board b,int row,int col) {
		for(int j=0;j<9;j++) {
			if(b.matrix[row][j].val!=0) {
				b.matrix[row][col].valNum.markUsed(b.matrix[row][j].val);
			}
		}
		for(int j=0;j<9;j++) {
			if(b.matrix[j][col].val!=0) {
				b.matrix[row][col].valNum.markUsed(b.matrix[j][col].val);
			}
		}
		int x = row/3, y = col/3;
	    for(int i = 3*x; i < 3*x+3; i++){
	        for(int j = 3*y; j < 3*y+3; j++){
	        	if(b.matrix[i][j].val!=0) {
	        		b.matrix[row][col].valNum.markUsed(b.matrix[i][j].val);
				}
	        }
	    }
	}
	//Doi chi so tu 1 chieu sang 2 chieu
	public int findRow(int number) {
		int row=0;
		for(int i=0;i<9;i++) if((number-i)%9==0) row=(number-i)/9;
		return row;
	}

	public int findCol(int number,int row) {
		return number-(row*9);
	}
	
	public Board generate(Board b,int numCell) {
		if(b.checkFinish(b)) return b;
		int i=findRow(numCell);
		int j=findCol(numCell,findRow(numCell));
		int rand;
		Random r = new Random();
		do {
			b.markUsed(b, i, j);
			if (b.matrix[i][j].valNum.countNotUse() == 0) {
				b.matrix[i][j].valNum.resetNum();
				b.matrix[i][j].val=0;
				b.generate(b,numCell-1);
			}
			rand = r.nextInt(9);
			b.matrix[i][j].val = b.matrix[i][j].valNum.num[rand];
		} while (b.matrix[i][j].valNum.used[rand]);
		numCell++;
		return b.generate(b,numCell);
	}
	
	public int checkCellBlankInRow(Board b,int r) {
		int count=0;
		for(int i=0;i<9;i++) if(b.matrix[r][i].val==0) count++;
		return count;
	}
	
	//Random o trong cap do de
	public Board selectLevel(Board b,int level) {
		int rand;
		Random rd = new Random();
		for(int i=0;i<9;i++) {
			do {
				rand = rd.nextInt(9);
				if(checkCellBlankInRow(b,i)<level) b.matrix[i][rand].val = 0;
			}while(checkCellBlankInRow(b,i)<level);	
		}		
		return b;
	}
	//Kiem tra xem gia tri nay co phu hop khong
	public boolean isValid(Board b, int row, int column) {
	    return (rowConstraint(b, row) && columnConstraint(b, column) && squareConstraint(b, row, column));
	}
	private boolean rowConstraint(Board b, int row) {
	    boolean[] constraint = new boolean[9];
	    return IntStream.range(0, 9).allMatch(column -> checkConstraint(b, row, constraint, column));
	}
	private boolean columnConstraint(Board b, int column) {
	    boolean[] constraint = new boolean[9];
	    return IntStream.range(0, 9).allMatch(row -> checkConstraint(b, row, constraint, column));
	}
	private boolean squareConstraint(Board b, int row, int column) {
	    boolean[] constraint = new boolean[9];
	    int rs = (row / 3) * 3;
	    int re = rs + 3;
	    int cs = (column / 3) * 3;
	    int ce = cs + 3;

	    for (int r = rs; r < re; r++) {
	        for (int c = cs; c < ce; c++) {
	            if (!checkConstraint(b, r, constraint, c)) return false;
	        }
	    }
	    return true;
	}
	boolean checkConstraint(Board b, int row, boolean[] constraint, int col) {
	    if (b.matrix[row][col].val != 0) {
	        if (!constraint[b.matrix[row][col].val - 1]) {
	            constraint[b.matrix[row][col].val - 1] = true;
	            
	            
	        } else {
	        	setCellExist(row,col,b.matrix[row][col].val);
//	        	System.out.println(row+","+col);
	            return false;
	        }
	    }
	    return true;
	}
	public void setCellExist(int row,int col,int val) {
		cellExist.setRow(row);
		cellExist.setCol(col);
		cellExist.setVal(val);
	}
	public Cell getCellExist() {
		return cellExist;
	}
	
	//Giai ban co sudoku bang thuat toan vet can
	
	public boolean solveGame(Board b) {	
		for(int i=0;i<9;i++) {
			for(int j=0;j<9;j++) {
				/*Neu la o trong*/
				if(b.matrix[i][j].val==0) {
					System.out.println("Duyet o trong ke tiep");
					for(int k=1;k<=9;k++) {
						b.matrix[i][j].val=k;
						System.out.println(i+","+j+":"+k);
						if(isValid(b, i, j) && solveGame(b)) return true;
						b.matrix[i][j].val=0;
					}
					System.out.println("Quay lui");
					return false;
				}
			}
		}
		return true;
	}
	public boolean sinhBoard(Board b) {	
		int k;
		Random r = new Random();
		for(int i=0;i<9;i++) {
			for(int j=0;j<9;j++) {
				Number num=new Number();
				/*Neu la o trong*/
				if(b.matrix[i][j].val==0) {
					System.out.println("Duyet o trong ke tiep");
					while(num.countNotUse()!= 0){
						k = r.nextInt(9)+1;
						b.matrix[i][j].val=k;
						num.markUsed(k);
						System.out.println(i+","+j+":"+k);
						System.out.println("NumNotUse="+num.countNotUse());
						if(isValid(b, i, j) && sinhBoard(b)) return true;
						b.matrix[i][j].val=0;
					}
					System.out.println("Quay lui");
					return false;
				}
			}
		}
		return true;
	}
	public void enterBoard() {
		for(int i=0;i<9;i++) {
			for(int j=0;j<9;j++) {
				System.out.print("Matrix["+i+"]["+j+"]=");
				Scanner sc=new Scanner(System.in);
				this.matrix[i][j].val=sc.nextInt();
			}
		}
	}
	public static void main(String[] args) {
		Board b=new Board();
//		System.out.println("Nhập vào ma trận sudoku");
//		int input[][]= {{4,7,2,9,8,0,3,6,5},
//						{3,9,6,7,4,5,1,8,2},
//						{1,5,8,3,6,2,9,4,7},
//						{0,1,4,8,5,0,2,9,3},
//						{9,2,5,4,7,3,8,1,6},
//						{0,8,3,1,2,9,7,5,4},
//						{2,6,1,5,3,0,4,7,9},
//						{8,3,7,6,9,0,5,2,1},
//						{5,4,9,2,1,7,6,3,8}};
//		for(int i=0;i<9;i++) {
//			for(int j=0;j<9;j++) {
//				b.matrix[i][j].val=input[i][j];
//			}
//		}
		b.sinhBoard(b);
		b.showBoard();
		
//		b.showBoard();
//		b.generate(b,0);
//		b.selectLevel(b,EASY);
//		System.out.println("Cap do de:");
//		b.showBoard();
//		System.out.println();
//		
//		b.solveGame(b);
//		System.out.println("Giai ban co sudoku");
//		b.showBoard();
//		b.solveGame(b);
//		b.showBoard();
	}
	public Cell[][] getMatrix() {
		return matrix;
	}
}
