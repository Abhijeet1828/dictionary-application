package com.distributed.project.dictionary.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.collections4.map.CaseInsensitiveMap;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.distributed.project.dictionary.server.dto.DictionaryDto;
import com.distributed.project.dictionary.server.utils.Constants;
import com.distributed.project.dictionary.server.utils.DtoConversionUtils;
import com.distributed.project.dictionary.server.utils.FailureConstants;
import com.distributed.project.dictionary.server.utils.SuccessConstants;
import com.distributed.project.dictionary.server.utils.TypeConversionUtils;

/**
 * This is a runnable instance of dictionary operations.
 * 
 * @author Abhijeet - 1278218
 *
 */
public class DictionaryUtils implements Runnable {

	private static final Logger LOGGER = LoggerFactory.getLogger(DictionaryUtils.class);

	protected DictionaryServer server = null;
	protected Socket client = null;
	protected int clientNumber = 0;
	protected String dictionaryFileLocation = null;

	/**
	 * Initializing the Dictionary utils class with respective client information.
	 * 
	 * @param server
	 * @param client
	 * @param clientNumber
	 * @param dictionaryFileLocation
	 */
	public DictionaryUtils(DictionaryServer server, Socket client, int clientNumber, String dictionaryFileLocation) {
		this.server = server;
		this.client = client;
		this.clientNumber = clientNumber;
		this.dictionaryFileLocation = dictionaryFileLocation;
	}

	@Override
	public void run() {
		LOGGER.info("Connection for thread {} started with client number {}", Thread.currentThread().getName(),
				this.clientNumber);

		// Opening the input and output stream with the client
		try (BufferedReader in = new BufferedReader(
				new InputStreamReader(client.getInputStream(), StandardCharsets.UTF_8));
				BufferedWriter out = new BufferedWriter(
						new OutputStreamWriter(client.getOutputStream(), StandardCharsets.UTF_8))) {

			// Initializing the option select variable 
			String optionSelect = null;

			// Keeping the thread running while client connection available
			while ((optionSelect = in.readLine()) != null) {
				LOGGER.info("Message from Client {} recieved, option {}", this.clientNumber, optionSelect);

				// Parsing the request from client
				DictionaryDto request = TypeConversionUtils.convertToCustomClass(optionSelect, DictionaryDto.class);
				
				// Adding the response times to server response time map
				server.addToResponseTimeMap(request.getResponseTimeMap());

				// If client selects exit option the remove from mapping and close the socket
				if (Constants.EXIT.equalsIgnoreCase(request.getAction())) {
					server.getClientSocketMap().remove(clientNumber);
					LOGGER.info("Client {} disconnecting, remaining clients {}", clientNumber,
							server.getClientSocketMap());
					break;
				}

				// Reading the dictionary file
				Map<String, Object> dictionaryData = readDictionaryFile(this.dictionaryFileLocation);
				
				// Handling read failures
				if (MapUtils.isEmpty(dictionaryData) || dictionaryData.containsKey(Constants.ERROR)) {
					LOGGER.error("Error while reading dictionary file {}", dictionaryData);

					// Parsing error response
					DictionaryDto errorResponse = new DictionaryDto(request.getAction(), request.getSearchKey(),
							request.getWord(), null, null, false, dictionaryData.get(Constants.ERROR).toString());

					// Sending error response to client
					sendResponseToClient(TypeConversionUtils.convertObjectToString(errorResponse), out);
				} else {
					// Execution operations according to the client request
					DictionaryDto response = dictionaryOptions(request, dictionaryData, this.dictionaryFileLocation);
					
					// Sending response to client
					sendResponseToClient(TypeConversionUtils.convertObjectToString(response), out);
				}

				LOGGER.info("Message Sent back from Server");
			}

			// Closing client socket on exiting while loop
			client.close();
		} catch (IOException e) {
			LOGGER.error("Exception occured in run method of DictionaryUtils for client {}", this.clientNumber, e);
		}
	}

	/**
	 * This method is used to compute the option selected by the client and run the
	 * respective method.
	 * 
	 * @param request
	 * @param dictionaryData
	 * @param dictionaryFileLocation
	 * @return
	 */
	private DictionaryDto dictionaryOptions(DictionaryDto request, Map<String, Object> dictionaryData,
			String dictionaryFileLocation) {
		switch (request.getAction()) {
		case Constants.SEARCH:
			return searchKeyInDictionary(request, dictionaryData);

		case Constants.ADD:
			return addWordInDictionary(request, dictionaryData, dictionaryFileLocation);

		case Constants.DELETE:
			return deleteWordFromDictionary(request, dictionaryData, dictionaryFileLocation);

		case Constants.UPDATE:
			return updateWordInDictionary(request, dictionaryData, dictionaryFileLocation);

		default:
			return DtoConversionUtils.convertToErrorResponse(request.getAction(), request.getSearchKey(),
					FailureConstants.UNDEFINED_OPTION_FAILURE.getMessage());
		}
	}

