package com.distributed.project.dictionary.server;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.swing.SwingUtilities;

import org.apache.commons.collections4.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This is the main class which initializes the Server UI and also starts
 * listening on the specified port for client.
 * 
 * @author Abhijeet - 1278218
 *
 */
public class DictionaryServer {

	private static final Logger LOGGER = LoggerFactory.getLogger(DictionaryServer.class);

	private Map<Integer, Socket> clientSocketMap = new ConcurrentHashMap<>();
	private Map<String, List<Long>> responseTimeMap = new ConcurrentHashMap<>();

	protected DictionaryServerUI serverFrame;

	/**
	 * This is the main method which is initialized on start of the project.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			// Initializing the dictionary server and the server UI
			DictionaryServer dictionaryServer = new DictionaryServer();
			dictionaryServer.serverFrame = new DictionaryServerUI(dictionaryServer);

			// Running UI in a seperate thread.
			SwingUtilities.invokeLater(() -> dictionaryServer.serverFrame.setVisible(true));

			// Invoking the server configurations which starts listening on the port
			dictionaryServer.serverConfigurations(Integer.parseInt(args[0]), args[1]);
		} catch (Exception e) {
			LOGGER.error("Exception in Main method", e);
		}
	}

	/**
	 * This method is used to open sockets for client connections and then assign
	 * threads to them.
	 * 
	 * @param port
	 * @param dictionaryFileLocation
	 */
	public void serverConfigurations(int port, String dictionaryFileLocation) {
		// Executor service which creates a pool of 5 threads with work queue for
		// queuing requests.
		ExecutorService executorService = new ThreadPoolExecutor(5, 10, 100, TimeUnit.MILLISECONDS,
				new ArrayBlockingQueue<>(5), new ThreadPoolExecutor.CallerRunsPolicy());

		// To track no of clients
		int noOfClients = 0;

		// Client socket initialization
		Socket clientSocket = null;

		// Starting the server socket and running in infinite loop
		try (ServerSocket server = new ServerSocket(port)) {
			while (true) {
				LOGGER.info("Waiting for connection on port {}", port);

				// Accepting client requests on port
				clientSocket = server.accept();

				// Increasing count of number of clients
				noOfClients++;

				// Adding clients to clientSocketMap
				clientSocketMap.put(noOfClients, clientSocket);

				LOGGER.info("Connection estabilished with Client Number {}, Hostname {}, Local Port {}", noOfClients,
						clientSocket.getInetAddress().getHostName(), clientSocket.getLocalPort());

				// Initializing the runnable dictionary utils
				DictionaryUtils dictionaryUtils = new DictionaryUtils(this, clientSocket, noOfClients,
						dictionaryFileLocation);

				// Assigning thread to a client of dictionary utils runnable
				executorService.execute(dictionaryUtils);
			}
		} catch (Exception e) {
			LOGGER.error("Exception in serverConfiguration", e);
		}
	}

	/**
	 * This method is used to fetch the client socket map
	 * 
	 * @return
	 */
	public synchronized Map<Integer, Socket> getClientSocketMap() {
		return clientSocketMap;
	}

	/**
	 * This method is used to fetch the response time map.
	 * 
	 * @return
	 */
	public synchronized Map<String, List<Long>> getResponseTimeMap() {
		return responseTimeMap;
	}

	/**
	 * This method is used to combine the existing response time map with the
	 * respective client's response time map by adding the response time list in the
	 * existing response list for respective actions.
	 * 
	 * @param clientResponseTimeMap
	 */
	public synchronized void addToResponseTimeMap(Map<String, List<Long>> clientResponseTimeMap) {
		if (MapUtils.isNotEmpty(clientResponseTimeMap)) {
			Map<String, List<Long>> combinedMap = Stream.of(getResponseTimeMap(), clientResponseTimeMap)
					.flatMap(map -> map.entrySet().stream())
					.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (v1, v2) -> Stream.of(v1, v2)
							.flatMap(Collection::stream).distinct().collect(Collectors.toList())));
			responseTimeMap.putAll(combinedMap);
		}
	}
}
