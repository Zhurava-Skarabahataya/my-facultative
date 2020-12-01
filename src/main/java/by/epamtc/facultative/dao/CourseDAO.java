package by.epamtc.facultative.dao;

import java.util.List;

import by.epamtc.facultative.bean.Course;
import by.epamtc.facultative.bean.RunnedCourse;
import by.epamtc.facultative.bean.StudentOnCourse;
import by.epamtc.facultative.bean.UserInfo;
import by.epamtc.facultative.dao.exception.DAOException;

/**
 * Interface provides methods for manipulation information concerning courses.
 * Methods should be implemented by classes who execute connection to database,
 * creating courses, editing information about courses, adding students to the
 * course and dropping them out.
 */
public interface CourseDAO {

	/**
	 * Method that connects to database and is used when creating a new run course.
	 * 
	 * @param infoAboutRunnedCourse Object of {@link RunnedCourse} which contains
	 *                              info about new course: it's title, dates of
	 *                              start and end, classroom and schedule.
	 * @throws DAOException when problems with database access occur. 
	 */
	void createRunCourse(RunnedCourse infoAboutRunnedCourse) throws DAOException;

	/**
	 * Method that connects to database and finds run courses of student.
	 * 
	 * @param userPageInfo Object of {@link UserInfo} with info about student
	 * @throws DAOException when problems with database access occur.
	 */
	void findRunCoursesOfStudent(UserInfo userPageInfo) throws DAOException;

	/**
	 * Method that connects to database and finds run courses of lecturer.
	 * 
	 * @param userPageInfo Object of {@link UserInfo} with info about lecturer
	 * @throws DAOException when problems with database access occur.
	 */
	void findRunCoursesOfLecturer(UserInfo userPageInfo) throws DAOException;

	/**
	 * Method that connects to database and finds information about course.
	 * 
	 * @param course Object of class {@link Course} with info about course id
	 * @throws DAOException when problems with database access occur.
	 */
	void findInfoAboutCourse(Course course) throws DAOException;

	/**
	 * Method connects to database and applies student for a run course.
	 * 
	 * @param userId      id of user
	 * @param runCourseId id of run course
	 * @throws DAOException when problems with database access occur.
	 */
	void applyStudentForRunCourse(int userId, int runCourseId) throws DAOException;

	/**
	 * Method connects to database and removes student's application for a run course.
	 * 
	 * @param userId      id of user
	 * @param runCourseId id of run course
	 * @throws DAOException when problems with database access occur.
	 */
	void removeApplicationForRunCourse(int userId, int runCourseId) throws DAOException;

	/**
	 * Connects to database and changes user's approval status on course.
	 * 
	 * @param studentId   id of student
	 * @param runCourseId id of the run course
	 * @param status      approval status id
	 * @throws DAOException when problems with database access occur.
	 */
	void changeStudentApprovalStatusOnRunCourse(int studentId, int runCourseId, int status) throws DAOException;

	/**
	 * Connects to database and gives student a grave for the course.
	 * @param studentId id of the student
	 * @param runCourseId id of the run course
	 * @param grade grade for the course
	 * @throws DAOException when problems with database access occur.
	 */
	void giveStudentGraveOnRunCourse(int studentId, int runCourseId, int grade) throws DAOException;

	/**
	 * Method leaves feedbacks about course into database.
	 * @param userId id of the user
	 * @param courseId id of the course
	 * @param comment text of the feedback
	 * @throws DAOException when problems with database access occur.
	 */
	void leaveFeedback(int userId, int courseId, String comment) throws DAOException;

	/**
	 * Method connects to database and finds students on run course.
	 * @param runCourseId id of the course
	 * @return List of {@link StudentOnCourse} of students that are enrolled for the course
	 * @throws DAOException when problems with database access occur.
	 */
	List<StudentOnCourse> findStudentsOnRunCourse(int runCourseId) throws DAOException;

	/**
	 * Method connects to database and finds courses of current department.
	 * @param departmentId id of department
	 * @return List of {@link Course} of courses of this department
	 * @throws DAOException when problems with database access occur.
	 */
	List<Course> findCoursesFromDepartment(int departmentId) throws DAOException;

	/**
	 * Method connects to database ad finds all available run courses of the university
	 * @return List of {@link RunnedCourse} runned course of the university
	 * @throws DAOException when problems with database access occur.
	 */
	List<RunnedCourse> findAllAvailableRunCourses() throws DAOException;

	/**
	 * Connects to database and finds runned courses of a certain course.
	 * @param courseId id of the course
	 * @return List of {@link RunnedCourse} runned courses of the course
	 * @throws DAOException when problems with database access occur.
	 */
	List<RunnedCourse> findRunnedCoursesByCourseId(int courseId) throws DAOException;

	/**
	 * Connects to database and finds info about runned course.
	 * @param runCourseId id of the run course
	 * @return Object {@link RunnedCourse} which contains detailed info about course.
	 * @throws DAOException when problems with database access occur.
	 */
	RunnedCourse findRunCourse(int runCourseId) throws DAOException;

	/**
	 * Changes user status to "expelled".
	 * @param userId id of the expelled student
	 * @throws DAOException when problems with database access occur.
	 */
	void expellStudent(int userId) throws DAOException;

	/**
	 * Method that connects to database and finds run courses of dean.
	 * 
	 * @param userPageInfo dean page info
	 * @throws DAOException when problems with database access occur.
	 */
	void findRunCoursesOfDean(UserInfo userPageInfo) throws DAOException;

}
