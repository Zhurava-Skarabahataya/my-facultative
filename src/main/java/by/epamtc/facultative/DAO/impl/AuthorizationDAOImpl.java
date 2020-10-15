package by.epamtc.facultative.DAO.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.mindrot.jbcrypt.BCrypt;

import by.epamtc.facultative.DAO.exception.DAOException;
import by.epamtc.facultative.DAO.impl.pool.ConnectionPool;
import by.epamtc.facultative.DAO.impl.pool.ConnectionPoolException;
import by.epamtc.facultative.bean.UserAuthorizationInfo;

public class AuthorizationDAOImpl {

	private static final AuthorizationDAOImpl instance = new AuthorizationDAOImpl();

	private static final Logger logger = Logger.getLogger(AuthorizationDAOImpl.class);

	private static final String QUERY_FIND_USER_IN_DATABASE = "SELECT * FROM users WHERE user_login = ? ";
//AND user_password = ?

	private AuthorizationDAOImpl() {

	}

	public static AuthorizationDAOImpl getInstance() {
		return instance;
	}

	public boolean authorizeUser(UserAuthorizationInfo info) throws DAOException {

		String userLogin;
		String userPasswordUnhashed;
		String hashedPassword = null;

		userLogin = info.getLogin();
		userPasswordUnhashed = info.getPassword();

		ConnectionPool connectionPool = ConnectionPool.getInstance();
		Connection connection = null;

		PreparedStatement statement = null;
		ResultSet resultSet = null;

		try {
			connection = connectionPool.getFreeConnection();

			statement = connection.prepareStatement(QUERY_FIND_USER_IN_DATABASE);

			statement.setString(1, userLogin);

			resultSet = statement.executeQuery();

			if (resultSet.next()) {
				
				hashedPassword = resultSet.getString("user_password");
				
				if (BCrypt.checkpw(userPasswordUnhashed, hashedPassword)) {
					return true;
				}

			}

		} catch (ConnectionPoolException e) {

			String message = "Не удалось получить соединение.";
			logger.error(message);
			throw new DAOException(message, e);
		}

		catch (SQLException e) {

			String message = "Проблема с выполнением запроса в базу данных.";
			logger.error(message, e);
			throw new DAOException(message, e);

		} finally {

			try {
				connectionPool.closeConnection(resultSet, statement, connection);

			} catch (ConnectionPoolException e) {

				String message = "Не удалось закрыть соединение.";
				logger.error(message);
				throw new DAOException(message, e);
			}
		}

		return false;

	}

}
