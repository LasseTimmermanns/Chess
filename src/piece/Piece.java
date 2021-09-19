package piece;

import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

import gui.Field;
import main.Location;
import main.Move;
import main.main;

public class Piece {
	
	public static final int NOT_POSSIBLE_MOVE = 0, POSSIBLE_MOVE = 1, LAST_MOVE = 2;
	private ImageIcon icon;
	private String name;
	private Location location;
	private int value, color;
	
	
	public Piece(String name, int color, Location loc, ImageIcon icon, int value) {
		this.name = name;
		this.color = color;
		this.location = loc;
		this.icon = icon;
		this.value = value;
		
		Field.getFieldByLocation(loc).movePieceHere(this);
	}
	
	public int getMoveCode(Location current) {
		
		if(!current.isInBoard()) return NOT_POSSIBLE_MOVE;
		if(current.getField().isOccupied()) {
			Piece p = current.getField().getCurrentPiece();
			if(p.getColor() != getColor()) return LAST_MOVE;
			return NOT_POSSIBLE_MOVE;
		}
		
		return POSSIBLE_MOVE;
	
	}
	
	public Location[] getPossibleMoves() {
		return null;
	}
	
	public ImageIcon getIcon() {
		return icon;
	}

	public String getName() {
		return name;
	}

	public Location getLocation() {
		return location;
	}

	public int getValue() {
		return value;
	}

	public int getColor() {
		return color;
	}

	
	
	
}
