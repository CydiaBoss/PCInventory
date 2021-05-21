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
	 * Searches the database for a device with a host name
	 * 
	 * @param host
	 * identifier (host name)
	 */
	public static void searchFor(String host) {
		// Ignore Cases
		final String term = host.toLowerCase();
		// Results Array
		ArrayList<Device> results = new ArrayList<>();
		// Look for
		Main.devices.parallelStream().filter(dev -> {
			if(dev.getHost().toLowerCase().contains(term)) 
				return true;
			else
				return false;
		}).forEach(dev -> 
			results.add(dev)
		);
		// Rebuilt Table with Results If Results Found
		if(results.size() == 0)
			JOptionPane.showMessageDialog(Main.front.getFrame(), "No Results Found", "Search Error", JOptionPane.INFORMATION_MESSAGE);
		else
			Main.front.tableRebuild(results);
	}
	
	/**
	 * Searches the database for a device with a host name
	 * 
	 * @param searchFor
	 * Which parameters to look for
	 * @param exclude
	 * If the search should be exclusion or inclusion
	 * @param term
	 * Search terms
	 */
	public static void searchFor(boolean[] searchFor, boolean[] exclude, ArrayList<String> terms) {
		// Search
		Main.devices.parallelStream().filter(device -> {
			boolean valid = true;
			// TODO Add Other Properties
			// Object[] deviceProp = {device.getHost(), device.getSerial(), device.getModel()};
			for(int i = 0; i < searchFor.length; i++) {
				// Skip if check box is false
				if(!searchFor[i])
					continue;
			}
			return valid;
		});
	}
}
