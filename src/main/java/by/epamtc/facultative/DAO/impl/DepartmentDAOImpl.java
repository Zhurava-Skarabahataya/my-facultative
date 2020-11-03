package by.epamtc.facultative.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import by.epamtc.facultative.bean.Department;
import by.epamtc.facultative.bean.Mark;
import by.epamtc.facultative.bean.Course;
import by.epamtc.facultative.bean.UserInfo;
import by.epamtc.facultative.dao.exception.DAOException;
import by.epamtc.facultative.dao.impl.pool.ConnectionPool;
import by.epamtc.facultative.dao.impl.pool.exception.ConnectionPoolException;
import by.epamtc.facultative.service.impl.FullNameServiceImpl;

public class DepartmentDAOImpl {

	private static final DepartmentDAOImpl instance = new DepartmentDAOImpl();

	private final String QUERY_FOR_ALL_DEPARTMENTS = "SELECT "
			+ "departments.department_id, departments.name, departments.description, departments.dean_id,"
			+ " users.first_name, users.second_name, users.patronymic, users.user_login"
			+ " FROM departments JOIN users ON departments.dean_id = users.user_id";

	private final String QUERY_FOR_COURSES_IN_DEPARTMENT = "SELECT * " + "FROM courses WHERE courses.department_id = ?";

	private final String QUERY_FOR_LECTURERS_IN_DEPARTMENT = "SELECT "
			+ "users.first_name, users.second_name, users.patronymic, users.user_login "
			+ "FROM users WHERE users.user_role_id = 2 AND users.department_department_id = ? AND users.status < 4";

	private final String QUERY_FOR_STUDENTS_IN_DEPARTMENT = "SELECT "
			+ "users.user_id , users.first_name, users.second_name, users.patronymic, users.user_login "
			+ "FROM users WHERE users.user_role_id = 1 AND status = 1 AND users.department_department_id = ?";

	private final String QUERY_FOR_STUDENTS_IN_ALL_DEPARTMENTS = "SELECT "
			+ "users.user_id, users.first_name, users.second_name, users.patronymic, "
			+ "users.user_login, departments.name, departments.department_id, "
			+ "user_details.user_mobile_number, user_details.user_adress, user_details.user_date_of_birth "
			+ " FROM users JOIN user_details ON users.user_id = user_details.users_user_id "
			+ "JOIN departments ON departments.department_id = users.department_department_id "
			+ "WHERE users.user_role_id = 1 AND users.status != 3";

	private DepartmentDAOImpl() {

	}

	public static DepartmentDAOImpl getInstance() {
		return instance;
	}

