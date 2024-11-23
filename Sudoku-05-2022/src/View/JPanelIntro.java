package View;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

public class JPanelIntro extends JPanel {
	public JTable table;
	public JScrollPane scpScroller;
	public JTextPane txtPane;
	public JButtonImage btnGoBack;
	public JLabel lblNameGame;

	public JPanelIntro(String contentTxpPane) {
		prepareUI(contentTxpPane);
	}

	public void prepareUI(String contentTxpPane) {
		txtPane = new JTextPane();
		scpScroller = new JScrollPane();
		btnGoBack = new JButtonImage();
		btnGoBack.createButton("/Image/btnBackFalse.png", 100, 40);
		
//		Font font = createFont(getClass().getResource("/Data/Play-Bold.ttf"));

		/* Chuan bi cac thuoc tinh cho text */
		SimpleAttributeSet attributes = new SimpleAttributeSet();
		StyledDocument doc1 = txtPane.getStyledDocument();
//		StyleConstants.setFontFamily(attributes, font.getFontName());
		StyleConstants.setFontFamily(attributes, Font.SANS_SERIF);
		StyleConstants.setFontSize(attributes, 13);
		StyleConstants.setAlignment(attributes, StyleConstants.ALIGN_JUSTIFIED);
		StyleConstants.setForeground(attributes, new Color(6, 56, 82));
		StyleConstants.setLineSpacing(attributes, 0.6f);
		doc1.setParagraphAttributes(0, doc1.getLength(), attributes, false);

		/* Chuan bi cac thuoc tinh cho text */
		SimpleAttributeSet attributes1 = new SimpleAttributeSet();
		StyleConstants.setBold(attributes1, true);

		/* Ap dung thuoc tinh cho text nhap vao */
		try {
			doc1.insertString(doc1.getLength(), contentTxpPane, attributes);
			doc1.setCharacterAttributes(0, 8, attributes1, false);
			doc1.setCharacterAttributes(17, 13, attributes1, false);
			doc1.setCharacterAttributes(47, 10, attributes1, false);
			doc1.setCharacterAttributes(67, 9, attributes1, false);
			doc1.setCharacterAttributes(90, 8, attributes1, false);
			doc1.setCharacterAttributes(103, 9, attributes1, false);
			doc1.setCharacterAttributes(130, 13, attributes1, false);
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
		this.add(btnGoBack);
		txtPane.setEditable(false);
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		Image imgBackground = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/Image/background.png"));
		g2d.drawImage(imgBackground, 0, 0, 700, 480, this);
		Image imgLeaderBoard = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/Image/imgIntro.png"));
		g2d.drawImage(imgLeaderBoard, 191, 20, 320, 430, this);
		btnGoBack.setBounds(10, 10, 100, 40);
		txtPane.setBounds(0, 0, 280, 322);
		txtPane.setOpaque(false);
		scpScroller.setViewportView(txtPane);
		scpScroller.setBounds(210, 108, 280, 322);
		this.add(scpScroller);
	}
	/* Tao font moi tu file */
	public Font createFont(URL url) {
		InputStream file = null;
		Font font = null;
		try {
			file = new FileInputStream(url.getPath());
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		try {
			font = Font.createFont(Font.TRUETYPE_FONT, file);
		} catch (FontFormatException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return font;
	}
}
