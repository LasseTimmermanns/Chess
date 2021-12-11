package Main;

import java.util.ArrayList;

import Gui.Field;
import Movement.Location;
import Movement.Move;
import Piece.Piece;

public class util {
		
	// ArrayList von Fields zu normalen Array
	public static Field[] fieldListToArray(ArrayList<Field> list) {
		Field[] out = new Field[list.size()];
		for(int i = 0; i < list.size(); i++) {
			out[i] = list.get(i);
		}
		
		return out;
	}
	
	//Funktion findet den passenden Move zu Figur und neuer Position
	public static Move findMove(Piece p, Location end) {
		for(Move m : p.getPossibleMoves()) {
			if((m.getEnd().X == end.X) && (m.getEnd().Y == end.Y)) return m;
		}
		
		System.out.println("findmoves == null");
		return null;
	}
	
	public static int getOtherColor(int color) {
		return color == 0 ? 1 : 0;
	}
	
}
