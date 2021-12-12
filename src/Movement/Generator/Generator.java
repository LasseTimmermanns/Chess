package Movement.Generator;

import java.util.ArrayList;

import Main.main;
import Movement.Location;
import Movement.Move;
import Piece.Piece;

public class Generator {

	/*
	 * Gibt moegliche Zuege wieder
	 * Nuetzlich fuer gerade und diagonale Movements
	 */
	public static ArrayList<Move> getMovesG(int x, int y, int x_operator, int y_operator, Piece p, boolean cover){
		ArrayList<Move> out = new ArrayList<Move>();
		boolean canContinue = true;
		
		while(canContinue) {
			x += x_operator;
			y += y_operator;
			
			Location current = new Location(x, y);
			switch(p.getMoveCode(current)) {
			case Piece.POSSIBLE_MOVE:
				out.add(new Move(p, current));
				break;
			case Piece.IS_COVERING:
				if(!cover) {
					canContinue = false;
				}else {
					out.add(new Move(p, current));	
				}
				
				break;
			case Piece.CAN_HIT:
				Piece pieceOnField = current.getField().getCurrentPiece();
				out.add(new Move(p, current, true));
				
				
				if(cover) {
					if(pieceOnField.getColor() != p.getColor() && pieceOnField.getType() == Piece.TYPE_KING) {						
						canContinue = true;
						main.check(); 
						
						/*
						 * Wie kann man Schach unterbinden
						 * 1: Rauswerfen
						 * 2: Weg versperren
						 */
						main.howToStopChess.add(p.getLocation()); 
						
						ArrayList<Location> arr2 = new ArrayList<Location>();
						out.forEach(action->arr2.add(action.getEnd()));
						main.howToStopChess.addAll(arr2);
						
						//out.forEach(action->System.out.println(action));
					}
				}else {
					canContinue = false;
				}
				
				break;
			case Piece.NOT_POSSIBLE_MOVE:
				canContinue = false;
				break;
			}
		}
		
		return out;
	}
	
	
	public static ArrayList<Move> getMoves(int x, int y, int x_operator, int y_operator, Piece p, boolean cover){
		ArrayList<Move> out = new ArrayList<Move>();
		Piece binded = null;
		boolean canContinue = true;
		int bindingInt = 0;
		boolean foundBinding = false;
		
		while(canContinue) {
			x += x_operator;
			y += y_operator;
			
			Location current = new Location(x, y);
			switch(p.getMoveCode(current)) {
			case Piece.POSSIBLE_MOVE:
				if(bindingInt == 0) out.add(new Move(p, current));
				break;
			case Piece.IS_COVERING:
				if(!cover) {
					canContinue = false;
				}else {
					if(bindingInt == 0) out.add(new Move(p, current));	
				}
				
				break;
			case Piece.CAN_HIT:
				Piece pieceOnField = current.getField().getCurrentPiece();
				out.add(new Move(p, current, true));
				
				if(cover) {
					if(pieceOnField.getColor() != p.getColor() && pieceOnField.getType() == Piece.TYPE_KING && bindingInt == 0) {	
						//wenn schach ist und keine figur um bindung zu überprüfen übersprungen wurde					
						canContinue = true;
						main.check(); 
						
						/*
						 * Wie kann man Schach unterbinden
						 * 1: Rauswerfen
						 * 2: Weg versperren
						 */
						main.howToStopChess.add(p.getLocation()); 
						
						ArrayList<Location> arr2 = new ArrayList<Location>();
						out.forEach(action->arr2.add(action.getEnd()));
						main.howToStopChess.addAll(arr2);
						
						//out.forEach(action->System.out.println(action));
					}else { //Gucken ob Figur gebunden ist
						if(bindingInt == 2) {
							if(foundBinding) {
								
								ArrayList<Location> arr2 = new ArrayList<Location>();
								out.forEach(action->arr2.add(action.getEnd()));
								binded.addPossibleLocations(arr2);
							}
							break;
						}
						if(bindingInt == 0) binded = pieceOnField;
						bindingInt++;
						
						if(pieceOnField.getColor() != p.getColor() && pieceOnField.getType() == Piece.TYPE_KING) {
							//Schach aber eine Figur steht noch davor
							foundBinding = true;
						}
							
							
						
					}
				}else {
					canContinue = false;
				}
				
				break;
			case Piece.NOT_POSSIBLE_MOVE:
				if(foundBinding) {
					ArrayList<Location> arr2 = new ArrayList<Location>();
					arr2.add(p.getLocation());
					out.forEach(action->arr2.add(action.getEnd()));
					binded.addPossibleLocations(arr2);
				}
				canContinue = false;
				break;
			}
		}
		
		return out;
	}
	
	
	
	
	
}
