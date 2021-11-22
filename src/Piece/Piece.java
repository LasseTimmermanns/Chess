package Piece;

import java.util.ArrayList;

import javax.swing.ImageIcon;

import Gui.Field;
import Movement.Location;
import Movement.Move;

public class Piece {
	
	public static final int NULL = -1, NOT_POSSIBLE_MOVE = 0, POSSIBLE_MOVE = 1, CAN_HIT = 2, IS_COVERING = 3;
	public static final Class<?> PAWN = Pawn.class, KNIGHT = Knight.class, BISHOP = 
			Bishop.class, ROOK = Rook.class, QUEEN = Queen.class, KING = King.class, EMPTY = null;
	public static ArrayList<Piece> all = new ArrayList<Piece>();
	private ImageIcon icon;
	private String name;
	private Location location;
	private Field field;
	protected ArrayList<Move> coverings = new ArrayList<Move>(), possibleMoves = new ArrayList<Move>();
	private int value, color;
	private boolean movesAreMarked, alreadyMoved;
	
	
	public Piece(String name, int color, Location loc, ImageIcon icon, int value) {
		this.name = name;
		this.color = color;
		this.location = loc;
		this.icon = icon;
		this.value = value;
		this.movesAreMarked = false;
		this.alreadyMoved = false;
		this.field = Field.getFieldByLocation(loc);
		
		
		field.setPiece(this);
		all.add(this);
	}
	
	public int getMoveCode(Location newLoc) {
		
		if(!newLoc.isInBoard()) return NOT_POSSIBLE_MOVE;
		if(newLoc.getField().isOccupied()) {
			Piece p = newLoc.getField().getCurrentPiece();
			if(p.getColor() != getColor()) return CAN_HIT;
			return IS_COVERING;
		}
		
		return POSSIBLE_MOVE;
	
	}
	
	public void move(Field newLoc) {
		alreadyMoved = true;
		field.removePiece();
		
		field = newLoc;
		location = newLoc.getPosition();
		
		newLoc.setPiece(this);
		
	}
	
	public boolean isAlreadyMoved() {
		return alreadyMoved;
	}
	
	public void setAlreadyMoved() {
		this.alreadyMoved = true;
	}
	
	public ArrayList<Move> getPossibleMoves() {
		return possibleMoves;
	}
	
	public void updatePossibleMoves() {}
	
	public void clearPossibleMoves() {
		possibleMoves.clear();
	}
	
	public ArrayList<Move> getCoverings() {
		return coverings;
	}
	
	public void updateCoverings() {}
	
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

	public boolean movesAreMarked() {
		return movesAreMarked;
	}
	
	public void setMovesAreMarked(boolean movesAreMarked) {
		this.movesAreMarked = movesAreMarked;
	}
	
	public void markMoves() {
		for(Move m : this.getPossibleMoves()) {
			m.getEnd().getField().mark();
		}
		
		this.movesAreMarked = !movesAreMarked;
	}
	
	
	
}