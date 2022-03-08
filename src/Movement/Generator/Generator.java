package Movement.Generator;

import java.util.ArrayList;

import Main.main;
import Movement.Location;
import Movement.Move;
import Piece.Piece;

public class Generator {

	
	public static ArrayList<Move> getMoves(int x, int y, int x_operator, int y_operator, Piece p, boolean cover){
		ArrayList<Move> out = new ArrayList<Move>();
		Piece hitted = null;
		boolean canContinue = true;
		
		while(canContinue) {
			x += x_operator;
			y += y_operator;
			
			Location current = new Location(x, y);
			switch(p.getMoveCode(current)) {
			case Piece.POSSIBLE_MOVE:
				if(hitted == null) out.add(new Move(p, current));
				break;
			case Piece.IS_COVERING:
				canContinue = false;
				
				if(cover && hitted == null) {
					out.add(new Move(p, current));
				}
				
				break;
			case Piece.CAN_HIT:
				//Gebundenheit ueberpruefen
				if(cover) {
					Piece pieceOnField = current.getField().getCurrentPiece();
					
					if(pieceOnField.getType() == Piece.TYPE_KING) {
						if(hitted == null) {
							main.check();
							canContinue = true; //Braucht man das??
							
							main.howToStopChess.add(p.getLocation());
							out.forEach(action->main.howToStopChess.add(action.getEnd()));	
						}else {
							hitted.addBindedLocation(p.getLocation());
							
							for(Move m : out) {
								hitted.addBindedLocation(m.getEnd());
							}
							
							Location currentLoc_binding = new Location(hitted.getLocation().X + x_operator, hitted.getLocation().Y + y_operator);
							while(true) {
								Piece pieceOnField_binding = currentLoc_binding.getField().getCurrentPiece();
								
								if(pieceOnField_binding != null) if(pieceOnField_binding.getType() == Piece.TYPE_KING) break;
								
								hitted.addBindedLocation(currentLoc_binding);
								currentLoc_binding = new Location(currentLoc_binding.X + x_operator, currentLoc_binding.Y + y_operator);
								
							}
							
							canContinue = false;
						}
					}else {
						if(hitted != null) {
							canContinue = false;
						}else {
							hitted = pieceOnField;
						}
							
					}
				}else {
					canContinue = false;
				}

				out.add(new Move(p, current, true));				
				break;
			case Piece.NOT_POSSIBLE_MOVE:
				canContinue = false;
				break;
			}
		}
		
		return out;
	}
	
	
	
	
	
}
