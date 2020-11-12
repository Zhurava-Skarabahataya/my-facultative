package by.epamtc.facultative.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import by.epamtc.facultative.dao.exception.DAOException;
import by.epamtc.facultative.dao.impl.pool.ConnectionPool;
import by.epamtc.facultative.dao.impl.pool.exception.ConnectionPoolException;
import by.epamtc.facultative.bean.UserRegistrationInfo;
import by.epamtc.facultative.dao.RegistrationDAO;

/**
 * Implementation of {@link RegistrationDAO}. Methods use {@link ConnectionPool}
 * to connect to database and make attempt to authorize user.
 */
public class RegistrationDAOImpl implements RegistrationDAO {

	/** A single instance of the class (pattern Singleton) */
	private static final RegistrationDAOImpl instance = new RegistrationDAOImpl();

	/** Logger of the class */
	private static final Logger logger = Logger.getLogger(RegistrationDAOImpl.class);

	/** Database parameter user id */
	private final String DB_PARAMETER_USER_ID = "users.user_id";

	/** Query for database for inserting new user into database */
	private final String QUERY_INSERT_NEW_USER_INTO_USERS = "INSERT into USERS(user_login, user_password, user_email,"
			+ "first_name, second_name, patronymic, user_role_id, department_department_id, status"
			+ ") VALUES (?, ?,?,?,?,?,?,?, 1)";

	/** Query for database for inserting new user into database */
	private final String QUERY_FIND_USER_ID_BY_LOGIN = "SELECT user_id FROM users WHERE user_login = ?";

	/** Query for database for inserting new user into user details */
	private final String QUERY_INSERT_NEW_USER_IN_USER_DETAILS = "INSERT INTO user_details (users_user_id) VALUES (?)";

	/** Query for database for selecting users with entered email */
	private final String QUERY_FIND_EMAIL_IN_DATABASE = "SELECT * FROM users WHERE user_email = ?";

	/** Query for database for selecting users with entered login */
	private final String QUERY_FIND_LOGIN_IN_DATABASE = "SELECT * FROM users WHERE user_login = ? ";

	/** Message occurs when problems with database */
	private final String ERROR_MESSAGE_CONNECTING_TO_DATABASE = "Problems with sql connection to database.";

	/** private constructor without parameters */
	private RegistrationDAOImpl() {

	}

	/**
	 * Returns singleton object of the class
	 * 
	 * @return Object of {@link CourseDAOImpl}
	 */
	public static RegistrationDAOImpl getInstance() {
		return instance;
	}

	/**
	 * Method registrates user. Provides connection to database, checks existance of
	 * entered data and registering user.
	 * 
	 * @param user is Object of {@link UserRegistrationInfo}, which contains
	 *             information about user's entered data
	 * @throws DAOException when problems with database access occur.
	 */
	@Override
	public void registrateUser(UserRegistrationInfo userRegistrationInfo) throws DAOException {

		ConnectionPool connectionPool = ConnectionPool.getInstance();
		Connection connection = null;

		try {
			connection = connectionPool.getFreeConnection();

		} catch (ConnectionPoolException e) {
			throw new DAOException(e);
		}

		PreparedStatement statement1 = null;
		PreparedStatement statement2 = null;
		PreparedStatement statement3 = null;

		ResultSet resultSet = null;

		String userLogin;
		String userPassword;
		String userEmail;
		String userFirstName;
		String userSecondName;
		String userPatronymic;

		int userRole;
		int departmentId;

		userLogin = userRegistrationInfo.getUserLogin();
		userPassword = userRegistrationInfo.getUserPassword();
		userEmail = userRegistrationInfo.getUserEmail();
		userFirstName = userRegistrationInfo.getFirstName();
		userSecondName = userRegistrationInfo.getSecondName();
		userPatronymic = userRegistrationInfo.getPatromynic();

		userRole = userRegistrationInfo.getUserRoleID();
		departmentId = userRegistrationInfo.getDepartmentID();

		int userId;
		userId = 0;

		try {
			connection.setAutoCommit(false);

			statement1 = connection.prepareStatement(QUERY_INSERT_NEW_USER_INTO_USERS);

			statement1.setString(1, userLogin);
			statement1.setString(2, userPassword);
			statement1.setString(3, userEmail);
			statement1.setString(4, userFirstName);
			statement1.setString(5, userSecondName);
			statement1.setString(6, userPatronymic);

			statement1.setInt(7, userRole);
			statement1.setInt(8, departmentId);

			statement1.executeUpdate();

			statement2 = connection.prepareStatement(QUERY_FIND_USER_ID_BY_LOGIN);
			statement2.setString(1, userLogin);

			resultSet = statement2.executeQuery();

			if (resultSet.first()) {
				userId = resultSet.getInt(DB_PARAMETER_USER_ID);
			}

			statement3 = connection.prepareStatement(QUERY_INSERT_NEW_USER_IN_USER_DETAILS);

			statement3.setInt(1, userId);

			statement3.executeUpdate();

			connection.commit();
			connection.setAutoCommit(true);

		} catch (SQLException e) {

			logger.error(ERROR_MESSAGE_CONNECTING_TO_DATABASE, e);
			throw new DAOException(ERROR_MESSAGE_CONNECTING_TO_DATABASE, e);

		} finally {

			try {
				connectionPool.closeConnection(resultSet, statement1, statement2, statement3, connection);

			} catch (ConnectionPoolException e) {

				throw new DAOException(e);
			}
		}
	}

