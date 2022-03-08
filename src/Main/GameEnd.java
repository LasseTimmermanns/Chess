package Main;

import java.awt.Color;

import Gui.GameEndMenu;
import Gui.gui;

public class GameEnd {

	public static final int NO_MATE_POSSIBLE = 100;
	public static final int FIFTY_MOVES = 101;
	public static final int CHECKMATE = 102;
	public static final int FORFEIT = 103;
	public static final int REMI = 104;
	public static final int STALEMATE = 105;
	public static final int REPEATING_MOVES = 106;
	
	
	public static void end(int winner, int game_reason) {
		String text = null;
		
		switch(game_reason) {
		case NO_MATE_POSSIBLE:
			text = "Kein Schachmatt mehr möglich";
			break;
		case FIFTY_MOVES:
			text = "In 50 Zügen wurde kein Bauer bewegt oder eine Figur geschlagen";
			break;
		case CHECKMATE:
			text = "Schachmatt";
			break;
		case FORFEIT:
			text = getColor(main.colorCanMove) + " hat aufgegeben";
			break;
		case REMI:
			text = "Remi";
			break;
		case STALEMATE:
			text = "Patt";
			break;
		case REPEATING_MOVES:
			text = "Dreimal die selbe Stellung";
			break;
		}		
		
		end(winner, text, game_reason);
	}
	
	public static String getColor(int color) {
		if(color == main.COLOR_WHITE) return "Weiß";
		return "Schwarz";
	}
	
	public static void end(int winner, String subtext, int game_reason) {
		main.gameRuns = false;
		
		switch(winner) {
		case main.COLOR_BLACK:
			new GameEndMenu("Schwarz gewinnt! ", subtext, gui.WHITE, gui.BLACK, game_reason);
			break;
		case main.COLOR_WHITE:
			new GameEndMenu("Weiß gewinnt! ", subtext, gui.BLACK, gui.WHITE, game_reason);
			break;
		case main.COLOR_NONE:
			new GameEndMenu("Unentschieden!", subtext, Color.BLACK, gui.NO_WINNER, game_reason);
			break;
		}		
	}
	
}
