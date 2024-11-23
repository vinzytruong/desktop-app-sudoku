package View;

import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class JButtonImage extends JButton {

    private String addressIMG;
    private int width;
    private int height;

    public JButtonImage() {
        this.addressIMG = new String();
        this.width = 0;
        this.height = 0;
    }

    public void createButton(String linkImg, int width, int height) {
        this.width = width;
        this.height = height;
        this.addressIMG = new String(linkImg);
        URL URLresource = getClass().getResource(this.addressIMG);
        if (URLresource == null) {
            throw new IllegalArgumentException("file not found!");
        } else {
            Image imgIcon = Toolkit.getDefaultToolkit().getImage(URLresource);
            this.setIcon(new ImageIcon(imgIcon.getScaledInstance(this.width, this.height, Image.SCALE_SMOOTH)));
            this.setBorderPainted(false);
            this.setContentAreaFilled(false);
            this.setFocusPainted(false);
        }
    }

    public void changeColor() {
        if (this.addressIMG.contains("False")) {
            this.addressIMG = new String(this.addressIMG.substring(0, this.addressIMG.indexOf("False"))) + "True.png";
            URL URLresource = getClass().getResource(this.addressIMG);
            if (URLresource == null) {
                throw new IllegalArgumentException("file not found!");
            } else {
                Image imgIcon = Toolkit.getDefaultToolkit().getImage(URLresource);
                this.setIcon(new ImageIcon(imgIcon.getScaledInstance(this.width, this.height, Image.SCALE_SMOOTH)));
            }
        } else if (this.addressIMG.contains("True")) {
            this.addressIMG = new String(this.addressIMG.substring(0, this.addressIMG.indexOf("True"))) + "False.png";
            URL URLresource = getClass().getResource(this.addressIMG);
            if (URLresource == null) {
                throw new IllegalArgumentException("file not found!");
            } else {
                Image imgIcon = Toolkit.getDefaultToolkit().getImage(URLresource);
                this.setIcon(new ImageIcon(imgIcon.getScaledInstance(this.width, this.height, Image.SCALE_SMOOTH)));
            }
        }
    }
}
