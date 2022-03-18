package Main;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import DataStructures.Position_Array;
import Gui.Field;
import Gui.GameEndMenu;
import Gui.SideMenu;
import Gui.SwitchPieceMenu;
import Gui.gui;
import Movement.Location;
import Movement.Move;
import Piece.King;
import Piece.Piece;

public class main {

	
	public static boolean gameRuns = false;
	public static GameEndMenu gameEndMenu = null;
	public static final int ROWS = 8, COLS = 8; // Reihen und Spaltenanzahl
	public static Field[][] allFields2D = new Field[ROWS][COLS]; //2d array um geordnet Felder zu speichern
	public static Field[] allFields = new Field[ROWS * COLS]; //1d array um Felder zu iteraten
	public static ImageIcon pawn_white_img, knight_white_img, bishop_white_img, rock_white_img, queen_white_img, king_white_img; //uninstanzierte Bilder f�r wei�e Figuren
	public static ImageIcon pawn_black_img, knight_black_img, bishop_black_img, rock_black_img, queen_black_img, king_black_img; //uninstanzierte Bilder f�r schwarze Figuren
	
	
	public static final Class<?>[][] START_BOARD = 
			{{Piece.ROOK, Piece.KNIGHT, Piece.BISHOP, Piece.KING, Piece.QUEEN, Piece.BISHOP, Piece.KNIGHT, Piece.ROOK},
			{Piece.PAWN, Piece.PAWN, Piece.PAWN, Piece.PAWN, Piece.PAWN, Piece.PAWN, Piece.PAWN, Piece.PAWN},
			{Piece.EMPTY, Piece.EMPTY, Piece.EMPTY, Piece.EMPTY, Piece.EMPTY, Piece.EMPTY, Piece.EMPTY, Piece.EMPTY},
			{Piece.EMPTY, Piece.EMPTY, Piece.EMPTY, Piece.EMPTY, Piece.EMPTY, Piece.EMPTY, Piece.EMPTY, Piece.EMPTY},
			{Piece.EMPTY, Piece.EMPTY, Piece.EMPTY, Piece.EMPTY, Piece.EMPTY, Piece.EMPTY, Piece.EMPTY, Piece.EMPTY},
			{Piece.EMPTY, Piece.EMPTY, Piece.EMPTY, Piece.EMPTY, Piece.EMPTY, Piece.EMPTY, Piece.EMPTY, Piece.EMPTY},
			{Piece.PAWN, Piece.PAWN, Piece.PAWN, Piece.PAWN, Piece.PAWN, Piece.PAWN, Piece.PAWN, Piece.PAWN},
			{Piece.ROOK, Piece.KNIGHT, Piece.BISHOP, Piece.KING, Piece.QUEEN, Piece.BISHOP, Piece.KNIGHT, Piece.ROOK}}; //Startfiguren Verteilung auf dem Spielfeld
	
	/*
	public static final Class<?>[][] START_BOARD = 
		{{Piece.EMPTY, Piece.EMPTY, Piece.EMPTY, Piece.EMPTY, Piece.EMPTY, Piece.EMPTY, Piece.EMPTY, Piece.EMPTY},
		{Piece.EMPTY, Piece.EMPTY, Piece.EMPTY, Piece.EMPTY, Piece.EMPTY, Piece.EMPTY, Piece.EMPTY, Piece.EMPTY},
		{Piece.EMPTY, Piece.EMPTY, Piece.EMPTY, Piece.EMPTY, Piece.EMPTY, Piece.EMPTY, Piece.EMPTY, Piece.KING},
		{Piece.EMPTY, Piece.EMPTY, Piece.EMPTY, Piece.EMPTY, Piece.EMPTY, Piece.EMPTY, Piece.EMPTY, Piece.EMPTY},
		{Piece.EMPTY, Piece.EMPTY, Piece.EMPTY, Piece.EMPTY, Piece.EMPTY, Piece.EMPTY, Piece.EMPTY, Piece.EMPTY},
		{Piece.EMPTY, Piece.EMPTY, Piece.KING, Piece.EMPTY, Piece.EMPTY, Piece.EMPTY, Piece.EMPTY, Piece.EMPTY},
		{Piece.EMPTY, Piece.EMPTY, Piece.EMPTY, Piece.EMPTY, Piece.EMPTY, Piece.ROOK, Piece.EMPTY, Piece.EMPTY},
		{Piece.EMPTY, Piece.EMPTY, Piece.EMPTY, Piece.EMPTY, Piece.EMPTY, Piece.EMPTY, Piece.ROOK, Piece.EMPTY}};
	*/
	
