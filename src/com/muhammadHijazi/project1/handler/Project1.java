package com.muhammadHijazi.project1.handler;

import com.muhammadHijazi.project1.UI.NewMessage;

/*
 * Muhammad Hijazi 2014
 * 
 * Basic SMTP mail program, v0.5
 * 
 * This class Handles initialization of the program, 
 * in this case it starts the NewMessage
 * GUI and makes it visible. 
 */
public class Project1 {
	public static void main(String args[]) {
		new NewMessage().setVisible(true);

	}
}
