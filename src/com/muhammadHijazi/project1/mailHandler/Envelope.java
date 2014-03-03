package com.muhammadHijazi.project1.mailHandler;

import java.io.IOException;

import com.muhammadHijazi.project1.UI.EMessage;

/*
 * acts as a relay between the GUI and the SMTP socket. Handles sending order, sending state.
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
		server = new SMTPHandler(localMailServ);
		if (server.isConnected() && server.readFromServer().equals("220")) {
			state = 1;

			try {
				server.writeToServer("HELO mail");
				errorCheck(server.readFromServer());
				server.writeToServer("MAIL FROM: <" + messageSender + ">");
				errorCheck(server.readFromServer());
				server.writeToServer("RCPT TO: <" + messageRec + ">");
				errorCheck(server.readFromServer());
				server.writeToServer("DATA");
				errorCheck(server.readFromServer());
				if (!messageSubject.isEmpty()) {
					server.writeToServer("Subject: " + messageSubject);
				}
				server.writeToServer(messageText);
				server.writeToServer(".");
				errorCheck(server.readFromServer());
				server.writeToServer("QUIT");
				errorCheck(server.readFromServer());
				new EMessage("Message Sent!", true).setVisible(true);
				return;
			} catch (ErrorCodeException ec) {
				System.out.println("Error code: " + ec.getECode());
				server.writeToServer("QUIT");
				return;
			} catch (Exception e) {
				System.out.println("I don't even know");
			}
		}
	}

	public void errorCheck(String code) throws Exception {
		if (!(code.startsWith("2") || code.startsWith("3"))) {
			throw new ErrorCodeException(code);
		} else {
			state++;
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
