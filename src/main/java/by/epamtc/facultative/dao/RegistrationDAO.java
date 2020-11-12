package by.epamtc.facultative.dao;

import by.epamtc.facultative.dao.exception.DAOException;
import by.epamtc.facultative.bean.UserRegistrationInfo;

/**
 * Interface provides methods for user registration. Methods should be
 * implemented by classes who execute connection to database, checking accuracy
 * of entered data and allowing to register.
 */
public interface RegistrationDAO {

	/**
	 * Method registrates user. Provides connection to database,
	 * checks existance of entered data and registering user.
	 * 
	 * @param user is Object of {@link UserRegistrationInfo}, which contains
	 *             information about user's entered data
	 * @throws DAOException when problems with database access occur. 
	 */
	void registrateUser(UserRegistrationInfo userRegistrationInfo) throws DAOException;

	/**
	 * Method checks if value of entered email already exists in database.
	 * @param email Object of {@link String} which is value of entered email by user
	 * @return true if email is already in database, false if it is vacant
	 * @throws DAOException when problems with database access occur. 
	 */
	boolean checkEmailIfExists(String email) throws DAOException;

	/**
	 * Method checks if value of entered login already exists in database.
	 * @param login Object of {@link String} which is value of entered login by user
	 * @return true if login is already in database, false if it is vacant
	 * @throws DAOException when problems with database access occur. 
	 */
	boolean checkLoginIfExists(String login) throws DAOException;

}
