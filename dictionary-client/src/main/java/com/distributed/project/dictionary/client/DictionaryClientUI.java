package com.distributed.project.dictionary.client;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;

import com.distributed.project.dictionary.client.gui.BottomPanel;
import com.distributed.project.dictionary.client.gui.OptionTabPane;
import com.distributed.project.dictionary.client.gui.TopPanel;
import com.distributed.project.dictionary.client.gui.WelcomePanel;

/**
 * This class is used to create {@link JFrame} with the necessary UI components
 * for dictionary operations.
 * 
 * @@author Abhijeet - 1278218
 *
 */
public class DictionaryClientUI extends JFrame {

	private static final long serialVersionUID = -8831590360324650243L;

	protected DictionaryClient dictionaryClient;

	/**
	 * Initializes the dictionary client and the Client UI.
	 * 
	 * @param dictionaryClient
	 */
	public DictionaryClientUI(DictionaryClient dictionaryClient) {
		this.dictionaryClient = dictionaryClient;
		initialize();
	}

	/**
	 * This method initializes the Client UI and adds the necessary components to
	 * the {@link JFrame}
	 * 
	 */
	private void initialize() {
		// JFrame Properties
		setTitle("Dictionary Client");
		setBounds(100, 100, 600, 480);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);

		// Adding top panel to frame
		getContentPane().add(new TopPanel());

		// Adding welcome panel to frame
		getContentPane().add(new WelcomePanel());

		// Creating tab options pane
		OptionTabPane optionTabPane = new OptionTabPane(dictionaryClient);

		// Adding option tab pane to frame
		getContentPane().add(optionTabPane);

		// Adding bottom panel to frame
		getContentPane().add(new BottomPanel(dictionaryClient));
	}

	/**
	 * This method is used to add error pop ups and close the Client for any fatal
	 * errors.
	 * 
	 * @param errorMessage
	 * @param isCritical
	 */
	public void addErrorPopUp(String errorMessage, boolean isCritical) {
		JOptionPane.showMessageDialog(this, errorMessage, "Alert", JOptionPane.WARNING_MESSAGE);
		if (isCritical) {
			this.setVisible(false);
			System.exit(1);
		}
	}

	/**
	 * This method is used to show the exit pop-up when client clicks on the exit
	 * button.
	 */
	public void addExitPopUp() {
		JOptionPane.showMessageDialog(this, "Thank you for using dictionary client!!", "Thank you",
				JOptionPane.PLAIN_MESSAGE);
		this.setVisible(true);
		System.exit(1);
	}
}
