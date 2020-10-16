package by.epamtc.facultative.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import by.epamtc.facultative.bean.InfoAboutCourse;
import by.epamtc.facultative.dao.exception.DAOException;
import by.epamtc.facultative.dao.impl.pool.ConnectionPool;
import by.epamtc.facultative.dao.impl.pool.ConnectionPoolException;

public class CourseInfoDAOImpl {

	private static final CourseInfoDAOImpl instance = new CourseInfoDAOImpl();

	private static final Logger logger = Logger.getLogger(CourseInfoDAOImpl.class);

	private final String QUERY_FOR_AVAILABLE_COURSES_IN_DEPARTMENT = "SELECT course_id,"
			+ "title FROM courses WHERE department_id = ?";

	private final String QUERY_FOR_AVAILABLE_COURSES_ALL = "SELECT courses.course.id, courses.title, courses.description, courses.course_program, course";

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

		// TODO Auto-generated method stub
		return courses;
	}

}
