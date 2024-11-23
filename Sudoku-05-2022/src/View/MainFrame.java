package View;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.JFrame;
import Controller.ControlGame;
import java.io.IOException;

public class MainFrame extends JFrame {

    public JPanelHall jpanelHall;
    public JPanelControl jpanelControl;
    public JPanelLevel jpanelLevel;
    public JPanelBoard jpanelBoard;
    public JPanelScore jpanelScore;
    public JPanelScore jpanelIntro;
    private static final int weight = 700;
    private static final int height = 500;
    private ControlGame ctrl;

    public MainFrame() throws IOException {
        prepareUI();
    }

    private void prepareUI() throws IOException {
        Image image = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/Image/icon.png"));
        System.out.println(getClass().getResource("/Image/icon.png"));
        this.setIconImage(image);
        this.setLocation((getWeightScreen() - weight) / 2, (getHeightScreen() - height) / 2);
        this.setPreferredSize(new Dimension(weight, height));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("SUDOKU");
        this.setResizable(false);
        this.setVisible(true);
        this.pack();
        ctrl = new ControlGame();
        ctrl.controlUI(this);
        

    }

    /*Lay chieu rong man hinh*/
    public int getWeightScreen() {
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        return (int) d.getWidth();
    }

    /*Lay chieu cao man hinh*/
    public int getHeightScreen() {
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        return (int) d.getHeight();
    }

    public static int getWeightFrame() {
        return weight;
    }

    public static int getHeightFrame() {
        return height;
    }

    public static void main(String[] args) throws IOException {
        new MainFrame();
    }
}
