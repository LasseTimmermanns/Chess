package Movement;

import java.util.ArrayList;

import DataStructures.Move_Stack;
import DataStructures.Position_Array;
import Gui.Field;
import Gui.SwitchPieceMenu;
import Main.main;
import Piece.Pawn;
import Piece.Piece;

public class Move {

	private Piece piece, piece2;
	private Location start, start2, end, end2;
	private int index;
	public static Move latest;
	boolean played, captured;
	public static Move_Stack history = new Move_Stack();
	public static int movesCapturePawn = 0; //Züge seit denen kein Bauer bewegt oder Figur geschlagen wurde: Bei 50 Remi
	
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
	private void init(Piece piece, Location end, Piece piece2, Location end2, boolean captured) {
		this.index = latest != null ? latest.getIndex() + 1 : 1;
		this.piece = piece;
		this.start = piece.getLocation();
		this.piece2 = piece2;
		this.start2 = piece2 != null ? piece2.getLocation() : null;
		this.end = end;
		this.end2 = end2;
		this.captured = captured;
	}
	
	public void setPlayed() {
		
		//Damit nicht zweimal setPlayed() gemacht werden kann
		if(this.played) return;
		this.played = true;
		
		if(captured) {
			piece2.kick();
		}
		if(piece2 != null && end2 != null && !captured) {
			piece2.move(end2.getField());
		}
		
		piece.move(end.getField());		
		latest = this;
		
		if(piece.getType() == Piece.TYPE_PAWN && (getEnd().Y == 7 || getEnd().Y == 0)) {
			new SwitchPieceMenu((Pawn) piece);
		}
		
		if(captured || piece.getType() == Piece.TYPE_PAWN) {
			movesCapturePawn = 0;
		}else if(piece.getColor() == main.COLOR_WHITE){ //Nur wenn weiß dran ist hoch gemacht, damit nicht doppelt
			movesCapturePawn++;
		}
		
		history.push(this);
		main.nextMove(true);
	}
	
	public static void back() {
		main.gameRuns = true;
		if(history.peek() == null) return;
		
		for(Piece p : Piece.all) {
			if(p.movesAreMarked()) p.markMoves();
		}
		
		Move m = history.pop();
		if(movesCapturePawn > 0) movesCapturePawn--; //50 Zug Regel zurück genommen
		
		m.piece.move(Field.getFieldByLocation(m.getStart()));
		m.piece.setMoves(m.piece.getMoves() - 2); //Zweimal methode Move aufgerufen: Zug spielen, Zug rückgängig
		
		if(m.piece2 != null) {
			if(m.captured) m.piece2.revive();
			m.piece2.move(Field.getFieldByLocation(m.getStart2()));
			m.piece2.setMoves(m.piece2.getMoves() - 2); //Zweimal methode Move aufgerufen: Zug spielen, Zug rückgängig
		}
		
		Position_Array.removeLatest();
		main.nextMove(false);
	}
	
	public Piece getPiece() {
		return this.piece;
	}
	
	public Piece getPiece2() {
		return piece2;
	}
	
	public Location getStart() {
		return this.start;
	}
	
	public Location getStart2() {
		return start2;
	}	
	
	public Location getEnd() {
		return this.end;
	}
	
	public Location getEnd2() {
		return end2;
	}
	
	public int getIndex() {
		return this.index;
	}
	
	public String toString() {
		return getPiece().getColor() + " " + getPiece().getName() + ": " + start.toString() + " -> " + end.toString();
	}

}
