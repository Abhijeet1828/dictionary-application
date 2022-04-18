package com.distributed.project.dictionary.client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.distributed.project.dictionary.client.dto.DictionaryDto;
import com.distributed.project.dictionary.client.utils.Constants;
import com.distributed.project.dictionary.client.utils.RequestProcessingUtils;
import com.distributed.project.dictionary.client.utils.ResponseProcessingUtils;
import com.distributed.project.dictionary.client.utils.TypeConversionUtils;

/**
 * This class is the main class for running dictionary client without the UI.
 * 
 * @author Abhijeet - 1278218
 *
 */
public class DictionaryTerminalClient {

	private static final Logger LOGGER = LoggerFactory.getLogger(DictionaryTerminalClient.class);

	public static void main(String[] args) {
		DictionaryTerminalClient dictionaryClient = new DictionaryTerminalClient();
		dictionaryClient.clientConfigurations(args[0], Integer.parseInt(args[1]));
	}

	public void clientConfigurations(String serverAddress, int port) {
		try (Socket socket = new Socket(serverAddress, port)) {
			clientConnection(socket);
		} catch (UnknownHostException e) {
			LOGGER.error("UnknownHostException in clientConfigurations", e);
		} catch (IOException e) {
			LOGGER.error("IOException in clientConfigurations", e);
		}
	}

	public void clientConnection(Socket socket) {
		try (BufferedReader in = new BufferedReader(
				new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
				BufferedWriter out = new BufferedWriter(
						new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8))) {

			Scanner scanner = new Scanner(System.in);
			String clientInput = null;

			LOGGER.info("Enter option for actions {}", Constants.OPTION_MAP);
			while (!(clientInput = scanner.nextLine()).equals(Constants.OPTION_MAP.inverse().get(Constants.EXIT))) {
				DictionaryDto request = RequestProcessingUtils.optionBasedRequest(clientInput, scanner);

				if (StringUtils.isNotBlank(request.getMessage())) {
					LOGGER.error(request.getMessage());
				} else {
					LOGGER.info("Sending Request {}", request);
					out.write(TypeConversionUtils.convertObjectToString(request) + StringUtils.LF);
					out.flush();

					String response = in.readLine();
					LOGGER.info("Received Output {}", response);
					ResponseProcessingUtils.processResponse(response);
				}

				LOGGER.info("Enter option for actions {}", Constants.OPTION_MAP);
			}

			scanner.close();
		} catch (IOException e) {
			LOGGER.error("IOException in clientConnection", e);
		}
	}

}