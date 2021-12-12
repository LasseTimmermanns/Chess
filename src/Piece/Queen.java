package Piece;

import java.util.ArrayList;

import Main.main;
import Movement.Location;
import Movement.Move;
import Movement.Generator.DiagonalMoves;
import Movement.Generator.StraightMoves;

public class Queen extends Piece{

	public Queen(int color, Location loc) {		
		super(Piece.TYPE_QUEEN, "Dame", color, loc, color == 0 ? main.queen_white_img : main.queen_black_img, 5);
	}
	
	@Override
	public void updateCoverings() {
		ArrayList<Move> moves = StraightMoves.get(this, true);
		moves.addAll(DiagonalMoves.get(this, true));
		super.coverings = moves;
	}
	
	@Override
	public void updateRawMoves() {
		ArrayList<Move> moves = StraightMoves.get(this, false);
		moves.addAll(DiagonalMoves.get(this, false));
		super.rawMoves = moves;
	}

}