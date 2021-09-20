package piece;

import javax.swing.ImageIcon;

import com.sun.tools.javac.util.ArrayUtils;

import main.Location;
import main.Movement;
import main.main;
import main.util;

public class Queen extends Piece{

	public Queen(int color, Location loc) {		
		super("Dame", color, loc, color == 0 ? main.queen_white_img : main.queen_black_img, 5);
	}
	
	@Override
	public Location[] getPossibleMoves() {
		return util.mergeLocations(Movement.getHorizontalMoves(this), Movement.getVerticalMoves(this));
	}

}