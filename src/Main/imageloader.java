package Main;

import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class imageloader {

	public static ImageIcon loadImage(String path) {
		Image img = null;
		
		try {
			img = ImageIO.read(main.class.getResource(path));
		}catch(IOException e) {
			e.printStackTrace();
		}
		
		
		
		return new ImageIcon(img);
		
	}
	
}
