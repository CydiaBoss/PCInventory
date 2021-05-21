package com.andrew.inv.manage.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.text.ParseException;
import java.time.LocalDate;

import javax.swing.Box;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.MaskFormatter;

import com.andrew.inv.manage.C;
import com.andrew.inv.manage.Main;
import com.andrew.inv.manage.db.Device;
import com.andrew.inv.manage.db.Device.Status;

public class Add extends JDialog {

	/**
	 * Serial
	 */
	private static final long serialVersionUID = 2280432853767677189L;
	
	private JPanel contentPane;
	private JTextField hostTxt;
	private JTextField serialTxt;
	private JTextField osTxt;
	private JComboBox<String> monthBox;
	private JComboBox<Status> statusBox;
	private JTextField userTxt;
	private JTextField locTxt;
	private JButton saveBtn;

	/**
	 * Main {@link DocumentListener} for all text fields
	 */
	private DocumentListener docListen = new DocumentListener() {

		// All updates are redirected to the same method
		
		@Override
		public void insertUpdate(DocumentEvent e) {
			valueChange();
		}

		@Override
		public void removeUpdate(DocumentEvent e) {
			valueChange();
		}

		@Override
		public void changedUpdate(DocumentEvent e) {
			valueChange();
		}
		
		/**
		 * Handles Text Updates
		 */
		private void valueChange() {
			saveBtn.setEnabled(true);
		}
		
	};
	
	private JTextField modelTxt;
	
