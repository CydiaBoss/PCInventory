package com.andrew.inv.manage;

import java.awt.EventQueue;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.FontUIResource;

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
	
	// Current File
	private static File currentMain = null;
	
	// File to Import
	private static File importFile = null;
	
	// Dangerous Mode (Database Unsafe)
	private static boolean safe = true;
	
	// Not Main 
	private static boolean main = true;
	
	public static void main(String[] args) throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
		// Start up Processes
		// Fix GUI Looks
		setGUI();
		// Select Main File
		if(args.length == 1) {
			// File Creation
			File openWithFile = new File(args[0]);
			// Check Type
			// If .pcdb, open selected inventory
			if(openWithFile.getName().endsWith(".pcdb")) {
				currentMain = openWithFile;
				main = false;
			// If .csv, ask if they want to import
			}else if(openWithFile.getName().endsWith(".csv")) {
				currentMain = C.DAT;
				importFile = openWithFile;
			// Error Otherwise
			}else{
				JOptionPane.showMessageDialog(
					null, 
					"PC Inventory cannot open file " + openWithFile.getName(), 
					"Cannot Open File", 
					JOptionPane.ERROR_MESSAGE
				);
				// Close
				System.exit(-1);
			}
		}else
			currentMain = C.DAT;
		// Ensure File Safety
		File lock = new File("." + ((main)? "" : currentMain.getName()) + "lock.tmp");
		if(!lock.createNewFile()) {
			// Not Safe
			safe = false;
			if(JOptionPane.showConfirmDialog(
				null, 
				"Someone else is running this program right now.\n"
				+ "This may lead to database corruption. Continue?", 
				"Multi-Run Detected!", 
				JOptionPane.YES_NO_CANCEL_OPTION, 
				JOptionPane.WARNING_MESSAGE) != JOptionPane.YES_OPTION)
					System.exit(0);
		// Create File Lock
		}else{
			// Hide Lock
			Files.setAttribute(Paths.get(lock.toURI()), "dos:hidden", true);
			lock.deleteOnExit();
		}
		// Read Images
		C.ICONS.add(Toolkit.getDefaultToolkit().getImage(Main.class.getResource("/icon16.png")));
		C.ICONS.add(Toolkit.getDefaultToolkit().getImage(Main.class.getResource("/icon32.png")));
		C.ICONS.add(Toolkit.getDefaultToolkit().getImage(Main.class.getResource("/icon64.png")));
		C.ICONS.add(Toolkit.getDefaultToolkit().getImage(Main.class.getResource("/icon128.png")));
		// File Check (Create if not existing)
		if(!currentMain.createNewFile())
			// Read Data from CSV
			try {
				devices = CSV.importData(Paths.get(currentMain.toURI()));
			}catch (FileNotFoundException e) {
				// Bad File
				JOptionPane.showMessageDialog(
					null, 
					"File Corruption Detected.\n" +
					"Please repair or delete data.csv to regain functionality.", 
					"File Corruption", 
					JOptionPane.ERROR_MESSAGE
				);
				// Crash
				System.exit(-1);
			}
		// New File Database
		else{
			// Make Main pcdb Hidden
			Files.setAttribute(Paths.get(currentMain.toURI()), "dos:hidden", true);
			devices = new ArrayList<>();
		}
		// Launch GUI
		EventQueue.invokeLater(() -> {
			try {
				front = new FrontPage();
				// Attempt File Import if Required
				if(importFile != null) {
					// Confirm
					int result = JOptionPane.showConfirmDialog(
						front.getFrame(), 
						importFile.getName() + " is about to be imported into the main database.\n" +
						"Continue?", 
						"Data Import Confirmation", 
						JOptionPane.YES_NO_OPTION, 
						JOptionPane.QUESTION_MESSAGE
					);
					// Import File
					if(result == JOptionPane.YES_OPTION) {
						// Save
						save(CSV.importData(Paths.get(importFile.toURI())));
						// Rebuild
						front.tableRebuild();
					}
				}
			} catch (Exception e) {}
		});
	}

	/**
	 * Set GUI Values
	 * 
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws UnsupportedLookAndFeelException
	 */
	private static void setGUI() throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
		// Fix GUI Looks
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		// Change Font for all
		for(Object key : UIManager.getLookAndFeel().getDefaults().keySet()) 
			if(UIManager.get(key) instanceof FontUIResource)
				UIManager.put(key, new FontUIResource(C.DEFAULT_FONT));
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
	 * If the current program is using the main database or not
	 * 
	 * @return
	 * Safety of Program
	 */
	public static boolean isMain() {
		return main;
	}
	
	/**
	 * Get Current File that is opened
	 * 
	 * @return
	 * Current File
	 */
	public static File getMainFile() {
		return currentMain;
	}
	
	/**
	 * Updated the data file
	 */
	public static void save() {
		// Edit
		try {
			CSV.exportData(Paths.get(currentMain.toURI()));
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
			CSV.exportData(Paths.get(currentMain.toURI()), d);
		} catch (IOException e) {}
	}
}
