package main;

import java.util.ArrayList;

import gui.Field;
import piece.Pawn;
import piece.Piece;

public class Movement {

	public static Location[] getHorizontalMoves(Piece p) {
		//Gerade Züge
		ArrayList<Location> possibleMoves = new ArrayList<Location>();
		
		Location start = p.getLocation();
		
		//Züge nach links
		int x = start.X;
		int y = start.Y;
		
		for(Location loc : getMoves(x, y, 1, 0, p)) {
			possibleMoves.add(loc);
		}
		
		for(Location loc : getMoves(x, y, (-1), 0, p)) {
			possibleMoves.add(loc);
		}
		
		for(Location loc : getMoves(x, y, 0, 1, p)) {
			possibleMoves.add(loc);
		}
		
		for(Location loc : getMoves(x, y, 0, (-1), p)) {
			possibleMoves.add(loc);
		}
		
		return toArray(possibleMoves);
	}
	
	public static Location[] getVerticalMoves(Piece p) {
		//Gerade Züge
		ArrayList<Location> possibleMoves = new ArrayList<Location>();
		
		Location start = p.getLocation();
		
		//Züge nach links
		int x = start.X;
		int y = start.Y;
		
		for(Location loc : getMoves(x, y, 1, 1, p)) {
			possibleMoves.add(loc);
		}
		
		for(Location loc : getMoves(x, y, (-1), 1, p)) {
			possibleMoves.add(loc);
		}
		
		for(Location loc : getMoves(x, y, 1, (-1), p)) {
			possibleMoves.add(loc);
		}
		
		for(Location loc : getMoves(x, y, (-1), (-1), p)) {
			possibleMoves.add(loc);
		}
		
		return toArray(possibleMoves);
	}
	
	public static Location[] getPawnMoves(Pawn p) {
		ArrayList<Location> possibleMoves = new ArrayList<Location>();
			
		Location frontLocation = new Location(p.getLocation().X, p.getLocation().Y + p.getDirection()),
				diagonalLeft = new Location(frontLocation.X - 1, frontLocation.Y),
				diagonalRight = new Location(frontLocation.X + 1, frontLocation.Y);
		
		Field.getFieldByLocation(frontLocation).setColor();
		
		Location[] locs = {frontLocation, diagonalLeft, diagonalRight};
		for(Location loc : locs) {
			
			System.out.println(loc.toString() + p.getMoveCode(loc));
			if(p.getMoveCode(loc) != Piece.NOT_POSSIBLE_MOVE) {
				possibleMoves.add(loc);
			}
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
	
	public static ArrayList<Location> getMoves(int x, int y, int x_operator, int y_operator, Piece p){
		ArrayList<Location> out = new ArrayList<Location>();
		boolean canContinue = true;
		
		while(canContinue) {
			x += x_operator;
			y += y_operator;
			
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
