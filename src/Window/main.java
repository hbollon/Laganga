package Window;

import java.awt.BorderLayout;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTree;

public class main {
	
	static private JFrame fenetre;
	static private JTree listeUser;
	static private JPanel panelGauche;
	
	public static void main(String[] args) {
		fenetre = new JFrame();
		listeUser = new JTree();
		initializeFrame(fenetre);
		initializeListe(listeUser);
		fenetre.setContentPane(getJPanel());

	  }       
	
	public static void initializeFrame(JFrame fenetre)
	{
		fenetre.setTitle("Laganga");
	    fenetre.setSize(1280, 720);
	    fenetre.setLocationRelativeTo(null);
	    ImageIcon img = new ImageIcon("icon.png");
	    fenetre.setIconImage(img.getImage());
	    fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    fenetre.setVisible(true);
	}
	
	public static void initializeListe(JTree listeUser)
	{
		listeUser = new JTree();
		listeUser.setSize(200, 420);
	}

	public static JPanel getJPanel()
	{
		listeUser.setSize(200, 420);
		panelGauche = new JPanel();
		panelGauche.setLayout(new BorderLayout());
		panelGauche.add(
		return panelGauche;
	}
}
