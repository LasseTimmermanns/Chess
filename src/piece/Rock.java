package piece;

import java.util.ArrayList;

import main.Location;
import main.Movement;
import main.main;

public class Rock extends Piece{
	
	//side = -1: linke seite;
	//side = 1: rechte seite;
	private int side = 1;
	private static ArrayList<Rock> all = new ArrayList<Rock>();

	public Rock(int color, Location loc) {		
		super("Turm", color, loc, color == 0 ? main.rock_white_img : main.rock_black_img, 5);
		
		if(loc.X == 0) side = -1;
		
		all.add(this);
	}
	
	@Override
	public void updateCoverings() {
		super.coverings = Movement.getStraightMoves(this, true);
	}
	
	@Override
	public void updatePossibleMoves() {
		super.possibleMoves = Movement.getStraightMoves(this, false);
	}
	
	public static ArrayList<Rock> getAll() {
		return all;
	}
	
	public int getSide() {
		return side;
	}
}