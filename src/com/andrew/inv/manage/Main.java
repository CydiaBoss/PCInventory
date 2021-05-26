package com.andrew.inv.manage;

import java.awt.EventQueue;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

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
	
	// Dangerous Mode (Database Unsafe)
	private static boolean safe = true;
	
	public static void main(String[] args) throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
		// Start up Processes
		// Fix GUI Looks
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		// Ensure File Safety
		File lock = new File(".lock.tmp");
		if(!lock.createNewFile()) {
			// Not Safe
			safe = false;
			if(JOptionPane.showConfirmDialog(
				null, 
				"Multiple instances of this program are running at once.\n"
				+ "This may lead to database corruption. Continue?", 
				"Multi-Run Detected!", 
				JOptionPane.YES_NO_CANCEL_OPTION, 
				JOptionPane.WARNING_MESSAGE) != JOptionPane.YES_OPTION)
					System.exit(0);
		// Create File Lock
		}else{
			lock.deleteOnExit();
		}
		// Read Images
		C.ICONS.add(Toolkit.getDefaultToolkit().getImage(Main.class.getResource("/icon16.png")));
		C.ICONS.add(Toolkit.getDefaultToolkit().getImage(Main.class.getResource("/icon32.png")));
		C.ICONS.add(Toolkit.getDefaultToolkit().getImage(Main.class.getResource("/icon64.png")));
		C.ICONS.add(Toolkit.getDefaultToolkit().getImage(Main.class.getResource("/icon128.png")));
		// File Check (Create if not existing)
		if(!C.DAT.createNewFile())
			// Read Data from CSV
			devices = CSV.importData(Paths.get(C.DAT.getAbsolutePath()));
		// Launch GUI
		EventQueue.invokeLater(() -> {
			try {
				front = new FrontPage();
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	/**
	 * If the current state of the program is safe
	 * 
	 * @return
	 * Safety of Program
	 */
	public static boolean isSafe() {
		return safe;
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
