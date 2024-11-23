package View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import View.MainFrame;

public class JPanelHall extends JPanel {

    public JButtonImage btnStart;
    public JButtonImage btnHighScore;
    public JButtonImage btnIntro;
    public JLabel lblNameGame;
    public JLabel lblAbout;
    public JPanel jpanelMenu;

    public JPanelHall() {
        prepareUI();
    }

    public void prepareUI() {

        btnStart = new JButtonImage();
        btnStart.createButton("/Image/btnStartFalse.png", 200, 60);

        btnHighScore = new JButtonImage();
        btnHighScore.createButton("/Image/btnHighScoreFalse.png", 200, 60);

        btnIntro = new JButtonImage();
        btnIntro.createButton("/Image/btnIntroFalse.png", 200, 60);

        jpanelMenu = new JPanel();
        jpanelMenu.setPreferredSize(new Dimension(300, 500));
        jpanelMenu.setLayout(new GridLayout(3, 1, 0, 10));
        jpanelMenu.setBorder(new EmptyBorder(new Insets(170, 40, 70, 20)));
        jpanelMenu.setOpaque(false);
        jpanelMenu.add(btnStart);
        jpanelMenu.add(btnHighScore);
        jpanelMenu.add(btnIntro);

        JPanel right = new JPanel();
        right.setPreferredSize(new Dimension(400, 500));
        right.setOpaque(false);

        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        this.add(jpanelMenu);
        this.add(right);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        Image imgBackground = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/Image/background.png"));
        g2d.drawImage(imgBackground, 0, 0, 700, 480, this);

        Image imgNameGame = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/Image/textSudoku.png"));
        g2d.drawImage(imgNameGame, 0, 0, 480, 200, this);
    }
}
