package com.muhammadHijazi.project1.mailHandler;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

import com.muhammadHijazi.project1.UI.EMessage;

public class SMTPHandler {

	private static final int SMTP_PORT = 25;

	private Socket mailSocket;
	private Scanner serverRead;
	private DataOutputStream serverWrite;
	private String code;

	public SMTPHandler(String server) {
		mailSocket = new Socket();
		try {
			mailSocket.connect(new InetSocketAddress(server, SMTP_PORT), 1000);
			if (mailSocket.isConnected()) {
				serverRead = new Scanner(new InputStreamReader(
						mailSocket.getInputStream()));
				serverWrite = new DataOutputStream(mailSocket.getOutputStream());
			}
		} catch (IOException e) {
			new EMessage(
					"Unable to connect to server! Please check your input and your internet connecton and try again.",
					false).setVisible(true);
		}
	}

	public void writeToServer(String write) throws IOException {
		String out = write;
		if (out.contains("\n")) {
			serverWrite.writeBytes(out);
		} else {
			serverWrite.writeBytes(out + "\n");
		}
		serverWrite.flush();
	}

	public String readFromServer() {
		code = serverRead.next();
		serverRead.nextLine();
		return code;
	}

	public boolean isConnected() {
		return mailSocket.isConnected();
	}

	public void close() throws IOException {
		mailSocket.close();
		System.out.println("Closed");
	}

}
