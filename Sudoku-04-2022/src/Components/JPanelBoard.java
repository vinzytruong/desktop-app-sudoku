package Components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Insets;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Model.Board;

public class JPanelBoard extends JPanel{
	public JPanelCellBig jpCellBig[];
	public Board game;
	private final int width = 500;
	private final int height = 500;
	public JPanelBoard() {
		prepareUI();
	}
	public void prepareUI() {
		this.jpCellBig=new JPanelCellBig[9];
		for(int i=0;i<9;i++) this.jpCellBig[i]=new JPanelCellBig();
		this.setLayout(new GridLayout(3,3,4,4));
		this.setBorder(new EmptyBorder(new Insets(21, 20, 20, 20)));
		this.setBackground(new Color(1,26,39));
		this.setPreferredSize(new Dimension(width, height));
//		this.setOpaque(false);
		for(int i=0;i<9;i++) this.add(jpCellBig[i]);
	}
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d=(Graphics2D) g;
		
		g2d.setColor(Color.WHITE);
		g2d.fillRect(10, 10, width-25, height-50);
		
		g2d.setColor(new Color(1,26,39));
		g2d.fillRect(20, 20, width-46, height-68);
		
	}
	public static void main(String[] args) {
		JFrame frame=new JFrame();
		frame.setSize(450, 450);
		frame.add(new JPanelBoard());
		frame.setVisible(true);
	}
}
