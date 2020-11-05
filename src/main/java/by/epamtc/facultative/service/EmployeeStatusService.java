package by.epamtc.facultative.service;

import by.epamtc.facultative.service.exception.ServiceException;

public interface EmployeeStatusService {

	void approveEmployee(int employeeId) throws ServiceException;

	void disapproveEmployee(int employeeId) throws ServiceException;

}
