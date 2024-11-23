package Controller;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import Components.JButtonCellBig;
import Components.JButtonImage;
import Components.JPanelBoard;
import Components.JPanelControl;
import Components.JPanelHall;
import Components.JPanelScore;
import Components.JPanelSelectLevel;
import Model.Board;
import Model.Player;
import View.MainFrame;

public class ControlGame extends JPanel {
	public JPanelHall hallPanel;
	public JPanelControl controlPanel;
	public JPanelSelectLevel selectPanel;
	public JPanelBoard gamePanel;
	public JPanelScore scorePanel;
	private Board boardQuestion;
	private Board boardAnswer;
	private Stack stackUndo;
	private Stack stackValue;
	private ButtonClickNumberListener clickNumber;
	private ButtonClickHintListener clickHint;
	private ButtonImageMouseListener mouseIMG;
	private ButtonMouseListener mouse;
	private ButtonClickListener click;
	private ButtonKeyListener key;
	private int countHint;
	private String level;
	private int second;
	private String value;
	private Timer time;
	private MainFrame mainFrame;

	public ControlGame() {
		hallPanel = new JPanelHall();
		controlPanel = new JPanelControl();
		selectPanel = new JPanelSelectLevel();
		gamePanel = new JPanelBoard();
		scorePanel = new JPanelScore();
		boardQuestion = new Board();
		boardAnswer = new Board();
		stackUndo = new Stack();
		stackValue = new Stack();
		clickNumber = new ButtonClickNumberListener();
		clickHint = new ButtonClickHintListener();
		mouseIMG = new ButtonImageMouseListener();
		mouse = new ButtonMouseListener();
		click = new ButtonClickListener();
		key = new ButtonKeyListener();
		countHint = 0;
		level = new String();
		second = 0;
		value = new String("00:00");
		time = new Timer();
	}

	public void goInterfaceGame(MainFrame m) {
		mainFrame = m;
		addEvtMouseIMG();
		addEvtSelectLevel();
		addEvtClickGoBack();
		gotoHallInterface();
//		switchInterface(1);
	}
	public void addEvtMouseIMG() {
		hallPanel.btnStart.addMouseListener(mouseIMG);
		hallPanel.btnHighScore.addMouseListener(mouseIMG);
		hallPanel.btnIntro.addMouseListener(mouseIMG);
		
		selectPanel.btnEasy.addMouseListener(mouseIMG);
		selectPanel.btnNormal.addMouseListener(mouseIMG);
		selectPanel.btnHard.addMouseListener(mouseIMG);
		
		controlPanel.btnHint.addMouseListener(mouseIMG);
		controlPanel.btnMenu.addMouseListener(mouseIMG);
		controlPanel.btnUndo.addMouseListener(mouseIMG);
		controlPanel.btnClear.addMouseListener(mouseIMG);
		
		scorePanel.btnGoBack.addMouseListener(mouseIMG);
	}
	public void addEvtSelectLevel() {
		selectPanel.btnHard.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				selectPanel.setVisible(false);
				gotoBoardInterface(gamePanel.game.DIF);
				level = new String("hard");
			}
		});
		selectPanel.btnNormal.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				selectPanel.setVisible(false);
				gotoBoardInterface(gamePanel.game.MED);
				level = new String("normal");
			}
		});
		selectPanel.btnEasy.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				selectPanel.setVisible(false);
				gotoBoardInterface(gamePanel.game.EASY);
				level = new String("easy");
			}
		});
	}

