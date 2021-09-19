package piece;

import javax.swing.ImageIcon;

import main.Location;
import main.main;

public class Knight extends Piece{

	public Knight(int color, Location loc) {		
		super("Springer", color, loc, color == 0 ? main.knight_white_img : main.knight_black_img, 3);
	}

}
