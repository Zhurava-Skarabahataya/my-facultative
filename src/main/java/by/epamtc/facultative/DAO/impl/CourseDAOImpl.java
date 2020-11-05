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

import by.epamtc.facultative.bean.StudentOnCourse;
import by.epamtc.facultative.bean.Course;
import by.epamtc.facultative.bean.RunnedCourse;
import by.epamtc.facultative.bean.UserInfo;
import by.epamtc.facultative.dao.CourseDAO;
import by.epamtc.facultative.dao.exception.DAOException;
import by.epamtc.facultative.dao.impl.pool.ConnectionPool;
import by.epamtc.facultative.dao.impl.pool.exception.ConnectionPoolException;
import by.epamtc.facultative.service.CourseInfoService;
import by.epamtc.facultative.service.FullNameService;
import by.epamtc.facultative.service.ServiceProvider;
import by.epamtc.facultative.service.impl.CourseInfoServiceImpl;

public class CourseDAOImpl implements CourseDAO {

	private static final CourseDAOImpl instance = new CourseDAOImpl();

	private static final Logger logger = Logger.getLogger(CourseDAOImpl.class);

	private final String ERROR_MESSAGE_PROBLEM_SQL = "Problems with database while executing query.";

	private final String DATABASE_PARAMETER_COURSE_ID = "courses.course_id";
	private final String DATABASE_PARAMETER_COURSE_TITLE = "title";
	private final String DATABASE_PARAMETER_RUN_COURSE_ID = "run_courses.run_courses_id";
	private final String DATABASE_PARAMETER_STUDENT_LIMIT = "run_courses.student_limit";
	private final String DATABASE_PARAMETER_CLASSROOM = "run_courses.classroom";
	private final String DATABASE_PARAMETER_COURSE_STATUS = "run_courses.run_courses_status";
	private final String DATABASE_PARAMETER_USER_RESULT = "users_has_run_courses.user_result";
	private final String DATABASE_PARAMETER_DURATION = "courses.duration_in_hours";
	private final String DATABASE_PARAMETER_DEPARTMENT_ID = "courses.department_id";
	private final String DATABASE_PARAMETER_SCHEDULE = "run_courses.shcedule";
	private final String DATABASE_PARAMETER_TITLE = "courses.title";
	private final String DATABASE_PARAMETER_COURSE_DESCRIPTION = "courses.description";
	private final String DATABASE_PARAMETER_COURSE_PROGRAM = "courses.course_program";
	private final String DATABASE_PARAMETER_COURSE_REQUIREMENT = "courses.requirement";
	private final String DATABASE_PARAMETER_APPROVAL_STATUS = "approval_statuses.approval_status_name";
	private final String DATABASE_PARAMETER_START_DATE = "run_courses.start_date";
	private final String DATABASE_PARAMETER_END_DATE = "run_courses.end_date";
	private final String DATABASE_PARAMETER_USER_ID = "users_has_run_courses.users_user_id";
	private final String DATABASE_PARAMETER_LECTURER_ID = "run_courses.lecturer_user_id";
	private final String DATABASE_PARAMETER_USER_LOGIN = "users.user_login";
	private final String DATABASE_PARAMETER_USER_FIRST_NAME = "users.first_name";
	private final String DATABASE_PARAMETER_USER_SECOND_NAME = "users.second_name";
	private final String DATABASE_PARAMETER_USER_PATRONYMIC = "users.patronymic";
	private final String DATABASE_PARAMETER_APPROVAL_STATUS_ID = "users_has_run_courses.approval_status_id";
	private final String DATABASE_PARAMETER_DEPARTMENT_NAME = "departments.name";

	private final String QUERY_FOR_AVAILABLE_COURSES_IN_DEPARTMENT = "SELECT courses.course_id,"
			+ "title FROM courses WHERE department_id = ?";

	private final String QUERY_FOR_ALL_AVAILABLE_COURSES = "SELECT run_courses.run_courses_id, "
			+ "run_courses.start_date, run_courses.end_date, run_courses.shcedule, "
			+ "run_courses.student_limit, run_courses.classroom, run_courses.run_courses_status, run_courses.lecturer_user_id, "
			+ "courses.course_id, courses.title, courses.description, courses.course_program, "
			+ "courses.requirement, courses.duration_in_hours, courses.department_id, "
			+ "run_courses_statuses.run_courses_statuses_title, users.first_name, "
			+ "users.second_name, users.patronymic FROM courses JOIN run_courses "
			+ "ON courses.course_id = run_courses.courses_course_id "
			+ "JOIN run_courses_statuses ON run_courses_statuses.run_courses_statuses_id = "
			+ "run_courses.run_courses_status JOIN users ON users.user_id = run_courses.lecturer_user_id"
			+ " WHERE run_courses_statuses.run_courses_statuses_id = 1";

	private final String QUERY_FOR_CREATING_RUN_COURSE = "INSERT INTO run_courses (courses_course_id, "
			+ " start_date, end_date, shcedule, student_limit, classroom, lecturer_user_id, run_courses_status) "
			+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

	private final String QUERY_FOR_RUN_COURSE_INFO_BY_RUN_COURSE_ID = "SELECT run_courses.run_courses_id, "
			+ "run_courses.start_date, run_courses.end_date, run_courses.shcedule, "
			+ "run_courses.student_limit, run_courses.classroom, run_courses.run_courses_status, run_courses.lecturer_user_id, "
			+ "courses.course_id, courses.title, courses.description, courses.course_program, "
			+ "courses.requirement, courses.duration_in_hours, courses.department_id, "
			+ "run_courses_statuses.run_courses_statuses_title, users.first_name, "
			+ "users.second_name, users.patronymic, users.user_login, departments.name FROM courses JOIN run_courses "
			+ "ON courses.course_id = run_courses.courses_course_id "
			+ "JOIN run_courses_statuses ON run_courses_statuses.run_courses_statuses_id = "
			+ "run_courses.run_courses_status JOIN users ON users.user_id = run_courses.lecturer_user_id"
			+ " JOIN departments ON courses.department_id = departments.department_id"
			+ " WHERE run_courses.run_courses_id = ?";

