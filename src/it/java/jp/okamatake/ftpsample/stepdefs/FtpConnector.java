package jp.okamatake.ftpsample.stepdefs;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;

import cucumber.api.java.After;

public class FtpConnector {

	private ControlConnection controlConnection;

	private int dataPort = -1;

	public String latestMessage;

	@After
	public void destroy() {
		if (controlConnection != null) {
			try {
				controlConnection.close();
			} catch (IOException e) {
			}
		}
	}

	public void connect(int port) throws Exception {
		controlConnection = new ControlConnection("127.0.0.1", port);
		latestMessage = receive();
	}

	public void send(String command) throws IOException {
		controlConnection.request.println(command);
		latestMessage = receive();
	}

	public String receive() throws IOException {
		return controlConnection.response.readLine();
	}

	public void setDataPort(String host, List<String> portChunks) throws UnknownHostException, IOException {
		dataPort = getPort(portChunks);
	}

	private int getPort(List<String> portChunks) {
		return Integer.parseInt(portChunks.get(0), 10) * 256 + Integer.parseInt(portChunks.get(1), 10);
	}

	public void upload(InputStream input) throws IOException {
		if (dataPort == -1) {
			throw new IllegalStateException("dataPortが未設定。");
		}

		try (DataConnection dataConnection = new DataConnection("127.0.0.1", dataPort)) {
			int n = 0;
			byte[] buffer = new byte[1024];
			while ((n = input.read(buffer)) != -1) {
				dataConnection.request.write(buffer, 0, n);
			}
			dataConnection.request.flush();
		}

		latestMessage = receive();
	}

	private static class ControlConnection implements Closeable {
		private final Socket connection;
		private final PrintWriter request;
		private final BufferedReader response;

		public ControlConnection(String host, int port) throws IOException {
			connection = new Socket(host, port);
			request = new PrintWriter(connection.getOutputStream(), true);
			response = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		}

		@Override
		public void close() throws IOException {
			if (request != null) {
				request.close();
			}
			if (response != null) {
				try{
					response.close();
				} catch (IOException e) {
				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (IOException e) {
				}
			}
		}
	}

	private static class DataConnection implements Closeable {
		private final Socket connection;
		private final OutputStream request;
		private final InputStream response;

		public DataConnection(String host, int port) throws IOException {
			connection = new Socket(host, port);
			request = connection.getOutputStream();
			response = connection.getInputStream();
		}

		@Override
		public void close() throws IOException {
			if (request != null) {
				request.close();
			}
			if (response != null) {
				try{
					response.close();
				} catch (IOException e) {
				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (IOException e) {
				}
			}
		}
	}
}
