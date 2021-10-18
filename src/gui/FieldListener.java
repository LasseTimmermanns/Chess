package gui;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import main.main;
import piece.Piece;

public class FieldListener implements MouseListener{

	Field field;
	
	public FieldListener(Field field) {
		this.field = field;
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		
		//Feld leeren
		if(field.isMarked() && main.lastSelected != null) {
			main.lastSelected.move(field);
			main.nextMove();
		}
		
		for(Field f : field.getMarked()) {
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
