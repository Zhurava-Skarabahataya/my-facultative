package by.epamtc.facultative.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.mindrot.jbcrypt.BCrypt;

import by.epamtc.facultative.dao.AuthorizationDAO;
import by.epamtc.facultative.dao.exception.DAOException;
import by.epamtc.facultative.dao.impl.pool.ConnectionPool;
import by.epamtc.facultative.dao.impl.pool.exception.ConnectionPoolException;
import by.epamtc.facultative.bean.UserAuthorizationInfo;

public class AuthorizationDAOImpl implements AuthorizationDAO {

	private static final AuthorizationDAOImpl instance = new AuthorizationDAOImpl();

	private static final Logger logger = Logger.getLogger(AuthorizationDAOImpl.class);

	private final String QUERY_FIND_USER_IN_DATABASE = "SELECT * FROM users WHERE user_login = ? ";

	private final String PARAMETER_USER_PASSWORD = "user_password";

	private final String ERROR_MESSAGE_DATABASE_PROBLEM = "Problem with database execution.";

	private AuthorizationDAOImpl() {

	}

	public static AuthorizationDAOImpl getInstance() {
		return instance;
	}

	@Override
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

				hashedPassword = resultSet.getString(PARAMETER_USER_PASSWORD);

				if (BCrypt.checkpw(userPasswordUnhashed, hashedPassword)) {
					return true;
				}

			}

		} catch (ConnectionPoolException e) {

			throw new DAOException(e);
		}

		catch (SQLException e) {

			logger.error(ERROR_MESSAGE_DATABASE_PROBLEM, e);
			throw new DAOException(ERROR_MESSAGE_DATABASE_PROBLEM, e);

		} finally {

			try {
				connectionPool.closeConnection(resultSet, statement, connection);

			} catch (ConnectionPoolException e) {

				throw new DAOException(e);
			}
		}

		return false;

	}

}
