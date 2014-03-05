package com.muhammadHijazi.project1.mailHandler;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.muhammadHijazi.project1.UI.EMessage;

/*
 * Acts as a relay between the GUI and the SMTP socket. Handles sending order, sending state.
 * Also handles the message itself, and not just the message details
 * States are as follows
 * 0: no connection
 * 1: connected to server
 * 2: HELO successful 
 * 3: MAIL FROM successful
 * 4: RCPT successful
 * 5: DATA successful
 * 6: QUIT successful (done)
 */
public class Envelope {
	SMTPHandler server;
	// strings passed from GUI
	private String messageSender, localMailServ, messageText, message;
	// all our recipients will be placed in this array
	private String[] messageRec, messageCc;
	/*
	 * The current state, this will be passed to a diffrent GUI later to
	 * represent the state on a progress bar.
	 */
	private int state;

	public Envelope(String mailServer, String sender, String receiver,
			String Cc, String message) {
		// Initialize all variables
		localMailServ = mailServer;

		// creates an array of recipients, splitting whenever a ; character
		// appears
		messageRec = receiver.split(";");
		messageCc = Cc.split(";");

		messageSender = sender;
		messageText = message;

		state = 0; // state 0, variables initialized, no connection
	}

	public void sendMessage() throws Exception {
		// Initialize connection to mail server
		server = new SMTPHandler(localMailServ);
		// test to make sure the socket has connected correctly, and that the
		// appropriate
		// code was received
		if (server.isConnected() && server.readFromServer().equals("220")) {
			// We are connected, set state to 1
			state = 1;

			try {
				// Hand shake
				server.writeToServer("HELO mail");

				// check server output
				errorCheck(server.readFromServer());

				// Identify the sender
				server.writeToServer("MAIL FROM: <" + messageSender + ">");
				errorCheck(server.readFromServer());

				// specify the recipient
				for (int i = 0; i < messageRec.length; i++) {
					server.writeToServer("RCPT TO: <" + messageRec[i].trim()
							+ ">");
					errorCheck(server.readFromServer());
				}
				if (!messageCc[0].equals("")) {
					for (int i = 0; i < messageCc.length; i++) {
						System.out.println(messageCc[i]);
						server.writeToServer("RCPT TO: <" + messageCc[i].trim()
								+ ">");
						errorCheck(server.readFromServer());
					}
				}
				// Start passing the message
				server.writeToServer("DATA");
				errorCheck(server.readFromServer());

				// Header Generation, each adds a header to
				// message string
				addMessageSender(messageSender);
				addMessageRecipents(messageRec);
				addMessageCc(messageCc);
				addMessageDate();

				// Add the message text to message string
				message = message + messageText;
				server.writeToServer(message);

				// End the DATA field
				server.writeToServer(".");
				errorCheck(server.readFromServer());
				
				// Exit
				server.writeToServer("QUIT");
				errorCheck(server.readFromServer());
				// Let the user know that the message has been sent, and close
				// the program after they have hit OK.
				new EMessage("Message Sent!", true).setVisible(true);
				return;
			} catch (ErrorCodeException ec) {
				// Let the user know an error has occurred
				new EMessage(
						"An Error has occured, please check your inputs and try again",
						false);
				// this is for my own sake mostly, will remove later
				// TODO: get rid of this when you are done
				System.out.println("Error code: " + ec.getECode());
				// Halt the connection with the server
				server.writeToServer("QUIT");
				// Return to the GUI
				return;
			} catch (Exception e) {
				// Shit Happens
				System.out.println("I don't even know");
			}
		}
	}

	public void errorCheck(String code) throws Exception {
		// if the code starts with a 2 or a 3, then our last input was accepted
		// and we can move on
		if (code.startsWith("2") || code.startsWith("3")) {
			// we incriment the state as the program progresses from here
			// state++;
		} else {
			// if not we let the user know something is wrong
			throw new ErrorCodeException(code);
		}
	}

	private void addMessageSender(String sender) {
		message = "From: " + sender + "\n";
	}

	private void addMessageRecipents(String[] rcp) {
		message = message + "To: " + rcp[0];
		for (int i = 1; i < rcp.length; i++) {
			message = message + ", " + rcp[i];
		}
		message = message + "\n";

	}

	private void addMessageCc(String[] messageCc2) {
		if (messageCc.length > 0) {
			message = message + "Cc: " + messageCc[0];
			for (int i = 1; i < messageCc.length; i++) {
				message = message + ", " + messageCc[i];
			}
			message = message + "\n";
		}
	}

	private void addMessageDate() {
		DateFormat dateFormat = DateFormat.getDateTimeInstance();
		Calendar date = Calendar.getInstance();

		message = message + "Date: " + dateFormat.format(date.getTime()) + "\n";

	}

	public void forceClose() throws IOException {
		server.close();
	}

	public int getState() {
		return state;
	}

	public String[] getMessageRec() {
		return messageRec;
	}

	public void setMessageRec(String messageRec[]) {
		this.messageRec = messageRec;
	}

	public String getMessageSender() {
		return messageSender;
	}

	public void setMessageSender(String messageSender) {
		this.messageSender = messageSender;
	}

	public String getLocalMailServ() {
		return localMailServ;
	}

	public void setLocalMailServ(String localMailServ) {
		this.localMailServ = localMailServ;
	}

	public String getMessageText() {
		return messageText;
	}

	public void setMessageText(String messageText) {
		this.messageText = messageText;
	}
}
