package by.epamtc.facultative.service;

import by.epamtc.facultative.dao.exception.DAOException;
import by.epamtc.facultative.dao.impl.UserDAOImpl;
import by.epamtc.facultative.service.exception.ServiceException;

public class EmployeeStatusService {

	private static final EmployeeStatusService instance = new EmployeeStatusService();

	private final int APPROVED_STATUS = 2;
	private final int DISAPPROVED_STATUS = 4;

	private EmployeeStatusService() {

	}

	public static EmployeeStatusService getInstance() {
		return instance;
	}

	public void approveEmployee(int employeeId) throws ServiceException {

		UserDAOImpl userDAO = UserDAOImpl.getInstance();

		try {
			userDAO.changeEmployeeStatus(employeeId, APPROVED_STATUS);
		} catch (DAOException e) {
			// значит, не удалось
			e.printStackTrace();
			throw new ServiceException(e);
		}

	}

	public void disapproveEmployee(int employeeId) throws ServiceException {
		UserDAOImpl userDAO = UserDAOImpl.getInstance();

		try {
			userDAO.changeEmployeeStatus(employeeId, DISAPPROVED_STATUS);
		} catch (DAOException e) {
			// значит, не удалось
			e.printStackTrace();
			throw new ServiceException(e);

		}

	}

}
