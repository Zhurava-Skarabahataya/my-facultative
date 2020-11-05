package by.epamtc.facultative.service.impl;

import by.epamtc.facultative.bean.RunnedCourse;
import by.epamtc.facultative.dao.CourseDAO;
import by.epamtc.facultative.dao.DAOFactory;
import by.epamtc.facultative.dao.exception.DAOException;
import by.epamtc.facultative.service.CourseCreatorService;
import by.epamtc.facultative.service.exception.ServiceException;

public class CourseCreatorServiceImpl implements CourseCreatorService{
	
	private static final CourseCreatorServiceImpl instance = new CourseCreatorServiceImpl();
	
	private CourseCreatorServiceImpl() {
		
	}
	
	public static CourseCreatorServiceImpl getInstance() {
		return instance;
	}

	@Override
	public void createRunCourse(RunnedCourse infoAboutRunnedCourse) throws ServiceException {
		
		DAOFactory daoFactory = DAOFactory.getInstance();
		CourseDAO courseInfoDAOImpl = daoFactory.getCourseDAO();
		try {
			courseInfoDAOImpl.createRunCourse(infoAboutRunnedCourse);
			
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

}
