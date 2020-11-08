package by.epamtc.facultative.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import by.epamtc.facultative.dao.QueryManager;
import by.epamtc.facultative.dao.UserDAO;
import by.epamtc.facultative.dao.exception.DAOException;
import by.epamtc.facultative.dao.impl.pool.ConnectionPool;
import by.epamtc.facultative.dao.impl.pool.exception.ConnectionPoolException;
import by.epamtc.facultative.bean.DepartmentStaff;
import by.epamtc.facultative.bean.Mark;
import by.epamtc.facultative.bean.UserInfo;

/**
 * Sends SQL requests concerning info about user: personal info, students' marks, user's courses 
 * to the database using Connections from ConnectionPool.
 */

public class UserDAOImpl implements UserDAO {

	/** A single instance of the class (pattern Singleton) */
	private static final UserDAOImpl instance = new UserDAOImpl();

	/**	Logger of the class */
	private static final Logger logger = Logger.getLogger(UserDAOImpl.class);

	/** An error message sending while throwing exception */
	private final String ERROR_MESSAGE_PROBLEM_SQL = "Problems with database while executing query.";

	/** Database parameter for user first name */
	private final String DATABASE_PARAMETER_USER_FIRST_NAME = "users.first_name";
	/** Database parameter for user second name */
	private final String DATABASE_PARAMETER_USER_SECOND_NAME = "users.second_name";
	/** Database parameter for user patronymic */
	private final String DATABASE_PARAMETER_USER_PATRONYMIC = "users.patronymic";
	/** Database parameter for user email */
	private final String DATABASE_PARAMETER_USER_EMAIL = "users.user_email";
	/** Database parameter for user role id */
	private final String DATABASE_PARAMETER_USER_ROLE_ID = "users.user_role_id";
	/** Database parameter for user id */
	private final String DATABASE_PARAMETER_USER_ID = "users.user_id";
	/** Database parameter for user role name */
	private final String DATABASE_PARAMETER_USER_ROLE_NAME = "user_roles.role_name";
	/** Database parameter for user result */
	private final String DATABASE_PARAMETER_USER_RESULT = "users_has_run_courses.user_result";
	/** Database parameter for user status */
	private final String DATABASE_PARAMETER_USER_STATUS = "users.status";
	/** Database parameter for user login */
	private final String DATABASE_PARAMETER_USER_LOGIN = "users.user_login";
	/** Database parameter for user adress */
	private final String DATABASE_PARAMETER_USER_ADRESS = "user_details.user_adress";
	/** Database parameter for user phone */
	private final String DATABASE_PARAMETER_USER_PHONE = "user_details.user_mobile_number";
	/** Database parameter for user date of birth */
	private final String DATABASE_PARAMETER_USER_DATE_OF_BIRTH = "user_details.user_date_of_birth";
	/** Database parameter for run course id */
	private final String DATABASE_PARAMETER_RUN_COURSE_ID = "run_courses.run_courses_id";
	/** Database parameter for department id */
	private final String DATABASE_PARAMETER_DEPARTMENT_ID = "users.department_department_id";
	/** Database parameter for user department name */
	private final String DATABASE_PARAMETER_DEPARTMENT_NAME = "departments.name";
	/** Database parameter for user course title */
	private final String DATABASE_PARAMETER_COURSE_TITLE = "courses.title";

	
	
	private final String QUERY_SELECT_USER_DATA = "query.select_user_data";
	
	private final String QUERY_SELECT_USER_DATA_BY_ID = "query.select_user_data_by_id";

	private final String QUERY_FOR_USER_DATA_BY_ID = "SELECT users.first_name, users.second_name, "
			+ "users.patronymic, users.user_email, users.department_department_id, departments.name,"
			+ " users.user_role_id, user_roles.role_name, user_details.user_adress, "
			+ "user_details.user_mobile_number, user_details.user_date_of_birth, users.status, users.user_login "
			+ "FROM users  " + "JOIN user_details ON users.user_id = user_details.users_user_id "
			+ "JOIN departments ON users.department_department_id = departments.department_id "
			+ "JOIN user_roles ON user_roles.role_id = users.user_role_id " + "WHERE users.user_id = ?";

