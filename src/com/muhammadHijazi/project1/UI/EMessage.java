package com.muhammadHijazi.project1.UI;

import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;

import java.awt.Window;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.UIManager;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import java.awt.Font;
import javax.swing.JTextPane;

/*
 * Creates an error message. Message is passed to constructor. 
 */
public class EMessage extends JFrame {
	private Window[] windows;

	// dispose decides if all open windows will dispose after button press.
	public EMessage(String message, final Boolean dispose) {
		setResizable(false);
		this.setSize(500, 100);
		this.setLocationRelativeTo(null);
		JButton btnNewButton = new JButton("OK");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (dispose) {
					// gets all open windows and disposes of them
					windows = new Window[java.awt.Window.getWindows().length];
					windows = java.awt.Window.getWindows();
					for (int i = 0; i < windows.length; i++) {
						windows[i].dispose();

					}
				} else {
					dispose();
				}
			}

		});

		JTextPane txtpnTextpane = new JTextPane();
		txtpnTextpane.setFont(new Font("Dialog", Font.BOLD, 12));
		txtpnTextpane.setEditable(false);
		txtpnTextpane.setBackground(UIManager.getColor("Button.background"));
		txtpnTextpane.setText(message);

		StyledDocument doc = txtpnTextpane.getStyledDocument();
		SimpleAttributeSet center = new SimpleAttributeSet();
		StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
		doc.setParagraphAttributes(0, doc.getLength(), center, false);

		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(groupLayout
				.createParallelGroup(Alignment.TRAILING)
				.addGroup(
						groupLayout.createSequentialGroup().addContainerGap()
								.addComponent(btnNewButton).addGap(220))
				.addGroup(
						Alignment.LEADING,
						groupLayout
								.createSequentialGroup()
								.addContainerGap()
								.addComponent(txtpnTextpane,
										GroupLayout.PREFERRED_SIZE, 475,
										GroupLayout.PREFERRED_SIZE)
								.addContainerGap(GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE)));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(
				Alignment.LEADING).addGroup(
				groupLayout
						.createSequentialGroup()
						.addComponent(txtpnTextpane,
								GroupLayout.PREFERRED_SIZE, 35,
								GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(btnNewButton).addGap(5)));
		getContentPane().setLayout(groupLayout);
	}
}
