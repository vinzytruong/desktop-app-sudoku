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
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import Model.Player;

public class JPanelScore extends JPanel{
	public JTable table;
	public JScrollPane sp;
	public JButtonImage btnGoBack;
	public JLabel lblNameGame;
	public JPanelScore() {
		prepareUI();
	}
	public void prepareUI() {
		table=new JTable();
		sp=new JScrollPane();
		
		lblNameGame=new JLabel("SUDOKU",SwingConstants.CENTER);
		lblNameGame.setForeground(Color.WHITE);
		lblNameGame.setFont(new Font("Play",Font.BOLD,24));
		
		btnGoBack=new JButtonImage();
		btnGoBack.createButton("../image/btnBackFalse.png",100,40);
		
//		this.setBackground(new Color(1,26,39));
		this.add(btnGoBack);
//		this.add(lblNameGame);
	}
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d=(Graphics2D) g;
		
		Image imgBackground=Toolkit.getDefaultToolkit().getImage(getClass().getResource("../image/background.png"));
		g2d.drawImage(imgBackground,0,0,700,480,this);
		
		Image imgLeaderBoard=Toolkit.getDefaultToolkit().getImage(getClass().getResource("../image/imgLeaderBoard.png"));
		g2d.drawImage(imgLeaderBoard,190,0,320,430,this);
		
//		g2d.setColor(Color.WHITE);
//		g2d.fillRect(175,65,300,300);
//		g2d.setColor(new Color(6,56,82));
//		g2d.fillRect(210,30,230,60);
		
		btnGoBack.setBounds(10, 10, 100, 40);
		
		lblNameGame.setBounds(175,30,300,60);
		
		
		DefaultTableModel m=new DefaultTableModel();
		m.setColumnIdentifiers(new Object[] {"Name","Second","Level"});
        Player sc=new Player();
        List<Player> dsPlayer=new ArrayList<>();
        List<Player> dsPlayer1=new ArrayList<>();
        List<Player> dsPlayer2=new ArrayList<>();
        dsPlayer=sc.getTop3Player("easy");
        dsPlayer1=sc.getTop3Player("normal");
        dsPlayer2=sc.getTop3Player("hard");
        
        	
//        m.setColumnIdentifiers();
        for(int i=0;i<dsPlayer.size();i++) {
        	m.addRow(new Object[] {dsPlayer.get(i).getName(),dsPlayer.get(i).getTime(),"easy"});	
        }
        for(int i=0;i<dsPlayer1.size();i++) {
        	m.addRow(new Object[] {dsPlayer1.get(i).getName(),dsPlayer1.get(i).getTime(),"normal"});	
        }
        for(int i=0;i<dsPlayer2.size();i++) {
        	m.addRow(new Object[] {dsPlayer2.get(i).getName(),dsPlayer2.get(i).getTime(),"hard"});	
        }
        m.addRow(new Object[] {"","",""});
//        table.setAutoCreateRowSorter(true);
        table.setModel(m);
		table.setRowHeight(30);
		table.setBackground(Color.WHITE);
//		table.setRowColor(1, Color.YELLOW);
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment( JLabel.CENTER );
		table.getColumnModel().getColumn(0).setCellRenderer( centerRenderer );
		table.getColumnModel().getColumn(1).setCellRenderer( centerRenderer );
		table.getColumnModel().getColumn(2).setCellRenderer( centerRenderer );
		table.setForeground(new Color(6,56,82));
		table.setFont(new Font("Play",Font.PLAIN,14));
//		table.setGridColor(new Color(1,26,39));
		table.getColumnModel().getColumn(0).setWidth(100);
		table.getColumnModel().getColumn(1).setWidth(100);
		table.getColumnModel().getColumn(2).setWidth(80);
		table.setOpaque(false);
		table.setFillsViewportHeight(true);
		table.setEnabled(false);
		table.setShowGrid(true);
		JTableHeader header = table.getTableHeader();
		header.setForeground(new Color(6,56,82));
		header.setFont(new Font("Play",Font.BOLD,16));
		header.setEnabled(false);
		header.setPreferredSize(new Dimension(280,30));
		
		sp.setViewportView(table);
		sp.setBounds(210, 100, 280, 303);
		this.add(sp);
	}
	public static void main(String[] args) {
		JFrame jframe = new JFrame("Game Sudoku");
		jframe.setPreferredSize(new Dimension(650,450));
		JPanelScore m=new JPanelScore();
		jframe.add(m,BorderLayout.CENTER);
		jframe.setResizable(false);
		jframe.setVisible(true);
		jframe.pack();
	}
}
