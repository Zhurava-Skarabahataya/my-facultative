package by.epamtc.facultative.dao;

import java.util.List;

import by.epamtc.facultative.bean.Course;
import by.epamtc.facultative.bean.RunnedCourse;
import by.epamtc.facultative.bean.StudentOnCourse;
import by.epamtc.facultative.bean.UserInfo;
import by.epamtc.facultative.dao.exception.DAOException;

public interface CourseDAO {

	void createRunCourse(RunnedCourse infoAboutRunnedCourse) throws DAOException;

	void getRunCoursesOfStudent(UserInfo userPageInfo) throws DAOException;

	void getRunCoursesOfLecturer(UserInfo userPageInfo) throws DAOException;

	void findInfoAboutCourse(Course course) throws DAOException;

	void applyStudentForRunCourse(int userId, int runCourseId) throws DAOException;

	void removeApplicationForRunCourse(int userId, int runCourseId) throws DAOException;

	void changeStudentApprovalStatusOnRunCourse(int studentId, int runCourseId, int status) throws DAOException;

	void giveStudentGraveOnRunCourse(int studentId, int runCourseId, int grade) throws DAOException;

	void leaveFeedback(int userId, int courseId, String comment) throws DAOException;

	List<StudentOnCourse> findStudentsOnRunCourse(int runCourseId) throws DAOException;

	List<Course> findCoursesFromDepartment(int departmentId) throws DAOException;

	List<RunnedCourse> findAllAvailableRunCourses() throws DAOException;

	List<RunnedCourse> findRunnedCoursesByCourseId(int courseId) throws DAOException;

	RunnedCourse findRunCourse(int runCourseId) throws DAOException;

}
