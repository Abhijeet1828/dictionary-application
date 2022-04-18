package com.distributed.project.dictionary.server.utils;

/**
 * This ENUM is used to store the error messages encountered across the
 * application.
 * 
 * @author Abhijeet - 1278218
 *
 */
public enum FailureConstants {

	FILE_NOT_FOUND_FAILURE(Constants.ERROR, "Oops! Looks like the dicitonary file specified is not present"),
	IO_FAILURE(Constants.ERROR, "Oops! Something went wrong. Please try again"),
	FILE_PARSE_FAILURE(Constants.ERROR, "Oops! Looks like the dictionary file format is incorrect"),
	SEARCH_FAILURE(Constants.ERROR, "Oops! Looks like the searched word is not present in the dictionary"),
	SEARCH_KEY_EMPTY_FAILURE(Constants.ERROR, "Oops! Looks like the search key is empty"),
	WORD_MEANING_EMPTY_FAILURE(Constants.ERROR, "Oops! Looks like either word or meaning is empty"),
	EXISTING_WORD_FAILURE(Constants.ERROR, "Oops! Looks like the word already exists in dictionary"),
	WORD_EMPTY_FAILURE(Constants.ERROR, "Oops! Looks like word field is empty"),
	NON_EXISTING_WORD_FAILURE(Constants.ERROR, "Oops! Looks like the word does not exist in dictionary"),
	UNDEFINED_OPTION_FAILURE(Constants.ERROR, "Oops! Looks like the option selected is not supported");

	private final String key;
	private final String message;

	public String getKey() {
		return key;
	}

	public String getMessage() {
		return message;
	}

	private FailureConstants(String key, String message) {
		this.key = key;
		this.message = message;
	}

}
