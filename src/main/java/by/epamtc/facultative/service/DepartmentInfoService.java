package by.epamtc.facultative.service;

import java.util.List;

import by.epamtc.facultative.bean.Department;
import by.epamtc.facultative.bean.UserInfo;
import by.epamtc.facultative.service.exception.ServiceException;

public interface DepartmentInfoService {

	List<Department> findAllDepartmentsInfo() throws ServiceException;

	List<UserInfo> findStudentsOfDepartment(int departmentId) throws ServiceException;

	List<Department> findStudentsOfAllDepartments() throws ServiceException;

	void findLecturersAndCoursesForDepartment(Department department) throws ServiceException;
}
