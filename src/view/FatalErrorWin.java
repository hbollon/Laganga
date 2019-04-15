package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import controller.CloseWindowButtonListener;

/**
 * Le FatalErrorWin est une fenêtre servant à afficher une fenêtre d'erreur qui arrête le programme à sa fermeture.
 * 
 * @author Julien Valverdé
 */
@SuppressWarnings("serial")
public class FatalErrorWin extends JFrame {
	public FatalErrorWin(String status) throws Exception {
		setTitle("Laganga - Erreur");
		setLocationRelativeTo(null);
		setSize(600, 150);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Container content = getContentPane();
		content.setLayout(new BorderLayout());
		content.setBackground(Color.WHITE);
		
		// Message d'erreur
		JPanel statusPanel = new JPanel(new GridLayout(0, 1));
		statusPanel.add(new JLabel("Une erreur est survenue :", JLabel.CENTER));
		statusPanel.add(new JLabel(status, JLabel.CENTER));
		
		content.add(statusPanel);
		
		// Bouton de fermeture
		JButton ok = new JButton("Ok");
		ok.addActionListener(new CloseWindowButtonListener(this));
		content.add(ok, BorderLayout.SOUTH);
		
		setVisible(true);
	}
}