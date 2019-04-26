package model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Observable;

/**
 * Un objet Database permet d'initier une connexion vers une base de données MySQL à l'aide de la bibliothèque JDBC
 * et d'y effectuer des requêtes.
 * 
 * @author Julien Valverdé
 * @version 1.0
 */
@SuppressWarnings("deprecation")
public class Database extends Observable {
	// Instance principale de Database
	public static Database database = new Database();
	
	// Coordonnées de la base de données
	private static final String HOST = "localhost";
	private static final String DATABASE = "l2_gr2";
	private static final String USER = "l2_gr2";
	private static final String PASSWORD = "5KUavzaM";
	
	// États (pour les objets observeurs)
	public static final int CONNECTION_SUCCESS = 1;
	public static final int CONNECTION_FAILURE = 2;
	public static final int QUERY_STARTED = 3;
	public static final int QUERY_FINISHED = 4;
	
	// Objet de connexion
	private Connection connection;
	
	/**
	 * Initialise la connexion à la base de données.
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public void connect() throws ClassNotFoundException, SQLException {
		Class.forName("org.mariadb.jdbc.Driver");
		connection = DriverManager.getConnection("jdbc:mysql://"+HOST+"/"+DATABASE, USER, PASSWORD);
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
		// Prévenir les observeurs qu'une requête a débuté
		setChanged();
		notifyObservers(QUERY_STARTED);
		
		// Exécution
		ResultSet res = st.executeQuery();
		res.beforeFirst();
		
		// Prévenir les observeurs que la requête est finie
		setChanged();
		notifyObservers(QUERY_FINISHED);
		
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
		// Prévenir les observeurs qu'une requête a débuté
		setChanged();
		notifyObservers(QUERY_STARTED);
		
		Statement st = connection.createStatement(
				ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_UPDATABLE);
		st.closeOnCompletion();
		
		ResultSet res = st.executeQuery(query);
		res.beforeFirst();
		
		// Prévenir les observeurs que la requête est finie
		setChanged();
		notifyObservers(QUERY_FINISHED);
		
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
		
		// Prévenir les observeurs qu'une requête a débuté
		setChanged();
		notifyObservers(QUERY_STARTED);
		
		// Exécution
		ResultSet res = st.executeQuery();
		res.beforeFirst();
		
		// Prévenir les observeurs que la requête est finie
		setChanged();
		notifyObservers(QUERY_FINISHED);
		
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