	/**
	 * This method is used to search key in a dictionary. Case Insensitive search is
	 * being used by implementing CaseInsensitiveMap implementation of
	 * org.apache.commons
	 * 
	 * @param request
	 * @param dictionaryData
	 * @return
	 */
	private DictionaryDto searchKeyInDictionary(DictionaryDto request, Map<String, Object> dictionaryData) {
		try {
			// Handling empty searches
			if (StringUtils.isEmpty(request.getSearchKey())) {
				LOGGER.error("Search key empty in request {}", request);
				return DtoConversionUtils.convertToErrorResponse(request.getAction(), request.getSearchKey(),
						FailureConstants.SEARCH_KEY_EMPTY_FAILURE.getMessage());
			}

			// Return meaning if word is present else return error response
			if (dictionaryData.containsKey(request.getSearchKey())) {
				Map<String, String> wordMeaningMap = TypeConversionUtils
						.convertToStringkeyValueMap(dictionaryData.get(request.getSearchKey()));
				return new DictionaryDto(request.getAction(), request.getSearchKey(), request.getSearchKey(),
						wordMeaningMap.get(Constants.FIRST_MEANING), wordMeaningMap.get(Constants.SECOND_MEANING), true,
						SuccessConstants.SEARCH_SUCCESS.getMessage());
			} else {
				LOGGER.error("Searched word {} not found in dictionary", request.getSearchKey());
				return DtoConversionUtils.convertToErrorResponse(request.getAction(), request.getSearchKey(),
						FailureConstants.SEARCH_FAILURE.getMessage());
			}
		} catch (Exception e) {
			LOGGER.error("Exception in searchKeyInDictionary", e);
			return DtoConversionUtils.convertToErrorResponse(request.getAction(), request.getSearchKey(),
					FailureConstants.IO_FAILURE.getMessage());
		}
	}

	/**
	 * This method is used to add word and the respective meaning in the dictionary
	 * file.
	 * 
	 * @param request
	 * @param dictionaryData
	 * @param dictionaryFileLocation
	 * @return
	 */
	private synchronized DictionaryDto addWordInDictionary(DictionaryDto request, Map<String, Object> dictionaryData,
			String dictionaryFileLocation) {
		// Word/Meaning should not be empty for insertion
		if (StringUtils.isEmpty(request.getWord()) || StringUtils.isEmpty(request.getMeaning())) {
			LOGGER.error("Either word/meaning is empty, word {}, meaning {}", request.getWord(), request.getMeaning());
			return DtoConversionUtils.convertToErrorResponse(request.getAction(), request.getWord(),
					FailureConstants.WORD_MEANING_EMPTY_FAILURE.getMessage());
		}

		// Existing words should not be added again
		if (dictionaryData.containsKey(request.getWord())) {
			LOGGER.error("Word {}, already present in dictionary", request.getWord());
			return DtoConversionUtils.convertToErrorResponse(request.getAction(), request.getWord(),
					FailureConstants.EXISTING_WORD_FAILURE.getMessage());
		}

		// Adding word and meanings in the dictionary file
		try (FileWriter fileWriter = new FileWriter(dictionaryFileLocation)) {
			Map<String, String> meaningMap = new HashMap<>();
			meaningMap.put(Constants.FIRST_MEANING, request.getMeaning());
			meaningMap.put(Constants.SECOND_MEANING,
					StringUtils.isNotEmpty(request.getSecondaryMeaning()) ? request.getSecondaryMeaning()
							: StringUtils.EMPTY);

			dictionaryData.put(request.getWord(), meaningMap);

			fileWriter.write(TypeConversionUtils.convertObjectToString(dictionaryData));
			fileWriter.flush();

			return new DictionaryDto(request.getAction(), request.getSearchKey(), request.getWord(),
					request.getMeaning(), request.getSecondaryMeaning(), true,
					SuccessConstants.ADD_SUCCESS.getMessage());
		} catch (IOException e) {
			LOGGER.error("Exception in addWordInDictionary", e);
			return DtoConversionUtils.convertToErrorResponse(request.getAction(), request.getWord(),
					FailureConstants.IO_FAILURE.getMessage());
		}
	}

	/**
	 * This method is used to delete word from the dictionary file.
	 * 
	 * @param request
	 * @param dictionaryData
	 * @param dictionaryFileLocation
	 * @return
	 */
	private synchronized DictionaryDto deleteWordFromDictionary(DictionaryDto request,
			Map<String, Object> dictionaryData, String dictionaryFileLocation) {
		// Word to be deleted is empty
		if (StringUtils.isEmpty(request.getWord())) {
			LOGGER.error("Word field is empty for request {}", request);
			return DtoConversionUtils.convertToErrorResponse(request.getAction(), request.getWord(),
					FailureConstants.WORD_EMPTY_FAILURE.getMessage());
		}

		// Word to be deleted is not present in dictionary
		if (BooleanUtils.isFalse(dictionaryData.containsKey(request.getWord()))) {
			LOGGER.error("Word {}, does not exist in dictionary to delete", request.getWord());
			return DtoConversionUtils.convertToErrorResponse(request.getAction(), request.getWord(),
					FailureConstants.NON_EXISTING_WORD_FAILURE.getMessage());
		}

		// Deleting word from the dictionary file
		try (FileWriter fileWriter = new FileWriter(dictionaryFileLocation)) {
			dictionaryData.remove(request.getWord());

			fileWriter.write(TypeConversionUtils.convertObjectToString(dictionaryData));
			fileWriter.flush();

			return new DictionaryDto(request.getAction(), request.getSearchKey(), request.getWord(),
					request.getMeaning(), request.getSecondaryMeaning(), true,
					SuccessConstants.DELETE_SUCCESS.getMessage());
		} catch (IOException e) {
			LOGGER.error("Exception in deleteWordFromDictionary", e);
			return DtoConversionUtils.convertToErrorResponse(request.getAction(), request.getWord(),
					FailureConstants.IO_FAILURE.getMessage());
		}
	}

