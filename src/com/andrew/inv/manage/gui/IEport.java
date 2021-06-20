package com.andrew.inv.manage.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.andrew.inv.manage.C;
import com.andrew.inv.manage.Main;
import com.andrew.inv.manage.db.FileChooser;
import com.andrew.inv.manage.file.CSV;

public class IEport extends JDialog {

	/**
	 * Serial
	 */
	private static final long serialVersionUID = -9197196751717797647L;
	
	private JPanel contentPane;
	private JTextField inFileTxt;

	/**
	 * Create the frame.
	 */
	public IEport(Main main) {
		setTitle("Import & Export");
		setIconImages(C.ICONS);
		setType(Type.POPUP);
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(450, 175);
		setLocationRelativeTo(null);
		setModalityType(ModalityType.APPLICATION_MODAL);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		Component verticalStrut = Box.createVerticalStrut(C.SPACING * 2);
		contentPane.add(verticalStrut, BorderLayout.NORTH);
		
		Component horizontalStrut = Box.createHorizontalStrut(C.SPACING * 2);
		contentPane.add(horizontalStrut, BorderLayout.EAST);
		
		Component horizontalStrut_1 = Box.createHorizontalStrut(C.SPACING * 2);
		contentPane.add(horizontalStrut_1, BorderLayout.WEST);
		
		JPanel iePanel = new JPanel();
		contentPane.add(iePanel, BorderLayout.CENTER);
		GridBagLayout gbl_iePanel = new GridBagLayout();
		gbl_iePanel.columnWidths = new int[]{0, 0, 0, 0};
		gbl_iePanel.rowHeights = new int[]{0, 0, 0};
		gbl_iePanel.columnWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_iePanel.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		iePanel.setLayout(gbl_iePanel);
		
		JLabel importExportLbl = new JLabel("Function: ");
		GridBagConstraints gbc_importExportLbl = new GridBagConstraints();
		gbc_importExportLbl.anchor = GridBagConstraints.EAST;
		gbc_importExportLbl.insets = new Insets(0, 0, 5, 5);
		gbc_importExportLbl.gridx = 0;
		gbc_importExportLbl.gridy = 0;
		iePanel.add(importExportLbl, gbc_importExportLbl);
		
		Box importExportBox = Box.createHorizontalBox();
		GridBagConstraints gbc_importExportBox = new GridBagConstraints();
		gbc_importExportBox.anchor = GridBagConstraints.WEST;
		gbc_importExportBox.gridwidth = 2;
		gbc_importExportBox.insets = new Insets(0, 0, 5, 0);
		gbc_importExportBox.gridx = 1;
		gbc_importExportBox.gridy = 0;
		iePanel.add(importExportBox, gbc_importExportBox);
		
		ButtonGroup ieBtnGrp = new ButtonGroup();
		
		JRadioButton importBtn = new JRadioButton("Import");
		importExportBox.add(importBtn);
		ieBtnGrp.add(importBtn);
		
		JRadioButton exportBtn = new JRadioButton("Export");
		importExportBox.add(exportBtn);
		ieBtnGrp.add(exportBtn);
		
		JLabel inFileLbl = new JLabel("File Location: ");
		GridBagConstraints gbc_inFileLbl = new GridBagConstraints();
		gbc_inFileLbl.anchor = GridBagConstraints.EAST;
		gbc_inFileLbl.insets = new Insets(0, 0, 0, 5);
		gbc_inFileLbl.gridx = 0;
		gbc_inFileLbl.gridy = 1;
		iePanel.add(inFileLbl, gbc_inFileLbl);
		
		inFileTxt = new JTextField();
		inFileTxt.setEnabled(false);
		GridBagConstraints gbc_inFileTxt = new GridBagConstraints();
		gbc_inFileTxt.fill = GridBagConstraints.HORIZONTAL;
		gbc_inFileTxt.insets = new Insets(0, 0, 0, 5);
		gbc_inFileTxt.gridx = 1;
		gbc_inFileTxt.gridy = 1;
		iePanel.add(inFileTxt, gbc_inFileTxt);
		inFileTxt.setColumns(10);
		
		JButton inFileBtn = new JButton("Browse");
		inFileBtn.addActionListener(e -> {
			File result = null;
			// If Importing
			if(importBtn.isSelected())
				result = FileChooser.showFC(this, FileChooser.OPEN, FileChooser.CSV);
			// Else Exporting
			else
				result = FileChooser.showFC(this, FileChooser.SAVE, FileChooser.CSV);
			// OK Pressed
			if(result != null)
				inFileTxt.setText(result.getAbsolutePath());
		});
		// Default Off
		inFileBtn.setEnabled(false);
		GridBagConstraints gbc_inFileBtn = new GridBagConstraints();
		gbc_inFileBtn.gridx = 2;
		gbc_inFileBtn.gridy = 1;
		iePanel.add(inFileBtn, gbc_inFileBtn);
		
		JPanel ctrlPanel = new JPanel();
		contentPane.add(ctrlPanel, BorderLayout.SOUTH);
		ctrlPanel.setLayout(new BorderLayout(0, 0));
		
		Component verticalStrut_2 = Box.createVerticalStrut(C.SPACING * 2);
		ctrlPanel.add(verticalStrut_2, BorderLayout.SOUTH);
		
		Component horizontalStrut_2 = Box.createHorizontalStrut(C.SPACING * 2);
		ctrlPanel.add(horizontalStrut_2, BorderLayout.WEST);
		
		Component horizontalStrut_3 = Box.createHorizontalStrut(C.SPACING * 2);
		ctrlPanel.add(horizontalStrut_3, BorderLayout.EAST);
		
		JPanel btnPanel = new JPanel();
		ctrlPanel.add(btnPanel, BorderLayout.CENTER);
		btnPanel.setLayout(new GridLayout(1, 0, 25, 0));
		
		JButton cancelBtn = new JButton("Cancel");
		cancelBtn.addActionListener(e -> 
			dispose()
		);
		btnPanel.add(cancelBtn);

		JButton templateBtn = new JButton("Get Template");
		templateBtn.setEnabled(false);
		templateBtn.addActionListener(e -> {
			// Prompt
			int result = JOptionPane.showConfirmDialog(
				this, 
				"Fill out this CSV File without modifying the header to import successfully.\n"
				+ "Are you ready to receive the template?", 
				"Getting Template", 
				JOptionPane.YES_NO_CANCEL_OPTION, 
				JOptionPane.INFORMATION_MESSAGE
			);
			// Actions
			if(result == JOptionPane.YES_OPTION) {
				// Get File
				File file = FileChooser.showFC(this, FileChooser.SAVE, FileChooser.CSV);
				if(file != null)
					try {
						// Save Template
						Files.copy(
							Paths.get(IEport.class.getResource("/template.csv").toURI()), 
							Paths.get(file.getAbsolutePath()), 
							StandardCopyOption.REPLACE_EXISTING,
							StandardCopyOption.COPY_ATTRIBUTES
						);
						// Also save Status Options for Reference
						Files.copy(
							Paths.get(IEport.class.getResource("/status_opts.csv").toURI()), 
							Paths.get(file.getParent() + File.separatorChar + "status_opts.csv"), 
							StandardCopyOption.REPLACE_EXISTING,
							StandardCopyOption.COPY_ATTRIBUTES
						);
					} catch (IOException | URISyntaxException e1) {}
			}
			// Otherwise do nothing
		});
		btnPanel.add(templateBtn);
		
		JButton startBtn = new JButton("Start");
		startBtn.addActionListener(e -> {
			// Get File
			File f = new File(inFileTxt.getText());
			// Check for Import or Export
			if(importBtn.isSelected()) {
				// Reads File and Uploads
				try {
					main.save(CSV.importData(Paths.get(inFileTxt.getText())));
					// Rebuilds Table
					main.getFront().tableRebuild();
					// Confirmation Message
					JOptionPane.showMessageDialog(this, "Data was imported.");
				} catch (FileNotFoundException e1) {
					// File is Bad
					JOptionPane.showMessageDialog(this, "File is formatted incorrectly. Unable to import.");
					// Stop and return
					return;
				}
			}else{
				try {
					// Creates the File
					f.createNewFile();
					// Exports
					CSV.exportData(Paths.get(inFileTxt.getText()), main.getDevices(), CSV.EDIT);
					// Confirmation Message
					JOptionPane.showMessageDialog(this, "Data was exported and stored in the new file.");
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			// Close
			dispose();
		});
		
		// Default Off
		startBtn.setEnabled(false);
		btnPanel.add(startBtn);

		// Set Up Enabler
		ActionListener radioBtns = e -> {
			if(importBtn.isSelected() || exportBtn.isSelected()) {
				// Enable the Template Button
				if(importBtn.isSelected())
					templateBtn.setEnabled(true);
				else
					templateBtn.setEnabled(false);
				inFileTxt.setEnabled(true);
				inFileBtn.setEnabled(true);
			}else{
				inFileTxt.setEnabled(false);
				inFileBtn.setEnabled(false);
			}
		};
		importBtn.addActionListener(radioBtns);
		exportBtn.addActionListener(radioBtns);
		
		// Document Listener
		inFileTxt.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void insertUpdate(DocumentEvent e) {
				update();
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				update();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				update();
			}
			
			/**
			 * What to do when the field is updated
			 */
			public void update() {
				// Enable or Disable the Start Button
				if(inFileTxt.getText().trim().equals(""))
					startBtn.setEnabled(false);
				else
					startBtn.setEnabled(true);
			}
			
		});
	}

}
