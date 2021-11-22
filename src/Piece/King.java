package Piece;

import Main.main;
import Movement.Location;
import Movement.Movegenerator;

public class King extends Piece{

	public King(int color, Location loc) {		
		super("King", color, loc, color == 0 ? main.king_white_img : main.king_black_img, 1000);
	}
	
	@Override
	public void updateCoverings() {
		super.coverings = Movegenerator.getKingMoves(this, true);
	}
	
	@Override
	public void updatePossibleMoves() {
		super.possibleMoves = Movegenerator.getKingMoves(this, false);
	}

}
