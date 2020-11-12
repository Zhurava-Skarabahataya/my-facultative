package by.epamtc.facultative.dao;

import java.util.List;

import by.epamtc.facultative.bean.Course;
import by.epamtc.facultative.bean.Department;
import by.epamtc.facultative.bean.UserInfo;
import by.epamtc.facultative.dao.exception.DAOException;

public interface DepartmentDAO {

	List<Department> findAllDepartments() throws DAOException;

	List<Course> findCoursesInDepartment(int departmentId) throws DAOException;

	List<UserInfo> findLecturersInDepartment(int departmentId) throws DAOException;

	List<UserInfo> findStudentsInDepartment(int departmentId) throws DAOException;

	List<UserInfo> findStudentsInAllDepartments() throws DAOException;

}
