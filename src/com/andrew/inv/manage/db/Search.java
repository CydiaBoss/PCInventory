package com.andrew.inv.manage.db;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import com.andrew.inv.manage.Main;

/**
 * Search Bar Controller
 * 
 * @author andre
 *
 */
public class Search {

	/**
	 * Searches the database for a device
	 * 
	 * @param term
	 * identifier
	 */
	public static void searchFor(String term) {
		// Ignore Cases
		term = term.toLowerCase();
		// Results Array
		ArrayList<Device> results = new ArrayList<>();
		boolean found = false;
		// Search Through Hostnames
		for(Device d : Main.devices) {
			if(d.getHost().toLowerCase().contains(term)) {
				results.add(d);
				found = true;
			}
		}
		// Rebuilt Table with Results If Results Found
		if(!found)
			JOptionPane.showMessageDialog(Main.front.getFrame(), "No Results Found");
		else
			Main.front.tableRebuild(results);
	}
	
}
