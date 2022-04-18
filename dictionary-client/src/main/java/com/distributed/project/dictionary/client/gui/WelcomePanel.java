package com.distributed.project.dictionary.client.gui;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import com.distributed.project.dictionary.client.utils.Constants;

/**
 * This class is used to initialize the welcome panel and add the ncessary
 * components. It extends {@link JPanel} for ease of use.
 * 
 * @author Abhijeet - 1278218
 *
 */
public class WelcomePanel extends JPanel {

	private static final long serialVersionUID = 942360467974099010L;

	public WelcomePanel() {
		initialize();
	}

	/**
	 * This method is used to initialize the welcome panel with necessary
	 * components.
	 */
	private void initialize() {
		// Generating welcome panel with properties
		setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		setBackground(new Color(176, 196, 222));
		setBounds(0, 50, 600, 38);
		setLayout(null);

		// Creating welcome label with properties
		JLabel welcomeLabel = new JLabel("Welcome to Dictionary Client");
		welcomeLabel.setFont(new Font(Constants.FONT_LUCIDA_GRANDE, Font.PLAIN, 16));
		welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		welcomeLabel.setBounds(176, 6, 246, 26);

		add(welcomeLabel);
	}
}
