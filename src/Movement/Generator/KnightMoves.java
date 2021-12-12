package Movement.Generator;

import java.util.ArrayList;

import Main.main;
import Movement.Location;
import Movement.Move;
import Piece.Knight;
import Piece.Piece;

public class KnightMoves {

	public static ArrayList<Move> get(Knight k, boolean cover) {
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
						if(loc.getField().getCurrentPiece().getType() == Piece.TYPE_KING) main.check();
						
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
