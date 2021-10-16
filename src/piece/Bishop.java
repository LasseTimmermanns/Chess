package piece;

import java.util.ArrayList;

import javax.swing.ImageIcon;

import main.Location;
import main.Move;
import main.Movement;
import main.main;

public class Bishop extends Piece{

	public Bishop(int color, Location loc) {		
		super("Laeufer", color, loc, color == 0 ? main.bishop_white_img : main.bishop_black_img, 3);
	}
	
	@Override
	public void updatePossibleMoves(){
		super.possibleMoves = Movement.getDiagonalMoves(this);
	}
}