//	public void switchInterface(int number) {
//		switch (number) {
//			case 1:
//				System.out.println("Đi đến giao diện sảnh");
//				controlPanel.setVisible(false);
//				gamePanel.setVisible(false);
//				selectPanel.setVisible(false);
//				scorePanel.setVisible(false);
//
//				hallPanel.setVisible(true);
//				hallPanel.btnStart.addMouseListener(mouseIMG);
//				hallPanel.btnHighScore.addMouseListener(mouseIMG);
//				hallPanel.btnIntro.addMouseListener(mouseIMG);
//				mainFrame.add(hallPanel, BorderLayout.CENTER);
//				mainFrame.setVisible(true);
//
//				addEvtClickStartGame();
//				addEvtClickHighScore();
//				break;
//			case 2:
//				System.out.println("Đi đến giao diện lựa chọn cấp độ");
//				controlPanel.setVisible(false);
//				gamePanel.setVisible(false);
//				selectPanel.setVisible(true);
//				scorePanel.setVisible(false);
//				hallPanel.setVisible(false);
//				
//				hallPanel.btnStart.removeMouseListener(mouseIMG);
//				hallPanel.btnHighScore.removeMouseListener(mouseIMG);
//				hallPanel.btnIntro.removeMouseListener(mouseIMG);
//				
//				selectPanel.btnEasy.addMouseListener(mouseIMG);
//				selectPanel.btnNormal.addMouseListener(mouseIMG);
//				selectPanel.btnHard.addMouseListener(mouseIMG);
//				mainFrame.add(selectPanel, BorderLayout.CENTER);
//				
//				selectLevel();
//				break;
//			case 3:
//				System.out.println("Đi đến giao diện bảng xếp hạng");
//				break;
//			case 4:
//				System.out.println("Đi đến giao diện giời thiệu");
//				break;
//	
//			default:
//				System.out.println("Chạy vào khối default");
//				break;
//		}
//	}
	public void gotoHallInterface() {
		controlPanel.setVisible(false);
		gamePanel.setVisible(false);
		selectPanel.setVisible(false);
		scorePanel.setVisible(false);
		hallPanel.setVisible(true);
		mainFrame.add(hallPanel, BorderLayout.CENTER);
		mainFrame.setVisible(true);

		addEvtClickStartGame();
		addEvtClickHighScore();
	}
	/* Lua chon cap do */
	public void gotoLevelInterface() {
		hallPanel.setVisible(false);
		selectPanel.setVisible(true);
		mainFrame.add(selectPanel, BorderLayout.CENTER);
	}
	/* Them su kien khi click vao start game */
	public void addEvtClickStartGame() {
		hallPanel.btnStart.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				gotoLevelInterface();
			}
		});
	}

	public void gotoBoardInterface(int level) {
		controlPanel.btnClear.setEnabled(true);
		controlPanel.btnHint.setEnabled(true);
		controlPanel.btnMenu.setEnabled(true);
		controlPanel.btnUndo.setEnabled(true);

		/* Bat dau tinh thoi gian choi game */
		resetTimer();
		startTimer();
		removeEvtClickNumber();
		removeEvtGamePanel();
		removeEvtControlPanel();

		/* Khoi tao ban co sudoku */
		boardQuestion = new Board();
		boardQuestion.generate(boardQuestion, 0);
		boardQuestion.selectLevel(boardQuestion, level);
		boardQuestion.showBoard();

		/* Luu lai ban co ban dau sau khi khoi tao vao gamePanel */
		gamePanel.game = new Board(boardQuestion);
		getVal(gamePanel.game);

		/* Them sư kien */
		addEvtBlankCell(gamePanel.game);
		addEvtClickMenu();
//		addEvtClickSolve();
		addEvtClickClear();
		addEvtClickUndo();
		addEvtClickHint();
		countHint = 0;

		controlPanel.setVisible(true);
		gamePanel.setVisible(true);

		BorderLayout layout = new BorderLayout();
		mainFrame.setLayout(layout);
		mainFrame.add(gamePanel, BorderLayout.CENTER);
		mainFrame.add(controlPanel, BorderLayout.EAST);
	}

	/* Lay gia tri tu mang 9x9 cho vao giao dien */
	public void getVal(Board boardSrc) {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				int idxCellBig = (i / 3) * 3 + (j / 3);
				int idxCell = (i % 3) * 3 + (j % 3);
				gamePanel.jpCellBig[idxCellBig].btnCellBig[idxCell].setText(boardSrc.getMatrix()[i][j].val + "");
				if (boardSrc.getMatrix()[i][j].val == 0)
					gamePanel.jpCellBig[idxCellBig].btnCellBig[idxCell].setText("");
			}
		}
	}

	/* Reset lai thoi gian */
	public void resetTimer() {
		time.cancel();
		time = new Timer();
		second = 0;
		value = "00:00";
		controlPanel.lblTime.setText(value);
	}

	/* Bat dau tinh thoi gian */
	public void startTimer() {
		time.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				second++;
				value = second / 60 + ":" + second % 60;
				controlPanel.lblTime.setText(value);
			}
		}, 1000, 1000);
	}

	/* Sau khi chien thang thuc hien luu diem */
	public void saveWin() {
		time.cancel();
		System.out.println("YOU WIN");
		JOptionPane.showMessageDialog(gamePanel, "YOU WIN");
		int select = JOptionPane.showConfirmDialog(this, "Save score ?", "Save Score", JOptionPane.YES_NO_OPTION);
		if (select == 0) {
			String name = JOptionPane.showInputDialog("Please enter your name here");
			Player.insertPlayer(name, second, level);
		}
		gotoHallInterface();
	}

	/* Them su kien cho cac o trong */
	public void addEvtBlankCell(Board board) {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				int idxCellBig = (i / 3) * 3 + (j / 3);
				int idxCell = (i % 3) * 3 + (j % 3);
				/* Neu la o trong */
				if (board.getMatrix()[i][j].val == 0) {
					gamePanel.jpCellBig[idxCellBig].btnCellBig[idxCell].setForeground(new Color(1, 26, 39));
					gamePanel.jpCellBig[idxCellBig].btnCellBig[idxCell].setBackground(Color.WHITE);
					gamePanel.jpCellBig[idxCellBig].btnCellBig[idxCell].setI(i);
					gamePanel.jpCellBig[idxCellBig].btnCellBig[idxCell].setJ(j);
					gamePanel.jpCellBig[idxCellBig].btnCellBig[idxCell].addMouseListener(mouse);
					gamePanel.jpCellBig[idxCellBig].btnCellBig[idxCell].addActionListener(click);
				}
				/* Neu khong phai o trong */
				else {
					gamePanel.jpCellBig[idxCellBig].btnCellBig[idxCell].setForeground(Color.WHITE);
					gamePanel.jpCellBig[idxCellBig].btnCellBig[idxCell].setBackground(new Color(6, 56, 82));
				}
			}
		}
	}

	public void addEvtClickHighScore() {
		hallPanel.btnHighScore.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				hallPanel.setVisible(false);
				scorePanel.setVisible(true);
				mainFrame.add(scorePanel, BorderLayout.CENTER);
			}
		});
	}

	/* Them su kien click vao undo */
	public void addEvtClickUndo() {
		controlPanel.btnUndo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JButtonCellBig btnCell=new JButtonCellBig();
				btnCell=stackValue.getData()[stackValue.getTop()];
				if (!stackValue.empty()) {
					btnCell.setText("");
					gamePanel.game.getMatrix()[btnCell.getI()][btnCell.getJ()].val=0;
					stackValue.pop();
				}
			}
		});
	}

	/* Them su kien khi click vao menu */
	public void addEvtClickMenu() {
		controlPanel.btnMenu.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {			
				resetTimer();
				removeEvtGamePanel();
				removeEvtControlPanel();
				new ControlGame();
				gotoHallInterface();
			}
		});
	}

	public void addEvtClickGoBack() {
		scorePanel.btnGoBack.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				scorePanel.setVisible(false);
				hallPanel.setVisible(true);
			}
		});
	}

