package com.distributed.project.dictionary.client.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import org.apache.commons.lang3.StringUtils;
import org.kordamp.ikonli.dashicons.Dashicons;
import org.kordamp.ikonli.swing.FontIcon;

import com.distributed.project.dictionary.client.DictionaryClient;
import com.distributed.project.dictionary.client.dto.DictionaryDto;
import com.distributed.project.dictionary.client.utils.Constants;

/**
 * This class is used to display the delete panel and the necessary UI
 * components. This class extends {@link JPanel} for ease of use.
 * 
 * @author Abhijeet - 1278218
 *
 */
public class DeletePanel extends JPanel implements ActionListener {

	private static final long serialVersionUID = 6116702033583931L;

	protected DictionaryClient dictionaryClient;
	protected JTextField wordTextField;
	protected JLabel responseLabel;
	protected JLabel executionTimeLabel;

	/**
	 * This constructor initialized the dictionary client.
	 * 
	 * @param dictionaryClient
	 */
	public DeletePanel(DictionaryClient dictionaryClient) {
		this.dictionaryClient = dictionaryClient;
		initialize();
	}

	/**
	 * This method initializes the delete panel and all the necessary components.
	 */
	private void initialize() {
		// Generating delete panel with properties
		setForeground(Color.BLACK);
		setBorder(BorderFactory.createLoweredSoftBevelBorder());
		setLayout(null);

		// Creating word label with properties
		JLabel wordLabel = new JLabel("Enter the word you want to delete");
		wordLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		wordLabel.setHorizontalAlignment(SwingConstants.CENTER);
		wordLabel.setBounds(158, 16, 255, 16);

		// Adding word label to delete panel
		add(wordLabel);

		// Creating word text field with properties
		wordTextField = new JTextField(10);
		wordTextField.setBounds(204, 44, 162, 26);

		// Adding word text field to delete panel
		add(wordTextField);

		// Creating delete button with properties
		JButton deleteButton = new JButton("DELETE", FontIcon.of(Dashicons.TRASH, 20));
		deleteButton.setFont(new Font(Constants.FONT_LUCIDA_GRANDE, Font.PLAIN, 13));
		deleteButton.setBounds(229, 82, 117, 29);
		deleteButton.addActionListener(this);

		// Adding delete button to delete panel
		add(deleteButton);

		// Creating response label with properties
		responseLabel = new JLabel("Word deleted successfully");
		responseLabel.setHorizontalAlignment(SwingConstants.CENTER);
		responseLabel.setForeground(new Color(255, 0, 0));
		responseLabel.setBounds(6, 123, 565, 16);
		responseLabel.setVisible(false);

		// Adding response label to delete panel
		add(responseLabel);

		// Creating execution time label to update panel
		executionTimeLabel = new JLabel("Time Taken: 300 ms");
		executionTimeLabel.setFont(new Font(Constants.FONT_LUCIDA_GRANDE, Font.BOLD | Font.ITALIC, 12));
		executionTimeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		executionTimeLabel.setBounds(342, 252, 229, 16);
		executionTimeLabel.setVisible(false);

		// Adding execution time label to update panel
		add(executionTimeLabel);
	}

	/**
	 * This method is called when a user clicks on the delete button for furthur
	 * actions of sending the request to the server and then display the response.
	 * 
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		// Marking the response and execution time label empty
		responseLabel.setText(StringUtils.EMPTY);
		executionTimeLabel.setText(StringUtils.EMPTY);

		// Sending request to server and recieving the response.
		DictionaryDto response = dictionaryClient.handleRequestAndResponse(null, wordTextField.getText(), null, null,
				Constants.DELETE);

		// Setting the response label from server response and marking it visible
		responseLabel.setText(response.getMessage());
		responseLabel.setVisible(true);

		// Setting the execution time label and marking it visible
		executionTimeLabel.setText("Time Taken: " + response.getTimeTaken() + StringUtils.SPACE + "ms");
		executionTimeLabel.setVisible(true);
	}

	/**
	 * This methos is used to refresh the delete panel when user navigates to the
	 * delete panel.
	 */
	public void stateChange() {
		// Clearing the response lable and marking it not visible
		responseLabel.setText(StringUtils.EMPTY);
		responseLabel.setVisible(false);

		// Clearing the execution time label and marking it not visible
		executionTimeLabel.setText(StringUtils.EMPTY);
		executionTimeLabel.setVisible(false);

		// Clearing the word field text entered previously.
		wordTextField.setText(StringUtils.EMPTY);
	}

}
