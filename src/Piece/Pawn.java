package Piece;

import Main.main;
import Movement.Location;
import Movement.Generator.PawnMoves;

public class Pawn extends Piece{

	private final int direction;
	
	public Pawn(int color, Location loc) {		
		super(Piece.TYPE_PAWN, "Bauer", color, loc, color == main.COLOR_WHITE ? main.pawn_white_img : main.pawn_black_img, 1);
		direction = color == main.COLOR_WHITE ? main.COLOR_WHITE_DIRECTION : main.COLOR_BLACK_DIRECTION;
	}
	
	public int getDirection() {
		return direction;
	}
	
	@Override
	public void updateCoverings() {
		super.coverings = PawnMoves.get(this, true);
	}
	
	@Override
	public void updateRawMoves() {
		super.rawMoves = PawnMoves.get(this, false);
	}

}
