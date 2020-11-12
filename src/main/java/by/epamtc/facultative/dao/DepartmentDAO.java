package by.epamtc.facultative.dao;

import java.util.List;

import by.epamtc.facultative.bean.Course;
import by.epamtc.facultative.bean.Department;
import by.epamtc.facultative.bean.UserInfo;
import by.epamtc.facultative.dao.exception.DAOException;

/**
 * Interface provides methods for manipulation information concerning
 * departments. Methods should be implemented by classes who execute connection
 * to database, finding and editing information about departments.
 */
public interface DepartmentDAO {

	/**
	 * Method finds detailed information about all departments of the university.
	 * 
	 * @return List of {@link Department} object which contain information about
	 *         departments.
	 * @throws DAOException when problems with database access occur.
	 */
	List<Department> findAllDepartments() throws DAOException;

	/**
	 * Method finds detailed information about courses of department.
	 * 
	 * @param departmentId id of the department needed
	 * @return List of {@link Course} object which contain detailed information
	 *         about courses of the department.
	 * @throws DAOException when problems with database access occur.
	 */
	List<Course> findCoursesInDepartment(int departmentId) throws DAOException;

	/**
	 * Method finds detailed information abut lecturers of the department.
	 * 
	 * @param departmentId id of the department needed
	 * @return List of {@link UserInfo} objects which contain detailed information
	 *         about lecturers of the department.
	 * @throws DAOException when problems with database access occur.
	 */
	List<UserInfo> findLecturersInDepartment(int departmentId) throws DAOException;

	/**
	 * Method finds detailed information abut students of the department.
	 * 
	 * @param departmentId id of the department needed
	 * @return List of {@link UserInfo} objects which contain detailed information
	 *         about students of the department.
	 * @throws DAOException when problems with database access occur.
	 */
	List<UserInfo> findStudentsInDepartment(int departmentId) throws DAOException;

	/**
	 * Method finds detailed information abut students of the university.
	 * 
	 * @return List of {@link UserInfo} objects which contain detailed information
	 *         about students of the university.
	 * @throws DAOException when problems with database access occur.
	 */
	List<UserInfo> findStudentsInAllDepartments() throws DAOException;

}
