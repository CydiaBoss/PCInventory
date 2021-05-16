package com.andrew.inv.manage.file;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.andrew.inv.manage.db.Device;

/**
 * Excel Sheet Management
 * 
 * @author andre
 *
 */
public class Excel {

	/**
	 * Import existing data from an Excel sheet
	 * 
	 * @param path
	 * The path to the excel sheet
	 * 
	 * @return
	 * The Data as a Device Array
	 */
	public static ArrayList<Device> importData(File path) {
		try {
			// Setup
			XSSFWorkbook in = new XSSFWorkbook(path);
			XSSFSheet sh = in.getSheetAt(0);
			ArrayList<Device> d = new ArrayList<>();
			// Iterate through each row (Skip Title Row)
			for(int i = 1; i < sh.getLastRowNum(); i++) {
				XSSFRow rw = sh.getRow(i);
				// Adds a new device with its Host, Serial, Model
				d.add(new Device(rw.getCell(0).getRawValue(), rw.getCell(1).getRawValue(), rw.getCell(2).getRawValue()));
			}
			// Close
			in.close();
			return d;
		} catch (InvalidFormatException | IOException e) {
			// Failed
			return null;
		}
	}
}
