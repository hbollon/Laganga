package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

public class CloseWindowButtonListener implements ActionListener {
	private JFrame window;
	
	public CloseWindowButtonListener(JFrame window) {
		this.window = window;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		window.dispose();
	}
}