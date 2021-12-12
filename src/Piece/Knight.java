package Piece;

import Main.main;
import Movement.Location;
import Movement.Generator.KnightMoves;

public class Knight extends Piece{

	public Knight(int color, Location loc) {		
		super(Piece.TYPE_KNIGHT, "Springer", color, loc, color == 0 ? main.knight_white_img : main.knight_black_img, 3);
	}
	
	@Override
	public void updateCoverings() {
		super.coverings = KnightMoves.get(this, true);
	}
	
	@Override
	public void updateRawMoves() {
		super.rawMoves = KnightMoves.get(this, false);
	}
	

}
