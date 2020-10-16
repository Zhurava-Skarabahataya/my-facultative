package by.epamtc.facultative.service;

import by.epamtc.facultative.dao.exception.DAOException;
import by.epamtc.facultative.dao.impl.AuthorizationDAOImpl;
import by.epamtc.facultative.bean.UserAuthorizationInfo;

public class AuthorizationUserService {

	private static final AuthorizationUserService instance = new AuthorizationUserService();

	private AuthorizationUserService() {

	}

	public static AuthorizationUserService getInstance() {
		return instance;
	}
	
	public boolean execute(UserAuthorizationInfo info) {
		
		AuthorizationDAOImpl authorizationDAO = AuthorizationDAOImpl.getInstance();
		
		try {
			return authorizationDAO.authorizeUser(info);
		} catch (DAOException e) {
			// ОБРАБОООТАЙ
			
			e.printStackTrace();
		}
		return false;
		
	}
	
}
