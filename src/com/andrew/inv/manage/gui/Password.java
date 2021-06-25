package com.andrew.inv.manage.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.SystemColor;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;

import com.andrew.inv.manage.C;
import com.andrew.inv.manage.Main;

public class Password extends JDialog {

	/**
	 * Serial
	 */
	private static final long serialVersionUID = -4104625974500149165L;
	private JPasswordField passField;
	private JPasswordField confPassField;

	/**
	 * Create the dialog.
	 */
	public Password(Main main) {
		setTitle("Lock Database");
		setModalityType(ModalityType.APPLICATION_MODAL);
		setResizable(false);
		setIconImages(C.ICONS);
		setBounds(100, 100, 450, 250);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		getContentPane().setLayout(gridBagLayout);
		{
			JTextArea textPane = new JTextArea();
			textPane.setText("Enter the password you wish to use to lock the database.");
			textPane.setLineWrap(true);
			textPane.setWrapStyleWord(true);
			textPane.setEditable(false);
			textPane.setFont(C.DEFAULT_FONT);
			textPane.setBackground(SystemColor.menu);
			GridBagConstraints gbc_textPane = new GridBagConstraints();
			gbc_textPane.gridwidth = 3;
			gbc_textPane.insets = new Insets(0, 0, 5, 5);
			gbc_textPane.fill = GridBagConstraints.BOTH;
			gbc_textPane.gridx = 1;
			gbc_textPane.gridy = 1;
			getContentPane().add(textPane, gbc_textPane);
		}
		{
			JLabel passLbl = new JLabel("Password:");
			GridBagConstraints gbc_passLbl = new GridBagConstraints();
			gbc_passLbl.gridwidth = 3;
			gbc_passLbl.fill = GridBagConstraints.HORIZONTAL;
			gbc_passLbl.insets = new Insets(0, 0, 5, 5);
			gbc_passLbl.gridx = 1;
			gbc_passLbl.gridy = 2;
			getContentPane().add(passLbl, gbc_passLbl);
		}
		{
			passField = new JPasswordField();
			GridBagConstraints gbc_passField = new GridBagConstraints();
			gbc_passField.gridwidth = 3;
			gbc_passField.fill = GridBagConstraints.HORIZONTAL;
			gbc_passField.insets = new Insets(0, 0, 5, 5);
			gbc_passField.gridx = 1;
			gbc_passField.gridy = 3;
			getContentPane().add(passField, gbc_passField);
		}
		{
			JLabel confPassLbl = new JLabel("Confirm Password:");
			GridBagConstraints gbc_confPassLbl = new GridBagConstraints();
			gbc_confPassLbl.gridwidth = 3;
			gbc_confPassLbl.fill = GridBagConstraints.HORIZONTAL;
			gbc_confPassLbl.insets = new Insets(0, 0, 5, 5);
			gbc_confPassLbl.gridx = 1;
			gbc_confPassLbl.gridy = 4;
			getContentPane().add(confPassLbl, gbc_confPassLbl);
		}
		{
			confPassField = new JPasswordField();
			GridBagConstraints gbc_confPassField = new GridBagConstraints();
			gbc_confPassField.gridwidth = 3;
			gbc_confPassField.insets = new Insets(0, 0, 5, 5);
			gbc_confPassField.fill = GridBagConstraints.HORIZONTAL;
			gbc_confPassField.gridx = 1;
			gbc_confPassField.gridy = 5;
			getContentPane().add(confPassField, gbc_confPassField);
		}
		{
			JButton cancelBtn = new JButton("Cancel");
			cancelBtn.addActionListener(e -> 
				// Just Cancel
				dispose()
			);
			GridBagConstraints gbc_cancelBtn = new GridBagConstraints();
			gbc_cancelBtn.fill = GridBagConstraints.HORIZONTAL;
			gbc_cancelBtn.insets = new Insets(0, 0, 0, 5);
			gbc_cancelBtn.gridx = 1;
			gbc_cancelBtn.gridy = 7;
			getContentPane().add(cancelBtn, gbc_cancelBtn);
		}
		{
			JButton submitBtn = new JButton("Submit");
			submitBtn.addActionListener(e -> {
				// Check for match
				// TODO Fix password matcher
				if(passField.getPassword().length != confPassField.getPassword().length) {
					JOptionPane.showMessageDialog(
						this, 
						"Passwords do not match!", 
						"Password Mismatch", 
						JOptionPane.ERROR_MESSAGE
					);
					// Go back
					return;
				}
				// Update Password
				main.setPassword(passField.getPassword());
			});
			GridBagConstraints gbc_submitBtn = new GridBagConstraints();
			gbc_submitBtn.fill = GridBagConstraints.HORIZONTAL;
			gbc_submitBtn.insets = new Insets(0, 0, 0, 5);
			gbc_submitBtn.gridx = 3;
			gbc_submitBtn.gridy = 7;
			getContentPane().add(submitBtn, gbc_submitBtn);
		}
	}

}
