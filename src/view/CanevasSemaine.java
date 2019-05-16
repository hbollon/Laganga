package view;

import java.awt.*;

public class CanevasSemaine extends Canvas {
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
	    
		g.fillRect(calculer_X_event(heure_debut, minute_debut, jour), calculer_Y_event(heure_debut, minute_debut), cellWidth-1, calculer_height_event (heure_debut, heure_fin, minute_debut, minute_fin));
		g.setColor(Color.BLACK);
		g.drawString(nom, jour*cellWidth+OFFSET_GAUCHE + (cellWidth-taille_nom)/2, OFFSET_HAUT + demi_heure_debut*cellHeight + nb_demi_heure/2*cellHeight+5);
	}
	public int calculer_X_event (int heure_debut, int minute_debut, int jour) {
		Dimension size = getSize();
		int cellWidth = getCellWidth(size.width);
		int valeur_x = jour*cellWidth+OFFSET_GAUCHE+1;
		return valeur_x;
	}
	public int calculer_Y_event (int heure_debut, int minute_debut) {
		Dimension size = getSize();
		int cellHeight = getCellHeight(size.height);
		int demi_heure_debut = heure_debut*2 + minute_debut/30; 
		int valeur_y =  demi_heure_debut*cellHeight+OFFSET_HAUT+1;
		return valeur_y;
	}
	public int calculer_height_event (int heure_debut, int heure_fin, int minute_debut, int minute_fin) {
		Dimension size = getSize();
		int demi_heure_debut = heure_debut*2 + minute_debut/30;
		int demi_heure_fin = heure_fin*2 + minute_fin/30;
		int cellHeight = getCellHeight(size.height);
		int nb_demi_heure = demi_heure_fin - demi_heure_debut;
		int height_event = cellHeight*nb_demi_heure-1;
		return height_event;
	}
}