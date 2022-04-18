package com.distributed.project.dictionary.client.utils;

import java.util.Scanner;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.distributed.project.dictionary.client.dto.DictionaryDto;

/**
 * This class is used to process the input from user and convert it to relevant
 * DTO.
 * 
 * @author Abhijeet - 1278218
 *
 */
public final class RequestProcessingUtils {

	private static final Logger LOGGER = LoggerFactory.getLogger(RequestProcessingUtils.class);

	private RequestProcessingUtils() {
		throw new IllegalStateException("RequestProcessingUtils class cannot be instantiated");
	}

	/**
	 * This method creates the object {@link DictionaryDto} according to the action.
	 * 
	 * @param searchKey
	 * @param word
	 * @param meaning
	 * @param secondaryMeaning
	 * @param action
	 * 
	 * @return {@link DictionaryDto}
	 */
	public static DictionaryDto optionBasedRequest(String searchKey, String word, String meaning,
			String secondaryMeaning, String action) {
		switch (action) {
		case Constants.SEARCH:
			return new DictionaryDto(Constants.SEARCH, searchKey, null, null, null, false, null);

		case Constants.ADD:
			return new DictionaryDto(Constants.ADD, null, word, meaning, secondaryMeaning, false, null);

		case Constants.DELETE:
			return new DictionaryDto(Constants.DELETE, null, word, null, null, false, null);

		case Constants.UPDATE:
			return new DictionaryDto(Constants.UPDATE, null, word, meaning, secondaryMeaning, false, null);

		case Constants.EXIT:
			return new DictionaryDto(Constants.EXIT, null, null, null, null, false, null);

		default:
			return null;
		}
	}

	public static DictionaryDto optionBasedRequest(String clientInput, Scanner scanner) {
		String optionSelected = Constants.OPTION_MAP.containsKey(clientInput) ? Constants.OPTION_MAP.get(clientInput)
				: Constants.UNDEFINED;

		if (StringUtils.isEmpty(optionSelected)) {
			return null;
		}

		switch (optionSelected) {
		case Constants.SEARCH:
			return searchForWordInDictionary(scanner);

		case Constants.ADD:
			return addWordInDictionary(scanner);

		case Constants.DELETE:
			return deleteWordFromDictionary(scanner);

		case Constants.UPDATE:
			return updateWordInDictionary(scanner);

		default:
			return null;
		}
	}

	private static DictionaryDto searchForWordInDictionary(Scanner scanner) {
		LOGGER.info("Please enter the word you want to search");
		LOGGER.info(StringUtils.LF);
		String searchKey = scanner.nextLine();

		return new DictionaryDto(Constants.SEARCH, searchKey, null, null, null, false, null);
	}

	private static DictionaryDto addWordInDictionary(Scanner scanner) {
		DictionaryDto request = new DictionaryDto();
		LOGGER.info("Please enter the word you want to add");
		request.setWord(scanner.nextLine());

		LOGGER.info("Please enter the meaning of the word");
		request.setMeaning(scanner.nextLine());

		LOGGER.info("Please enter the secondary meaning of the word");
		request.setSecondaryMeaning(scanner.nextLine());

		request.setAction(Constants.ADD);

		return request;
	}

	private static DictionaryDto deleteWordFromDictionary(Scanner scanner) {
		LOGGER.info("Please enter the word you want to delete");
		String word = scanner.nextLine();

		return new DictionaryDto(Constants.DELETE, null, word, null, null, false, null);
	}

	private static DictionaryDto updateWordInDictionary(Scanner scanner) {
		DictionaryDto request = new DictionaryDto();
		LOGGER.info("Please enter the word you want to update");
		request.setWord(scanner.nextLine());

		LOGGER.info("Please enter the meaning of the word");
		request.setMeaning(scanner.nextLine());

		LOGGER.info("Please enter the secondary meaning of the word");
		request.setSecondaryMeaning(scanner.nextLine());

		request.setAction(Constants.UPDATE);

		return request;
	}

}