//	/* Them su kien khi click vao solve */
//	public void addEvtClickSolve() {
//		controlPanel.btnSolve.addActionListener(new ActionListener() {
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				controlPanel.btnSolve.setEnabled(false);
//				time.cancel();
//				/* Giai ban co va luu lai */
//				boardAnswer = new Board(boardQuestion);
//				boardAnswer.solveGame(boardAnswer);
//				boardAnswer.showBoard();
//				getVal(boardAnswer);
//			}
//		});
//	}
	
	/* Them su kien khi click vao play again */
	public void addEvtClickClear() {
		controlPanel.btnClear.addActionListener(new ActionListener() {
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
	
	/* Them su kien ho tro giai sau khi da click vao o so trong ban co sudoku */
	public void addEvtClickHint() {
		if (countHint <= 3)
			controlPanel.btnHint.addActionListener(clickHint);
	}

	/* Them su kien chon mot so tren giao dien de dien vao ban co sudoku */
	public void addEvtClickNum(int i, int j) {
		for (int x = 0; x < 9; x++) {
			clickNumber.i = i;
			clickNumber.j = j;
			controlPanel.btnNumber[x].addMouseListener(mouse);
			controlPanel.btnNumber[x].addActionListener(clickNumber);
		}
	}

	/* Xoa su kien chon mot so tren giao dien de dien vao ban co sudoku */
	public void removeEvtClickNumber() {
		for (int x = 0; x < 9; x++) {
			controlPanel.btnNumber[x].removeActionListener(clickNumber);
			controlPanel.btnNumber[x].removeMouseListener(mouse);
			controlPanel.btnNumber[x].setBackground(Color.WHITE);
		}
	}

	/* Xoa su kien re chuot vao o trong ban co sudoku */
	public void removeEvtGamePanel() {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				int idxCellBig = (i / 3) * 3 + (j / 3);
				int idxCell = (i % 3) * 3 + (j % 3);
				gamePanel.jpCellBig[idxCellBig].btnCellBig[idxCell].removeMouseListener(mouse);
				gamePanel.jpCellBig[idxCellBig].btnCellBig[idxCell].removeActionListener(click);
				gamePanel.jpCellBig[idxCellBig].btnCellBig[idxCell].setBackground(Color.WHITE);
			}
		}
	}

	public void removeEvtControlPanel() {
		removeEvtClickNumber();
		controlPanel.btnHint.removeActionListener(clickHint);	
	}

	/* Su kien re chuot vao button hinh anh */
	public class ButtonImageMouseListener implements MouseListener {
		@Override
		public void mouseReleased(MouseEvent e) {}

		@Override
		public void mousePressed(MouseEvent e) {}

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
		public void mouseClicked(MouseEvent e) {}
	}

	/* Su kien re chuot trong ban co sudoku */
	public class ButtonMouseListener implements MouseListener {
		@Override
		public void mouseReleased(MouseEvent e) {}

		@Override
		public void mousePressed(MouseEvent e) {}

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
		public void mouseClicked(MouseEvent e) {}
	}

	/* Su kien click chuot trong ban co sudoku */
	public class ButtonClickListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			JButtonCellBig btnClicked = (JButtonCellBig) e.getSource();
			btnClicked.setBackground(new Color(240, 129, 15));
			btnClicked.removeMouseListener(mouse);
			if (!stackUndo.empty())
				stackUndo.getData()[stackUndo.getTop()].setBackground(Color.WHITE);
			stackUndo.push(btnClicked);

			System.out.println(btnClicked.getI() + "," + btnClicked.getJ());
			btnClicked.addKeyListener(key);
			addEvtClickNum(btnClicked.getI(), btnClicked.getJ());
			clickHint.i = btnClicked.getI();
			clickHint.j = btnClicked.getJ();
		}
	}

	/* Su kien goi y loi giai sau khi click chuot vao mot o trong ban co sudoku */
	public class ButtonClickHintListener implements ActionListener {
		int i = -1, j = -1;

		@Override
		public void actionPerformed(ActionEvent e) {
			JButtonImage btnClicked = (JButtonImage) e.getSource();
			btnClicked.setBackground(new Color(240, 129, 15));
			System.out.println("CountHint: " + countHint);
			if (i != -1 && j != -1) {
				int idxCellBig = (i / 3) * 3 + (j / 3);
				int idxCell = (i % 3) * 3 + (j % 3);
				boardAnswer = new Board(boardQuestion);
				boardAnswer.solveGame(boardAnswer);
				boardAnswer.showBoard();
				gamePanel.jpCellBig[idxCellBig].btnCellBig[idxCell].setText(boardAnswer.getMatrix()[i][j].val + "");
				countHint++;
				System.out.println("CountHint: " + countHint);
			}
		}
	}

	/* Su kien chon so sau khi click chuot vao mot o trong ban co sudoku */
	public class ButtonClickNumberListener implements ActionListener {
		public int i = 0, j = 0;

		@Override
		public void actionPerformed(ActionEvent e) {
			JButton btnClicked = (JButton) e.getSource();
			btnClicked.setBackground(new Color(240, 129, 15));

			/* Lay gia tri so duoc chon */
			controlPanel.valClicked = Integer.parseInt(btnClicked.getText());

			int idxCellBig = (i / 3) * 3 + (j / 3);
			int idxCell = (i % 3) * 3 + (j % 3);

			if (controlPanel.valClicked != 0) {
				gamePanel.game.getMatrix()[i][j].val = controlPanel.valClicked;

				/* Neu so nhap vao khong hop le thi se thong bao trung lap */
				if (!gamePanel.game.isValid(gamePanel.game, i, j)) {
					gamePanel.jpCellBig[idxCellBig].btnCellBig[idxCell].setBackground(Color.RED);
					System.out.println("TRUNG LAP");
				} else {
					gamePanel.jpCellBig[idxCellBig].btnCellBig[idxCell].setText(controlPanel.valClicked + "");
					gamePanel.game.getMatrix()[i][j].val = controlPanel.valClicked;
					gamePanel.game.showBoard();
					stackValue.push(gamePanel.jpCellBig[idxCellBig].btnCellBig[idxCell]);

					/* Neu nhu tat ca cac o da dien deu khong bi trung lap thi chien thang */
					if (gamePanel.game.checkFinish(gamePanel.game))
						saveWin();
				}
			}
			controlPanel.setValClicked(0);
			removeEvtClickNumber();
		}
	}

	/* Su kien nhap so tu ban phim sau khi click chuot vao mot o trong ban co sudoku */
	public class ButtonKeyListener implements KeyListener {
		@Override
		public void keyTyped(KeyEvent e) {}

		@Override
		public void keyReleased(KeyEvent e) {}

		@Override
		public void keyPressed(KeyEvent e) {
			JButtonCellBig btnKeyPressed = (JButtonCellBig) e.getSource();
			/* Lay i va j duoc click */
			int i = btnKeyPressed.getI();
			int j = btnKeyPressed.getJ();

			System.out.println(btnKeyPressed.getI() + "," + btnKeyPressed.getJ());
			gamePanel.game.getMatrix()[i][j].val = 0;
			int v = e.getKeyCode();
			
			/* Neu ki tu nhap vao la so */
			if ((v >= 49 && v <= 57) || (v >= 97 && v <= 105)) {
				if (v >= 49 && v <= 57)
					v -= 48;
				if (v >= 97 && v <= 105)
					v -= 96;
				gamePanel.game.getMatrix()[i][j].val = v;

				/* Neu so nhap vao khong hop le thi se thong bao trung lap */
				if (!gamePanel.game.isValid(gamePanel.game, i, j)) {
					btnKeyPressed.setBackground(Color.RED);
					System.out.println("TRUNG LAP");
					
					int xExist=gamePanel.game.getCellExist().getRow();
					int yExist=gamePanel.game.getCellExist().getCol();
					int idxCellBig = (xExist / 3) * 3 + (yExist / 3);
					int idxCell = (xExist % 3) * 3 + (yExist % 3);
					gamePanel.jpCellBig[idxCellBig].btnCellBig[idxCell].setBackground(Color.RED);
					gamePanel.game.getMatrix()[i][j].val=0;
					gamePanel.game.showBoard();
				}
				/* Neu so nhap vao la hop le */
				else {
					btnKeyPressed.setText(v + "");
					gamePanel.game.getMatrix()[i][j].val = v;
					gamePanel.game.showBoard();
					stackValue.push(btnKeyPressed);
				}
			}
			/* Neu ki tu nhap vao khong phai la so */
			else {
				btnKeyPressed.setBackground(Color.RED);
				System.out.println("HAY NHAP SO");
				JOptionPane.showMessageDialog(gamePanel, "HAY NHAP SO");
			}
			/* Neu tat ca deu da co so thi chien thang */
			if (gamePanel.game.checkFinish(gamePanel.game))
				saveWin();
		}
	}
}
