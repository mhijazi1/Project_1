package com.muhammadHijazi.project1.UI;

import javax.swing.JFrame;
import javax.swing.JProgressBar;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;

import com.muhammadHijazi.project1.mailHandler.Envelope;

public class SMTPProgress extends JFrame {
	public SMTPProgress(Envelope e) {

		setSize(500, 100);
		setLocationRelativeTo(null);

		JProgressBar progressBar = new JProgressBar();
		progressBar.setValue(e.getState() * (100 / 6));

		try {
			e.sendMessage();
		} catch (Exception e1) {
			System.out.println("error2");
		}

		progressBar.repaint();
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(
				Alignment.LEADING).addGroup(
				groupLayout
						.createSequentialGroup()
						.addContainerGap()
						.addComponent(progressBar, GroupLayout.DEFAULT_SIZE,
								474, Short.MAX_VALUE).addContainerGap()));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(
				Alignment.LEADING).addGroup(
				groupLayout
						.createSequentialGroup()
						.addGap(30)
						.addComponent(progressBar, GroupLayout.PREFERRED_SIZE,
								GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addContainerGap(27, Short.MAX_VALUE)));
		getContentPane().setLayout(groupLayout);

	}
}
