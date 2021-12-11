package Gui;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import Main.main;
import Main.util;
import Movement.Location;
import Movement.Move;
import Piece.Piece;
import Piece.Rook;

public class FieldListener implements MouseListener{

	Field field;
	
	public FieldListener(Field field) {
		this.field = field;
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		
		if(field.isMarked()) { //Zug wird gemacht
			for(Move m : new ArrayList<Move>(main.lastSelected.getPossibleMoves())) {
				if(m.getEnd().getField().equals(field)) { //Richtiger Zug
					
					//Alle gemarkten werden unmarked
					for(Field f : Field.getMarked()) {
						f.mark();	
					}
					
					m.setPlayed();
				}
			}	
		}else { //Neue Figur wird ausgewählt oder marker werden entfernt
			//Alle gemarkten werden unmarked
			for(Field f : Field.getMarked()) {
				f.mark();	
			}
			
			if(field.isOccupied()) {				
				Piece selected = field.getCurrentPiece();
				
				if(!selected.movesAreMarked()) {
					selected.markMoves();
				}
				
				if(main.lastSelected != null) main.lastSelected.setMovesAreMarked(false);
				
				main.lastSelected = selected;
			}		
		}
		
		/*
		if(field.isMarked() && main.lastSelected != null) { //Wenn das Feld markiert ist
			Piece p = main.lastSelected;
			Location end = field.getPosition();
			
			//Rochade
			if(p.getClass() == Piece.KING) {
				int differenz = p.getLocation().X - field.getPosition().X;
				if(Math.abs(differenz) == 2) {
					for(Rook r : Rook.getAll()) {
						if(r.getColor() != p.getColor()) continue;
						
						//Linke Rochade
						if(r.getSide() == -1 && differenz == 2) {
							 r.move(Field.getFieldByLocation(new Location(p.getLocation().X - 1, p.getLocation().Y)));
						}
						
						//Rechte Rochade
						if(r.getSide() == 1 && differenz == -2) {
							 r.move(Field.getFieldByLocation(new Location(p.getLocation().X + 1, p.getLocation().Y)));
						}
					}
					
				}
			}
			
			//Move wird durchgef�hrt
			p.move(field);
			main.nextMove(util.findMove(p, end), false);
		}*/
		
		
		
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}	
}
