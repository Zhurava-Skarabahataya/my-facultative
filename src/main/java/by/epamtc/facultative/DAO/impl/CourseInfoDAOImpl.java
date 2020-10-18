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

import by.epamtc.facultative.bean.CourseStudentInfo;
import by.epamtc.facultative.bean.InfoAboutCourse;
import by.epamtc.facultative.bean.InfoAboutRunnedCourse;
import by.epamtc.facultative.bean.UserInfo;
import by.epamtc.facultative.dao.exception.DAOException;
import by.epamtc.facultative.dao.impl.pool.ConnectionPool;
import by.epamtc.facultative.dao.impl.pool.ConnectionPoolException;
import by.epamtc.facultative.service.FullNameService;

public class CourseInfoDAOImpl {

	private static final CourseInfoDAOImpl instance = new CourseInfoDAOImpl();

	private static final Logger logger = Logger.getLogger(CourseInfoDAOImpl.class);

	private final String QUERY_FOR_AVAILABLE_COURSES_IN_DEPARTMENT = "SELECT course_id,"
			+ "title FROM courses WHERE department_id = ?";

	private final String QUERY_FOR_ALL_AVAILABLE_COURSES = "SELECT run_courses.run_courses_id, "
			+ "run_courses.start_date, run_courses.end_date, run_courses.shcedule, "
			+ "run_courses.student_limit, run_courses.classroom, run_courses.run_courses_status, run_courses.lecturer_user_id, "
			+ "courses.course_id, courses.title, courses.description, courses.course_program, "
			+ "courses.requirement, courses.duration_in_hours, courses.department_id, "
			+ "run_courses_statuses.run_courses_statuses_title, users.first_name, "
			+ "users.second.name, users.patronymic FROM courses JOIN run_courses "
			+ "ON courses.course_id = run_courses.courses_course_id "
			+ "JOIN run_courses_statuses ON run_courses_statuses.run_courses_statuses_id = "
			+ "run_courses.run_courses_status JOIN users ON users.user_id = run_courses.lecturer_user_id";

	private final String QUERY_FOR_CREATING_RUN_COURSE = "INSERT INTO run_courses (courses_course_id, "
			+ " start_date, end_date, shcedule, student_limit, classroom, lecturer_user_id, run_courses_status) "
			+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

	private final String QUERY_FOR_STUDENT_RUN_COURSE = "";

	private final String QUERY_FOR_LECTURER_RUN_COURSE = "SELECT run_courses.run_courses_id,"
			+ " run_courses.start_date, run_courses.end_date, run_courses.shcedule, run_courses.student_limit, "
			+ " run_courses.classroom, run_courses.run_courses_status, "
			+ " courses.course_id, courses.title, courses.description, courses.course_program, "
			+ "courses.requirement, courses.duration_in_hours, courses.department_id, run_courses_statuses.run_courses_statuses_title"
			+ "  FROM courses JOIN run_courses ON courses.course_id = run_courses.courses_course_id "
			+ "JOIN run_courses_statuses ON run_courses_statuses.run_courses_statuses_id = "
			+ "run_courses.run_courses_status WHERE run_courses.lecturer_user_id = ? ";

	private final String QUERY_FOR_STUDENTS_ON_COURSE = "SELECT users.user_login, users.first_name, users.second_name, "
			+ "users.patronymic, users_has_run_courses.users_user_id, "
			+ "users_has_run_courses.user_result, users_has_run_courses.approval_status_id, "
			+ "approval_statuses.approval_status_name FROM users_has_run_courses JOIN users "
			+ "ON users.user_id = users_has_run_courses.users_user_id JOIN approval_statuses "
			+ "ON users_has_run_courses.approval_status_id = approval_statuses.approval_status_id"
			+ " WHERE users_has_run_courses.run_courses_id = ?";

	private CourseInfoDAOImpl() {

	}

	public static CourseInfoDAOImpl getInstance() {
		return instance;
	}

	public List<InfoAboutCourse> findCoursesFromDepartment(int departmentId) throws DAOException {

		List<InfoAboutCourse> courses = new ArrayList<InfoAboutCourse>();

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
				InfoAboutCourse infoAboutCourse = new InfoAboutCourse();
				infoAboutCourse.setCourseId(courseId);
				infoAboutCourse.setCourseName(courseTitle);
				courses.add(infoAboutCourse);
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

		// TODO Auto-generated method stub
		return courses;
	}

