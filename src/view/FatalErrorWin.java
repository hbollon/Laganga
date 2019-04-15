package view;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

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
		getContentPane().setBackground(Color.WHITE);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setLayout(new BorderLayout());
		
		// Message d'erreur
		add(new JLabel("Une erreur est survenue :\n"+status, JLabel.CENTER), BorderLayout.CENTER);
		
		// Bouton de fermeture
		JButton ok = new JButton("Ok");
		ok.addActionListener(new CloseWindowButtonListener(this));
		add(ok, BorderLayout.SOUTH);
		
		setVisible(true);
	}
}