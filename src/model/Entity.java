package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public abstract class Entity {
	// Objet usine
	private EntityFactory factory;
	
	// Propriétés du type d'entité
	private String table;
	private String single;
	private String prefix;
	
	public String getTable() {
		return table;
	}
	public String getSingle() {
		return single;
	}
	public String getPrefix() {
		return prefix;
	}
	
	// Attributs de l'entité
	private int id;
	
	public int getId() {
		return id;
	}
	
	/*
	 * Constructeur
	 * Sauvegarde les propriétés du modèle à partir de l'usine
	 */
	public Entity(EntityFactory factory, ResultSet res) throws Exception {
		this.factory = factory;
		
		// Stockage des propriétés de l'entité
		table = factory.getTable();
		single = factory.getSingle();
		prefix = factory.getPrefix();
		
		save(res);
	}
	
	private void save(ResultSet res) throws Exception {
		id = res.getInt(getPrefix()+"id");
	}
	
	public void refresh() {
		
	}
	
	/*
	 * update
	 */
	protected int bind(PreparedStatement st) throws Exception {
		return 1;
	}
	
	/*
	 * delete
	 */
	public void delete() {
	}
}