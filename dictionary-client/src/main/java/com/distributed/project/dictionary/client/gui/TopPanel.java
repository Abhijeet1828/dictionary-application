package com.distributed.project.dictionary.client.gui;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import org.kordamp.ikonli.dashicons.Dashicons;
import org.kordamp.ikonli.swing.FontIcon;

import com.distributed.project.dictionary.client.utils.Constants;

/**
 * This class is used to add the top panel with necessary components. It extends
 * {@link JPanel} for ease of use.
 * 
 * @author Abhijeet - 1278218
 *
 */
public class TopPanel extends JPanel {

	private static final long serialVersionUID = 7805485857847257430L;

	public TopPanel() {
		initialize();
	}

	/**
	 * This method is used to initialize the top panel with necessary components.
	 */
	private void initialize() {
		// Generating top panel with properties
		setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		setBackground(new Color(176, 196, 222));
		setBounds(0, 0, 600, 50);
		setLayout(null);

		// Creating heading label with properties
		JLabel headingLabel = new JLabel("DICTIONARY CLIENT");
		headingLabel.setFont(new Font(Constants.FONT_LUCIDA_GRANDE, Font.PLAIN, 17));
		headingLabel.setBounds(188, 6, 221, 38);
		headingLabel.setHorizontalAlignment(SwingConstants.CENTER);
		headingLabel.setVerticalAlignment(SwingConstants.CENTER);

		// Adding heading to the top panel
		add(headingLabel);

		// Creating heading icon label with properties
		JLabel headingIcon = new JLabel(FontIcon.of(Dashicons.BOOK, 30));
		headingIcon.setBounds(6, 6, 61, 38);

		// Adding heading icon to top panel
		add(headingIcon);
	}

}
