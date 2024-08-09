
/**
 * StreamClient Class
 * 
 * CPSC 441
 * Assignment 1
 *Abrar Zawad SAfwan	
 *30150892
 */

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.logging.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.Scanner;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.FileInputStream;
import java.util.concurrent.TimeUnit;

public class StreamClient {

	// Variables
	private String serverName;
	private int serverPort;
	private int bufferSize;
	private byte serviceCode;
	private String inName;
	private String outName;

	// Executor Service for threads
	private ExecutorService executor;

	private static final Logger logger = Logger.getLogger("StreamClient"); // global logger

	/**
	 * Constructor to initialize the class.
	 * 
	 * @param serverName remote server name
	 * @param serverPort remote server port number
	 * @param bufferSize buffer size used for read/write
	 */
	public StreamClient(String serverName, int serverPort, int bufferSize) {
		this.serverName = serverName;
		this.serverPort = serverPort;
		this.bufferSize = bufferSize;

		executor = Executors.newFixedThreadPool(2);
	}

	/**
	 * Compress the specified file via the remote server.
	 * 
	 * @param inName  name of the input file to be processed
	 * @param outName name of the output file
	 */
	public void getService(int serviceCode, String inName, String outName) {

		// 1:Open a TCP socket to communicate with the server
		try {
			Socket socket = new Socket(serverName, serverPort);
			System.out.println("Connection is Succesfull.");

			// 2: Open the input and output files
			FileInputStream file_InputStream = new FileInputStream(inName);
			FileOutputStream file_OutputStream = new FileOutputStream(outName);
			OutputStream socket_Output = socket.getOutputStream();
			InputStream socket_Input = socket.getInputStream();

			// 3: Send the requested service code to the server
			socket_Output.write(serviceCode);
			socket_Output.flush();

			// 4: and 5
			executor.execute(new Write(file_InputStream, socket, bufferSize, socket_Output));
			executor.execute(new Read(socket_Input, file_OutputStream, bufferSize));

			executor.shutdown();
			try {
				if (executor.awaitTermination(1, TimeUnit.SECONDS))
					;
				executor.shutdown();
			} catch (InterruptedException e) {
				executor.shutdownNow();
			}
			try {
				// 6: Clean up
				file_InputStream.close();
				file_OutputStream.close();
				socket.close();
				System.out.println("Streams are closed.");
			} catch (Exception e) {
				e.printStackTrace();
			}

		} // first try ening
		catch (Exception e) {
			e.printStackTrace();
		}

	}// get service ending
}
