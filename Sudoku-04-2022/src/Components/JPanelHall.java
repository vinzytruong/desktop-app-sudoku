package Components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import View.MainFrame;

public class JPanelHall extends JPanel{
	public JButtonImage btnStart;
	public JButtonImage btnHighScore;
	public JButtonImage btnIntro;
	public JLabel lblNameGame;
	public JLabel lblAbout;
	public JPanel menuPanel;
	public JPanelHall() {
		prepareUI();
	}

	public void prepareUI() {
		btnStart=new JButtonImage();
		btnStart.createButton("../image/btnStartFalse.png",200,60);
		
		btnHighScore=new JButtonImage();
		btnHighScore.createButton("../image/btnHighScoreFalse.png",200,60);
		
		btnIntro=new JButtonImage();
		btnIntro.createButton("../image/btnIntroFalse.png",200,60);

		menuPanel=new JPanel();
		menuPanel.setPreferredSize(new Dimension(300,500));
		menuPanel.setLayout(new GridLayout(3,1,0,10));
		menuPanel.setBorder(new EmptyBorder(new Insets(170,40,70,20)));
		menuPanel.setOpaque(false);
		menuPanel.add(btnStart);
		menuPanel.add(btnHighScore);
		menuPanel.add(btnIntro);
		
		JPanel right=new JPanel();
		right.setPreferredSize(new Dimension(400,500));
		right.setOpaque(false);
		
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		this.add(menuPanel);
		this.add(right);
	}
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d=(Graphics2D) g;

		Image imgBackground=Toolkit.getDefaultToolkit().getImage(getClass().getResource("../image/background.png"));
		g2d.drawImage(imgBackground,0,0,700,480,this);

		Image imgNameGame=Toolkit.getDefaultToolkit().getImage(getClass().getResource("../image/textSudoku.png"));
		g2d.drawImage(imgNameGame,0,0,480,200,this);
	}
	public static void main(String[] args) {
		JFrame jframe = new JFrame("Game Sudoku");
		jframe.setPreferredSize(new Dimension(650,450));
		JPanelHall m=new JPanelHall();
		jframe.add(m,BorderLayout.CENTER);
		jframe.setResizable(false);
		jframe.setVisible(true);
		jframe.pack();
	}
}
