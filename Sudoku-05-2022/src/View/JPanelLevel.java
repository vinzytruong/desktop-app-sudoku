package View;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class JPanelLevel extends JPanel{
	public JButtonImage btnEasy;
	public JButtonImage btnNormal;
	public JButtonImage btnHard;
	public JLabel lblNameGame;
	public JPanelLevel() {
		prepareUI();
	}
	public void prepareUI() {
		btnEasy=new JButtonImage();
		btnEasy.createButton("/Image/btnEasyFalse.png", 150, 190);
		
		btnNormal=new JButtonImage();
		btnNormal.createButton("/Image/btnNormalFalse.png", 150, 190);
		
		btnHard=new JButtonImage();
		btnHard.createButton("/Image/btnHardFalse.png", 150, 190);
		
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
		
		Image imgBackground=Toolkit.getDefaultToolkit().getImage(getClass().getResource("/Image/background.png"));
		g2d.drawImage(imgBackground,0,0,700,480,this);
		
		Image imgSelectLevel=Toolkit.getDefaultToolkit().getImage(getClass().getResource("/Image/lblSelectLevel.png"));
		g2d.drawImage(imgSelectLevel,70,30,540,160,this);
	}
}
