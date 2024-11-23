package View;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class JPanelArea extends JPanel {
	public JButtonCell btnCell[];
	
	public JPanelArea() {
		prepareUI();
	}
	
	public void prepareUI() {
		btnCell=new JButtonCell[9];
		for(int i=0;i<9;i++) btnCell[i]=new JButtonCell();
		this.setBackground(new Color(1,26,39));
		this.setLayout(new GridLayout(3,3,1,1));
		for(int i=0;i<9;i++)
			this.add(btnCell[i]);
	}
}
