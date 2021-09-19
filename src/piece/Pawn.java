package piece;

import javax.swing.ImageIcon;

import main.Location;
import main.main;

public class Pawn extends Piece{

	public Pawn(int color, Location loc) {		
		super("Bauer", color, loc, color == 0 ? main.pawn_white_img : main.pawn_black_img, 1);
	}

}
