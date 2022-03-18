package Gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;

import Main.main;

public class gui {
	
	//225, 180, 122
	//156, 112, 56
	//168, 122, 61
	
	public static JFrame jf; //Fenster
	public static SideMenu side_menu;
	public static JPanel board, frame; //Panels
	public static final int WIDTH = 800, HEIGHT = 800; //Final Width und Height definiert
	public static final int BOARD_MARGIN = 0; //Margin vom Brett zur Kante
	public static final Color WHITE = new Color(255,253,208), BLACK = new Color(26,8,0); //Feld Farbwerte festgelegt
	public static final Color FIELD_BORDER = new Color(64, 41, 4);//Randfarbe festgelegt
	public static final Color SIDEMENU_BACKGROUND = FIELD_BORDER;//Randfarbe festgelegt
	public static final Color BACKGROUND = new Color(191, 144, 69); //Hintergrundfarbe für JFrame erstellt
	public static final Color MOVE_MARKER = new Color(61, 138, 56); //Farbe f�r die Markierungen, wenn man einen Zug machen kann
	public static final Color CHESS_MARKER = new Color(252, 48, 3); //Farbe f�r die Markierungen, wenn der König unter Schach steht
	public static final Color NO_WINNER = new Color(156, 112, 56); //Farbe f�r EndMenu wenn Unentschieden
	public static boolean graphics_rendered = false;
	
	public gui() {
		//Fenster wird erstellt
		jf = new JFrame("Chess");
		jf.setSize(WIDTH, HEIGHT);
		jf.setLocationRelativeTo(null);
		jf.setResizable(true);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//Spielbrett wird erstellt
		board = new JPanel();
		board.setLayout(new GridLayout(8, 8));
		board.setBackground(FIELD_BORDER);
		board.setBorder(BorderFactory.createLineBorder(FIELD_BORDER, jf.getWidth() / 80, false));
		
		//Seiten Menue, fuer Remi, Aufgeben & Rueckgaengig
		side_menu = new SideMenu();
		
		//Panel für Layoutverwaltung wird erstellt
		frame = new JPanel();
		jf.getContentPane().add(frame);
		frame.setLayout(new GridBagLayout());
		frame.setBackground(BACKGROUND);
		frame.addComponentListener(onFrameResize());
		
		
		//GridBagConstraints fuer board, damit das in der Mitte ist
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		
		frame.add(board, c);
		frame.add(side_menu);
		
	}
	
	//Das Schachbrett bleibt trotz resize quadratisch
	private ComponentAdapter onFrameResize() {
		return new ComponentAdapter() {
			
			@Override
			public void componentResized(ComponentEvent e) {
				resizeBoard();
			}
		};
		
	}
	
	//Methode damit Schachbrett immer quadratisch bleibt
	public static void resizeBoard() {
		int width = frame.getWidth(), height = frame.getHeight(); //deklarieren
		int min = width < height ? width : height; //kleineren Wert feststellen
			
		min -= BOARD_MARGIN * 2; //Margin einberechnen
		int field_size = min / 8;
		
		int menu_width = width < height ? field_size * 3 : field_size;
		int menu_height = height < width ? field_size * 3 : field_size;
		
		//GridBagConstraints fuer side_menu
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = menu_width < menu_height ? 1 : 0;
		c.gridy = menu_width < menu_height ? 0 : 1;
		
		side_menu.setLayout(menu_width < menu_height ? new GridLayout(3, 1) : new GridLayout(1, 3));
		
		side_menu.resizeAll();
				
		frame.remove(side_menu);
		frame.add(side_menu, c);
		
		side_menu.setPreferredSize(new Dimension(menu_width, menu_height));		
		board.setPreferredSize(new Dimension(min - field_size, min - field_size));
		
		board.setBorder(BorderFactory.createLineBorder(FIELD_BORDER, jf.getWidth() / 80, false));
		
		frame.revalidate();
	}
	
	public static void resizeFieldBorders() {
		for(Field f : main.allFields) f.resizeBorder();
	}
	
	//rendern des GUIs
	public void complete() {
		graphics_rendered = true;
		
		jf.setVisible(true);
		resizeFieldBorders();
	}
	
}
