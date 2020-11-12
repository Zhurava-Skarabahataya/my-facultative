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

import by.epamtc.facultative.bean.Department;
import by.epamtc.facultative.bean.Mark;
import by.epamtc.facultative.bean.Course;
import by.epamtc.facultative.bean.UserInfo;
import by.epamtc.facultative.dao.DepartmentDAO;
import by.epamtc.facultative.dao.exception.DAOException;
import by.epamtc.facultative.dao.impl.pool.ConnectionPool;
import by.epamtc.facultative.dao.impl.pool.exception.ConnectionPoolException;
import by.epamtc.facultative.service.FullNameService;
import by.epamtc.facultative.service.ServiceProvider;

/**
 * Implementation if {@link DepartmentDAO}. Sends SQL requests concerning info
 * about departments: description, courses, students and lecturers of the
 * department, to the database using Connections from ConnectionPool. All
 * methods use {@link ConnectionPool} to connect to database.
 */
public class DepartmentDAOImpl implements DepartmentDAO {

	/** A single instance of the class (pattern Singleton) */
	private static final DepartmentDAOImpl instance = new DepartmentDAOImpl();

	/** Logger of the class */
	private static final Logger logger = Logger.getLogger(DepartmentDAOImpl.class);

	/** Message about problems with database executing query */
	private final String ERROR_MESSAGE_PROBLEM_SQL = "Problems with database while executing query.";

	/** Database parameter department id */
	private final String DATABASE_PARAMETER_DEPARTMENT_ID = "departments.department_id";
	/** Database parameter dean id */
	private final String DATABASE_PARAMETER_DEAN_ID = "departments.dean_id";
	/** Database parameter department name */
	private final String DATABASE_PARAMETER_DEPARTMENT_NAME = "departments.name";
	/** Database parameter department description */
	private final String DATABASE_PARAMETER_DEPARTMENT_DESCRIPTION = "departments.description";
	/** Database parameter user login */
	private final String DATABASE_PARAMETER_USER_LOGIN = "users.user_login";
	/** Database parameter user id */
	private final String DATABASE_PARAMETER_USER_ID = "users.user_id";
	/** Database parameter user first name */
	private final String DATABASE_PARAMETER_USER_FIRST_NAME = "users.first_name";
	/** Database parameter user second name */
	private final String DATABASE_PARAMETER_USER_SECOND_NAME = "users.second_name";
	/** Database parameter user patronymic */
	private final String DATABASE_PARAMETER_USER_PATRONYMIC = "users.patronymic";
	/** Database parameter user phone */
	private final String DATABASE_PARAMETER_USER_PHONE = "user_details.user_mobile_number";
	/** Database parameter user adres */
	private final String DATABASE_PARAMETER_USER_ADRESS = "user_details.user_adress";
	/** Database parameter user role id */
	private final String DATABASE_PARAMETER_USER_ROLE_ID = "users.user_role_id";
	/** Database parameter user birth date */
	private final String DATABASE_PARAMETER_USER_BIRTH_DATE = "user_details.user_date_of_birth";
	/** Database parameter course title */
	private final String DATABASE_PARAMETER_COURSE_TITLE = "courses.title";
	/** Database parameter course description */
	private final String DATABASE_PARAMETER_COURSE_DESCRIPTION = "courses.description";
	/** Database parameter course id */
	private final String DATABASE_PARAMETER_COURSE_ID = "courses.course_id";

	/** Query for database for selecting detailed info about all departments */
	private final String QUERY_FOR_ALL_DEPARTMENTS = "SELECT "
			+ "departments.department_id, departments.name, departments.description, departments.dean_id,"
			+ " users.first_name, users.second_name, users.patronymic, users.user_login"
			+ " FROM departments JOIN users ON departments.dean_id = users.user_id";

	/** Query for database for selecting available courses in department */
	private final String QUERY_FOR_COURSES_IN_DEPARTMENT = "SELECT * " + "FROM courses WHERE courses.department_id = ?";

