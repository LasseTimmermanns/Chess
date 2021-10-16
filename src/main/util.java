package main;

import java.util.ArrayList;

import gui.Field;

public class util {

	public static Location[] mergeLocations(Location[] location1, Location[] location2) {
		Location[] out = new Location[location1.length + location2.length];
		
		for(int i = 0; i < location1.length; i++) {
			out[i] = location1[i];
		}
		
		for(int i = location1.length; i < location1.length + location2.length; i++) {
			out[i] = location2[i - location1.length];
		}
		
		
		return out;
	}
	
	public static Location[] loclistToArray(ArrayList<Location> list) {
		Location[] out = new Location[list.size()];
		for(int i = 0; i < list.size(); i++) {
			out[i] = list.get(i);
		}
		
		return out;
	}
	
	public static Field[] fieldListToArray(ArrayList<Field> list) {
		Field[] out = new Field[list.size()];
		for(int i = 0; i < list.size(); i++) {
			out[i] = list.get(i);
		}
		
		return out;
	}
	
}
