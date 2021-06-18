package com.andrew.inv.manage.db;

import java.time.LocalDate;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import com.andrew.inv.manage.Main;
import com.andrew.inv.manage.db.Device.Status;

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
	public static void searchFor(Main main, String host) {
		// Ignore Cases
		final String term = host.toLowerCase();
		// Results Array
		ArrayList<Device> results = new ArrayList<>();
		// Look for
		main.getDevices().parallelStream().filter(dev -> {
			if(dev.getHost().toLowerCase().contains(term)) 
				return true;
			else
				return false;
		}).forEach(dev -> 
			results.add(dev)
		);
		// Rebuilt Table with Results If Results Found
		if(results.size() == 0)
			JOptionPane.showMessageDialog(main.getFront().getFrame(), "No Results Found", "Search Error", JOptionPane.INFORMATION_MESSAGE);
		else
			main.getFront().tableRebuild(results);
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
	public static void searchFor(Main main, boolean[] searchFor, boolean[] exclude, String[] terms) {
		// Results Array
		ArrayList<Device> results = new ArrayList<>();
		// Cleanup Logic
		for(int h = 0; h < searchFor.length; h++)
			// If Include and Blank, Ignore This Field
			if(searchFor[h] && !exclude[h] && terms[h].trim().equals(""))
				searchFor[h] = false;
		// Search
		// Use stream for thread safe
		main.getDevices().stream().filter(device -> {
			boolean valid = true;
			// Device Properties
			Object[] deviceProp = {
				device.getHost(), 
				device.getSerial(), 
				device.getModel(),
				device.getOS(),
				device.getDateUpdated(),
				device.getStatus(),
				device.getUser(),
				device.getLoc()
			};
			// Testing
			for(int i = 0; i < searchFor.length; i++) {
				// Skip if check box is false
				if(!searchFor[i])
					continue;
				// Test for exclusion
				if(exclude[i]) {
					// If Property is a String
					if(deviceProp[i] instanceof String) {
						// If String equal (exclude)
						if(((String) deviceProp[i]).toLowerCase().contains(terms[i].toLowerCase())) {
							valid = false;
							break;
						}
					// If Property is Date
					}else if(deviceProp[i] instanceof LocalDate){
						// If Date equal (exclude)
						if(((LocalDate) deviceProp[i]).toString().equals(terms[i])) {
							valid = false;
							break;
						}
					// If Property is Status
					}else{
						// If Status equal (exclude)
						if(((Status) deviceProp[i]).name().equals(terms[i])) {
							valid = false;
							break;
						}
					}
				// Test for Inclusion
				}else{
					// If Property is a String
					if(deviceProp[i] instanceof String) {
						// If String not equal (exclude)
						if(!((String) deviceProp[i]).toLowerCase().contains(terms[i].toLowerCase())) {
							valid = false;
							break;
						}
					// If Property is Date
					}else if(deviceProp[i] instanceof LocalDate){
						// If Date not equal (exclude)
						if(!((LocalDate) deviceProp[i]).toString().equals(terms[i])) {
							valid = false;
							break;
						}
					// If Property is Status
					}else{
						// If Status not equal (exclude)
						if(!((Status) deviceProp[i]).name().equals(terms[i])) {
							valid = false;
							break;
						}
					}
				}
			}
			// Return Results
			return valid;
		// Compile
		}).forEach(dev -> {
			// If null, skip
			if(dev == null)
				return;
			else
				results.add(dev);
		});
		// Rebuilt Table with Results If Results Found
		if(results.size() == 0)
			JOptionPane.showMessageDialog(main.getFront().getAdvSearch(), "No Results Found", "Search Error", JOptionPane.INFORMATION_MESSAGE);
		else {
			main.getFront().tableRebuild(results);
			// Hide Again
			main.getFront().getAdvSearch().setVisible(false);
		}
	}
}
