package Piece;

import Main.main;
import Movement.Location;
import Movement.Generator.DiagonalMoves;

public class Bishop extends Piece{

	public Bishop(int color, Location loc) {		
		super(Piece.TYPE_BISHOP, "Laeufer", color, loc, color == 0 ? main.bishop_white_img : main.bishop_black_img, 3);
	}
	
	@Override
	public void updateCoverings(){
		super.coverings = DiagonalMoves.get(this, true);
	}
	
	@Override
	public void updateRawMoves(){
		super.rawMoves = DiagonalMoves.get(this, false);
	}
}
