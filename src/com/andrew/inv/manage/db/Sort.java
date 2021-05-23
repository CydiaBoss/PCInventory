package com.andrew.inv.manage.db;

import java.util.Comparator;

/**
 * Class that handles sorting function
 * 
 * @author andre
 *
 */
public class Sort implements Comparator<Device> {
	
	// Sort by Header
	public static final int HOST = 0,
							USER = 1,
							MODEL = 2,
							OS = 3,
							STAT = 4;
	
	// Selected Mode
	private final int MODE;
	
	// Ascending
	private boolean ascendingMode;

	/**
	 * Set up Comparator for {@link Device} {@link ArrayList}
	 * 
	 * @param MODE
	 * What to Sort
	 * @param isAscending
	 * Ascending or Not
	 */
	public Sort(final int MODE, boolean isAscending) {
		this.MODE = MODE;
		this.ascendingMode = isAscending;
	}
	
	/**
	 * Compares two {@link Devices} based on previously stated values
	 */
	@Override
	public int compare(Device o1, Device o2) {
		// Do Sort
		switch(MODE) {
			// Host Name
			case HOST: 
				// Sorts List
				if(ascendingMode) {
					return o1.getHost().toLowerCase().compareTo(o2.getHost().toLowerCase());
				}else{
					return o2.getHost().toLowerCase().compareTo(o1.getHost().toLowerCase());
				}
			// User
			case USER: 
				// Sorts List
				if(ascendingMode) {
					return o1.getUser().toLowerCase().compareTo(o2.getUser().toLowerCase());
				}else{
					return o2.getUser().toLowerCase().compareTo(o1.getUser().toLowerCase());
				}
			// Model
			case MODEL:
				// Sorts List
				if(ascendingMode) {
					return o1.getModel().toLowerCase().compareTo(o2.getModel().toLowerCase());
				}else{
					return o2.getModel().toLowerCase().compareTo(o1.getModel().toLowerCase());
				}
			// OS
			case OS:
				// Sorts List
				if(ascendingMode) {
					return o1.getOS().toLowerCase().compareTo(o2.getOS().toLowerCase());
				}else{
					return o2.getOS().toLowerCase().compareTo(o1.getOS().toLowerCase());
				}
			// Status
			case STAT: 
				// Sorts List
				if(ascendingMode) {
					return o1.getStatus().name().toLowerCase().compareTo(o2.getStatus().name().toLowerCase());
				}else{
					return o2.getStatus().name().toLowerCase().compareTo(o1.getStatus().name().toLowerCase());
				}
			// Nothing Happens (This Option is Never Valid)
			default:
				return 0;
		}
	}

}
