package com.andrew.inv.manage.db;

import java.awt.Component;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * This class will handle all request for the file manager
 * 
 * @author andre
 */
public class FileChooser extends JFileChooser{
	
	/**
	 * Serial
	 */
	private static final long serialVersionUID = 2944212737168008525L;
	
	/**
	 * Acceptable File Extensions
	 */
	public static final FileNameExtensionFilter CSV = new FileNameExtensionFilter("CSV File", "csv"),
												PCDB = new FileNameExtensionFilter("PCDB File", "pcdb");
	
	/**
	 * Determine Mode
	 */
	public static final int SAVE = 0,
							OPEN = 1;
	
	private FileNameExtensionFilter ext;

	private FileChooser(FileNameExtensionFilter ext) {
		// Set as the desired file extension
		this.ext = ext;
		// Setup extensions
		addChoosableFileFilter(ext);
		// Dis-allows All Files
		setAcceptAllFileFilterUsed(false);
		// Set as file only
		setFileSelectionMode(JFileChooser.FILES_ONLY);
	}
	
	/**
	 * Ensure File Ending Exists
	 */
	@Override
	public File getSelectedFile() {
		// Get Selected File
		File selFile = super.getSelectedFile();
		// Detect File Ext. and Add if needed
		if(selFile == null || selFile.getName().endsWith("." + ext.getExtensions()[0]))
			return selFile;
		else
			return new File(selFile.getAbsolutePath() + "." + ext.getExtensions()[0]);
	}
	
	public static File showFC(Component parent, final int MODE, final FileNameExtensionFilter EXT) {
		// Start File Chooser
		FileChooser fc = new FileChooser(EXT);
		int result = -1;
		// Select Mode
		switch(MODE) {
			case SAVE: 
				result = fc.showSaveDialog(parent);
				break;
			case OPEN:
				result = fc.showOpenDialog(parent);
				break;
		}
		// Detect Cancellation
		if(result != APPROVE_OPTION)
			return null;
		// Otherwise
		return fc.getSelectedFile();
	}
}
