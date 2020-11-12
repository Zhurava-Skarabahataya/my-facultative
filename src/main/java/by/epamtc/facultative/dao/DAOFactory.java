package by.epamtc.facultative.dao;

import by.epamtc.facultative.dao.impl.AuthorizationDAOImpl;
import by.epamtc.facultative.dao.impl.CourseDAOImpl;
import by.epamtc.facultative.dao.impl.DepartmentDAOImpl;
import by.epamtc.facultative.dao.impl.RegistrationDAOImpl;
import by.epamtc.facultative.dao.impl.UserDAOImpl;
import by.epamtc.facultative.dao.impl.WelcomePageDAOImpl;

/**
 * Factory class that provides different implementation of DAO intefaces.
 */
public class DAOFactory {

	/** A single instance of the class (pattern Singleton) */
	private static final DAOFactory instance = new DAOFactory();

	/** An object of {@link CourseDAOImpl} */
	private CourseDAO courseDAO = CourseDAOImpl.getInstance();
	/** An object of {@link RegistrationDAOImpl} */
	private RegistrationDAO registrationDAO = RegistrationDAOImpl.getInstance();
	/** An object of {@link AuthorizationDAOImpl} */
	private AuthorizationDAO authorizationDAO = AuthorizationDAOImpl.getInstance();
	/** An object of {@link UserDAOImpl} */
	private UserDAO userDAO = UserDAOImpl.getInstance();
	/** An object of {@link DepartmentDAOImpl} */
	private DepartmentDAO departmentDAO = DepartmentDAOImpl.getInstance();
	/** An object of {@link WelcomePageDAOImpl} */
	private WelcomePageDAO welcomePageDAO = WelcomePageDAOImpl.getInstance();

	/** private constructor without parameters */
	private DAOFactory() {

	}

	/**
	 * Returns singleton object of the class
	 * 
	 * @return Object of {@link DAOFactory}
	 */
	public static DAOFactory getInstance() {
		return instance;
	}

	/**
	 * Method returns field of {@link CourseDAO} object
	 * @return {@link CourseDAO} object
	 */
	public CourseDAO getCourseDAO() {
		return courseDAO;
	}

	/**
	 * Method returns field of {@link RegistrationDAO} object
	 * @return {@link RegistrationDAO} object
	 */
	public RegistrationDAO getRegistrationDAO() {
		return registrationDAO;
	}

	/**
	 * Method returns field of {@link AuthorizationDAO} object
	 * @return {@link AuthorizationDAO} object
	 */
	public AuthorizationDAO getAuthorizationDAO() {
		return authorizationDAO;
	}

	/**
	 * Method returns field of {@link UserDAO} object
	 * @return {@link UserDAO} object
	 */
	public UserDAO getUserDAO() {
		return userDAO;
	}

	/**
	 * Method returns field of {@link DepartmentDAO} object
	 * @return {@link DepartmentDAO} object
	 */
	public DepartmentDAO getDepartmentDAO() {
		return departmentDAO;
	}

	/**
	 * Method returns field of {@link WelcomePageDAO} object
	 * @return {@link WelcomePageDAO} object
	 */
	public WelcomePageDAO getWelcomePageDAO() {
		return welcomePageDAO;
	}

}
