package Movement.Generator;

import java.util.ArrayList;

import Main.main;
import Movement.Location;
import Movement.Move;
import Piece.Pawn;
import Piece.Piece;

public class PawnMoves {

	//cover true, wenn auch Felder ausgegeben werden sollen, welche die Figur deckt
	public static ArrayList<Move> get(Pawn p, boolean cover) {
		ArrayList<Move> out = new ArrayList<Move>();
		Location pawnLoc = p.getLocation();
		
		//Zuege nach vorne, nach links und nach rechts
		Location forward = new Location(pawnLoc.X, pawnLoc.Y + p.getDirection()),
				doubleForward = new Location(pawnLoc.X, pawnLoc.Y + (2 * p.getDirection())),
				diagonalLeft = new Location(forward.X - 1, forward.Y),
				diagonalRight = new Location(forward.X + 1, forward.Y);
		
		

		//En Passant
		Move latest = Move.latest;
		if(latest!=null) {
			if(latest.getPiece().getType() == Piece.TYPE_PAWN) {
				if(latest.getEnd().X == pawnLoc.X + 1 || latest.getEnd().X == pawnLoc.X - 1) { //Wenn Pawn daneben ist
					if(Math.abs(latest.getStart().Y - latest.getEnd().Y) == 2 && latest.getEnd().Y == pawnLoc.Y) { //Wenn Pawn eben doppelschritt gemacht hat und jetzt auf dem selben y ist
						
						Location newLoc = new Location(latest.getEnd().X, pawnLoc.Y + p.getDirection());
						
						Pawn lastPawn = (Pawn) latest.getPiece();
						out.add(new Move(p, newLoc, lastPawn, null, true));
					}
				}
			}
		}		
	
		
		out = addMoveWhenCondition(p, new Move(p, forward), new int[]{Piece.POSSIBLE_MOVE}, out, cover);
		out = addMoveWhenCondition(p, new Move(p, diagonalLeft, true), new int[]{Piece.CAN_HIT}, out, cover);
		out = addMoveWhenCondition(p, new Move(p, diagonalRight, true), new int[]{Piece.CAN_HIT}, out, cover);
		
		if(cover) {
			if(diagonalLeft.isInBoard()) out.add(new Move(p, diagonalLeft));
			if(diagonalRight.isInBoard()) out.add(new Move(p, diagonalRight));
			
			return out;
		}
		
		//Doppelt nach vorne
		if(p.getMoveCode(forward) == Piece.POSSIBLE_MOVE && !p.isAlreadyMoved()) {
			out = addMoveWhenCondition(p, new Move(p, doubleForward), new int[]{Piece.POSSIBLE_MOVE}, out, false);
		}
		
		return out;
	}
	

	//Methode fÃ¼gt Location hinzu, wenn eine der Bedingungen erfÃ¼llt ist
	private static ArrayList<Move> addMoveWhenCondition(Piece p, Move move, int[] conditions, ArrayList<Move> moves, boolean checkChess){
		for(int condition : conditions) {
			if(p.getMoveCode(move.getEnd()) == condition) {
				//Wenn im cover Mode, muss die Methode nur auf Schach überprüfen, im richtigen Mode werden die Züge hinzugefügt
				if(!checkChess) moves.add(move);
				
				if((condition == Piece.CAN_HIT) && move.getEnd().getField().getCurrentPiece().getType() == Piece.TYPE_KING) {
					main.check();
					main.howToStopChess.add(p.getLocation()); //Wenn er den KÃ¶nig schlagen kÃ¶nnte, muss er geschlagen werden um es zu unterbinden
				}
				break;
			}
		}
			
		return moves;
	}
	
}
