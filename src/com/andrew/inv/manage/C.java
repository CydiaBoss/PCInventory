package com.andrew.inv.manage;

import java.awt.Font;
import java.awt.Image;
import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Constants
 * 
 * @author andre
 *
 */
public class C {
	
	/**
	 * Data File
	 */
	public static final File DAT = new File(".data.pcdb");
	
	/**
	 * Default Year
	 * (Current Year)
	 */
	public static final String DEFAULT_YEAR = LocalDate.now().getYear() + "";
	
	/**
	 * Months
	 */
	public static final String[] MONTHS = {
		"January", 
		"February", 
		"March", 
		"April", 
		"May", 
		"June", 
		"July", 
		"August", 
		"September", 
		"October", 
		"November", 
		"December"
	};
	
	/**
	 * Headers
	 */
	public static final String HEADER = 
		"Host Name," + 
		"Serial #," + 
		"Model," + 
		"OS," + 
		"Date of Update (YYYY-MM-DD)," + 
		"Current Location," + 
		"Status," + 
		"Current User," +
		"Notes";
	
	/**
	 * GUI Default Font
	 */
	public static final Font DEFAULT_FONT = new Font("Segoe UI", Font.PLAIN, 12);
	
	/**
	 * Icons
	 */
	public static final ArrayList<Image> ICONS = new ArrayList<>();
	
	/**
	 * Key Iteration
	 */
	public static final int KEY_ITERATE_NUM = 65536;
	
	/**
	 * Salt Size
	 */
	public static final int SALT_SIZE = 65536;
	
	/**
	 * Spacing for GUI
	 */
	public static final int SPACING = 10;
}
