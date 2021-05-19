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
		try {
			CSV.exportData(Paths.get(C.DAT.getAbsolutePath()));
		} catch (IOException e) {}
	}
}
