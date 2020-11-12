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

/**
 * Implementation of {@link AuthorizationDAO}. Methods use
 * {@link ConnectionPool} to connect to database and make attempt to authorize
 * user.
 */
public class AuthorizationDAOImpl implements AuthorizationDAO {

	/** A single instance of the class (pattern Singleton) */
	private static final AuthorizationDAOImpl instance = new AuthorizationDAOImpl();

	/** Logger of the class */
	private static final Logger logger = Logger.getLogger(AuthorizationDAOImpl.class);

	/** Query for database for selecting users with entered login */
	private final String QUERY_FIND_USER_IN_DATABASE = "SELECT * FROM users WHERE user_login = ? ";

	/** Database parameter user password */
	private final String PARAMETER_USER_PASSWORD = "user_password";

	/** Message occurs when problems with database */
	private final String ERROR_MESSAGE_DATABASE_PROBLEM = "Problem with database execution.";

	/** private constructor without parameters */
	private AuthorizationDAOImpl() {

	}

	/**
	 * Returns singleton object of the class
	 * 
	 * @return Object of {@link CourseDAOImpl}
	 */
	public static AuthorizationDAOImpl getInstance() {
		return instance;
	}

	/**
	 * Method connects to database via {@link ConnectionPool} and makes attempt to
	 * authorize user, if entered login and password are valid
	 * 
	 * @param info data transfer object {@link UserAuthorizationInfo} which contains
	 *             info about entered password and login
	 * @return true if user exists, false if user not found
	 * @throws DAOException when problems with database access occur. 
	 */
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
