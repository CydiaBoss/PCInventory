package com.andrew.inv.manage.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.text.ParseException;
import java.time.LocalDate;

import javax.swing.Box;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.MaskFormatter;

import com.andrew.inv.manage.C;
import com.andrew.inv.manage.db.Device.Status;
import com.andrew.inv.manage.db.Search;

import javax.swing.JCheckBox;
import javax.swing.JToggleButton;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

public class AdvSearch extends JDialog {

	/**
	 * Serial
	 */
	private static final long serialVersionUID = 2280432853767677189L;
	
	private JPanel contentPane;
	private JTextField hostTxt;
	private JTextField serialTxt;
	private JTextField osTxt;
	private JFormattedTextField yearTxt;
	private JComboBox<String> monthBox;
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
		setIconImages(C.ICONS);
		setType(Type.POPUP);
		setResizable(false);
		setModalityType(ModalityType.APPLICATION_MODAL);
		setSize(450, 350);
		setLocationRelativeTo(null);
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
		
		JPanel editPanel = new JPanel();
		contentPane.add(editPanel, BorderLayout.CENTER);
		GridBagLayout gbl_editPanel = new GridBagLayout();
		gbl_editPanel.columnWidths = new int[]{0, 0, 0, 0, 0, 0};
		gbl_editPanel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_editPanel.columnWeights = new double[]{0.0, 0.0, 1.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_editPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		editPanel.setLayout(gbl_editPanel);
		
		JCheckBox hostCheck = new JCheckBox("Host Name: ");
		hostCheck.setSelected(true);
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
		
		hostTxt = new JTextField();
		hostTxt.getDocument().addDocumentListener(docListen);
		GridBagConstraints gbc_hostTxt = new GridBagConstraints();
		gbc_hostTxt.gridwidth = 3;
		gbc_hostTxt.insets = new Insets(0, 0, 5, 0);
		gbc_hostTxt.fill = GridBagConstraints.HORIZONTAL;
		gbc_hostTxt.gridx = 2;
		gbc_hostTxt.gridy = 0;
		editPanel.add(hostTxt, gbc_hostTxt);
		hostTxt.setColumns(10);
		
		// Enabler
		hostCheck.addActionListener(e -> {
			// Enable
			if(hostCheck.isSelected()) {
				hostTog.setEnabled(true);
				hostTxt.setEnabled(true);
			// Disable
			}else{
				hostTog.setEnabled(false);
				hostTxt.setEnabled(false);
			}
		});
		
		JCheckBox serialCheck = new JCheckBox("Serial: ");
		GridBagConstraints gbc_serialCheck = new GridBagConstraints();
		gbc_serialCheck.anchor = GridBagConstraints.WEST;
		gbc_serialCheck.insets = new Insets(0, 0, 5, 5);
		gbc_serialCheck.gridx = 0;
		gbc_serialCheck.gridy = 1;
		editPanel.add(serialCheck, gbc_serialCheck);
		
		JToggleButton serialTog = new JToggleButton("Exclude");
		serialTog.setEnabled(false);
		GridBagConstraints gbc_serialTog = new GridBagConstraints();
		gbc_serialTog.insets = new Insets(0, 0, 5, 5);
		gbc_serialTog.gridx = 1;
		gbc_serialTog.gridy = 1;
		editPanel.add(serialTog, gbc_serialTog);
		
		serialTxt = new JTextField();
		serialTxt.setEnabled(false);
		serialTxt.getDocument().addDocumentListener(docListen);
		GridBagConstraints gbc_serialTxt = new GridBagConstraints();
		gbc_serialTxt.gridwidth = 3;
		gbc_serialTxt.insets = new Insets(0, 0, 5, 0);
		gbc_serialTxt.fill = GridBagConstraints.HORIZONTAL;
		gbc_serialTxt.gridx = 2;
		gbc_serialTxt.gridy = 1;
		editPanel.add(serialTxt, gbc_serialTxt);
		serialTxt.setColumns(10);
		
		// Enabler
		serialCheck.addActionListener(e -> {
			// Enable
			if(serialCheck.isSelected()) {
				serialTog.setEnabled(true);
				serialTxt.setEnabled(true);
			// Disable
			}else{
				serialTog.setEnabled(false);
				serialTxt.setEnabled(false);
			}
		});
		
		JCheckBox modelCheck = new JCheckBox("Model: ");
		GridBagConstraints gbc_modelCheck = new GridBagConstraints();
		gbc_modelCheck.anchor = GridBagConstraints.WEST;
		gbc_modelCheck.insets = new Insets(0, 0, 5, 5);
		gbc_modelCheck.gridx = 0;
		gbc_modelCheck.gridy = 2;
		editPanel.add(modelCheck, gbc_modelCheck);
		
		JToggleButton modelTog = new JToggleButton("Exclude");
		modelTog.setEnabled(false);
		GridBagConstraints gbc_modelTog = new GridBagConstraints();
		gbc_modelTog.insets = new Insets(0, 0, 5, 5);
		gbc_modelTog.gridx = 1;
		gbc_modelTog.gridy = 2;
		editPanel.add(modelTog, gbc_modelTog);
		
		modelTxt = new JTextField();
		modelTxt.setEnabled(false);
		modelTxt.getDocument().addDocumentListener(docListen);
		GridBagConstraints gbc_modelTxt = new GridBagConstraints();
		gbc_modelTxt.gridwidth = 3;
		gbc_modelTxt.insets = new Insets(0, 0, 5, 0);
		gbc_modelTxt.fill = GridBagConstraints.HORIZONTAL;
		gbc_modelTxt.gridx = 2;
		gbc_modelTxt.gridy = 2;
		editPanel.add(modelTxt, gbc_modelTxt);
		modelTxt.setColumns(10);
		
		// Enabler
		modelCheck.addActionListener(e -> {
			// Enable
			if(modelCheck.isSelected()) {
				modelTog.setEnabled(true);
				modelTxt.setEnabled(true);
			// Disable
			}else{
				modelTog.setEnabled(false);
				modelTxt.setEnabled(false);
			}
		});
		
		JCheckBox osCheck = new JCheckBox("OS: ");
		GridBagConstraints gbc_osCheck = new GridBagConstraints();
		gbc_osCheck.anchor = GridBagConstraints.WEST;
		gbc_osCheck.fill = GridBagConstraints.VERTICAL;
		gbc_osCheck.insets = new Insets(0, 0, 5, 5);
		gbc_osCheck.gridx = 0;
		gbc_osCheck.gridy = 3;
		editPanel.add(osCheck, gbc_osCheck);
		
		JToggleButton osTog = new JToggleButton("Exclude");
		osTog.setEnabled(false);
		GridBagConstraints gbc_osTog = new GridBagConstraints();
		gbc_osTog.insets = new Insets(0, 0, 5, 5);
		gbc_osTog.gridx = 1;
		gbc_osTog.gridy = 3;
		editPanel.add(osTog, gbc_osTog);

		osTxt = new JTextField();
		osTxt.setEnabled(false);
		osTxt.getDocument().addDocumentListener(docListen);
		GridBagConstraints gbc_osTxt = new GridBagConstraints();
		gbc_osTxt.gridwidth = 3;
		gbc_osTxt.insets = new Insets(0, 0, 5, 0);
		gbc_osTxt.fill = GridBagConstraints.HORIZONTAL;
		gbc_osTxt.gridx = 2;
		gbc_osTxt.gridy = 3;
		editPanel.add(osTxt, gbc_osTxt);
		osTxt.setColumns(10);
		
		// Enabler
		osCheck.addActionListener(e -> {
			// Enable
			if(osCheck.isSelected()) {
				osTog.setEnabled(true);
				osTxt.setEnabled(true);
			// Disable
			}else{
				osTog.setEnabled(false);
				osTxt.setEnabled(false);
			}
		});
		
		JCheckBox dateCheck = new JCheckBox("Date Updated: ");
		GridBagConstraints gbc_dateCheck = new GridBagConstraints();
		gbc_dateCheck.anchor = GridBagConstraints.WEST;
		gbc_dateCheck.insets = new Insets(0, 0, 5, 5);
		gbc_dateCheck.gridx = 0;
		gbc_dateCheck.gridy = 4;
		editPanel.add(dateCheck, gbc_dateCheck);
		
		JToggleButton dateTog = new JToggleButton("Exclude");
		dateTog.setEnabled(false);
		GridBagConstraints gbc_dateTog = new GridBagConstraints();
		gbc_dateTog.insets = new Insets(0, 0, 5, 5);
		gbc_dateTog.gridx = 1;
		gbc_dateTog.gridy = 4;
		editPanel.add(dateTog, gbc_dateTog);
		
		MaskFormatter yearForm = null;
		try {
			yearForm = new MaskFormatter("####");
		} catch (ParseException e1) {}
		
		yearTxt = new JFormattedTextField(yearForm);
		yearTxt.setText(C.DEFAULT_YEAR);
		yearTxt.setEnabled(false);
		yearTxt.getDocument().addDocumentListener(docListen);
		GridBagConstraints gbc_yearTxt = new GridBagConstraints();
		gbc_yearTxt.insets = new Insets(0, 0, 5, 5);
		gbc_yearTxt.fill = GridBagConstraints.HORIZONTAL;
		gbc_yearTxt.gridx = 2;
		gbc_yearTxt.gridy = 4;
		editPanel.add(yearTxt, gbc_yearTxt);
		yearTxt.setColumns(10);
		
		monthBox = new JComboBox<>();
		monthBox.setEnabled(false);
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
		
		JSpinner dateSpin = new JSpinner();
		dateSpin.setEnabled(false);
		dateSpin.setModel(new SpinnerNumberModel(1, 1, 31, 1));
		GridBagConstraints gbc_dateSpin = new GridBagConstraints();
		gbc_dateSpin.fill = GridBagConstraints.BOTH;
		gbc_dateSpin.insets = new Insets(0, 0, 5, 0);
		gbc_dateSpin.gridx = 4;
		gbc_dateSpin.gridy = 4;
		editPanel.add(dateSpin, gbc_dateSpin);
		
		// Enabler
		dateCheck.addActionListener(e -> {
			// Enable
			if(dateCheck.isSelected()) {
				dateTog.setEnabled(true);
				yearTxt.setEnabled(true);
				monthBox.setEnabled(true);
				dateSpin.setEnabled(true);
			// Disable
			}else{
				dateTog.setEnabled(false);
				yearTxt.setEnabled(false);
				monthBox.setEnabled(false);
				dateSpin.setEnabled(false);
			}
		});
		
		JCheckBox statusCheck = new JCheckBox("Status: ");
		GridBagConstraints gbc_statusCheck = new GridBagConstraints();
		gbc_statusCheck.anchor = GridBagConstraints.WEST;
		gbc_statusCheck.insets = new Insets(0, 0, 5, 5);
		gbc_statusCheck.gridx = 0;
		gbc_statusCheck.gridy = 5;
		editPanel.add(statusCheck, gbc_statusCheck);
		
		JToggleButton statusTog = new JToggleButton("Exclude");
		statusTog.setEnabled(false);
		GridBagConstraints gbc_statusTog = new GridBagConstraints();
		gbc_statusTog.insets = new Insets(0, 0, 5, 5);
		gbc_statusTog.gridx = 1;
		gbc_statusTog.gridy = 5;
		editPanel.add(statusTog, gbc_statusTog);
		
		statusBox = new JComboBox<>();
		statusBox.setEnabled(false);
		statusBox.setModel(new DefaultComboBoxModel<Status>(Status.values()));
		statusBox.addActionListener(e -> 
			searchBtn.setEnabled(true)
		);
		GridBagConstraints gbc_statusBox = new GridBagConstraints();
		gbc_statusBox.gridwidth = 3;
		gbc_statusBox.insets = new Insets(0, 0, 5, 0);
		gbc_statusBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_statusBox.gridx = 2;
		gbc_statusBox.gridy = 5;
		editPanel.add(statusBox, gbc_statusBox);
		
		// Enabler
		statusCheck.addActionListener(e -> {
			// Enable
			if(statusCheck.isSelected()) {
				statusTog.setEnabled(true);
				statusBox.setEnabled(true);
			// Disable
			}else{
				statusTog.setEnabled(false);
				statusBox.setEnabled(false);
			}
		});
		
		JCheckBox userCheck = new JCheckBox("Current User: ");
		GridBagConstraints gbc_userCheck = new GridBagConstraints();
		gbc_userCheck.anchor = GridBagConstraints.WEST;
		gbc_userCheck.insets = new Insets(0, 0, 5, 5);
		gbc_userCheck.gridx = 0;
		gbc_userCheck.gridy = 6;
		editPanel.add(userCheck, gbc_userCheck);
		
		JToggleButton userTog = new JToggleButton("Exclude");
		userTog.setEnabled(false);
		GridBagConstraints gbc_userTog = new GridBagConstraints();
		gbc_userTog.insets = new Insets(0, 0, 5, 5);
		gbc_userTog.gridx = 1;
		gbc_userTog.gridy = 6;
		editPanel.add(userTog, gbc_userTog);
		
		userTxt = new JTextField();
		userTxt.setEnabled(false);
		userTxt.getDocument().addDocumentListener(docListen);
		GridBagConstraints gbc_userTxt = new GridBagConstraints();
		gbc_userTxt.gridwidth = 3;
		gbc_userTxt.insets = new Insets(0, 0, 5, 0);
		gbc_userTxt.fill = GridBagConstraints.HORIZONTAL;
		gbc_userTxt.gridx = 2;
		gbc_userTxt.gridy = 6;
		editPanel.add(userTxt, gbc_userTxt);
		userTxt.setColumns(10);
		
		// Enabler
		userCheck.addActionListener(e -> {
			// Enable
			if(userCheck.isSelected()) {
				userTog.setEnabled(true);
				userTxt.setEnabled(true);
			// Disable
			}else{
				userTog.setEnabled(false);
				userTxt.setEnabled(false);
			}
		});
		
		JCheckBox locCheck = new JCheckBox("Current Location: ");
		GridBagConstraints gbc_locCheck = new GridBagConstraints();
		gbc_locCheck.anchor = GridBagConstraints.WEST;
		gbc_locCheck.insets = new Insets(0, 0, 0, 5);
		gbc_locCheck.gridx = 0;
		gbc_locCheck.gridy = 7;
		editPanel.add(locCheck, gbc_locCheck);
		
		JToggleButton locTog = new JToggleButton("Exclude");
		locTog.setEnabled(false);
		GridBagConstraints gbc_locTog = new GridBagConstraints();
		gbc_locTog.insets = new Insets(0, 0, 0, 5);
		gbc_locTog.gridx = 1;
		gbc_locTog.gridy = 7;
		editPanel.add(locTog, gbc_locTog);
		
		locTxt = new JTextField();
		locTxt.setEnabled(false);
		locTxt.getDocument().addDocumentListener(docListen);
		GridBagConstraints gbc_locTxt = new GridBagConstraints();
		gbc_locTxt.gridwidth = 3;
		gbc_locTxt.fill = GridBagConstraints.HORIZONTAL;
		gbc_locTxt.gridx = 2;
		gbc_locTxt.gridy = 7;
		editPanel.add(locTxt, gbc_locTxt);
		locTxt.setColumns(10);
		
		// Enabler
		locCheck.addActionListener(e -> {
			// Enable
			if(locCheck.isSelected()) {
				locTog.setEnabled(true);
				locTxt.setEnabled(true);
			// Disable
			}else{
				locTog.setEnabled(false);
				locTxt.setEnabled(false);
			}
		});
		
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
		btnPanel.setLayout(new GridLayout(1, 0, C.SPACING * 10, 0));
		
		JButton cancelBtn = new JButton("Cancel");
		cancelBtn.addActionListener(e -> {
			setVisible(false);
		});
		btnPanel.add(cancelBtn);
		
		// Save Button is Default Disabled
		searchBtn = new JButton("Search");
		searchBtn.setEnabled(false);
		searchBtn.addActionListener(e -> {
			Search.searchFor(
				// Should this property be checked?
				new boolean[] {
					hostCheck.isSelected(),
					serialCheck.isSelected(),
					modelCheck.isSelected(),
					osCheck.isSelected(),
					dateCheck.isSelected(),
					statusCheck.isSelected(),
					userCheck.isSelected(),
					locCheck.isSelected()
				// Exclude Mode?
				}, new boolean[] {
					hostTog.isSelected(),
					serialTog.isSelected(),
					modelTog.isSelected(),
					osTog.isSelected(),
					dateTog.isSelected(),
					statusTog.isSelected(),
					userTog.isSelected(),
					locTog.isSelected()
				// Values to look for
				}, new String[] {
					hostTxt.getText(),
					serialTxt.getText(),
					modelTxt.getText(),
					osTxt.getText(),
					LocalDate.of(
						Integer.parseInt(yearTxt.getText()), 
						monthBox.getSelectedIndex() + 1, 
						(int) dateSpin.getValue()
					).toString(),
					((Status) statusBox.getSelectedItem()).name(),
					userTxt.getText(),
					locTxt.getText()
				}
			);
		});
		btnPanel.add(searchBtn);
	}

	/**
	 * Make Visible with host name override
	 * 
	 * @param show
	 * Show the GUI
	 * @param hostName
	 * The search bar text
	 */
	public void setVisible(boolean show, String hostName) {
		// Update Text
		hostTxt.setText(hostName);
		// Show
		setVisible(true);
	}
}
