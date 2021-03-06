package com.andrew.inv.manage.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.SystemColor;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.IOException;
import java.util.EventObject;
import java.util.List;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import com.andrew.inv.manage.C;
import com.andrew.inv.manage.Main;
import com.andrew.inv.manage.db.Device;
import com.andrew.inv.manage.db.Device.Status;
import com.andrew.inv.manage.db.FileChooser;
import com.andrew.inv.manage.db.Search;
import com.andrew.inv.manage.db.Sort;

public class FrontPage {

	private JFrame frm;
	private JTextField searchBar;
	private JTable resultDisplay;
	
	private AdvSearch advSearch;
	
	// Main Thread
	private Main main;
	
	private List<Device> currentData;

	/**
	 * Create the application.
	 */
	public FrontPage(Main m) {
		// Setup
		main = m;
		currentData = m.getDevices();
		// Start Correct Processes
		initialize();
		// Start ADVSearch in background
		advSearch = new AdvSearch(m);
		frm.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		// Create Frame
		frm = new JFrame();
		frm.setTitle(
			((main.isSafe())? "PC Inventory" : "PC Inventory [UNSAFE]") + 
			((main.isMain())? "" : " [" + main.getMainFile().getName() + "]")
		);
		frm.setIconImages(C.ICONS);
		frm.setSize(600, 450);
		frm.setMinimumSize(new Dimension(450, 300));
		frm.setLocationRelativeTo(null);
		frm.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frm.getContentPane().setLayout(new BorderLayout(0, 0));
		
		// The Search Panel
		JPanel searchPanel = new JPanel();
		frm.getContentPane().add(searchPanel, BorderLayout.NORTH);
		searchPanel.setLayout(new BorderLayout(0, 0));
		
		Component verticalStrut = Box.createVerticalStrut(C.SPACING);
		searchPanel.add(verticalStrut, BorderLayout.NORTH);
		
		Component verticalStrut_1 = Box.createVerticalStrut(C.SPACING);
		searchPanel.add(verticalStrut_1, BorderLayout.SOUTH);
		
		Component horizontalStrut = Box.createHorizontalStrut(C.SPACING);
		searchPanel.add(horizontalStrut, BorderLayout.WEST);
		
		Component horizontalStrut_1 = Box.createHorizontalStrut(C.SPACING);
		searchPanel.add(horizontalStrut_1, BorderLayout.EAST);
		
		JPanel searchSpace = new JPanel();
		searchPanel.add(searchSpace, BorderLayout.CENTER);
		searchSpace.setLayout(new BorderLayout(0, 0));
		
		searchBar = new JTextField();
		searchBar.setToolTipText("Press Enter to Search for a Host Name");
		searchSpace.add(searchBar, BorderLayout.CENTER);
		searchBar.setColumns(10);
		
		JButton advSearchBtn = new JButton("Advance Search");
		advSearchBtn.addActionListener(e -> 
			advSearch.setVisible(true, searchBar.getText().trim())
		);
		searchSpace.add(advSearchBtn, BorderLayout.EAST);
		
		// Setup Enter Detection
		searchBar.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				// Search on Enter Key
				if(e.getKeyChar() == '\n') {
					// Reset The Results to Main
					if(searchBar.getText().trim().equals("")) 
						tableRebuild();
					// Search
					else
						Search.searchFor(main, searchBar.getText().trim());
				}
			}

			@Override
			public void keyPressed(KeyEvent e) {}

