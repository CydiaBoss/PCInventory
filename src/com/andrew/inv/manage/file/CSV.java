package com.andrew.inv.manage.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.InputMismatchException;

import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

import com.andrew.inv.manage.C;
import com.andrew.inv.manage.db.Device;
import com.andrew.inv.manage.db.Device.Status;
import com.andrew.inv.manage.db.Security;

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
	 * Imports data from a CSV file (Does not lock file)
	 * 
	 * @param path
	 * Path to file
	 * 
	 * @return
	 * Data or null when failure
	 * 
	 * @throws FileNotFoundException 
	 * Thrown when file is bad
	 */
	public static ArrayList<Device> importData(Path path) throws FileNotFoundException{
		ArrayList<Device> devices = new ArrayList<>();
		// Parse
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
	
	/**
	 * Import Data with a password
	 * 
	 * @param path
	 * The File
	 * @param password
	 * The Password
	 * @param iv
	 * The IV
	 * @param salt
	 * The salt
	 * 
	 * @return
	 * The data
	 * 
	 * @throws FileNotFoundException
	 */
	public static ArrayList<Device> importData(Path path, char[] password, IvParameterSpec iv, byte[] salt) throws FileNotFoundException{
		ArrayList<Device> devices = new ArrayList<>();
		try {
			// Make Reader
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(path.toUri())), "UTF-8"));
			// Skip encryption flags
			reader.readLine();
			reader.readLine();
			reader.readLine();
			// Gather the rest of the file
			String encryptData = "";
			String temp = "";
			do {
				encryptData += temp;
				temp = reader.readLine();
			}while(temp != null);
			// Close Reader
			reader.close();
			// Get Key
			SecretKey key = Security.getKey(password, salt);
			// Decrypt
			String plainData = new String(Security.decrypt(Security.decode(encryptData), key, iv));
			// Parse
			for(String row : plainData.split("\n")) {
				// Header
				if(row.equals(C.HEADER))
					continue;
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
			}
		} catch (IOException | ArrayIndexOutOfBoundsException | DateTimeException | NoSuchAlgorithmException | InvalidKeySpecException | InputMismatchException e) {
			// Throws File Bad Exception
			throw new FileNotFoundException();
		// Wrong password
		} catch (InvalidKeyException e) {
			return null;
		}
		return devices;
	}
	
	private static ArrayList<String> parseRow(String line) {
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
	 * Append to a File (Will Lock File)
	 * 
	 * @param path
	 * The File
	 * @param devices
	 * New Devices
	 * 
	 * @throws IOException
	 * Error
	 */
	public static void exportData(Path path, ArrayList<Device> devices, StandardOpenOption... options) throws IOException {
		// Create an array of strings
		String row = "";
		// Add Header Row
		row += C.HEADER + "\n";
		// Transcribe
		for(Device d : devices)
			row +=
				d.getHost() + "," + 
				d.getSerial() + "," + 
				d.getModel() + "," + 
				d.getOS() + "," + 
				d.getDateUpdated().toString() + "," + 
				d.getLoc() + "," + 
				d.getStatus().name() + "," + 
				d.getUser() + "," +
				"\"" + d.getNote().replaceAll("\n", "\\\\n") + "\"\n" // Replaces \n so no interference here
			;
		// Open FileChannel
		FileChannel fChannel = FileChannel.open(path, options);
		// Lock Attempt
		FileLock lock = fChannel.tryLock();
		if(lock == null)
			throw new IOException();
		// Write
		fChannel.write(ByteBuffer.wrap(row.getBytes(StandardCharsets.UTF_8)));
		// Ensure Complete
		fChannel.force(true);
		// Unlock After Completion
		lock.close();
		// Close Channel
		fChannel.close();
	}
	
	/**
	 * Export Data with a password
	 * 
	 * @param path
	 * The File
	 * @param password
	 * The Password
	 * @param iv
	 * The IV
	 * @param salt
	 * The salt
	 */
	public static void exportData(Path path, ArrayList<Device> devices, char[] password, IvParameterSpec iv, byte[] salt) {
		// Create an array of strings
		String row = "true\n";
		// Add Encryption Data
		row += iv.getIV() + "\n" +
			   salt + "\n";
		// Add Header Row
		row += C.HEADER + "\n";
		// Transcribe
		for(Device d : devices)
			row +=
				d.getHost() + "," + 
				d.getSerial() + "," + 
				d.getModel() + "," + 
				d.getOS() + "," + 
				d.getDateUpdated().toString() + "," + 
				d.getLoc() + "," + 
				d.getStatus().name() + "," + 
				d.getUser() + "," +
				"\"" + d.getNote().replaceAll("\n", "\\\\n") + "\"\n" // Replaces \n so no interference here
			;
		// Attempt Encryption
		try {
			// Make Data
			byte[] data = Security.encrypt(row.getBytes(), Security.getKey(password, salt), iv);
			// Encode
			String encodeData = Security.encode(data);
			// Open FileChannel
			FileChannel fChannel = FileChannel.open(path, EDIT);
			// Lock Attempt
			FileLock lock = fChannel.tryLock();
			if(lock == null)
				throw new IOException();
			// Write
			fChannel.write(ByteBuffer.wrap(encodeData.getBytes()));
			// Ensure Complete
			fChannel.force(true);
			// Unlock After Completion
			lock.close();
			// Close Channel
			fChannel.close();
		} catch (InvalidKeyException | InvalidKeySpecException | NoSuchAlgorithmException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
