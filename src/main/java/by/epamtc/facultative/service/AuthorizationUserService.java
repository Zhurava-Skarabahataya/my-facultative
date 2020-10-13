package by.epamtc.facultative.service;

import by.epamtc.facultative.DAO.exception.DAOException;
import by.epamtc.facultative.DAO.impl.AuthorizationDAOImpl;
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
		
	}
	
}
