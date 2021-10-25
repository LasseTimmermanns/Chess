package piece;

import main.Location;
import main.Movement;
import main.main;

public class Knight extends Piece{

	public Knight(int color, Location loc) {		
		super("Springer", color, loc, color == 0 ? main.knight_white_img : main.knight_black_img, 3);
	}
	
	@Override
	public void updateCoverings() {
		super.coverings = Movement.getKnightMoves(this, true);
	}
	
	@Override
	public void updatePossibleMoves() {
		super.possibleMoves = Movement.getKnightMoves(this, false);
	}
	

}
