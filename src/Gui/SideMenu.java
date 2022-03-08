package Gui;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Listeners.SideMenuListeners;
import Main.main;
import sun.awt.www.content.image.jpeg;

public class SideMenu extends JPanel{

	public static ImageIcon remi, forfeit, back;
	private static SideMenu menu = null;
	private static HashMap<JLabel, ImageIcon> labels_icon = new HashMap<JLabel, ImageIcon>();
	
	public SideMenu() {
		menu = this;
		
		setLayout(new GridLayout(1, 3));
		setBackground(new Color(135, 67, 0));
		setVisible(true);
		
		labels_icon.put(createJLabel(forfeit, SideMenuListeners.forfeit()), forfeit);
		labels_icon.put(createJLabel(back, SideMenuListeners.back()), back);
		labels_icon.put(createJLabel(remi, SideMenuListeners.remi()), remi);
		
		for(JLabel jl : labels_icon.keySet()) add(jl);
		
	}
	
	public JLabel createJLabel(ImageIcon icon, MouseListener listener) {
		JLabel jl = new JLabel(icon, JLabel.CENTER);
		jl.addComponentListener(onResize(jl, icon));
		jl.addMouseListener(listener);
		jl.setOpaque(false);
		
		return jl;
	}
	
	public void resizeIcon(JLabel jl, ImageIcon icon) {
		int w = jl.getWidth() - jl.getWidth() / 6, h = jl.getHeight() - jl.getHeight() / 6;
		int size = w > h ? h : w;
		jl.setIcon(main.resizeImage(icon, size, size));
	}

	public static void resizeAll() {
		
		for(Entry<JLabel, ImageIcon> entry : labels_icon.entrySet()) {
			menu.resizeIcon(entry.getKey(), entry.getValue());
		}
	}
	
	private ComponentAdapter onResize(JLabel jl, ImageIcon icon) {
		return new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				resizeIcon(jl, icon);
			}
		};
		
	}
}
