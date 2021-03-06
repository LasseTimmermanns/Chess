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
		for(Move m : p.getPlayableMoves()) {
			if((m.getEnd().X == end.X) && (m.getEnd().Y == end.Y)) return m;
		}
		
		System.out.println("findmoves == null");
		return null;
	}
	
	public static int switchColor(int color) {
		return color == main.COLOR_WHITE ? main.COLOR_BLACK : main.COLOR_WHITE;
	}
	
	//https://stackoverflow.com/questions/3054449/how-to-properly-define-hash-function-for-a-list-of-objects
	public static int hashList(Object[] list) {
		int hashCode = 1;
		for(Object obj : list) {
			hashCode = 31*hashCode + (obj==null ? 0 : obj.hashCode());
		}
		
		return hashCode;
	}
	
}
