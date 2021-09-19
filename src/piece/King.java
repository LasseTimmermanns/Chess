package piece;

import javax.swing.ImageIcon;

import main.Location;
import main.main;

public class King extends Piece{

	public King(int color, Location loc) {		
		super("King", color, loc, color == 0 ? main.king_white_img : main.king_black_img, 1000);
	}

}
