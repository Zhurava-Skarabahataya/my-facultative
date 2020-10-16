package by.epamtc.facultative.service;

import java.util.List;

import by.epamtc.facultative.bean.InfoAboutCourse;
import by.epamtc.facultative.dao.exception.DAOException;
import by.epamtc.facultative.dao.impl.CourseInfoDAOImpl;

public class CourseInfoProvider {
	
	private static final CourseInfoProvider instance = new CourseInfoProvider();
	
	private CourseInfoProvider() {
		
		
	}

	public static CourseInfoProvider getInstance() {
		return instance;
	}

	public List<InfoAboutCourse> findAvailableCoursesForDepartment(int userDeparttmentId) {
		
		List<InfoAboutCourse> courses = null;
		int departmentId = userDeparttmentId;
		
		CourseInfoDAOImpl courseInfoDAOImpl = CourseInfoDAOImpl.getInstance();
		try {
			courses = courseInfoDAOImpl.findCoursesFromDepartment(departmentId);
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return courses;
	}
	
	
}
