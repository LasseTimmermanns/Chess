package main;

import gui.Field;

public class Location {

	public final int X, Y;
	public Location(int x, int y) {
		this.X = x;
		this.Y = y;
	}
	
	public boolean isInBoard() {
		return X >= 0 && X < main.COLS && Y >= 0 && Y < main.ROWS;
	}
	
	public Field getField() {
		if(!isInBoard()) {
			System.out.println("getField not in Boardrange");
			 return null;
		}
		
		return Field.getFieldByLocation(this);
	}
	
	public String toString() {
		return "(" + Integer.toString(X) + "|" + Integer.toString(Y) + ")";
	}

}
