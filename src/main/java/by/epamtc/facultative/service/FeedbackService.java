package by.epamtc.facultative.service;

import by.epamtc.facultative.service.exception.ServiceException;

public interface FeedbackService {

	void leaveFeadback(int userId, int courseId, String comment) throws ServiceException;

}
