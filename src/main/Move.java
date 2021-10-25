package main;

import piece.Piece;

public class Move {

	private Piece piece;
	private Location start, end;
	private int index;
	public static Move latest;
	
	public Move(Piece piece, Location start, Location end, boolean played) {
		this.index = latest != null ? latest.getIndex() : 1;
		this.piece = piece;
		this.start = start;
		this.end = end;
		
		if(played) latest = this;
	}
	
	public Piece getPiece() {
		return piece;
	}
	
	public Location getStart() {
		return start;
	}
	
	public Location getEnd() {
		return end;
	}
	
	public int getIndex() {
		return index;
	}

}
