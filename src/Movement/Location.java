package Movement;

import java.util.Objects;

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

	@Override
	public int hashCode() {
		return Objects.hash(X, Y);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Location other = (Location) obj;
		return X == other.X && Y == other.Y;
	}
	
	

}
