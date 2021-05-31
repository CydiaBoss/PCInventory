package com.andrew.inv.manage.db;

import java.awt.Color;
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
		LOANER("Loaner Computer", new Color(128, 212, 255)),	// BLUE
		REASSIGN("To Be Reassigned", new Color(198, 255, 179)),	// GREEN
		REIMAGE("To Be Reimaged", new Color(198, 255, 179)),	// GREEN
		RENAME("To Be Renamed", new Color(198, 255, 179)),		// GREEN
		WIPE("To Be Wiped", new Color(255, 230, 128)),			// ORANGE
		IST("Sent to IST", new Color(255, 230, 128)), 			// ORANGE
		HOME("Home Computer", new Color(255, 255, 128)),		// YELLOW
		FAULTY("Faulty", new Color(255, 179, 179)), 			// RED
		TEMP("Temporary Reassigned", new Color(243, 216, 229)); // GREY-ISH RED
		
		// The message
		private final String MSG;
		
		// Colour of Table
		private final Color COL;
		
		// The Status
		Status(final String MSG) {
			this.MSG = MSG;
			this.COL = Color.WHITE;
		}
		
		// The Status
		Status(final String MSG, final Color COL) {
			this.MSG = MSG;
			this.COL = COL;
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
		 * Return the Colour
		 * 
		 * @return
		 * The colour
		 */
		public Color getColour() {
			return COL;
		}
		
		/**
		 * Overrides the normal name
		 */
		@Override
		public String toString() {
			return getMsg();
		}
		
	}
	
	private final String SERIAL,
						 MODEL;
						 
	// Default is Other
	private String host = "?",
				   os = "?",
				   note = "";
	
	// Default is Today's Date
	private LocalDate dateUpdated = LocalDate.now();
	
	// Default is Unknown
	private String location = "?";
	
	// Default status is "in use"
	private Status status = Status.INUSE;
	
	// User
	private String user = "?";
	
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
	public Device(String host, final String SERIAL, final String MODEL) {
		// Assign the correct variables
		this.host = host;
		// Prevent Emptiness 
		if(SERIAL.trim().equals("") || SERIAL == null)
			this.SERIAL = "?";
		else
			this.SERIAL = SERIAL;
		// Prevent Emptiness 
		if(MODEL.trim().equals("") || MODEL == null)
			this.MODEL = "?";
		else
			this.MODEL = MODEL;
	}

	public String getHost() {
		return host;
	}
	
	public void setHost(String host) {
		this.host = host;
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
	
	public String getNote() {
		return note;
	}
	
	public void setNote(String note) {
		this.note = note;
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
	 * Date of Update
	 */
	public void setOS(String newOS, LocalDate date) {
		// Prevent Emptiness 
		if(newOS.trim().equals("") || newOS == null)
			os = "?";
		else
			os = newOS;
		dateUpdated = date;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
		// Erase Note if not Sent to IST
		if(status == Status.IST) 
			note = "";
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
		// Prevent Emptiness 
		if(user.trim().equals("") || user == null)
			this.user = "?";
		else
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
		// Prevent Emptiness 
		if(location.trim().equals("") || location == null)
			this.location = "?";
		else
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

