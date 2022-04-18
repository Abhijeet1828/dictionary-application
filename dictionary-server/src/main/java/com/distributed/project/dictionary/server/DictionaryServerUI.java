package com.distributed.project.dictionary.server;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import com.distributed.project.dictionary.server.gui.BottomPanel;
import com.distributed.project.dictionary.server.gui.OptionTabPane;
import com.distributed.project.dictionary.server.gui.TopPanel;
import com.distributed.project.dictionary.server.gui.WelcomePanel;

/**
 * This class is used to make {@link JFrame} with the necessary UI components
 * for the dictionary server utility.
 * 
 * @author Abhijeet - 1278218
 *
 */
public class DictionaryServerUI extends JFrame {

	private static final long serialVersionUID = -1655006406018341930L;

	protected DictionaryServer dictionaryServer;

	/**
	 * This constructor initializes the dictionary server and the UI.
	 * 
	 * @param dictionaryServer
	 */
	public DictionaryServerUI(DictionaryServer dictionaryServer) {
		this.dictionaryServer = dictionaryServer;
		initialize();
	}

	/**
	 * This method initialized the dictionary server UI i.e {@link JFrame} and adds
	 * the necessary components.
	 * 
	 */
	private void initialize() {
		// JFrame properties
		setTitle("Dictionary Server");
		setBounds(850, 100, 600, 480);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);

		// Adding the top panel to the UI.
		getContentPane().add(new TopPanel());

		// Adding the welcome panel to the UI.
		getContentPane().add(new WelcomePanel());

		// Adding the option tab pane to the UI.
		getContentPane().add(new OptionTabPane(dictionaryServer));

		// Adding the bottom panel to the UI.
		getContentPane().add(new BottomPanel(dictionaryServer, this));
	}

}
