package gui;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import main.Location;
import main.main;
import piece.Piece;
import piece.Rock;

public class FieldListener implements MouseListener{

	Field field;
	
	public FieldListener(Field field) {
		this.field = field;
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		
		//Feld leeren
		if(field.isMarked() && main.lastSelected != null) {
			//Rochade
			Piece p = main.lastSelected;
			Location start = p.getLocation();
			Location end = field.getPosition();
			
			//Rochade
			if(p.getClass() == Piece.KING) {
				int differenz = p.getLocation().X - field.getPosition().X;
				if(Math.abs(differenz) == 2) {
					for(Rock r : Rock.getAll()) {
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
			
			p.move(field);
			main.nextMove(p, start, end, false);
		}
		
		for(Field f : Field.getMarked()) {
			f.mark();	//Alle gemarkten werden unmarked
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
