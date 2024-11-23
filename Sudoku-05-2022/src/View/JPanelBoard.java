package View;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Insets;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import Model.Board;

public class JPanelBoard extends JPanel {
	public JPanelArea jpanelArea[];
	public Board game;
	private final int width = 500;
	private final int height = 500;

	public JPanelBoard() {
		prepareUI();
	}

	public void prepareUI() {
//		Font font = createFont(getClass().getResource("/Data/Play-Bold.ttf"));
		this.jpanelArea = new JPanelArea[9];
		for (int i = 0; i < 9; i++)
			this.jpanelArea[i] = new JPanelArea();
		this.setLayout(new GridLayout(3, 3, 4, 4));
		this.setBorder(new EmptyBorder(new Insets(21, 20, 20, 20)));
		this.setBackground(new Color(1, 26, 39));
//		this.setFont(new Font(font.getFontName(), Font.BOLD, 13));
		this.setFont(new Font(Font.SANS_SERIF,Font.BOLD,13));
		this.setPreferredSize(new Dimension(width, height));
		for (int i = 0; i < 9; i++)
			this.add(jpanelArea[i]);
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;

		g2d.setColor(Color.WHITE);
		g2d.fillRect(10, 10, width - 25, height - 50);

		g2d.setColor(new Color(1, 26, 39));
		g2d.fillRect(20, 20, width - 46, height - 68);

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
