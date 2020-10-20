package by.epamtc.facultative.service;

import by.epamtc.facultative.dao.exception.DAOException;
import by.epamtc.facultative.dao.impl.CourseDAOImpl;

public class StudentStatusService {
	
	private static final StudentStatusService instance = new StudentStatusService();
	
	private final int APPROVED_STUDENT_STATUS = 2;
	private final int DISAPPROVED_STUDENT_STATUS = 3;
	
	private StudentStatusService() {
		
	}
	
	public static StudentStatusService getInstance () {
		return instance;
	}

	public void approveStudentOnCourse(int studentId, int runCourseId) {
		
		CourseDAOImpl courseDAOImpl = CourseDAOImpl.getInstance();
		try {
			courseDAOImpl.changeStudentApprovalStatusOnRunCourse(studentId, runCourseId, APPROVED_STUDENT_STATUS);
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
public void disapproveStudentOnCourse(int studentId, int runCourseId) {
		
		CourseDAOImpl courseDAOImpl = CourseDAOImpl.getInstance();
		try {
			courseDAOImpl.changeStudentApprovalStatusOnRunCourse(studentId, runCourseId, DISAPPROVED_STUDENT_STATUS);
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	

}
