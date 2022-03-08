package Gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.border.MatteBorder;

import Listeners.GameEndMenuListeners;
import Main.GameEnd;
import Main.main;

public class GameEndMenu extends JDialog{

	private static Color foreground, background;
	private static JPanel jp;
	
	public GameEndMenu(String title, String subTitle, Color foreground, Color background, int reason) {
		this.foreground = foreground;
		this.background = background;
		
		main.gameEndMenu = this;

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.weightx = 1;
		gbc.weighty = 0.25;
		gbc.fill = GridBagConstraints.BOTH;
		
		setUndecorated(true);
		setSize(350, 350 / 2 + 35);
		setLocationRelativeTo(gui.jf);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setModal(true);
        setTitle(title);
        getContentPane().setBackground(background);      
		setLocationRelativeTo(gui.jf);
		setBackground(background);
        setLayout(new GridBagLayout());
        getRootPane().setBorder(BorderFactory.createLineBorder(gui.FIELD_BORDER, 10));
       
		JLabel jl = new JLabel(title, SwingConstants.CENTER);
		jl.setFont(new Font("ARIAL", Font.BOLD, 24));
		jl.setForeground(foreground);
        jl.setBackground(background);
		jl.setVisible(true);
		
		gbc.gridy = 0;
		add(jl, gbc);
		
		JLabel jl2 = new JLabel(subTitle, SwingConstants.CENTER);
		jl2.setFont(new Font("ARIAL", Font.PLAIN, 12));
		jl2.setForeground(foreground);
        jl2.setBackground(background);
		jl2.setVisible(true);
		
		gbc.gridy = 1;
		
		add(jl2, gbc);
		
		jp = new JPanel();
		jp.setLayout(new GridLayout(2, 2));

		if(reason == GameEnd.FORFEIT || reason == GameEnd.REMI) {
			createJB("Abbrechen", getMatteBorder(4, 0, 2, 2), GameEndMenuListeners.getUndoForfeitListener());
		}else {
			createJB("Zug zurücksetzen", getMatteBorder(4, 0, 2, 2), GameEndMenuListeners.getUndoListener());
		}
		
		createJB("Brett ansehen", getMatteBorder(4, 2, 2, 0), GameEndMenuListeners.getWatchListener());
		createJB("Beenden", getMatteBorder(2, 0, 0, 2), GameEndMenuListeners.getCloseListener());
		createJB("Erneut Spielen", getMatteBorder(2, 2, 0, 0), GameEndMenuListeners.getAgainListener());
		
		gbc.gridy = 2;
		gbc.gridheight = 2;
		
		add(jp, gbc);
		jp.setVisible(true);
		setVisible(true);
	}
	
	private static void createJB(String text, MatteBorder border, MouseListener listener) {
		JButton jb = new JButton();
		jb.setBorder(border);
		jb.setText(text);
		jb.setFocusPainted(false);
		jb.setBackground(background);
		jb.setForeground(foreground);
		jb.setFont(new Font("ARIAL", Font.PLAIN, 14));
		jb.addMouseListener(listener);
		jb.setVisible(true);
		
		jp.add(jb);
	}
	
	private static MatteBorder getMatteBorder(int a, int b, int c, int d) {
		return BorderFactory.createMatteBorder(a, b, c, d, gui.FIELD_BORDER);	
	}
	
	public static void display() {
		main.gameEndMenu.setVisible(true);
	}
	
	public static void switchDisplay() {
		main.gameEndMenu.setVisible(!main.gameEndMenu.isVisible());
	}
	
}
