package Piece;

import Main.main;
import Movement.Location;
import Movement.Movegenerator;

public class Pawn extends Piece{

	private final int direction;
	
	public Pawn(int color, Location loc) {		
		super("Bauer", color, loc, color == main.COLOR_WHITE ? main.pawn_white_img : main.pawn_black_img, 1);
		direction = color == main.COLOR_WHITE ? main.COLOR_WHITE_DIRECTION : main.COLOR_BLACK_DIRECTION;
	}
	
	public int getDirection() {
		return direction;
	}
	
	@Override
	public void updateCoverings() {
		super.coverings = Movegenerator.getPawnMoves(this, true);
	}
	
	@Override
	public void updatePossibleMoves() {
		super.possibleMoves = Movegenerator.getPawnMoves(this, false);
	}

}