	/**
	 * Create the frame.
	 */
	public Add() {
		setTitle("Add New Device");
		setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/icon.png")));
		setType(Type.POPUP);
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setModalityType(ModalityType.APPLICATION_MODAL);
		setBounds(100, 100, 450, 325);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		Component verticalStrut = Box.createVerticalStrut(20);
		contentPane.add(verticalStrut, BorderLayout.NORTH);
		
		Component horizontalStrut = Box.createHorizontalStrut(20);
		contentPane.add(horizontalStrut, BorderLayout.EAST);
		
		Component horizontalStrut_1 = Box.createHorizontalStrut(20);
		contentPane.add(horizontalStrut_1, BorderLayout.WEST);
		
		JPanel editPanel = new JPanel();
		contentPane.add(editPanel, BorderLayout.CENTER);
		GridBagLayout gbl_editPanel = new GridBagLayout();
		gbl_editPanel.columnWidths = new int[]{0, 0, 0, 0, 0};
		gbl_editPanel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_editPanel.columnWeights = new double[]{0.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
		gbl_editPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		editPanel.setLayout(gbl_editPanel);
		
		JLabel hostLbl = new JLabel("Host Name: ");
		GridBagConstraints gbc_hostLbl = new GridBagConstraints();
		gbc_hostLbl.anchor = GridBagConstraints.EAST;
		gbc_hostLbl.insets = new Insets(0, 0, 5, 5);
		gbc_hostLbl.gridx = 0;
		gbc_hostLbl.gridy = 0;
		editPanel.add(hostLbl, gbc_hostLbl);
		
		hostTxt = new JTextField();
		hostTxt.getDocument().addDocumentListener(docListen);
		GridBagConstraints gbc_hostTxt = new GridBagConstraints();
		gbc_hostTxt.gridwidth = 3;
		gbc_hostTxt.insets = new Insets(0, 0, 5, 0);
		gbc_hostTxt.fill = GridBagConstraints.HORIZONTAL;
		gbc_hostTxt.gridx = 1;
		gbc_hostTxt.gridy = 0;
		editPanel.add(hostTxt, gbc_hostTxt);
		hostTxt.setColumns(10);
		
		JLabel serialLbl = new JLabel("Serial #: ");
		GridBagConstraints gbc_serialLbl = new GridBagConstraints();
		gbc_serialLbl.anchor = GridBagConstraints.EAST;
		gbc_serialLbl.insets = new Insets(0, 0, 5, 5);
		gbc_serialLbl.gridx = 0;
		gbc_serialLbl.gridy = 1;
		editPanel.add(serialLbl, gbc_serialLbl);
		
		serialTxt = new JTextField();
		GridBagConstraints gbc_serialTxt = new GridBagConstraints();
		gbc_serialTxt.gridwidth = 3;
		gbc_serialTxt.insets = new Insets(0, 0, 5, 0);
		gbc_serialTxt.fill = GridBagConstraints.HORIZONTAL;
		gbc_serialTxt.gridx = 1;
		gbc_serialTxt.gridy = 1;
		editPanel.add(serialTxt, gbc_serialTxt);
		serialTxt.setColumns(10);
		
		JLabel modelLbl = new JLabel("Model: ");
		GridBagConstraints gbc_modelLbl = new GridBagConstraints();
		gbc_modelLbl.anchor = GridBagConstraints.EAST;
		gbc_modelLbl.insets = new Insets(0, 0, 5, 5);
		gbc_modelLbl.gridx = 0;
		gbc_modelLbl.gridy = 2;
		editPanel.add(modelLbl, gbc_modelLbl);
		
		modelTxt = new JTextField();
		GridBagConstraints gbc_modelTxt = new GridBagConstraints();
		gbc_modelTxt.gridwidth = 3;
		gbc_modelTxt.insets = new Insets(0, 0, 5, 0);
		gbc_modelTxt.fill = GridBagConstraints.HORIZONTAL;
		gbc_modelTxt.gridx = 1;
		gbc_modelTxt.gridy = 2;
		editPanel.add(modelTxt, gbc_modelTxt);
		modelTxt.setColumns(10);
		
		JLabel osLbl = new JLabel("OS: ");
		GridBagConstraints gbc_osLbl = new GridBagConstraints();
		gbc_osLbl.anchor = GridBagConstraints.EAST;
		gbc_osLbl.insets = new Insets(0, 0, 5, 5);
		gbc_osLbl.gridx = 0;
		gbc_osLbl.gridy = 3;
		editPanel.add(osLbl, gbc_osLbl);
		
		osTxt = new JTextField();
		osTxt.getDocument().addDocumentListener(docListen);
		GridBagConstraints gbc_osTxt = new GridBagConstraints();
		gbc_osTxt.gridwidth = 3;
		gbc_osTxt.insets = new Insets(0, 0, 5, 0);
		gbc_osTxt.fill = GridBagConstraints.HORIZONTAL;
		gbc_osTxt.gridx = 1;
		gbc_osTxt.gridy = 3;
		editPanel.add(osTxt, gbc_osTxt);
		osTxt.setColumns(10);
		
		JLabel updateLbl = new JLabel("Update Date: ");
		GridBagConstraints gbc_updateLbl = new GridBagConstraints();
		gbc_updateLbl.anchor = GridBagConstraints.EAST;
		gbc_updateLbl.insets = new Insets(0, 0, 5, 5);
		gbc_updateLbl.gridx = 0;
		gbc_updateLbl.gridy = 4;
		editPanel.add(updateLbl, gbc_updateLbl);
		
		MaskFormatter yearForm = null;
		try {
			yearForm = new MaskFormatter("####");
		} catch (ParseException e1) {}
		
		JFormattedTextField yearTxt = new JFormattedTextField(yearForm);
		GridBagConstraints gbc_yearTxt = new GridBagConstraints();
		gbc_yearTxt.insets = new Insets(0, 0, 5, 5);
		gbc_yearTxt.fill = GridBagConstraints.HORIZONTAL;
		gbc_yearTxt.gridx = 1;
		gbc_yearTxt.gridy = 4;
		editPanel.add(yearTxt, gbc_yearTxt);
		
		monthBox = new JComboBox<>();
		monthBox.setModel(new DefaultComboBoxModel<String>(C.MONTHS));
		monthBox.addActionListener(e -> 
			saveBtn.setEnabled(true)
		);
		GridBagConstraints gbc_monthBox = new GridBagConstraints();
		gbc_monthBox.insets = new Insets(0, 0, 5, 5);
		gbc_monthBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_monthBox.gridx = 2;
		gbc_monthBox.gridy = 4;
		editPanel.add(monthBox, gbc_monthBox);
		
		JSpinner dateSpin = new JSpinner();
		dateSpin.setModel(new SpinnerNumberModel(1, 1, 31, 1));
		GridBagConstraints gbc_dateSpin = new GridBagConstraints();
		gbc_dateSpin.fill = GridBagConstraints.HORIZONTAL;
		gbc_dateSpin.insets = new Insets(0, 0, 5, 0);
		gbc_dateSpin.gridx = 3;
		gbc_dateSpin.gridy = 4;
		editPanel.add(dateSpin, gbc_dateSpin);
		
		JLabel statusLbl = new JLabel("Status: ");
		GridBagConstraints gbc_statusLbl = new GridBagConstraints();
		gbc_statusLbl.anchor = GridBagConstraints.EAST;
		gbc_statusLbl.insets = new Insets(0, 0, 5, 5);
		gbc_statusLbl.gridx = 0;
		gbc_statusLbl.gridy = 5;
		editPanel.add(statusLbl, gbc_statusLbl);
		
		statusBox = new JComboBox<>();
		statusBox.setModel(new DefaultComboBoxModel<Status>(Status.values()));
		statusBox.addActionListener(e -> 
			saveBtn.setEnabled(true)
		);
		GridBagConstraints gbc_statusBox = new GridBagConstraints();
		gbc_statusBox.gridwidth = 3;
		gbc_statusBox.insets = new Insets(0, 0, 5, 0);
		gbc_statusBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_statusBox.gridx = 1;
		gbc_statusBox.gridy = 5;
		editPanel.add(statusBox, gbc_statusBox);
		
		JLabel userLbl = new JLabel("Current User: ");
		GridBagConstraints gbc_userLbl = new GridBagConstraints();
		gbc_userLbl.anchor = GridBagConstraints.NORTHEAST;
		gbc_userLbl.insets = new Insets(0, 0, 5, 5);
		gbc_userLbl.gridx = 0;
		gbc_userLbl.gridy = 6;
		editPanel.add(userLbl, gbc_userLbl);
		
		userTxt = new JTextField();
		userTxt.getDocument().addDocumentListener(docListen);
		GridBagConstraints gbc_userTxt = new GridBagConstraints();
		gbc_userTxt.gridwidth = 3;
		gbc_userTxt.insets = new Insets(0, 0, 5, 0);
		gbc_userTxt.fill = GridBagConstraints.HORIZONTAL;
		gbc_userTxt.gridx = 1;
		gbc_userTxt.gridy = 6;
		editPanel.add(userTxt, gbc_userTxt);
		userTxt.setColumns(10);
		
		JLabel locLbl = new JLabel("Current Location: ");
		GridBagConstraints gbc_locLbl = new GridBagConstraints();
		gbc_locLbl.anchor = GridBagConstraints.EAST;
		gbc_locLbl.insets = new Insets(0, 0, 0, 5);
		gbc_locLbl.gridx = 0;
		gbc_locLbl.gridy = 7;
		editPanel.add(locLbl, gbc_locLbl);
		
		locTxt = new JTextField();
		locTxt.getDocument().addDocumentListener(docListen);
		GridBagConstraints gbc_locTxt = new GridBagConstraints();
		gbc_locTxt.gridwidth = 3;
		gbc_locTxt.fill = GridBagConstraints.HORIZONTAL;
		gbc_locTxt.gridx = 1;
		gbc_locTxt.gridy = 7;
		editPanel.add(locTxt, gbc_locTxt);
		locTxt.setColumns(10);
		
		JPanel ctrlPanel = new JPanel();
		contentPane.add(ctrlPanel, BorderLayout.SOUTH);
		ctrlPanel.setLayout(new BorderLayout(0, 0));
		
		Component verticalStrut_2 = Box.createVerticalStrut(20);
		ctrlPanel.add(verticalStrut_2, BorderLayout.SOUTH);
		
		Component horizontalStrut_2 = Box.createHorizontalStrut(20);
		ctrlPanel.add(horizontalStrut_2, BorderLayout.WEST);
		
		Component horizontalStrut_3 = Box.createHorizontalStrut(20);
		ctrlPanel.add(horizontalStrut_3, BorderLayout.EAST);
		
		JPanel btnPanel = new JPanel();
		ctrlPanel.add(btnPanel, BorderLayout.CENTER);
		btnPanel.setLayout(new GridLayout(1, 0, 100, 0));
		
		JButton cancelBtn = new JButton("Cancel");
		cancelBtn.addActionListener(e -> {
			dispose();
		});
		btnPanel.add(cancelBtn);
		
		// Save Button is Default Disabled
		saveBtn = new JButton("Save");
		saveBtn.setEnabled(false);
		saveBtn.addActionListener(e -> {
			// New Device
			Device d = new Device(
				hostTxt.getText().trim(), 
				serialTxt.getText().trim(), 
				modelTxt.getText().trim()
			);
			// Update Information
			d.setLoc(locTxt.getText().trim());
			d.setStatus((Status) statusBox.getSelectedItem());
			d.setUser(userTxt.getText().trim());
			d.setOS(osTxt.getText().trim(), 
				LocalDate.of(
					Integer.parseInt(yearTxt.getText().trim()), 
					monthBox.getSelectedIndex() + 1, 
					(int) dateSpin.getValue()));
			// Save Changes
			Main.save(d);
			// Rebuilt Table
			Main.front.tableRebuild();
			// Close
			dispose();
		});
		btnPanel.add(saveBtn);
	}

}