	public List<Department> findAllDepartments() throws DAOException {

		List<Department> departmets = new ArrayList<Department>();

		ConnectionPool cp = ConnectionPool.getInstance();
		Connection conn = null;

		try {
			conn = cp.getFreeConnection();
		} catch (ConnectionPoolException e) {
			throw new DAOException(e);
		}

		PreparedStatement ps = null;
		ResultSet rs = null;

		String query = QUERY_FOR_ALL_DEPARTMENTS;

		try {
			ps = conn.prepareStatement(query);

			rs = ps.executeQuery();

			while (rs.next()) {

				int departmentId = rs.getInt("departments.department_id");
				int departmentDeanId = rs.getInt("departments.dean_id");
				String departmentName = rs.getString("departments.name");
				String departmentDescription = rs.getString("departments.description");
				String deanFirstName = rs.getString("users.first_name");
				String deanSecondName = rs.getString("users.second_name");
				String deanPatronymic = rs.getString("users.patronymic");
				String deanLogin = rs.getString("users.user_login");

				Department department = new Department();
				department.setDepartmentID(departmentId);
				department.setDeanId(departmentDeanId);
				department.setDepartmentName(departmentName);
				department.setDepartmentDescription(departmentDescription);

				String deanFullName = FullNameServiceImpl.getInstance().createFullName(deanFirstName, deanSecondName,
						deanPatronymic);
				department.setDeanName(deanFullName);
				department.setDeanLogin(deanLogin);

				departmets.add(department);

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
		return departmets;
	}

	public List<UserInfo> findLecturersInDepartment(int departmentId) throws DAOException {
		List<UserInfo> lecturers = new ArrayList<UserInfo>();

		ConnectionPool cp = ConnectionPool.getInstance();
		Connection conn = null;

		try {
			conn = cp.getFreeConnection();
		} catch (ConnectionPoolException e) {
			throw new DAOException(e);
		}

		PreparedStatement ps = null;
		ResultSet rs = null;

		String query = QUERY_FOR_LECTURERS_IN_DEPARTMENT;

		try {
			ps = conn.prepareStatement(query);

			ps.setInt(1, departmentId);

			rs = ps.executeQuery();

			while (rs.next()) {
				String firstName = rs.getString("users.first_name");
				String secondName = rs.getString("users.second_name");
				String patronymic = rs.getString("users.patronymic");
				String userLogin = rs.getString("users.user_login");

				UserInfo lecturer = new UserInfo();
				lecturer.setUserFirstName(firstName);
				lecturer.setUserSecondName(secondName);
				lecturer.setUserPatronymic(patronymic);
				lecturer.setUserLogin(userLogin);

				lecturers.add(lecturer);
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
		return lecturers;
	}

	public List<Course> findCoursesInDepartment(int departmentId) throws DAOException {

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

		String query = QUERY_FOR_COURSES_IN_DEPARTMENT;

		try {
			ps = conn.prepareStatement(query);

			ps.setInt(1, departmentId);

			rs = ps.executeQuery();

			while (rs.next()) {
				String courseTitle = rs.getString("courses.title");
				String courseDescription = rs.getString("courses.description");
				int courseId = rs.getInt("courses.course_id");

				Course course = new Course();
				course.setCourseName(courseTitle);
				course.setCourseDescription(courseDescription);
				course.setCourseId(courseId);

				courses.add(course);
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

	public List<UserInfo> findStudentsInDepartment(int departmentId) throws DAOException {

		List<UserInfo> students = new ArrayList<UserInfo>();

		ConnectionPool cp = ConnectionPool.getInstance();
		Connection conn = null;

		try {
			conn = cp.getFreeConnection();
		} catch (ConnectionPoolException e) {
			throw new DAOException(e);
		}

		PreparedStatement ps = null;
		ResultSet rs = null;

		String query = QUERY_FOR_STUDENTS_IN_DEPARTMENT;

		try {
			ps = conn.prepareStatement(query);

			ps.setInt(1, departmentId);

			rs = ps.executeQuery();

			while (rs.next()) {
				
				
				int studentId = rs.getInt("users.user_id");
				String firstName = rs.getString("users.first_name");
				String secondName = rs.getString("users.second_name");
				String patronymic = rs.getString("users.patronymic");
				String userLogin = rs.getString("users.user_login");

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

		return students;
	}

	public List<UserInfo> findStudentsInAllDepartments() throws DAOException {

		List<UserInfo> studentsOfAllDepartments = new ArrayList<UserInfo>();

		ConnectionPool cp = ConnectionPool.getInstance();
		Connection conn = null;

		try {
			conn = cp.getFreeConnection();

			PreparedStatement ps = null;
			ResultSet resultSet = null;

			String query = QUERY_FOR_STUDENTS_IN_ALL_DEPARTMENTS;

			ps = conn.prepareStatement(query);

			resultSet = ps.executeQuery();

			while (resultSet.next()) {
				int userId = resultSet.getInt("users.user_id");
				String userFirstName = resultSet.getString("users.first_name");
				String userSecondName = resultSet.getString("users.second_name");
				String userPatronymic = resultSet.getString("users.patronymic");
				String userLogin = resultSet.getString("users.user_login");
				String userMobile = resultSet.getString("user_details.user_mobile_number");
				String userAdress = resultSet.getString("user_details.user_adress");
				LocalDate userDateOfBirth = null;
				java.sql.Date sqlDate = resultSet.getDate("user_details.user_date_of_birth");
				if (sqlDate != null) {
					userDateOfBirth = sqlDate.toLocalDate();
				}
				int departmentId = resultSet.getInt("departments.department_id");
				String departmentName = resultSet.getString("departments.name");

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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return studentsOfAllDepartments;
	}

}
