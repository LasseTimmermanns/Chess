package main;

import piece.Piece;

public class Move {

	private Piece piece;
	private Location start, end;
	
	public Move(Piece piece, Location start, Location end) {
		this.piece = piece;
		this.start = start;
		this.end = end;
	}

}
