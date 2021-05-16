package com.andrew.inv.manage.db;

import java.time.LocalDate;

/**
 * Represents a device object
 * 
 * @author andre
 *
 */
public class Device {
	
	/**
	 * The possible status of the {@link Device}
	 * @author andre
	 *
	 */
	public enum Status {
		
		INUSE("In Use"),
		LOANER("Loaner Computer"),
		REASSIGN("To Be Reassigned"),
		REIMAGE("To Be Reimaged"),
		RENAME("To Be Renamed"),
		WIPE("To Be Wiped"),
		IST("Sent to IST"),
		HOME("Home Computer"),
		FAULTY("Faulty"),
		TEMP("Temporary Reassigned");
		
		// The message
		private final String MSG;
		
		// The Status
		Status(final String MSG) {
			this.MSG = MSG;
		}
		
		/**
		 * Return the Message
		 * 
		 * @return
		 * The Message
		 */
		public String getMsg() {
			return MSG;
		}
		
		/**
		 * Overrides the normal name
		 */
		@Override
		public String toString() {
			return getMsg();
		}
		
	}
	
	private final String HOST,
						 SERIAL,
						 MODEL;
						 
	// Default is Other
	private String os = "Other";
	
	// Default is Today's Date
	private LocalDate dateUpdated = LocalDate.now();
	
	// Default is Unknown
	private String location = "Unknown";
	
	// Default status is "in use"
	private Status status = Status.INUSE;
	
	// User
	private String user = "Free";
	
	/**
	 * Creates a device object
	 * 
	 * @param HOST
	 * Host Name
	 * @param SERIAL
	 * Serial Number
	 * @param MODEL
	 * Model
	 * @param os
	 * Operating System
	 */
	public Device(final String HOST, final String SERIAL, final String MODEL) {
		// Assign the correct variables
		this.HOST = HOST;
		this.SERIAL = SERIAL;
		this.MODEL = MODEL;
	}

	public String getHost() {
		return HOST;
	}

	public String getSerial() {
		return SERIAL;
	}
	
	public String getModel() {
		return MODEL;
	}
	
	public String getOS() {
		return os;
	}
	
	public LocalDate getDateUpdated() {
		return dateUpdated;
	}
	
	/**
	 * Update the OS
	 * 
	 * @param newOS
	 * New OS Version
	 * @param date
	 * Date of Update (YYYY-MM-DD)
	 */
	public void setOS(String newOS, String date) {
		os = newOS;
		dateUpdated = LocalDate.parse(date);
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	/**
	 * @return 
	 * The User
	 */
	public String getUser() {
		return user;
	}

	/**
	 * @param user
	 * The New User
	 */
	public void setUser(String user) {
		this.user = user;
	}

	/**
	 * Get Location
	 * 
	 * @return 
	 * the location
	 */
	public String getLoc() {
		return location;
	}

	/**
	 * Change Location
	 * 
	 * @param location 
	 * the location
	 */
	public void setLoc(String location) {
		this.location = location;
	}
	
	
	
	/**
	 * Overridden toString()
	 * 
	 * @return
	 * Returns the host name of the computer 
	 */
	@Override
	public String toString() {
		return getHost();
	}
	
}

