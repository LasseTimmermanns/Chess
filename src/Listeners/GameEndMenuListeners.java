package Listeners;

import Main.main;
import Movement.Move;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import Gui.gui;

public class GameEndMenuListeners {

	public static MouseListener getCloseListener() {
		return new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseReleased(MouseEvent e) {
				main.gameEndMenu.setVisible(false);
				main.gameEndMenu.dispose();
				
				gui.jf.setVisible(false);
				gui.jf.dispose();
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub

			}
		};
	}
	
	public static MouseListener getWatchListener() {
		return new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseReleased(MouseEvent e) {
				main.gameEndMenu.setVisible(false);
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub

			}
		};
	}
	
	public static MouseListener getAgainListener() {
		return new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(MouseEvent e) {


			}

			@Override
			public void mouseReleased(MouseEvent e) {

				main.gameEndMenu.setVisible(false);
				main.gameEndMenu.dispose();
				main.gameEndMenu = null;
				
				main.restartGame();
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub

			}
		};
	}
	
	public static MouseListener getUndoListener() {
		return new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(MouseEvent e) {


			}

			@Override
			public void mouseReleased(MouseEvent e) {

				main.gameEndMenu.setVisible(false);
				main.gameEndMenu.dispose();
				main.gameEndMenu = null;
				
				Move.back();
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub

			}
		};
	}
	
	public static MouseListener getUndoForfeitListener() {
		return new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(MouseEvent e) {


			}

			@Override
			public void mouseReleased(MouseEvent e) {
				main.gameEndMenu.setVisible(false);
				main.gameEndMenu.dispose();
				main.gameEndMenu = null;
				main.gameRuns = true;
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub

			}
		};
	}
}
