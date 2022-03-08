package Gui;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import Listeners.SwitchPieceMenuListener;
import Main.main;
import Movement.Move;
import Piece.Pawn;

public class SwitchPieceMenu extends JDialog{

	Pawn pawn;
	
	public SwitchPieceMenu(Pawn pawn) {
		this.pawn = pawn;

        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        setUndecorated(true);
		setSize(400, 400 / 4 + 40);
		setLocationRelativeTo(gui.jf);
		//setAlwaysOnTop(true);
        setModal(true);
        setTitle("Figurauswahl");
        setLayout(new GridLayout(1, 4));
        createJLabels();
		setVisible(true);
	}
	
	private void createJLabels() {
		ImageIcon[] icons;
		Color bg;
		if(main.colorCanMove == main.COLOR_WHITE) {
			ImageIcon[] tempicons = {main.knight_white_img, main.bishop_white_img, main.rock_white_img, main.queen_white_img};
			bg = gui.BLACK;
			icons = tempicons;
		}else {
			ImageIcon[] tempicons = {main.knight_black_img, main.bishop_black_img, main.rock_black_img, main.queen_black_img};
			bg = gui.WHITE;
			icons = tempicons;
		}
		
		for(int i = 0; i < 4; i++) {
			add(getJLabel(i, icons[i], bg));
		}
	}
	
	private JLabel getJLabel(int index, ImageIcon icon, Color bg) {
		JLabel jl = new JLabel(Integer.toString(index));
		jl.setBackground(bg);
		jl.setOpaque(true);
		jl.setBorder(BorderFactory.createLineBorder(gui.FIELD_BORDER, 3, false)); //Border
		jl.setIcon(main.resizeImage(icon, 100, 100));
		
		jl.addMouseListener(new SwitchPieceMenuListener(index, pawn, this));
		return jl;
	}
	
	
}
