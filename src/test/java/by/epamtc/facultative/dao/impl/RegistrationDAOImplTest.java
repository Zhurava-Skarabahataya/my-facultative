package by.epamtc.facultative.dao.impl;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import by.epamtc.facultative.bean.UserRegistrationInfo;
import by.epamtc.facultative.dao.exception.DAOException;
import by.epamtc.facultative.dao.impl.pool.ConnectionPool;
import by.epamtc.facultative.dao.impl.pool.ConnectionPoolTest;
import by.epamtc.facultative.dao.impl.pool.exception.ConnectionPoolException;

public class RegistrationDAOImplTest {
	
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

	
	@BeforeClass
	public static void initializeConnectionPools() throws ConnectionPoolException, IOException {
		ConnectionPoolTest connectionPoolTest = ConnectionPoolTest.getInstance();
		ConnectionPool cp = ConnectionPool.getInstance();

		connectionPoolTest.initPool();
		cp.initPool();
		
		FileInputStream in = new FileInputStream("db.properties");
		Properties props = new Properties();
		props.load(in);
		in.close();

		FileOutputStream out = new FileOutputStream("db.properties");
		props.setProperty("db.jdbc_url", "jdbc:mysql://127.0.0.1:3306/facultative_test_copy?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC");
		props.store(out, null);
		out.close();


	}
	
	@Test
	public void registrateUser() throws DAOException {
		
		UserRegistrationInfo userRegistrationInfo = new UserRegistrationInfo();
		
		userRegistrationInfo.setFirstName("f");
		userRegistrationInfo.setSecondName("s");
		userRegistrationInfo.setPatromynic("p");
		userRegistrationInfo.setUserEmail("email@email.email");
		userRegistrationInfo.setUserLogin("login");
		userRegistrationInfo.setDepartmentID(1);
		userRegistrationInfo.setUserPassword("1");
		userRegistrationInfo.setUserRoleID(1);

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
		
		PreparedStatement searchRegistratedUser = null;

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

			throw new DAOException(ERROR_MESSAGE_CONNECTING_TO_DATABASE, e);

		} finally {

			try {
				connectionPool.closeConnection(resultSet, statement1, statement2, statement3, connection);

			} catch (ConnectionPoolException e) {

				throw new DAOException(e);
			}
		}
		
		
		
		
	}
	
	@AfterClass
	public static void closeTestDatabase() throws IOException {
		FileInputStream in = new FileInputStream("db.properties");
		Properties props = new Properties();
		props.load(in);
		in.close();

		FileOutputStream out = new FileOutputStream("db.properties");
		props.setProperty("db.jdbc_url", "jdbc:mysql://127.0.0.1:3306/facultative?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC");
		props.store(out, null);
		out.close();
	}

}