	public static Piece lastSelected = null; //Letzte ausgew�hlte Figur
	public static final int COLOR_NONE = -1, COLOR_WHITE = 0, COLOR_BLACK = 1, COLOR_WHITE_DIRECTION = 1, COLOR_BLACK_DIRECTION = -1; //Farben und Richtungen f�r Movement
	public static int colorCanMove = COLOR_WHITE; //Farbe die f�hren darf
	public static ArrayList<Location> howToStopChess = new ArrayList<Location>();
	public static boolean check = false;
	public static int checkers = 0; //Wieviele Figuren sagen Schach
	public static Field chessMarked = null;
	public static final boolean WHITE_PIECES_FLIPPED = false, BLACK_PIECES_FLIPPED = false;
	
	
	public static void main(String[] args) {
		//Font resize end screen
		
		instiantiateImages(); //Figuren bekommen Aussehen
		
		gui g = new gui(); //GUI wird erstellt
		createFields(); //Felder werden erstellt
		restartGame();
		
		g.complete(); //JFrame wird sichtbar gemacht
	}
	
	public static void restartGame() {
		gameEndMenu = null;
		check = false;
		checkers = 0;
		Move.latest = null;
		colorCanMove = COLOR_WHITE;
		howToStopChess.clear();
		Position_Array.clear();
		Move.movesCapturePawn = 0;
		if(chessMarked != null) chessMarked.resetBorder();
		
		for(Field f : allFields) {
			f.removePiece();
		}
		
		Piece.all.clear();//alte Figuren entfernt
		
		putPieces(); //Figuren werden auf Spielfeld verteilt
		updatePieceCoverings(); //Es wird geupdatet, welche Figuren welche Felder decken
		updatePieceMoves(); //Es wird geupdatet, welche Figuren wohin moven k�nnen
		filterMoves(); //Falsche Züge werden weggemacht (Am Anfang ansich unnötig da alles geht)
		checkGameEnd();

		gameRuns = true;
	}
	
	public static void nextMove(boolean newMove) {
		check = false;
		checkers = 0;
		
		if(chessMarked != null) chessMarked.resetBorder();
		colorCanMove = colorCanMove == COLOR_WHITE ? COLOR_BLACK : COLOR_WHITE;
		howToStopChess.clear();
		
		for(Piece p : Piece.all) p.clearMoves();
		if(newMove) Position_Array.add(util.hashList(Piece.all.toArray()));
		
		updatePieceCoverings();
		updatePieceMoves();
		filterMoves();
		checkGameEnd();
		
		if(check == true) {
			for(Piece p : Piece.all) {
				if(p.getColor() == colorCanMove && p.getType() == Piece.TYPE_KING) {
					p.getLocation().getField().markChess();
				}
			}
		}
	}
	
	//Es wird geupdatet, welche Figuren welche Felder decken
	public static void updatePieceCoverings() {
		Field.clearCoverings();
		
		for(Piece p : Piece.all) {
			//if(p.getColor() == colorCanMove)
			p.updateCoverings();
			if(p.getCoverings() == null) continue;
			for(Move move : p.getCoverings()) {
				move.getEnd().getField().setCoveredBy(p.getColor(), true);
			}
		}
	}
	
	// Es wird geupdatet, welche Figuren wohin moven k�nnen
	public static void updatePieceMoves() {
		for(Piece p : Piece.all) {
			if(p.getColor() != colorCanMove) {
				p.clearMoves();
				continue;
			}
			p.updateRawMoves();
		}
	}
	
	//Ungültige Züge werden entfernt wie, wenn Schach ist oder eine Figur gebunden ist
	public static void filterMoves() {
		for(Piece p : Piece.all) {
			p.filterMoves();
		}
	}
	
	public static int checkGameEnd() {
		if(Position_Array.checkEnding()) {
			GameEnd.end(COLOR_NONE, GameEnd.REPEATING_MOVES);
		}
		
		boolean gotMoves = false;
		for(Piece p : Piece.all) {	
			if(p.getColor() != colorCanMove) continue;
			if(p.getPlayableMoves().size() > 0) gotMoves = true;
		}
		
		if(Piece.all.size() == 2) GameEnd.end(COLOR_NONE, GameEnd.NO_MATE_POSSIBLE);//Es sind nur noch 2 Kings auf dem Spielfeld
		if(Move.movesCapturePawn >= 50) {
			 GameEnd.end(COLOR_NONE, GameEnd.FIFTY_MOVES);
		}
		
		if(!gotMoves) {
			if(check) {
				GameEnd.end(util.switchColor(colorCanMove), GameEnd.CHECKMATE);
			}else {
				GameEnd.end(COLOR_NONE, GameEnd.STALEMATE);
			}
		}
		
		return -1;
	}
	
