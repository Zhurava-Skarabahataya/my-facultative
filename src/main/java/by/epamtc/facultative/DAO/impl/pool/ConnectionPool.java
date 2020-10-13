package by.epamtc.facultative.DAO.impl.pool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import org.apache.log4j.Logger;

import by.epamtc.facultative.DAO.exception.DAOException;

public class ConnectionPool {

	private static final ConnectionPool instance = new ConnectionPool();
	private ResourceBundle resourceBundle = ResourceBundle.getBundle(DB_PROPERTIES_FILE);

	private static final Logger logger = Logger.getLogger(ConnectionPool.class);

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

	public void initPool() throws ConnectionPoolException {

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

			}

		} catch (ClassNotFoundException e) {

			String message = "Проблема с драйвером.";
			logger.error(message, e);
			throw new ConnectionPoolException(message, e);

		} catch (SQLException e) {

			String message = "Проблема с выполнением соединений.";
			logger.error(message, e);
			throw new ConnectionPoolException(message, e);
		}
	}

	public Connection getFreeConnection() throws ConnectionPoolException {
		
		Connection connection;
		connection = freeConnections.poll();
		
		if (connection == null) {

			String message = "Не удалось получить соединение.";
			logger.error(message);
			throw new ConnectionPoolException(message);
		}
		
		takenConnections.add(connection) ;
		
		return connection;
	}

	public void releaseConnection(Connection connection) {
		
		takenConnections.remove(connection);
		freeConnections.add(connection);
	}

	public void clearConnectionPool() {
		
	}

	public void closeConnection(ResultSet rs, Statement st, Connection conn) throws ConnectionPoolException  {
		
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {

				String message = "Проблема с закрытием результатов запроса в базу данных.";
				logger.error(message, e);
				throw new ConnectionPoolException(message, e);
			}
		}

		if (st != null) {
			try {
				st.close();
			} catch (SQLException e) {

				String message = "Проблема с закрытием запроса в базу данных.";
				logger.error(message, e);
				throw new ConnectionPoolException(message, e);
			}
		}
		if (conn != null) {
			
			releaseConnection(conn);
		}
	}

	public void closeConnection(Statement st, Connection conn) throws ConnectionPoolException {
		if (st != null) {
			try {
				st.close();
			} catch (SQLException e) {

				String message = "Проблема с закрытием запроса в базу данных.";
				logger.error(message, e);
				throw new ConnectionPoolException(message, e);
			}
		}
		if (conn != null) {
			
			releaseConnection(conn);
		}
	}

}
