package by.epamtc.facultative.service.impl;

import by.epamtc.facultative.dao.CourseDAO;
import by.epamtc.facultative.dao.DAOFactory;
import by.epamtc.facultative.dao.exception.DAOException;
import by.epamtc.facultative.service.StudentStatusService;
import by.epamtc.facultative.service.exception.ServiceException;

public class StudentStatusServiceImpl implements StudentStatusService {

	private static final StudentStatusServiceImpl instance = new StudentStatusServiceImpl();

	private final int APPROVED_STUDENT_STATUS = 2;
	private final int DISAPPROVED_STUDENT_STATUS = 3;

	private StudentStatusServiceImpl() {

	}

	public static StudentStatusServiceImpl getInstance() {
		return instance;
	}

	@Override
	public void approveStudentOnCourse(int studentId, int runCourseId) throws ServiceException {

		DAOFactory daoFactory = DAOFactory.getInstance();
		CourseDAO courseDAO = daoFactory.getCourseDAO();

		try {
			courseDAO.changeStudentApprovalStatusOnRunCourse(studentId, runCourseId, APPROVED_STUDENT_STATUS);

		} catch (DAOException e) {
			throw new ServiceException(e);
		}

	}

	@Override
	public void disapproveStudentOnCourse(int studentId, int runCourseId) throws ServiceException {

		DAOFactory daoFactory = DAOFactory.getInstance();
		CourseDAO courseDAO = daoFactory.getCourseDAO();
		
		try {
			courseDAO.changeStudentApprovalStatusOnRunCourse(studentId, runCourseId, DISAPPROVED_STUDENT_STATUS);
		} catch (DAOException e) {
			throw new ServiceException(e);

		}

	}

}
