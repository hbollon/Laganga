package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

public class AnnuleEvent implements ActionListener {

	private JFrame f;
	
	public AnnuleEvent(JFrame f) {
		this.f = f;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		f.dispose();
	}
}
