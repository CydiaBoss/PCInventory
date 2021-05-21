package com.andrew.inv.manage.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.EventObject;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;

import com.andrew.inv.manage.Main;
import com.andrew.inv.manage.db.Device;
import com.andrew.inv.manage.db.Search;

import javax.swing.table.DefaultTableModel;

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
		frm.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/icon.png")));
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
		searchBtn.addActionListener(e -> {
			if(searchBar.getText().trim().equals("")) 
				tableRebuild();
			else
				Search.searchFor(searchBar.getText().trim());
		});
		searchSpace.add(searchBtn, BorderLayout.EAST);
		
		// Setup Enter Detection
		searchBar.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				// Search on Enter Key
				if(e.getKeyChar() == '\n')
					searchBtn.doClick();
			}

			@Override
			public void keyPressed(KeyEvent e) {}

			@Override
			public void keyReleased(KeyEvent e) {}
			
		});
		
		Component horizontalStrut_2 = Box.createHorizontalStrut(20);
		frm.getContentPane().add(horizontalStrut_2, BorderLayout.EAST);
		
		Component horizontalStrut_3 = Box.createHorizontalStrut(20);
		frm.getContentPane().add(horizontalStrut_3, BorderLayout.WEST);
		
		// Display Results
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		frm.getContentPane().add(scrollPane, BorderLayout.CENTER);
		
		// Make Table
		resultDisplay = new JTable() {
			
			{
				// Disable Reordering
				getTableHeader().setReorderingAllowed(false);
				// Disable Resizing
				getTableHeader().setResizingAllowed(false);
				// Single Selection Only
				getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
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
		
		// Setup Data for Table
		tableRebuild();
		
		// See Line 192 for List Listener
		
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
		
		JButton newBtn = new JButton("Add");
		newBtn.addActionListener(e -> 
			new Add().setVisible(true)
		);
		btnPanel.add(newBtn);
		
		// Edit Button -> Default Unselected
		JButton editBtn = new JButton("Edit");
		editBtn.addActionListener(e -> 
			new Edit((Device) resultDisplay.getModel()
										   .getValueAt(resultDisplay.getSelectedRow(), 0))
										   .setVisible(true)
		);
		editBtn.setEnabled(false);
		btnPanel.add(editBtn);
		
		JButton ixBtn = new JButton("Import/Export");
		ixBtn.addActionListener(e -> 
			new IEport().setVisible(true)
		);
		btnPanel.add(ixBtn);
		
		// Add List Selection Listener
		resultDisplay.getSelectionModel().addListSelectionListener(e -> {
			// Enable or Disable Edit Button
			if(resultDisplay.getSelectedRow() != -1) 
				editBtn.setEnabled(true);
			else
				editBtn.setEnabled(false);
		});
		
	}
	
	// Headers for the Tables
	private final String[] HEADERS = new String[] {
		"Hostname", "User", "Model", "OS", "Status"
	};
		
	/**
	 * Rebuilds the table with Main Database
	 */
	public void tableRebuild() {
		tableRebuild(Main.devices);
	}
	
	/**
	 * Rebuilds the table
	 * 
	 * @param devices
	 * The Devices to Display
	 */
	public void tableRebuild(ArrayList<Device> devices) {
		// Setup Data for Table
		Object[][] deviceData = new Object[devices.size()][5];
		
		for(int i = 0; i < devices.size(); i++) {
			Device d = devices.get(i);
			deviceData[i] = new Object[] {
				d, d.getUser(), d.getModel(), d.getOS(), d.getStatus()
			};
		}
		
		// Push for Update
		EventQueue.invokeLater(() -> 
			// Update Table
			resultDisplay.setModel(new DefaultTableModel(deviceData, HEADERS))
		);
	}
	
	/**
	 * Returns the Frame
	 * 
	 * @return
	 * The Frame
	 */
	public JFrame getFrame() {
		return frm;
	}

}
