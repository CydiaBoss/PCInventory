package com.andrew.inv.manage.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.Box;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;

import com.andrew.inv.manage.C;
import com.andrew.inv.manage.Main;
import com.andrew.inv.manage.db.Device;

public class ISTStatus extends JDialog {

	/**
	 * Serial
	 */
	private static final long serialVersionUID = 328209274339574732L;
	
	private JPanel contentPane;

	/**
	 * Create the frame.
	 */
	public ISTStatus(Device d) {
		setType(Type.POPUP);
		setIconImages(C.ICONS);
		setResizable(false);
		// Setup
		setTitle("Status Information");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setModalityType(ModalityType.APPLICATION_MODAL);
		setSize(300, 230);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		Component verticalStrut_1 = Box.createVerticalStrut(C.SPACING * 2);
		contentPane.add(verticalStrut_1, BorderLayout.SOUTH);
		
		Component horizontalStrut = Box.createHorizontalStrut(C.SPACING * 2);
		contentPane.add(horizontalStrut, BorderLayout.EAST);
		
		Component horizontalStrut_1 = Box.createHorizontalStrut(C.SPACING * 2);
		contentPane.add(horizontalStrut_1, BorderLayout.WEST);
		
		JPanel infoPanel = new JPanel();
		contentPane.add(infoPanel, BorderLayout.CENTER);
		infoPanel.setLayout(new GridLayout(0, 1, 0, 0));
		
		JPanel lblPanel = new JPanel();
		infoPanel.add(lblPanel);
		lblPanel.setLayout(new GridLayout(0, 1, 0, 0));
		
		JLabel statusLbl = new JLabel("Status: " + d.getStatus());
		lblPanel.add(statusLbl);
		
		JLabel locLbl = new JLabel("Current Location: " + d.getLoc());
		lblPanel.add(locLbl);
		
		JPanel notePanel = new JPanel();
		infoPanel.add(notePanel);
		notePanel.setLayout(new BorderLayout(0, 0));
		
		JLabel infoLbl = new JLabel("Notes: ");
		notePanel.add(infoLbl, BorderLayout.NORTH);
		
		JScrollPane noteScroll = new JScrollPane();
		noteScroll.setViewportBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		noteScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		noteScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		notePanel.add(noteScroll, BorderLayout.CENTER);
		
		JTextArea note = new JTextArea();
		note.setWrapStyleWord(true);
		note.setLineWrap(true);
		noteScroll.setViewportView(note);
		note.setText(d.getNote());
		
		this.addWindowListener(new WindowListener() {

			@Override
			public void windowOpened(WindowEvent e) {}

			// Update the text within the database
			@Override
			public void windowClosing(WindowEvent e) {
				// Add Note
				d.setNote(note.getText());
				// Save
				Main.save();
			}

			@Override
			public void windowClosed(WindowEvent e) {}

			@Override
			public void windowIconified(WindowEvent e) {}

			@Override
			public void windowDeiconified(WindowEvent e) {}

			@Override
			public void windowActivated(WindowEvent e) {}

			@Override
			public void windowDeactivated(WindowEvent e) {}
			
		});
	}

}
