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
}