package com.muhammadHijazi.project1.mailHandler;

import java.io.IOException;

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
	private String messageRec, messageSender, localMailServ, messageSubject,
			messageText;
	/*
	 * The current state, this will be passed to a diffrent GUI later to
	 * represent the state on a progress bar.
	 */
	private int state;
	// used later to check and see if the message has a subject
	private boolean hasSubject;

	// two possible constructors

	// constructor 1: w/ subject
	public Envelope(String mailServer, String receiver, String sender,
			String message, String subject) {
		// Initialize all variables
		localMailServ = mailServer;
		messageRec = receiver;
		messageSender = sender;
		messageText = message;
		messageSubject = subject;
		state = 0; // state 0, variables initialized, no connection
		hasSubject = true; // a subject is given, so we set this to true
	}

	// constructor 2: w/o subject
	public Envelope(String mailServer, String receiver, String sender,
			String message) {
		// Initialize all variables
		localMailServ = mailServer;
		messageRec = receiver;
		messageSender = sender;
		messageText = message;
		state = 0; // state 0, variables initialized, no connection
		hasSubject = false; // no subject, so we set this to false
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
				server.writeToServer("RCPT TO: <" + messageRec + ">");
				errorCheck(server.readFromServer());
				// Start passing the message
				server.writeToServer("DATA");
				errorCheck(server.readFromServer());
				// If a Subject was specified, add it into the data field
				if (!messageSubject.isEmpty()) {
					server.writeToServer("Subject: " + messageSubject);
				}
				// Add the message text
				server.writeToServer(messageText);
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
				// Let the user know an error has occured
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
		//if the code starts with a 2 or a 3, then our last input was accepted and we can move on
		if (code.startsWith("2") || code.startsWith("3")) {
			//we incriment the state as the program progresses from here
			state++;
		} else {
			// if not we let the user know something is wrong
			throw new ErrorCodeException(code);
		}
	}

	public void forceClose() throws IOException {
		server.close();
	}

	public int getState() {
		return state;
	}

	public String getMessageRec() {
		return messageRec;
	}

	public void setMessageRec(String messageRec) {
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

	public String getMessageSubject() {
		if (hasSubject)
			return messageSubject;
		else
			return "";
	}

	public void setMessageSubject(String messageSubject) {
		this.messageSubject = messageSubject;
		hasSubject = true;
	}

	public String getMessageText() {
		return messageText;
	}

	public void setMessageText(String messageText) {
		this.messageText = messageText;
	}
}
