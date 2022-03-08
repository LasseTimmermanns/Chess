package Movement.Generator;

import java.util.ArrayList;

import Gui.Field;
import Main.main;
import Main.util;
import Movement.Location;
import Movement.Move;
import Piece.King;
import Piece.Knight;
import Piece.Piece;
import Piece.Rook;

public class KingMoves {


	public static ArrayList<Move> get(King k, boolean cover) {
		ArrayList<Move> out = new ArrayList<Move>();
		Location start = k.getLocation();
		
		//Rochade
		if(!cover && !k.isAlreadyMoved() && !main.check) {
			//König und Turm dürfen noch nicht bewegt worden sein, zwischen Turm und König keine Figuren
			
			for(Rook r : Rook.getAll()) {
				if(r.isAlreadyMoved()) continue;
				if(r.getColor() != k.getColor()) continue;
				if(r.getSide() == 0) continue;
				
				boolean obstructed = false;
				Location loc = new Location(k.getLocation().X + r.getSide(), k.getLocation().Y);
				while(loc.isInBoard() && !obstructed) {
					
					Field f = Field.getFieldByLocation(loc);
					if(f.isOccupied() || f.isCoveredBy(util.switchColor(k.getColor()))) {
						if(f.getCurrentPiece() == r) break;
						obstructed = true;
					}
					
					loc = new Location(loc.X + r.getSide(), loc.Y);
				}
				
				if(!obstructed) {
					Location kingLoc = new Location(k.getLocation().X + (r.getSide() * 2), k.getLocation().Y);
					Location rookLoc = new Location(kingLoc.X - r.getSide(), kingLoc.Y);
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
	
	
}
