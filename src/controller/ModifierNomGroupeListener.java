package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;

import model.entities.Group;

public class ModifierNomGroupeListener implements ActionListener {
	private Group group;
	private JTextField name;
	
	public ModifierNomGroupeListener(Group group, JTextField name) {
		this.group = group;
		this.name = name;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		group.setName(name.getText());
		try {
			group.update();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
}
