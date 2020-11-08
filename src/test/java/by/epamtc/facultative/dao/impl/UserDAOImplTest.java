package by.epamtc.facultative.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import by.epamtc.facultative.bean.UserInfo;
import by.epamtc.facultative.dao.DAOFactory;
import by.epamtc.facultative.dao.UserDAO;
import by.epamtc.facultative.dao.exception.DAOException;
import by.epamtc.facultative.dao.impl.pool.ConnectionPool;
import by.epamtc.facultative.dao.impl.pool.ConnectionPoolTest;
import by.epamtc.facultative.dao.impl.pool.exception.ConnectionPoolException;

public class UserDAOImplTest {

	private static final Logger logger = Logger.getLogger(UserDAOImplTest.class);

	private final String ERROR_MESSAGE_PROBLEM_SQL = "Problems with database while executing query.";

	private final String QUERY_SELECT_USER_DATA = "SELECT users.first_name, users.second_name, users.patronymic, "
			+ "users.user_email, users.department_department_id, departments.name, "
			+ "users.user_role_id, user_roles.role_name, " + " user_details.user_adress,"
			+ "user_details.user_mobile_number, user_details.user_date_of_birth, users.user_id, " + "users.status "
			+ " FROM users  JOIN user_details ON users.user_id = user_details.users_user_id "
			+ "JOIN departments ON users.department_department_id = departments.department_id "
			+ "JOIN user_roles ON user_roles.role_id = users.user_role_id" + " where users.user_login = ?";
	
	private final String QUERY_SELECT_USER_DATA_BY_ID = "SELECT users.first_name, users.second_name, users.patronymic, "
			+ "users.user_email, users.department_department_id, departments.name, "
			+ "users.user_role_id, user_roles.role_name, " + " user_details.user_adress,"
			+ "user_details.user_mobile_number, user_details.user_date_of_birth, users.user_id, " 
			+ "users.status, users.user_login "
			+ " FROM users  JOIN user_details ON users.user_id = user_details.users_user_id "
			+ "JOIN departments ON users.department_department_id = departments.department_id "
			+ "JOIN user_roles ON user_roles.role_id = users.user_role_id" + " where users.user_id = ?";

	private final String DATABASE_PARAMETER_USER_FIRST_NAME = "users.first_name";
	private final String DATABASE_PARAMETER_USER_SECOND_NAME = "users.second_name";
	private final String DATABASE_PARAMETER_USER_PATRONYMIC = "users.patronymic";
	private final String DATABASE_PARAMETER_USER_EMAIL = "users.user_email";
	private final String DATABASE_PARAMETER_USER_ROLE_ID = "users.user_role_id";
	private final String DATABASE_PARAMETER_USER_ID = "users.user_id";
	private final String DATABASE_PARAMETER_USER_ROLE_NAME = "user_roles.role_name";
	private final String DATABASE_PARAMETER_USER_RESULT = "users_has_run_courses.user_result";
	private final String DATABASE_PARAMETER_USER_STATUS = "users.status";
	private final String DATABASE_PARAMETER_USER_LOGIN = "users.user_login";
	private final String DATABASE_PARAMETER_USER_ADRESS = "user_details.user_adress";
	private final String DATABASE_PARAMETER_USER_PHONE = "user_details.user_mobile_number";
	private final String DATABASE_PARAMETER_USER_DATE_OF_BIRTH = "user_details.user_date_of_birth";
	private final String DATABASE_PARAMETER_RUN_COURSE_ID = "run_courses.run_courses_id";
	private final String DATABASE_PARAMETER_DEPARTMENT_ID = "users.department_department_id";
	private final String DATABASE_PARAMETER_DEPARTMENT_NAME = "departments.name";
	private final String DATABASE_PARAMETER_COURSE_TITLE = "courses.title";

	@BeforeClass
	public static void initializeConnectionPools() throws ConnectionPoolException {
		ConnectionPoolTest connectionPoolTest = ConnectionPoolTest.getInstance();
		ConnectionPool cp = ConnectionPool.getInstance();

		connectionPoolTest.initPool();
		cp.initPool();

	}

