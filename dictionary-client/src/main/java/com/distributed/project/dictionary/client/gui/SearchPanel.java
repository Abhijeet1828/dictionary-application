package com.distributed.project.dictionary.client.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;

import org.apache.commons.lang3.StringUtils;
import org.kordamp.ikonli.dashicons.Dashicons;
import org.kordamp.ikonli.swing.FontIcon;

import com.distributed.project.dictionary.client.DictionaryClient;
import com.distributed.project.dictionary.client.dto.DictionaryDto;
import com.distributed.project.dictionary.client.utils.Constants;

/**
 * This class is used to create search panel and add relevant components. This
 * class extends {@link JPanel} for ease of use.
 * 
 * @author Abhijeet - 1278218
 *
 */
public class SearchPanel extends JPanel implements ActionListener {

	private static final long serialVersionUID = -3984458338131472464L;

	protected DictionaryClient dictionaryClient;
	protected JTextField searchField;
	protected JLabel responseLabel;
	protected JTextArea searchResponseField;
	protected JLabel executionTimeLabel;

	/**
	 * This constructor is used to initialize the dictionary client.
	 * 
	 * @param dictionaryClient
	 */
	public SearchPanel(DictionaryClient dictionaryClient) {
		this.dictionaryClient = dictionaryClient;
		initialize();
	}

	/**
	 * This method is used to intialize the search panel and add relevant
	 * components.
	 */
	private void initialize() {
		// Generating search tab panel with properties
		setForeground(Color.BLACK);
		setBorder(BorderFactory.createLoweredSoftBevelBorder());
		setLayout(null);

		// Creating search label with properties
		JLabel searchLabel = new JLabel("Enter the word you want to search for");
		searchLabel.setFont(new Font(Constants.FONT_LUCIDA_GRANDE, Font.PLAIN, 14));
		searchLabel.setHorizontalAlignment(SwingConstants.CENTER);
		searchLabel.setBounds(158, 6, 263, 28);

		// Adding search label to search panel
		add(searchLabel);

		// Creating search text field with properties
		searchField = new JTextField(10);
		searchField.setBounds(194, 46, 174, 26);

		// Adding search text field to search panel
		add(searchField);

		// Creating search button with text, icon & properties
		JButton searchButton = new JButton("SEARCH", FontIcon.of(Dashicons.SEARCH, 20));
		searchButton.setFont(new Font(Constants.FONT_LUCIDA_GRANDE, Font.PLAIN, 13));
		searchButton.setBounds(219, 84, 117, 29);
		searchButton.addActionListener(this);

		// Adding search button to search panel
		add(searchButton);

		// Creating response label with properties
		responseLabel = new JLabel("Word searched successfully");
		responseLabel.setForeground(new Color(255, 0, 0));
		responseLabel.setHorizontalAlignment(SwingConstants.CENTER);
		responseLabel.setBounds(6, 125, 565, 16);
		responseLabel.setVisible(false);

		// Adding response label to search panel
		add(responseLabel);

		// Creating search response area with properties
		searchResponseField = new JTextArea();
		searchResponseField.setLineWrap(true);
		searchResponseField.setWrapStyleWord(true);
		searchResponseField.setEditable(false);

		// Creating search scroll pane and enclosing search response field in it
		JScrollPane searchScrollPane = new JScrollPane(searchResponseField,
				ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		searchScrollPane.setBounds(115, 157, 354, 71);
		searchScrollPane.setVisible(true);

		// Adding search scroll pane to search panel
		add(searchScrollPane);

		// Creating execution time label to search panel
		executionTimeLabel = new JLabel("Time Taken: 300 ms");
		executionTimeLabel.setFont(new Font(Constants.FONT_LUCIDA_GRANDE, Font.BOLD | Font.ITALIC, 12));
		executionTimeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		executionTimeLabel.setBounds(342, 252, 229, 16);
		executionTimeLabel.setVisible(false);

		// Adding execution time label to search panel
		add(executionTimeLabel);
	}

	/**
	 * This method is called whenever user clicks on the search button in the search
	 * panel. It sends requests to server and adds response and execution label
	 * depending upon the response.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		// Sending and receiving the response from server
		DictionaryDto response = dictionaryClient.handleRequestAndResponse(searchField.getText(), null, null, null,
				Constants.SEARCH);

		// Setting response field, response and execution label empty on start
		searchResponseField.setText(StringUtils.EMPTY);
		responseLabel.setText(StringUtils.EMPTY);
		executionTimeLabel.setText(StringUtils.EMPTY);

		// If searched word is present then display meaning
		if (response.isPresent()) {
			StringBuilder builder = new StringBuilder("MEANING: " + response.getMeaning());
			if (StringUtils.isNotBlank(response.getSecondaryMeaning())) {
				builder.append(StringUtils.SPACE + StringUtils.LF + StringUtils.CR);
				builder.append(StringUtils.LF + StringUtils.CR);
				builder.append("ALTERNATE MEANING: " + response.getSecondaryMeaning());
			}
			searchResponseField.setText(builder.toString());
		}

		// Setting the response label and marking it active.
		responseLabel.setText(response.getMessage());
		responseLabel.setVisible(true);

		// Setting the execution time label and marking it active
		executionTimeLabel.setText("Time Taken: " + response.getTimeTaken() + StringUtils.SPACE + "ms");
		executionTimeLabel.setVisible(true);
	}

	/**
	 * This method is called whenever user clicks on the search tab to clear the
	 * fields from previous search request.
	 */
	public void stateChange() {
		// Clearing the response label and removing from view
		responseLabel.setText(StringUtils.EMPTY);
		responseLabel.setVisible(false);

		// Clearing the execution time label and removing from view
		executionTimeLabel.setText(StringUtils.EMPTY);
		executionTimeLabel.setVisible(false);

		// Clearing the search field
		searchField.setText(StringUtils.EMPTY);

		// Clearing the search response field.
		searchResponseField.setText(StringUtils.EMPTY);
	}

}