	/**
	 * This method is used to update existing word in dictionary file.
	 * 
	 * @param request
	 * @param dictionaryData
	 * @param dictionaryFileLocation
	 * @return
	 */
	private synchronized DictionaryDto updateWordInDictionary(DictionaryDto request, Map<String, Object> dictionaryData,
			String dictionaryFileLocation) {
		// Word/Meaning should not be empty for update
		if (StringUtils.isEmpty(request.getWord()) || StringUtils.isEmpty(request.getMeaning())) {
			LOGGER.error("Either word/meaning is empty, word {}, meaning {}", request.getWord(), request.getMeaning());
			return DtoConversionUtils.convertToErrorResponse(request.getAction(), request.getWord(),
					FailureConstants.WORD_MEANING_EMPTY_FAILURE.getMessage());
		}

		// Word to be updated is not present in dictionary
		if (BooleanUtils.isFalse(dictionaryData.containsKey(request.getWord()))) {
			LOGGER.error("Word {}, does not exist in dictionary to update", request.getWord());
			return DtoConversionUtils.convertToErrorResponse(request.getAction(), request.getWord(),
					FailureConstants.NON_EXISTING_WORD_FAILURE.getMessage());
		}

		// Updating meaning for the word in dictionary
		try (FileWriter fileWriter = new FileWriter(dictionaryFileLocation)) {
			Map<String, String> wordMeaningMap = TypeConversionUtils
					.convertToStringkeyValueMap(dictionaryData.get(request.getWord()));
			wordMeaningMap.put(Constants.FIRST_MEANING, request.getMeaning());
			wordMeaningMap.put(Constants.SECOND_MEANING,
					StringUtils.isNotEmpty(request.getSecondaryMeaning()) ? request.getSecondaryMeaning()
							: wordMeaningMap.get(Constants.SECOND_MEANING));

			dictionaryData.put(request.getWord(), wordMeaningMap);

			fileWriter.write(TypeConversionUtils.convertObjectToString(dictionaryData));
			fileWriter.flush();

			return new DictionaryDto(request.getAction(), request.getSearchKey(), request.getWord(),
					request.getMeaning(), request.getSecondaryMeaning(), true,
					SuccessConstants.UPDATE_SUCCESS.getMessage());
		} catch (IOException e) {
			LOGGER.error("Exception in updateWordInDictionary", e);
			return DtoConversionUtils.convertToErrorResponse(request.getAction(), request.getWord(),
					FailureConstants.IO_FAILURE.getMessage());
		}
	}

	/**
	 * This method is used to read the dictionary file from the given file location.
	 * 
	 * @param dictionaryFileLocation
	 * @return
	 */
	private Map<String, Object> readDictionaryFile(String dictionaryFileLocation) {
		try (FileReader fileReader = new FileReader(dictionaryFileLocation)) {
			JSONParser jsonParser = new JSONParser();

			return new CaseInsensitiveMap<>(TypeConversionUtils.convertToMap(jsonParser.parse(fileReader)));

		} catch (FileNotFoundException e) {
			LOGGER.error("FileNotFound Exception in readDictionaryFile", e);
			return Collections.singletonMap(FailureConstants.FILE_NOT_FOUND_FAILURE.getKey(),
					FailureConstants.FILE_NOT_FOUND_FAILURE.getMessage());
		} catch (IOException e) {
			LOGGER.error("IO Exception in readDictionaryFile", e);
			return Collections.singletonMap(FailureConstants.IO_FAILURE.getKey(),
					FailureConstants.IO_FAILURE.getMessage());
		} catch (ParseException e) {
			LOGGER.error("Exception while parsing dictionary file in readDictionaryFile", e);
			return Collections.singletonMap(FailureConstants.FILE_PARSE_FAILURE.getKey(),
					FailureConstants.FILE_PARSE_FAILURE.getMessage());
		}
	}

	/**
	 * This method is used to send message back to the client using the
	 * BufferedWriter and the respected string.
	 * 
	 * @param output
	 * @param bufferedWriter
	 * @throws IOException
	 */
	private void sendResponseToClient(String output, BufferedWriter bufferedWriter) throws IOException {
		bufferedWriter.write(output + StringUtils.LF);
		bufferedWriter.flush();
	}
}
