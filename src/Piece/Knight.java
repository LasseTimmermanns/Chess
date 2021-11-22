package Piece;

import Main.main;
import Movement.Location;
import Movement.Movegenerator;

public class Knight extends Piece{

	public Knight(int color, Location loc) {		
		super("Springer", color, loc, color == 0 ? main.knight_white_img : main.knight_black_img, 3);
	}
	
	@Override
	public void updateCoverings() {
		super.coverings = Movegenerator.getKnightMoves(this, true);
	}
	
	@Override
	public void updatePossibleMoves() {
		super.possibleMoves = Movegenerator.getKnightMoves(this, false);
	}
	

}
