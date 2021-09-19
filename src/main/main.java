package main;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import gui.Field;
import gui.gui;
import piece.Bishop;
import piece.King;
import piece.Knight;
import piece.Pawn;
import piece.Piece;
import piece.Queen;
import piece.Rock;

public class main {

	public static final int ROWS = 8, COLS = 8;
	public static Field[][] allFields2D = new Field[ROWS][COLS]; //2d array um geordnet Felder zu speichern
	public static Field[] allFields = new Field[ROWS * COLS];
	public static ImageIcon pawn_white_img, knight_white_img, bishop_white_img, rock_white_img, queen_white_img, king_white_img;
	public static ImageIcon pawn_black_img, knight_black_img, bishop_black_img, rock_black_img, queen_black_img, king_black_img;
	public static final Class PAWN = Pawn.class, KNIGHT = Knight.class, BISHOP = 
			Bishop.class, ROCK = Rock.class, QUEEN = Queen.class, KING = King.class, EMPTY = null;
	public static final Class[][] PIECES_ON_BOARD = 
			{{ROCK, KNIGHT, BISHOP, QUEEN, KING, BISHOP, KNIGHT, ROCK},
			{PAWN, PAWN, PAWN, PAWN, PAWN, PAWN, PAWN, PAWN},
			{EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
			{EMPTY, EMPTY, EMPTY, ROCK, EMPTY, EMPTY, EMPTY, EMPTY},
			{EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
			{EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
			{PAWN, PAWN, PAWN, PAWN, PAWN, PAWN, PAWN, PAWN},
			{ROCK, KNIGHT, BISHOP, QUEEN, KING, BISHOP, KNIGHT, ROCK}};
	
	public static void main(String[] args) {
		instiantiateSkins();
		
		gui g = new gui(); //Gui wird erstellt
		createFields(); //Felder werden erstellt
		putPieces();
		
		g.complete(); //JFrame wird sichtbar gemacht
		
		
		
	}
	
	private static void createFields() {
		//Schachbrett 8x8 Felder
		for(int x = 0; x < ROWS; x++) {
			for(int y = 0; y < COLS; y++) {
				//Ordnung im 2d array durch diese Schleifen
				allFields2D[x][y] = new Field(new Location(x, y));
				allFields[x * ROWS + y] = allFields2D[x][y];
			}
		}
	}

	private static void putPieces() {
		for(int x = 0; x < ROWS; x++) {
			for(int y = 0; y < COLS; y++) {
				//WHITE = 0, BLACK = 1
				int color = y < (COLS / 2) ? 1 : 0;
				Class piece = PIECES_ON_BOARD[y][x];				
				
				if(piece != null) {
					try {
						Constructor con = piece.getConstructor(int.class, Location.class);
						Object xyz = con.newInstance(color, new Location(y, x));
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
			return gui.WHITE;
		}
		
		return gui.BLACK;
	}
	
	public static ImageIcon resizeImage(ImageIcon old, int newWidth, int newHeight) {
		return new ImageIcon(old.getImage().getScaledInstance(newWidth, newHeight,  java.awt.Image.SCALE_SMOOTH));
	}

}