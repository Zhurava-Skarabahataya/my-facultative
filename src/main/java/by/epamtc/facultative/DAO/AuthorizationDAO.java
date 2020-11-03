package by.epamtc.facultative.dao;

import by.epamtc.facultative.bean.UserAuthorizationInfo;
import by.epamtc.facultative.dao.exception.DAOException;

public interface AuthorizationDAO {

	public boolean authorizeUser(UserAuthorizationInfo info) throws DAOException;

}
