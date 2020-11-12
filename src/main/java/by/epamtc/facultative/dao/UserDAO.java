package by.epamtc.facultative.dao;

import java.util.List;

import by.epamtc.facultative.bean.DepartmentStaff;
import by.epamtc.facultative.bean.Mark;
import by.epamtc.facultative.bean.UserInfo;
import by.epamtc.facultative.dao.exception.DAOException;

public interface UserDAO {

	DepartmentStaff findFacultyStaffInfo(int departmentId) throws DAOException;

	DepartmentStaff findAllFacultiesStaffInfo() throws DAOException;

	List<Mark> findStudentResults(int studentId) throws DAOException;

	List<Mark> findStudentResults(String studentLogin) throws DAOException;

	void provideUserInfo(UserInfo userPageInfo) throws DAOException;
	
	void provideUserInfoById(UserInfo userPageInfo) throws DAOException;

	void updateUserInfo(UserInfo userPageInfo) throws DAOException;

	void changeEmployeeStatus(int employeeId, int status) throws DAOException;


}
