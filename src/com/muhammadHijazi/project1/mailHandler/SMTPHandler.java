package com.muhammadHijazi.project1.mailHandler;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

import com.muhammadHijazi.project1.UI.EMessage;
/*
 * Handles socket connection
 */
public class SMTPHandler {
	//SMPT ports are usually 25, however they can be other ports
	//this may need to be accounted for later on, but for now
	//it is outside the scope of this project
	private static final int SMTP_PORT = 25;
	//our time out time in MS, 1000ms = 1sec
	private static final int TIMEOUT = 1000;
	//our Socket
	private Socket mailSocket;
	//information from server, we use scanner because we are really
	//only intreasted in the return codes that the server provides
	//and scanner allowes us to work with tokens more easily
	private Scanner serverRead;
	//our output to the server
	private DataOutputStream serverWrite;
	//our return code string
	private String code;

	public SMTPHandler(String server) {
		//we need a time out for our socket connection
		//to do this, we first initilize our socket
		mailSocket = new Socket();
		try {
			//and then we initlize the connecton using the connect method
			// this method takes an InetSocket and a time out int
			mailSocket.connect(new InetSocketAddress(server, SMTP_PORT), TIMEOUT);
			//if we connected, this is where we intiilize our reader and writer
			if (mailSocket.isConnected()) {
				serverRead = new Scanner(new InputStreamReader(
						mailSocket.getInputStream()));
				serverWrite = new DataOutputStream(mailSocket.getOutputStream());
			}
		} catch (IOException e) {
			//If we time out or something goes wrong
			new EMessage(
					"Unable to connect to server! Please check your input and your internet connecton and try again.",
					false).setVisible(true);
		}
	}
	//method used to write to server
	public void writeToServer(String write) throws IOException {
		String out = write;
		//check to see if what we wants to write ends with a
		//new line character
		if (out.endsWith("\n")) {
			//if it does, send it
			serverWrite.writeBytes(out);
		} else {
			//if it doesnt, add one and send it
			serverWrite.writeBytes(out + "\n");
		}
		//flush our writer just in case
		serverWrite.flush();
	}
	
	//Method for reading from server
	public String readFromServer() {
		//get the next token (which should be our code) from the server
		code = serverRead.next();
		//get the next line, this isn't saved and is simply read so that we can
		//bet the next code on the next read
		serverRead.nextLine();
		//return the code
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
