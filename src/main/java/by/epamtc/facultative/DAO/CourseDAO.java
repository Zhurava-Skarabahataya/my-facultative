package by.epamtc.facultative.dao;

import java.util.List;

import by.epamtc.facultative.bean.Course;
import by.epamtc.facultative.bean.RunnedCourse;
import by.epamtc.facultative.bean.StudentOnCourse;
import by.epamtc.facultative.bean.UserInfo;
import by.epamtc.facultative.dao.exception.DAOException;

public interface CourseDAO {
	
	public void createRunCourse(RunnedCourse infoAboutRunnedCourse) throws DAOException;

	public void getRunCoursesOfStudent(UserInfo userPageInfo) throws DAOException;
	public void getRunCoursesOfLecturer(UserInfo userPageInfo) throws DAOException;
	public void findInfoAboutCourse(Course course) throws DAOException;
	
	public void applyStudentForRunCourse(int userId, int runCourseId) throws DAOException;
	public void removeApplicationForRunCourse(int userId, int runCourseId) throws DAOException;
	public void changeStudentApprovalStatusOnRunCourse(int studentId, int runCourseId, int status) throws DAOException;
	public void giveStudentGraveOnRunCourse(int studentId, int runCourseId, int grade) throws DAOException;
	public void leaveFeedback(int userId, int courseId, String comment) throws DAOException;
	
	public List<StudentOnCourse> findStudentsOnRunCourse(int runCourseId) throws DAOException;
	public List<Course> findCoursesFromDepartment(int departmentId) throws DAOException;
	public List<RunnedCourse> findAllAvailableRunCourses() throws DAOException;
	public List<RunnedCourse> findRunnedCoursesByCourseId(int courseId) throws DAOException;
	
	public RunnedCourse findRunCourse(int runCourseId) throws DAOException;
	

}
