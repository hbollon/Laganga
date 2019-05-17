package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

import controller.CloseWindow;
import model.entities.Entity;
import model.entities.User;

public class WinCreateGroupe extends JFrame implements Observer {

	//private JLabel status = new JLabel("Ajouter un groupe", JLabel.CENTER);
			
	private static final long serialVersionUID = 1335122662037829913L;

	public WinCreateGroupe() throws Exception {
		super();
		
		this.setTitle("Cr�ation Groupe");
	    this.setSize(800, 700);
		this.setBackground(Color.white);
		this.setLayout(new BorderLayout());
		
		// Cr�ation du GridLayout principal
		JPanel newGroupe = new JPanel(new GridLayout(3,1));
		
		// Liste des membres
		DefaultMutableTreeNode arbre = new DefaultMutableTreeNode("jaajent");
		List<Entity> listeArbre = User.factory.getAll();
		for(int i = 0; i < listeArbre.size(); i++) {
			User member = (User) listeArbre.get(i);
			arbre.add(new DefaultMutableTreeNode(member.getFirstName() + " " + member.getLastName()));
		}
		
		// Cr�ation des 3 �l�ments de la fen�tre en GridLayout
		JPanel nomGroupe = new JPanel(new FlowLayout());
		JPanel nomAdmin = new JPanel(new FlowLayout());
		JPanel creation = new JPanel(new FlowLayout());
		JButton creerGroupe = new JButton("Cr�er Groupe");
		//creerGroupe.addActionListener(new CreateGroupe(null));
		
		// Remplissage du panel du nom de groupe
		JLabel nomDeGroupe = new JLabel("Nom du Groupe : ");
		JTextField champNomGroupe = new JTextField();
		champNomGroupe.setPreferredSize(new Dimension(200,25));
		
		nomGroupe.add(nomDeGroupe);
		nomGroupe.add(champNomGroupe);
		
		// Remplissage du panel de l'ajout de membre
		JLabel nomDeAdmin = new JLabel("Nom de l'admin ");
		JTree listeNomAdmin = new JTree(arbre);
		listeNomAdmin.setPreferredSize(new Dimension(200,25));
		JScrollPane liste = new JScrollPane(listeNomAdmin);
		
		nomAdmin.add(nomDeAdmin);
		nomAdmin.add(liste);
		
		// Adaptation du bouton
		creerGroupe.setPreferredSize(new Dimension(150,50));
		creation.add(creerGroupe);
		
		// Remplissage du GridLayout
		newGroupe.add(nomGroupe);
		newGroupe.add(nomAdmin);
		newGroupe.add(creation);
		
		this.add(newGroupe);
		this.setVisible(true);
		this.addWindowListener(new CloseWindow(this));
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}
}