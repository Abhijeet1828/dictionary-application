package com.distributed.project.dictionary.server.utils;

/**
 * This is a constants class which is use to store constants which can be used
 * anywhere in the application.
 * 
 * @author Abhijeet - 1278218
 *
 */
public final class Constants {

	private Constants() {
		throw new IllegalStateException("Constants class cannot be instantiated");
	}

	// ERROR AND SUCCESS KEY CONSTANTS
	public static final String ERROR = "error";
	public static final String SUCCESS = "success";

	// ACTION CONSTANTS
	public static final String SEARCH = "search";
	public static final String ADD = "add";
	public static final String DELETE = "delete";
	public static final String UPDATE = "update";
	public static final String EXIT = "exit";

	// DICTIONARY FILE STORAGE KEY CONSTANTS
	public static final String FIRST_MEANING = "firstMeaning";
	public static final String SECOND_MEANING = "secondMeaning";

	// FONT CONSTANTS
	public static final String FONT_LUCIDA_GRANDE = "Lucida Grande";

	// GRAPH CONSTANTS
	public static final String GRAPH_ROW_KEY = "Average Response Time (ms)";
}
