package model;

import java.sql.*;

/**
 * Un objet Database permet d'initier une connexion vers une base de données MySQL à l'aide de la bibliothèque JDBC
 * et d'y effectuer des requêtes.
 * 
 * @author Julien Valverdé
 * @version 1.0
 */
public class Database {
	// Instance principale de Database
	public static Database database;
	
	// Objet de connexion
	private Connection connection;
	
	/**
	 * Constructeur de Database. Initie la connexion avec la base de données MySQL.
	 * 
	 * @param host Adresse du serveur MySQL.
	 * @param database Nom de la base de données.
	 * @param user Nom d'utilisateur.
	 * @param password Mot de passe.
	 * 
	 * @throws ClassNotFoundException Le driver JDBC n'a pas pu être chargé.
	 * @throws SQLException	 La connexion avec la base de données n'a pas pu être initiée.
	 */
	public Database(String host, String database, String user, String password) throws ClassNotFoundException, SQLException {
		Class.forName("org.mariadb.jdbc.Driver");		
		connection = DriverManager.getConnection("jdbc:mysql://"+host+"/"+database, user, password);
	}
	
	/**
	 * Retourne un objet de requête préparée (mais pas exécutée).
	 * @param query
	 * @return
	 * @throws SQLException
	 */
	public PreparedStatement getPreparedQuery(String query) throws SQLException {
		PreparedStatement st = connection.prepareStatement(
				query,
				ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_UPDATABLE);
		st.closeOnCompletion();
		
		return st;
	}
	
	/**
	 * Exécute une requête préparée et retourne le résultat.
	 * @param st
	 * @return
	 * @throws SQLException
	 */
	public ResultSet executePreparedQuery(PreparedStatement st) throws SQLException {
		ResultSet res = st.executeQuery();
		res.beforeFirst();
		
		return res;
	}
	
	/**
	 * Exécute une requête et retourne un objet ResultSet.
	 * 
	 * @param query Requête SQL.
	 * 
	 * @return ResultSet contenant les lignes retournées par la requête.
	 * 
	 * @throws SQLException
	 */
	public ResultSet execute(String query) throws SQLException {
		Statement st = connection.createStatement(
				ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_UPDATABLE);
		st.closeOnCompletion();
		
		ResultSet res = st.executeQuery(query);
		res.beforeFirst();
		
		return res;
	}
	
	/**
	 * 
	 * @param query
	 * @param values
	 * @param types
	 * @return
	 * @throws SQLException 
	 */
	public ResultSet prepareAndExecute(String query, Object[] values, String[] types) throws SQLException {
		PreparedStatement st = connection.prepareStatement(
				query,
				ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_UPDATABLE);
		st.closeOnCompletion();
		
		// Application des paramètres (s'ils sont précisés)
		if (values != null && types != null) {
			for (int i = 0; i < values.length; i++) {
				Object value = values[i];
				
				switch (types[i]) {
					case "int": st.setInt(i + 1, (int) value); break;
					case "boolean": st.setBoolean(i + 1, (boolean) value); break;
					case "String": st.setString(i + 1, (String) value); break;
					case "Date": st.setDate(i + 1, (Date) value); break;
				}
			}
		}
		
		ResultSet res = st.executeQuery();
		res.beforeFirst();
		
		return res;
	}
	
	/**
	 * 
	 * @param query
	 * @param ent
	 * @return
	 * @throws Exception
	 */
	public ResultSet prepareWithEntityAttributes(String query, Entity ent) throws Exception {
		PreparedStatement st = connection.prepareStatement(
				query,
				ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_UPDATABLE);
		st.closeOnCompletion();
		
		ent.bindUpdateFields(st);
		
		ResultSet res = st.executeQuery();
		res.beforeFirst();
		
		return res;
	}
}