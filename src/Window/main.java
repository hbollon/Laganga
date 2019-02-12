package Window;

public class main {
	
	public static void main(String[] args) {
		MainWin fenetre = new MainWin();
		fenetre.setVisible(true);
		
	}       
	
	/*public static void initializeFrame()
	{
		fenetre.setTitle("Laganga");
	    fenetre.setSize(1280, 720);
	    fenetre.setLocationRelativeTo(null);
	    ImageIcon img = new ImageIcon("icon.png");
	    fenetre.setIconImage(img.getImage());
	    fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    fenetre.setVisible(true);
	}
	
	public static JTree getJTree()
	{
		if(listeUser == null)
		{
			listeUser = new JTree();
		}
		return listeUser;
	}
	
	public static void initializeListe()
	{
		listeUser = new JTree();
		listeUser.setSize(200, 420);
	}

	public static JPanel getJPanel()
	{
		if(panelGauche == null)
		{
			panelGauche = new JPanel();
			panelGauche.setLayout(new BorderLayout());
			panelGauche.add(getJTree(), BorderLayout.WEST);
		}
		return panelGauche;
	}*/
}
