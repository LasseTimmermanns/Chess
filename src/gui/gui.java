package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class gui {
	
	public static JFrame jf; //Fenster
	public static JPanel board, frame; //Panels
	public static final int WIDTH = 800, HEIGHT = 800; //Final Width und Height definiert
	public static final int BOARD_SIZE_MAX = 1000000; //Maximal Groesse fuer das Brett
	public static final int BOARD_MARGIN = 20; //Margin vom Brett zur Kante
	public static final Color WHITE = new Color(255,253,208), BLACK = new Color(26,8,0); //Feld Farbwerte festgelegt
	public static final Color FIELD_BORDER = new Color(77,38,0);//Randfarbe festgelegt
	public static final Color BACKGROUND = new Color(156, 112, 56); //Hintergrundfarbe für JFrame erstellt
	public static final Color MARKER = new Color(61, 138, 56); //Farbe f�r die Markierungen, wenn man einen Zug machen kann
	
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
		board.setBorder(BorderFactory.createLineBorder(FIELD_BORDER, 10, false));
		
		//Panel für Layoutverwaltung wird erstellt
		frame = new JPanel();
		jf.getContentPane().add(frame);
		frame.setLayout(new GridBagLayout());
		frame.setBackground(BACKGROUND);
		frame.addComponentListener(onFrameResize());
		frame.add(board);
		
		
	}
	
	//Das Schachbrett bleibt trotz resize quadratisch
	private ComponentAdapter onFrameResize() {
		return new ComponentAdapter() {
			
			@Override
			public void componentResized(ComponentEvent e) {
				boardToSquare();
			}
		};
		
	}
	
	public static void boardToSquare() {
		int width = frame.getWidth(), height = frame.getHeight(); //deklarieren
		int min = width < height ? width : height; //kleineren Wert feststellen
		min = min > BOARD_SIZE_MAX ? BOARD_SIZE_MAX : min; //Max Board size eingebracht
		
		
		
		min -= BOARD_MARGIN * 2; //Margin einberechnen
		
		board.setPreferredSize(new Dimension(min, min));
		frame.revalidate();
	}
	
	//rendern des GUIs
	public void complete() {
		jf.setVisible(true);
		
		System.out.println("complete");
	}
	
}
