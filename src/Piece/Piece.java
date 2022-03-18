package Piece;

import java.util.ArrayList;
import java.util.Objects;

import javax.swing.ImageIcon;

import Gui.Field;
import Main.main;
import Movement.Location;
import Movement.Move;

public class Piece {
	
	public static final int NULL = -1, NOT_POSSIBLE_MOVE = 0, POSSIBLE_MOVE = 1, CAN_HIT = 2, IS_COVERING = 3;
	public static final Class<?> PAWN = Pawn.class, KNIGHT = Knight.class, BISHOP = 
			Bishop.class, ROOK = Rook.class, QUEEN = Queen.class, KING = King.class, EMPTY = null;
	public static final int TYPE_PAWN = 0, TYPE_KNIGHT = 1, TYPE_BISHOP = 2, TYPE_ROOK = 3, TYPE_QUEEN = 4, TYPE_KING = 5;
	public static ArrayList<Piece> all = new ArrayList<Piece>();
	private ImageIcon icon;
	private String name;
	private Location location;
	private Field field;
	protected ArrayList<Move> coverings = new ArrayList<Move>(), rawMoves = new ArrayList<Move>(), playableMoves = new ArrayList<Move>();
	protected ArrayList<Location> possibleLocations = new ArrayList<Location>(), bindedLocations = new ArrayList<Location>();
	private int value, color, type, moves = 0;
	private boolean movesAreMarked;
	
	
	
	public Piece(int type, String name, int color, Location loc, ImageIcon icon, int value) {
		this.type = type;
		this.name = name;
		this.color = color;
		this.location = loc;
		this.icon = icon;
		this.value = value;
		this.movesAreMarked = false;
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
		moves++;
		field.removePiece();
		
		field = newLoc;
		location = newLoc.getPosition();
		
		newLoc.setPiece(this);
	}
	
	public boolean isAlreadyMoved() {
		return moves > 0;
	}
	
	public int getMoves() {
		return moves;
	}
	
	public void setMoves(int moves) {
		this.moves = moves;
	}
	
	public ArrayList<Move> getPlayableMoves() {
		return playableMoves;
	}
	
	public void addPossibleLocations(ArrayList<Location> arrList) {
		possibleLocations.addAll(arrList);
	}
	
	public void addBindedLocation(Location loc) {
		bindedLocations.add(loc);
	}
	
	public void updateRawMoves() {}
	
	public void clearMoves() {
		rawMoves.clear();
		possibleLocations.clear();
		bindedLocations.clear();		
		playableMoves.clear();
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
		for(Move m : this.getPlayableMoves()) {
			m.getEnd().getField().markMove();
		}
		
		this.movesAreMarked = !movesAreMarked;
	}	
	
	public int getType() {
		return type;
	}
	
	public void kick() {
		getLocation().getField().removePiece();
		Piece.all.remove(this);
	}
	
	public void revive() {
		Piece.all.add(this);
	}
	
	public void filterMoves() {
		
		if(bindedLocations.isEmpty()) {
			possibleLocations.addAll(main.howToStopChess);
		}else {
			possibleLocations.addAll(bindedLocations);
		}
		
		//Wenn Liste leer ist, sind alle Züge möglich
		//König darf sich immer (wenn das Gebiet nicht besetzt ist) bewegen
		
		if(possibleLocations.size() == 0 || getType() == TYPE_KING) {
			playableMoves.addAll(rawMoves);
			return;
		}
		
		if(main.check && !bindedLocations.isEmpty()) return; //Wenn die Figut gebunden ist und Schach ist, kann sich diese nicht bewegen
		if(main.checkers > 1) return;  //Wenn mehr als eine Figur Schach "sagt", kann nur der König sich bewegen um Schach zu verhindern
		
		for(Move m : new ArrayList<Move>(rawMoves)) {
			boolean correctMove = false;
			for(Location loc : possibleLocations) {
				if(m.getEnd().toString().equals(loc.toString())) {
					correctMove = true;
					break;
				}
			}
			
			if(correctMove) playableMoves.add(m);
		}
	}
	
	public String toString() {
		return getName() + " " + Integer.toString(getColor());
	}

	@Override
	public int hashCode() {
		return Objects.hash(color, type, location.hashCode());
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Piece other = (Piece) obj;
		return color == other.color && type == other.type && location.equals(other.location);
	}
	
	
}
