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
 * This class is used for shwowing the add operation UI to the user by adding
 * neecessary component. This class extends {@link JPanel} for ease of use.
 * 
 * @author Abhijeet - 1278218
 *
 */
public class AddPanel extends JPanel implements ActionListener {

	private static final long serialVersionUID = 3912221170106789644L;

	protected DictionaryClient dictionaryClient;
	protected JTextField wordTextField;
	protected JTextField meaningTextField;
	protected JTextField alternateMeaningTextField;
	protected JLabel responseLabel;
	protected JLabel executionTimeLabel;

	/**
	 * This constructor initialized the dictionary client.
	 * 
	 * @param dictionaryClient
	 */
	public AddPanel(DictionaryClient dictionaryClient) {
		this.dictionaryClient = dictionaryClient;
		initialize();
	}

	/**
	 * This method initializes the add panel with components.
	 * 
	 */
	private void initialize() {
		// Generating add panel with properties
		setForeground(Color.BLACK);
		setBorder(BorderFactory.createLoweredSoftBevelBorder());
		setLayout(null);

		// Creating word label with properties
		JLabel wordLabel = new JLabel("Word");
		wordLabel.setFont(new Font(Constants.FONT_LUCIDA_GRANDE, Font.PLAIN, 14));
		wordLabel.setHorizontalAlignment(SwingConstants.CENTER);
		wordLabel.setBounds(160, 36, 61, 16);

		// Adding word label to add panel
		add(wordLabel);

		// Creating meaning label with properties
		JLabel meaningLabel = new JLabel("Meaning");
		meaningLabel.setFont(new Font(Constants.FONT_LUCIDA_GRANDE, Font.PLAIN, 14));
		meaningLabel.setHorizontalAlignment(SwingConstants.CENTER);
		meaningLabel.setBounds(160, 73, 61, 16);

		// Adding meaning label to add panel
		add(meaningLabel);

		// Creating alternate meaning label with properties
		JLabel alternateMeaningLabel = new JLabel("Alternate Meaning");
		alternateMeaningLabel.setFont(new Font(Constants.FONT_LUCIDA_GRANDE, Font.PLAIN, 14));
		alternateMeaningLabel.setHorizontalAlignment(SwingConstants.CENTER);
		alternateMeaningLabel.setBounds(103, 114, 130, 16);

		// Adding alternate meaning label to add panel
		add(alternateMeaningLabel);

		// Creating word text field with properties
		wordTextField = new JTextField(10);
		wordTextField.setBounds(245, 30, 130, 26);

		// Adding word text field to add panel
		add(wordTextField);

		// Creating meaning text field with properties
		meaningTextField = new JTextField(10);
		meaningTextField.setBounds(245, 69, 130, 26);

		// Adding meaning text field to add panel
		add(meaningTextField);

		// Creating alternate meaning text field with properties
		alternateMeaningTextField = new JTextField(10);
		alternateMeaningTextField.setBounds(245, 109, 130, 26);

		// Adding alternate meaning text field to add panel
		add(alternateMeaningTextField);

		// Creating add button with properties
		JButton addButton = new JButton("ADD", FontIcon.of(Dashicons.PLUS_LIGHT, 20));
		addButton.setFont(new Font(Constants.FONT_LUCIDA_GRANDE, Font.PLAIN, 13));
		addButton.setBounds(245, 147, 117, 29);
		addButton.addActionListener(this);

		// Adding add button to add panel
		add(addButton);

		// Creating response label with properties
		responseLabel = new JLabel("Word added successfully");
		responseLabel.setForeground(new Color(255, 69, 0));
		responseLabel.setFont(new Font(Constants.FONT_LUCIDA_GRANDE, Font.PLAIN, 13));
		responseLabel.setHorizontalAlignment(SwingConstants.CENTER);
		responseLabel.setBounds(6, 193, 565, 16);
		responseLabel.setVisible(false);

		// Adding response label to add panel
		add(responseLabel);

		// Creating execution time label to add panel
		executionTimeLabel = new JLabel("Time Taken: 300 ms");
		executionTimeLabel.setFont(new Font(Constants.FONT_LUCIDA_GRANDE, Font.BOLD | Font.ITALIC, 12));
		executionTimeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		executionTimeLabel.setBounds(342, 252, 229, 16);
		executionTimeLabel.setVisible(false);

		// Adding execution time label to add panel
		add(executionTimeLabel);
	}

	/**
	 * This method is used to perform furthur actions when add button is clicked. It
	 * calls dictionary client to send request to the server and then display the
	 * response and execution time.
	 * 
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		// Setting empty text to response label and execution time label for each button
		// click.
		responseLabel.setText(StringUtils.EMPTY);
		executionTimeLabel.setText(StringUtils.EMPTY);

		// Sending request and receiving response.
		DictionaryDto response = dictionaryClient.handleRequestAndResponse(null, wordTextField.getText(),
				meaningTextField.getText(), alternateMeaningTextField.getText(), Constants.ADD);

		// Setting the response label according to the server response.
		responseLabel.setText(response.getMessage());
		responseLabel.setVisible(true);

		// Setting the execution time label according to the server response.
		executionTimeLabel.setText("Time Taken: " + response.getTimeTaken() + StringUtils.SPACE + "ms");
		executionTimeLabel.setVisible(true);
	}

	/**
	 * This methos is used to handle state changes of the option tab pane. It
	 * refershes the whole page by removing the previously shown results as well as
	 * the word enetered by the user.
	 * 
	 */
	public void stateChange() {
		// Removing the response lable and marking it empty
		responseLabel.setText(StringUtils.EMPTY);
		responseLabel.setVisible(false);

		// Removing the execution time label and marking it empty
		executionTimeLabel.setText(StringUtils.EMPTY);
		executionTimeLabel.setVisible(false);

		// Marking the word field empty
		wordTextField.setText(StringUtils.EMPTY);

		// Marking the meaning field empty
		meaningTextField.setText(StringUtils.EMPTY);

		// Marking the alternate meaning field empty
		alternateMeaningTextField.setText(StringUtils.EMPTY);
	}

}
