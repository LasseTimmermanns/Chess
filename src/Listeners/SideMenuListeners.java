package Listeners;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import Main.GameEnd;
import Main.main;
import Main.util;
import Movement.Move;

public class SideMenuListeners {

	public static MouseListener back() {
		return new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				Move.back();
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		};
	}
	
	public static MouseListener forfeit() {
		return new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				GameEnd.end(util.switchColor(main.colorCanMove), GameEnd.FORFEIT);
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		};
	}
	
	public static MouseListener remi() {
		return new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				GameEnd.end(main.COLOR_NONE, GameEnd.REMI);
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		};
	}
}
