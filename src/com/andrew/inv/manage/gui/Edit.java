package com.andrew.inv.manage.gui;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.GridLayout;
import java.awt.Component;
import javax.swing.Box;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.DefaultComboBoxModel;
import com.andrew.inv.manage.db.Device.Status;

public class Edit extends JFrame {

	/**
	 * Serial
	 */
	private static final long serialVersionUID = 2280432853767677189L;
	
	private JPanel contentPane;
	private JTextField hostTxt;
	private JTextField osTxt;
	private JTextField updateTxt;
	private JTextField userTxt;
	private JTextField textField;

	/**
	 * Create the frame.
	 */
	public Edit() {
		setTitle("Edit");
		setType(Type.POPUP);
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 275);
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
		gbl_editPanel.columnWidths = new int[]{0, 0, 0};
		gbl_editPanel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0};
		gbl_editPanel.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_editPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		editPanel.setLayout(gbl_editPanel);
		
		JLabel hostLbl = new JLabel("Host Name: ");
		GridBagConstraints gbc_hostLbl = new GridBagConstraints();
		gbc_hostLbl.anchor = GridBagConstraints.EAST;
		gbc_hostLbl.insets = new Insets(0, 0, 5, 5);
		gbc_hostLbl.gridx = 0;
		gbc_hostLbl.gridy = 0;
		editPanel.add(hostLbl, gbc_hostLbl);
		
		hostTxt = new JTextField();
		GridBagConstraints gbc_hostTxt = new GridBagConstraints();
		gbc_hostTxt.insets = new Insets(0, 0, 5, 0);
		gbc_hostTxt.fill = GridBagConstraints.HORIZONTAL;
		gbc_hostTxt.gridx = 1;
		gbc_hostTxt.gridy = 0;
		editPanel.add(hostTxt, gbc_hostTxt);
		hostTxt.setColumns(10);
		
		JLabel osLbl = new JLabel("OS: ");
		GridBagConstraints gbc_osLbl = new GridBagConstraints();
		gbc_osLbl.anchor = GridBagConstraints.EAST;
		gbc_osLbl.insets = new Insets(0, 0, 5, 5);
		gbc_osLbl.gridx = 0;
		gbc_osLbl.gridy = 1;
		editPanel.add(osLbl, gbc_osLbl);
		
		osTxt = new JTextField();
		GridBagConstraints gbc_osTxt = new GridBagConstraints();
		gbc_osTxt.insets = new Insets(0, 0, 5, 0);
		gbc_osTxt.fill = GridBagConstraints.HORIZONTAL;
		gbc_osTxt.gridx = 1;
		gbc_osTxt.gridy = 1;
		editPanel.add(osTxt, gbc_osTxt);
		osTxt.setColumns(10);
		
		JLabel updateLbl = new JLabel("Update Date: ");
		GridBagConstraints gbc_updateLbl = new GridBagConstraints();
		gbc_updateLbl.anchor = GridBagConstraints.EAST;
		gbc_updateLbl.insets = new Insets(0, 0, 5, 5);
		gbc_updateLbl.gridx = 0;
		gbc_updateLbl.gridy = 2;
		editPanel.add(updateLbl, gbc_updateLbl);
		
		updateTxt = new JTextField();
		GridBagConstraints gbc_updateTxt = new GridBagConstraints();
		gbc_updateTxt.insets = new Insets(0, 0, 5, 0);
		gbc_updateTxt.fill = GridBagConstraints.HORIZONTAL;
		gbc_updateTxt.gridx = 1;
		gbc_updateTxt.gridy = 2;
		editPanel.add(updateTxt, gbc_updateTxt);
		updateTxt.setColumns(10);
		
		JLabel statusLbl = new JLabel("Status: ");
		GridBagConstraints gbc_statusLbl = new GridBagConstraints();
		gbc_statusLbl.anchor = GridBagConstraints.EAST;
		gbc_statusLbl.insets = new Insets(0, 0, 5, 5);
		gbc_statusLbl.gridx = 0;
		gbc_statusLbl.gridy = 3;
		editPanel.add(statusLbl, gbc_statusLbl);
		
		JComboBox<Status> statusBox = new JComboBox<>();
		statusBox.setModel(new DefaultComboBoxModel<Status>(Status.values()));
		GridBagConstraints gbc_statusBox = new GridBagConstraints();
		gbc_statusBox.insets = new Insets(0, 0, 5, 0);
		gbc_statusBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_statusBox.gridx = 1;
		gbc_statusBox.gridy = 3;
		editPanel.add(statusBox, gbc_statusBox);
		
		JLabel userLbl = new JLabel("Current User: ");
		GridBagConstraints gbc_userLbl = new GridBagConstraints();
		gbc_userLbl.anchor = GridBagConstraints.NORTHEAST;
		gbc_userLbl.insets = new Insets(0, 0, 5, 5);
		gbc_userLbl.gridx = 0;
		gbc_userLbl.gridy = 4;
		editPanel.add(userLbl, gbc_userLbl);
		
		userTxt = new JTextField();
		GridBagConstraints gbc_userTxt = new GridBagConstraints();
		gbc_userTxt.insets = new Insets(0, 0, 5, 0);
		gbc_userTxt.fill = GridBagConstraints.HORIZONTAL;
		gbc_userTxt.gridx = 1;
		gbc_userTxt.gridy = 4;
		editPanel.add(userTxt, gbc_userTxt);
		userTxt.setColumns(10);
		
		JLabel locLbl = new JLabel("Current Location: ");
		GridBagConstraints gbc_locLbl = new GridBagConstraints();
		gbc_locLbl.anchor = GridBagConstraints.EAST;
		gbc_locLbl.insets = new Insets(0, 0, 0, 5);
		gbc_locLbl.gridx = 0;
		gbc_locLbl.gridy = 5;
		editPanel.add(locLbl, gbc_locLbl);
		
		textField = new JTextField();
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridx = 1;
		gbc_textField.gridy = 5;
		editPanel.add(textField, gbc_textField);
		textField.setColumns(10);
		
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
		btnPanel.add(cancelBtn);
		
		JButton saveBtn = new JButton("Save");
		btnPanel.add(saveBtn);
	}

}
