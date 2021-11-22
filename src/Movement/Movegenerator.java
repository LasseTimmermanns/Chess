package Movement;

import java.util.ArrayList;

import Gui.Field;
import Main.main;
import Piece.King;
import Piece.Knight;
import Piece.Pawn;
import Piece.Piece;
import Piece.Rook;

public class Movegenerator {

	
	/*
	 * Gibt moegliche Zuege wieder
	 * Nuetzlich fuer gerade und diagonale Movements
	 */
	public static ArrayList<Move> getMoves(int x, int y, int x_operator, int y_operator, Piece p, boolean cover){
		ArrayList<Move> out = new ArrayList<Move>();
		boolean canContinue = true;
		
		while(canContinue) {
			x += x_operator;
			y += y_operator;
			
			Location current = new Location(x, y);
			switch(p.getMoveCode(current)) {
			case Piece.POSSIBLE_MOVE:
				out.add(new Move(p, current, true));
				break;
			case Piece.IS_COVERING:
				if(!cover) {
					canContinue = false;
					break;
				}
			case Piece.CAN_HIT:
				out.add(new Move(p, current, true));
				canContinue = false;
				break;
			case Piece.NOT_POSSIBLE_MOVE:
				canContinue = false;
				break;
			}
		}
		
		return out;
	}
	
	//cover true, wenn auch Felder ausgegeben werden sollen, welche die Figur deckt
	public static ArrayList<Move> getStraightMoves(Piece p, boolean cover) {
		ArrayList<Move> out = new ArrayList<Move>();
		
		int x = p.getLocation().X;
		int y = p.getLocation().Y;
		
		out.addAll(getMoves(x, y, 1, 0, p, cover)); //Rechts
		out.addAll(getMoves(x, y, (-1), 0, p, cover)); //Links
		out.addAll(getMoves(x, y, 0, 1, p, cover)); //Oben
		out.addAll(getMoves(x, y, 0, (-1), p, cover)); //Unten
		
		
		if(p.getName() == "Turm") {
			System.out.println("---");
			System.out.println(cover);
			for(Move m : out) {
				System.out.println(m);
			}
		}
		
		return out;
	}
	
	//cover true, wenn auch Felder ausgegeben werden sollen, welche die Figur deckt
	public static ArrayList<Move> getDiagonalMoves(Piece p, boolean cover) {
		ArrayList<Move> out = new ArrayList<Move>();
		
		int x = p.getLocation().X;
		int y = p.getLocation().Y;
		
		out.addAll(getMoves(x, y, 1, 1, p, cover)); //Oben rechts
		out.addAll(getMoves(x, y, (-1), 1, p, cover)); //Oben links
		out.addAll(getMoves(x, y, 1, (-1), p, cover)); //Unten rechts
		out.addAll(getMoves(x, y, (-1), (-1), p, cover)); //Unten links
		
		return out;
	}

