package com.distributed.project.dictionary.client.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import org.kordamp.ikonli.dashicons.Dashicons;
import org.kordamp.ikonli.swing.FontIcon;

import com.distributed.project.dictionary.client.DictionaryClient;
import com.distributed.project.dictionary.client.utils.Constants;

/**
 * This class is used to display the bottom panel in the UI. This class extends
 * {@link JPanel} for ease of use.
 * 
 * @author Abhijeet - 1278218
 *
 */
public class BottomPanel extends JPanel implements ActionListener {

	private static final long serialVersionUID = 2063528127911937602L;

	protected DictionaryClient dictionaryClient;

	/**
	 * This constructor is used to initialize the dictionary client.
	 * 
	 * @param dictionaryClient
	 */
	public BottomPanel(DictionaryClient dictionaryClient) {
		this.dictionaryClient = dictionaryClient;
		initialize();
	}

	/**
	 * This method is used to initialize the bottom with components.
	 */
	private void initialize() {
		// Generating bottom panel with properties
		setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		setBackground(new Color(176, 196, 222));
		setBounds(0, 410, 600, 42);
		setLayout(null);

		// Creating exit button with properties
		JButton exitButton = new JButton("EXIT", FontIcon.of(Dashicons.CONTROLS_BACK, 20));
		exitButton.setFont(new Font(Constants.FONT_LUCIDA_GRANDE, Font.PLAIN, 13));
		exitButton.setBounds(477, 6, 117, 29);
		exitButton.addActionListener(this);

		// Adding exit button to bottom panel
		add(exitButton);

		// Creating author label with properties
		JLabel authorLabel = new JLabel("Author: Abhijeet");
		authorLabel.setFont(new Font("Lucida Grande", Font.BOLD | Font.ITALIC, 11));
		authorLabel.setHorizontalAlignment(SwingConstants.CENTER);
		authorLabel.setBounds(22, 13, 131, 16);
		authorLabel.setVisible(true);

		// Adding author label to bottom panel
		add(authorLabel);
	}

	/**
	 * This method is called when user clicks on the exit button. It sends exit
	 * option select to server for disconnection and then exits the process.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		dictionaryClient.handleRequestAndResponse(null, null, null, null, Constants.EXIT);
	}

}
