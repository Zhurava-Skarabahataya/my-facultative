package by.epamtc.facultative.service;

import java.util.List;

import by.epamtc.facultative.bean.Course;
import by.epamtc.facultative.bean.RunnedCourse;
import by.epamtc.facultative.bean.UserInfo;
import by.epamtc.facultative.service.exception.ServiceException;

public interface CourseInfoService {

	List<Course> findAvailableCoursesForDepartment(int userDeparttmentId) throws ServiceException;

	List<RunnedCourse> findAllAvailableRunCourses() throws ServiceException;

	RunnedCourse findRunCourseById(int runCourseId) throws ServiceException;

	void findStudentRunCourses(UserInfo userPageInfo) throws ServiceException;

	void findLecturerRunCourses(UserInfo userPageInfo) throws ServiceException;

	void findDeanRunCourses(UserInfo userPageInfo);

	void applyStudentForRunCourse(int userId, int runCourseId) throws ServiceException;

	void findInfoAboutCourse(Course course) throws ServiceException;

	void removeApplicationStudentForRunCourse(int userId, int runCourseId) throws ServiceException;

	void giveStudentGraveOnRunCourse(int studentId, int runCourseId, int grade) throws ServiceException;

	void defineCourseLaunchStatus(RunnedCourse runnedCourse);

	void defineCourseLaunchStatus(List<RunnedCourse> courses);

	boolean isStudentOnRunCourse(int userId, RunnedCourse info);

	int getUserOnCourseApprovalStatusId(int userId, RunnedCourse info);

	int getUserMarkOnCourse(int userId, RunnedCourse runCourse);

}
