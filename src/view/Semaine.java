package view;

import java.awt.*;

public class Semaine extends Canvas {
	// Nombre de colonnes et de lignes
	private static final int NB_COLONNES = 7;
	private static final int NB_LIGNES = 48;
	
	// Offsets
	private static final int OFFSET_GAUCHE = 35;
	private static final int OFFSET_DROITE = 2;
	private static final int OFFSET_HAUT = 30;
	private static final int OFFSET_BAS = 0;
	
	// Jours
	private static final String[] JOURS = {"Lundi", "Mardi", "Mercredi", "Jeudi", "Vendredi", "Samedi", "Dimanche"};
	
	private int getCellWidth(int width) {
		return (width - OFFSET_GAUCHE - OFFSET_DROITE) / NB_COLONNES;
	}
	private int getCellHeight(int height) {
		return (height - OFFSET_HAUT - OFFSET_BAS) / NB_LIGNES;
	}

	private int getXFromColonneNb(int colonne, int cellWidth) {
		return OFFSET_GAUCHE + cellWidth * colonne;
	}
	private int getYFromLigneNb(int ligne, int cellHeight) {
		return OFFSET_HAUT + cellHeight * ligne;
	}
	
	public void paint(Graphics g){
		int ligne, colonne;
		
		Dimension size = getSize();
		setBackground(Color.WHITE);
	    
		int cellWidth = getCellWidth(size.width);
	    int cellHeight = getCellHeight(size.height);
	    
	    // Quadrillage
		g.setColor(Color.BLACK);
		for (ligne = 0; ligne < NB_LIGNES; ligne++) {
			for (colonne = 0; colonne < NB_COLONNES; colonne++) {
				g.drawRect(getXFromColonneNb(colonne, cellWidth), getYFromLigneNb(ligne, cellHeight), cellWidth, cellHeight);
			}
		}
		
		// Heures
		int heures = 0;
		int minutes = 0;
		
		g.setColor(Color.BLACK);		
		for (ligne = 0; ligne < NB_LIGNES; ligne++) {
			if (minutes > 0) { 
				g.drawString(heures+"h"+minutes, 0, getYFromLigneNb(ligne, cellHeight) + cellHeight / 2);
			}else {
				g.drawString(heures+"h", 0, getYFromLigneNb(ligne, cellHeight) + cellHeight / 2);
			}
			minutes += 30;
			
			if (minutes >= 60) {
				heures++;
				minutes = 0;
			}
		}
		
		// Jours
		g.setColor(Color.BLACK);
		for (colonne = 0; colonne < NB_COLONNES; colonne++) {
			g.drawString(JOURS[colonne], 24+getXFromColonneNb(colonne, cellWidth), 5+OFFSET_HAUT / 2);
		}
		
		drawEvent(8,0,12,0,1,g,Color.BLUE, "Event1");
		drawEvent(15,0,17,0,1,g,Color.DARK_GRAY, "Event2");
		drawEvent(8,30,11,30,2,g,Color.GREEN, "Event3");
		drawEvent(13,0,16,0,3,g,Color.ORANGE, "Event4");
		drawEvent(8,0,11,30,4,g,Color.RED, "Event5");
		drawEvent(1,0,9,0,6,g,Color.YELLOW, "Event6");
	}
	
	public void drawEvent (int heure_debut, int minute_debut, int heure_fin, int minute_fin, int jour, Graphics g, Color couleur, String nom) {
		
		g.setColor(couleur);
		
		Dimension size = getSize();
		int demi_heure_debut = heure_debut*2 + minute_debut/30;
		int demi_heure_fin = heure_fin*2 + minute_fin/30;
		int nb_demi_heure = demi_heure_fin - demi_heure_debut;
		int cellWidth = getCellWidth(size.width);
	    int cellHeight = getCellHeight(size.height);
	    int taille_nom = nom.length()*7;
	    
		g.fillRect(jour*cellWidth+OFFSET_GAUCHE+1, demi_heure_debut*cellHeight+OFFSET_HAUT+1, cellWidth-1, cellHeight*nb_demi_heure-1);
		g.setColor(Color.BLACK);
		g.drawString(nom, jour*cellWidth+OFFSET_GAUCHE + (cellWidth-taille_nom)/2, OFFSET_HAUT + demi_heure_debut*cellHeight + nb_demi_heure/2*cellHeight+5);
	}
}
	/*private JTable tableau;
	private JScrollPane tableContainer;
	
	public Semaine() {
		super(new FlowLayout(FlowLayout.CENTER));
		
		String  title[] = {"", "Lundi", "Mardi", "Mercredi", "Jeudi", "Vendredi", "Samedi", "Dimanche"};
		Object[][] data = { 
				{"00:00", "", "", "", "", "", "", ""},
				{"01:00", "", "", "", "", "", "", ""},
				{"02:00", "", "", "", "", "", "", ""},
				{"03:00", "", "", "", "", "", "", ""},
				{"04:00", "", "", "", "", "", "", ""},
				{"05:00", "", "", "", "", "", "", ""},
				{"06:00", "", "", "", "", "", "", ""},
				{"07:00", "", "", "", "", "", "", ""},
				{"08:00", "", "", "", "", "", "", ""},
				{"09:00", "", "", "", "", "", "", ""},
				{"10:00", "", "", "", "", "", "", ""},
				{"11:00", "", "", "", "", "", "", ""},
				{"12:00", "", "", "", "", "", "", ""},
				{"13:00", "", "", "", "", "", "", ""},
				{"14:00", "", "", "", "", "", "", ""},
				{"15:00", "", "", "", "", "", "", ""},
				{"16:00", "", "", "", "", "", "", ""},
				{"17:00", "", "", "", "", "", "", ""},
				{"18:00", "", "", "", "", "", "", ""},
				{"19:00", "", "", "", "", "", "", ""},
				{"20:00", "", "", "", "", "", "", ""},
				{"21:00", "", "", "", "", "", "", ""},
				{"22:00", "", "", "", "", "", "", ""},
				{"23:00", "", "", "", "", "", "", ""}
		};
		
	    tableau = new JTable(data, title){
	        private static final long serialVersionUID = 1L;

	        public boolean isCellEditable(int row, int column) {                
	                return false;               
	        };
	    };
	    tableContainer = new JScrollPane(tableau);
	    tableau.getTableHeader().setReorderingAllowed(false);
	    tableau.getTableHeader().setResizingAllowed(false);
		this.add(tableContainer, BorderLayout.CENTER);
	}
}
*/