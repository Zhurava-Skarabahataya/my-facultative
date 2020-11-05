package by.epamtc.facultative.dao.impl.pool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import org.apache.log4j.Logger;

import by.epamtc.facultative.dao.exception.DAOException;
import by.epamtc.facultative.dao.impl.pool.exception.ConnectionPoolException;

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

	private final String ERROR_MESSAGE_DRIVER_PROBLEMS = "Problems with database driver.";
	private final String ERROR_MESSAGE_SQL_PROBLEMS = "Problems with sql connection.";
	private final String ERROR_MESSAGE_NO_FREE_CONNECTION = "Problem: no free connections.";
	private final String ERROR_MESSAGE_STATEMENT_NOT_CLOSING = "Problem with closing statement.";
	private final String ERROR_MESSAGE_RESULT_SET_NOT_CLOSING = "Problem with closing result set.";
	private final String ERROR_MESSAGE_CONNECTION_NOT_CLOSING = "Problem with closing connection.";

	private BlockingQueue<Connection> freeConnections;
	private BlockingQueue<Connection> takenConnections;

	private ConnectionPool() {

	}

	public static ConnectionPool getInstance() {
		return instance;
	}

	public void initPool() throws ConnectionPoolException {

		String driver;
		String jdbcUrl;
		String login;
		String password;

		driver = resourceBundle.getString(DRIVER);
		jdbcUrl = resourceBundle.getString(JDBC_URL);
		login = resourceBundle.getString(DB_LOGIN);
		password = resourceBundle.getString(DB_PASSWORD);

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

				Connection connection;
				connection = DriverManager.getConnection(jdbcUrl, login, password);

				freeConnections.add(connection);
			}

		} catch (ClassNotFoundException e) {

			logger.error(ERROR_MESSAGE_DRIVER_PROBLEMS, e);
			throw new ConnectionPoolException(ERROR_MESSAGE_DRIVER_PROBLEMS, e);

		} catch (SQLException e) {

			logger.error(ERROR_MESSAGE_SQL_PROBLEMS, e);
			throw new ConnectionPoolException(ERROR_MESSAGE_SQL_PROBLEMS, e);
		}
	}

	public Connection getFreeConnection() throws ConnectionPoolException {

		Connection connection;
		connection = freeConnections.poll();

		if (connection == null) {

			logger.error(ERROR_MESSAGE_NO_FREE_CONNECTION);
			throw new ConnectionPoolException(ERROR_MESSAGE_NO_FREE_CONNECTION);
		}

		takenConnections.add(connection);

		return connection;
	}

	public void releaseConnection(Connection connection) {

		takenConnections.remove(connection);
		freeConnections.add(connection);
	}

	public void clearConnectionPool() throws ConnectionPoolException {

		closeConnectionQueue(freeConnections);
		closeConnectionQueue(takenConnections);

	}

	private void closeConnectionQueue(BlockingQueue<Connection> connectionQueue) throws ConnectionPoolException {

		Connection connection;

		while ((connection = connectionQueue.poll()) != null) {
			try {
				connection.close();

			} catch (SQLException e) {

				logger.error(ERROR_MESSAGE_CONNECTION_NOT_CLOSING, e);
				throw new ConnectionPoolException(ERROR_MESSAGE_CONNECTION_NOT_CLOSING, e);
			}
		}

	}

	public void closeConnection(ResultSet resultSet, Statement statement, Connection connection)
			throws ConnectionPoolException {

		if (resultSet != null) {
			try {
				resultSet.close();
			} catch (SQLException e) {

				logger.error(ERROR_MESSAGE_RESULT_SET_NOT_CLOSING, e);
				throw new ConnectionPoolException(ERROR_MESSAGE_RESULT_SET_NOT_CLOSING, e);
			}
		}

		if (statement != null) {
			try {
				statement.close();

			} catch (SQLException e) {

				logger.error(ERROR_MESSAGE_STATEMENT_NOT_CLOSING, e);
				throw new ConnectionPoolException(ERROR_MESSAGE_STATEMENT_NOT_CLOSING, e);
			}
		}
		if (connection != null) {

			releaseConnection(connection);
		}
	}

	public void closeConnection(Statement statement, Connection connection) throws ConnectionPoolException {

		if (statement != null) {
			try {
				statement.close();
			} catch (SQLException e) {

				logger.error(ERROR_MESSAGE_STATEMENT_NOT_CLOSING, e);
				throw new ConnectionPoolException(ERROR_MESSAGE_STATEMENT_NOT_CLOSING, e);
			}
		}
		if (connection != null) {

			releaseConnection(connection);
		}
	}

	public void closeConnection(ResultSet resultSet, Statement statement1, Statement statement2, Statement statement3,
			Connection connection) throws ConnectionPoolException {

		if (resultSet != null) {

			try {
				resultSet.close();
			} catch (SQLException e) {

				logger.error(ERROR_MESSAGE_RESULT_SET_NOT_CLOSING, e);
				throw new ConnectionPoolException(ERROR_MESSAGE_RESULT_SET_NOT_CLOSING, e);
			}
		}

		try {
			if (statement1 != null) {
				statement1.close();
			}

			if (statement2 != null) {
				statement2.close();
			}

			if (statement3 != null) {
				statement3.close();
			}

		} catch (SQLException e) {

			logger.error(ERROR_MESSAGE_STATEMENT_NOT_CLOSING, e);
			throw new ConnectionPoolException(ERROR_MESSAGE_STATEMENT_NOT_CLOSING, e);
		}

		if (connection != null) {

			releaseConnection(connection);
		}

	}

	public void closeConnection(Statement statement1, Statement statement2, Connection connection)
			throws ConnectionPoolException {
		
		try {
			if (statement1 != null) {
				statement1.close();
			}
			if (statement2 != null) {
				statement2.close();
			}
		} catch (SQLException e) {
			logger.error(ERROR_MESSAGE_STATEMENT_NOT_CLOSING, e);
			throw new ConnectionPoolException(ERROR_MESSAGE_STATEMENT_NOT_CLOSING, e);
		}

		if (connection != null) {

			releaseConnection(connection);
		}
	}

}
