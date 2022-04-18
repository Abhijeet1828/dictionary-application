package com.distributed.project.dictionary.client.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.distributed.project.dictionary.client.dto.DictionaryDto;

/**
 * 
 * @author Abhijeet - 1278218
 *
 */
public final class ResponseProcessingUtils {

	private static final Logger LOGGER = LoggerFactory.getLogger(ResponseProcessingUtils.class);

	private ResponseProcessingUtils() {
		throw new IllegalStateException("ResponseProcessingUtils class cannot be instantiated");
	}
	
	public static void processResponse(String response) {
		DictionaryDto responseClass = TypeConversionUtils.convertToCustomClass(response, DictionaryDto.class);

		if (responseClass.getAction().equalsIgnoreCase(Constants.SEARCH) && responseClass.isPresent()) {
			LOGGER.info("Word: {} -- Meaning: {}, SecondaryMeaning: {}", responseClass.getWord(),
					responseClass.getMeaning(), responseClass.getSecondaryMeaning());
		} else if (responseClass.isPresent()) {
			LOGGER.info(responseClass.getMessage());
		} else {
			LOGGER.error(responseClass.getMessage());
		}
	}
}
