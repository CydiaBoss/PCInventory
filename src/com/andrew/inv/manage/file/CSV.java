package com.andrew.inv.manage.file;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.util.ArrayList;

import com.andrew.inv.manage.Main;
import com.andrew.inv.manage.db.Device;

import com.andrew.inv.manage.db.Device.Status;

/**
 * CSV Sheet Management
 * 
 * @author andre
 *
 */
public class CSV {

	/**
	 * Imports data from a CSV file
	 * 
	 * @param path
	 * Path to file
	 * 
	 * @return
	 * Data
	 */
	public static ArrayList<Device> importData(Path path) {
		ArrayList<Device> devices = new ArrayList<>();
		try {
			// Go thru each row
			Files.lines(path).forEach(row -> {
				ArrayList<String> data = parseRow(row);
				// Adds a new device with its Host, Serial, Model
				Device currDevice = new Device(data.get(0), data.get(1), data.get(2));
				// Set OS
				currDevice.setOS(data.get(3), LocalDate.parse(data.get(4)));
				// Set Location
				currDevice.setLoc(data.get(5));
				// Set Status
				currDevice.setStatus(Status.valueOf(data.get(6)));
				// Set User
				currDevice.setUser(data.get(7));
				// Add
				devices.add(currDevice);
			});
		} catch (IOException e) {
			e.printStackTrace();
			return null;
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
				cells.add(tempCell + "," + cell.substring(0, cell.length() - 2));
				// Reset
				tempCell = "";
				inQuote = false;
			// In Progress
			}else if(inQuote) {
				// Append
				tempCell += "," + cell;
			// Quote Detection Start
			}else if(cell.startsWith("\"")) {
				inQuote = true;
				// (Remove Quote Mark)
				tempCell = cell.substring(1, cell.length() - 1);
			// Otherwise, add normally
			}else {
				cells.add(cell.trim());
			}
		}
		return cells;
	}
	
	// Options
	public static final StandardOpenOption[] EDIT = {StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING},
											 ADD = {StandardOpenOption.WRITE, StandardOpenOption.APPEND};
	
	/**
	 * Write to a File
	 * 
	 * @param path
	 * The File
	 * 
	 * @throws IOException
	 * Error
	 */
	public static void exportData(Path path) throws IOException {
		// Create an array of strings
		ArrayList<String> row = new ArrayList<>();
		// Transcribe
		for(Device d : Main.devices) 
			row.add(
				d.getHost() + "," + 
				d.getSerial() + "," + 
				d.getModel() + "," + 
				d.getOS() + "," + 
				d.getDateUpdated().toString() + "," + 
				d.getLoc() + "," + 
				d.getStatus().name() + "," + 
				d.getUser()
			);
		// Write
		Files.write(path, row, CSV.EDIT);
	}
	
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
				d.getUser()
			);
		
		// Write
		Files.write(path, row, CSV.ADD);
	}
} 
