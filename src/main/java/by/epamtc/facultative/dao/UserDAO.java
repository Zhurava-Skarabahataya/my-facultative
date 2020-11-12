package by.epamtc.facultative.dao;

import java.util.List;

import by.epamtc.facultative.bean.DepartmentStaff;
import by.epamtc.facultative.bean.Mark;
import by.epamtc.facultative.bean.UserInfo;
import by.epamtc.facultative.dao.exception.DAOException;

/**
 * Interface sends SQL requests concerning info about user: personal info, students'
 * marks, user's courses to the database.
 *
 */
public interface UserDAO {

	/**
	 * Finds info about staff of the department.
	 * 
	 * @param departmentId id of current department
	 * @return object of DepartmentStaff class, which contains three collections:
	 *         with working lecturers, not approved lecturers and fired lecturers.
	 * @throws DAOException when problems with database access occur. 
	 */
	DepartmentStaff findFacultyStaffInfo(int departmentId) throws DAOException;

	/**
	 * Finds info about staff of all departments.
	 * 
	 * @return object of DepartmentStaff class, which contains collection of all
	 *         lecturers.
	 * @throws DAOException when problems with database access occur. 
	 */
	DepartmentStaff findAllFacultiesStaffInfo() throws DAOException;

	/**
	 * Method finds all marks of student with this id and returns them as
	 * collection.
	 * 
	 * @param studentId Student's user id
	 * @return ArrayList <Marks> of marks
	 * @throws DAOException when problems with database access occur. 
	 */
	List<Mark> findStudentResults(int studentId) throws DAOException;

	/**
	 * Method finds all marks of student with current login and returns them as
	 * collection.
	 * 
	 * @param studentLogin student's user login
	 * @return ArrayList <Marks> of marks
	 * @throws DAOException when problems with database access occur. 
	 */
	List<Mark> findStudentResults(String studentLogin) throws DAOException;

	/**
	 * Finds info about user: name, role, email, faculty, adress and phone.
	 * 
	 * @param userPageInfo Object of UserInfo, containing info about user login
	 * @throws DAOException when problems with database access occur. 
	 */
	void provideUserInfo(UserInfo userPageInfo) throws DAOException;
	
	/**
	 * Finds info about user: name, role, email, faculty, adress and phone.
	 * 
	 * @param userPageInfo Object of UserInfo, containing info about user id
	 * @throws DAOException when problems with database access occur. 
	 */
	void provideUserInfoById(UserInfo userPageInfo) throws DAOException;

	/**
	 * Updates user info in database.
	 * 
	 * @param userPageInfo Object of UserInfo class, contains changed information
	 * @throws DAOException when problems with database access occur. 
	 */
	void updateUserInfo(UserInfo userPageInfo) throws DAOException;

	/**
	 * Changes status of the employee.
	 * 
	 * @param employeeId user id of the employee
	 * @param status     id of the working status
	 * @throws DAOException when problems with database access occur. 
	 */
	void changeEmployeeStatus(int employeeId, int status) throws DAOException;


}
