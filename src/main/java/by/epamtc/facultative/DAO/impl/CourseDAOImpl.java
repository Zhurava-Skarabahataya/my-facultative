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
import by.epamtc.facultative.dao.exception.DAOException;
import by.epamtc.facultative.dao.impl.pool.ConnectionPool;
import by.epamtc.facultative.dao.impl.pool.ConnectionPoolException;
import by.epamtc.facultative.service.FullNameService;

public class CourseDAOImpl {

	private static final CourseDAOImpl instance = new CourseDAOImpl();

	private static final Logger logger = Logger.getLogger(CourseDAOImpl.class);

	private final String QUERY_FOR_AVAILABLE_COURSES_IN_DEPARTMENT = "SELECT course_id,"
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
			+ "users.second_name, users.patronymic, departments.name FROM courses JOIN run_courses "
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
			+ "users_has_run_courses.approval_status_id, approval_statuses.approval_status_name " + "FROM courses "
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

	private CourseDAOImpl() {

	}

	public static CourseDAOImpl getInstance() {
		return instance;
	}

	public List<Course> findCoursesFromDepartment(int departmentId) throws DAOException {

		List<Course> courses = new ArrayList<Course>();

		ConnectionPool cp = ConnectionPool.getInstance();
		Connection conn = null;

		try {
			conn = cp.getFreeConnection();
		} catch (ConnectionPoolException e) {
			throw new DAOException(e);
		}

		PreparedStatement ps = null;
		ResultSet rs = null;

		String query = QUERY_FOR_AVAILABLE_COURSES_IN_DEPARTMENT;

		try {
			ps = conn.prepareStatement(query);

			ps.setInt(1, departmentId);

			rs = ps.executeQuery();

			while (rs.next()) {
				int courseId = rs.getInt("course_id");
				String courseTitle = rs.getString("title");
				Course infoAboutCourse = new Course();
				infoAboutCourse.setCourseId(courseId);
				infoAboutCourse.setCourseName(courseTitle);
				courses.add(infoAboutCourse);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				cp.closeConnection(rs, ps, conn);
			} catch (ConnectionPoolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		// TODO Auto-generated method stub
		return courses;
	}

	public void createRunCourse(RunnedCourse infoAboutRunnedCourse) throws DAOException {
		// courses_course_id start_date, end_date, shedule, student_limit, classroom,
		// lecturer_id, ru_courses_status

		int coursesCourseId = infoAboutRunnedCourse.getCourseId();
		LocalDate startDate = infoAboutRunnedCourse.getDateOfStart();
		LocalDate endDate = infoAboutRunnedCourse.getDateOfEnd();

		String shedule = infoAboutRunnedCourse.getShedule();
		int studentLimit = infoAboutRunnedCourse.getStudentLimit();
		int classroom = infoAboutRunnedCourse.getClassroomNumber();
		int lecturerId = infoAboutRunnedCourse.getLecturerId();
		int courseStatus = infoAboutRunnedCourse.getCourseStatus();

		ConnectionPool cp = ConnectionPool.getInstance();
		Connection conn = null;

		try {
			conn = cp.getFreeConnection();
		} catch (ConnectionPoolException e) {
			throw new DAOException(e);
		}

		PreparedStatement ps = null;

		try {
			ps = conn.prepareStatement(QUERY_FOR_CREATING_RUN_COURSE);

			ps.setInt(1, coursesCourseId);
			Date sqlStartDate = Date.valueOf(startDate);
			ps.setDate(2, sqlStartDate);
			Date sqlEndDate = Date.valueOf(endDate);
			ps.setDate(3, sqlEndDate);
			ps.setString(4, shedule);
			ps.setInt(5, studentLimit);
			ps.setInt(6, classroom);
			ps.setInt(7, lecturerId);
			ps.setInt(8, courseStatus);

			ps.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				cp.closeConnection(ps, conn);
			} catch (ConnectionPoolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public List<RunnedCourse> getRunCoursesOfStudent(int userId) throws DAOException {

		List<RunnedCourse> courses = new ArrayList<RunnedCourse>();

		ConnectionPool cp = ConnectionPool.getInstance();
		Connection conn = null;

		try {
			conn = cp.getFreeConnection();
		} catch (ConnectionPoolException e) {
			throw new DAOException(e);
		}

		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = conn.prepareStatement(QUERY_FOR_STUDENT_RUN_COURSES);
			ps.setInt(1, userId);

			rs = ps.executeQuery();

			while (rs.next()) {
				int runCourseId = rs.getInt("run_courses.run_courses_id");

				Date sqlDateStart = rs.getDate("run_courses.start_date");
				LocalDate startDate = sqlDateStart.toLocalDate();
				Date sqlDateEnd = rs.getDate("run_courses.end_date");
				LocalDate endDate = sqlDateEnd.toLocalDate();

				String shcedule = rs.getString("run_courses.shcedule");

				int studentLimit = rs.getInt("run_courses.student_limit");

				int classroom = rs.getInt("run_courses.classroom");

				int runCourseStatusId = rs.getInt("run_courses.run_courses_status");

				int courseId = rs.getInt("courses.course_id");

				String courseTitle = rs.getString("courses.title");
				String courseDescription = rs.getString("courses.description");
				String courseProgram = rs.getString("courses.course_program");
				String courseRequirement = rs.getString("courses.requirement");
				int courseDuration = rs.getInt("courses.duration_in_hours");
				int departmentId = rs.getInt("courses.department_id");

				String studentStatusOnCourse = rs.getString("approval_statuses.approval_status_name");

				RunnedCourse infoAboutRunnedCourse = new RunnedCourse();
				infoAboutRunnedCourse.setClassroomNumber(classroom);
				infoAboutRunnedCourse.setCourseId(courseId);
				infoAboutRunnedCourse.setCourseName(courseTitle);
				infoAboutRunnedCourse.setCourseStatus(runCourseStatusId);
				infoAboutRunnedCourse.setDateOfEnd(endDate);
				infoAboutRunnedCourse.setDateOfStart(startDate);
				infoAboutRunnedCourse.setLecturerId(userId);
				infoAboutRunnedCourse.setRunCourseId(runCourseId);
				infoAboutRunnedCourse.setShedule(shcedule);
				infoAboutRunnedCourse.setStudentLimit(studentLimit);
				infoAboutRunnedCourse.setStudentStatusName(studentStatusOnCourse);

				Course infoAbourCourse = new Course();
				infoAbourCourse.setCourseDepartment(departmentId);
				infoAbourCourse.setCourseDescription(courseDescription);
				infoAbourCourse.setCourseDuration(courseDuration);
				infoAbourCourse.setCourseId(courseId);
				infoAbourCourse.setCourseName(courseTitle);
				infoAbourCourse.setCourseProgram(courseProgram);
				infoAbourCourse.setCourseRequirement(courseRequirement);

				infoAboutRunnedCourse.setInfoAboutCourse(infoAbourCourse);

				List<StudentOnCourse> students = findStudentsOnRunCourse(runCourseId);

				infoAboutRunnedCourse.setStudentsOnCourse(students);

				courses.add(infoAboutRunnedCourse);

			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		finally {
			try {
				cp.closeConnection(rs, ps, conn);
			} catch (ConnectionPoolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return courses;
	}

	public List<RunnedCourse> getRunCoursesOfLecturer(int userId) throws DAOException {

		List<RunnedCourse> courses = new ArrayList<RunnedCourse>();

		ConnectionPool cp = ConnectionPool.getInstance();
		Connection conn = null;

		try {
			conn = cp.getFreeConnection();
		} catch (ConnectionPoolException e) {
			throw new DAOException(e);
		}

		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = conn.prepareStatement(QUERY_FOR_LECTURER_RUN_COURSE);
			ps.setInt(1, userId);

			rs = ps.executeQuery();

			while (rs.next()) {
				int runCourseId = rs.getInt("run_courses.run_courses_id");
				Date sqlDateStart = rs.getDate("run_courses.start_date");
				LocalDate startDate = sqlDateStart.toLocalDate();
				Date sqlDateEnd = rs.getDate("run_courses.end_date");
				LocalDate endDate = sqlDateEnd.toLocalDate();
				String shcedule = rs.getString("run_courses.shcedule");
				int studentLimit = rs.getInt("run_courses.student_limit");
				int classroom = rs.getInt("run_courses.classroom");
				int runCourseStatusId = rs.getInt("run_courses.run_courses_status");
				int courseId = rs.getInt("courses.course_id");
				String courseTitle = rs.getString("courses.title");
				String courseDescription = rs.getString("courses.description");
				String courseProgram = rs.getString("courses.course_program");
				String courseRequirement = rs.getString("courses.requirement");
				int courseDuration = rs.getInt("courses.duration_in_hours");
				int departmentId = rs.getInt("courses.department_id");

				RunnedCourse infoAboutRunnedCourse = new RunnedCourse();
				infoAboutRunnedCourse.setClassroomNumber(classroom);
				infoAboutRunnedCourse.setCourseId(courseId);
				infoAboutRunnedCourse.setCourseName(courseTitle);
				infoAboutRunnedCourse.setCourseStatus(runCourseStatusId);
				infoAboutRunnedCourse.setDateOfEnd(endDate);
				infoAboutRunnedCourse.setDateOfStart(startDate);
				infoAboutRunnedCourse.setLecturerId(userId);
				infoAboutRunnedCourse.setRunCourseId(runCourseId);
				infoAboutRunnedCourse.setShedule(shcedule);
				infoAboutRunnedCourse.setStudentLimit(studentLimit);

				Course infoAbourCourse = new Course();
				infoAbourCourse.setCourseDepartment(departmentId);
				infoAbourCourse.setCourseDescription(courseDescription);
				infoAbourCourse.setCourseDuration(courseDuration);
				infoAbourCourse.setCourseId(courseId);
				infoAbourCourse.setCourseName(courseTitle);
				infoAbourCourse.setCourseProgram(courseProgram);
				infoAbourCourse.setCourseRequirement(courseRequirement);

				infoAboutRunnedCourse.setInfoAboutCourse(infoAbourCourse);

				List<StudentOnCourse> students = findStudentsOnRunCourse(runCourseId);

				infoAboutRunnedCourse.setStudentsOnCourse(students);

				courses.add(infoAboutRunnedCourse);

			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		finally {
			try {
				cp.closeConnection(rs, ps, conn);
			} catch (ConnectionPoolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return courses;
	}

	private List<StudentOnCourse> findStudentsOnRunCourse(int runCourseId) throws DAOException {

		List<StudentOnCourse> students = new ArrayList<StudentOnCourse>();

		ConnectionPool cp = ConnectionPool.getInstance();
		Connection conn = null;

		try {
			conn = cp.getFreeConnection();
		} catch (ConnectionPoolException e) {
			throw new DAOException(e);
		}

		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = conn.prepareStatement(QUERY_FOR_STUDENTS_ON_COURSE);

			ps.setInt(1, runCourseId);

			rs = ps.executeQuery();

			while (rs.next()) {
				String userLogin = rs.getString("users.user_login");
				String firstName = rs.getString("users.first_name");
				String secondName = rs.getString("users.second_name");
				String patronymic = rs.getString("users.patronymic");
				int userId = rs.getInt("users_has_run_courses.users_user_id");
				int userResult = rs.getInt("users_has_run_courses.user_result");
				int userApprovalStatusId = rs.getInt("users_has_run_courses.approval_status_id");
				String userApprovalStatusName = rs.getString("approval_statuses.approval_status_name");

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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		finally {
			try {
				cp.closeConnection(rs, ps, conn);
			} catch (ConnectionPoolException e) {

				e.printStackTrace();
			}
		}

		return students;
	}

	public List<RunnedCourse> findAllAvailableRunCourses() throws DAOException {

		List<RunnedCourse> courses = new ArrayList<RunnedCourse>();

		ConnectionPool cp = ConnectionPool.getInstance();
		Connection conn = null;

		try {
			conn = cp.getFreeConnection();
		} catch (ConnectionPoolException e) {
			throw new DAOException(e);
		}

		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = conn.prepareStatement(QUERY_FOR_ALL_AVAILABLE_COURSES);

			rs = ps.executeQuery();
			while (rs.next()) {
				String courseTitle = rs.getString("courses.title");
				String courseDescription = rs.getString("courses.description");
				String courseProgram = rs.getString("courses.course_program");
				String courseRequirement = rs.getString("courses.requirement");
				String shcedule = rs.getString("run_courses.shcedule");
				String lecturerFirstName = rs.getString("users.first_name");
				String lecturerSecondName = rs.getString("users.second_name");
				String lecturerPatronymic = rs.getString("users.patronymic");

				Date sqlDateStart = rs.getDate("run_courses.start_date");
				LocalDate startDate = sqlDateStart.toLocalDate();
				Date sqlDateEnd = rs.getDate("run_courses.end_date");
				LocalDate endDate = sqlDateEnd.toLocalDate();
				int studentLimit = rs.getInt("run_courses.student_limit");
				int runCourseId = rs.getInt("run_courses.run_courses_id");
				int classroom = rs.getInt("run_courses.classroom");
				int runCourseStatusId = rs.getInt("run_courses.run_courses_status");
				int courseId = rs.getInt("courses.course_id");
				int courseDuration = rs.getInt("courses.duration_in_hours");
				int departmentId = rs.getInt("courses.department_id");
				int lecturerId = rs.getInt("run_courses.lecturer_user_id");

				String lecturerFullName = FullNameService.getInstance().createFullName(lecturerFirstName,
						lecturerSecondName, lecturerPatronymic);

				RunnedCourse infoAboutRunnedCourse = new RunnedCourse();
				infoAboutRunnedCourse.setClassroomNumber(classroom);
				infoAboutRunnedCourse.setCourseId(courseId);
				infoAboutRunnedCourse.setCourseName(courseTitle);
				infoAboutRunnedCourse.setCourseStatus(runCourseStatusId);
				infoAboutRunnedCourse.setDateOfEnd(endDate);
				infoAboutRunnedCourse.setDateOfStart(startDate);
				infoAboutRunnedCourse.setLecturerId(lecturerId);
				infoAboutRunnedCourse.setRunCourseId(runCourseId);
				infoAboutRunnedCourse.setShedule(shcedule);
				infoAboutRunnedCourse.setStudentLimit(studentLimit);
				infoAboutRunnedCourse.setLecturerName(lecturerFullName);

				Course infoAbourCourse = new Course();
				infoAbourCourse.setCourseDepartment(departmentId);
				infoAbourCourse.setCourseDescription(courseDescription);
				infoAbourCourse.setCourseDuration(courseDuration);
				infoAbourCourse.setCourseId(courseId);
				infoAbourCourse.setCourseName(courseTitle);
				infoAbourCourse.setCourseProgram(courseProgram);
				infoAbourCourse.setCourseRequirement(courseRequirement);

				infoAboutRunnedCourse.setInfoAboutCourse(infoAbourCourse);

				List<StudentOnCourse> students = findStudentsOnRunCourse(runCourseId);

				infoAboutRunnedCourse.setStudentsOnCourse(students);

				courses.add(infoAboutRunnedCourse);

			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				cp.closeConnection(rs, ps, conn);
			} catch (ConnectionPoolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return courses;
	}

	public RunnedCourse findRunCourse(int runCourseId) throws DAOException {

		RunnedCourse infoAboutRunnedCourse = null;

		ConnectionPool cp = ConnectionPool.getInstance();
		Connection conn = null;

		try {
			conn = cp.getFreeConnection();
		} catch (ConnectionPoolException e) {
			throw new DAOException(e);
		}

		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = conn.prepareStatement(QUERY_FOR_RUN_COURSE_INFO_BY_RUN_COURSE_ID);
			ps.setInt(1, runCourseId);
			rs = ps.executeQuery();

			if (rs.next()) {
				String courseTitle = rs.getString("courses.title");
				String courseDescription = rs.getString("courses.description");
				String courseProgram = rs.getString("courses.course_program");
				String courseRequirement = rs.getString("courses.requirement");
				String shcedule = rs.getString("run_courses.shcedule");
				String lecturerFirstName = rs.getString("users.first_name");
				String lecturerSecondName = rs.getString("users.second_name");
				String lecturerPatronymic = rs.getString("users.patronymic");
				String departmentName = rs.getString("departments.name");

				Date sqlDateStart = rs.getDate("run_courses.start_date");
				LocalDate startDate = sqlDateStart.toLocalDate();
				Date sqlDateEnd = rs.getDate("run_courses.end_date");
				LocalDate endDate = sqlDateEnd.toLocalDate();
				int studentLimit = rs.getInt("run_courses.student_limit");
				int classroom = rs.getInt("run_courses.classroom");
				int runCourseStatusId = rs.getInt("run_courses.run_courses_status");
				int courseId = rs.getInt("courses.course_id");
				int courseDuration = rs.getInt("courses.duration_in_hours");
				int departmentId = rs.getInt("courses.department_id");
				int lecturerId = rs.getInt("run_courses.lecturer_user_id");

				String lecturerFullName = FullNameService.getInstance().createFullName(lecturerFirstName,
						lecturerSecondName, lecturerPatronymic);

				infoAboutRunnedCourse = new RunnedCourse();
				infoAboutRunnedCourse.setClassroomNumber(classroom);
				infoAboutRunnedCourse.setCourseId(courseId);
				infoAboutRunnedCourse.setCourseName(courseTitle);
				infoAboutRunnedCourse.setCourseStatus(runCourseStatusId);
				infoAboutRunnedCourse.setDateOfEnd(endDate);
				infoAboutRunnedCourse.setDateOfStart(startDate);
				infoAboutRunnedCourse.setLecturerId(lecturerId);
				infoAboutRunnedCourse.setRunCourseId(runCourseId);
				infoAboutRunnedCourse.setShedule(shcedule);
				infoAboutRunnedCourse.setStudentLimit(studentLimit);
				infoAboutRunnedCourse.setLecturerName(lecturerFullName);

				Course infoAbourCourse = new Course();
				infoAbourCourse.setCourseDepartment(departmentId);
				infoAbourCourse.setCourseDescription(courseDescription);
				infoAbourCourse.setCourseDuration(courseDuration);
				infoAbourCourse.setCourseId(courseId);
				infoAbourCourse.setCourseName(courseTitle);
				infoAbourCourse.setCourseProgram(courseProgram);
				infoAbourCourse.setCourseRequirement(courseRequirement);
				infoAbourCourse.setDepartmentName(departmentName);

				infoAboutRunnedCourse.setInfoAboutCourse(infoAbourCourse);

				List<StudentOnCourse> students = findStudentsOnRunCourse(runCourseId);

				infoAboutRunnedCourse.setStudentsOnCourse(students);

			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				cp.closeConnection(rs, ps, conn);
			} catch (ConnectionPoolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return infoAboutRunnedCourse;
	}

	public void applyStudentForRunCourse(int userId, int runCourseId) throws DAOException {

		ConnectionPool cp = ConnectionPool.getInstance();
		Connection conn = null;
		PreparedStatement ps = null;

		try {
			conn = cp.getFreeConnection();

			ps = conn.prepareStatement(QUERY_FOR_APPLYING_STUDENT_FOR_RUN_COURSE);

			ps.setInt(1, userId);
			ps.setInt(2, runCourseId);
			ps.setInt(3, 1);

			ps.executeUpdate();

		} catch (ConnectionPoolException e) {
			e.printStackTrace();
			throw new DAOException(e);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				cp.closeConnection(ps, conn);
			} catch (ConnectionPoolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public void finsInfoAboutCourse(Course course) throws DAOException {

		int courseId = course.getCourseId();

		ConnectionPool cp = ConnectionPool.getInstance();
		Connection conn = null;

		try {
			conn = cp.getFreeConnection();
		} catch (ConnectionPoolException e) {
			throw new DAOException(e);
		}

		PreparedStatement ps = null;
		ResultSet rs = null;

		String query = QUERY_FOR_COURSE_INFO;

		try {
			ps = conn.prepareStatement(query);

			ps.setInt(1, courseId);

			rs = ps.executeQuery();

			while (rs.next()) {

				String courseName = rs.getString("title");
				String description = rs.getString("description");
				String courseProgram = rs.getString("course_program");
				String requirement = rs.getString("requirement");
				int duration = rs.getInt("duration_in_hours");

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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		finally {

			try {
				cp.closeConnection(rs, ps, conn);
			} catch (ConnectionPoolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	private List<RunnedCourse> findRunnedCoursesByCourseId(int courseId) throws DAOException {

		List<RunnedCourse> runCourses = new ArrayList<RunnedCourse>();

		ConnectionPool cp = ConnectionPool.getInstance();
		Connection conn = null;

		try {
			conn = cp.getFreeConnection();
		} catch (ConnectionPoolException e) {
			throw new DAOException(e);
		}

		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = conn.prepareStatement(QUERY_FOR_RUN_COURSE_INFO_BY_COURSE_ID);
			ps.setInt(1, courseId);

			rs = ps.executeQuery();

			while (rs.next()) {
				int runCourseId = rs.getInt("run_courses.run_courses_id");
				Date sqlDateStart = rs.getDate("run_courses.start_date");
				LocalDate startDate = sqlDateStart.toLocalDate();
				Date sqlDateEnd = rs.getDate("run_courses.end_date");
				LocalDate endDate = sqlDateEnd.toLocalDate();
				String shcedule = rs.getString("run_courses.shcedule");
				int studentLimit = rs.getInt("run_courses.student_limit");
				int classroom = rs.getInt("run_courses.classroom");
				int runCourseStatusId = rs.getInt("run_courses.run_courses_status");
				String lecturerFirstName = rs.getString("users.first_name");
				String lecturerSecondName = rs.getString("users.second_name");
				String lecturerPatronymic = rs.getString("users.patronymic");

				RunnedCourse infoAboutRunnedCourse = new RunnedCourse();
				infoAboutRunnedCourse.setClassroomNumber(classroom);
				infoAboutRunnedCourse.setCourseId(courseId);
				infoAboutRunnedCourse.setCourseStatus(runCourseStatusId);
				infoAboutRunnedCourse.setDateOfEnd(endDate);
				infoAboutRunnedCourse.setDateOfStart(startDate);
				infoAboutRunnedCourse.setRunCourseId(runCourseId);
				infoAboutRunnedCourse.setShedule(shcedule);
				infoAboutRunnedCourse.setStudentLimit(studentLimit);

				FullNameService fullNameService = FullNameService.getInstance();
				String lecturerFullName = fullNameService.createFullName(lecturerFirstName, lecturerSecondName,
						lecturerPatronymic);

				infoAboutRunnedCourse.setLecturerName(lecturerFullName);

				List<StudentOnCourse> students = findStudentsOnRunCourse(runCourseId);

				infoAboutRunnedCourse.setStudentsOnCourse(students);

				runCourses.add(infoAboutRunnedCourse);

			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		finally {
			try {
				cp.closeConnection(rs, ps, conn);
			} catch (ConnectionPoolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return runCourses;
	}

	public void removeApplicationForRunCourse(int userId, int runCourseId) throws DAOException {

		ConnectionPool cp = ConnectionPool.getInstance();
		Connection conn = null;

		PreparedStatement ps = null;

		try {
			conn = cp.getFreeConnection();
			ps = conn.prepareStatement(QUERY_FOR_REMOVE_APPLICATION_STUDENT_FOR_RUN_COURSE);
			ps.setInt(1, userId);
			ps.setInt(2, runCourseId);

			ps.execute();

		} catch (ConnectionPoolException e) {
			throw new DAOException(e);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				cp.closeConnection(ps, conn);
			} catch (ConnectionPoolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public void changeStudentApprovalStatusOnRunCourse(int studentId, int runCourseId, int status) throws DAOException {
		ConnectionPool cp = ConnectionPool.getInstance();
		Connection conn = null;

		PreparedStatement ps = null;

		try {
			conn = cp.getFreeConnection();
			ps = conn.prepareStatement(QUERY_FOR_CHANGING_APPROVAL_STATUS_ON_COURSE);
			
			ps.setInt(1, status);
			ps.setInt(2, studentId);
			ps.setInt(3, runCourseId);
			
			ps.executeUpdate();
			
		} catch (ConnectionPoolException e) {
			throw new DAOException(e);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
				try {
					cp.closeConnection(ps, conn);
				} catch (ConnectionPoolException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		
		}
		
	}

}
