package view;

import java.awt.*;
import javax.swing.*;

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
		setBackground(Color.DARK_GRAY);
	    
	    int cellWidth = getCellWidth(size.width);
	    int cellHeight = getCellHeight(size.height);
	    
	    // Cadrillage
		g.setColor(Color.WHITE);
		for (ligne = 0; ligne < NB_LIGNES; ligne++) {
			for (colonne = 0; colonne < NB_COLONNES; colonne++) {
				g.drawRect(getXFromColonneNb(colonne, cellWidth), getYFromLigneNb(ligne, cellHeight), cellWidth, cellHeight);
			}
		}
		
		// Heures
		int heures = 0;
		int minutes = 0;
		
		g.setColor(Color.WHITE);		
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
		g.setColor(Color.WHITE);
		for (colonne = 0; colonne < NB_COLONNES; colonne++) {
			g.drawString(JOURS[colonne], getXFromColonneNb(colonne, cellWidth), OFFSET_HAUT / 2);
		}
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