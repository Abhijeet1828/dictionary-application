package com.distributed.project.dictionary.server.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.net.Socket;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import org.apache.commons.collections4.MapUtils;

import com.distributed.project.dictionary.server.DictionaryServer;

/**
 * This class is used to show table of clients connected. It extends
 * {@link JPanel} for ease of use.
 * 
 * @author Abhijeet - 1278218
 *
 */
public class ClientTable extends JPanel {

	private static final long serialVersionUID = -1572243626622147265L;

	protected DictionaryServer dictionaryServer;
	protected JTable table;
	protected DefaultTableModel defaultTableModel;

	/**
	 * This constructor intializes the dictionary server.
	 * 
	 * @param dictionaryServer
	 */
	public ClientTable(DictionaryServer dictionaryServer) {
		this.dictionaryServer = dictionaryServer;
		initialize();
	}

	/**
	 * This method initialized the client table panel and add {@link JTable} to the
	 * panel.
	 * 
	 */
	private void initialize() {
		// Initializing the client table panel with properties
		setForeground(Color.BLACK);
		setBorder(BorderFactory.createLoweredSoftBevelBorder());
		setLayout(null);

		// Initializing the default table model with data and column names
		defaultTableModel = new DefaultTableModel(clientSocketData(), getColumnNames());

		// Creating jtable with default table model
		table = new JTable(defaultTableModel);

		// Initializing table with properties
		table.setBorder(new LineBorder(new Color(0, 0, 0), 1, false));
		table.setBackground(new Color(220, 220, 220));
		table.setShowGrid(true);
		table.setShowVerticalLines(true);
		table.setShowHorizontalLines(true);
		table.setGridColor(Color.BLACK);
		table.setRowHeight(30);

		// Adding cell renderer for aligning the text to center
		DefaultTableCellRenderer dataCellRenderer = new DefaultTableCellRenderer();
		dataCellRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		for (int columnIndex = 0; columnIndex < defaultTableModel.getColumnCount(); columnIndex++) {
			table.getColumnModel().getColumn(columnIndex).setCellRenderer(dataCellRenderer);
		}

		// Adding properties to the table header
		JTableHeader jTableHeader = table.getTableHeader();
		jTableHeader.setBorder(new LineBorder(new Color(0, 0, 0), 1, false));
		jTableHeader.setBackground(new Color(211, 211, 211));

		// Adding cell renderer for aligning the header text to center
		DefaultTableCellRenderer headerCellRenderer = (DefaultTableCellRenderer) jTableHeader.getDefaultRenderer();
		headerCellRenderer.setHorizontalAlignment(SwingConstants.CENTER);

		// Adding the client table to scroll pane for column headers to be visible
		JScrollPane sp = new JScrollPane(table);
		sp.setBounds(0, 0, 577, 274);
		sp.setVisible(true);

		// Setting the dimensions of the table headers
		jTableHeader.setPreferredSize(new Dimension(sp.getWidth(), 30));

		// Adding scroll pane with client table to the table panel.
		add(sp);
	}

	/**
	 * This method is used to refresh the data in the table for active clients
	 * whenever the user comes on the client table panel.
	 * 
	 */
	public void stateChange() {
		// Getting an instance of default table model
		DefaultTableModel tableModel = (DefaultTableModel) table.getModel();

		// Removing all the old content from the table.
		tableModel.setRowCount(0);

		// Populating new values to the table
		tableModel.setDataVector(clientSocketData(), getColumnNames());

		// Aligning the text to center
		DefaultTableCellRenderer dataCellRenderer = new DefaultTableCellRenderer();
		dataCellRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		for (int columnIndex = 0; columnIndex < tableModel.getColumnCount(); columnIndex++) {
			table.getColumnModel().getColumn(columnIndex).setCellRenderer(dataCellRenderer);
		}

		// Firing a table changed event for the table to refresh.
		tableModel.fireTableDataChanged();
	}

	/**
	 * This method is used to fetch the active clients and return a 2d string array
	 * with the client data.
	 * 
	 * @return
	 */
	private String[][] clientSocketData() {
		// Fetching the active client data
		Map<Integer, Socket> clientSocketMap = dictionaryServer.getClientSocketMap();

		// Returning empty 2d string array if no clients are connected
		if (MapUtils.isEmpty(clientSocketMap)) {
			return new String[0][0];
		}

		// Initializing the 2d string array with the size of the client map
		String[][] data = new String[clientSocketMap.size()][3];

		// counter for changing rows
		int count = 0;

		// iterating over clientSocketMap and filling the 2d string array with
		// clientNumber, Hostname & port.
		for (Map.Entry<Integer, Socket> entry : clientSocketMap.entrySet()) {
			data[count][0] = entry.getKey().toString();
			data[count][1] = entry.getValue().getInetAddress().getHostName();
			data[count][2] = String.valueOf(entry.getValue().getLocalPort());
			count++;
		}

		// Returning the 2d array
		return data;
	}

	/**
	 * This method is used to return the hardcoded column header names.
	 * 
	 * @return
	 */
	private String[] getColumnNames() {
		return new String[] { "CLIENT NUMBER", "HOSTNAME", "PORT" };
	}

}
