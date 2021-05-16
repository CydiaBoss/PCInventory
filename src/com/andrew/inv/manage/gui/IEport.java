package com.andrew.inv.manage.gui;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Component;
import javax.swing.Box;
import javax.swing.ButtonGroup;

import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.JRadioButton;
import java.awt.Insets;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JProgressBar;
import java.awt.Color;

public class IEport extends JFrame {

	/**
	 * Serial
	 */
	private static final long serialVersionUID = -9197196751717797647L;
	
	private JPanel contentPane;
	private JTextField inFileTxt;

	/**
	 * Create the frame.
	 */
	public IEport() {
		setTitle("Import & Export");
		setType(Type.POPUP);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 274);
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
		
		JPanel iePanel = new JPanel();
		contentPane.add(iePanel, BorderLayout.CENTER);
		GridBagLayout gbl_iePanel = new GridBagLayout();
		gbl_iePanel.columnWidths = new int[]{0, 0, 0, 0};
		gbl_iePanel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0};
		gbl_iePanel.columnWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_iePanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
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
		gbc_inFileLbl.insets = new Insets(0, 0, 5, 5);
		gbc_inFileLbl.gridx = 0;
		gbc_inFileLbl.gridy = 1;
		iePanel.add(inFileLbl, gbc_inFileLbl);
		
		inFileTxt = new JTextField();
		GridBagConstraints gbc_inFileTxt = new GridBagConstraints();
		gbc_inFileTxt.fill = GridBagConstraints.HORIZONTAL;
		gbc_inFileTxt.insets = new Insets(0, 0, 5, 5);
		gbc_inFileTxt.gridx = 1;
		gbc_inFileTxt.gridy = 1;
		iePanel.add(inFileTxt, gbc_inFileTxt);
		inFileTxt.setColumns(10);
		
		JButton inFileBtn = new JButton("...");
		GridBagConstraints gbc_inFileBtn = new GridBagConstraints();
		gbc_inFileBtn.insets = new Insets(0, 0, 5, 0);
		gbc_inFileBtn.gridx = 2;
		gbc_inFileBtn.gridy = 1;
		iePanel.add(inFileBtn, gbc_inFileBtn);
		
		JLabel fileTypeLbl = new JLabel("File Type: ");
		GridBagConstraints gbc_fileTypeLbl = new GridBagConstraints();
		gbc_fileTypeLbl.anchor = GridBagConstraints.EAST;
		gbc_fileTypeLbl.insets = new Insets(0, 0, 5, 5);
		gbc_fileTypeLbl.gridx = 0;
		gbc_fileTypeLbl.gridy = 2;
		iePanel.add(fileTypeLbl, gbc_fileTypeLbl);
		
		Box fileTypeBox = Box.createHorizontalBox();
		GridBagConstraints gbc_fileTypeBox = new GridBagConstraints();
		gbc_fileTypeBox.insets = new Insets(0, 0, 5, 0);
		gbc_fileTypeBox.anchor = GridBagConstraints.WEST;
		gbc_fileTypeBox.gridwidth = 2;
		gbc_fileTypeBox.gridx = 1;
		gbc_fileTypeBox.gridy = 2;
		iePanel.add(fileTypeBox, gbc_fileTypeBox);
		
		ButtonGroup fileBtnGrp = new ButtonGroup();
		
		JRadioButton xlsxBtn = new JRadioButton(".xlsx");
		fileTypeBox.add(xlsxBtn);
		fileBtnGrp.add(xlsxBtn);
		
		JRadioButton csvBtn = new JRadioButton(".csv");
		fileTypeBox.add(csvBtn);
		fileBtnGrp.add(csvBtn);
		
		JProgressBar progressBar = new JProgressBar();
		progressBar.setStringPainted(true);
		progressBar.setForeground(Color.GREEN);
		GridBagConstraints gbc_progressBar = new GridBagConstraints();
		gbc_progressBar.insets = new Insets(0, 0, 5, 0);
		gbc_progressBar.fill = GridBagConstraints.BOTH;
		gbc_progressBar.gridwidth = 3;
		gbc_progressBar.gridx = 0;
		gbc_progressBar.gridy = 4;
		iePanel.add(progressBar, gbc_progressBar);
		
		JLabel progLbl = new JLabel("...");
		GridBagConstraints gbc_progLbl = new GridBagConstraints();
		gbc_progLbl.gridwidth = 3;
		gbc_progLbl.insets = new Insets(0, 0, 0, 5);
		gbc_progLbl.gridx = 0;
		gbc_progLbl.gridy = 5;
		iePanel.add(progLbl, gbc_progLbl);
		
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
		
		JButton startBtn = new JButton("Start");
		btnPanel.add(startBtn);
	}

}