	@Test
	public void provideUserInfo() throws DAOException, ConnectionPoolException {

		UserInfo userTestInfo = new UserInfo();

		String testLogin = "test";
		userTestInfo.setUserLogin(testLogin);

		ConnectionPoolTest connectionPoolTest = ConnectionPoolTest.getInstance();
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		try {
			connection = connectionPoolTest.getFreeConnection();
			String query = QUERY_SELECT_USER_DATA;
			statement = connection.prepareStatement(query);
			String login;
			login = userTestInfo.getUserLogin();
			statement.setString(1, login);
			resultSet = statement.executeQuery();

			if (resultSet.next()) {

				int userId;
				int userRoleId;
				int userFacultyId;
				int userStatusId;
				String userFirstName;
				String userSecondName;
				String userPatronymic;
				String userEmail;
				String userRole;
				String userFaculty;
				String userAdress;
				String userPhone;

				LocalDate userDateOfBirth = null;
				userId = resultSet.getInt(DATABASE_PARAMETER_USER_ID);
				userFacultyId = resultSet.getInt(DATABASE_PARAMETER_DEPARTMENT_ID);
				userRoleId = resultSet.getInt(DATABASE_PARAMETER_USER_ROLE_ID);
				userStatusId = resultSet.getInt(DATABASE_PARAMETER_USER_STATUS);
				userFirstName = resultSet.getString(DATABASE_PARAMETER_USER_FIRST_NAME);
				userSecondName = resultSet.getString(DATABASE_PARAMETER_USER_SECOND_NAME);
				userPatronymic = resultSet.getString(DATABASE_PARAMETER_USER_PATRONYMIC);
				userEmail = resultSet.getString(DATABASE_PARAMETER_USER_EMAIL);
				userFaculty = resultSet.getString(DATABASE_PARAMETER_DEPARTMENT_NAME);
				userRole = resultSet.getString(DATABASE_PARAMETER_USER_ROLE_NAME);
				userAdress = resultSet.getString(DATABASE_PARAMETER_USER_ADRESS);
				userPhone = resultSet.getString(DATABASE_PARAMETER_USER_PHONE);

				Date sqlDate = resultSet.getDate(DATABASE_PARAMETER_USER_DATE_OF_BIRTH);
				if (sqlDate != null) {
					userDateOfBirth = sqlDate.toLocalDate();
				}

				userTestInfo.setUserId(userId);
				userTestInfo.setUserFirstName(userFirstName);
				userTestInfo.setUserSecondName(userSecondName);
				userTestInfo.setUserPatronymic(userPatronymic);
				userTestInfo.setUserEmail(userEmail);
				userTestInfo.setUserRoleId(userRoleId);
				userTestInfo.setUserRole(userRole);
				userTestInfo.setUserStatusId(userStatusId);
				userTestInfo.setUserFacultyId(userFacultyId);
				userTestInfo.setUserFaculty(userFaculty);

				userTestInfo.setUserAdress(userAdress);
				userTestInfo.setUserPhone(userPhone);
				userTestInfo.setUserDateOfBirth(userDateOfBirth);
			}

		} catch (SQLException e) {
			logger.error(ERROR_MESSAGE_PROBLEM_SQL, e);
			throw new DAOException(ERROR_MESSAGE_PROBLEM_SQL, e);

		} catch (ConnectionPoolException e) {
			throw new DAOException(e);

		} finally {
			try {
				connectionPoolTest.closeConnection(resultSet, statement, connection);
			} catch (ConnectionPoolException e) {
				throw new DAOException(e);
			}
		}

		UserInfo userControlInfo = new UserInfo();
		userControlInfo.setUserLogin(testLogin);

		UserDAO userDAO = DAOFactory.getInstance().getUserDAO();

		userDAO.provideUserInfo(userControlInfo);

		org.junit.Assert.assertEquals(userControlInfo, userTestInfo);

	}

