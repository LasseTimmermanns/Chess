package Movement;

import Piece.Piece;

public class Move {

	private Piece piece;
	private Location start, end;
	private int index;
	public static Move latest;
	boolean played, captured;
	
	public Move(Piece piece, Location end) {
		init(piece, end, null, null, false);
	}
	
	public Move(Piece piece, Location end, boolean captured) {
		if(captured) {
			init(piece, end, null, null, true);
			return;
		}

		init(piece, end, null, null, false);
	}
	
	public Move(Piece piece, Location end,  Piece p2, Location loc2, boolean captured) {
		init(piece, end, p2, loc2, captured);
	}
	
	//Ganzer Konstrukor Erste Figur, Wohin sie moved, Zweite Figur, Wohin sie moved, captured = true wenn die zweite rausgeworfen wird.
	private void init(Piece piece, Location end,  Piece p2, Location loc2, boolean captured) {
		this.index = latest != null ? latest.getIndex() : 1;
		this.piece = piece;
		this.start = piece.getLocation();
		this.end = end;
		this.captured = true;
	}
	
	public void setPlayed() {
		
		//Damit nicht zweimal setPlayed() gemacht wird
		if(this.played) return;
		
		this.played = true;
		latest = this;
	}
	
	public Piece getPiece() {
		return this.piece;
	}
	
	public Location getStart() {
		return this.start;
	}
	
	public Location getEnd() {
		return this.end;
	}
	
	public int getIndex() {
		return this.index;
	}
	
	public String toString() {
		return getPiece().getName() + ": " + start.toString() + " -> " + end.toString();
	}

}
