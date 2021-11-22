package Piece;

import java.util.ArrayList;

import Main.main;
import Movement.Location;
import Movement.Move;
import Movement.Movegenerator;

public class Queen extends Piece{

	public Queen(int color, Location loc) {		
		super("Dame", color, loc, color == 0 ? main.queen_white_img : main.queen_black_img, 5);
	}
	
	@Override
	public void updateCoverings() {
		ArrayList<Move> moves = Movegenerator.getStraightMoves(this, true);
		moves.addAll(Movegenerator.getDiagonalMoves(this, true));
		super.coverings = moves;
	}
	
	@Override
	public void updatePossibleMoves() {
		ArrayList<Move> moves = Movegenerator.getStraightMoves(this, false);
		moves.addAll(Movegenerator.getDiagonalMoves(this, false));
		super.possibleMoves = moves;
	}

}