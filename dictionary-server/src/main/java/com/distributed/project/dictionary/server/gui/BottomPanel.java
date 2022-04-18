package com.distributed.project.dictionary.server.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import org.kordamp.ikonli.dashicons.Dashicons;
import org.kordamp.ikonli.swing.FontIcon;

import com.distributed.project.dictionary.server.DictionaryServer;
import com.distributed.project.dictionary.server.DictionaryServerUI;
import com.distributed.project.dictionary.server.utils.Constants;

/**
 * This class is used to create bottom panel with the necessary UI components.
 * It extends {@link JPanel} for ease of use.
 * 
 * @author Abhijeet - 1278218
 *
 */
public class BottomPanel extends JPanel implements ActionListener {

	private static final long serialVersionUID = -8172709478561343004L;

	protected DictionaryServer dictionaryServer;
	protected DictionaryServerUI dictionaryServerUI;

	/**
	 * Initializing the dictionary server UI and the dictionary server.
	 * 
	 * @param dictionaryServer
	 * @param dictionaryServerUI
	 */
	public BottomPanel(DictionaryServer dictionaryServer, DictionaryServerUI dictionaryServerUI) {
		this.dictionaryServerUI = dictionaryServerUI;
		this.dictionaryServer = dictionaryServer;
		initialize();
	}

	/**
	 * This method is used to set the properties of the JPanel and add exit button
	 * and author label.
	 * 
	 */
	private void initialize() {
		// Initializing bottom panel with properties
		setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		setBackground(new Color(240, 128, 128));
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
		authorLabel.setFont(new Font(Constants.FONT_LUCIDA_GRANDE, Font.BOLD | Font.ITALIC, 11));
		authorLabel.setHorizontalAlignment(SwingConstants.CENTER);
		authorLabel.setBounds(22, 13, 131, 16);

		// Adding author label to bottom panel
		add(authorLabel);
	}

	/**
	 * This method is used for actions to be taken when exit button is clicked. It
	 * shows an exit popup and closes the application.
	 * 
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		JOptionPane.showMessageDialog(dictionaryServerUI, "Thank you for using dictionary server!!", "Thank you",
				JOptionPane.PLAIN_MESSAGE);
		dictionaryServerUI.setVisible(true);
		System.exit(1);
	}

}
