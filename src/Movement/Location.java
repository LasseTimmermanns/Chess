package Movement;

import Gui.Field;
import Main.main;

public class Location {

	public final int X, Y;
	public Location(int x, int y) {
		this.X = x;
		this.Y = y;
		
	}
	
	//true, wenn im Board
	public boolean isInBoard() {
		return X >= 0 && X < main.COLS && Y >= 0 && Y < main.ROWS;
	}
	
	//gibt Feld auf der Location zurueck
	public Field getField() {
		if(!isInBoard()) {
			System.out.println("Nicht inboard " + this.toString());
			return null;
		}
		
		return Field.getFieldByLocation(this);
	}
	
	public String toString() {
		return "(" + Integer.toString(X) + "|" + Integer.toString(Y) + ")";
	}

}
