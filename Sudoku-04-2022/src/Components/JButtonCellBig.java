package Components;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Timer;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.border.Border;

import View.MainFrame;

public class JButtonCellBig extends JButton{
	private int i,j;
//	public MainFrame main;
//	public Timer time;
	public JButtonCellBig() {
		this.setBackground(new Color(1,26,39));
//		this.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
		this.setBorderPainted(false);
		this.setFont(new Font("Play",Font.BOLD,14));
//		this.setOpaque(false);
		
//		this.setContentAreaFilled(false);
	}
	
	public int getI() {
		return i;
	}
	public void setI(int i) {
		this.i = i;
	}
	public int getJ() {
		return j;
	}
	public void setJ(int j) {
		this.j = j;
	}	
}
