package com.andrew.inv.manage;

import java.awt.EventQueue;
import java.awt.HeadlessException;
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

	// Start
	public static void main(String[] args) {
		// Start up Processes
		// Fix GUI Looks
		setGUI();
		// Look for arguments (Open something else)
		if(args.length > 0) {
			String path = "";
			// Rebuild full path in case of spaces
			for(String pathPiece : args)
				path += pathPiece;
			// Make File
			File f = new File(path);
			// Try Opening
			openFile(f);
		// Starts with Default
		}else
			openFile(C.DAT);
	}
	
	// Main GUI
	private FrontPage front;
	
	// Device Database
	private ArrayList<Device> devices;
	
	// Current File
	private File currentMain = null;
	
	// File Lock
	private File lock = null;
	
	// Dangerous Mode (Database Unsafe)
	private boolean safe = true;
	
	// Not Main 
	private boolean main;
	
	/**
	 * Start the App
	 * 
	 * @param fileToOpen
	 * The File (Another File)
	 * 
	 * @throws IOException
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws UnsupportedLookAndFeelException
	 */
	private Main(File fileToOpen) throws IOException {
		// Setup
		currentMain = fileToOpen;
		// Read Images
		C.ICONS.add(Toolkit.getDefaultToolkit().getImage(Main.class.getResource("/icon16.png")));
		C.ICONS.add(Toolkit.getDefaultToolkit().getImage(Main.class.getResource("/icon32.png")));
		C.ICONS.add(Toolkit.getDefaultToolkit().getImage(Main.class.getResource("/icon64.png")));
		C.ICONS.add(Toolkit.getDefaultToolkit().getImage(Main.class.getResource("/icon128.png")));
		// Tries to locks File
		fileLock();
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
		// New Default File Database
		else if (currentMain == C.DAT){
			// Make Main pcdb Hidden
			Files.setAttribute(Paths.get(currentMain.toURI()), "dos:hidden", true);
			devices = new ArrayList<>();
		}
		// Correct Flags
		main = currentMain.equals(C.DAT);
		// Launch GUI
		EventQueue.invokeLater(() -> 
			front = new FrontPage(this)
		);
	}

	/**
	 * Set GUI Values
	 */
	private static void setGUI() {
		// Fix GUI Looks
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {}
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
	public boolean isSafe() {
		return safe;
	}
	
	/**
	 * If the current program is using the main database or not
	 * 
	 * @return
	 * Safety of Program
	 */
	public boolean isMain() {
		return main;
	}
	
	/**
	 * Get Current File that is opened
	 * 
	 * @return
	 * Current File
	 */
	public File getMainFile() {
		return currentMain;
	}
	
	public ArrayList<Device> getDevices() {
		return devices;
	}
	
	public FrontPage getFront() {
		return front;
	}
	
	/**
	 * Renames the current file and execute relevant processes
	 * 
	 * @param name
	 * New Name
	 */
	public void renameFile(String name) {
		// Create Destination File
		File dest = new File(
			// Determine Parent Directory
			((currentMain.getParent() != null)? currentMain.getParent() + File.separator : "") + 
			name + ".pcdb"
		);
		// Rename the File
		if(!currentMain.renameTo(
			dest
		// If Failure
		)){
			JOptionPane.showMessageDialog(
				front.getFrame(), 
				"Failure to rename file.", 
				"Failure", 
				JOptionPane.ERROR_MESSAGE
			);
			// Leave
			return;
		}
		// Update to new File
		currentMain = dest;
		// Update File Lock
		lock.delete();
		try {
			fileLock();
		} catch (HeadlessException | IOException e) {}
	}
	
	/**
	 * Detect for File Lock
	 * 
	 * TODO May replace with FileLock
	 * 
	 * @throws HeadlessException
	 * @throws IOException
	 */
	private void fileLock() throws HeadlessException, IOException {
		// Ensure File Safety
		lock = new File(
			((currentMain.getParent() != null)? currentMain.getParent() + File.separator : "") + 
			"." + ((main)? "" : currentMain.getName()) + "lock.tmp"
		);
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
	}
	
	/**
	 * Attempts early unlocking
	 */
	public void unlock() {
		lock.delete();
	}
	
	/**
	 * Import a CSV into the database
	 * 
	 * @param in
	 * The File
	 * 
	 * @throws FileNotFoundException
	 */
	public void importFile(File in) throws FileNotFoundException {
		// Confirm
		int result = JOptionPane.showConfirmDialog(
			front.getFrame(), 
			in.getName() + " is about to be imported into the main database.\n" +
			"Continue?", 
			"Data Import Confirmation", 
			JOptionPane.YES_NO_OPTION, 
			JOptionPane.QUESTION_MESSAGE
		);
		// Import File
		if(result == JOptionPane.YES_OPTION) {
			// Save
			save(CSV.importData(Paths.get(in.toURI())));
			// Rebuild
			front.tableRebuild();
		}
	}
	
	/**
	 * Updated the data file
	 */
	public void save() {
		// Edit
		try {
			CSV.exportData(Paths.get(currentMain.toURI()), devices);
		} catch (IOException e) {}
	}
	
	/**
	 * Add to the data file
	 * 
	 * @param d
	 * The new devices
	 */
	public void save(Device... d) {
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
	public void save(ArrayList<Device> d) {
		
		// Add to Registry
		devices.addAll(d);
		
		// Append
		try {
			CSV.exportData(Paths.get(currentMain.toURI()), d);
		} catch (IOException e) {}
	}
	
	/**
	 * Will launch another instances of the application
	 * 
	 * @param fileToOpen
	 * Path to another window with this file
	 */
	public static void openFile(File fileToOpen) {
		// Attempt to open the File in another Thread
		Thread th = new Thread(() -> {
			try {
				// If .pcdb or just the default database, open selected inventory
				if(fileToOpen.getName().endsWith(".pcdb")) 
					new Main(fileToOpen);
				// If .csv, ask if they want to import
				else if(fileToOpen.getName().endsWith(".csv")) {
					// Import into Default
					new Main(C.DAT).importFile(fileToOpen);
				// Not a supported file type
				}else{
					JOptionPane.showMessageDialog(
						null, 
						"PC Inventory could not open the file " + fileToOpen.getName(), 
						"Cannot Open File", 
						JOptionPane.ERROR_MESSAGE
					);
					// Close
					return;
				}
			} catch (IOException e) {}
		});
		th.run();
	}
}
