package com.muhammadHijazi.project1.UI;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextArea;

import javax.swing.JButton;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;

import com.muhammadHijazi.project1.mailHandler.Envelope;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class NewMessage extends JFrame {
	private JTextField txtTo;
	private JTextField txtFrom;
	private JTextField txtLocalMailserver;
	private JTextField txtSubject;
	private JTextArea txtrMessage;

	public NewMessage() {

		this.setLocationRelativeTo(null);
		this.setSize(750, 750);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		txtLocalMailserver = new JTextField();
		txtLocalMailserver.setToolTipText("Local mailserver");
		txtLocalMailserver.setColumns(10);
		txtLocalMailserver.setText("Local mailserver");

		textHide(txtLocalMailserver, "Local mailserver");

		txtFrom = new JTextField();
		txtFrom.setToolTipText("From");
		txtFrom.setText("From");
		txtFrom.setColumns(10);

		textHide(txtFrom, "From");

		this.txtTo = new JTextField();
		txtTo.setToolTipText("To");
		txtTo.setText("To");
		this.txtTo.setColumns(10);

		textHide(txtTo, "To");

		txtSubject = new JTextField();
		txtSubject.setToolTipText("Subject");
		txtSubject.setText("Subject");
		txtSubject.setColumns(10);

		textHide(txtSubject, "Subject");

		txtrMessage = new JTextArea();
		txtrMessage.setLineWrap(true);

		txtrMessage.setWrapStyleWord(true);

		JScrollPane scroll = new JScrollPane(txtrMessage);
		getContentPane().add(scroll);

		JButton btnSend = new JButton("Send");
		btnSend.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				while (true) {

					if (txtLocalMailserver.getText().equals(
							txtLocalMailserver.getToolTipText())
							|| txtLocalMailserver.getText().isEmpty()) {
						new EMessage("Please input a valid mail server", false)
								.setVisible(true);
						return;
					} else if (txtFrom.getText().equals(
							txtFrom.getToolTipText())
							|| txtFrom.getText().isEmpty()) {
						new EMessage("Please input a valid Sender", false)
								.setVisible(true);
						return;
					} else if (txtTo.getText().equals(txtTo.getToolTipText())
							|| txtTo.getText().isEmpty()) {
						new EMessage("Please input a valid Recipent", false)
								.setVisible(true);
						return;
					} else {
						break;
					}
				}
				if (txtLocalMailserver.getText().equals(
						txtLocalMailserver.getToolTipText())
						|| txtLocalMailserver.getText().isEmpty()) {
					txtSubject.setText("");
				}
				Envelope en = new Envelope(txtLocalMailserver.getText(), txtTo
						.getText(), txtFrom.getText(), txtrMessage.getText(),
						txtSubject.getText());

				try {
					// new SMTPProgress(
					en.sendMessage();
					// ).setVisible(true);
				} catch (Exception e1) {
					System.out.println("Error");
				} finally {
					// dispose();
				}
			}
		});

		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout
				.setHorizontalGroup(groupLayout
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								groupLayout
										.createSequentialGroup()
										.addGap(12)
										.addGroup(
												groupLayout
														.createParallelGroup(
																Alignment.LEADING)
														.addGroup(
																groupLayout
																		.createSequentialGroup()
																		.addComponent(
																				scroll,
																				GroupLayout.DEFAULT_SIZE,
																				724,
																				Short.MAX_VALUE)
																		.addContainerGap())
														.addGroup(
																groupLayout
																		.createSequentialGroup()
																		.addGroup(
																				groupLayout
																						.createParallelGroup(
																								Alignment.TRAILING)
																						.addComponent(
																								btnSend,
																								Alignment.LEADING,
																								GroupLayout.PREFERRED_SIZE,
																								117,
																								GroupLayout.PREFERRED_SIZE)
																						.addComponent(
																								txtLocalMailserver,
																								Alignment.LEADING,
																								GroupLayout.DEFAULT_SIZE,
																								711,
																								Short.MAX_VALUE)
																						.addComponent(
																								txtFrom,
																								Alignment.LEADING,
																								GroupLayout.DEFAULT_SIZE,
																								711,
																								Short.MAX_VALUE)
																						.addComponent(
																								txtTo,
																								Alignment.LEADING,
																								GroupLayout.DEFAULT_SIZE,
																								711,
																								Short.MAX_VALUE)
																						.addComponent(
																								txtSubject,
																								Alignment.LEADING,
																								GroupLayout.DEFAULT_SIZE,
																								711,
																								Short.MAX_VALUE))
																		.addGap(25)))));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(
				Alignment.LEADING).addGroup(
				groupLayout
						.createSequentialGroup()
						.addGap(6)
						.addComponent(btnSend, GroupLayout.PREFERRED_SIZE, 19,
								GroupLayout.PREFERRED_SIZE)
						.addGap(12)
						.addComponent(txtLocalMailserver,
								GroupLayout.PREFERRED_SIZE,
								GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addGap(11)
						.addComponent(txtFrom, GroupLayout.PREFERRED_SIZE,
								GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addGap(12)
						.addComponent(txtTo, GroupLayout.PREFERRED_SIZE,
								GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addGap(13)
						.addComponent(txtSubject, GroupLayout.PREFERRED_SIZE,
								GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addGap(24)
						.addComponent(scroll, GroupLayout.DEFAULT_SIZE, 511,
								Short.MAX_VALUE).addGap(37)));
		groupLayout.setAutoCreateGaps(true);
		groupLayout.setAutoCreateContainerGaps(true);
		getContentPane().setLayout(groupLayout);

	}

	private void textHide(final JTextField text, final String original) {

		text.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				if (text.getText().length() == 0)
					text.setText(original);
			}
		});

		text.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if (text.getText().equals(original))
					text.setText(null);
			}
		});
	}
}
