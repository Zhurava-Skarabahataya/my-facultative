package by.epamtc.facultative.dao;

import by.epamtc.facultative.bean.UserAuthorizationInfo;
import by.epamtc.facultative.dao.exception.DAOException;

/**
 * Interface provides methods for user authorization. Methods should be
 * implemented by classes who execute connection to database, checking accuracy
 * of entered data and allowing to sigh in.
 */
public interface AuthorizationDAO {

	/**
	 * Method that returns if user can sign in. Provides connection to database, checks existance of
	 * entered data and returning if user can sign in.
	 * 
	 * @param user is Object of {@link UserAuthorizationInfo}, which contains information about user's login and password
	 * @return true if user's data exist in database, false if user's login and password are not correct
	 * @throws DAOException when problems with database access occur. 
	 */
	boolean authorizeUser(UserAuthorizationInfo user) throws DAOException;

}
