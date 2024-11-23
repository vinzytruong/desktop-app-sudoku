package View;

import java.awt.Color;
import java.awt.Dimension;
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
import java.util.ArrayList;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import Model.Player;

public class JPanelScore extends JPanel {

    public JTable table;
    public JScrollPane sp;
    public JButtonImage btnGoBack;

    public JPanelScore() {
        prepareUI();
    }

    public void prepareUI() {
        btnGoBack = new JButtonImage();
        btnGoBack.createButton("/Image/btnBackFalse.png", 100, 40);

        table = new JTable();
        sp = new JScrollPane(sp, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        sp.setBackground(new Color(6, 56, 82));
        sp.setViewportView(table);

        this.add(btnGoBack);
        this.add(sp);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        Image imgBackground = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/Image/background.png"));
        g2d.drawImage(imgBackground, 0, 0, 700, 480, this);

        Image imgLeaderBoard = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/Image/imgLeaderBoard.png"));
        g2d.drawImage(imgLeaderBoard, 191, 20, 320, 430, this);

        btnGoBack.setBounds(10, 10, 100, 40);

        DefaultTableModel m = new DefaultTableModel();
        m.setColumnIdentifiers(new Object[]{"Name", "Second", "Level"});
        Player sc = new Player();
        List<Player> dsPlayer = new ArrayList<>();
        List<Player> dsPlayer1 = new ArrayList<>();
        List<Player> dsPlayer2 = new ArrayList<>();
        dsPlayer = sc.getTop3Player("easy");
        dsPlayer1 = sc.getTop3Player("normal");
        dsPlayer2 = sc.getTop3Player("hard");

        for (int i = 0; i < dsPlayer.size(); i++) {
            m.addRow(new Object[]{dsPlayer.get(i).getName(), dsPlayer.get(i).getTime(), "easy"});
        }

        for (int i = 0; i < dsPlayer1.size(); i++) {
            m.addRow(new Object[]{dsPlayer1.get(i).getName(), dsPlayer1.get(i).getTime(), "normal"});
        }

        for (int i = 0; i < dsPlayer2.size(); i++) {
            m.addRow(new Object[]{dsPlayer2.get(i).getName(), dsPlayer2.get(i).getTime(), "hard"});
        }

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        
        Font font = createNewFont();

        table.setModel(m);
        table.setRowHeight(31);
        table.setBackground(Color.WHITE);
        table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
        table.setForeground(new Color(6, 56, 82));
	table.setFont(new Font(font.getFontName(), Font.BOLD, 13));
        table.getColumnModel().getColumn(0).setWidth(100);
        table.getColumnModel().getColumn(1).setWidth(100);
        table.getColumnModel().getColumn(2).setWidth(80);
        table.setFillsViewportHeight(true);
        table.setEnabled(false);
        table.setShowGrid(true);
        table.setBounds(0, 0, 280, 310);

        JTableHeader header = table.getTableHeader();
        header.setForeground(new Color(6, 56, 82));
        header.setFont(new Font(font.getFontName(), Font.BOLD, 16));
        header.setEnabled(false);
        header.setPreferredSize(new Dimension(280, 31));

        sp.setBounds(210, 111, 280, 310);
    }

    /* Tao font moi tu file */
    public Font createNewFont() {
        InputStream file = null;
        Font font = null;
        try {
            file = new FileInputStream("Play-Bold.ttf");
        } catch (FileNotFoundException e1) {
            System.out.println("Error get file to create font");
        }
        try {
            font = Font.createFont(Font.TRUETYPE_FONT, file);
        } catch (FontFormatException | IOException e1) {
            System.out.println("Error create font");
        }
        return font;
    }
}
