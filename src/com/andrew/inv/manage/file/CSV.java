package com.andrew.inv.manage.file;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.ArrayList;

import com.andrew.inv.manage.C;
import com.andrew.inv.manage.db.Device;

import com.andrew.inv.manage.db.Device.Status;

/**
 * CSV (PCDB) Sheet Management
 * 
 * @author andre
 *
 */
public class CSV {

	// Column Location
	public static final int HOST = 0,
							SERIAL = 1,
							MODEL = 2,
							OS = 3,
							DATE = 4,
							LOC = 5,
							STAT = 6,
							USER = 7,
							NOTE = 8;
	
	/**
	 * Imports data from a CSV file
	 * 
	 * @param path
	 * Path to file
	 * 
	 * @return
	 * Data
	 * 
	 * @throws FileNotFoundException 
	 * Thrown when file is bad
	 */
	public static ArrayList<Device> importData(Path path) throws FileNotFoundException {
		ArrayList<Device> devices = new ArrayList<>();
		try {
			// Go thru each row
			Files.lines(path).forEach(row -> {
				// Ignore Header Row
				if(row.equals(C.HEADER)) 
					return;
				// Start Processing
				ArrayList<String> data = parseRow(row);
				// Adds a new device with its Host, Serial, Model
				Device currDevice = new Device(data.get(HOST), data.get(SERIAL), data.get(MODEL));
				// Set OS
				currDevice.setOS(data.get(OS), LocalDate.parse(data.get(DATE)));
				// Set Location
				currDevice.setLoc(data.get(LOC));
				// Set Status
				currDevice.setStatus(Status.valueOf(data.get(STAT)));
				// Set User
				currDevice.setUser(data.get(USER));
				// Add Note if there is one (length 9 + buffer)
				if(data.size() >= 9)
					currDevice.setNote(data.get(NOTE).replaceAll("\\\\n", "\n"));
				// Add
				devices.add(currDevice);
			});
		} catch (IOException | ArrayIndexOutOfBoundsException | DateTimeException e) {
			// Throws File Bad Exception
			throw new FileNotFoundException();
		}
		// Device Return
		return devices;
	}
	
	public static ArrayList<String> parseRow(String line) {
		// Setup
		ArrayList<String> cells = new ArrayList<>();
		String tempCell = "";
		// Tracks quotation
		boolean inQuote = false;
		for(String cell : line.split(",")) {
			// Quote Detection Ends
			if(cell.endsWith("\"") && inQuote) {
				// Add Text (Remove Quote Mark)
				cells.add(tempCell + "," + cell.substring(0, cell.length() - 1));
				// Reset
				tempCell = "";
				inQuote = false;
			// In Progress
			}else if(inQuote) {
				// Append
				tempCell += "," + cell;
			// Just one term with quotation marks
			}else if(cell.startsWith("\"") && cell.endsWith("\"")) {
				cells.add(cell.substring(1, cell.length() - 1));
			// Quote Detection Start
			}else if(cell.startsWith("\"")) {
				inQuote = true;
				// (Remove Quote Mark)
				tempCell = cell.substring(1);
			// Otherwise, add normally
			}else {cells.add(cell.trim());
				
			}
		}
		// Insert Buffer
		cells.add("");
		// Return Results
		return cells;
	}
	
	// Options
	public static final StandardOpenOption[] EDIT = {StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING},
											 ADD = {StandardOpenOption.WRITE, StandardOpenOption.APPEND};
	
	/**
	 * Append to a File
	 * 
	 * @param path
	 * The File
	 * @param devices
	 * New Devices
	 * 
	 * @throws IOException
	 * Error
	 */
	public static void exportData(Path path, ArrayList<Device> devices) throws IOException {
		// Create an array of strings
		ArrayList<String> row = new ArrayList<>();
		// Add Header Row
		row.add(C.HEADER);
		// Transcribe
		for(Device d : devices)
			row.add(
				d.getHost() + "," + 
				d.getSerial() + "," + 
				d.getModel() + "," + 
				d.getOS() + "," + 
				d.getDateUpdated().toString() + "," + 
				d.getLoc() + "," + 
				d.getStatus().name() + "," + 
				d.getUser() + "," +
				"\"" + d.getNote().replaceAll("\n", "\\\\n") + "\"" // Replaces \n so no interference here
			);
		
		// Write
		Files.write(path, row, CSV.ADD);
	}
}
