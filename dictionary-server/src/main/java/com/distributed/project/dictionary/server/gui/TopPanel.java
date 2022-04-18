package com.distributed.project.dictionary.server.gui;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import org.kordamp.ikonli.dashicons.Dashicons;
import org.kordamp.ikonli.swing.FontIcon;

import com.distributed.project.dictionary.server.utils.Constants;

/**
 * This class is used to create the top panel with necessary components. It
 * extends {@link JPanel} for ease of use.
 * 
 * @author Abhijeet - 1278218
 *
 */
public class TopPanel extends JPanel {

	private static final long serialVersionUID = 6985559261977398785L;

	/**
	 * This method is used to call the intialize method.
	 */
	public TopPanel() {
		initialize();
	}

	/**
	 * This method is used to intialize the top panel with necessary components.
	 * 
	 */
	private void initialize() {
		// Initializing the top panel with properties
		setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		setBackground(new Color(240, 128, 128));
		setBounds(0, 0, 600, 50);
		setLayout(null);

		// Creating the heading label with properties
		JLabel headingLabel = new JLabel("DICTIONARY SERVER");
		headingLabel.setFont(new Font(Constants.FONT_LUCIDA_GRANDE, Font.PLAIN, 17));
		headingLabel.setBounds(188, 6, 221, 38);
		headingLabel.setHorizontalAlignment(SwingConstants.CENTER);
		headingLabel.setVerticalAlignment(SwingConstants.CENTER);

		// Adding heading label to top panel
		add(headingLabel);

		// Creating the heading icon 1 label with properties
		JLabel headingIcon1 = new JLabel(FontIcon.of(Dashicons.BOOK, 30));
		headingIcon1.setBounds(6, 6, 61, 38);

		// Adding heading icon 1 to the top panel
		add(headingIcon1);

		// Creating the heading icon 2 label with properties
		JLabel headingIcon2 = new JLabel(FontIcon.of(Dashicons.ADMIN_TOOLS, 30));
		headingIcon2.setBounds(39, 6, 48, 38);

		// Adding heading icon 2 to the top panel
		add(headingIcon2);
	}
}
