package by.epamtc.facultative.service;

import by.epamtc.facultative.bean.InfoAboutRunnedCourse;
import by.epamtc.facultative.dao.exception.DAOException;
import by.epamtc.facultative.dao.impl.CourseInfoDAOImpl;

public class CourseCreatorService {
	
	private static final CourseCreatorService instance = new CourseCreatorService();
	
	private CourseCreatorService() {
		
	}
	
	public static CourseCreatorService getInstance() {
		return instance;
	}

	public void createRunCourse(InfoAboutRunnedCourse infoAboutRunnedCourse) {
		
		CourseInfoDAOImpl courseInfoDAOImpl = CourseInfoDAOImpl.getInstance();
		try {
			courseInfoDAOImpl.createRunCourse(infoAboutRunnedCourse);
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
