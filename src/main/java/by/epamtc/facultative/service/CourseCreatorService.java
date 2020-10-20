package by.epamtc.facultative.service;

import by.epamtc.facultative.bean.RunnedCourse;
import by.epamtc.facultative.dao.exception.DAOException;
import by.epamtc.facultative.dao.impl.CourseDAOImpl;

public class CourseCreatorService {
	
	private static final CourseCreatorService instance = new CourseCreatorService();
	
	private CourseCreatorService() {
		
	}
	
	public static CourseCreatorService getInstance() {
		return instance;
	}

	public void createRunCourse(RunnedCourse infoAboutRunnedCourse) {
		
		CourseDAOImpl courseInfoDAOImpl = CourseDAOImpl.getInstance();
		try {
			courseInfoDAOImpl.createRunCourse(infoAboutRunnedCourse);
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
