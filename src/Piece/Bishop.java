package Piece;

import Main.main;
import Movement.Location;
import Movement.Movegenerator;

public class Bishop extends Piece{

	public Bishop(int color, Location loc) {		
		super("Laeufer", color, loc, color == 0 ? main.bishop_white_img : main.bishop_black_img, 3);
	}
	
	@Override
	public void updateCoverings(){
		super.coverings = Movegenerator.getDiagonalMoves(this, true);
	}
	
	@Override
	public void updatePossibleMoves(){
		super.possibleMoves = Movegenerator.getDiagonalMoves(this, false);
	}
}
