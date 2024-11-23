package View;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JButton;

public class JButtonCell extends JButton {
    private int i, j;

    public JButtonCell() {
        this.setBackground(new Color(1, 26, 39));
        this.setBorderPainted(false);
        this.setFont(new Font("Play", Font.BOLD, 14));
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
