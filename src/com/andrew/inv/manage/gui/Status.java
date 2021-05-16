package com.andrew.inv.manage.gui;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Component;
import javax.swing.Box;
import java.awt.GridLayout;
import javax.swing.JLabel;

public class Status extends JFrame {

	/**
	 * Serial
	 */
	private static final long serialVersionUID = 328209274339574732L;
	
	private JPanel contentPane;

	/**
	 * Create the frame.
	 */
	public Status() {
		setType(Type.POPUP);
		setResizable(false);
		// Setup
		setTitle("Status Information");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 300, 150);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		Component verticalStrut = Box.createVerticalStrut(20);
		contentPane.add(verticalStrut, BorderLayout.NORTH);
		
		Component verticalStrut_1 = Box.createVerticalStrut(20);
		contentPane.add(verticalStrut_1, BorderLayout.SOUTH);
		
		Component horizontalStrut = Box.createHorizontalStrut(20);
		contentPane.add(horizontalStrut, BorderLayout.EAST);
		
		Component horizontalStrut_1 = Box.createHorizontalStrut(20);
		contentPane.add(horizontalStrut_1, BorderLayout.WEST);
		
		JPanel infoPanel = new JPanel();
		contentPane.add(infoPanel, BorderLayout.CENTER);
		infoPanel.setLayout(new GridLayout(0, 1, 0, 0));
		
		JLabel statusLbl = new JLabel("Status: <>");
		infoPanel.add(statusLbl);
		
		JLabel locLbl = new JLabel("Current Location: <>");
		infoPanel.add(locLbl);
	}

}