	public void createRunCourse(InfoAboutRunnedCourse infoAboutRunnedCourse) throws DAOException {
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
		}
		finally {
			try {
				cp.closeConnection(ps, conn);
			} catch (ConnectionPoolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public List<InfoAboutRunnedCourse> getRunCoursesOfStudent(int userId) throws DAOException {

		ConnectionPool cp = ConnectionPool.getInstance();
		Connection conn = null;

		try {
			conn = cp.getFreeConnection();
		} catch (ConnectionPoolException e) {
			throw new DAOException(e);
		}

		PreparedStatement ps = null;

		return null;
	}

	public List<InfoAboutRunnedCourse> getRunCoursesOfLecturer(int userId) throws DAOException {

		List<InfoAboutRunnedCourse> courses = new ArrayList<InfoAboutRunnedCourse>();

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
				String runCourseStatusName = rs.getString("run_courses_statuses.run_courses_statuses_title");

				InfoAboutRunnedCourse infoAboutRunnedCourse = new InfoAboutRunnedCourse();
				infoAboutRunnedCourse.setClassroomNumber(classroom);
				infoAboutRunnedCourse.setCourseId(courseId);
				infoAboutRunnedCourse.setCourseName(courseTitle);
				infoAboutRunnedCourse.setCourseStatus(runCourseStatusId);
				infoAboutRunnedCourse.setCourseStatusName(runCourseStatusName);
				infoAboutRunnedCourse.setDateOfEnd(endDate);
				infoAboutRunnedCourse.setDateOfStart(startDate);
				infoAboutRunnedCourse.setLecturerId(userId);
				infoAboutRunnedCourse.setRunCourseId(runCourseId);
				infoAboutRunnedCourse.setShedule(shcedule);
				infoAboutRunnedCourse.setStudentLimit(studentLimit);

				InfoAboutCourse infoAbourCourse = new InfoAboutCourse();
				infoAbourCourse.setCourseDepartment(departmentId);
				infoAbourCourse.setCourseDescription(courseDescription);
				infoAbourCourse.setCourseDuration(courseDuration);
				infoAbourCourse.setCourseId(courseId);
				infoAbourCourse.setCourseName(courseTitle);
				infoAbourCourse.setCourseProgram(courseProgram);
				infoAbourCourse.setCourseRequirement(courseRequirement);

				infoAboutRunnedCourse.setInfoAboutCourse(infoAbourCourse);

				
				
				List<CourseStudentInfo> students = findStudentsOnRunCourse(runCourseId);
				

				infoAboutRunnedCourse.setStudentsOnCourse(students);

				courses.add(infoAboutRunnedCourse);
				System.out.println("дОБАВИЛИ курс");

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

	private List<CourseStudentInfo> findStudentsOnRunCourse(int runCourseId) throws DAOException {
		
		List<CourseStudentInfo> students = new ArrayList<CourseStudentInfo>();
		
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
			
			while(rs.next()) {
				String userLogin = rs.getString("users.user_login");
				String firstName = rs.getString("users.first_name");
				String secondName = rs.getString("users.second_name");
				String patronymic = rs.getString("users.patronymic");
				int userId = rs.getInt("users_has_run_course.users_user_id");
				int userResult = rs.getInt("users_has_run_course.user_result");
				int userApprovalStatusId = rs.getInt("users_has_run_course.approval_status_id");
				String userApprovalStatusName = rs.getString("approval_statuses.approval_status_name");
				
				CourseStudentInfo student = new CourseStudentInfo();
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

	public List<InfoAboutRunnedCourse> findAllAvailableRunCourses() throws DAOException {
		
		List<InfoAboutRunnedCourse> courses = new ArrayList<InfoAboutRunnedCourse>();
		
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
				String runCourseStatusName = rs.getString("run_courses_statuses.run_courses_statuses_title");
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
				
				String lecturerFullName = FullNameService.getInstance().createFullName(lecturerFirstName, lecturerSecondName, lecturerPatronymic);
				
				InfoAboutRunnedCourse infoAboutRunnedCourse = new InfoAboutRunnedCourse();
				infoAboutRunnedCourse.setClassroomNumber(classroom);
				infoAboutRunnedCourse.setCourseId(courseId);
				infoAboutRunnedCourse.setCourseName(courseTitle);
				infoAboutRunnedCourse.setCourseStatus(runCourseStatusId);
				infoAboutRunnedCourse.setCourseStatusName(runCourseStatusName);
				infoAboutRunnedCourse.setDateOfEnd(endDate);
				infoAboutRunnedCourse.setDateOfStart(startDate);
				infoAboutRunnedCourse.setLecturerId(lecturerId);
				infoAboutRunnedCourse.setRunCourseId(runCourseId);
				infoAboutRunnedCourse.setShedule(shcedule);
				infoAboutRunnedCourse.setStudentLimit(studentLimit);
				infoAboutRunnedCourse.setLecturerName(lecturerFullName);

				InfoAboutCourse infoAbourCourse = new InfoAboutCourse();
				infoAbourCourse.setCourseDepartment(departmentId);
				infoAbourCourse.setCourseDescription(courseDescription);
				infoAbourCourse.setCourseDuration(courseDuration);
				infoAbourCourse.setCourseId(courseId);
				infoAbourCourse.setCourseName(courseTitle);
				infoAbourCourse.setCourseProgram(courseProgram);
				infoAbourCourse.setCourseRequirement(courseRequirement);

				infoAboutRunnedCourse.setInfoAboutCourse(infoAbourCourse);

				
				
				List<CourseStudentInfo> students = findStudentsOnRunCourse(runCourseId);
				

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

}