	private final String QUERY_UPDATE_USER_DATA_IN_USERS = "UPDATE users "
			+ "SET users.first_name = ? , users.second_name = ?, users.patronymic = ?, "
			+ " users.department_department_id = ? WHERE users.user_id = ?";

	private final String QUERY_UPDATE_USER_DATA_IN_USER_DETAILS = "UPDATE user_details "
			+ "SET user_adress = ? , user_mobile_number = ?, user_date_of_birth = ?" + " WHERE users_user_id = ?";

	private final String QUERY_FOR_CHANGING_EMPLOYEE_STATUS = "UPDATE users " + "SET status = ? WHERE user_id = ?";

	private final String QUERY_FOR_FINDING_STAFF_OF_DEPARTMENT = "SELECT "
			+ "users.user_id, users.first_name, users.second_name, users.patronymic, users.user_login, "
			+ "users.user_role_id, users.status, user_roles.role_name, "
			+ "user_details.user_mobile_number, user_details.user_adress, " + "user_details.user_date_of_birth "
			+ "FROM users JOIN user_details ON users.user_id = user_details.users_user_id "
			+ " JOIN user_roles ON user_roles.role_id = users.user_role_id "
			+ "WHERE users.user_role_id = 2 AND users.department_department_id = ?";

	private final String QUERY_FOR_FINDING_STAFF_OF_UNIVERSITY = "SELECT "
			+ "users.user_id, users.first_name, users.second_name, users.patronymic, users.user_login, "
			+ "departments.name, departments.department_id, "
			+ "users.user_role_id, users.status, user_roles.role_name, "
			+ "user_details.user_mobile_number, user_details.user_adress, " + "user_details.user_date_of_birth "
			+ "FROM users JOIN user_details ON users.user_id = user_details.users_user_id "
			+ " JOIN user_roles ON user_roles.role_id = users.user_role_id "
			+ "JOIN departments ON departments.department_id = users.department_department_id "
			+ "WHERE users.user_role_id = 2 OR users.user_role_id = 3";

	private final String QUERY_FOR_STUDENT_RESULTS = "SELECT users_has_run_courses.user_result, "
			+ "run_courses.run_courses_id, courses.title FROM users_has_run_courses "
			+ "JOIN run_courses ON run_courses.run_courses_id = users_has_run_courses.run_courses_id "
			+ "JOIN courses ON courses.course_id = run_courses.courses_course_id"
			+ " WHERE users_has_run_courses.users_user_id = ?";
	
	private final String QUERY_FOR_STUDENT_RESULTS_BY_LOGIN = "SELECT users_has_run_courses.user_result, "
			+ "run_courses.run_courses_id, courses.title, users.user_id "
			+ "FROM users JOIN users_has_run_courses "
			+ "ON users.user_id = users_has_run_courses.users_user_id "
			+ "JOIN run_courses "
			+ "ON run_courses.run_courses_id = users_has_run_courses.run_courses_id "
			+ "JOIN courses ON courses.course_id = run_courses.courses_course_id "
			+ "WHERE users.user_login = ?";

	private UserDAOImpl() {

	}

	public static UserDAOImpl getInstance() {
		return instance;
	}

