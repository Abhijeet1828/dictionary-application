package com.distributed.project.dictionary.client.gui;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.jfree.chart.ChartPanel;

import com.distributed.project.dictionary.client.DictionaryClient;
import com.distributed.project.dictionary.client.utils.TabPaneEnum;

/**
 * This class is used to create {@link JTabbedPane} which is used to change
 * between different dictionary options.
 * 
 * @author Abhijeet - 1278218
 *
 */
public class OptionTabPane extends JTabbedPane implements ChangeListener {

	private static final long serialVersionUID = 8525682203688343146L;

	protected DictionaryClient dictionaryClient;
	protected SearchPanel searchPanel;
	protected AddPanel addPanel;
	protected UpdatePanel updatePanel;
	protected DeletePanel deletePanel;

	protected ChartPanel chartPanel;
	protected GraphPanel graphPanel;

	/**
	 * This constructor is used to initialize the dictionary client
	 * 
	 * @param dictionaryClient
	 */
	public OptionTabPane(DictionaryClient dictionaryClient) {
		this.dictionaryClient = dictionaryClient;
		initialize();
	}

	/**
	 * This method is used to initialize the option tab pane and add the necessary
	 * components.
	 */
	private void initialize() {
		// Initializing option tab pane with properties
		setTabPlacement(SwingConstants.TOP);
		setBackground(Color.WHITE);
		setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		setBounds(0, 87, 600, 322);

		// Adding search panel to option tab pane
		searchPanel = new SearchPanel(dictionaryClient);
		addPane(searchPanel, TabPaneEnum.SEARCH.getPaneName(), TabPaneEnum.SEARCH.getPaneIcon(),
				TabPaneEnum.SEARCH.getPaneIndex());

		// Adding add panel to option tab panel
		addPanel = new AddPanel(dictionaryClient);
		addPane(addPanel, TabPaneEnum.ADD.getPaneName(), TabPaneEnum.ADD.getPaneIcon(), TabPaneEnum.ADD.getPaneIndex());

		// Adding update panel to option tab panel
		updatePanel = new UpdatePanel(dictionaryClient);
		addPane(updatePanel, TabPaneEnum.UPDATE.getPaneName(), TabPaneEnum.UPDATE.getPaneIcon(),
				TabPaneEnum.UPDATE.getPaneIndex());

		// Adding delete panel to option tab panel
		deletePanel = new DeletePanel(dictionaryClient);
		addPane(deletePanel, TabPaneEnum.DELETE.getPaneName(), TabPaneEnum.DELETE.getPaneIcon(),
				TabPaneEnum.DELETE.getPaneIndex());

		// Creating chart panel with properties
		graphPanel = new GraphPanel(dictionaryClient);
		chartPanel = new ChartPanel(graphPanel.createChart());
		chartPanel.setBackground(Color.WHITE);
		chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
		chartPanel.setForeground(Color.BLACK);
		chartPanel.setLayout(null);

		// Adding chart panel to option tab panel
		addPane(chartPanel, TabPaneEnum.GRAPH.getPaneName(), TabPaneEnum.GRAPH.getPaneIcon(),
				TabPaneEnum.GRAPH.getPaneIndex());

		// Adding change listener to track change in tab panels
		addChangeListener(this);
	}

	/**
	 * This method is used to add panes to the {@link JTabbedPane}. It adds the tab
	 * with the given parameters and sets backgrouns and foreground colors.
	 * 
	 * @param panel
	 * @param paneName
	 * @param icon
	 * @param index
	 */
	private void addPane(JPanel panel, String paneName, Icon icon, int index) {
		// Adding the tab to the option tab pane
		addTab(paneName, icon, panel, null);

		// Adding the foreground colour of pane
		setForegroundAt(index, Color.BLACK);

		// Adding the background colour of pane
		setBackgroundAt(index, Color.GRAY);
	}

	/**
	 * This method is called when user clicks on the different tab.
	 */
	@Override
	public void stateChanged(ChangeEvent e) {
		// Checking if the state change happened in option tab pane
		if (e.getSource() instanceof JTabbedPane) {
			JTabbedPane pane = (JTabbedPane) e.getSource();

			// Getting the index of the pane selected
			int selectedIndex = pane.getSelectedIndex();

			// Calling the respective state change methods of operations
			if (TabPaneEnum.SEARCH.getPaneIndex() == selectedIndex) {
				searchPanel.stateChange();
			} else if (TabPaneEnum.ADD.getPaneIndex() == selectedIndex) {
				addPanel.stateChange();
			} else if (TabPaneEnum.UPDATE.getPaneIndex() == selectedIndex) {
				updatePanel.stateChange();
			} else if (TabPaneEnum.DELETE.getPaneIndex() == selectedIndex) {
				deletePanel.stateChange();
			} else if (TabPaneEnum.GRAPH.getPaneIndex() == selectedIndex) {
				chartPanel.setChart(graphPanel.stateChange());
			}
		}
	}

}