	private final String QUERY_FOR_LECTURER_RUN_COURSE = "SELECT run_courses.run_courses_id,"
			+ " run_courses.start_date, run_courses.end_date, run_courses.shcedule, run_courses.student_limit, "
			+ " run_courses.classroom, run_courses.run_courses_status, "
			+ " courses.course_id, courses.title, courses.description, courses.course_program, "
			+ "courses.requirement, courses.duration_in_hours, courses.department_id, run_courses_statuses.run_courses_statuses_title"
			+ "  FROM courses JOIN run_courses ON courses.course_id = run_courses.courses_course_id "
			+ "JOIN run_courses_statuses ON run_courses_statuses.run_courses_statuses_id = "
			+ "run_courses.run_courses_status WHERE run_courses.lecturer_user_id = ? ";

	private final String QUERY_FOR_COURSE_INFO = "SELECT * FROM courses WHERE course_id = ?";

	private final String QUERY_FOR_RUN_COURSE_INFO_BY_COURSE_ID = "SELECT run_courses.run_courses_id, "
			+ "run_courses.start_date, run_courses.end_date, run_courses.shcedule, "
			+ "run_courses.student_limit, run_courses.classroom, run_courses.run_courses_status, "
			+ "run_courses.lecturer_user_id, " + "run_courses_statuses.run_courses_statuses_title, users.first_name, "
			+ "users.second_name, users.patronymic, departments.name FROM courses JOIN run_courses "
			+ "ON courses.course_id = run_courses.courses_course_id "
			+ "JOIN run_courses_statuses ON run_courses_statuses.run_courses_statuses_id = "
			+ "run_courses.run_courses_status JOIN users ON users.user_id = run_courses.lecturer_user_id"
			+ " JOIN departments ON courses.department_id = departments.department_id" + " WHERE courses.course_id = ?";

	private final String QUERY_FOR_STUDENT_RUN_COURSES = "SELECT "
			+ "run_courses.run_courses_id, run_courses.start_date, " + "run_courses.end_date, run_courses.shcedule, "
			+ "run_courses.student_limit, run_courses.classroom, "
			+ "run_courses.run_courses_status, courses.course_id, "
			+ "courses.title, courses.description, courses.course_program, "
			+ "courses.requirement, courses.duration_in_hours, courses.department_id, "
			+ "users_has_run_courses.approval_status_id, users_has_run_courses.user_result,"
			+ " approval_statuses.approval_status_name " + "FROM courses "
			+ "JOIN run_courses ON courses.course_id = run_courses.courses_course_id " + "JOIN run_courses_statuses "
			+ "ON run_courses_statuses.run_courses_statuses_id = run_courses.run_courses_status "
			+ "JOIN users_has_run_courses " + "ON run_courses.run_courses_id = users_has_run_courses.run_courses_id "
			+ "JOIN approval_statuses ON approval_statuses.approval_status_id = users_has_run_courses.approval_status_id"
			+ " WHERE users_has_run_courses.users_user_id = ?";

	private final String QUERY_FOR_STUDENTS_ON_COURSE = "SELECT users.user_login, users.first_name, users.second_name, "
			+ "users.patronymic, users_has_run_courses.users_user_id, "
			+ "users_has_run_courses.user_result, users_has_run_courses.approval_status_id, "
			+ "approval_statuses.approval_status_name FROM users_has_run_courses JOIN users "
			+ "ON users.user_id = users_has_run_courses.users_user_id JOIN approval_statuses "
			+ "ON users_has_run_courses.approval_status_id = approval_statuses.approval_status_id"
			+ " WHERE users_has_run_courses.run_courses_id = ?";

	private final String QUERY_FOR_APPLYING_STUDENT_FOR_RUN_COURSE = "INSERT INTO users_has_run_courses "
			+ "(users_user_id, run_courses_id, approval_status_id) VALUES (?, ?, ?);";

	private final String QUERY_FOR_REMOVE_APPLICATION_STUDENT_FOR_RUN_COURSE = "DELETE "
			+ "FROM users_has_run_courses WHERE users_user_id = ? AND run_courses_id = ?";

	private final String QUERY_FOR_CHANGING_APPROVAL_STATUS_ON_COURSE = "UPDATE users_has_run_courses"
			+ " SET approval_status_id = ? WHERE users_user_id = ? AND run_courses_id = ?";

	private final String QUERY_FOR_GIVING_STUDENT_GRADE = "UPDATE users_has_run_courses "
			+ " SET user_result = ? WHERE users_user_id = ? AND run_courses_id = ?";

	private final String QUERY_FOR_LEAVING_FEEDBACK = "INSERT INTO courses_feedbacks "
			+ "(course_feedback_text, users_user_id_author, courses_course_id) VALUES (?, ?, ?)";

	private CourseDAOImpl() {

	}

	public static CourseDAOImpl getInstance() {
		return instance;
	}

