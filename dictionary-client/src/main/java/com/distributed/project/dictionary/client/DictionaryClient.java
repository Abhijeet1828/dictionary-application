package com.distributed.project.dictionary.client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.SwingUtilities;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.distributed.project.dictionary.client.dto.DictionaryDto;
import com.distributed.project.dictionary.client.utils.Constants;
import com.distributed.project.dictionary.client.utils.RequestProcessingUtils;
import com.distributed.project.dictionary.client.utils.TypeConversionUtils;

/**
 * This is main class which initialized the Client UI and also connects, sends
 * and receives response from the server.
 * 
 * @author Abhijeet - 1278218
 *
 */
public class DictionaryClient implements Serializable {

	private static final long serialVersionUID = 3058985667743373514L;

	private static final Logger LOGGER = LoggerFactory.getLogger(DictionaryClient.class);

	protected DictionaryClientUI clientFrame;
	protected Socket socket;
	protected BufferedReader in;
	protected BufferedWriter out;
	protected Map<String, List<Long>> responseTimeMap = new HashMap<>();

	/**
	 * Main method which is executed on start of the project.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// Initializing dictionary client
		DictionaryClient dictionaryClient = new DictionaryClient();
		
		// Initializing DictonaryClientUI 
		dictionaryClient.clientFrame = new DictionaryClientUI(dictionaryClient);

		// Running UI in separate thread and making it visible
		SwingUtilities.invokeLater(() -> dictionaryClient.clientFrame.setVisible(true));

		// Invoking client connections with server
		dictionaryClient.clientConfigurationsAndConnection(args[0], Integer.parseInt(args[1]));
	}

	/**
	 * This method is used to initialize the socket with server address and the
	 * port. It also initializes the input and output buffered stream to exchange
	 * request and response with server.
	 * 
	 * @param serverAddress
	 * @param port
	 */
	protected void clientConfigurationsAndConnection(String serverAddress, int port) {
		try {
			// Opening socket with dictionary server
			socket = new Socket(serverAddress, port);

			// Input connection - messages from server
			in = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));

			// Output connection - messages to server
			out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8));
		} catch (IOException e) {
			LOGGER.error("IOException in clientConfigurations", e);
			clientFrame.addErrorPopUp(Constants.ERROR_UNKNOWN_HOST, true);
		}
	}

	/**
	 * This method is used to create request using the arguments to send to the
	 * server. It publishes the request using the {@link BufferedWriter} and then
	 * reads the response using the {@link BufferedReader}.
	 * 
	 * @implSpec This method also calculates the response time to display to the
	 *           user.
	 * 
	 * @param searchKey
	 * @param word
	 * @param meaning
	 * @param secondMeaning
	 * @param action
	 * 
	 * @return {@link DictionaryDto}
	 */
	public DictionaryDto handleRequestAndResponse(String searchKey, String word, String meaning, String secondMeaning,
			String action) {
		// Converting method parameters to request
		DictionaryDto request = RequestProcessingUtils.optionBasedRequest(searchKey, word, meaning, secondMeaning,
				action);
		request.setResponseTimeMap(responseTimeMap);
		LOGGER.info("Sending Request {}", request);
		try {
			// Capturing the start time of request
			long startTime = System.currentTimeMillis();

			// Sending the request to the server
			out.write(TypeConversionUtils.convertObjectToString(request) + StringUtils.LF);
			out.flush();

			// If client clicks on exit then show exit pop-up
			if (Constants.EXIT.equalsIgnoreCase(action)) {
				clientFrame.addExitPopUp();
			}

			// Reading the response
			String response = in.readLine();

			// Capturing the end time of request
			long endTime = System.currentTimeMillis();
			LOGGER.info("Received Response {}", response);

			// Handling response from server - Both error and success
			DictionaryDto dictionaryResponse = null;
			if (StringUtils.isEmpty(response)) {
				LOGGER.error("Error response from server");
				dictionaryResponse = new DictionaryDto(request.getAction(), null, null, null, null, false,
						Constants.ERROR_COMMUNICATION);
			} else {
				LOGGER.info("Success response from server");
				dictionaryResponse = TypeConversionUtils.convertToCustomClass(response, DictionaryDto.class);
			}

			// Capturing the response time
			dictionaryResponse.setTimeTaken(endTime - startTime);

			// Storing the response time in map for graph display
			storeResponseTimes(dictionaryResponse.getAction(), Long.valueOf(endTime - startTime));

			// Returning the response
			return dictionaryResponse;
		} catch (IOException e) {
			LOGGER.error("IOException in handleRequestAndResponse", e);
			clientFrame.addErrorPopUp(Constants.ERROR_COMMUNICATION, true);
			return null;
		}
	}

	/**
	 * This method is used to store the response time for each action in a map.
	 * 
	 * @implNote If responseTimeMap is empty or does not contain the operation key,
	 *           then add new or else add the response time to the respective
	 *           action's response time list.
	 * 
	 * @param action
	 * @param responseTime
	 */
	private void storeResponseTimes(String action, Long responseTime) {
		if (MapUtils.isEmpty(responseTimeMap) || !responseTimeMap.containsKey(action)) {
			List<Long> timeList = new ArrayList<>();
			timeList.add(responseTime);
			responseTimeMap.put(action, timeList);
		} else if (responseTimeMap.containsKey(action)) {
			List<Long> timeList = responseTimeMap.get(action);
			timeList.add(responseTime);
			responseTimeMap.put(action, timeList);
		}
	}

	/**
	 * This method returns the response time map for graph panel
	 * 
	 * @return
	 */
	public Map<String, List<Long>> getResponseTimeMap() {
		return responseTimeMap;
	}

}
