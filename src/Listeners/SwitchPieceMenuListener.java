package Listeners;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import Gui.SwitchPieceMenu;
import Main.main;
import Piece.Bishop;
import Piece.Knight;
import Piece.Pawn;
import Piece.Piece;
import Piece.Queen;
import Piece.Rook;

public class SwitchPieceMenuListener implements MouseListener{

	private int index;
	private Pawn pawn;
	private SwitchPieceMenu menu;
	
	public SwitchPieceMenuListener(int index, Pawn pawn, SwitchPieceMenu menu) {
		this.index = index;
		this.pawn = pawn;
		this.menu = menu;
	}
	
	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		pawn.kick();		
		
		switch(index) {
		case 0:
			//Springer
			new Knight(main.colorCanMove, pawn.getLocation());
			break;
		case 1:
			//Läufer
			new Bishop(main.colorCanMove, pawn.getLocation());
			break;
		case 2:
			//Turm
			new Rook(main.colorCanMove, pawn.getLocation(), 0);
			break;
		case 3:
			//Dame
			new Queen(main.colorCanMove, pawn.getLocation());
			break;
		}
		
		menu.dispose();
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