package com.andrew.inv.manage.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.EventObject;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import com.andrew.inv.manage.Main;
import com.andrew.inv.manage.db.Device;

public class FrontPage {

	private JFrame frm;
	private JTextField searchBar;
	private JTable resultDisplay;

	/**
	 * Create the application.
	 */
	public FrontPage() {
		initialize();
		frm.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		// Create Frame
		frm = new JFrame();
		frm.setTitle("PC Inventory");
		frm.setBounds(100, 100, 450, 300);
		frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frm.getContentPane().setLayout(new BorderLayout(0, 0));
		
		// The Search Panel
		JPanel searchPanel = new JPanel();
		frm.getContentPane().add(searchPanel, BorderLayout.NORTH);
		searchPanel.setLayout(new BorderLayout(0, 0));
		
		// Padding between the components
		int searchSpacing = 10;
		
		Component verticalStrut = Box.createVerticalStrut(searchSpacing);
		searchPanel.add(verticalStrut, BorderLayout.NORTH);
		
		Component verticalStrut_1 = Box.createVerticalStrut(searchSpacing);
		searchPanel.add(verticalStrut_1, BorderLayout.SOUTH);
		
		Component horizontalStrut = Box.createHorizontalStrut(searchSpacing);
		searchPanel.add(horizontalStrut, BorderLayout.WEST);
		
		Component horizontalStrut_1 = Box.createHorizontalStrut(searchSpacing);
		searchPanel.add(horizontalStrut_1, BorderLayout.EAST);
		
		JPanel searchSpace = new JPanel();
		searchPanel.add(searchSpace, BorderLayout.CENTER);
		searchSpace.setLayout(new BorderLayout(0, 0));
		
		searchBar = new JTextField();
		searchBar.setToolTipText("Press Enter to Search");
		searchSpace.add(searchBar, BorderLayout.CENTER);
		searchBar.setColumns(10);
		
		JButton searchBtn = new JButton("Search");
		searchSpace.add(searchBtn, BorderLayout.EAST);
		
		Component horizontalStrut_2 = Box.createHorizontalStrut(20);
		frm.getContentPane().add(horizontalStrut_2, BorderLayout.EAST);
		
		Component horizontalStrut_3 = Box.createHorizontalStrut(20);
		frm.getContentPane().add(horizontalStrut_3, BorderLayout.WEST);
		
		// Display Results
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		frm.getContentPane().add(scrollPane, BorderLayout.CENTER);
		
		// Setup Data for Table
		String[] headers = new String[] {
			"Hostname", "User", "Model", "OS", "Status"
		};
		
		Object[][] deviceData = new Object[Main.devices.size()][5];
		
		for(int i = 0; i < Main.devices.size(); i++) {
			Device d = Main.devices.get(i);
			deviceData[i] = new Object[] {
				d, d.getUser(), d.getModel(), d.getOS(), d.getStatus()
			};
		}
		
		// Make Table
		resultDisplay = new JTable(deviceData, headers) {
			
			{
				// Disable Reordering
				getTableHeader().setReorderingAllowed(false);
				// Disable Resizing
				getTableHeader().setResizingAllowed(false);
			}
			
			/**
			 * Internal Serial 
			 */
			private static final long serialVersionUID = -3618415364238047257L;

			/**
			 * Disables Editing
			 */
			@Override
			public boolean editCellAt(int r, int c, EventObject e) {
				return false;
			}
			
		};
		
		// Add Mouse Listener
		resultDisplay.addMouseListener(new MouseAdapter() {
			// On Mouse Click Event (Double Click)
			@Override
			public void mouseClicked(MouseEvent e) {
				// Double Click
				if(e.getClickCount() == 2) {
					int col = resultDisplay.getSelectedColumn(),
						row = resultDisplay.getSelectedRow();
					switch(col) {
						// OS Column
						case 3: 
							new OS((Device) resultDisplay.getModel().getValueAt(row, 0)).setVisible(true);
							break;
						// Status Column
						case 4:
							new Status((Device) resultDisplay.getModel().getValueAt(row, 0)).setVisible(true);
							break;
					}
				}
			}
		});
		
		scrollPane.setViewportView(resultDisplay);
		
		// Control Panel with Button
		JPanel ctrlPanel = new JPanel();
		frm.getContentPane().add(ctrlPanel, BorderLayout.SOUTH);
		ctrlPanel.setLayout(new BorderLayout(0, 0));
		
		Component verticalStrut_2 = Box.createVerticalStrut(searchSpacing);
		ctrlPanel.add(verticalStrut_2, BorderLayout.NORTH);
		
		Component verticalStrut_3 = Box.createVerticalStrut(searchSpacing);
		ctrlPanel.add(verticalStrut_3, BorderLayout.SOUTH);
		
		Component horizontalStrut_4 = Box.createHorizontalStrut(searchSpacing);
		ctrlPanel.add(horizontalStrut_4, BorderLayout.WEST);
		
		Component horizontalStrut_5 = Box.createHorizontalStrut(searchSpacing);
		ctrlPanel.add(horizontalStrut_5, BorderLayout.EAST);
		
		JPanel btnPanel = new JPanel();
		ctrlPanel.add(btnPanel, BorderLayout.CENTER);
		btnPanel.setLayout(new GridLayout(1, 0, 20, 0));
		
		JButton editBtn = new JButton("Edit");
		btnPanel.add(editBtn);
		
		JButton ixBtn = new JButton("Import/Export");
		btnPanel.add(ixBtn);
		
		JButton newBtn = new JButton("Add");
		btnPanel.add(newBtn);
	}

}
