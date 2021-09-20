package piece;

import javax.swing.ImageIcon;

import main.Location;
import main.Movement;
import main.main;
import main.util;

public class Pawn extends Piece{

	private final int direction;
	
	public Pawn(int color, Location loc) {		
		super("Bauer", color, loc, color == 0 ? main.pawn_white_img : main.pawn_black_img, 1);
		direction = color == 0 ? 1 : -1;
	}
	
	public int getDirection() {
		return direction;
	}
	
	@Override
	public Location[] getPossibleMoves() {
		return Movement.getPawnMoves(this);
	}

}
