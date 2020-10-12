package by.epamtc.facultative.DAO.impl.pool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ConnectionPool {

	private static final ConnectionPool instance = new ConnectionPool();
	private ResourceBundle resourceBundle = ResourceBundle.getBundle(DB_PROPERTIES_FILE);

	private static final String DRIVER = "db.driver";
	private static final String JDBC_URL = "db.jdbc_url";
	private static final String DB_LOGIN = "db.login";
	private static final String DB_PASSWORD = "db.password";
	private static final String CONNECTION_COUNT = "db.connection_count";

	private static final String DB_PROPERTIES_FILE = "db";

	private static final int MINIMAL_CONNECTION_COUNT = 5;

	private BlockingQueue<Connection> freeConnections;
	private BlockingQueue<Connection> takenConnections;

	private ConnectionPool() {

	}

	public static ConnectionPool getInstance() {
		return instance;
	}

	public void initPool() {
		String driver = resourceBundle.getString(DRIVER);
		String jdbcUrl = resourceBundle.getString(JDBC_URL);
		String login = resourceBundle.getString(DB_LOGIN);
		String password = resourceBundle.getString(DB_PASSWORD);

		int connectionCount = 0;
		try {
			connectionCount = Integer.parseInt(resourceBundle.getString(CONNECTION_COUNT));
		} catch (NumberFormatException e) {
			connectionCount = MINIMAL_CONNECTION_COUNT;
		}

		freeConnections = new ArrayBlockingQueue<Connection>(connectionCount);
		takenConnections = new ArrayBlockingQueue<Connection>(connectionCount);

		try {
			Class.forName(driver);

			for (int count = 0; count < connectionCount; count++) {
				Connection connection = DriverManager.getConnection(jdbcUrl, login, password);
				freeConnections.add(connection);
				System.out.println("Добавили соединение");
			}

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			// ОБРАБООООООТАЙ ПОТОМ БЕДА С ДРАЙВЕРОМ
		} catch (SQLException e) {
			e.printStackTrace();
			// ОБРАБООООООТАЙ ПОТОМ БЕДА С СОЕДИНЕНИЯМИ
		}
	}

	
	public Connection getFreeConnection() {
		Connection connection;
		connection = freeConnections.poll();
		if (connection == null) {
			//БЕДА НЕТ СОЕДНИНЕНИЯ ОБРАТИТЕСЬ ПОЗЖЕ
		}
		takenConnections.add(connection);
		return connection;
	}
	
	public void releaseConnection(Connection connection) {
		takenConnections.remove(connection);
		freeConnections.add(connection);
	}
	
	public void clearConnectionPool() {
		
	}
	
}
