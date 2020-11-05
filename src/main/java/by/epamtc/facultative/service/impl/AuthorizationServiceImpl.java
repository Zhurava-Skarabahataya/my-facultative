package by.epamtc.facultative.service.impl;

import by.epamtc.facultative.dao.AuthorizationDAO;
import by.epamtc.facultative.dao.DAOFactory;
import by.epamtc.facultative.dao.exception.DAOException;
import by.epamtc.facultative.dao.impl.AuthorizationDAOImpl;
import by.epamtc.facultative.service.AuthorizationService;
import by.epamtc.facultative.service.exception.ServiceException;
import by.epamtc.facultative.bean.UserAuthorizationInfo;

public class AuthorizationServiceImpl implements AuthorizationService{

	private static final AuthorizationServiceImpl instance = new AuthorizationServiceImpl();

	private AuthorizationServiceImpl() {

	}

	public static AuthorizationServiceImpl getInstance() {
		return instance;
	}
	
	@Override
	public boolean execute(UserAuthorizationInfo info) throws ServiceException {
		
		DAOFactory daoFactory = DAOFactory.getInstance();
		AuthorizationDAO authorizationDAO = daoFactory.getAuthorizationDAO();
		
		try {
			return authorizationDAO.authorizeUser(info);
		} catch (DAOException e) {
			
			throw new ServiceException(e);

		}
	}
	
}