	//cover true, wenn auch Felder ausgegeben werden sollen, welche die Figur deckt
	public static ArrayList<Move> getPawnMoves(Pawn p, boolean cover) {
		ArrayList<Move> out = new ArrayList<Move>();
		Location pawnLoc = p.getLocation();
		
		//Zuege nach vorne, nach links und nach rechts
		Location forward = new Location(pawnLoc.X, pawnLoc.Y + p.getDirection()),
				doubleForward = new Location(pawnLoc.X, pawnLoc.Y + (2 * p.getDirection())),
				diagonalLeft = new Location(forward.X - 1, forward.Y),
				diagonalRight = new Location(forward.X + 1, forward.Y);
		
		if(cover) {
			if(diagonalLeft.isInBoard()) out.add(new Move(p, diagonalLeft));
			if(diagonalRight.isInBoard()) out.add(new Move(p, diagonalRight));
			return out;
		}

		//TODO: En passant 
		if(p.getDirection() == -1 && pawnLoc.Y == 3 || p.getDirection() == 1 && pawnLoc.Y == 4) {
			if((Move.latest.getPiece().getClass() == Piece.PAWN) && (Move.latest.getPiece().getColor() != p.getColor())) {
				int x = Move.latest.getEnd().X;
				if((x == pawnLoc.X - 1 || x == pawnLoc.X + 1) && (Math.abs(Move.latest.getStart().Y - Move.latest.getEnd().Y) == 2)) {
					Location en_passant_loc = new Location(Move.latest.getStart().X, Move.latest.getStart().Y  + ((Pawn) Move.latest.getPiece()).getDirection());
					if(en_passant_loc.isInBoard()) {
						
						out.add(new Move(p, pawnLoc, Move.latest.getPiece(), en_passant_loc, true));
					}
				}
			}
		}
		
		
		
		out = addMoveWhenCondition(p, new Move(p, forward), new int[]{Piece.POSSIBLE_MOVE}, out);
		out = addMoveWhenCondition(p, new Move(p, diagonalLeft, true), new int[]{Piece.CAN_HIT}, out);
		out = addMoveWhenCondition(p, new Move(p, diagonalRight, true), new int[]{Piece.CAN_HIT}, out);
		
		//Doppelt nach vorne
		if(p.getMoveCode(forward) == Piece.POSSIBLE_MOVE && !p.isAlreadyMoved()) {
			out = addMoveWhenCondition(p, new Move(p, doubleForward), new int[]{Piece.POSSIBLE_MOVE}, out);
		}
		
		return out;
	}
	
	public static ArrayList<Move> getKingMoves(King k, boolean cover) {
		ArrayList<Move> out = new ArrayList<Move>();
		Location start = k.getLocation();
		
		//Rochade
		if(!cover && !k.isAlreadyMoved()) {
			//König und Turm dürfen noch nicht bewegt worden sein, zwischen Turm und König keine Figuren
			
			//Links
			for(Rook r : Rook.getAll()) {
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
					Location kingLoc = new Location(k.getLocation().X + (r.getSide() * 2), k.getLocation().Y);
					Location rookLoc = new Location(kingLoc.X + r.getSide(), kingLoc.Y);
					out.add(new Move(k, kingLoc, r, rookLoc, false));
				}
				
			}
			
		}
		
		//Alle möglichen Moves werden erstellt
		for(int x = -1; x <= 1; x++) {
			for(int y = -1; y <= 1; y++) {
				if(y == 0 && x == 0) continue;
				Location possibleMoveLoc = new Location(start.X + x, start.Y + y);

				if(!possibleMoveLoc.isInBoard()) continue;
				
				Field possibleMoveField = possibleMoveLoc.getField();
				Move possibleMove = new Move(k, possibleMoveLoc);
				
				if(possibleMoveField.isOccupied()) {
					possibleMove = new Move(k, possibleMoveLoc, true);
				}
				
				
				out.add(possibleMove);
			}	
		}
		
		if(cover) return out;
		
		int opponentsColor = k.getColor() == main.COLOR_WHITE ? main.COLOR_BLACK : main.COLOR_WHITE;
		for(Move move : new ArrayList<Move>(out)) { //Arraylist Kopie um ConcurrentModificationException zu vermeiden
			if(move.getEnd().getField().isCoveredBy(opponentsColor)) {
				out.remove(move);
			}
			
			if(k.getMoveCode(move.getEnd()) == Piece.NOT_POSSIBLE_MOVE || k.getMoveCode(move.getEnd()) == Piece.IS_COVERING) {
				out.remove(move);
			}
		}
		
		return out;
	}
	
	//Methode fügt Location hinzu, wenn eine der Bedingungen erfüllt ist
	private static ArrayList<Move> addMoveWhenCondition(Piece p, Move move, int[] conditions, ArrayList<Move> moves){
			
		for(int condition : conditions) {
			if(p.getMoveCode(move.getEnd()) == condition) {
				moves.add(move);
				break;
			}
		}
			
		return moves;
	}
	
	public static ArrayList<Move> getKnightMoves(Knight k, boolean cover) {
		ArrayList<Move> out = new ArrayList<Move>();
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
						out.add(new Move(k, loc, true));
						break;
					case Piece.POSSIBLE_MOVE:
						out.add(new Move(k, loc, false));
						break;
					}
				}
			}	
		}
		
		return out;
	}
	

}
