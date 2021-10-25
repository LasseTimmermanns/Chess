package piece;

import main.Location;
import main.Movement;
import main.main;

public class King extends Piece{

	public King(int color, Location loc) {		
		super("King", color, loc, color == 0 ? main.king_white_img : main.king_black_img, 1000);
	}
	
	@Override
	public void updateCoverings() {
		super.coverings = Movement.getKingMoves(this, true);
	}
	
	@Override
	public void updatePossibleMoves() {
		super.possibleMoves = Movement.getKingMoves(this, false);
	}

}