	@Override
	public List<Mark> findStudentResults(int studentId) throws DAOException {

		List<Mark> marks = new ArrayList<Mark>();

		ConnectionPool connectionPool = ConnectionPool.getInstance();
		Connection connection = null;

		PreparedStatement statement = null;
		ResultSet resultSet = null;

		try {
			connection = connectionPool.getFreeConnection();

			statement = connection.prepareStatement(QUERY_FOR_STUDENT_RESULTS);

			statement.setInt(1, studentId);

			resultSet = statement.executeQuery();

			while (resultSet.next()) {

				int markNumber = resultSet.getInt(DATABASE_PARAMETER_USER_RESULT);

				if (markNumber != 0) {

					int runCourseId;
					String courseTitle;

					runCourseId = resultSet.getInt(DATABASE_PARAMETER_RUN_COURSE_ID);
					courseTitle = resultSet.getString(DATABASE_PARAMETER_COURSE_TITLE);

					Mark mark = new Mark();
					mark.setMarkGrade(markNumber);
					mark.setRunCourseId(runCourseId);
					mark.setCourseTitle(courseTitle);

					marks.add(mark);
				}
			}

		} catch (ConnectionPoolException e) {
			throw new DAOException(e);

		} catch (SQLException e) {
			logger.error(ERROR_MESSAGE_PROBLEM_SQL, e);
			throw new DAOException(ERROR_MESSAGE_PROBLEM_SQL, e);

		} finally {
			try {
				connectionPool.closeConnection(resultSet, statement, connection);

			} catch (ConnectionPoolException e) {
				throw new DAOException(e);
			}
		}
		return marks;
	}
	
	@Override
	public List<Mark> findStudentResults(String studentLogin) throws DAOException {

		List<Mark> marks = new ArrayList<Mark>();

		ConnectionPool connectionPool = ConnectionPool.getInstance();
		Connection connection = null;

		PreparedStatement statement = null;
		ResultSet resultSet = null;

		try {
			connection = connectionPool.getFreeConnection();

			statement = connection.prepareStatement(QUERY_FOR_STUDENT_RESULTS_BY_LOGIN);

			statement.setString(1, studentLogin);

			resultSet = statement.executeQuery();

			while (resultSet.next()) {

				int markNumber = resultSet.getInt(DATABASE_PARAMETER_USER_RESULT);

				if (markNumber != 0) {

					int runCourseId;
					String courseTitle;

					runCourseId = resultSet.getInt(DATABASE_PARAMETER_RUN_COURSE_ID);
					courseTitle = resultSet.getString(DATABASE_PARAMETER_COURSE_TITLE);

					Mark mark = new Mark();
					mark.setMarkGrade(markNumber);
					mark.setRunCourseId(runCourseId);
					mark.setCourseTitle(courseTitle);

					marks.add(mark);
				}
			}

		} catch (ConnectionPoolException e) {
			throw new DAOException(e);

		} catch (SQLException e) {
			logger.error(ERROR_MESSAGE_PROBLEM_SQL, e);
			throw new DAOException(ERROR_MESSAGE_PROBLEM_SQL, e);

		} finally {
			try {
				connectionPool.closeConnection(resultSet, statement, connection);

			} catch (ConnectionPoolException e) {
				throw new DAOException(e);
			}
		}
		return marks;
	}

	@Override
	public void provideUserInfo(UserInfo userPageInfo) throws DAOException {

		ConnectionPool connectionPool = ConnectionPool.getInstance();
		Connection connection = null;

		PreparedStatement statement = null;
		ResultSet resultSet = null;

		try {
			connection = connectionPool.getFreeConnection();

			QueryManager queryManager = QueryManager.getInstance();
			String query = queryManager.getValue(QUERY_SELECT_USER_DATA);

			statement = connection.prepareStatement(query);

			String login;
			login = userPageInfo.getUserLogin();

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

				userPageInfo.setUserId(userId);

				userPageInfo.setUserFirstName(userFirstName);
				userPageInfo.setUserSecondName(userSecondName);
				userPageInfo.setUserPatronymic(userPatronymic);
				userPageInfo.setUserEmail(userEmail);

				userPageInfo.setUserRoleId(userRoleId);
				userPageInfo.setUserRole(userRole);
				userPageInfo.setUserStatusId(userStatusId);

				userPageInfo.setUserFacultyId(userFacultyId);
				userPageInfo.setUserFaculty(userFaculty);

				userPageInfo.setUserAdress(userAdress);
				userPageInfo.setUserPhone(userPhone);
				userPageInfo.setUserDateOfBirth(userDateOfBirth);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			logger.error(ERROR_MESSAGE_PROBLEM_SQL, e);
			throw new DAOException(ERROR_MESSAGE_PROBLEM_SQL, e);

		} catch (ConnectionPoolException e) {
			e.printStackTrace();

			throw new DAOException(e);

		} finally {
			try {
				connectionPool.closeConnection(resultSet, statement, connection);
			} catch (ConnectionPoolException e) {
				e.printStackTrace();

				throw new DAOException(e);
			}
		}

	}
	