	/**
	 * Method checks if value of entered email already exists in database.
	 * 
	 * @param email Object of {@link String} which is value of entered email by user
	 * @return true if email is already in database, false if it is vacant
	 * @throws DAOException when problems with database access occur.
	 */
	@Override
	public boolean checkEmailIfExists(String email) throws DAOException {

		String emailToCkeck;
		emailToCkeck = email;

		ConnectionPool connectionPool = ConnectionPool.getInstance();
		Connection connection = null;

		try {
			connection = connectionPool.getFreeConnection();

		} catch (ConnectionPoolException e) {

			throw new DAOException(e);
		}

		ResultSet resultSet = null;
		PreparedStatement statement = null;

		try {
			statement = connection.prepareStatement(QUERY_FIND_EMAIL_IN_DATABASE);

			statement.setString(1, emailToCkeck);
			resultSet = statement.executeQuery();

			if (resultSet.next()) {
				return true;
			}

		} catch (SQLException e) {

			logger.error(ERROR_MESSAGE_CONNECTING_TO_DATABASE, e);
			throw new DAOException(ERROR_MESSAGE_CONNECTING_TO_DATABASE, e);

		} finally {

			try {
				connectionPool.closeConnection(resultSet, statement, connection);
			} catch (ConnectionPoolException e) {

				throw new DAOException(e);
			}

		}
		return false;

	}

	/**
	 * Method checks if value of entered login already exists in database.
	 * 
	 * @param login Object of {@link String} which is value of entered login by user
	 * @return true if login is already in database, false if it is vacant
	 * @throws DAOException when problems with database access occur.
	 */
	@Override
	public boolean checkLoginIfExists(String login) throws DAOException {

		String loginToCheck;
		loginToCheck = login;

		ConnectionPool connectionPool = ConnectionPool.getInstance();
		Connection connection = null;

		try {
			connection = connectionPool.getFreeConnection();

		} catch (ConnectionPoolException e) {

			throw new DAOException(e);
		}

		ResultSet resultSet = null;
		PreparedStatement statement = null;

		try {
			statement = connection.prepareStatement(QUERY_FIND_LOGIN_IN_DATABASE);

			statement.setString(1, loginToCheck);
			resultSet = statement.executeQuery();

			if (resultSet.next()) {
				return true;
			}

		} catch (SQLException e) {

			logger.error(ERROR_MESSAGE_CONNECTING_TO_DATABASE, e);
			throw new DAOException(ERROR_MESSAGE_CONNECTING_TO_DATABASE, e);

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
