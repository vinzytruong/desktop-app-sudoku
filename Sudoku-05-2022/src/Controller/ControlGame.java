package Controller;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import Model.*;
import View.*;
import java.net.URL;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class ControlGame extends JPanel {

    public JPanelHall jpanelHall;
    public JPanelControl jpanelControl;
    public JPanelLevel jpanelLevel;
    public JPanelBoard jpanelBoard;
    public JPanelScore jpanelScore;
    public JPanelIntro jpanelIntro;
    private Board boardQuestion;
    private Board boardAnswer;
    private Stack stackUndo;
    private Stack stackValue;
    private ButtonClickNumListener evtClickNum;
    private ButtonMouseIMGListener evtMouseIMG;
    private ButtonMouseListener evtMouse;
    private ButtonClickListener evtClickCell;
    private ButtonKeyListener evtKeypress;
    private ExitListener evtExit;
    private String level;
    private int second;
    private String value;
    private Timer time;
    private Timer solveTime;
    private MainFrame mainFrame;
    private String pathFileBoard;
    private String pathFileIntroduce;
    private String pathIcon;
    private boolean stop = false;

    public ControlGame() {
        jpanelHall = new JPanelHall();
        jpanelControl = new JPanelControl();
        jpanelLevel = new JPanelLevel();
        jpanelBoard = new JPanelBoard();
        jpanelScore = new JPanelScore();
        jpanelIntro = new JPanelIntro("No information");
        boardQuestion = new Board();
        boardAnswer = new Board();
        stackUndo = new Stack();
        stackValue = new Stack();
        evtClickNum = new ButtonClickNumListener();
        evtMouseIMG = new ButtonMouseIMGListener();
        evtMouse = new ButtonMouseListener();
        evtClickCell = new ButtonClickListener();
        evtKeypress = new ButtonKeyListener();
        evtExit = new ExitListener();
        level = new String();
        second = 0;
        value = new String("00:00");
        time = new Timer();
        solveTime = new Timer();
//        pathFileBoard = new String("..\\board.txt");
//        pathFileIntroduce = getClass().getResource("/Data/introduce.txt").getFile();
        pathIcon = getClass().getResource("/Data/introduce.txt").getFile();
    }

    public void controlUI(MainFrame m) throws IOException {
        mainFrame = m;
        runAudio("/Data/audio.wav");
        File f1 = new File("board.txt");
        if (f1.exists()) {
            System.out.println("Exists file board.txt");
        } else {
            f1.createNewFile();
            System.out.println("Created!");
        }
        f1.setReadOnly();

        File f2 = new File("introduce.txt");
        if (f2.exists()) {
            System.out.println("Exists file introduce.txt");
        } else {
            f2.createNewFile();
            System.out.println("Created!");
        }
        f2.setReadOnly();

        if (readFileBoard() == null) {
            goHallInterface();
        } else {
            level = new String(readFileBoard().level);
            if (readFileBoard().level.equals("easy")) {
                goBoardInterface(Board.EASY);
            }
            if (readFileBoard().level.equals("normal")) {
                goBoardInterface(Board.NORMAL);
            }
            if (readFileBoard().level.equals("hard")) {
                goBoardInterface(Board.HARD);
            }
        }
        addEvtMouseIMG();
        addEvtLevel();
        goLevelInterface();
        goHScoreInterface();
        goIntroInterface();
    }

    public Board readFileBoard() {

        Board boardSolving = new Board();
        FileInputStream file = null;
        try {
            file = new FileInputStream("board.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            Scanner dt = new Scanner(file, "UTF-8");
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    boardQuestion.getMatrix()[i][j].val = dt.nextInt();
                }
            }
            dt.nextLine();
            boardSolving.level = dt.nextLine();
            boardSolving.time = dt.nextInt();

            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    boardSolving.getMatrix()[i][j].val = dt.nextInt();
                }
            }
            dt.close();
        } catch (Exception e) {
            return null;
        }
        boardSolving.showBoard();
        return boardSolving;
    }

    public String readFileIntro() {
        String data = new String();
        FileInputStream file = null;
        try {
            file = new FileInputStream("introduce.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            Scanner dt = new Scanner(file, "UTF-8");
            while (dt.hasNext()) {
                data = data + dt.nextLine() + "\n";

            }
            dt.close();
        } catch (Exception e) {
            return null;
        }
        return data;
    }

    public void writeFileBoard(Board boardQuestion, Board boardSolving) {
        File f = new File("board.txt");
        f.setWritable(true);

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream("board.txt");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        PrintWriter out = new PrintWriter(fos);

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                out.printf("%d ", boardQuestion.getMatrix()[i][j].val);

            }
            out.printf("\n");
        }

        out.printf("%s\n", boardSolving.level);
        out.printf("%d\n", boardSolving.time);

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                out.printf("%d ", boardSolving.getMatrix()[i][j].val);
            }
            out.printf("\n");
        }
        out.close();

        f.setReadOnly();
    }

    public void clearFile(String urlFile) {
        try {
            File file = new File("board.txt");
            file.setWritable(true);
            file.delete();
            file.createNewFile();
            file.setWritable(false);
            file.setReadOnly();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void addEvtMouseIMG() {
        jpanelHall.btnStart.addMouseListener(evtMouseIMG);
        jpanelHall.btnHighScore.addMouseListener(evtMouseIMG);
        jpanelHall.btnIntro.addMouseListener(evtMouseIMG);

        jpanelLevel.btnEasy.addMouseListener(evtMouseIMG);
        jpanelLevel.btnNormal.addMouseListener(evtMouseIMG);
        jpanelLevel.btnHard.addMouseListener(evtMouseIMG);

        jpanelControl.btnSolve.addMouseListener(evtMouseIMG);
        jpanelControl.btnMenu.addMouseListener(evtMouseIMG);
        jpanelControl.btnUndo.addMouseListener(evtMouseIMG);
        jpanelControl.btnClear.addMouseListener(evtMouseIMG);

        jpanelScore.btnGoBack.addMouseListener(evtMouseIMG);
    }

    public void addEvtLevel() {
        jpanelLevel.btnHard.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jpanelLevel.setVisible(false);
                level = new String("hard");
                goBoardInterface(Board.HARD);

            }
        });
        jpanelLevel.btnNormal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jpanelLevel.setVisible(false);
                level = new String("normal");
                goBoardInterface(Board.NORMAL);
            }
        });
        jpanelLevel.btnEasy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jpanelLevel.setVisible(false);
                level = new String("easy");
                goBoardInterface(Board.EASY);
            }
        });
    }

    public void goHallInterface() {
        jpanelControl.setVisible(false);
        jpanelBoard.setVisible(false);
        jpanelLevel.setVisible(false);
        jpanelScore.setVisible(false);
        jpanelHall.setVisible(true);
        mainFrame.add(jpanelHall, BorderLayout.CENTER);
        mainFrame.setVisible(true);
    }

    /* Them su kien khi click vao start game */
    public void goLevelInterface() {
        jpanelHall.btnStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jpanelHall.setVisible(false);
                jpanelLevel.setVisible(true);
                mainFrame.add(jpanelLevel, BorderLayout.CENTER);
            }
        });
    }

    public void goHScoreInterface() {
        jpanelHall.btnHighScore.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jpanelHall.setVisible(false);
                jpanelScore.setVisible(true);
                addEvtBack();
                mainFrame.add(jpanelScore, BorderLayout.CENTER);
            }
        });
    }

    public void goIntroInterface() {
        jpanelHall.btnIntro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String s = readFileIntro();
                jpanelHall.setVisible(false);

                jpanelIntro = new JPanelIntro(s);
                jpanelIntro.setVisible(true);
                mainFrame.add(jpanelIntro, BorderLayout.CENTER);

                jpanelIntro.btnGoBack.addMouseListener(evtMouseIMG);
                addEvtBack();
            }
        });
    }

    public void goBoardInterface(int levelBoard) {
        jpanelControl.btnClear.setEnabled(true);
        jpanelControl.btnSolve.setEnabled(true);
        jpanelControl.btnMenu.setEnabled(true);
        jpanelControl.btnUndo.setEnabled(true);

        resetTimer();
        removeEvtClickNum();
        removeEvtBoard();
        removeEvtClickNum();

        if (readFileBoard() == null) {
            boardQuestion = new Board();
            boardQuestion.generate(boardQuestion);
            boardQuestion.selectLevel(boardQuestion, levelBoard);
            /* Luu lai ban co ban dau sau khi khoi tao vao gamePanel */

            jpanelBoard.game = new Board(boardQuestion);
            jpanelBoard.game.hintCell(boardQuestion);
//            System.out.println(boardQuestion.getMatrix()[0][0].getNumber().getNumberEnable());
            getVal(jpanelBoard.game);
            startTimer();

            /* Them sư kien */
            addEvtBlankCell(jpanelBoard.game);
        } else {
            /* Luu lai ban co ban dau sau khi khoi tao vao gamePanel */
            jpanelBoard.game = new Board(boardQuestion);
            getVal(jpanelBoard.game);
            startTimer();

            /* Them sư kien */
            addEvtBlankCell(jpanelBoard.game);

            Board boardSolving = new Board(readFileBoard());
            getVal(boardSolving);
        }
        addEvtMenu();
        addEvtSolve();
        addEvtClear();
        addEvtUndo();

        mainFrame.addWindowListener(evtExit);

        jpanelControl.setVisible(true);
        jpanelBoard.setVisible(true);

        BorderLayout layout = new BorderLayout();
        mainFrame.setLayout(layout);
        mainFrame.add(jpanelBoard, BorderLayout.CENTER);
        mainFrame.add(jpanelControl, BorderLayout.EAST);
    }

    /* Lay gia tri tu mang 9x9 cho vao giao dien */
    public void getVal(Board boardSrc) {
        second = boardSrc.time;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                int idxCellBig = (i / 3) * 3 + (j / 3);
                int idxCell = (i % 3) * 3 + (j % 3);
                jpanelBoard.jpanelArea[idxCellBig].btnCell[idxCell].setText(boardSrc.getMatrix()[i][j].val + "");
                if (boardSrc.getMatrix()[i][j].val == 0) {
                    jpanelBoard.jpanelArea[idxCellBig].btnCell[idxCell].setToolTipText(boardQuestion.getMatrix()[i][j].getNumber().getNumberEnable());
                    jpanelBoard.jpanelArea[idxCellBig].btnCell[idxCell].setText("");
                }
            }
        }
    }

    /* Reset lai thoi gian */
    public void resetTimer() {
        time.cancel();
        time = new Timer();
        second = 0;
        value = "00:00";
        jpanelControl.lblTime.setText(value);
    }

    /* Bat dau tinh thoi gian */
    public void startTimer() {
        time.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                second++;
                value = second / 60 + ":" + second % 60;
                jpanelControl.lblTime.setText(value);
            }
        }, 1000, 1000);
    }

    /* Them su kien cho cac o trong */
    public void addEvtBlankCell(Board board) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                int idxCellBig = (i / 3) * 3 + (j / 3);
                int idxCell = (i % 3) * 3 + (j % 3);

                /* Neu la o trong */
                if (board.getMatrix()[i][j].val == 0) {
                    jpanelBoard.jpanelArea[idxCellBig].btnCell[idxCell].setForeground(new Color(1, 26, 39));
                    jpanelBoard.jpanelArea[idxCellBig].btnCell[idxCell].setBackground(Color.WHITE);
                    jpanelBoard.jpanelArea[idxCellBig].btnCell[idxCell].setI(i);
                    jpanelBoard.jpanelArea[idxCellBig].btnCell[idxCell].setJ(j);
                    jpanelBoard.jpanelArea[idxCellBig].btnCell[idxCell].addMouseListener(evtMouse);
                    jpanelBoard.jpanelArea[idxCellBig].btnCell[idxCell].addActionListener(evtClickCell);
                } else {/* Neu khong phai o trong */
                    jpanelBoard.jpanelArea[idxCellBig].btnCell[idxCell].setForeground(Color.WHITE);
                    jpanelBoard.jpanelArea[idxCellBig].btnCell[idxCell].setBackground(new Color(6, 56, 82));
                }
            }
        }
    }

    /* Them su kien click vao undo */
    public void addEvtUndo() {
        jpanelControl.btnUndo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JButtonCell btnCell = new JButtonCell();

                if (!stackValue.empty()) {
                    btnCell = stackValue.getData()[stackValue.getTop()];
                    btnCell.setText("");
                    jpanelBoard.game.getMatrix()[btnCell.getI()][btnCell.getJ()].val = 0;
                    stackValue.pop();
                }
            }
        });
    }

    /* Them su kien khi click vao menu */
    public void addEvtMenu() {
        jpanelControl.btnMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetTimer();
                removeEvtBoard();
                removeEvtClickNum();
                mainFrame.removeWindowListener(evtExit);
                solveTime.cancel();
                stop = true;
                for (int i = 0; i < 9; i++) {
                    for (int j = 0; j < 9; j++) {
                        int idxCellBig = (i / 3) * 3 + (j / 3);
                        int idxCell = (i % 3) * 3 + (j % 3);
                        jpanelBoard.jpanelArea[idxCellBig].btnCell[idxCell].setText("");
                        jpanelBoard.game = new Board();
                    }
                }
                new ControlGame();
                goHallInterface();
            }
        });
    }

    public void addEvtBack() {
        jpanelIntro.btnGoBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jpanelIntro.setVisible(false);
                jpanelHall.setVisible(true);
            }
        });
        jpanelScore.btnGoBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jpanelScore.setVisible(false);
                jpanelHall.setVisible(true);
            }
        });
    }

    /* Giai ban co sudoku bang thuat toan vet can */
    public boolean solveGame(Board b) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                int idxCellBig = (i / 3) * 3 + (j / 3);
                int idxCell = (i % 3) * 3 + (j % 3);
                /* Neu la o trong */
                if (b.getMatrix()[i][j].val == 0) {
                    /*System.out.println("Duyet o trong ke tiep");*/
                    for (int k = 1; k <= 9; k++) {
                        if (stop == true) {
                            return false;
                        }
                        b.getMatrix()[i][j].val = k;
                        jpanelBoard.jpanelArea[idxCellBig].btnCell[idxCell].setText(b.getMatrix()[i][j].val + "");
                        jpanelBoard.jpanelArea[idxCellBig].btnCell[idxCell].setBackground(new Color(237, 231, 119));
                        try {
                            TimeUnit.MILLISECONDS.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        Cell cellExist = new Cell();
                        if (b.isValid(b, i, j, cellExist)) {
                            jpanelBoard.jpanelArea[idxCellBig].btnCell[idxCell].setBackground(Color.WHITE);
                            if (solveGame(b)) {
                                return true;
                            }
                        }
                        b.getMatrix()[i][j].val = 0;
                        jpanelBoard.jpanelArea[idxCellBig].btnCell[idxCell].setText("");
                    }
                    jpanelBoard.jpanelArea[idxCellBig].btnCell[idxCell].setBackground(Color.WHITE);
                    /*System.out.println("Quay lui");*/
                    return false;
                }
            }
        }
        return true;
    }

    /* Them su kien khi click vao solve */
    public void addEvtSolve() {
        jpanelControl.btnSolve.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jpanelControl.btnSolve.setEnabled(false);
                jpanelControl.btnClear.setEnabled(false);
                jpanelControl.btnUndo.setEnabled(false);
                time.cancel();
                solveTime.cancel();
                solveTime = new Timer();
                stop = false;

                /* Giai ban co va luu lai */
                boardAnswer = new Board(boardQuestion);
                solveTime.scheduleAtFixedRate(new TimerTask() {
                    @Override
                    public void run() {
                        if (solveGame(boardAnswer)) {
                            messageFinished();
                        }
                        solveTime.cancel();
                    }
                }, 1000, 1000);
            }
        });
    }

    /* Them su kien khi click vao play again */
    public void addEvtClear() {
        jpanelControl.btnClear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetTimer();
                startTimer();
                System.out.println("Board copy:");
                boardQuestion.showBoard();
                getVal(boardQuestion);
            }
        });
    }

    /* Them su kien chon mot so tren giao dien de dien vao ban co sudoku */
    public void addEvtClickNum(int i, int j) {
        for (int x = 0; x < 9; x++) {
            evtClickNum.i = i;
            evtClickNum.j = j;
            jpanelControl.btnNumber[x].addMouseListener(evtMouse);
            jpanelControl.btnNumber[x].addActionListener(evtClickNum);
        }
    }

    /* Xoa su kien chon mot so tren giao dien de dien vao ban co sudoku */
    public void removeEvtClickNum() {
        for (int x = 0; x < 9; x++) {
            jpanelControl.btnNumber[x].removeActionListener(evtClickNum);
            jpanelControl.btnNumber[x].removeMouseListener(evtMouse);
            jpanelControl.btnNumber[x].setBackground(Color.WHITE);
        }
    }

    /* Xoa su kien re chuot vao o trong ban co sudoku */
    public void removeEvtBoard() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                int idxCellBig = (i / 3) * 3 + (j / 3);
                int idxCell = (i % 3) * 3 + (j % 3);
                jpanelBoard.jpanelArea[idxCellBig].btnCell[idxCell].removeMouseListener(evtMouse);
                jpanelBoard.jpanelArea[idxCellBig].btnCell[idxCell].removeActionListener(evtClickCell);
                jpanelBoard.jpanelArea[idxCellBig].btnCell[idxCell].setBackground(Color.WHITE);
            }
        }
    }

    public void messageSaveBoard(Board boardQuestion, Board boardSolving) {
        Icon icon;
        if (pathIcon == null) {
            throw new IllegalArgumentException("File not found!");
        } else {
            Image imgIcon = Toolkit.getDefaultToolkit().getImage(pathIcon);
            icon = new ImageIcon(imgIcon.getScaledInstance(20, 20, Image.SCALE_SMOOTH));
        }
        int select = JOptionPane.showConfirmDialog(this, "Do you want to save sudoku status ?", "EXIT",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, icon);

        if (select == 0) {
            writeFileBoard(boardQuestion, boardSolving);
        } else {
            clearFile(pathFileBoard);
        }
    }

    public void alertInvalid() {
        Icon icon;
        if (pathIcon == null) {
            throw new IllegalArgumentException("File not found!");
        } else {
            Image imgIcon = Toolkit.getDefaultToolkit().getImage(pathIcon);
            icon = new ImageIcon(imgIcon.getScaledInstance(20, 20, Image.SCALE_SMOOTH));
        }
        JOptionPane.showMessageDialog(this, "Invalid value. Please enter the number", "ALERT", JOptionPane.YES_NO_OPTION, icon);
    }

    public void messageFinished() {
        Icon icon;
        if (pathIcon == null) {
            throw new IllegalArgumentException("file not found!");
        } else {
            Image imgIcon = Toolkit.getDefaultToolkit().getImage(pathIcon);
            icon = new ImageIcon(imgIcon.getScaledInstance(20, 20, Image.SCALE_SMOOTH));
        }
        JOptionPane.showMessageDialog(this, "Finished solving", "FINISHED", JOptionPane.YES_NO_OPTION, icon);
    }

    /* Sau khi chien thang thuc hien luu diem */
    public void messageSaveResult() {
        time.cancel();
        Icon icon;
        if (pathIcon == null) {
            throw new IllegalArgumentException("File not found!");
        } else {
            Image imgIcon = Toolkit.getDefaultToolkit().getImage(pathIcon);
            icon = new ImageIcon(imgIcon.getScaledInstance(20, 20, Image.SCALE_SMOOTH));
        }
        int select = JOptionPane.showConfirmDialog(this, "Do you want to save the results ?", "YOU WIN",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, icon);
        if (select == 0) {
            String name = JOptionPane.showInputDialog(this, "Please enter your name here", "SAVE RESULT",
                    JOptionPane.YES_NO_OPTION);
            Player.insertPlayer(name, second, level);
        }
        goHallInterface();
    }

    /* Su kien re chuot vao button hinh anh */
    public class ButtonMouseIMGListener implements MouseListener {

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mousePressed(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
            JButtonImage btnExited = (JButtonImage) e.getSource();
            btnExited.changeColor();
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            JButtonImage btnEntered = (JButtonImage) e.getSource();
            btnEntered.changeColor();
        }

        @Override
        public void mouseClicked(MouseEvent e) {
        }
    }

    /* Su kien re chuot trong ban co sudoku */
    public class ButtonMouseListener implements MouseListener {

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mousePressed(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
            JButton btnExited = (JButton) e.getSource();
            btnExited.setBackground(Color.WHITE);
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            JButton btnEntered = (JButton) e.getSource();
            btnEntered.setBackground(new Color(237, 231, 119));
        }

        @Override
        public void mouseClicked(MouseEvent e) {
        }
    }

    /* Su kien click chuot trong ban co sudoku */
    public class ButtonClickListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            JButtonCell btnClicked = (JButtonCell) e.getSource();
            btnClicked.setBackground(new Color(240, 129, 15));
            btnClicked.removeMouseListener(evtMouse);
            if (!stackUndo.empty()) {
                stackUndo.getData()[stackUndo.getTop()].setBackground(Color.WHITE);
            }
            stackUndo.push(btnClicked);

            /*Xoa su kien truoc tai day*/
            int k = btnClicked.getKeyListeners().length;
            for (int i = 0; i < k; i++) {
                btnClicked.removeKeyListener(btnClicked.getKeyListeners()[i]);
            }
            removeEvtClickNum();

            /*Them su kien moi*/
            btnClicked.addKeyListener(evtKeypress);
            addEvtClickNum(btnClicked.getI(), btnClicked.getJ());
        }
    }

    /* Su kien chon so sau khi click chuot vao mot o trong ban co sudoku */
    public class ButtonClickNumListener implements ActionListener {

        public int i = 0, j = 0;

        @Override
        public void actionPerformed(ActionEvent e) {
            JButton btnClicked = (JButton) e.getSource();
            btnClicked.setBackground(new Color(240, 129, 15));
            Cell cellExist = new Cell();

            /* Lay gia tri so duoc chon */
            jpanelControl.valClicked = Integer.parseInt(btnClicked.getText());

            int idxCellBig = (i / 3) * 3 + (j / 3);
            int idxCell = (i % 3) * 3 + (j % 3);

            if (jpanelControl.valClicked != 0) {
                jpanelBoard.game.getMatrix()[i][j].val = jpanelControl.valClicked;

                /* Neu so nhap vao khong hop le thi se thong bao trung lap */
                if (!jpanelBoard.game.isValid(jpanelBoard.game, i, j, cellExist)) {
                    jpanelBoard.jpanelArea[idxCellBig].btnCell[idxCell].setBackground(Color.RED);
                    alertIdentical(i, j, cellExist);
                } else {
                    jpanelBoard.jpanelArea[idxCellBig].btnCell[idxCell].setText(jpanelControl.valClicked + "");
                    jpanelBoard.game.getMatrix()[i][j].val = jpanelControl.valClicked;
                    jpanelBoard.game.showBoard();
                    stackValue.push(jpanelBoard.jpanelArea[idxCellBig].btnCell[idxCell]);

                    /* Neu nhu tat ca cac o da dien deu khong bi trung lap thi chien thang */
                    if (jpanelBoard.game.checkFinish(jpanelBoard.game)) {
                        messageSaveResult();
                    }
                }
            }
            jpanelControl.setValClicked(0);
//            removeEvtClickNum();
        }
    }

    public void alertIdentical(int i, int j, Cell cellExist) {
        int xExist = cellExist.getRow();
        int yExist = cellExist.getCol();
        int idxCellBig = (xExist / 3) * 3 + (yExist / 3);
        int idxCell = (xExist % 3) * 3 + (yExist % 3);
        Color colorBefore = jpanelBoard.jpanelArea[idxCellBig].btnCell[idxCell].getBackground();
        jpanelBoard.jpanelArea[idxCellBig].btnCell[idxCell].setBackground(Color.RED);
        Icon icon;
        if (pathIcon == null) {
            throw new IllegalArgumentException("file not found!");
        } else {
            Image imgIcon = Toolkit.getDefaultToolkit().getImage(pathIcon);
            icon = new ImageIcon(imgIcon.getScaledInstance(20, 20, Image.SCALE_SMOOTH));
        }
        JOptionPane.showMessageDialog(this, "Identical value. Please enter another value", "ALERT", JOptionPane.YES_NO_OPTION, icon);

        jpanelBoard.jpanelArea[idxCellBig].btnCell[idxCell].setBackground(colorBefore);
        jpanelBoard.jpanelArea[(i / 3) * 3 + (j / 3)].btnCell[(i % 3) * 3 + (j % 3)]
                .setBackground(new Color(240, 129, 15));
        jpanelBoard.game.getMatrix()[i][j].val = 0;
        jpanelBoard.game.showBoard();
    }

    public void runAudio(String path) {
        try {
            URL url = this.getClass().getResource(path);
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            clip.start();
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    /*Su kien nhap so tu ban phim sau khi click chuot vao mot o trong ban co sudoku*/
    public class ButtonKeyListener implements KeyListener {

        @Override
        public void keyTyped(KeyEvent e) {
        }

        @Override
        public void keyReleased(KeyEvent e) {
        }

        @Override
        public void keyPressed(KeyEvent e) {
            JButtonCell btnKeyPressed = (JButtonCell) e.getSource();
            /* Lay i va j duoc click */
            int i = btnKeyPressed.getI();
            int j = btnKeyPressed.getJ();
            Cell cellExist = new Cell();

            jpanelBoard.game.getMatrix()[i][j].val = 0;
            int v = e.getKeyCode();
//            System.out.println(v);
            /* Neu ki tu nhap vao la so */
            if ((v >= 49 && v <= 57) || (v >= 97 && v <= 105)) {
                if (v >= 49 && v <= 57) {
                    v -= 48;
                }
                if (v >= 97 && v <= 105) {
                    v -= 96;
                }
                jpanelBoard.game.getMatrix()[i][j].val = v;

                /* Neu so nhap vao khong hop le thi se thong bao trung lap */
                if (!jpanelBoard.game.isValid(jpanelBoard.game, i, j, cellExist)) {
                    btnKeyPressed.setBackground(Color.RED);
                    alertIdentical(i, j, cellExist);
                } /* Neu so nhap vao la hop le */ else {
                    btnKeyPressed.setText(v + "");
                    jpanelBoard.game.getMatrix()[i][j].val = v;
                    jpanelBoard.game.showBoard();
                    stackValue.push(btnKeyPressed);
                }
            } else {
                /* Neu ki tu nhap vao khong phai la so */
                btnKeyPressed.setBackground(Color.RED);
                alertInvalid();
            }
            /* Neu tat ca deu da co so thi chien thang */
            if (jpanelBoard.game.checkFinish(jpanelBoard.game)) {
                messageSaveResult();
            }
        }
    }

    public class ExitListener implements WindowListener {

        public void windowClosing(WindowEvent e) {
            time.cancel();
            jpanelBoard.game.level = level;
            jpanelBoard.game.time = second;
            messageSaveBoard(boardQuestion, jpanelBoard.game);
        }

        @Override
        public void windowActivated(WindowEvent arg0) {
        }

        @Override
        public void windowClosed(WindowEvent arg0) {
        }

        @Override
        public void windowDeactivated(WindowEvent arg0) {
        }

        @Override
        public void windowDeiconified(WindowEvent arg0) {
        }

        @Override
        public void windowIconified(WindowEvent arg0) {
        }

        @Override
        public void windowOpened(WindowEvent arg0) {
        }
    }
}
