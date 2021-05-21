package com.andrew.inv.manage.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.time.LocalDate;

import javax.swing.Box;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.andrew.inv.manage.C;
import com.andrew.inv.manage.Main;
import com.andrew.inv.manage.db.Device;
import com.andrew.inv.manage.db.Device.Status;
import javax.swing.SwingConstants;
import javax.swing.JCheckBox;
import javax.swing.JToggleButton;

public class AdvSearch extends JDialog {

	/**
	 * Serial
	 */
	private static final long serialVersionUID = 2280432853767677189L;
	
	private JPanel contentPane;
	private JTextField hostTxt;
	private JTextField serialTxt;
	private JTextField osTxt;
	private JTextField yearTxt;
	private JComboBox<String> monthBox;
	private JTextField dateTxt;
	private JComboBox<Status> statusBox;
	private JTextField userTxt;
	private JTextField locTxt;
	private JButton searchBtn;

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
			searchBtn.setEnabled(true);
		}
		
	};

	private JTextField modelTxt;
	
	/**
	 * Create the frame.
	 */
	public AdvSearch() {
		setTitle("Advance Search");
		setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/icon.png")));
		setType(Type.POPUP);
		setResizable(false);
		setModalityType(ModalityType.APPLICATION_MODAL);
		setBounds(100, 100, 450, 350);
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
		gbl_editPanel.columnWidths = new int[]{0, 0, 0, 0, 0, 0};
		gbl_editPanel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_editPanel.columnWeights = new double[]{0.0, 0.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
		gbl_editPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		editPanel.setLayout(gbl_editPanel);
		
		hostTxt = new JTextField();
		hostTxt.getDocument().addDocumentListener(docListen);
		
		JCheckBox hostCheck = new JCheckBox("Host Name: ");
		hostCheck.setHorizontalAlignment(SwingConstants.RIGHT);
		GridBagConstraints gbc_hostCheck = new GridBagConstraints();
		gbc_hostCheck.anchor = GridBagConstraints.WEST;
		gbc_hostCheck.insets = new Insets(0, 0, 5, 5);
		gbc_hostCheck.gridx = 0;
		gbc_hostCheck.gridy = 0;
		editPanel.add(hostCheck, gbc_hostCheck);
		
		JToggleButton hostTog = new JToggleButton("Exclude");
		GridBagConstraints gbc_hostTog = new GridBagConstraints();
		gbc_hostTog.insets = new Insets(0, 0, 5, 5);
		gbc_hostTog.gridx = 1;
		gbc_hostTog.gridy = 0;
		editPanel.add(hostTog, gbc_hostTog);
		GridBagConstraints gbc_hostTxt = new GridBagConstraints();
		gbc_hostTxt.gridwidth = 3;
		gbc_hostTxt.insets = new Insets(0, 0, 5, 0);
		gbc_hostTxt.fill = GridBagConstraints.HORIZONTAL;
		gbc_hostTxt.gridx = 2;
		gbc_hostTxt.gridy = 0;
		editPanel.add(hostTxt, gbc_hostTxt);
		hostTxt.setColumns(10);
		
		JCheckBox serialCheck = new JCheckBox("Serial: ");
		GridBagConstraints gbc_serialCheck = new GridBagConstraints();
		gbc_serialCheck.anchor = GridBagConstraints.WEST;
		gbc_serialCheck.insets = new Insets(0, 0, 5, 5);
		gbc_serialCheck.gridx = 0;
		gbc_serialCheck.gridy = 1;
		editPanel.add(serialCheck, gbc_serialCheck);
		
		JToggleButton serialTog = new JToggleButton("Exclude");
		GridBagConstraints gbc_serialTog = new GridBagConstraints();
		gbc_serialTog.insets = new Insets(0, 0, 5, 5);
		gbc_serialTog.gridx = 1;
		gbc_serialTog.gridy = 1;
		editPanel.add(serialTog, gbc_serialTog);
		
		serialTxt = new JTextField();
		GridBagConstraints gbc_serialTxt = new GridBagConstraints();
		gbc_serialTxt.gridwidth = 3;
		gbc_serialTxt.insets = new Insets(0, 0, 5, 0);
		gbc_serialTxt.fill = GridBagConstraints.HORIZONTAL;
		gbc_serialTxt.gridx = 2;
		gbc_serialTxt.gridy = 1;
		editPanel.add(serialTxt, gbc_serialTxt);
		serialTxt.setColumns(10);
		
		JCheckBox modelCheck = new JCheckBox("Model: ");
		GridBagConstraints gbc_modelCheck = new GridBagConstraints();
		gbc_modelCheck.anchor = GridBagConstraints.WEST;
		gbc_modelCheck.insets = new Insets(0, 0, 5, 5);
		gbc_modelCheck.gridx = 0;
		gbc_modelCheck.gridy = 2;
		editPanel.add(modelCheck, gbc_modelCheck);
		
		JToggleButton modelTog = new JToggleButton("Exclude");
		GridBagConstraints gbc_modelTog = new GridBagConstraints();
		gbc_modelTog.insets = new Insets(0, 0, 5, 5);
		gbc_modelTog.gridx = 1;
		gbc_modelTog.gridy = 2;
		editPanel.add(modelTog, gbc_modelTog);
		
		modelTxt = new JTextField();
		GridBagConstraints gbc_modelTxt = new GridBagConstraints();
		gbc_modelTxt.gridwidth = 3;
		gbc_modelTxt.insets = new Insets(0, 0, 5, 0);
		gbc_modelTxt.fill = GridBagConstraints.HORIZONTAL;
		gbc_modelTxt.gridx = 2;
		gbc_modelTxt.gridy = 2;
		editPanel.add(modelTxt, gbc_modelTxt);
		modelTxt.setColumns(10);
		
		osTxt = new JTextField();
		osTxt.getDocument().addDocumentListener(docListen);
		
		JCheckBox osCheck = new JCheckBox("OS: ");
		GridBagConstraints gbc_osCheck = new GridBagConstraints();
		gbc_osCheck.anchor = GridBagConstraints.WEST;
		gbc_osCheck.fill = GridBagConstraints.VERTICAL;
		gbc_osCheck.insets = new Insets(0, 0, 5, 5);
		gbc_osCheck.gridx = 0;
		gbc_osCheck.gridy = 3;
		editPanel.add(osCheck, gbc_osCheck);
		
		JToggleButton osTog = new JToggleButton("Include");
		GridBagConstraints gbc_osTog = new GridBagConstraints();
		gbc_osTog.insets = new Insets(0, 0, 5, 5);
		gbc_osTog.gridx = 1;
		gbc_osTog.gridy = 3;
		editPanel.add(osTog, gbc_osTog);
		GridBagConstraints gbc_osTxt = new GridBagConstraints();
		gbc_osTxt.gridwidth = 3;
		gbc_osTxt.insets = new Insets(0, 0, 5, 0);
		gbc_osTxt.fill = GridBagConstraints.HORIZONTAL;
		gbc_osTxt.gridx = 2;
		gbc_osTxt.gridy = 3;
		editPanel.add(osTxt, gbc_osTxt);
		osTxt.setColumns(10);
		
		yearTxt = new JTextField();
		yearTxt.getDocument().addDocumentListener(docListen);
		
		JCheckBox dateCheck = new JCheckBox("Date Updated: ");
		GridBagConstraints gbc_dateCheck = new GridBagConstraints();
		gbc_dateCheck.anchor = GridBagConstraints.WEST;
		gbc_dateCheck.insets = new Insets(0, 0, 5, 5);
		gbc_dateCheck.gridx = 0;
		gbc_dateCheck.gridy = 4;
		editPanel.add(dateCheck, gbc_dateCheck);
		
		JToggleButton dateTog = new JToggleButton("Exclude");
		GridBagConstraints gbc_dateTog = new GridBagConstraints();
		gbc_dateTog.insets = new Insets(0, 0, 5, 5);
		gbc_dateTog.gridx = 1;
		gbc_dateTog.gridy = 4;
		editPanel.add(dateTog, gbc_dateTog);
		GridBagConstraints gbc_yearTxt = new GridBagConstraints();
		gbc_yearTxt.insets = new Insets(0, 0, 5, 5);
		gbc_yearTxt.fill = GridBagConstraints.HORIZONTAL;
		gbc_yearTxt.gridx = 2;
		gbc_yearTxt.gridy = 4;
		editPanel.add(yearTxt, gbc_yearTxt);
		yearTxt.setColumns(10);
		
		monthBox = new JComboBox<>();
		monthBox.setModel(new DefaultComboBoxModel<String>(C.MONTHS));
		monthBox.addActionListener(e -> 
			searchBtn.setEnabled(true)
		);
		GridBagConstraints gbc_monthBox = new GridBagConstraints();
		gbc_monthBox.insets = new Insets(0, 0, 5, 5);
		gbc_monthBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_monthBox.gridx = 3;
		gbc_monthBox.gridy = 4;
		editPanel.add(monthBox, gbc_monthBox);
		
		dateTxt = new JTextField();
		dateTxt.getDocument().addDocumentListener(docListen);
		GridBagConstraints gbc_dateTxt = new GridBagConstraints();
		gbc_dateTxt.insets = new Insets(0, 0, 5, 0);
		gbc_dateTxt.fill = GridBagConstraints.HORIZONTAL;
		gbc_dateTxt.gridx = 4;
		gbc_dateTxt.gridy = 4;
		editPanel.add(dateTxt, gbc_dateTxt);
		dateTxt.setColumns(10);
		
		statusBox = new JComboBox<>();
		statusBox.setModel(new DefaultComboBoxModel<Status>(Status.values()));
		statusBox.addActionListener(e -> 
			searchBtn.setEnabled(true)
		);
		
		JCheckBox statusCheck = new JCheckBox("Status: ");
		GridBagConstraints gbc_statusCheck = new GridBagConstraints();
		gbc_statusCheck.anchor = GridBagConstraints.WEST;
		gbc_statusCheck.insets = new Insets(0, 0, 5, 5);
		gbc_statusCheck.gridx = 0;
		gbc_statusCheck.gridy = 5;
		editPanel.add(statusCheck, gbc_statusCheck);
		
		JToggleButton statusTog = new JToggleButton("Exclude");
		GridBagConstraints gbc_statusTog = new GridBagConstraints();
		gbc_statusTog.insets = new Insets(0, 0, 5, 5);
		gbc_statusTog.gridx = 1;
		gbc_statusTog.gridy = 5;
		editPanel.add(statusTog, gbc_statusTog);
		GridBagConstraints gbc_statusBox = new GridBagConstraints();
		gbc_statusBox.gridwidth = 3;
		gbc_statusBox.insets = new Insets(0, 0, 5, 0);
		gbc_statusBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_statusBox.gridx = 2;
		gbc_statusBox.gridy = 5;
		editPanel.add(statusBox, gbc_statusBox);
		
		userTxt = new JTextField();
		userTxt.getDocument().addDocumentListener(docListen);
		
		JCheckBox userCheck = new JCheckBox("Current User: ");
		GridBagConstraints gbc_userCheck = new GridBagConstraints();
		gbc_userCheck.anchor = GridBagConstraints.WEST;
		gbc_userCheck.insets = new Insets(0, 0, 5, 5);
		gbc_userCheck.gridx = 0;
		gbc_userCheck.gridy = 6;
		editPanel.add(userCheck, gbc_userCheck);
		
		JToggleButton userTog = new JToggleButton("Exclude");
		GridBagConstraints gbc_userTog = new GridBagConstraints();
		gbc_userTog.insets = new Insets(0, 0, 5, 5);
		gbc_userTog.gridx = 1;
		gbc_userTog.gridy = 6;
		editPanel.add(userTog, gbc_userTog);
		GridBagConstraints gbc_userTxt = new GridBagConstraints();
		gbc_userTxt.gridwidth = 3;
		gbc_userTxt.insets = new Insets(0, 0, 5, 0);
		gbc_userTxt.fill = GridBagConstraints.HORIZONTAL;
		gbc_userTxt.gridx = 2;
		gbc_userTxt.gridy = 6;
		editPanel.add(userTxt, gbc_userTxt);
		userTxt.setColumns(10);
		
		locTxt = new JTextField();
		locTxt.getDocument().addDocumentListener(docListen);
		
		JCheckBox locCheck = new JCheckBox("Current Location: ");
		GridBagConstraints gbc_locCheck = new GridBagConstraints();
		gbc_locCheck.anchor = GridBagConstraints.WEST;
		gbc_locCheck.insets = new Insets(0, 0, 0, 5);
		gbc_locCheck.gridx = 0;
		gbc_locCheck.gridy = 7;
		editPanel.add(locCheck, gbc_locCheck);
		
		JToggleButton locTog = new JToggleButton("Exclude");
		GridBagConstraints gbc_locTog = new GridBagConstraints();
		gbc_locTog.insets = new Insets(0, 0, 0, 5);
		gbc_locTog.gridx = 1;
		gbc_locTog.gridy = 7;
		editPanel.add(locTog, gbc_locTog);
		GridBagConstraints gbc_locTxt = new GridBagConstraints();
		gbc_locTxt.gridwidth = 3;
		gbc_locTxt.fill = GridBagConstraints.HORIZONTAL;
		gbc_locTxt.gridx = 2;
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
		searchBtn = new JButton("Search");
		searchBtn.setEnabled(false);
		searchBtn.addActionListener(e -> {
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
					Integer.parseInt(dateTxt.getText().trim())));
			// Save Changes
			Main.save(d);
			// Rebuilt Table
			Main.front.tableRebuild();
			// Close
			dispose();
		});
		btnPanel.add(searchBtn);
	}

}
