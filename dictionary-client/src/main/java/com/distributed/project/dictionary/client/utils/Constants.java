package com.distributed.project.dictionary.client.utils;

import com.google.common.collect.ImmutableBiMap;

/**
 * This class is used to store constants to use across the application.
 * 
 * @author Abhijeet - 1278218
 *
 */
public final class Constants {

	private Constants() {
		throw new IllegalStateException("Constants class cannot be instantiated");
	}

	// OPERATION CONSTANTS
	public static final String SEARCH = "search";
	public static final String ADD = "add";
	public static final String DELETE = "delete";
	public static final String UPDATE = "update";
	public static final String EXIT = "exit";
	public static final String UNDEFINED = "undefined";

	// OPTION MAP
	public static final ImmutableBiMap<String, String> OPTION_MAP = ImmutableBiMap.of("1", Constants.SEARCH, "2",
			Constants.ADD, "3", Constants.DELETE, "4", Constants.UPDATE, "5", Constants.EXIT);

	// FONT CONSTANTS
	public static final String FONT_LUCIDA_GRANDE = "Lucida Grande";

	// ERROR CONSTANTS
	public static final String ERROR_UNKNOWN_HOST = "Unknown host - Either server address or port mentioned are not reachable";
	public static final String ERROR_COMMUNICATION = "Connection Issue - Error while sending request to server";

	// GRAPH CONSTANTS
	public static final String GRAPH_ROW_KEY = "Average Response Time (ms)";
}
