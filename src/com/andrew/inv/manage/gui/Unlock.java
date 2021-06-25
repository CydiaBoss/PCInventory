package com.andrew.inv.manage.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.SystemColor;

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
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 0, 0, 0, 0, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0, 0, 0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, 1.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		JTextArea instructPane = new JTextArea();
		instructPane.setEditable(false);
		instructPane.setFont(C.DEFAULT_FONT);
		instructPane.setText("The database you wish to open is locked with a password. Please enter the password.");
		instructPane.setWrapStyleWord(true);
		instructPane.setLineWrap(true);
		instructPane.setBackground(SystemColor.menu);
		GridBagConstraints gbc_instructPane = new GridBagConstraints();
		gbc_instructPane.gridwidth = 3;
		gbc_instructPane.insets = new Insets(0, 0, 5, 5);
		gbc_instructPane.fill = GridBagConstraints.BOTH;
		gbc_instructPane.gridx = 1;
		gbc_instructPane.gridy = 1;
		contentPane.add(instructPane, gbc_instructPane);
		
		passwordField = new JPasswordField();
		GridBagConstraints gbc_passwordField = new GridBagConstraints();
		gbc_passwordField.gridwidth = 3;
		gbc_passwordField.insets = new Insets(0, 0, 5, 5);
		gbc_passwordField.fill = GridBagConstraints.HORIZONTAL;
		gbc_passwordField.gridx = 1;
		gbc_passwordField.gridy = 2;
		contentPane.add(passwordField, gbc_passwordField);
		
		JButton cancelBtn = new JButton("Cancel");
		cancelBtn.addActionListener(e -> {
			// Close
			dispose();
		});
		GridBagConstraints gbc_cancelBtn = new GridBagConstraints();
		gbc_cancelBtn.fill = GridBagConstraints.HORIZONTAL;
		gbc_cancelBtn.insets = new Insets(0, 0, 0, 5);
		gbc_cancelBtn.gridx = 1;
		gbc_cancelBtn.gridy = 4;
		contentPane.add(cancelBtn, gbc_cancelBtn);
		
		JButton submitBtn = new JButton("Submit");
		submitBtn.addActionListener(e -> {
			// Disabled
			isCancelled = false;
			// Close
			dispose();
		});
		GridBagConstraints gbc_submitBtn = new GridBagConstraints();
		gbc_submitBtn.fill = GridBagConstraints.HORIZONTAL;
		gbc_submitBtn.insets = new Insets(0, 0, 0, 5);
		gbc_submitBtn.gridx = 3;
		gbc_submitBtn.gridy = 4;
		contentPane.add(submitBtn, gbc_submitBtn);
		
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
