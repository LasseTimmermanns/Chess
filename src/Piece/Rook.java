package Piece;

import java.util.ArrayList;

import Main.main;
import Movement.Location;
import Movement.Move;
import Movement.Movegenerator;

public class Rook extends Piece{
	
	//side = -1: linke seite;
	//side = 1: rechte seite;
	private int side = 1;
	private static ArrayList<Rook> all = new ArrayList<Rook>();

	public Rook(int color, Location loc) {		
		super("Turm", color, loc, color == 0 ? main.rock_white_img : main.rock_black_img, 5);
		
		if(loc.X == 0) side = -1;
		
		all.add(this);
	}
	
	@Override
	public void updateCoverings() {
		super.coverings = Movegenerator.getStraightMoves(this, true);
	}
	
	@Override
	public void updatePossibleMoves() {
		super.possibleMoves = Movegenerator.getStraightMoves(this, false);
	}
	
	public static ArrayList<Rook> getAll() {
		return all;
	}
	
	public int getSide() {
		return side;
	}
}