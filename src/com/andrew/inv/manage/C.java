package com.andrew.inv.manage;

import java.awt.Image;
import java.io.File;
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
	public static final File DAT = new File("data.csv");
	
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
	 * Icons
	 */
	public static final ArrayList<Image> ICONS = new ArrayList<>();

	/**
	 * Spacing for GUI
	 */
	public static final int SPACING = 10;
}
