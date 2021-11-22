package Gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import Main.main;
import Main.util;
import Movement.Location;
import Piece.Piece;

public class Field extends JLabel{

	private Color fieldColor;
	private boolean marked = false;
	private Location location;
	private Piece currentPiece;
	private boolean[] coveredBy;
	
	
	public Field(Location loc) {
		//Eigenschaften
		this.location = loc; //Location
		this.fieldColor = main.getFieldColor(loc.X + loc.Y); //Feldfarbe
		this.coveredBy = new boolean[]{false, false}; //Welche Farben das Feld decken
		
		setOpaque(true);
		setBackground(fieldColor);
		setVisible(true); //Sichtbar machen
		setBorder(BorderFactory.createLineBorder(gui.FIELD_BORDER, 3, false)); //Border
		gui.board.add(this); //Hinzufuegen zu dem Schachbrett
		
		// Wenn auf das Feld geklickt wird
		addMouseListener(new FieldListener(this));

		// Wenn Feld andere Gr��e bekommt
		addComponentListener(onResize());
		
	}

	public void mark() {
		marked = !marked;
		this.updateUI();
	}
	
	public static Field getFieldByLocation(Location loc) {
		return main.allFields2D[loc.Y][loc.X];
	}
	
	//Feld markieren bei m�glichen Zug
	protected void paintChildren(Graphics g) {
		super.paintChildren(g);
		
		Color c = marked ? gui.MARKER : new Color(0,0,0,0); // Wenn unmarked werden soll, Color unsichtbar
		int s = getWidth();
		g.setColor(c);
		g.fillOval(s / 3, s / 3, s / 3, s / 3);
		//g.fillOval(s/ 4 + s / 12, s / 4 + s / 12, s / 4, s / 4);
		
	}
	
	//Wenn Gr��e ver�ndert wird
	private ComponentAdapter onResize() {
		return new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				if(isOccupied()) {
					resizeIcon();
				}
			}
		};
		
	}
	
	public void removePiece() {
		currentPiece = null;
		setIcon(null);
	}
	
	public void setPiece(Piece p) {
		currentPiece = p;
		
		setIcon(p.getIcon());
		resizeIcon();
		resizeIcon();

	}
	
	public void resizeIcon() {
		setIcon(main.resizeImage((ImageIcon) getCurrentPiece().getIcon(), getWidth() - 10, getHeight() - 10));
	}
	
	public static void resizeAll() {
		for(Field f : main.allFields) {
			if(f.isOccupied()) {
				f.resizeIcon();
			}
		}
	}
	
	//Gibt alle markierten Felder zurueck
	public static Field[] getMarked() {
		ArrayList<Field> all = new ArrayList<Field>();
		for(Field f : main.allFields) {
			if(f.isMarked()) {
				all.add(f);
			}
		}
		
		return util.fieldListToArray(all);
	}
	
	public Color getFieldColor() {
		return fieldColor;
	}
	
	public boolean isMarked() {
		return marked;
	}
	
	public boolean isOccupied() {
		return currentPiece != null;
	}
	
	public Piece getCurrentPiece() {
		return currentPiece;
	}
	
	public boolean isCoveredBy(int color) {
		return coveredBy[color];
	}
	
	public void setCoveredBy(int color, boolean value) {
		coveredBy[color] = value;
	}
	
	public Location getPosition() {
		return location;
	}
	
}