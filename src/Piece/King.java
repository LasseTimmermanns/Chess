package Piece;

import Main.main;
import Movement.Location;
import Movement.Generator.KingMoves;

public class King extends Piece{

	public King(int color, Location loc) {		
		super(Piece.TYPE_KING, "King", color, loc, color == 0 ? main.king_white_img : main.king_black_img, 1000);
	}
	
	@Override
	public void updateCoverings() {
		super.coverings = KingMoves.get(this, true);
	}
	
	@Override
	public void updateRawMoves() {
		super.rawMoves = KingMoves.get(this, false);
	}

}
