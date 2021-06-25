package com.andrew.inv.manage.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.SystemColor;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import com.andrew.inv.manage.C;

/**
 * The password entry frame 
 * 
 * @author andre
 *
 */
public class Unlock extends JDialog{
	
	/**
	 * Serial
	 */
	private static final long serialVersionUID = -8320493518309332304L;
	
	private JPanel contentPane;
	private JPasswordField passwordField;
	
	private boolean isCancelled = true;
	
	/**
	 * Create the frame.
	 * 
	 * @wbp.parser.entryPoint
	 */
	public void initialize() {
		// Setup
		setResizable(false);
		setTitle("Enter Password");
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setModalityType(ModalityType.APPLICATION_MODAL);
		setBounds(100, 100, 350, 220);
		setIconImages(C.ICONS);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{0, 0, 0, 0};
		gbl_panel.rowHeights = new int[]{0, 0, 0, 0};
		gbl_panel.columnWeights = new double[]{1.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		JTextArea instructPane = new JTextArea();
		instructPane.setWrapStyleWord(true);
		instructPane.setLineWrap(true);
		instructPane.setFont(C.DEFAULT_FONT);
		instructPane.setText("The database you wish to open is locked with a password. Please enter the password.");
		instructPane.setEditable(false);
		instructPane.setBackground(SystemColor.menu);
		GridBagConstraints gbc_instructPane = new GridBagConstraints();
		gbc_instructPane.gridwidth = 3;
		gbc_instructPane.insets = new Insets(0, 0, 5, 5);
		gbc_instructPane.fill = GridBagConstraints.BOTH;
		gbc_instructPane.gridx = 0;
		gbc_instructPane.gridy = 0;
		panel.add(instructPane, gbc_instructPane);
		
		passwordField = new JPasswordField();
		GridBagConstraints gbc_passwordField = new GridBagConstraints();
		gbc_passwordField.fill = GridBagConstraints.HORIZONTAL;
		gbc_passwordField.gridwidth = 3;
		gbc_passwordField.insets = new Insets(0, 0, 5, 0);
		gbc_passwordField.gridx = 0;
		gbc_passwordField.gridy = 1;
		panel.add(passwordField, gbc_passwordField);
		
		JButton cancelBtn = new JButton("Cancel");
		GridBagConstraints gbc_cancelBtn = new GridBagConstraints();
		gbc_cancelBtn.fill = GridBagConstraints.HORIZONTAL;
		gbc_cancelBtn.insets = new Insets(0, 0, 0, 5);
		gbc_cancelBtn.gridx = 0;
		gbc_cancelBtn.gridy = 2;
		panel.add(cancelBtn, gbc_cancelBtn);
		
		JButton submitBtn = new JButton("Submit");
		GridBagConstraints gbc_submitBtn = new GridBagConstraints();
		gbc_submitBtn.fill = GridBagConstraints.HORIZONTAL;
		gbc_submitBtn.gridx = 2;
		gbc_submitBtn.gridy = 2;
		panel.add(submitBtn, gbc_submitBtn);
		
		Component horizontalStrut = Box.createHorizontalStrut(20);
		contentPane.add(horizontalStrut, BorderLayout.WEST);
		
		Component horizontalStrut_1 = Box.createHorizontalStrut(20);
		contentPane.add(horizontalStrut_1, BorderLayout.EAST);
		
		Component verticalStrut = Box.createVerticalStrut(20);
		contentPane.add(verticalStrut, BorderLayout.NORTH);
		
		Component verticalStrut_1 = Box.createVerticalStrut(20);
		contentPane.add(verticalStrut_1, BorderLayout.SOUTH);
		submitBtn.addActionListener(e -> {
			// Disabled
			isCancelled = false;
			// Close
			dispose();
		});
		cancelBtn.addActionListener(e -> {
			// Close
			dispose();
		});
		
		// Show
		setVisible(true);
	}

	/**
	 * Returns the entered password if not cancelled or closed
	 * 
	 * @return
	 * The password or null if cancelled
	 */
	public char[] getPassword() {
		return (isCancelled)? null : passwordField.getPassword();
	}
	
}