	/** Query for database for selecting lecturers in department */
	private final String QUERY_FOR_LECTURERS_IN_DEPARTMENT = "SELECT "
			+ "users.first_name, users.second_name, users.patronymic, users.user_login, users.user_role_id, "
			+ " departments.name  "
			+ "FROM users JOIN departments ON users.department_department_id = departments.department_id "
			+ " WHERE users.user_role_id > 1 AND users.user_role_id <4  AND users.department_department_id = ? AND users.status < 4";

	/** Query for database for selecting students in department */
	private final String QUERY_FOR_STUDENTS_IN_DEPARTMENT = "SELECT "
			+ "users.user_id , users.first_name, users.second_name, users.patronymic, users.user_login "
			+ "FROM users WHERE users.user_role_id = 1 AND status = 1 AND users.department_department_id = ?";

	/** Query for database for selecting students of university */
	private final String QUERY_FOR_STUDENTS_IN_ALL_DEPARTMENTS = "SELECT "
			+ "users.user_id, users.first_name, users.second_name, users.patronymic, "
			+ "users.user_login, departments.name, departments.department_id, "
			+ "user_details.user_mobile_number, user_details.user_adress, user_details.user_date_of_birth "
			+ " FROM users JOIN user_details ON users.user_id = user_details.users_user_id "
			+ "JOIN departments ON departments.department_id = users.department_department_id "
			+ "WHERE users.user_role_id = 1 AND users.status != 3";

	/** private constructor without parameters */
	private DepartmentDAOImpl() {

	}

	/**
	 * Returns singleton object of the class
	 * 
	 * @return Object of {@link CourseDAOImpl}
	 */
	public static DepartmentDAOImpl getInstance() {
		return instance;
	}

