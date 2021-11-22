package Main;

import java.awt.Color;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import javax.swing.ImageIcon;

import Gui.Field;
import Gui.gui;
import Movement.Location;
import Movement.Move;
import Piece.Piece;

public class main {

	public static final int ROWS = 8, COLS = 8;
	public static Field[][] allFields2D = new Field[ROWS][COLS]; //2d array um geordnet Felder zu speichern
	public static Field[] allFields = new Field[ROWS * COLS];
	public static ImageIcon pawn_white_img, knight_white_img, bishop_white_img, rock_white_img, queen_white_img, king_white_img;
	public static ImageIcon pawn_black_img, knight_black_img, bishop_black_img, rock_black_img, queen_black_img, king_black_img;
	
	public static final Class<?>[][] START_BOARD = 
			{{Piece.EMPTY, Piece.KNIGHT, Piece.BISHOP, Piece.KING, Piece.QUEEN, Piece.BISHOP, Piece.KNIGHT, Piece.EMPTY},
			{Piece.PAWN, Piece.PAWN, Piece.PAWN, Piece.PAWN, Piece.PAWN, Piece.PAWN, Piece.PAWN, Piece.PAWN},
			{Piece.EMPTY, Piece.EMPTY, Piece.EMPTY, Piece.EMPTY, Piece.EMPTY, Piece.EMPTY, Piece.EMPTY, Piece.EMPTY},
			{Piece.EMPTY, Piece.EMPTY, Piece.EMPTY, Piece.ROOK, Piece.EMPTY, Piece.EMPTY, Piece.EMPTY, Piece.EMPTY},
			{Piece.EMPTY, Piece.EMPTY, Piece.EMPTY, Piece.EMPTY, Piece.EMPTY, Piece.EMPTY, Piece.EMPTY, Piece.EMPTY},
			{Piece.EMPTY, Piece.EMPTY, Piece.EMPTY, Piece.EMPTY, Piece.EMPTY, Piece.EMPTY, Piece.EMPTY, Piece.EMPTY},
			{Piece.PAWN, Piece.PAWN, Piece.PAWN, Piece.PAWN, Piece.PAWN, Piece.PAWN, Piece.PAWN, Piece.PAWN},
			{Piece.EMPTY, Piece.KNIGHT, Piece.BISHOP, Piece.KING, Piece.QUEEN, Piece.BISHOP, Piece.KNIGHT, Piece.EMPTY}};
	public static Piece lastSelected = null;
	public static final int COLOR_WHITE = 0, COLOR_BLACK = 1, COLOR_WHITE_DIRECTION = 1, COLOR_BLACK_DIRECTION = -1;
	public static int colorCanMove = COLOR_BLACK;
	
	
	public static void main(String[] args) {
		instiantiateSkins();
		
		gui g = new gui(); //Gui wird erstellt
		createFields(); //Felder werden erstellt
		putPieces();
		
		g.complete(); //JFrame wird sichtbar gemacht
		
		updatePieceCoverings();
		updatePieceMoves();
		
	}
	
	public static void nextMove(Piece p, Location end, boolean firstMove) {
		if(firstMove) {
			nextMove(null, firstMove);
			return;
		}
		
		
	}
	
	public static void nextMove(Move move, boolean firstMove) {
		colorCanMove = colorCanMove == COLOR_WHITE ? COLOR_BLACK : COLOR_WHITE;
		//colorCanMove = COLOR_WHITE;
		
		move.setPlayed();
		
		updatePieceCoverings();
		updatePieceMoves();
	}
	
	public static void updatePieceCoverings() {
		for(Piece p : Piece.all) {
			p.updateCoverings();
			if(p.getCoverings() == null) continue;
			for(Move move : p.getCoverings()) {
				move.getEnd().getField().setCoveredBy(p.getColor(), true);
			}
		}
	}
	
	public static void updatePieceMoves() {
		for(Piece p : Piece.all) {
			if(p.getColor() != colorCanMove) {
				p.clearPossibleMoves();
				continue;
			}
			p.updatePossibleMoves();
		}
	}
	
	private static void createFields() {
		
		//Schachbrett 8x8 Felder
		for(int y = ROWS - 1; y >= 0; y--) {
			for(int x = 0; x < COLS; x++) {
				//Ordnung im 2d array durch diese Schleifen
				
				allFields2D[y][x] = new Field(new Location(x, y));
				allFields[y * ROWS + x] = allFields2D[y][x]; //DAVOR zuerst x und dann y
			}
		}
	}

	private static void putPieces() {
		for(int y = 7; y >= 0; y--) {
			for(int x = 0; x < COLS; x++) {
				int color = y < (COLS / 2) ? COLOR_WHITE : COLOR_BLACK;
				Class<?> piece = START_BOARD[7- y][7- x];				
				
				if(piece != null) {
					try {
						Constructor<?> con = piece.getConstructor(int.class, Location.class);
						con.newInstance(color, new Location(x, y));
					} catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
						e.printStackTrace();
					}
				}	
			}
		}
	}

	
	private static void instiantiateSkins() {
		String path_base = "/pieces/";
		
		pawn_white_img = imageloader.loadImage(path_base + "pawn_white.png");
		pawn_black_img = imageloader.loadImage(path_base + "pawn_black.png");
		knight_white_img = imageloader.loadImage(path_base + "knight_white.png");
		knight_black_img = imageloader.loadImage(path_base + "knight_black.png");
		bishop_white_img = imageloader.loadImage(path_base + "bishop_white.png");
		bishop_black_img = imageloader.loadImage(path_base + "bishop_black.png");
		rock_white_img = imageloader.loadImage(path_base + "rock_white.png");
		rock_black_img = imageloader.loadImage(path_base + "rock_black.png");
		queen_white_img = imageloader.loadImage(path_base + "queen_white.png");
		queen_black_img = imageloader.loadImage(path_base + "queen_black.png");
		king_white_img = imageloader.loadImage(path_base + "king_white.png");
		king_black_img = imageloader.loadImage(path_base + "king_black.png");
	}
	
	//Berechnet ob ein Feld schwarz oder weiss ist
	//Formel: (x + y) % 2 == 0
	public static Color getFieldColor(int sum) {
		if(sum % 2 == 0) {
			return gui.BLACK;
		}
		
		return gui.WHITE;
	}
	
	public static ImageIcon resizeImage(ImageIcon old, int newWidth, int newHeight) {
		return new ImageIcon(old.getImage().getScaledInstance(newWidth, newHeight,  java.awt.Image.SCALE_SMOOTH));
	}

}
