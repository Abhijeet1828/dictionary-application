package com.distributed.project.dictionary.server.utils;

import com.distributed.project.dictionary.server.dto.DictionaryDto;

/**
 * This class is used for converting the parameters to necessary DTO.
 * 
 * @author Abhijeet - 1278218
 *
 */
public final class DtoConversionUtils {

	private DtoConversionUtils() {
		throw new IllegalStateException("DtoConversionUtils class cannot be instantiated");
	}

	/**
	 * This method is used to create {@link DictionaryDto} for a given error
	 * response.
	 * 
	 * @param action
	 * @param searchKey
	 * @param errorMessage
	 * @return
	 */
	public static DictionaryDto convertToErrorResponse(String action, String searchKey, String errorMessage) {
		return new DictionaryDto(action, searchKey, searchKey, null, null, false, errorMessage);
	}
}
