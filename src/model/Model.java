/*
 * Fichier : Model.java
 * Auteur : Julien Valverdé
 */
package model;

import java.sql.*;


/*
 * Model
 * Une instance du modèle. Une instance du modèle représente une table de la base de données.
 */
public abstract class Model {
	// Propriétés du modèle
	private String table;
	private String prefix;
	
	public String getTable() {
		return table;
	}
	public String getPrefix() {
		return prefix;
	}
	
	
	// Données
	protected ResultSet res;
	protected int resRowID;
	
	
	// Champs de la table
	private int id;
	
	public int getID() {
		return id;
	}
	
	
	/*
	 * Constructeur
	 * Sauvegarde les propriétés du modèle à partir de l'usine
	 */
	public Model(ModelFactory factory) {
		table = factory.getTable();
		prefix = factory.getPrefix();
	}
	
	/*
	 * save
	 * Sauvegarde les champs de la table issus du ResultSet
	 */
	public void save(ResultSet res) throws Exception {
		this.res = res;
		resRowID = res.getRow();
		
		id = res.getInt(getPrefix()+"id");
	}
	
	private void moveToCurrentRow() throws SQLException {
		res.absolute(resRowID);
	}
	
	/*
	 * update
	 */
	protected void update() throws SQLException {
		moveToCurrentRow();
		
		res.updateInt(getPrefix()+"id", id);
	}
	
	/*
	 * delete
	 */
	public void delete() throws SQLException {
		moveToCurrentRow();
		
		res.deleteRow();
	}
}