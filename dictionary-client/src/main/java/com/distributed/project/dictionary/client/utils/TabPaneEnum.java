package com.distributed.project.dictionary.client.utils;

import javax.swing.Icon;

import org.kordamp.ikonli.dashicons.Dashicons;
import org.kordamp.ikonli.swing.FontIcon;

/**
 * This {@link Enum} is used to add necessary constants for operation panels.
 * 
 * @author Abhijeet - 1278218
 *
 */
public enum TabPaneEnum {

	SEARCH("SEARCH", 0, FontIcon.of(Dashicons.SEARCH, 20)), 
	ADD("ADD", 1, FontIcon.of(Dashicons.PLUS_LIGHT, 20)),
	UPDATE("UPDATE", 2, FontIcon.of(Dashicons.YES, 20)), 
	DELETE("DELETE", 3, FontIcon.of(Dashicons.TRASH, 20)),
	GRAPH("GRAPH", 4, FontIcon.of(Dashicons.CHART_BAR, 20));

	private final String paneName;

	private final int paneIndex;

	private final Icon paneIcon;

	public String getPaneName() {
		return paneName;
	}

	public int getPaneIndex() {
		return paneIndex;
	}

	public Icon getPaneIcon() {
		return paneIcon;
	}

	private TabPaneEnum(String paneName, int paneIndex, Icon paneIcon) {
		this.paneName = paneName;
		this.paneIndex = paneIndex;
		this.paneIcon = paneIcon;
	}

}