			@Override
			public void keyReleased(KeyEvent e) {}
			
		});
		
		Component horizontalStrut_2 = Box.createHorizontalStrut(C.SPACING * 2);
		frm.getContentPane().add(horizontalStrut_2, BorderLayout.EAST);
		
		Component horizontalStrut_3 = Box.createHorizontalStrut(C.SPACING * 2);
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
		
		// Add Mouse Listener
		resultDisplay.addMouseListener(new MouseAdapter() {
			// On Mouse Click Event (Double Click)
			@Override
			public void mouseClicked(MouseEvent e) {
				// Double Click
				if(e.getClickCount() == 2) {
					int col = resultDisplay.getSelectedColumn(),
						row = resultDisplay.getSelectedRow();
					// Get Device
					Device d = (Device) resultDisplay.getModel().getValueAt(row, 0);
					
					switch(col) {
						// PC Information Column
						case 0:
							JOptionPane.showMessageDialog(
								frm, 
								"Host Name: " + d.getHost() + 
								"\nSerial #: " + d.getSerial(), 
								d.getHost().toUpperCase() + "'s PC Information", 
								JOptionPane.PLAIN_MESSAGE
							);
							break;
						// OS Column
						case 3: 
							JOptionPane.showMessageDialog(
								frm, 
								"OS: " + d.getOS() + 
								"\nDate Updated #: " + d.getDateUpdated(), 
								d.getHost().toUpperCase() + "'s OS Information", 
								JOptionPane.PLAIN_MESSAGE
							);
							break;
						// Status Column
						case 4: {
							if(d.getStatus() != Status.IST)
								JOptionPane.showMessageDialog(
									frm, 
									"Status: " + d.getStatus() + 
									"\nCurrent Location #: " + d.getLoc(), 
									d.getHost().toUpperCase() + "'s Current Status", 
									JOptionPane.PLAIN_MESSAGE
								);
							else
								new ISTStatus(main, (Device) resultDisplay.getModel().getValueAt(row, 0)).setVisible(true);
							break;
						}
					}
				}
			}
		});
		
		// Add Sorting Function to Table Header
		resultDisplay.getTableHeader().addMouseListener(new MouseAdapter() {
			// Store Sort Mode 
			private boolean[] ascendingMode = {true, true, true, true, true};
			// On Mouse Click Event
			@Override
			public void mouseClicked(MouseEvent e) {
				// Get Column
				int header = resultDisplay.columnAtPoint(e.getPoint());
				// Do Sort
				currentData.sort(new Sort(header, ascendingMode[header]));
				// Switch Modes
				ascendingMode[header] = !ascendingMode[header];
				// Rebuild
				tableRebuild(currentData);
			}
		});
		
		scrollPane.setViewportView(resultDisplay);
		
		// Control Panel with Button
		JPanel ctrlPanel = new JPanel();
		frm.getContentPane().add(ctrlPanel, BorderLayout.SOUTH);
		ctrlPanel.setLayout(new BorderLayout(0, 0));
		
		Component verticalStrut_2 = Box.createVerticalStrut(C.SPACING);
		ctrlPanel.add(verticalStrut_2, BorderLayout.NORTH);
		
		Component verticalStrut_3 = Box.createVerticalStrut(C.SPACING);
		ctrlPanel.add(verticalStrut_3, BorderLayout.SOUTH);
		
		Component horizontalStrut_4 = Box.createHorizontalStrut(C.SPACING);
		ctrlPanel.add(horizontalStrut_4, BorderLayout.WEST);
		
		Component horizontalStrut_5 = Box.createHorizontalStrut(C.SPACING);
		ctrlPanel.add(horizontalStrut_5, BorderLayout.EAST);
		
		JPanel btnPanel = new JPanel();
		ctrlPanel.add(btnPanel, BorderLayout.CENTER);
		btnPanel.setLayout(new GridLayout(1, 0, C.SPACING * 2, 0));
		
		JButton newBtn = new JButton("Add");
		newBtn.addActionListener(e -> 
			new Add(main).setVisible(true)
		);
		btnPanel.add(newBtn);
		
		// Edit Button -> Default Unselected
		JButton editBtn = new JButton("Edit");
		editBtn.addActionListener(e -> 
			new Edit(main, (Device) resultDisplay.getModel()
										   .getValueAt(resultDisplay.getSelectedRow(), 0))
										   .setVisible(true)
		);
		editBtn.setEnabled(false);
		btnPanel.add(editBtn);
		
		JButton ixBtn = new JButton("Import/Export");
		ixBtn.addActionListener(e -> 
			new IEport(main).setVisible(true)
		);
		btnPanel.add(ixBtn);
		
		// Menu Bar
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBackground(SystemColor.menu);
		frm.setJMenuBar(menuBar);
		
		// Menus
		
		JMenu fileMenu = new JMenu("File");
		menuBar.add(fileMenu);
		
		// Make new database
		JMenuItem newMenuItem = new JMenuItem("New Database");
		newMenuItem.setIcon(new ImageIcon(FrontPage.class.getResource("/menu/file-new.png")));
		newMenuItem.addActionListener(e -> {
			// Select File
			File newFile = FileChooser.showFC(frm, FileChooser.SAVE, FileChooser.PCDB);
			// Not Cancelled
			if(newFile != null) {
				// Create
				try {
					newFile.createNewFile();
				} catch (IOException e1) {}
				// Open
				Main.openFile(newFile);
			}
		});
		fileMenu.add(newMenuItem);
		
		// Open a database
		JMenuItem openMenuItem = new JMenuItem("Open Database");
		openMenuItem.setIcon(new ImageIcon(FrontPage.class.getResource("/menu/file-open.png")));
		openMenuItem.addActionListener(e -> {
			// Select File
			File openFile = FileChooser.showFC(frm, FileChooser.OPEN, FileChooser.PCDB);
			// Not Cancelled
			if(openFile != null) 
				// Open
				Main.openFile(openFile);
		});
		fileMenu.add(openMenuItem);
		
		JMenu editMenu = new JMenu("Edit");
		menuBar.add(editMenu);
		
		// Rename a database
		JMenuItem renameMenuItem = new JMenuItem("Rename Database");
		renameMenuItem.setIcon(new ImageIcon(FrontPage.class.getResource("/menu/edit-rename.png")));
		// Do not allow renaming if default database is being used at the moment
		if(main.isMain()) {
			renameMenuItem.setEnabled(false);
			renameMenuItem.setToolTipText("Cannot rename default database");
		}else
			renameMenuItem.addActionListener(e -> {
				String newName = JOptionPane.showInputDialog(
					frm, 
					// Remove .pcdb
					"Rename " + main.getMainFile().getName().substring(0, main.getMainFile().getName().length() - 5) + " to:", 
					"Rename To", 
					JOptionPane.PLAIN_MESSAGE
				);
				// Not Cancelled or Empty
				if(newName != null && newName.length() > 0) {
					// Determine Path
					main.renameFile(newName);
					// Update File
					EventQueue.invokeLater(() -> 
						frm.setTitle(
							((main.isSafe())? "PC Inventory" : "PC Inventory [UNSAFE]") + 
							((main.isMain())? "" : " [" + main.getMainFile().getName() + "]")
						)
					);
				}
			});
		editMenu.add(renameMenuItem);
		
		JMenu toolsMenu = new JMenu("Tools");
		menuBar.add(toolsMenu);
		
		JMenuItem lockMenuItem = new JMenuItem("Lock Database");
		lockMenuItem.addActionListener(e -> 
			new Password(main).setVisible(true)
		);
		lockMenuItem.setIcon(new ImageIcon(FrontPage.class.getResource("/menu/tools-lock.png")));
		toolsMenu.add(lockMenuItem);
		
		JMenuItem settingsMenuItem = new JMenuItem("Settings");
		settingsMenuItem.setIcon(new ImageIcon(FrontPage.class.getResource("/menu/tools-settings.png")));
		toolsMenu.add(settingsMenuItem);
		
		// Add List Selection Listener
		resultDisplay.getSelectionModel().addListSelectionListener(e -> {
			// Enable or Disable Edit Button
			if(resultDisplay.getSelectedRow() != -1) 
				editBtn.setEnabled(true);
			else
				editBtn.setEnabled(false);
		});
		
		// Closing Operations
		frm.addWindowListener(new WindowListener() {

			@Override
			public void windowOpened(WindowEvent e) {}

			@Override
			public void windowClosing(WindowEvent e) {}

			@Override
			public void windowClosed(WindowEvent e) {
				// Close the ADVSearch in the background
				advSearch.dispose();
			}

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
	
	// Headers for the Tables
	private final String[] HEADERS = new String[] {
		"Hostname", "User", "Model", "OS", "Status"
	};
		
	/**
	 * Rebuilds the table with Main Database
	 */
	public void tableRebuild() {
		tableRebuild(main.getDevices());
	}
	
	/**
	 * Rebuilds the table
	 * 
	 * @param devices
	 * The Devices to Display
	 */
	public void tableRebuild(List<Device> devices) {
		// Update Current Results
		currentData = devices;
		// Setup Data for Table
		// TODO Fixed set storage
		Object[][] deviceData = new Object[devices.size()][5];
		
		for(int i = 0; i < devices.size(); i++) {
			Device d = devices.get(i);
			// Catches null Devices
			if(d == null)
				continue;
			// Build
			deviceData[i] = new Object[] {
				d, 
				d.getUser(), 
				d.getModel(), 
				d.getOS(), 
				d.getStatus()
			};
		}
		
		// Push for Update
		EventQueue.invokeLater(() -> {
			// Update Table
			resultDisplay.setModel(new DefaultTableModel(deviceData, HEADERS));
			// Colour
			resultDisplay.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {

				/**
				 * Serial
				 */
				private static final long serialVersionUID = -97356007888202796L;
				
				@Override
				public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
					// Get cell from super class
					Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
					// Colour Modification (Not Selected)
					if(!isSelected)
						cell.setBackground(((Device.Status) deviceData[row][4]).getColour());
					// Return
					return cell;
				}
				
			});
		});
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

	/**
	 * Returns the Frame
	 * 
	 * @return
	 * The Frame
	 */
	public AdvSearch getAdvSearch() {
		return advSearch;
	}
}
