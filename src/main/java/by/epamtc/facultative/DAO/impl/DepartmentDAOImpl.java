package by.epamtc.facultative.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import by.epamtc.facultative.bean.Department;
import by.epamtc.facultative.bean.Course;
import by.epamtc.facultative.bean.UserInfo;
import by.epamtc.facultative.dao.exception.DAOException;
import by.epamtc.facultative.dao.impl.pool.ConnectionPool;
import by.epamtc.facultative.dao.impl.pool.ConnectionPoolException;
import by.epamtc.facultative.service.FullNameService;

public class DepartmentDAOImpl {

	private static final DepartmentDAOImpl instance = new DepartmentDAOImpl();

	private final String QUERY_FOR_ALL_DEPARTMENTS = "SELECT "
			+ "departments.department_id, departments.name, departments.description, departments.dean_id,"
			+ " users.first_name, users.second_name, users.patronymic, users.user_login"
			+ " FROM departments JOIN users ON departments.dean_id = users.user_id";
	
	private final String QUERY_FOR_COURSES_IN_DEPARTMENT = "SELECT * "
			+ "FROM courses WHERE courses.department_id = ?";
	
	private final String QUERY_FOR_LECTURERS_IN_DEPARTMENT = "SELECT "
			+ "users.first_name, users.second_name, users.patronymic, users.user_login "
			+ "FROM users WHERE users.user_role_id = 2 AND users.department_department_id = ?";

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
				
				String deanFullName = FullNameService.getInstance().createFullName(deanFirstName, deanSecondName, deanPatronymic);
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

			ps.setInt(1,  departmentId);
			
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

			ps.setInt(1,  departmentId);
			
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

	

}
