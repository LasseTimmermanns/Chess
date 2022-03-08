package DataStructures;

import java.util.ArrayList;

import Gui.Field;
import Piece.Piece;

public class Position_Array {

	private static ArrayList<String[]> all_positions = new ArrayList<String[]>();
	
	public static void add(Field[] f) {
		all_positions.add(toStringArray(f));		
	}
	
	public static void removeLatest() {
		all_positions.remove(all_positions.size() - 1);
	}
	
	private static String[] toStringArray(Field[] f) {
		String[] s = new String[64];
		
		for(int i = 0; i < s.length; i++) {
			Piece p = f[i].getCurrentPiece();
			s[i] = "null";
			if(p == null) continue;
			
			s[i] = p.toString();
		}
		
		return s;
	}

	public static boolean checkEnding() {
		if(all_positions.size() < 3) return false;

		for(int x = 0; x < all_positions.size(); x++) {
			int matches = 1;
			
			String[] s_x = all_positions.get(x);
			for(int y = 0; y < all_positions.size(); y++) {
				if(y == x) continue;
				
				String[] s_y = all_positions.get(y);
				if(s_y == s_x) {
					matches++;
				}else if(array_equal(s_x, s_y)){
					matches++;
				}
			}	
			
			if(matches >= 3) return true;
		}
		
		
		return false;
	}
	
	public static boolean array_equal(String[] s1, String[] s2) {
		for (int i = 0; i < s1.length; i++){			
            if (!(s1[i].equals(s2[i]))) {            	
                return false;
            }
        }
		
		return true;
	}
	
	public static void clear() {
		all_positions.clear();
	}

}
