package piece;

import java.util.ArrayList;

import main.Location;
import main.Movement;
import main.main;

public class Queen extends Piece{

	public Queen(int color, Location loc) {		
		super("Dame", color, loc, color == 0 ? main.queen_white_img : main.queen_black_img, 5);
	}
	
	@Override
	public void updateCoverings() {
		ArrayList<Location> moves = Movement.getStraightMoves(this, true);
		moves.addAll(Movement.getDiagonalMoves(this, true));
		super.coverings = moves;
	}
	
	@Override
	public void updatePossibleMoves() {
		ArrayList<Location> moves = Movement.getStraightMoves(this, false);
		moves.addAll(Movement.getDiagonalMoves(this, false));
		super.possibleMoves = moves;
	}

}