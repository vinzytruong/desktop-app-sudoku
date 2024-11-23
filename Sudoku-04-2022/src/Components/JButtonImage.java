package Components;

import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class JButtonImage extends JButton {
	private String addressImg;
	private int width;
	private int height;

	public JButtonImage() {
		this.addressImg = new String();
		this.width = 0;
		this.height = 0;
	}

	public void createButton(String linkImg, int width, int height) {
		this.width = width;
		this.height = height;
		this.addressImg = new String(linkImg);
		System.out.println(this.addressImg);
		URL URLresource = getClass().getResource(this.addressImg);
		if (URLresource == null) {
			throw new IllegalArgumentException("file not found!");
		} else {
			Image imgIcon = Toolkit.getDefaultToolkit().getImage(URLresource);
			this.setIcon(new ImageIcon(imgIcon.getScaledInstance(this.width, this.height, imgIcon.SCALE_SMOOTH)));
//			this.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
			
			this.setBorderPainted(false);
			this.setContentAreaFilled(false);
			this.setFocusPainted(false);
//			this.setOpaque(false);
		}
	}

	public void changeColor() {
		if(this.addressImg.contains("False")) {
			System.out.println(this.addressImg.substring(0, this.addressImg.indexOf("False")));
			this.addressImg=new String(this.addressImg.substring(0, this.addressImg.indexOf("False")))+"True.png";
			URL URLresource = getClass().getResource(this.addressImg);
			if (URLresource == null) throw new IllegalArgumentException("file not found!");
			else {
				Image imgIcon=Toolkit.getDefaultToolkit().getImage(URLresource);
				this.setIcon(new ImageIcon(imgIcon.getScaledInstance(this.width, this.height,imgIcon.SCALE_SMOOTH)));
			}
		} 
		else if(this.addressImg.contains("True")) {
			System.out.println(this.addressImg.substring(0, this.addressImg.indexOf("True")));
			this.addressImg = new String(this.addressImg.substring(0, this.addressImg.indexOf("True"))) + "False.png";
			URL URLresource = getClass().getResource(this.addressImg);
			if (URLresource == null) {
				throw new IllegalArgumentException("file not found!");
			} else {
				Image imgIcon = Toolkit.getDefaultToolkit().getImage(URLresource);
				this.setIcon(new ImageIcon(imgIcon.getScaledInstance(this.width, this.height, imgIcon.SCALE_SMOOTH)));
			}
		}	
	}
}
