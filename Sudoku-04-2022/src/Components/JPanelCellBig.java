package Components;

import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import Model.CellBig;

public class JPanelCellBig extends JPanel {
	public CellBig cellBig;
	public JButtonCellBig btnCellBig[];
	
	public JPanelCellBig() {
		prepareUI();
	}
	
	public void prepareUI() {
		btnCellBig=new JButtonCellBig[9];
		for(int i=0;i<9;i++) btnCellBig[i]=new JButtonCellBig();
//		cellBig=new CellBig();
		this.setBackground(new Color(1,26,39));
//		this.setOpaque(false);
		this.setLayout(new GridLayout(3,3,1,1));
		for(int i=0;i<9;i++) {
			this.add(btnCellBig[i]);
			
		}
	}
	public static void main(String[] args) {
		JFrame frame=new JFrame();
		frame.setSize(270, 270);
		frame.add(new JPanelCellBig());
		frame.setVisible(true);
	}
}
