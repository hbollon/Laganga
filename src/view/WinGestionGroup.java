package view;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

import controller.ModifierNomGroupeListener;
import controller.SupprimerGroupeListener;
import controller.SupprimerMembreListener;
import model.LocalUser;
import model.entities.Group;
import model.entities.User;
import view.tabs.Tab;

@SuppressWarnings("deprecation")
public class WinGestionGroup extends Tab implements Observer {
	private static final long serialVersionUID = -942892911146765943L;
	
	public WinGestionGroup(Group group) {
		super();
		
		setName("Groupe : '" + group.getName() + " '");
		content.setLayout(new BorderLayout());
		
		boolean estProprietaire = LocalUser.localUser.getUser() == group.getOwner();
			
			// Liste des membres
			DefaultMutableTreeNode top = new DefaultMutableTreeNode("Membres du Groupe");
			
			List<User> members = group.getMembers();
			for(int i = 0; i < members.size(); i++) {
				User member = members.get(i);
				
				top.add(new DefaultMutableTreeNode(member.getFirstName() + " " + member.getLastName()));
			}
			
		if (estProprietaire) {
			
			//Panel au centre de la fenêtre
			JPanel gestGroupe = new JPanel(new GridLayout(4,2));
			
			// Partie "Nom Du Groupe"
			JPanel nomChanger = new JPanel(new GridLayout(2,1));
			
			JLabel nomGroupe = new JLabel("Nom du groupe : ");
			JTextField nomDuGroupe = new JTextField(group.getName()); 
			Button changerNom = new Button("Changer le nom");
			changerNom.addActionListener(new ModifierNomGroupeListener(group, nomDuGroupe));
			
			nomChanger.add(nomDuGroupe);
			nomChanger.add(changerNom);
	
			gestGroupe.add(nomGroupe);
			gestGroupe.add(nomChanger);
			
			// Partie "Propriétaire Du Groupe"
			JLabel proprietaire = new JLabel("Propriétaire du groupe : ");
			
			gestGroupe.add(proprietaire);
			gestGroupe.add(new JLabel(group.getOwner().getFirstName() + " " + group.getOwner().getLastName()));
	
			// Partie "Liste Des Membres"
			JPanel listeMembres = new JPanel(new GridLayout(1,2));
			JLabel listeDesMembres = new JLabel("Liste des membres : ");
			JTree arbre = new JTree(top);
			JScrollPane liste = new JScrollPane(arbre);
			Button bannirMembre = new Button("Mieux vaut bannir que guérir");
			bannirMembre.addActionListener(new SupprimerMembreListener(group, arbre, members));
			
			listeMembres.add(liste);
			listeMembres.add(bannirMembre);
			
			gestGroupe.add(listeDesMembres);
			gestGroupe.add(listeMembres);
	
			// Partie "Suppression
			Button supprimer = new Button("Supprimer");
			JLabel supprimerGroupe = new JLabel("Supprimer le groupe : ");
			supprimer.addActionListener(new SupprimerGroupeListener(this, group));
			
			gestGroupe.add(supprimerGroupe);
			gestGroupe.add(supprimer);
			
			content.add(gestGroupe, BorderLayout.CENTER);
			
		}else {
			
			//Panel au centre de la fenêtre
			JPanel gestGroupe = new JPanel(new GridLayout(3,2));
			
			// Partie "Nom Du Groupe"			
			JLabel nomGroupe = new JLabel("Nom du groupe : ");
			JLabel nomDuGroupe = new JLabel(group.getName()); 
			Button changerNom = new Button("Changer le nom");

				
			gestGroupe.add(nomGroupe);
			gestGroupe.add(nomDuGroupe);
						
			// Partie "Propriétaire Du Groupe"
			JLabel proprietaire = new JLabel("Propriétaire du groupe : ");
					
			gestGroupe.add(proprietaire);
			gestGroupe.add(new JLabel(group.getOwner().getFirstName() + " " + group.getOwner().getLastName()));
				
			// Partie "Liste Des Membres"
			JLabel listeDesMembres = new JLabel("Liste des membres : ");
			JTree arbre = new JTree(top);
			JScrollPane liste = new JScrollPane(arbre);
			liste.setPreferredSize(new Dimension(200,200));
						
			gestGroupe.add(listeDesMembres);
			gestGroupe.add(liste);
			
			content.add(gestGroupe, BorderLayout.CENTER);
		}
		open();
	}

	@Override
	public void update(Observable o, Object arg) {

	}
}
