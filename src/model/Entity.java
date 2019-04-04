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
	}
	
	public void save(ResultSet res) throws Exception {
		id = res.getInt(getPrefix()+"id");
	}
	
	protected int bindUpdateFields(PreparedStatement st) throws Exception {
		return 1;
	}
	
	/*
	 * Opérations sur la base de données
	 */
	
	/**
	 * Met à jour les attributs de l'objet depuis la base de données.
	 */
	public void refresh() {
		
	}
	
	/**
	 * Met à jour la base de données depuis les attributs de l'objet.
	 */
	public void update() throws Exception {
		PreparedStatement st = Database.database.getPreparedQuery(factory.getUpdateQuery(this));
		
		int i = factory.bindUpdateFields(this, st);
		
		Database.database.executePreparedQuery(st);
	}
	
	/**
	 * Récupère la liste des champs à mettre à jour lors d'une opération update.
	 */
	public String getUpdateFields() {
		return "";
	}
	
	/**
	 * Supprime l'entrée de la base de données.
	 */
	public void delete() {
	}
}