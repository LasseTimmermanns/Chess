package DataStructures;

import java.util.ArrayList;

import Gui.Field;
import Piece.Piece;

public class Position_Array {

	private static ArrayList<Integer> all_positions = new ArrayList<Integer>();
	
	public static void add(int hash) {
		all_positions.add(hash);
	}
	
	public static void removeLatest() {
		all_positions.remove(all_positions.size() - 1);
	}

	public static boolean checkEnding() {
		if(all_positions.size() < 3) return false;

		for(int x = 0; x < all_positions.size(); x++) {
			int matches = 0;
			
			for(int y = 0; y < all_positions.size(); y++) {
				//Wenn es bei beiden der selbe index ist, also der gleiche wert sein muss (zaehlt nicht mit)
				if(y == x) continue;

				if(all_positions.get(x).equals(all_positions.get(y))) {
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
