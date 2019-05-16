package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
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
	public void connect() throws Exception {
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://"+HOST+"/"+DATABASE, USER, PASSWORD);
		}
		catch (Exception e) {
			setChanged();
			notifyObservers(CONNECTION_FAILURE);
			
			throw e;
		}
		
		setChanged();
		notifyObservers(CONNECTION_SUCCESS);
	}
	
	// Préparation d'une requête
	public PreparedStatement prepare(String query) throws SQLException {
		PreparedStatement st = connection.prepareStatement(
				query,
				ResultSet.TYPE_SCROLL_INSENSITIVE);
		st.closeOnCompletion();
		
		return st;
	}
	public PreparedStatement prepare(String query, FieldsList fields, Map<String, Object> values) throws SQLException, Exception {
		PreparedStatement st = prepare(query);
		fields.bind(st, values);
		
		return st;
	}
	
	// Préparation d'une requête INSERT/UPDATE/DELETE préparée
	public PreparedStatement prepareUpdate(String query, FieldsList fields, Map<String, Object> values) throws SQLException, Exception {
		PreparedStatement st = connection.prepareStatement(
				query,
				PreparedStatement.RETURN_GENERATED_KEYS);
		st.closeOnCompletion();
		
		fields.bind(st, values);
		
		return st;
	}
	
	// Exécution d'une requête préparée
	public ResultSet execute(PreparedStatement st) throws SQLException {
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
	
	// Exécution d'une requête INSERT/UPDATE/DELETE préparée
	public ResultSet executeUpdate(PreparedStatement st) throws SQLException {
		// Prévenir les observeurs qu'une requête a débuté
		setChanged();
		notifyObservers(QUERY_STARTED);
		
		// Exécution
		st.executeUpdate();
		
		ResultSet res = st.getGeneratedKeys();
		res.beforeFirst();
		
		// Prévenir les observeurs que la requête est finie
		setChanged();
		notifyObservers(QUERY_FINISHED);
		
		return res;
	}
	
	// Fonction 2 en 1 qui prépare et exécute
	public ResultSet prepareAndExecute(String query) throws SQLException {
		return execute(prepare(query));
	}
	public ResultSet prepareAndExecute(String query, FieldsList fields, Map<String, Object> values) throws SQLException, Exception {
		return execute(prepare(query, fields, values));
	}	
	public ResultSet prepareUpdateAndExecute(String query, FieldsList fields, Map<String, Object> values) throws SQLException, Exception {
		return executeUpdate(prepareUpdate(query, fields, values));
	}
}