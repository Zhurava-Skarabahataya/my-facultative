package by.epamtc.facultative.service;

import by.epamtc.facultative.bean.RunnedCourse;
import by.epamtc.facultative.service.exception.ServiceException;

public interface CourseCreatorService {

	void createRunCourse(RunnedCourse infoAboutRunnedCourse) throws ServiceException;

}
