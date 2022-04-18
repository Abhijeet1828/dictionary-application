package com.distributed.project.dictionary.server.gui;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.jfree.chart.ChartPanel;
import org.kordamp.ikonli.dashicons.Dashicons;
import org.kordamp.ikonli.swing.FontIcon;

import com.distributed.project.dictionary.server.DictionaryServer;

/**
 * This class is used to create an option tab pane for selecting between client
 * table and response time graph. This class extends {@link JTabbedPane} for
 * ease of use.
 * 
 * @author Abhijeet - 1278218
 *
 */
public class OptionTabPane extends JTabbedPane implements ChangeListener {

	private static final long serialVersionUID = -7329291571143004585L;

	protected DictionaryServer dictionaryServer;
	protected ClientTable clientTable;

	protected ChartPanel chartPanel;
	protected GraphPanel graphPanel;

	/**
	 * This constructor is used to initialize the dicitonary server.
	 * 
	 * @param dictionaryServer
	 */
	public OptionTabPane(DictionaryServer dictionaryServer) {
		this.dictionaryServer = dictionaryServer;
		initialize();
	}

	/**
	 * This method is used to intialize the option tab pane and add necessary
	 * components.
	 */
	private void initialize() {
		// Initializing the option tab pane with properties
		setBackground(Color.WHITE);
		setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		setBounds(0, 87, 600, 322);

		// Creating instance of client table
		clientTable = new ClientTable(dictionaryServer);

		// Adding client table to option tab pane
		addPane(clientTable, "CLIENTS", FontIcon.of(Dashicons.GROUPS, 20), 0);

		// Initializing the graph and chart panel
		graphPanel = new GraphPanel(dictionaryServer);
		chartPanel = new ChartPanel(graphPanel.createChart());

		// Adding properties to the chart panel
		chartPanel.setBackground(Color.WHITE);
		chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
		chartPanel.setForeground(Color.BLACK);
		chartPanel.setLayout(null);

		// Adding the chart panel to the option tab pane
		addPane(chartPanel, "GRAPH", FontIcon.of(Dashicons.CHART_BAR, 20), 1);

		// Adding a change listener to track tab change events
		addChangeListener(this);
	}

	/**
	 * This method is used to add a panel to the {@link JTabbedPane} with component,
	 * name of the component, icon of the component and the index at which has to
	 * sit.
	 * 
	 * @param panel
	 * @param paneName
	 * @param icon
	 * @param index
	 */
	private void addPane(JPanel panel, String paneName, Icon icon, int index) {
		addTab(paneName, icon, panel, null);
		setForegroundAt(index, Color.BLACK);
		setBackgroundAt(index, Color.GRAY);
	}

	/**
	 * This method is called when a tab change event is occured and accordingly
	 * calls the stateChange() method for respective tabs.
	 */
	@Override
	public void stateChanged(ChangeEvent e) {
		if (e.getSource() instanceof JTabbedPane) {
			JTabbedPane pane = (JTabbedPane) e.getSource();
			int selectedIndex = pane.getSelectedIndex();

			if (0 == selectedIndex) {
				clientTable.stateChange();
			} else if (1 == selectedIndex) {
				chartPanel.setChart(graphPanel.stateChange());
			}
		}
	}

}
