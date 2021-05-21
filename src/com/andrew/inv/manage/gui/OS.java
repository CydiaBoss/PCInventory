package com.andrew.inv.manage.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;

import javax.swing.Box;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.andrew.inv.manage.C;
import com.andrew.inv.manage.db.Device;

public class OS extends JDialog {

	/**
	 * Serial
	 */
	private static final long serialVersionUID = 7289470778621898897L;
	
	private JPanel contentPane;

	/**
	 * Create the frame.
	 */
	public OS(Device d) {
		setType(Type.POPUP);
		setIconImages(C.ICONS);
		setResizable(false);
		// Setup
		setTitle(d.getHost() + "'s OS Information");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setModalityType(ModalityType.APPLICATION_MODAL);
		setSize(300, 150);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		Component verticalStrut = Box.createVerticalStrut(C.SPACING * 2);
		contentPane.add(verticalStrut, BorderLayout.NORTH);
		
		Component verticalStrut_1 = Box.createVerticalStrut(C.SPACING * 2);
		contentPane.add(verticalStrut_1, BorderLayout.SOUTH);
		
		Component horizontalStrut = Box.createHorizontalStrut(C.SPACING * 2);
		contentPane.add(horizontalStrut, BorderLayout.EAST);
		
		Component horizontalStrut_1 = Box.createHorizontalStrut(C.SPACING * 2);
		contentPane.add(horizontalStrut_1, BorderLayout.WEST);
		
		JPanel infoPanel = new JPanel();
		contentPane.add(infoPanel, BorderLayout.CENTER);
		infoPanel.setLayout(new GridLayout(0, 1, 0, 0));
		
		JLabel osLbl = new JLabel("Operating System: " + d.getOS());
		infoPanel.add(osLbl);
		
		JLabel dateLbl = new JLabel("Date Updated: " + d.getDateUpdated());
		infoPanel.add(dateLbl);
	}

}