	@Override
	public List<Course> findCoursesFromDepartment(int departmentId) throws DAOException {

		List<Course> courses = new ArrayList<Course>();

		ConnectionPool connectionPool = ConnectionPool.getInstance();
		Connection connection = null;

		PreparedStatement statement = null;
		ResultSet resultSet = null;

		try {
			connection = connectionPool.getFreeConnection();

			String query = QUERY_FOR_AVAILABLE_COURSES_IN_DEPARTMENT;
			statement = connection.prepareStatement(query);

			statement.setInt(1, departmentId);

			resultSet = statement.executeQuery();

			while (resultSet.next()) {

				int courseId = resultSet.getInt(DATABASE_PARAMETER_COURSE_ID);
				String courseTitle = resultSet.getString(DATABASE_PARAMETER_COURSE_TITLE);

				Course infoAboutCourse = new Course();

				infoAboutCourse.setCourseId(courseId);
				infoAboutCourse.setCourseName(courseTitle);

				courses.add(infoAboutCourse);
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

	@Override
	public void createRunCourse(RunnedCourse infoAboutRunnedCourse) throws DAOException {

		int coursesCourseId;
		int studentLimit;
		int classroom;
		int lecturerId;
		int courseStatus;

		String shedule;

		LocalDate startDate;
		LocalDate endDate;

		coursesCourseId = infoAboutRunnedCourse.getCourseId();
		studentLimit = infoAboutRunnedCourse.getStudentLimit();
		classroom = infoAboutRunnedCourse.getClassroomNumber();
		lecturerId = infoAboutRunnedCourse.getLecturerId();
		courseStatus = infoAboutRunnedCourse.getCourseStatus();

		startDate = infoAboutRunnedCourse.getDateOfStart();
		endDate = infoAboutRunnedCourse.getDateOfEnd();

		shedule = infoAboutRunnedCourse.getShedule();

		ConnectionPool connectionPool = ConnectionPool.getInstance();
		Connection connection = null;

		PreparedStatement statement = null;

		try {
			connection = connectionPool.getFreeConnection();

			statement = connection.prepareStatement(QUERY_FOR_CREATING_RUN_COURSE);

			statement.setInt(1, coursesCourseId);

			Date sqlStartDate = Date.valueOf(startDate.plusDays(1));
			Date sqlEndDate = Date.valueOf(endDate.plusDays(1));

			statement.setDate(2, sqlStartDate);
			statement.setDate(3, sqlEndDate);

			statement.setString(4, shedule);
			statement.setInt(5, studentLimit);
			statement.setInt(6, classroom);
			statement.setInt(7, lecturerId);
			statement.setInt(8, courseStatus);

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
	public void getRunCoursesOfStudent(UserInfo userPageInfo) throws DAOException {

		ConnectionPool connectionPool = ConnectionPool.getInstance();
		Connection connection = null;

		PreparedStatement statement = null;
		ResultSet resultSet = null;

		List<RunnedCourse> currentCourses = new ArrayList<RunnedCourse>();
		List<RunnedCourse> endedCourses = new ArrayList<RunnedCourse>();
		List<RunnedCourse> canselledCourses = new ArrayList<RunnedCourse>();

		int userId;
		userId = userPageInfo.getUserId();

		try {
			connection = connectionPool.getFreeConnection();

			statement = connection.prepareStatement(QUERY_FOR_STUDENT_RUN_COURSES);
			statement.setInt(1, userId);

			resultSet = statement.executeQuery();

			while (resultSet.next()) {

				int runCourseId;
				int studentLimit;
				int classroom;
				int runCourseStatusId;
				int courseId;
				int courseResult;
				int courseDuration;
				int departmentId;

				String shcedule;
				String courseTitle;
				String courseDescription;
				String courseProgram;
				String courseRequirement;
				String studentStatusOnCourse;

				Date sqlDateStart;
				Date sqlDateEnd;
				LocalDate startDate;
				LocalDate endDate;

				runCourseId = resultSet.getInt(DATABASE_PARAMETER_RUN_COURSE_ID);
				studentLimit = resultSet.getInt(DATABASE_PARAMETER_STUDENT_LIMIT);
				classroom = resultSet.getInt(DATABASE_PARAMETER_CLASSROOM);
				runCourseStatusId = resultSet.getInt(DATABASE_PARAMETER_COURSE_STATUS);
				courseId = resultSet.getInt(DATABASE_PARAMETER_COURSE_ID);
				courseResult = resultSet.getInt(DATABASE_PARAMETER_USER_RESULT);
				courseDuration = resultSet.getInt(DATABASE_PARAMETER_DURATION);
				departmentId = resultSet.getInt(DATABASE_PARAMETER_DEPARTMENT_ID);

				shcedule = resultSet.getString(DATABASE_PARAMETER_SCHEDULE);
				courseTitle = resultSet.getString(DATABASE_PARAMETER_TITLE);
				courseDescription = resultSet.getString(DATABASE_PARAMETER_COURSE_DESCRIPTION);
				courseProgram = resultSet.getString(DATABASE_PARAMETER_COURSE_PROGRAM);
				courseRequirement = resultSet.getString(DATABASE_PARAMETER_COURSE_REQUIREMENT);
				studentStatusOnCourse = resultSet.getString(DATABASE_PARAMETER_APPROVAL_STATUS);

				sqlDateStart = resultSet.getDate(DATABASE_PARAMETER_START_DATE);
				sqlDateEnd = resultSet.getDate(DATABASE_PARAMETER_END_DATE);
				startDate = sqlDateStart.toLocalDate();
				endDate = sqlDateEnd.toLocalDate();

				RunnedCourse infoAboutRunnedCourse = new RunnedCourse();

				infoAboutRunnedCourse.setRunCourseId(runCourseId);
				infoAboutRunnedCourse.setCourseId(courseId);
				infoAboutRunnedCourse.setClassroomNumber(classroom);
				infoAboutRunnedCourse.setCourseStatus(runCourseStatusId);
				infoAboutRunnedCourse.setStudentLimit(studentLimit);
				infoAboutRunnedCourse.setStudentResult(courseResult);

				infoAboutRunnedCourse.setCourseName(courseTitle);
				infoAboutRunnedCourse.setShedule(shcedule);
				infoAboutRunnedCourse.setStudentStatusName(studentStatusOnCourse);

				infoAboutRunnedCourse.setDateOfEnd(endDate);
				infoAboutRunnedCourse.setDateOfStart(startDate);

				Course infoAbourCourse = new Course();

				infoAbourCourse.setCourseId(courseId);
				infoAbourCourse.setCourseName(courseTitle);
				infoAbourCourse.setCourseProgram(courseProgram);
				infoAbourCourse.setCourseRequirement(courseRequirement);
				infoAbourCourse.setCourseDescription(courseDescription);

				infoAbourCourse.setCourseDepartment(departmentId);
				infoAbourCourse.setCourseDuration(courseDuration);

				infoAboutRunnedCourse.setInfoAboutCourse(infoAbourCourse);

				ServiceProvider serviceProvider = ServiceProvider.getInstance();
				CourseInfoService courseInfoService = serviceProvider.getCourseInfoService();
				courseInfoService.defineCourseLaunchStatus(infoAboutRunnedCourse);

				int courseLaunchStatus = infoAboutRunnedCourse.getCurrentState();

				if (courseLaunchStatus == 1) {
					canselledCourses.add(infoAboutRunnedCourse);

				} else if (courseLaunchStatus == 2) {
					endedCourses.add(infoAboutRunnedCourse);

				} else {
					currentCourses.add(infoAboutRunnedCourse);
				}

				List<StudentOnCourse> students = findStudentsOnRunCourse(runCourseId);

				infoAboutRunnedCourse.setStudentsOnCourse(students);

				userPageInfo.setCanselledCourses(canselledCourses);
				userPageInfo.setEndedCourses(endedCourses);
				userPageInfo.setCurrentCourses(currentCourses);

			}

		} catch (SQLException e) {
			logger.error(ERROR_MESSAGE_PROBLEM_SQL, e);
			throw new DAOException(ERROR_MESSAGE_PROBLEM_SQL, e);

		} catch (ConnectionPoolException e) {
			throw new DAOException(e);

		}

		finally {
			try {
				connectionPool.closeConnection(resultSet, statement, connection);

			} catch (ConnectionPoolException e) {
				throw new DAOException(e);
			}

		}
	}

	@Override
	public void getRunCoursesOfLecturer(UserInfo userPageInfo) throws DAOException {

		List<RunnedCourse> currentCourses = new ArrayList<RunnedCourse>();
		List<RunnedCourse> endedCourses = new ArrayList<RunnedCourse>();
		List<RunnedCourse> canselledCourses = new ArrayList<RunnedCourse>();

		int userId;
		userId = userPageInfo.getUserId();

		ConnectionPool connectionPool = ConnectionPool.getInstance();
		Connection connection = null;

		PreparedStatement statement = null;
		ResultSet resultSet = null;

		try {
			connection = connectionPool.getFreeConnection();

			statement = connection.prepareStatement(QUERY_FOR_LECTURER_RUN_COURSE);
			statement.setInt(1, userId);

			resultSet = statement.executeQuery();

			while (resultSet.next()) {

				int runCourseId;
				int departmentId;
				int studentLimit;
				int classroom;
				int runCourseStatusId;
				int courseId;
				int courseDuration;

				String courseTitle;
				String shcedule;
				String courseDescription;
				String courseProgram;
				String courseRequirement;

				Date sqlDateStart;
				Date sqlDateEnd;
				LocalDate startDate;
				LocalDate endDate;

				runCourseId = resultSet.getInt(DATABASE_PARAMETER_RUN_COURSE_ID);
				departmentId = resultSet.getInt(DATABASE_PARAMETER_DEPARTMENT_ID);
				studentLimit = resultSet.getInt(DATABASE_PARAMETER_STUDENT_LIMIT);
				classroom = resultSet.getInt(DATABASE_PARAMETER_CLASSROOM);
				runCourseStatusId = resultSet.getInt(DATABASE_PARAMETER_COURSE_STATUS);
				courseId = resultSet.getInt(DATABASE_PARAMETER_COURSE_ID);
				courseDuration = resultSet.getInt(DATABASE_PARAMETER_DURATION);

				courseTitle = resultSet.getString(DATABASE_PARAMETER_TITLE);
				shcedule = resultSet.getString(DATABASE_PARAMETER_SCHEDULE);
				courseDescription = resultSet.getString(DATABASE_PARAMETER_COURSE_DESCRIPTION);
				courseProgram = resultSet.getString(DATABASE_PARAMETER_COURSE_PROGRAM);
				courseRequirement = resultSet.getString(DATABASE_PARAMETER_COURSE_REQUIREMENT);

				sqlDateStart = resultSet.getDate(DATABASE_PARAMETER_START_DATE);
				sqlDateEnd = resultSet.getDate(DATABASE_PARAMETER_END_DATE);
				startDate = sqlDateStart.toLocalDate();
				endDate = sqlDateEnd.toLocalDate();

				RunnedCourse infoAboutRunnedCourse = new RunnedCourse();

				infoAboutRunnedCourse.setClassroomNumber(classroom);
				infoAboutRunnedCourse.setCourseId(courseId);
				infoAboutRunnedCourse.setCourseStatus(runCourseStatusId);
				infoAboutRunnedCourse.setLecturerId(userId);
				infoAboutRunnedCourse.setRunCourseId(runCourseId);
				infoAboutRunnedCourse.setStudentLimit(studentLimit);

				infoAboutRunnedCourse.setCourseName(courseTitle);
				infoAboutRunnedCourse.setShedule(shcedule);

				infoAboutRunnedCourse.setDateOfEnd(endDate);
				infoAboutRunnedCourse.setDateOfStart(startDate);

				Course infoAbourCourse = new Course();

				infoAbourCourse.setCourseDepartment(departmentId);
				infoAbourCourse.setCourseId(courseId);
				infoAbourCourse.setCourseDuration(courseDuration);

				infoAbourCourse.setCourseName(courseTitle);
				infoAbourCourse.setCourseDescription(courseDescription);
				infoAbourCourse.setCourseProgram(courseProgram);
				infoAbourCourse.setCourseRequirement(courseRequirement);

				infoAboutRunnedCourse.setInfoAboutCourse(infoAbourCourse);

				CourseInfoServiceImpl courseInfoService = CourseInfoServiceImpl.getInstance();
				courseInfoService.defineCourseLaunchStatus(infoAboutRunnedCourse);

				int courseLaunchStatus;
				courseLaunchStatus = infoAboutRunnedCourse.getCurrentState();

				if (courseLaunchStatus == 1) {
					canselledCourses.add(infoAboutRunnedCourse);

				} else if (courseLaunchStatus == 2) {
					endedCourses.add(infoAboutRunnedCourse);

				} else {
					currentCourses.add(infoAboutRunnedCourse);
				}

				List<StudentOnCourse> students = findStudentsOnRunCourse(runCourseId);

				infoAboutRunnedCourse.setStudentsOnCourse(students);

				userPageInfo.setCanselledCourses(canselledCourses);
				userPageInfo.setEndedCourses(endedCourses);
				userPageInfo.setCurrentCourses(currentCourses);
			}

		} catch (SQLException e) {

			logger.error(ERROR_MESSAGE_PROBLEM_SQL, e);
			throw new DAOException(ERROR_MESSAGE_PROBLEM_SQL, e);

		} catch (ConnectionPoolException e) {
			throw new DAOException(e);
		}

		finally {
			try {
				connectionPool.closeConnection(resultSet, statement, connection);

			} catch (ConnectionPoolException e) {
				throw new DAOException(e);
			}
		}
	}

	@Override
	public List<StudentOnCourse> findStudentsOnRunCourse(int runCourseId) throws DAOException {

		List<StudentOnCourse> students = new ArrayList<StudentOnCourse>();

		ConnectionPool connectionPool = ConnectionPool.getInstance();
		Connection connection = null;

		PreparedStatement statement = null;
		ResultSet resultSet = null;

		try {
			connection = connectionPool.getFreeConnection();
			statement = connection.prepareStatement(QUERY_FOR_STUDENTS_ON_COURSE);

			statement.setInt(1, runCourseId);

			resultSet = statement.executeQuery();

			while (resultSet.next()) {

				int userId;
				int userResult;
				int userApprovalStatusId;

				String userLogin;
				String firstName;
				String secondName;
				String patronymic;
				String userApprovalStatusName;

				userId = resultSet.getInt(DATABASE_PARAMETER_USER_ID);
				userResult = resultSet.getInt(DATABASE_PARAMETER_USER_RESULT);
				userApprovalStatusId = resultSet.getInt(DATABASE_PARAMETER_APPROVAL_STATUS_ID);

				userLogin = resultSet.getString(DATABASE_PARAMETER_USER_LOGIN);
				firstName = resultSet.getString(DATABASE_PARAMETER_USER_FIRST_NAME);
				secondName = resultSet.getString(DATABASE_PARAMETER_USER_SECOND_NAME);
				patronymic = resultSet.getString(DATABASE_PARAMETER_USER_PATRONYMIC);
				userApprovalStatusName = resultSet.getString(DATABASE_PARAMETER_APPROVAL_STATUS);

				StudentOnCourse student = new StudentOnCourse();

				student.setUserLogin(userLogin);
				student.setUserFirstName(firstName);
				student.setUserSecondName(secondName);
				student.setUserPatronymic(patronymic);

				student.setUserId(userId);
				student.setResult(userResult);
				student.setUserApprovalStatusId(userApprovalStatusId);
				student.setUserApprovalStatusName(userApprovalStatusName);

				students.add(student);
			}

		} catch (SQLException e) {
			logger.error(ERROR_MESSAGE_PROBLEM_SQL, e);
			throw new DAOException(ERROR_MESSAGE_PROBLEM_SQL, e);

		} catch (ConnectionPoolException e) {
			throw new DAOException(e);

		}

		finally {
			try {
				connectionPool.closeConnection(resultSet, statement, connection);

			} catch (ConnectionPoolException e) {
				throw new DAOException(e);
			}
		}
		return students;
	}

	@Override
	public List<RunnedCourse> findAllAvailableRunCourses() throws DAOException {

		List<RunnedCourse> courses = new ArrayList<RunnedCourse>();

		ConnectionPool connectionPool = ConnectionPool.getInstance();
		Connection connection = null;

		PreparedStatement statement = null;
		ResultSet resultSet = null;

		try {
			connection = connectionPool.getFreeConnection();

			statement = connection.prepareStatement(QUERY_FOR_ALL_AVAILABLE_COURSES);

			resultSet = statement.executeQuery();

			while (resultSet.next()) {

				int runCourseId;
				int studentLimit;
				int classroom;
				int runCourseStatusId;
				int courseId;
				int courseDuration;
				int departmentId;
				int lecturerId;

				String courseTitle;
				String courseDescription;
				String courseProgram;
				String courseRequirement;
				String shcedule;
				String lecturerFirstName;
				String lecturerSecondName;
				String lecturerPatronymic;

				Date sqlDateStart;
				Date sqlDateEnd;
				LocalDate startDate;
				LocalDate endDate;

				runCourseId = resultSet.getInt(DATABASE_PARAMETER_RUN_COURSE_ID);
				studentLimit = resultSet.getInt(DATABASE_PARAMETER_STUDENT_LIMIT);
				classroom = resultSet.getInt(DATABASE_PARAMETER_CLASSROOM);
				runCourseStatusId = resultSet.getInt(DATABASE_PARAMETER_COURSE_STATUS);
				courseId = resultSet.getInt(DATABASE_PARAMETER_COURSE_ID);
				courseDuration = resultSet.getInt(DATABASE_PARAMETER_DURATION);
				departmentId = resultSet.getInt(DATABASE_PARAMETER_DEPARTMENT_ID);
				lecturerId = resultSet.getInt(DATABASE_PARAMETER_LECTURER_ID);

				courseTitle = resultSet.getString(DATABASE_PARAMETER_TITLE);
				courseDescription = resultSet.getString(DATABASE_PARAMETER_COURSE_DESCRIPTION);
				courseProgram = resultSet.getString(DATABASE_PARAMETER_COURSE_PROGRAM);
				courseRequirement = resultSet.getString(DATABASE_PARAMETER_COURSE_REQUIREMENT);
				shcedule = resultSet.getString(DATABASE_PARAMETER_SCHEDULE);
				lecturerFirstName = resultSet.getString(DATABASE_PARAMETER_USER_FIRST_NAME);
				lecturerSecondName = resultSet.getString(DATABASE_PARAMETER_USER_SECOND_NAME);
				lecturerPatronymic = resultSet.getString(DATABASE_PARAMETER_USER_PATRONYMIC);

				sqlDateStart = resultSet.getDate(DATABASE_PARAMETER_START_DATE);
				sqlDateEnd = resultSet.getDate(DATABASE_PARAMETER_END_DATE);

				startDate = sqlDateStart.toLocalDate();
				endDate = sqlDateEnd.toLocalDate();

				ServiceProvider serviceProvider = ServiceProvider.getInstance();
				FullNameService fullNameService = serviceProvider.getFullNameService();
				String lecturerFullName = fullNameService.createFullName(lecturerFirstName,
						lecturerSecondName, lecturerPatronymic);

				RunnedCourse infoAboutRunnedCourse = new RunnedCourse();

				infoAboutRunnedCourse.setRunCourseId(runCourseId);
				infoAboutRunnedCourse.setCourseId(courseId);
				infoAboutRunnedCourse.setClassroomNumber(classroom);
				infoAboutRunnedCourse.setLecturerId(lecturerId);
				infoAboutRunnedCourse.setStudentLimit(studentLimit);
				infoAboutRunnedCourse.setLecturerName(lecturerFullName);

				infoAboutRunnedCourse.setCourseName(courseTitle);
				infoAboutRunnedCourse.setCourseStatus(runCourseStatusId);
				infoAboutRunnedCourse.setShedule(shcedule);

				infoAboutRunnedCourse.setDateOfEnd(endDate);
				infoAboutRunnedCourse.setDateOfStart(startDate);

				Course infoAbourCourse = new Course();

				infoAbourCourse.setCourseId(courseId);
				infoAbourCourse.setCourseDepartment(departmentId);
				infoAbourCourse.setCourseDuration(courseDuration);

				infoAbourCourse.setCourseDescription(courseDescription);
				infoAbourCourse.setCourseName(courseTitle);
				infoAbourCourse.setCourseProgram(courseProgram);
				infoAbourCourse.setCourseRequirement(courseRequirement);

				infoAboutRunnedCourse.setInfoAboutCourse(infoAbourCourse);

				List<StudentOnCourse> students = findStudentsOnRunCourse(runCourseId);

				infoAboutRunnedCourse.setStudentsOnCourse(students);

				courses.add(infoAboutRunnedCourse);

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

	@Override
	public RunnedCourse findRunCourse(int runCourseId) throws DAOException {

		RunnedCourse infoAboutRunnedCourse = null;

		ConnectionPool connectionPool = ConnectionPool.getInstance();
		Connection connection = null;

		PreparedStatement statement = null;
		ResultSet resultSet = null;

		try {
			connection = connectionPool.getFreeConnection();

			statement = connection.prepareStatement(QUERY_FOR_RUN_COURSE_INFO_BY_RUN_COURSE_ID);
			statement.setInt(1, runCourseId);
			resultSet = statement.executeQuery();

			if (resultSet.next()) {

				int runCourseStatusId;
				int studentLimit;
				int classroom;
				int courseId;
				int courseDuration;
				int departmentId;
				int lecturerId;

				String courseTitle;
				String courseDescription;
				String courseProgram;
				String courseRequirement;
				String shcedule;
				String lecturerFirstName;
				String lecturerSecondName;
				String lecturerPatronymic;
				String lecturerLogin;
				String departmentName;

				Date sqlDateStart;
				Date sqlDateEnd;
				LocalDate startDate;
				LocalDate endDate;

				studentLimit = resultSet.getInt(DATABASE_PARAMETER_STUDENT_LIMIT);
				classroom = resultSet.getInt(DATABASE_PARAMETER_CLASSROOM);
				runCourseStatusId = resultSet.getInt(DATABASE_PARAMETER_COURSE_STATUS);
				courseId = resultSet.getInt(DATABASE_PARAMETER_COURSE_ID);
				courseDuration = resultSet.getInt(DATABASE_PARAMETER_DURATION);
				departmentId = resultSet.getInt(DATABASE_PARAMETER_DEPARTMENT_ID);
				lecturerId = resultSet.getInt(DATABASE_PARAMETER_LECTURER_ID);

				courseTitle = resultSet.getString(DATABASE_PARAMETER_TITLE);
				courseDescription = resultSet.getString(DATABASE_PARAMETER_COURSE_DESCRIPTION);
				courseProgram = resultSet.getString(DATABASE_PARAMETER_COURSE_PROGRAM);
				courseRequirement = resultSet.getString(DATABASE_PARAMETER_COURSE_REQUIREMENT);
				shcedule = resultSet.getString(DATABASE_PARAMETER_SCHEDULE);
				lecturerFirstName = resultSet.getString(DATABASE_PARAMETER_USER_FIRST_NAME);
				lecturerSecondName = resultSet.getString(DATABASE_PARAMETER_USER_SECOND_NAME);
				lecturerPatronymic = resultSet.getString(DATABASE_PARAMETER_USER_PATRONYMIC);
				lecturerLogin = resultSet.getString(DATABASE_PARAMETER_USER_LOGIN);
				departmentName = resultSet.getString(DATABASE_PARAMETER_DEPARTMENT_NAME);

				sqlDateStart = resultSet.getDate(DATABASE_PARAMETER_START_DATE);
				startDate = sqlDateStart.toLocalDate();
				sqlDateEnd = resultSet.getDate(DATABASE_PARAMETER_END_DATE);
				endDate = sqlDateEnd.toLocalDate();

				ServiceProvider serviceProvider = ServiceProvider.getInstance();
				FullNameService fullNameService = serviceProvider.getFullNameService();
				String lecturerFullName = fullNameService.createFullName(lecturerFirstName,
						lecturerSecondName, lecturerPatronymic);
				
				infoAboutRunnedCourse = new RunnedCourse();

				infoAboutRunnedCourse.setRunCourseId(runCourseId);
				infoAboutRunnedCourse.setClassroomNumber(classroom);
				infoAboutRunnedCourse.setCourseId(courseId);
				infoAboutRunnedCourse.setLecturerId(lecturerId);
				infoAboutRunnedCourse.setStudentLimit(studentLimit);
				infoAboutRunnedCourse.setLecturerName(lecturerFullName);
				infoAboutRunnedCourse.setLecturerLogin(lecturerLogin);

				infoAboutRunnedCourse.setCourseName(courseTitle);
				infoAboutRunnedCourse.setCourseStatus(runCourseStatusId);
				infoAboutRunnedCourse.setShedule(shcedule);

				infoAboutRunnedCourse.setDateOfEnd(endDate);
				infoAboutRunnedCourse.setDateOfStart(startDate);

				Course infoAbourCourse = new Course();

				infoAbourCourse.setCourseDepartment(departmentId);
				infoAbourCourse.setCourseDuration(courseDuration);
				infoAbourCourse.setCourseId(courseId);

				infoAbourCourse.setCourseName(courseTitle);
				infoAbourCourse.setCourseDescription(courseDescription);
				infoAbourCourse.setCourseProgram(courseProgram);
				infoAbourCourse.setCourseRequirement(courseRequirement);
				infoAbourCourse.setDepartmentName(departmentName);

				infoAboutRunnedCourse.setInfoAboutCourse(infoAbourCourse);

				List<StudentOnCourse> students = findStudentsOnRunCourse(runCourseId);

				infoAboutRunnedCourse.setStudentsOnCourse(students);
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
		return infoAboutRunnedCourse;
	}

	@Override
	public void applyStudentForRunCourse(int userId, int runCourseId) throws DAOException {

		ConnectionPool connectionPool = ConnectionPool.getInstance();
		Connection connection = null;
		PreparedStatement statement = null;

		int userStatus;
		userStatus = 1;

		try {
			connection = connectionPool.getFreeConnection();

			statement = connection.prepareStatement(QUERY_FOR_APPLYING_STUDENT_FOR_RUN_COURSE);

			statement.setInt(1, userId);
			statement.setInt(2, runCourseId);
			statement.setInt(3, userStatus);

			statement.executeUpdate();

		} catch (ConnectionPoolException e) {
			throw new DAOException(e);

		} catch (SQLException e) {
			logger.error(ERROR_MESSAGE_PROBLEM_SQL, e);
			throw new DAOException(ERROR_MESSAGE_PROBLEM_SQL, e);

		} finally {
			try {
				connectionPool.closeConnection(statement, connection);

			} catch (ConnectionPoolException e) {
				throw new DAOException(e);

			}
		}

	}

	@Override
	public void findInfoAboutCourse(Course course) throws DAOException {

		int courseId = course.getCourseId();

		ConnectionPool connectionPool = ConnectionPool.getInstance();
		Connection connection = null;

		PreparedStatement statement = null;
		ResultSet resultSet = null;

		String query = QUERY_FOR_COURSE_INFO;

		try {
			connection = connectionPool.getFreeConnection();

			statement = connection.prepareStatement(query);

			statement.setInt(1, courseId);

			resultSet = statement.executeQuery();

			while (resultSet.next()) {

				int duration;

				String courseName;
				String description;
				String courseProgram;
				String requirement;

				courseName = resultSet.getString(DATABASE_PARAMETER_TITLE);
				description = resultSet.getString(DATABASE_PARAMETER_COURSE_DESCRIPTION);
				courseProgram = resultSet.getString(DATABASE_PARAMETER_COURSE_PROGRAM);
				requirement = resultSet.getString(DATABASE_PARAMETER_COURSE_REQUIREMENT);
				duration = resultSet.getInt(DATABASE_PARAMETER_DURATION);

				course.setCourseName(courseName);
				course.setCourseDescription(description);
				course.setCourseProgram(courseProgram);
				course.setCourseRequirement(requirement);
				course.setCourseDuration(duration);
			}

			List<RunnedCourse> runnedCourses = null;
			runnedCourses = findRunnedCoursesByCourseId(courseId);

			course.setRunCourses(runnedCourses);

		} catch (SQLException e) {
			logger.error(ERROR_MESSAGE_PROBLEM_SQL, e);
			throw new DAOException(ERROR_MESSAGE_PROBLEM_SQL, e);

		} catch (ConnectionPoolException e) {
			throw new DAOException(e);
		}

		finally {
			try {
				connectionPool.closeConnection(resultSet, statement, connection);

			} catch (ConnectionPoolException e) {
				throw new DAOException(e);

			}
		}
	}

	@Override
	public List<RunnedCourse> findRunnedCoursesByCourseId(int courseId) throws DAOException {

		List<RunnedCourse> runCourses = new ArrayList<RunnedCourse>();

		ConnectionPool connectionPool = ConnectionPool.getInstance();
		Connection connection = null;

		PreparedStatement statement = null;
		ResultSet resultSet = null;

		try {
			connection = connectionPool.getFreeConnection();
		
			statement = connection.prepareStatement(QUERY_FOR_RUN_COURSE_INFO_BY_COURSE_ID);
			statement.setInt(1, courseId);

			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				
				int runCourseId;
				int studentLimit;
				int classroom;
				int runCourseStatusId;
				
				String shcedule;
				String lecturerFirstName;
				String lecturerSecondName ;
				String lecturerPatronymic;

				Date sqlDateStart;
				Date sqlDateEnd;
				LocalDate startDate;
				LocalDate endDate;

				runCourseId= resultSet.getInt(DATABASE_PARAMETER_RUN_COURSE_ID);
				studentLimit= resultSet.getInt(DATABASE_PARAMETER_STUDENT_LIMIT);
				classroom = resultSet.getInt(DATABASE_PARAMETER_CLASSROOM);
				runCourseStatusId= resultSet.getInt(DATABASE_PARAMETER_COURSE_STATUS);
				
				shcedule = resultSet.getString(DATABASE_PARAMETER_SCHEDULE);
				lecturerFirstName= resultSet.getString(DATABASE_PARAMETER_USER_FIRST_NAME);
				lecturerSecondName= resultSet.getString(DATABASE_PARAMETER_USER_SECOND_NAME);
				lecturerPatronymic= resultSet.getString(DATABASE_PARAMETER_USER_PATRONYMIC);
				
				sqlDateStart= resultSet.getDate(DATABASE_PARAMETER_START_DATE);
				sqlDateEnd= resultSet.getDate(DATABASE_PARAMETER_END_DATE);
				startDate= sqlDateStart.toLocalDate();
				endDate = sqlDateEnd.toLocalDate();

				RunnedCourse infoAboutRunnedCourse = new RunnedCourse();
				
				infoAboutRunnedCourse.setRunCourseId(runCourseId);
				infoAboutRunnedCourse.setCourseId(courseId);
				infoAboutRunnedCourse.setClassroomNumber(classroom);
				infoAboutRunnedCourse.setCourseStatus(runCourseStatusId);
				infoAboutRunnedCourse.setStudentLimit(studentLimit);

				infoAboutRunnedCourse.setShedule(shcedule);

				infoAboutRunnedCourse.setDateOfEnd(endDate);
				infoAboutRunnedCourse.setDateOfStart(startDate);

				ServiceProvider serviceProvider = ServiceProvider.getInstance();
				FullNameService fullNameService = serviceProvider.getFullNameService();
				String lecturerFullName = fullNameService.createFullName(lecturerFirstName,
						lecturerSecondName, lecturerPatronymic);

				infoAboutRunnedCourse.setLecturerName(lecturerFullName);

				List<StudentOnCourse> students = findStudentsOnRunCourse(runCourseId);

				infoAboutRunnedCourse.setStudentsOnCourse(students);

				runCourses.add(infoAboutRunnedCourse);

			}

		} catch (SQLException e) {
			logger.error(ERROR_MESSAGE_PROBLEM_SQL, e);
			throw new DAOException(ERROR_MESSAGE_PROBLEM_SQL, e);

		} catch (DAOException e) {
			throw new DAOException(e);

		} catch (ConnectionPoolException e) {
			throw new DAOException(e);

		} finally {
			try {
				connectionPool.closeConnection(resultSet, statement, connection);

			} catch (ConnectionPoolException e) {
				throw new DAOException(e);
			}
		}
		return runCourses;
	}

	@Override
	public void removeApplicationForRunCourse(int userId, int runCourseId) throws DAOException {

		ConnectionPool connectionPool = ConnectionPool.getInstance();
		Connection connection = null;

		PreparedStatement statement = null;

		try {
			connection = connectionPool.getFreeConnection();
			statement = connection.prepareStatement(QUERY_FOR_REMOVE_APPLICATION_STUDENT_FOR_RUN_COURSE);

			statement.setInt(1, userId);
			statement.setInt(2, runCourseId);

			statement.execute();

		} catch (ConnectionPoolException e) {
			throw new DAOException(e);

		} catch (SQLException e) {
			logger.error(ERROR_MESSAGE_PROBLEM_SQL, e);
			throw new DAOException(ERROR_MESSAGE_PROBLEM_SQL, e);

		} finally {
			try {
				connectionPool.closeConnection(statement, connection);
				
			} catch (ConnectionPoolException e) {
				throw new DAOException(e);

			}
		}

	}

	@Override
	public void changeStudentApprovalStatusOnRunCourse(int studentId, int runCourseId, int status) throws DAOException {
		
		ConnectionPool connectionPool = ConnectionPool.getInstance();
		Connection connection = null;

		PreparedStatement statement = null;

		try {
			connection = connectionPool.getFreeConnection();
			statement = connection.prepareStatement(QUERY_FOR_CHANGING_APPROVAL_STATUS_ON_COURSE);

			statement.setInt(1, status);
			statement.setInt(2, studentId);
			statement.setInt(3, runCourseId);

			statement.executeUpdate();

		} catch (ConnectionPoolException e) {
			throw new DAOException(e);
			
		} catch (SQLException e) {
			logger.error(ERROR_MESSAGE_PROBLEM_SQL, e);
			throw new DAOException(ERROR_MESSAGE_PROBLEM_SQL, e);

		} finally {
			try {
				connectionPool.closeConnection(statement, connection);
				
			} catch (ConnectionPoolException e) {
				throw new DAOException(e);
			}
		}

	}

	@Override
	public void giveStudentGraveOnRunCourse(int studentId, int runCourseId, int grade) throws DAOException {

		ConnectionPool connectionPool = ConnectionPool.getInstance();
		Connection connection = null;

		PreparedStatement statement = null;

		String query = QUERY_FOR_GIVING_STUDENT_GRADE;

		try {
			connection = connectionPool.getFreeConnection();

			statement = connection.prepareStatement(query);

			statement.setInt(1, grade);
			statement.setInt(2, studentId);
			statement.setInt(3, runCourseId);

			statement.executeUpdate();

		} catch (ConnectionPoolException e) {
			throw new DAOException(e);

		} catch (SQLException e) {
			logger.error(ERROR_MESSAGE_PROBLEM_SQL, e);
			throw new DAOException(ERROR_MESSAGE_PROBLEM_SQL, e);

		} finally {
			try {
				connectionPool.closeConnection(statement, connection);
			} catch (ConnectionPoolException e) {
				throw new DAOException(e);

			}
		}

	}

	@Override
	public void leaveFeedback(int userId, int courseId, String comment) throws DAOException {

		ConnectionPool connectionPool = ConnectionPool.getInstance();
		Connection connection = null;

		PreparedStatement statement = null;

		try {
			connection = connectionPool.getFreeConnection();
			statement = connection.prepareStatement(QUERY_FOR_LEAVING_FEEDBACK);

			statement.setString(1, comment);
			statement.setInt(2, userId);
			statement.setInt(3, courseId);

			statement.executeUpdate();

		} catch (ConnectionPoolException e) {
			throw new DAOException(e);

		} catch (SQLException e) {
			logger.error(ERROR_MESSAGE_PROBLEM_SQL, e);
			throw new DAOException(ERROR_MESSAGE_PROBLEM_SQL, e);

		} finally {
			try {
				connectionPool.closeConnection(statement, connection);
			} catch (ConnectionPoolException e) {
				throw new DAOException(e);
			}
		}
	}

}
