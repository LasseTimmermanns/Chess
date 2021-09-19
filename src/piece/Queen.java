package piece;

import javax.swing.ImageIcon;

import main.Location;
import main.main;

public class Queen extends Piece{

	public Queen(int color, Location loc) {		
		super("Dame", color, loc, color == 0 ? main.queen_white_img : main.queen_black_img, 5);
	}

}