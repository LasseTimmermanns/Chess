package piece;

import java.util.ArrayList;

import javax.swing.ImageIcon;

import main.Location;
import main.Move;
import main.Movement;
import main.main;

public class Rock extends Piece{

	public Rock(int color, Location loc) {		
		super("Turm", color, loc, color == 0 ? main.rock_white_img : main.rock_black_img, 5);
	}
	
	@Override
	public void updateCoverings() {
		super.coverings = Movement.getStraightMoves(this, true);
	}
	
	@Override
	public void updatePossibleMoves() {
		super.possibleMoves = Movement.getStraightMoves(this, false);
	}
	
	
	
	

}