	private static void createFields() {
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
				//Wenn oben beim Spielfeld, dann color = Black sonst color = White
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

	
	
	private static void instiantiateImages() {
		String path_piece_base = "/pieces/";
		String path_sidemenu_base = "/side_menu/";
		
		pawn_white_img = imageloader.loadImage(path_piece_base + "pawn_white.png", WHITE_PIECES_FLIPPED);
		knight_white_img = imageloader.loadImage(path_piece_base + "knight_white.png", WHITE_PIECES_FLIPPED);
		bishop_white_img = imageloader.loadImage(path_piece_base + "bishop_white.png", WHITE_PIECES_FLIPPED);
		rock_white_img = imageloader.loadImage(path_piece_base + "rock_white.png", WHITE_PIECES_FLIPPED);
		queen_white_img = imageloader.loadImage(path_piece_base + "queen_white.png", WHITE_PIECES_FLIPPED);
		king_white_img = imageloader.loadImage(path_piece_base + "king_white.png", WHITE_PIECES_FLIPPED);
		
		pawn_black_img = imageloader.loadImage(path_piece_base + "pawn_black.png", BLACK_PIECES_FLIPPED);
		knight_black_img = imageloader.loadImage(path_piece_base + "knight_black.png", BLACK_PIECES_FLIPPED);
		bishop_black_img = imageloader.loadImage(path_piece_base + "bishop_black.png", BLACK_PIECES_FLIPPED);
		rock_black_img = imageloader.loadImage(path_piece_base + "rock_black.png", BLACK_PIECES_FLIPPED);
		queen_black_img = imageloader.loadImage(path_piece_base + "queen_black.png", BLACK_PIECES_FLIPPED);
		king_black_img = imageloader.loadImage(path_piece_base + "king_black.png", BLACK_PIECES_FLIPPED);
		

		SideMenu.remi = imageloader.loadImage(path_sidemenu_base + "remi.png");
		SideMenu.forfeit = imageloader.loadImage(path_sidemenu_base + "forfeit.png");
		SideMenu.back = imageloader.loadImage(path_sidemenu_base + "back.png");
	}
	
	//Berechnet ob ein Feld schwarz oder wei� ist
	//Formel: (x + y) % 2 == 0
	public static Color getFieldColor(int sum) {
		if(sum % 2 == 0) {
			return gui.BLACK;
		}
		
		return gui.WHITE;
	}
	
	//Figurenbilder werden auf richtige Gr��e gesetzt
	public static ImageIcon resizeImage(ImageIcon old, int newWidth, int newHeight) {
		return new ImageIcon(old.getImage().getScaledInstance(newWidth, newHeight, java.awt.Image.SCALE_SMOOTH));
	}
	
	public static void check() {	
		
		check = true;
		checkers++;
		
		for(Piece p : Piece.all) {
			if(p.getColor() == colorCanMove && p.getType() == Piece.TYPE_KING) {
				p.getLocation().getField().markChess();
			}
		}
	}
	
	//https://stackoverflow.com/questions/50883802/how-to-rotate-an-imageicon-in-java
	public static BufferedImage rotate(BufferedImage image, Double degrees) {
	    // Calculate the new size of the image based on the angle of rotaion
	    double radians = Math.toRadians(degrees);
	    double sin = Math.abs(Math.sin(radians));
	    double cos = Math.abs(Math.cos(radians));
	    int newWidth = (int) Math.round(image.getWidth() * cos + image.getHeight() * sin);
	    int newHeight = (int) Math.round(image.getWidth() * sin + image.getHeight() * cos);

	    // Create a new image
	    BufferedImage rotate = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
	    Graphics2D g2d = rotate.createGraphics();
	    // Calculate the "anchor" point around which the image will be rotated
	    int x = (newWidth - image.getWidth()) / 2;
	    int y = (newHeight - image.getHeight()) / 2;
	    // Transform the origin point around the anchor point
	    AffineTransform at = new AffineTransform();
	    at.setToRotation(radians, x + (image.getWidth() / 2), y + (image.getHeight() / 2));
	    at.translate(x, y);
	    g2d.setTransform(at);
	    // Paint the originl image
	    g2d.drawImage(image, 0, 0, null);
	    g2d.dispose();
	    return rotate;
	}
	
	public static ImageIcon flipImage(Image old) {
		return new ImageIcon(rotate((BufferedImage) old, 180.0d));
	}

}
