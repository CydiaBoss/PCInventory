package com.andrew.inv.manage;

import java.awt.EventQueue;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;

import javax.swing.UIManager;

import com.andrew.inv.manage.db.Device;
import com.andrew.inv.manage.file.CSV;
import com.andrew.inv.manage.gui.FrontPage;

/**
 * Main Class
 * 
 * @author andre
 *
 */
public class Main {

	// Main GUI
	public static FrontPage front = null;
	
	// Device Database
	public static ArrayList<Device> devices = null;
	
	public static void main(String[] args) throws IOException {
		// Start up Processes
		// File Check (Create if not existing)
		if(!C.DAT.createNewFile())
			// Read Data from CSV
			devices = CSV.importData(Paths.get(C.DAT.getAbsolutePath()));
		// Launch GUI
		EventQueue.invokeLater(() -> {
			try {
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				front = new FrontPage();
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	/**
	 * Updated the data file
	 */
	public static void save() {
		// Edit
		try {
			CSV.exportData(Paths.get(C.DAT.getAbsolutePath()));
		} catch (IOException e) {}
	}
	
	/**
	 * Add to the data file
	 * 
	 * @param d
	 * The new devices
	 */
	public static void save(Device... d) {
		// Array to ArrayList
		ArrayList<Device> devs = new ArrayList<>();
		for(Device de : d) 
			devs.add(de);
		
		// Transfer Control to other Method
		save(devs);
	}
	
	/**
	 * Add to the data file
	 * 
	 * @param d
	 * The new devices
	 */
	public static void save(ArrayList<Device> d) {
		
		// Add to Registry
		Main.devices.addAll(d);
		
		// Append
		try {
			CSV.exportData(Paths.get(C.DAT.getAbsolutePath()), d);
		} catch (IOException e) {}
	}
}
