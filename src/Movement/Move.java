package Movement;

import Main.main;
import Piece.Piece;

public class Move {

	private Piece piece, piece2;
	private Location start, end, loc2;
	private int index;
	public static Move latest;
	boolean played, captured;
	
	public Move(Piece piece, Location end) {
		init(piece, end, null, null, false);
	}
	
	public Move(Piece piece, Location end, boolean captured) {
		if(captured && end.isInBoard()) {
			init(piece, end, end.getField().getCurrentPiece(), null, true);
		}else {
			init(piece, end, null, null, false);
		}
	}
	
	public Move(Piece piece, Location end,  Piece p2, Location loc2, boolean captured) {
		init(piece, end, p2, loc2, captured);
	}
	
	//Ganzer Konstrukor Erste Figur, Wohin sie moved, Zweite Figur, Wohin sie moved, captured = true wenn die zweite rausgeworfen wird.
	private void init(Piece piece, Location end,  Piece piece2, Location loc2, boolean captured) {
		this.index = latest != null ? latest.getIndex() : 1;
		this.piece = piece;
		this.start = piece.getLocation();
		this.piece2 = piece2;
		this.loc2 = loc2;
		this.end = end;
		this.captured = captured;
	}
	
	public void setPlayed() {
		
		//Damit nicht zweimal setPlayed() gemacht wird
		if(this.played) return;
		this.played = true;
		
		if(captured) {
			System.out.println(piece2.getName());
			System.out.println(piece2.getLocation());
			piece2.getLocation().getField().removePiece();
			Piece.all.remove(piece2);
		}
		if(piece2 != null && loc2 != null && !captured) {
			piece2.move(loc2.getField());
		}
		piece.move(end.getField());		
		latest = this;
		
		main.nextMove(this, false);
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
		return getPiece().getColor() + " " + getPiece().getName() + ": " + start.toString() + " -> " + end.toString();
	}

}
