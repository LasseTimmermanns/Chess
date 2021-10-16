package main;

import java.util.ArrayList;

import gui.Field;
import piece.King;
import piece.Pawn;
import piece.Piece;

public class Movement {

	//cover true, wenn auch Felder ausgegeben werden sollen, welche die Figur deckt
	public static ArrayList<Location> getStraightMoves(Piece p, boolean cover) {
		ArrayList<Location> out = new ArrayList<Location>();
		
		int x = p.getLocation().X;
		int y = p.getLocation().Y;
		
		out.addAll(getMoves(x, y, 1, 0, p, cover)); //Rechts
		out.addAll(getMoves(x, y, (-1), 0, p, cover)); //Links
		out.addAll(getMoves(x, y, 0, 1, p, cover)); //Oben
		out.addAll(getMoves(x, y, 0, (-1), p, cover)); //Unten
		
		return out;
	}
	
	//cover true, wenn auch Felder ausgegeben werden sollen, welche die Figur deckt
	public static ArrayList<Location> getDiagonalMoves(Piece p, boolean cover) {
		ArrayList<Location> out = new ArrayList<Location>();
		
		int x = p.getLocation().X;
		int y = p.getLocation().Y;
		
		out.addAll(getMoves(x, y, 1, 1, p, cover)); //Oben rechts
		out.addAll(getMoves(x, y, (-1), 1, p, cover)); //Oben links
		out.addAll(getMoves(x, y, 1, (-1), p, cover)); //Unten rechts
		out.addAll(getMoves(x, y, (-1), (-1), p, cover)); //Unten links
		
		return out;
	}

	//cover true, wenn auch Felder ausgegeben werden sollen, welche die Figur deckt
	public static ArrayList<Location> getPawnMoves(Pawn p, boolean cover) {
		ArrayList<Location> out = new ArrayList<Location>();
			
		//Zuege nach vorne, nach links und nach rechts
		Location forward = new Location(p.getLocation().X, p.getLocation().Y + p.getDirection()),
				doubleForward = new Location(p.getLocation().X, p.getLocation().Y + (2 * p.getDirection())),
				diagonalLeft = new Location(forward.X - 1, forward.Y),
				diagonalRight = new Location(forward.X + 1, forward.Y);
		
		if(cover) {
			if(diagonalLeft.isInBoard()) out.add(diagonalLeft);
			if(diagonalRight.isInBoard()) out.add(diagonalRight);
			return out;
		}
		
		out = addLocationWhenCondition(p, forward, new int[]{Piece.POSSIBLE_MOVE}, out);
		out = addLocationWhenCondition(p, diagonalLeft, new int[]{Piece.CAN_HIT}, out);
		out = addLocationWhenCondition(p, diagonalRight, new int[]{Piece.CAN_HIT}, out);
		
		//Doppelt nach vorne
		if(p.getMoveCode(forward) == Piece.POSSIBLE_MOVE && !p.isAlreadyMoved()) {
			out = addLocationWhenCondition(p, doubleForward, new int[]{Piece.POSSIBLE_MOVE}, out);
		}
		
		return out;
	}
	
	public static ArrayList<Location> getKingMoves(King k, boolean cover) {
		ArrayList<Location> out = new ArrayList<Location>();
		Location start = k.getLocation();
		
		for(int x = -1; x <= 1; x++) {
			for(int y = -1; y <= 1; y++) {
				if(y == 0 && x == 0) continue;
				Location loc = new Location(start.X + x, start.Y + y);
				if(loc.isInBoard())	out.add(loc);
			}	
		}
		
		if(cover) return out;
		
		int opponentsColor = k.getColor() == main.COLOR_WHITE ? main.COLOR_BLACK : main.COLOR_WHITE;
		for(Location loc : new ArrayList<Location>(out)) { //Arraylist Kopie um ConcurrentModificationException zu vermeiden
			if(loc.getField().isCoveredBy(opponentsColor)) {
				System.out.println("removing" + loc);
				out.remove(loc);
			}
			
			if(k.getMoveCode(loc) == Piece.NOT_POSSIBLE_MOVE || k.getMoveCode(loc) == Piece.IS_COVERING) {
				System.out.println("removing");
				out.remove(loc);
			}
		}
		
		System.out.println(out.toArray());
		
		return out;
	}
	
	//Methode fügt Location hinzu, wenn eine der Bedingungen erfüllt ist
	private static ArrayList<Location> addLocationWhenCondition(Piece p, Location loc, int[] conditions, ArrayList<Location> moves){
			
		for(int condition : conditions) {
			if(p.getMoveCode(loc) == condition) {
				moves.add(loc);
				break;
			}
		}
			
		return moves;
	}
	
	public static ArrayList<Location> getMoves(int x, int y, int x_operator, int y_operator, Piece p, boolean cover){
		ArrayList<Location> out = new ArrayList<Location>();
		boolean canContinue = true;
		
		while(canContinue) {
			x += x_operator;
			y += y_operator;
			
			Location current = new Location(x, y);
			switch(p.getMoveCode(current)) {
			case Piece.POSSIBLE_MOVE:
				out.add(current);
				break;
			case Piece.IS_COVERING:
				if(!cover) {
					canContinue = false;
					break;
				}
			case Piece.CAN_HIT:
				out.add(current);
				canContinue = false;
				break;
			case Piece.NOT_POSSIBLE_MOVE:
				canContinue = false;
				break;
			}
		}
		
		return out;
	}
	

}
