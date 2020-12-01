package by.epamtc.facultative.service.impl;

import by.epamtc.facultative.dao.DAOFactory;
import by.epamtc.facultative.dao.UserDAO;
import by.epamtc.facultative.dao.exception.DAOException;
import by.epamtc.facultative.service.EmployeeStatusService;
import by.epamtc.facultative.service.exception.ServiceException;

public class EmployeeStatusServiceImpl implements EmployeeStatusService {

	private static final EmployeeStatusServiceImpl instance = new EmployeeStatusServiceImpl();

	private final int APPROVED_STATUS = 2;
	private final int DISAPPROVED_STATUS = 4;
	private final int FIRED_STATUS = 5;

	private EmployeeStatusServiceImpl() {

	}

	public static EmployeeStatusServiceImpl getInstance() {
		return instance;
	}

	@Override
	public void approveEmployee(int employeeId) throws ServiceException {

		DAOFactory daoFactory = DAOFactory.getInstance();
		UserDAO userDAO = daoFactory.getUserDAO();

		try {
			userDAO.changeEmployeeStatus(employeeId, APPROVED_STATUS);

		} catch (DAOException e) {
			throw new ServiceException(e);
		}

	}

	@Override
	public void disapproveEmployee(int employeeId) throws ServiceException {

		DAOFactory daoFactory = DAOFactory.getInstance();
		UserDAO userDAO = daoFactory.getUserDAO();

		try {
			userDAO.changeEmployeeStatus(employeeId, DISAPPROVED_STATUS);

		} catch (DAOException e) {
			throw new ServiceException(e);

		}

	}

	@Override
	public void fireEmployee(int employeeId) throws ServiceException {
		DAOFactory daoFactory = DAOFactory.getInstance();
		UserDAO userDAO = daoFactory.getUserDAO();

		try {
			userDAO.changeEmployeeStatus(employeeId, FIRED_STATUS);

		} catch (DAOException e) {
			throw new ServiceException(e);

		}
		
	}
	
	

}
