package Model;

import java.util.Random;
import java.util.Scanner;

public class Board {
    private Cell matrix[][];
    public static final int EASY = 4;
    public static final int NORMAL = 5;
    public static final int HARD = 6;
    public String level;
    public int time;

    public Board() {
        matrix = new Cell[9][9];
        level = new String();
        time = 0;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                matrix[i][j] = new Cell();
                matrix[i][j].val = 0;
            }
        }
    }

    public Board(Board b) {
        this.matrix = new Cell[9][9];
        this.level = new String(b.level);
        this.time = b.time;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                this.matrix[i][j] = new Cell(b.matrix[i][j]);
            }
        }
    }

    public void showBoard() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                System.out.print(matrix[i][j].val + " ");
            }
            System.out.println();
        }
    }

    public boolean checkFinish(Board b) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (b.matrix[i][j].val == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    public int checkCellBlankInRow(Board b, int r) {
        int count = 0;
        for (int i = 0; i < 9; i++) {
            if (b.matrix[r][i].val == 0) {
                count++;
            }
        }
        return count;
    }

    // Random o trong cap do de
    public Board selectLevel(Board b, int level) {
        int rand;
        Random rd = new Random();
        for (int i = 0; i < 9; i++) {
            do {
                rand = rd.nextInt(9);
                if (checkCellBlankInRow(b, i) < level) {
                    b.matrix[i][rand].val = 0;
                }
            } while (checkCellBlankInRow(b, i) < level);
        }
        return b;
    }

    public boolean constraintRow(Board b, int row, int col, Cell cellExist) {
        for (int i = 0; i < 9; i++) {
            if (i != col && b.matrix[row][i].val == b.matrix[row][col].val) {
//              System.out.println("Khong thoa rang buoc hang");
                cellExist.setRow(row);
                cellExist.setCol(i);
                cellExist.setVal(b.matrix[row][i].val);
                return false;
            }
        }
        return true;
    }

    public boolean constraintCol(Board b, int row, int col, Cell cellExist) {
        for (int i = 0; i < 9; i++) {
            if (i != row && b.matrix[i][col].val == b.matrix[row][col].val) {
//		System.out.println("Khong thoa rang buoc cot");
                cellExist.setRow(i);
                cellExist.setCol(col);
                cellExist.setVal(b.matrix[i][col].val);
                return false;
            }
        }
        return true;
    }

    public boolean constraintArea(Board b, int row, int col, Cell cellExist) {
        int rowStart = (row / 3) * 3;
        int rowEnd = rowStart + 3;
        int colStart = (col / 3) * 3;
        int colEnd = colStart + 3;

        for (int r = rowStart; r < rowEnd; r++) {
            for (int c = colStart; c < colEnd; c++) {
                if (b.matrix[row][col].val != 0) {
                    if (r != row && c != col && b.matrix[r][c].val == b.matrix[row][col].val) {
//			System.out.println("Khong thoa rang buoc vung");
                        cellExist.setRow(r);
                        cellExist.setCol(c);
                        cellExist.setVal(b.matrix[r][c].val);

                        return false;
                    }
                }
            }
        }
        return true;
    }

    public boolean isValid(Board b, int row, int col, Cell cellExist) {
        return (constraintRow(b, row, col, cellExist) && constraintCol(b, row, col, cellExist)
                && constraintArea(b, row, col, cellExist));
    }

    public boolean generate(Board b) {
        int k;
        Random r = new Random();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                Number num = new Number();
                /* Neu la o trong */
                if (b.matrix[i][j].val == 0) {
//                  System.out.println("Duyet o trong ke tiep");
                    while (num.countNotUse() != 0) {
                        k = r.nextInt(9) + 1;
                        b.matrix[i][j].val = k;
                        num.markUsed(k);
//			System.out.println(i + "," + j + ":" + k);
//			System.out.println("NumNotUse=" + num.countNotUse());
                        Cell cellExist = new Cell();
                        if (isValid(b, i, j, cellExist) && generate(b)) {
                            return true;
                        }
                        b.matrix[i][j].val = 0;
                    }
//                  System.out.println("Quay lui");
                    return false;
                }
            }
        }
        return true;
    }
    public void hintCell(Board b) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (b.matrix[i][j].val == 0) {
                    for (int k = 1; k <= 9; k++) {
                        b.matrix[i][j].val = k;
                        Cell cellExist = new Cell();
                        if (isValid(b, i, j, cellExist)) {
                            b.matrix[i][j].getNumber().markUsed(k);
                        }
                        b.matrix[i][j].val=0;
                    }
                }

            }
        }
    }


    /* Giai ban co sudoku bang thuat toan vet can */
    public boolean solveGame(Board b) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                /* Neu la o trong */
                if (b.matrix[i][j].val == 0) {
//                  System.out.println("Duyet o trong ke tiep");
                    for (int k = 1; k <= 9; k++) {
                        b.matrix[i][j].val = k;
//			System.out.println(i + "," + j + ":" + k);
                        Cell cellExist = new Cell();
                        if (isValid(b, i, j, cellExist) && solveGame(b)) {
                            return true;
                        }
                        b.matrix[i][j].val = 0;
                    }
//                  System.out.println("Quay lui");
                    return false;
                }
            }
        }
        return true;
    }

    public Cell[][] getMatrix() {
        return matrix;
    }

    public void enterBoard() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                System.out.print("Matrix[" + i + "][" + j + "]=");
                Scanner sc = new Scanner(System.in);
                this.matrix[i][j].val = sc.nextInt();
            }
        }
    }
    public static void main(String[] args) {
        Board b = new Board();
        b.generate(b);
        b.selectLevel(b, EASY);
        b.showBoard();
        b.hintCell(b);
//        for (int i = 0; i < 9; i++) {
//            for (int j = 0; j < 9; j++) {
//                System.out.print(i+","+j+":");
//                b.matrix[i][j].getNumber().showNum();  
 
                System.out.println(b.matrix[0][0].getNumber().getNumberEnable());
//            }
//        }
    }
}
