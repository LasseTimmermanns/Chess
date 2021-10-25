package main;

import java.util.ArrayList;

import gui.Field;
import piece.King;
import piece.Knight;
import piece.Pawn;
import piece.Piece;
import piece.Rock;

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
		Location pawnLoc = p.getLocation();
		
		//Zuege nach vorne, nach links und nach rechts
		Location forward = new Location(pawnLoc.X, pawnLoc.Y + p.getDirection()),
				doubleForward = new Location(pawnLoc.X, pawnLoc.Y + (2 * p.getDirection())),
				diagonalLeft = new Location(forward.X - 1, forward.Y),
				diagonalRight = new Location(forward.X + 1, forward.Y);
		
		if(cover) {
			if(diagonalLeft.isInBoard()) out.add(diagonalLeft);
			if(diagonalRight.isInBoard()) out.add(diagonalRight);
			return out;
		}

		//TODO: En passant 
		if(p.getDirection() == -1 && pawnLoc.Y == 3 || p.getDirection() == 1 && pawnLoc.Y == 4) {
			if((Move.latest.getPiece().getClass() == Piece.PAWN) && (Move.latest.getPiece().getColor() != p.getColor())) {
				int x = Move.latest.getEnd().X;
				if((x == pawnLoc.X - 1 || x == pawnLoc.X + 1) && (Math.abs(Move.latest.getStart().Y - Move.latest.getEnd().Y) == 2)) {
					Location en_passant = new Location(Move.latest.getStart().X, Move.latest.getStart().Y  + ((Pawn) Move.latest.getPiece()).getDirection());
					if(en_passant.isInBoard()) {
						out.add(en_passant);
					}
				}
			}
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
		
		//Rochade
		if(!cover && !k.isAlreadyMoved()) {
			//König und Turm dürfen noch nicht bewegt worden sein, zwischen Turm und König keine Figuren
			
			//Links
			for(Rock r : Rock.getAll()) {
				if(r.isAlreadyMoved()) continue;
				if(r.getColor() != k.getColor()) continue;
				
				Location loc = k.getLocation();
				boolean obstructed = false;
				while(loc.isInBoard() && !obstructed) {
					loc = new Location(loc.X + r.getSide(), loc.Y);
					
					Field f = Field.getFieldByLocation(loc);
					if(f.isOccupied()) {
						if(f.getCurrentPiece() == r) break;
						obstructed = true;
					}
				}
				
				if(!obstructed) {
					Location loc2 = new Location(k.getLocation().X + (r.getSide() * 2), k.getLocation().Y);
					out.add(loc2);
				}
				
			}
			
		}
		
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
				out.remove(loc);
			}
			
			if(k.getMoveCode(loc) == Piece.NOT_POSSIBLE_MOVE || k.getMoveCode(loc) == Piece.IS_COVERING) {
				out.remove(loc);
			}
		}
		
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
	
	public static ArrayList<Location> getKnightMoves(Knight k, boolean cover) {
		ArrayList<Location> out = new ArrayList<Location>();
		Location current = k.getLocation();
		
		//Wenn die Summe aus positivem x und positivem y == 3 ist, ist es ein Springer Zug
		for(int x = -2; x <= 2; x++) {
			for(int y = -2; y <= 2; y++) {
				if(Math.abs(x) + Math.abs(y) == 3) {
					Location loc = new Location(current.X + x, current.Y + y);
					
					switch(k.getMoveCode(loc)) {
					case Piece.IS_COVERING:
						if(!cover) break;
					case Piece.CAN_HIT:
					case Piece.POSSIBLE_MOVE:
						out.add(loc);
					}
				}
			}	
		}
		
		return out;
	}
	

}
