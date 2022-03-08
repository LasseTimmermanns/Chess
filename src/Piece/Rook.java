package Piece;

import java.util.ArrayList;

import Main.main;
import Movement.Location;
import Movement.Generator.StraightMoves;

public class Rook extends Piece{
	
	//side = -1: linke seite;
	//side = 1: rechte seite;
	private int side = 1;
	private static ArrayList<Rook> all = new ArrayList<Rook>();

	public Rook(int color, Location loc) {		
		super(Piece.TYPE_ROOK, "Turm", color, loc, color == 0 ? main.rock_white_img : main.rock_black_img, 5);
		
		init(loc.X < 4 ? -1 : 1);
	}
	
	public Rook(int color, Location loc, int side) {		
		super(Piece.TYPE_ROOK, "Turm", color, loc, color == 0 ? main.rock_white_img : main.rock_black_img, 5);
		 
		init(side);
	}
	
	public void init(int side) {
		this.side = side;
		all.add(this);
	}
	
	@Override
	public void updateCoverings() {
		super.coverings = StraightMoves.get(this, true);
	}
	
	@Override
	public void updateRawMoves() {
		super.rawMoves = StraightMoves.get(this, false);
	}
	
	public static ArrayList<Rook> getAll() {
		return all;
	}
	
	public int getSide() {
		return side;
	}
}