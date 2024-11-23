package Components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class JPanelSelectLevel extends JPanel{
	public JButtonImage btnEasy;
	public JButtonImage btnNormal;
	public JButtonImage btnHard;
	public JLabel lblNameGame;
	public JPanelSelectLevel() {
		prepareUI();
	}
	public void prepareUI() {
		btnEasy=new JButtonImage();
		btnEasy.createButton("../image/btnEasyFalse.png", 150, 190);
		
		btnNormal=new JButtonImage();
		btnNormal.createButton("../image/btnNormalFalse.png", 150, 190);
		
		btnHard=new JButtonImage();
		btnHard.createButton("../image/btnHardFalse.png", 150, 190);
		
		this.setLayout(new GridLayout(1, 3, 0, 0));
		this.setBorder(new EmptyBorder(new Insets(170,60,80,60)));
		this.add(btnEasy);
		this.add(btnNormal);
		this.add(btnHard);
	}
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d=(Graphics2D) g;
		
		Image imgBackground=Toolkit.getDefaultToolkit().getImage(getClass().getResource("../image/background.png"));
		g2d.drawImage(imgBackground,0,0,700,480,this);
		
		Image imgSelectLevel=Toolkit.getDefaultToolkit().getImage(getClass().getResource("../image/lblSelectLevel.png"));
		g2d.drawImage(imgSelectLevel,70,30,540,160,this);
	}
	public static void main(String[] args) {
		JFrame jframe = new JFrame("Game Sudoku");
		jframe.setPreferredSize(new Dimension(650,450));
		JPanelSelectLevel m=new JPanelSelectLevel();
		jframe.add(m,BorderLayout.CENTER);
		jframe.setResizable(false);
		jframe.setVisible(true);
		jframe.pack();
	}
}