	@Test
	public	void provideUserInfoById() throws DAOException{
		
		UserInfo userTestInfo = new UserInfo();
		
		int userTestId = 36;
		userTestInfo.setUserId(userTestId);
		
		ConnectionPoolTest connectionPoolTest = ConnectionPoolTest.getInstance();
		Connection connection = null;

		PreparedStatement statement = null;
		ResultSet resultSet = null;

		try {
			connection = connectionPoolTest.getFreeConnection();

			String query = QUERY_SELECT_USER_DATA_BY_ID;

			statement = connection.prepareStatement(query);

			int userId;
			userId = userTestInfo.getUserId();

			statement.setInt(1, userId);

			resultSet = statement.executeQuery();

			if (resultSet.next()) {

				int userRoleId;
				int userFacultyId;
				int userStatusId;

				String userFirstName;
				String userSecondName;
				String userPatronymic;
				String userEmail;
				String userRole;
				String userFaculty;
				String userAdress;
				String userPhone;
				String userLogin;

				LocalDate userDateOfBirth = null;

				userId = resultSet.getInt(DATABASE_PARAMETER_USER_ID);
				userFacultyId = resultSet.getInt(DATABASE_PARAMETER_DEPARTMENT_ID);
				userRoleId = resultSet.getInt(DATABASE_PARAMETER_USER_ROLE_ID);
				userStatusId = resultSet.getInt(DATABASE_PARAMETER_USER_STATUS);

				userFirstName = resultSet.getString(DATABASE_PARAMETER_USER_FIRST_NAME);
				userSecondName = resultSet.getString(DATABASE_PARAMETER_USER_SECOND_NAME);
				userPatronymic = resultSet.getString(DATABASE_PARAMETER_USER_PATRONYMIC);
				userEmail = resultSet.getString(DATABASE_PARAMETER_USER_EMAIL);
				userFaculty = resultSet.getString(DATABASE_PARAMETER_DEPARTMENT_NAME);
				userRole = resultSet.getString(DATABASE_PARAMETER_USER_ROLE_NAME);
				userAdress = resultSet.getString(DATABASE_PARAMETER_USER_ADRESS);
				userPhone = resultSet.getString(DATABASE_PARAMETER_USER_PHONE);
				userLogin = resultSet.getString(DATABASE_PARAMETER_USER_LOGIN);

				Date sqlDate = resultSet.getDate(DATABASE_PARAMETER_USER_DATE_OF_BIRTH);

				if (sqlDate != null) {
					userDateOfBirth = sqlDate.toLocalDate();
				}

				userTestInfo.setUserId(userId);

				userTestInfo.setUserFirstName(userFirstName);
				userTestInfo.setUserSecondName(userSecondName);
				userTestInfo.setUserPatronymic(userPatronymic);
				userTestInfo.setUserEmail(userEmail);
				userTestInfo.setUserLogin(userLogin);

				userTestInfo.setUserRoleId(userRoleId);
				userTestInfo.setUserRole(userRole);
				userTestInfo.setUserStatusId(userStatusId);

				userTestInfo.setUserFacultyId(userFacultyId);
				userTestInfo.setUserFaculty(userFaculty);

				userTestInfo.setUserAdress(userAdress);
				userTestInfo.setUserPhone(userPhone);
				userTestInfo.setUserDateOfBirth(userDateOfBirth);
			}

		} catch (SQLException e) {
			logger.error(ERROR_MESSAGE_PROBLEM_SQL, e);
			throw new DAOException(ERROR_MESSAGE_PROBLEM_SQL, e);

		} catch (ConnectionPoolException e) {
			throw new DAOException(e);

		} finally {
			try {
				connectionPoolTest.closeConnection(resultSet, statement, connection);
			} catch (ConnectionPoolException e) {
				throw new DAOException(e);
			}
		}
		
		UserInfo userControlInfo = new UserInfo();
		userControlInfo.setUserId(userTestId);
		
		UserDAO userDAO = DAOFactory.getInstance().getUserDAO();

		userDAO.provideUserInfoById(userControlInfo);
		
		org.junit.Assert.assertEquals(userControlInfo, userTestInfo);

		
	}
}
