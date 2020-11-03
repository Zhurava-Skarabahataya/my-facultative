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

public class RegistrationDAOImpl implements RegistrationDAO {

	private static final RegistrationDAOImpl instance = new RegistrationDAOImpl();

	private static final Logger logger = Logger.getLogger(RegistrationDAOImpl.class);

	private final String QUERY_INSERT_NEW_USER_INTO_USERS = "INSERT into USERS(user_login, user_password, user_email,"
			+ "first_name, second_name, patronymic, user_role_id, department_department_id, status"
			+ ") VALUES (?, ?,?,?,?,?,?,?, 1)";

	private final String DB_PARAMETER_USER_ID = "users_user_id";

	private final String QUERY_FIND_USER_ID_BY_LOGIN = "SELECT user_id FROM users WHERE user_login = ?";

	private final String QUERY_INSERT_NEW_USER_IN_USER_DETAILS = "INSERT INTO user_details (users_user_id) VALUES (?)";

	private final String QUERY_FIND_EMAIL_IN_DATABASE = "SELECT * FROM users WHERE user_email = ?";

	private final String QUERY_FIND_LOGIN_IN_DATABASE = "SELECT * FROM users WHERE user_login = ? ";

	private final String ERROR_MESSAGE_CONNECTING_TO_DATABASE = "Problems with sql connection to database.";

	private RegistrationDAOImpl() {

	}

	public static RegistrationDAOImpl getInstance() {
		return instance;
	}

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
