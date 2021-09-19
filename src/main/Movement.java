package main;

import java.util.ArrayList;

import piece.Piece;

public class Movement {

	public static Location[] getHorizontalMoves(Piece p) {
		//Gerade Züge
		ArrayList<Location> possibleMoves = new ArrayList<Location>();
		
		Location start = p.getLocation();
		
		//Züge nach links
		int x = start.X;
		int y = start.Y;
		
		for(Location loc : machwas(x, y, 1, 0, p)) {
			possibleMoves.add(loc);
		}
		
		for(Location loc : machwas(x, y, (-1), 0, p)) {
			possibleMoves.add(loc);
		}
		
		for(Location loc : machwas(x, y, 0, 1, p)) {
			possibleMoves.add(loc);
		}
		
		for(Location loc : machwas(x, y, 0, (-1), p)) {
			possibleMoves.add(loc);
		}
		
		return toArray(possibleMoves);
	}
	
	public static Location[] toArray(ArrayList<Location> locList) {
		Location[] out = new Location[locList.size()];
		for(int i = 0; i < locList.size(); i++) {
			out[i] = locList.get(i);
		}
		
		return out;
	}
	
	public static ArrayList<Location> machwas(int x, int y, int x_operator1, int y_operator2, Piece p){
		ArrayList<Location> out = new ArrayList<Location>();
		boolean canContinue = true;
		
		while(canContinue) {
			x += x_operator1;
			y += y_operator2;
			
			Location current = new Location(x, y);
			switch(p.getMoveCode(current)) {
			case Piece.POSSIBLE_MOVE:
				out.add(current);
				break;
			case Piece.LAST_MOVE:
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
