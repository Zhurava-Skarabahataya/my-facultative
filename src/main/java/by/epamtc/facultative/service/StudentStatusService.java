package by.epamtc.facultative.service;

import by.epamtc.facultative.service.exception.ServiceException;

public interface StudentStatusService {

	void approveStudentOnCourse(int studentId, int runCourseId) throws ServiceException;

	void disapproveStudentOnCourse(int studentId, int runCourseId) throws ServiceException;

	void expelStudent(int userId) throws ServiceException;

}