	@Override
	public	void provideUserInfoById(UserInfo userPageInfo) throws DAOException{
		ConnectionPool connectionPool = ConnectionPool.getInstance();
		Connection connection = null;

		PreparedStatement statement = null;
		ResultSet resultSet = null;

		try {
			connection = connectionPool.getFreeConnection();

			String query = QUERY_SELECT_USER_DATA_BY_ID;

			statement = connection.prepareStatement(query);

			int userId;
			userId = userPageInfo.getUserId();

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

				userPageInfo.setUserId(userId);

				userPageInfo.setUserFirstName(userFirstName);
				userPageInfo.setUserSecondName(userSecondName);
				userPageInfo.setUserPatronymic(userPatronymic);
				userPageInfo.setUserEmail(userEmail);
				userPageInfo.setUserLogin(userLogin);

				userPageInfo.setUserRoleId(userRoleId);
				userPageInfo.setUserRole(userRole);
				userPageInfo.setUserStatusId(userStatusId);

				userPageInfo.setUserFacultyId(userFacultyId);
				userPageInfo.setUserFaculty(userFaculty);

				userPageInfo.setUserAdress(userAdress);
				userPageInfo.setUserPhone(userPhone);
				userPageInfo.setUserDateOfBirth(userDateOfBirth);
			}

		} catch (SQLException e) {
			logger.error(ERROR_MESSAGE_PROBLEM_SQL, e);
			throw new DAOException(ERROR_MESSAGE_PROBLEM_SQL, e);

		} catch (ConnectionPoolException e) {
			throw new DAOException(e);

		} finally {
			try {
				connectionPool.closeConnection(resultSet, statement, connection);
			} catch (ConnectionPoolException e) {
				throw new DAOException(e);
			}
		}
		
	}

	@Override
	public void updateUserInfo(UserInfo userPageInfo) throws DAOException {

		ConnectionPool connectionPool = ConnectionPool.getInstance();
		Connection connection = null;

		PreparedStatement statement1 = null;
		PreparedStatement statement2 = null;
		try {
			connection = connectionPool.getFreeConnection();

			statement1 = connection.prepareStatement(QUERY_UPDATE_USER_DATA_IN_USERS);
			statement2 = connection.prepareStatement(QUERY_UPDATE_USER_DATA_IN_USER_DETAILS);

			statement1.setString(1, userPageInfo.getUserFirstName());
			statement1.setString(2, userPageInfo.getUserSecondName());
			statement1.setString(3, userPageInfo.getUserPatronymic());
			statement1.setInt(4, userPageInfo.getUserFacultyId());

			statement2.setString(1, userPageInfo.getUserAdress());
			statement2.setString(2, userPageInfo.getUserPhone());

			if (userPageInfo.getUserDateOfBirth() != null) {
				Date sqlDate = Date.valueOf(userPageInfo.getUserDateOfBirth());
				statement2.setDate(3, sqlDate);
			} else {
				statement2.setDate(3, null);
			}

			statement1.setInt(5, userPageInfo.getUserId());
			statement2.setInt(4, userPageInfo.getUserId());

			statement1.executeUpdate();
			statement2.executeUpdate();

		} catch (SQLException e) {
			logger.error(ERROR_MESSAGE_PROBLEM_SQL, e);
			throw new DAOException(ERROR_MESSAGE_PROBLEM_SQL, e);

		} catch (ConnectionPoolException e) {
			throw new DAOException(e);

		} finally {
			try {
				connectionPool.closeConnection(statement1, statement2, connection);

			} catch (ConnectionPoolException e) {
				throw new DAOException(e);

			}
		}

	}

	@Override
	public DepartmentStaff findFacultyStaffInfo(int departmentId) throws DAOException {

		DepartmentStaff facultyStaffInfo = new DepartmentStaff();

		List<UserInfo> workingLecturers = new ArrayList<UserInfo>();
		List<UserInfo> notApprovedLecturers = new ArrayList<UserInfo>();
		List<UserInfo> firedLecturers = new ArrayList<UserInfo>();

		ConnectionPool connectionPool = ConnectionPool.getInstance();
		Connection connection = null;

		PreparedStatement statement = null;
		ResultSet resultSet = null;

		try {
			connection = connectionPool.getFreeConnection();
			statement = connection.prepareStatement(QUERY_FOR_FINDING_STAFF_OF_DEPARTMENT);

			statement.setInt(1, departmentId);

			resultSet = statement.executeQuery();

			while (resultSet.next()) {

				int userId;
				int userRoleId;
				int userStatus;

				String userFirstName;
				String userSecondName;
				String userPatronymic;
				String userLogin;
				String userRoleName;
				String userMobile;
				String userAdress;

				LocalDate userDateOfBirth;
				Date sqlDate;

				userId = resultSet.getInt(DATABASE_PARAMETER_USER_ID);
				userRoleId = resultSet.getInt(DATABASE_PARAMETER_USER_ROLE_ID);
				userStatus = resultSet.getInt(DATABASE_PARAMETER_USER_STATUS);

				userFirstName = resultSet.getString(DATABASE_PARAMETER_USER_FIRST_NAME);
				userSecondName = resultSet.getString(DATABASE_PARAMETER_USER_SECOND_NAME);
				userPatronymic = resultSet.getString(DATABASE_PARAMETER_USER_PATRONYMIC);
				userLogin = resultSet.getString(DATABASE_PARAMETER_USER_LOGIN);
				userRoleName = resultSet.getString(DATABASE_PARAMETER_USER_ROLE_NAME);
				userMobile = resultSet.getString(DATABASE_PARAMETER_USER_PHONE);
				userAdress = resultSet.getString(DATABASE_PARAMETER_USER_ADRESS);

				userDateOfBirth = null;
				sqlDate = resultSet.getDate(DATABASE_PARAMETER_USER_DATE_OF_BIRTH);
				if (sqlDate != null) {
					userDateOfBirth = sqlDate.toLocalDate();
				}

				UserInfo employee = new UserInfo();

				employee.setUserId(userId);
				employee.setUserRoleId(userRoleId);
				employee.setUserStatusId(userStatus);

				employee.setUserFirstName(userFirstName);
				employee.setUserSecondName(userSecondName);
				employee.setUserPatronymic(userPatronymic);
				employee.setUserLogin(userLogin);
				employee.setUserRole(userRoleName);
				employee.setUserPhone(userMobile);
				employee.setUserAdress(userAdress);
				employee.setUserDateOfBirth(userDateOfBirth);

				if (userStatus == 1) {
					notApprovedLecturers.add(employee);
				} else if (userStatus == 2) {
					workingLecturers.add(employee);
				} else {
					firedLecturers.add(employee);
				}
			}
		} catch (ConnectionPoolException e) {
			throw new DAOException(e);

		} catch (SQLException e) {
			logger.error(ERROR_MESSAGE_PROBLEM_SQL, e);
			throw new DAOException(ERROR_MESSAGE_PROBLEM_SQL, e);

		} finally {
			try {
				connectionPool.closeConnection(resultSet, statement, connection);
			} catch (ConnectionPoolException e) {
				throw new DAOException(e);
			}
		}
		facultyStaffInfo.setWorkingStaff(workingLecturers);
		facultyStaffInfo.setNotApprovedStaff(notApprovedLecturers);
		facultyStaffInfo.setFiredStaff(firedLecturers);

		return facultyStaffInfo;
	}

	@Override
	public DepartmentStaff findAllFacultiesStaffInfo() throws DAOException {

		DepartmentStaff allFacultiesStaffInfo = new DepartmentStaff();

		List<UserInfo> allStaff = new ArrayList<UserInfo>();

		ConnectionPool connectionPool = ConnectionPool.getInstance();
		Connection connection = null;

		PreparedStatement statement = null;
		ResultSet resultSet = null;

		try {
			connection = connectionPool.getFreeConnection();
			statement = connection.prepareStatement(QUERY_FOR_FINDING_STAFF_OF_UNIVERSITY);

			resultSet = statement.executeQuery();

			while (resultSet.next()) {

				int userId;
				int departmentId;
				int userRoleId;
				int userStatus;

				String userFirstName;
				String userSecondName;
				String userPatronymic;
				String userLogin;
				String userRoleName;
				String userMobile;
				String userAdress;
				String departmentName;

				LocalDate userDateOfBirth;
				Date sqlDate;

				userId = resultSet.getInt(DATABASE_PARAMETER_USER_ID);
				userRoleId = resultSet.getInt(DATABASE_PARAMETER_USER_ROLE_ID);
				userStatus = resultSet.getInt(DATABASE_PARAMETER_USER_STATUS);
				departmentId = resultSet.getInt(DATABASE_PARAMETER_DEPARTMENT_ID);

				userFirstName = resultSet.getString(DATABASE_PARAMETER_USER_FIRST_NAME);
				userSecondName = resultSet.getString(DATABASE_PARAMETER_USER_SECOND_NAME);
				userPatronymic = resultSet.getString(DATABASE_PARAMETER_USER_PATRONYMIC);
				userLogin = resultSet.getString(DATABASE_PARAMETER_USER_LOGIN);
				userRoleName = resultSet.getString(DATABASE_PARAMETER_USER_ROLE_NAME);
				userMobile = resultSet.getString(DATABASE_PARAMETER_USER_PHONE);
				userAdress = resultSet.getString(DATABASE_PARAMETER_USER_ADRESS);
				departmentName = resultSet.getString(DATABASE_PARAMETER_DEPARTMENT_NAME);

				userDateOfBirth = null;
				sqlDate = resultSet.getDate(DATABASE_PARAMETER_USER_DATE_OF_BIRTH);
				if (sqlDate != null) {
					userDateOfBirth = sqlDate.toLocalDate();
				}

				UserInfo employee = new UserInfo();

				employee.setUserId(userId);
				employee.setUserRoleId(userRoleId);
				employee.setUserStatusId(userStatus);
				employee.setUserFacultyId(departmentId);

				employee.setUserFirstName(userFirstName);
				employee.setUserSecondName(userSecondName);
				employee.setUserPatronymic(userPatronymic);
				employee.setUserLogin(userLogin);
				employee.setUserRole(userRoleName);
				employee.setUserPhone(userMobile);
				employee.setUserAdress(userAdress);
				employee.setUserDateOfBirth(userDateOfBirth);
				employee.setUserFaculty(departmentName);

				allStaff.add(employee);
			}
			allFacultiesStaffInfo.setAllStaff(allStaff);

		} catch (ConnectionPoolException e) {
			throw new DAOException(e);

		} catch (SQLException e) {
			logger.error(ERROR_MESSAGE_PROBLEM_SQL, e);
			throw new DAOException(ERROR_MESSAGE_PROBLEM_SQL, e);

		} finally {
			try {
				connectionPool.closeConnection(resultSet, statement, connection);
			} catch (ConnectionPoolException e) {
				throw new DAOException(e);
			}
		}
		return allFacultiesStaffInfo;
	}

	@Override
	public void changeEmployeeStatus(int employeeId, int status) throws DAOException {

		ConnectionPool connectionPool = ConnectionPool.getInstance();

		Connection connection = null;
		PreparedStatement statement = null;

		try {
			connection = connectionPool.getFreeConnection();

			statement = connection.prepareStatement(QUERY_FOR_CHANGING_EMPLOYEE_STATUS);

			statement.setInt(1, status);
			statement.setInt(2, employeeId);

			statement.executeUpdate();

		} catch (SQLException e) {
			logger.error(ERROR_MESSAGE_PROBLEM_SQL, e);
			throw new DAOException(ERROR_MESSAGE_PROBLEM_SQL, e);

		} catch (ConnectionPoolException e) {
			throw new DAOException(e);

		} finally {
			try {
				connectionPool.closeConnection(statement, connection);
			} catch (ConnectionPoolException e) {
				throw new DAOException(e);

			}

		}

	}

	@Override
	public void findUserInfoById(UserInfo userInfo) throws DAOException {

		int userId;
		userId = userInfo.getUserId();

		ConnectionPool connectionPool = ConnectionPool.getInstance();
		Connection connection = null;

		PreparedStatement statement = null;

		ResultSet resultSet = null;

		try {
			connection = connectionPool.getFreeConnection();

			String query = QUERY_FOR_USER_DATA_BY_ID;

			statement = connection.prepareStatement(query);

			statement.setInt(1, userId);

			resultSet = statement.executeQuery();

			if (resultSet.next()) {

				int userStatusId;
				int userRoleId;
				int userFacultyId;

				String userFirstName;
				String userSecondName;
				String userPatronymic;
				String userEmail;

				String userRole;
				String userFaculty;
				String userAdress;
				String userPhone;
				String userLogin;

				LocalDate userDateOfBirth;
				Date sqlDate;

				userFirstName = resultSet.getString(DATABASE_PARAMETER_USER_FIRST_NAME);
				userSecondName = resultSet.getString(DATABASE_PARAMETER_USER_SECOND_NAME);
				userPatronymic = resultSet.getString(DATABASE_PARAMETER_USER_PATRONYMIC);
				userEmail = resultSet.getString(DATABASE_PARAMETER_USER_EMAIL);
				userLogin = resultSet.getString(DATABASE_PARAMETER_USER_LOGIN);

				userFacultyId = resultSet.getInt(DATABASE_PARAMETER_DEPARTMENT_ID);
				userFaculty = resultSet.getString(DATABASE_PARAMETER_DEPARTMENT_NAME);

				userRoleId = resultSet.getInt(DATABASE_PARAMETER_USER_ROLE_ID);
				userRole = resultSet.getString(DATABASE_PARAMETER_USER_ROLE_NAME);
				userStatusId = resultSet.getInt(DATABASE_PARAMETER_USER_STATUS);

				userAdress = resultSet.getString(DATABASE_PARAMETER_USER_ADRESS);
				userPhone = resultSet.getString(DATABASE_PARAMETER_USER_PHONE);

				sqlDate = resultSet.getDate(DATABASE_PARAMETER_USER_DATE_OF_BIRTH);
				userDateOfBirth = null;

				if (sqlDate != null) {
					userDateOfBirth = sqlDate.toLocalDate();
				}

				userInfo.setUserFirstName(userFirstName);
				userInfo.setUserSecondName(userSecondName);
				userInfo.setUserPatronymic(userPatronymic);
				userInfo.setUserEmail(userEmail);
				userInfo.setUserLogin(userLogin);

				userInfo.setUserRoleId(userRoleId);
				userInfo.setUserRole(userRole);
				userInfo.setUserStatusId(userStatusId);

				userInfo.setUserFacultyId(userFacultyId);
				userInfo.setUserFaculty(userFaculty);

				userInfo.setUserAdress(userAdress);
				userInfo.setUserPhone(userPhone);
				userInfo.setUserDateOfBirth(userDateOfBirth);
			}

		} catch (SQLException e) {
			logger.error(ERROR_MESSAGE_PROBLEM_SQL, e);
			throw new DAOException(ERROR_MESSAGE_PROBLEM_SQL, e);

		} catch (ConnectionPoolException e) {
			throw new DAOException(e);

		} finally {
			try {
				connectionPool.closeConnection(resultSet, statement, connection);
			} catch (ConnectionPoolException e) {
				throw new DAOException(e);
			}
		}
	}

}
