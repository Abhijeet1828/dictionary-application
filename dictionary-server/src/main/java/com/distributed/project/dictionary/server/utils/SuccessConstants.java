package com.distributed.project.dictionary.server.utils;

/**
 * This ENUM is used to store success messages.
 * 
 * @author Abhijeet - 1278218
 *
 */
public enum SuccessConstants {

	SEARCH_SUCCESS(Constants.SUCCESS, "Word found successfully"),
	ADD_SUCCESS(Constants.SUCCESS, "Word added successfully"),
	DELETE_SUCCESS(Constants.DELETE, "Word deleted successfully"),
	UPDATE_SUCCESS(Constants.UPDATE, "Word updated successfully");

	private final String key;
	private final String message;

	public String getKey() {
		return key;
	}

	public String getMessage() {
		return message;
	}

	private SuccessConstants(String key, String message) {
		this.key = key;
		this.message = message;
	}

}
