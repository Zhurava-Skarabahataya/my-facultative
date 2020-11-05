package by.epamtc.facultative.service.impl;

import by.epamtc.facultative.dao.CourseDAO;
import by.epamtc.facultative.dao.DAOFactory;
import by.epamtc.facultative.dao.exception.DAOException;
import by.epamtc.facultative.service.FeedbackService;
import by.epamtc.facultative.service.exception.ServiceException;

public class FeedbackServiceImpl implements FeedbackService{
	
	private static final FeedbackServiceImpl instance = new FeedbackServiceImpl();
	
	private FeedbackServiceImpl() {
		
	}
	
	public static FeedbackServiceImpl getInstance() {
		return instance;
	}

	@Override
	public void leaveFeadback(int userId, int courseId, String comment) throws ServiceException {
		
		DAOFactory daoFactory = DAOFactory.getInstance();
		
		CourseDAO courseDAO = daoFactory.getCourseDAO();
		
		try {
			courseDAO.leaveFeedback( userId, courseId, comment);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

}
