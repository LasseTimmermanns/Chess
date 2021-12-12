package Movement.Generator;

import java.util.ArrayList;

import Movement.Move;
import Piece.Piece;

public class DiagonalMoves {
	
	//cover true, wenn auch Felder ausgegeben werden sollen, welche die Figur deckt
	public static ArrayList<Move> get(Piece p, boolean cover) {
		ArrayList<Move> out = new ArrayList<Move>();
		
		int x = p.getLocation().X;
		int y = p.getLocation().Y;
		
		out.addAll(Generator.getMoves(x, y, 1, 1, p, cover)); //Oben rechts
		out.addAll(Generator.getMoves(x, y, (-1), 1, p, cover)); //Oben links
		out.addAll(Generator.getMoves(x, y, 1, (-1), p, cover)); //Unten rechts
		out.addAll(Generator.getMoves(x, y, (-1), (-1), p, cover)); //Unten links
		
		return out;
	}
	
}
