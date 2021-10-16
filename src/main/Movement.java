package main;

import java.util.ArrayList;

import gui.Field;
import piece.King;
import piece.Pawn;
import piece.Piece;

public class Movement {

	public static ArrayList<Location> getStraightMoves(Piece p) {
		ArrayList<Location> possibleMoves = new ArrayList<Location>();
		
		int x = p.getLocation().X;
		int y = p.getLocation().Y;
		
		possibleMoves.addAll(getMoves(x, y, 1, 0, p)); //Rechts
		possibleMoves.addAll(getMoves(x, y, (-1), 0, p)); //Links
		possibleMoves.addAll(getMoves(x, y, 0, 1, p)); //Oben
		possibleMoves.addAll(getMoves(x, y, 0, (-1), p)); //Unten
		
		return possibleMoves;
	}
	
	public static ArrayList<Location> getDiagonalMoves(Piece p) {
		ArrayList<Location> possibleMoves = new ArrayList<Location>();
		
		int x = p.getLocation().X;
		int y = p.getLocation().Y;
		
		possibleMoves.addAll(getMoves(x, y, 1, 1, p)); //Oben rechts
		possibleMoves.addAll(getMoves(x, y, (-1), 1, p)); //Oben links
		possibleMoves.addAll(getMoves(x, y, 1, (-1), p)); //Unten rechts
		possibleMoves.addAll(getMoves(x, y, (-1), (-1), p)); //Unten links
		
		return possibleMoves;
	}
	
	public static ArrayList<Location> getPawnMoves(Pawn p) {
		ArrayList<Location> possibleMoves = new ArrayList<Location>();
			
		//Zuege nach vorne, nach links und nach rechts
		Location forward = new Location(p.getLocation().X, p.getLocation().Y + p.getDirection()),
				doubleForward = new Location(p.getLocation().X, p.getLocation().Y + (2 * p.getDirection())),
				diagonalLeft = new Location(forward.X - 1, forward.Y),
				diagonalRight = new Location(forward.X + 1, forward.Y);
		
		possibleMoves = addLocationWhenCondition(p, forward, new int[]{Piece.POSSIBLE_MOVE}, possibleMoves);
		possibleMoves = addLocationWhenCondition(p, diagonalLeft, new int[]{Piece.CAN_HIT}, possibleMoves);
		possibleMoves = addLocationWhenCondition(p, diagonalRight, new int[]{Piece.CAN_HIT}, possibleMoves);
		
		//Doppelt nach vorne
		if(p.getMoveCode(forward) == Piece.POSSIBLE_MOVE && !p.isAlreadyMoved()) {
			possibleMoves = addLocationWhenCondition(p, doubleForward, new int[]{Piece.POSSIBLE_MOVE}, possibleMoves);
		}
		
		return possibleMoves;
	}
	
	public static ArrayList<Location> getKingMoves(King k) {
		
		
		return null;
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
	
	public static ArrayList<Location> getMoves(int x, int y, int x_operator, int y_operator, Piece p){
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