	/**
	 * Method finds detailed information about all departments of the university.
	 * Method uses {@link ConnectionPool} to connect to database.
	 * 
	 * @return List of {@link Department} object which contain information about
	 *         departments.
	 * @throws DAOException when problems with database access occur.
	 */
	@Override
	public List<Department> findAllDepartments() throws DAOException {

		List<Department> departmets = new ArrayList<Department>();

		ConnectionPool connectionPool = ConnectionPool.getInstance();
		Connection connection = null;

		PreparedStatement statement = null;
		ResultSet resultSet = null;

		try {
			connection = connectionPool.getFreeConnection();

			String query = QUERY_FOR_ALL_DEPARTMENTS;

			statement = connection.prepareStatement(query);

			resultSet = statement.executeQuery();

			while (resultSet.next()) {

				int departmentId;
				int departmentDeanId;

				String departmentName;
				String departmentDescription;
				String deanFirstName;
				String deanSecondName;
				String deanPatronymic;
				String deanLogin;

				departmentId = resultSet.getInt(DATABASE_PARAMETER_DEPARTMENT_ID);
				departmentDeanId = resultSet.getInt(DATABASE_PARAMETER_DEAN_ID);

				departmentName = resultSet.getString(DATABASE_PARAMETER_DEPARTMENT_NAME);
				departmentDescription = resultSet.getString(DATABASE_PARAMETER_DEPARTMENT_DESCRIPTION);
				deanFirstName = resultSet.getString(DATABASE_PARAMETER_USER_FIRST_NAME);
				deanSecondName = resultSet.getString(DATABASE_PARAMETER_USER_SECOND_NAME);
				deanPatronymic = resultSet.getString(DATABASE_PARAMETER_USER_PATRONYMIC);
				deanLogin = resultSet.getString(DATABASE_PARAMETER_USER_LOGIN);

				Department department = new Department();

				department.setDepartmentID(departmentId);
				department.setDeanId(departmentDeanId);
				department.setDepartmentName(departmentName);
				department.setDepartmentDescription(departmentDescription);

				ServiceProvider serviceProvider = ServiceProvider.getInstance();
				FullNameService fullNameService = serviceProvider.getFullNameService();

				String deanFullName = fullNameService.createFullName(deanFirstName, deanSecondName, deanPatronymic);

				department.setDeanName(deanFullName);
				department.setDeanLogin(deanLogin);

				departmets.add(department);
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
		return departmets;
	}

	/**
	 * Method finds detailed information abut lecturers of the department. Method
	 * uses {@link ConnectionPool} to connect to database.
	 * 
	 * @param departmentId id of the department needed
	 * @return List of {@link UserInfo} objects which contain detailed information
	 *         about lecturers of the department.
	 * @throws DAOException when problems with database access occur.
	 */
	@Override
	public List<UserInfo> findLecturersInDepartment(int departmentId) throws DAOException {

		List<UserInfo> lecturers = new ArrayList<UserInfo>();

		ConnectionPool connectionPool = ConnectionPool.getInstance();
		Connection connection = null;

		PreparedStatement statement = null;
		ResultSet resultSet = null;

		try {
			connection = connectionPool.getFreeConnection();

			String query = QUERY_FOR_LECTURERS_IN_DEPARTMENT;

			statement = connection.prepareStatement(query);

			statement.setInt(1, departmentId);

			resultSet = statement.executeQuery();

			while (resultSet.next()) {

				String firstName;
				String secondName;
				String patronymic;
				String userLogin;
				String departmentName;
				int userRoleId;

				firstName = resultSet.getString(DATABASE_PARAMETER_USER_FIRST_NAME);
				secondName = resultSet.getString(DATABASE_PARAMETER_USER_SECOND_NAME);
				patronymic = resultSet.getString(DATABASE_PARAMETER_USER_PATRONYMIC);
				userLogin = resultSet.getString(DATABASE_PARAMETER_USER_LOGIN);
				userRoleId = resultSet.getInt(DATABASE_PARAMETER_USER_ROLE_ID);
				departmentName = resultSet.getString(DATABASE_PARAMETER_DEPARTMENT_NAME);

				UserInfo lecturer = new UserInfo();

				lecturer.setUserFirstName(firstName);
				lecturer.setUserSecondName(secondName);
				lecturer.setUserPatronymic(patronymic);
				lecturer.setUserLogin(userLogin);
				lecturer.setUserRoleId(userRoleId);
				lecturer.setUserFaculty(departmentName);

				lecturers.add(lecturer);
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
		return lecturers;
	}

	/**
	 * Method finds detailed information about courses of department.Method uses
	 * {@link ConnectionPool} to connect to database.
	 * 
	 * @param departmentId id of the department needed
	 * @return List of {@link Course} object which contain detailed information
	 *         about courses of the department.
	 * @throws DAOException when problems with database access occur.
	 */
	@Override
	public List<Course> findCoursesInDepartment(int departmentId) throws DAOException {

		List<Course> courses = new ArrayList<Course>();

		ConnectionPool connectionPool = ConnectionPool.getInstance();
		Connection connection = null;

		PreparedStatement statement = null;
		ResultSet resultSet = null;

		try {
			connection = connectionPool.getFreeConnection();

			String query = QUERY_FOR_COURSES_IN_DEPARTMENT;

			statement = connection.prepareStatement(query);

			statement.setInt(1, departmentId);

			resultSet = statement.executeQuery();

			while (resultSet.next()) {

				String courseTitle = resultSet.getString(DATABASE_PARAMETER_COURSE_TITLE);
				String courseDescription = resultSet.getString(DATABASE_PARAMETER_COURSE_DESCRIPTION);
				int courseId = resultSet.getInt(DATABASE_PARAMETER_COURSE_ID);

				Course course = new Course();

				course.setCourseName(courseTitle);
				course.setCourseDescription(courseDescription);
				course.setCourseId(courseId);

				courses.add(course);
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
		return courses;
	}

	/**
	 * Method finds detailed information abut students of the department. Method
	 * uses {@link ConnectionPool} to connect to database.
	 * 
	 * @param departmentId id of the department needed
	 * @return List of {@link UserInfo} objects which contain detailed information
	 *         about students of the department.
	 * @throws DAOException when problems with database access occur.
	 */
	@Override
	public List<UserInfo> findStudentsInDepartment(int departmentId) throws DAOException {

		List<UserInfo> students = new ArrayList<UserInfo>();

		ConnectionPool connectionPool = ConnectionPool.getInstance();
		Connection connection = null;

		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			connection = connectionPool.getFreeConnection();

			String query = QUERY_FOR_STUDENTS_IN_DEPARTMENT;

			statement = connection.prepareStatement(query);

			statement.setInt(1, departmentId);

			resultSet = statement.executeQuery();

			while (resultSet.next()) {

				int studentId;

				String firstName;
				String secondName;
				String patronymic;
				String userLogin;

				studentId = resultSet.getInt(DATABASE_PARAMETER_USER_ID);
				firstName = resultSet.getString(DATABASE_PARAMETER_USER_FIRST_NAME);
				secondName = resultSet.getString(DATABASE_PARAMETER_USER_SECOND_NAME);
				patronymic = resultSet.getString(DATABASE_PARAMETER_USER_PATRONYMIC);
				userLogin = resultSet.getString(DATABASE_PARAMETER_USER_LOGIN);

				UserInfo student = new UserInfo();

				student.setUserFirstName(firstName);
				student.setUserSecondName(secondName);
				student.setUserPatronymic(patronymic);
				student.setUserLogin(userLogin);
				student.setUserId(studentId);

				UserDAOImpl userDAO = UserDAOImpl.getInstance();
				List<Mark> studentMarks = userDAO.findStudentResults(studentId);

				student.setStudentMarks(studentMarks);

				students.add(student);
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

		return students;
	}

	/**
	 * Method finds detailed information abut students of the university. Method
	 * uses {@link ConnectionPool} to connect to database.
	 * 
	 * @return List of {@link UserInfo} objects which contain detailed information
	 *         about students of the university.
	 * @throws DAOException when problems with database access occur.
	 */
	@Override
	public List<UserInfo> findStudentsInAllDepartments() throws DAOException {

		List<UserInfo> studentsOfAllDepartments = new ArrayList<UserInfo>();

		ConnectionPool connectionPool = ConnectionPool.getInstance();
		Connection connection = null;

		PreparedStatement statement = null;
		ResultSet resultSet = null;

		try {
			connection = connectionPool.getFreeConnection();

			String query = QUERY_FOR_STUDENTS_IN_ALL_DEPARTMENTS;

			statement = connection.prepareStatement(query);

			resultSet = statement.executeQuery();

			while (resultSet.next()) {

				int userId;
				int departmentId;

				String userFirstName;
				String userSecondName;
				String userPatronymic;
				String userLogin;
				String userMobile;
				String userAdress;
				String departmentName;

				LocalDate userDateOfBirth;
				Date sqlDate;

				userId = resultSet.getInt(DATABASE_PARAMETER_USER_ID);
				departmentId = resultSet.getInt(DATABASE_PARAMETER_DEPARTMENT_ID);

				userFirstName = resultSet.getString(DATABASE_PARAMETER_USER_FIRST_NAME);
				userSecondName = resultSet.getString(DATABASE_PARAMETER_USER_SECOND_NAME);
				userPatronymic = resultSet.getString(DATABASE_PARAMETER_USER_PATRONYMIC);
				userLogin = resultSet.getString(DATABASE_PARAMETER_USER_LOGIN);
				userMobile = resultSet.getString(DATABASE_PARAMETER_USER_PHONE);
				userAdress = resultSet.getString(DATABASE_PARAMETER_USER_ADRESS);
				departmentName = resultSet.getString(DATABASE_PARAMETER_DEPARTMENT_NAME);

				userDateOfBirth = null;
				sqlDate = resultSet.getDate(DATABASE_PARAMETER_USER_BIRTH_DATE);
				if (sqlDate != null) {
					userDateOfBirth = sqlDate.toLocalDate();
				}

				UserInfo student = new UserInfo();

				student.setUserId(userId);
				student.setUserFirstName(userFirstName);
				student.setUserSecondName(userSecondName);
				student.setUserPatronymic(userPatronymic);
				student.setUserLogin(userLogin);
				student.setUserPhone(userMobile);
				student.setUserAdress(userAdress);
				student.setUserDateOfBirth(userDateOfBirth);
				student.setUserFacultyId(departmentId);
				student.setUserFaculty(departmentName);

				UserDAOImpl userDAO = UserDAOImpl.getInstance();
				List<Mark> studentMarks = userDAO.findStudentResults(userId);

				student.setStudentMarks(studentMarks);

				studentsOfAllDepartments.add(student);
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

		return studentsOfAllDepartments;
	}

}
