package Main;

import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class imageloader {

	
	
	//Bild von Pfad wird geladen
	public static ImageIcon loadImage(String path, boolean flipped) {
		Image img = null;
		
		try {
			img = ImageIO.read(main.class.getResource(path));
			if(flipped) return main.flipImage(img);
		}catch(IOException e) {
			e.printStackTrace();
		}
		
		return new ImageIcon(img);
	}
	
	public static ImageIcon loadImage(String path) {
		return loadImage(path, false);
	}
	
}
