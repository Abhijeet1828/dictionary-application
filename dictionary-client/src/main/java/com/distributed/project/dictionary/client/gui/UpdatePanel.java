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
 * This class is used to initialize the update panel and add the necessary
 * components. It extends {@link JPanel} for ease of use.
 * 
 * @author Abhijeet - 1278218
 *
 */
public class UpdatePanel extends JPanel implements ActionListener {

	private static final long serialVersionUID = -1710583359185546027L;

	protected DictionaryClient dictionaryClient;
	protected JTextField wordTextField;
	protected JTextField meaningTextField;
	protected JTextField alternateMeaningTextField;
	protected JLabel responseLabel;
	protected JLabel executionTimeLabel;

	/**
	 * This constructor is used to initialize the dictionary client.
	 * 
	 * @param dictionaryClient
	 */
	public UpdatePanel(DictionaryClient dictionaryClient) {
		this.dictionaryClient = dictionaryClient;
		initialize();
	}

	/**
	 * This method is used to intialize the update panel with necessary components.
	 */
	private void initialize() {
		// Generating update panel with properties
		setForeground(Color.BLACK);
		setBorder(BorderFactory.createLoweredSoftBevelBorder());
		setLayout(null);

		// Creating word label with properties
		JLabel wordLabel = new JLabel("Word");
		wordLabel.setFont(new Font(Constants.FONT_LUCIDA_GRANDE, Font.PLAIN, 14));
		wordLabel.setHorizontalAlignment(SwingConstants.CENTER);
		wordLabel.setBounds(155, 28, 61, 16);

		// Adding word label to update panel
		add(wordLabel);

		// Creating meaning label with properties
		JLabel meaningLabel = new JLabel("Meaning");
		meaningLabel.setFont(new Font(Constants.FONT_LUCIDA_GRANDE, Font.PLAIN, 14));
		meaningLabel.setHorizontalAlignment(SwingConstants.CENTER);
		meaningLabel.setBounds(155, 69, 61, 16);

		// Adding meaning label to update panel
		add(meaningLabel);

		// Creating alternate meaning label with properties
		JLabel alternateMeaningLabel = new JLabel("Alternate Meaning");
		alternateMeaningLabel.setHorizontalAlignment(SwingConstants.CENTER);
		alternateMeaningLabel.setFont(new Font(Constants.FONT_LUCIDA_GRANDE, Font.PLAIN, 14));
		alternateMeaningLabel.setBounds(86, 109, 130, 16);

		// Adding alternate meaning label to update panel
		add(alternateMeaningLabel);

		// Creating word text field with properties
		wordTextField = new JTextField(10);
		wordTextField.setBounds(244, 23, 130, 26);

		// Adding word text field to update panel
		add(wordTextField);

		// Creating meaning text field with properties
		meaningTextField = new JTextField(10);
		meaningTextField.setBounds(244, 64, 130, 26);

		// Adding meaning text field to update panel
		add(meaningTextField);

		// Creating alternate meaning text field with properties
		alternateMeaningTextField = new JTextField(10);
		alternateMeaningTextField.setBounds(244, 105, 130, 26);

		// Adding alternate meaning text field to update panel
		add(alternateMeaningTextField);

		// Creating update button with properties
		JButton updateButton = new JButton("UPDATE", FontIcon.of(Dashicons.YES, 20));
		updateButton.setFont(new Font(Constants.FONT_LUCIDA_GRANDE, Font.PLAIN, 13));
		updateButton.setBounds(244, 143, 117, 29);
		updateButton.addActionListener(this);

		// Adding update button to add panel
		add(updateButton);

		// Creating response label with properties
		responseLabel = new JLabel("Word updated successfully");
		responseLabel.setForeground(new Color(255, 69, 0));
		responseLabel.setHorizontalAlignment(SwingConstants.CENTER);
		responseLabel.setBounds(6, 184, 565, 16);
		responseLabel.setVisible(false);

		// Adding response label to update panel
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
	 * This method is called whenever user clicks on the update button. It sends
	 * request to the server and receives response and then populates the relevant
	 * labels.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		// Marking response and execution time label empty at start
		responseLabel.setText(StringUtils.EMPTY);
		executionTimeLabel.setText(StringUtils.EMPTY);

		// Sending and receiving response from the server
		DictionaryDto response = dictionaryClient.handleRequestAndResponse(null, wordTextField.getText(),
				meaningTextField.getText(), alternateMeaningTextField.getText(), Constants.UPDATE);

		// Adding the response and marking it visible
		responseLabel.setText(response.getMessage());
		responseLabel.setVisible(true);

		// Adding the execution time and marking it visible
		executionTimeLabel.setText("Time Taken: " + response.getTimeTaken() + StringUtils.SPACE + "ms");
		executionTimeLabel.setVisible(true);
	}

	/**
	 * This method is called whenever user switches to the update panel. It clears
	 * the old response and fields for new request.
	 */
	public void stateChange() {
		// Making response label empty and removing it from view.
		responseLabel.setText(StringUtils.EMPTY);
		responseLabel.setVisible(false);

		// Making the execution time label empty and remvoing it from view.
		executionTimeLabel.setText(StringUtils.EMPTY);
		executionTimeLabel.setVisible(false);

		// Making the word text field empty
		wordTextField.setText(StringUtils.EMPTY);

		// Making the meaning text field empty
		meaningTextField.setText(StringUtils.EMPTY);

		// Making the alternate meaning text field empty
		alternateMeaningTextField.setText(StringUtils.EMPTY);
	}

}
