package by.epamtc.facultative.service;

import by.epamtc.facultative.dao.impl.CourseDAOImpl;

public class FeedbackService {
	
	private static final FeedbackService instance = new FeedbackService();
	
	private FeedbackService() {
		
	}
	
	public static FeedbackService getInstance() {
		return instance;
	}

	public void leaveFeadback(int userId, int courseId, String comment) {
		
		CourseDAOImpl courseDAO = CourseDAOImpl.getInstance();
		courseDAO.leaveFeedback( userId, courseId, comment);
		
	}

}
