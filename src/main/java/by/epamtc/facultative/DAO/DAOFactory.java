package by.epamtc.facultative.dao;

import by.epamtc.facultative.dao.impl.AuthorizationDAOImpl;
import by.epamtc.facultative.dao.impl.CourseDAOImpl;
import by.epamtc.facultative.dao.impl.RegistrationDAOImpl;
import by.epamtc.facultative.dao.impl.UserDAOImpl;

public class DAOFactory {
	
	private static final DAOFactory instance = new DAOFactory();
	
	private CourseDAO courseDAO = CourseDAOImpl.getInstance();
	private RegistrationDAO registrationDAO = RegistrationDAOImpl.getInstance();
	private AuthorizationDAO authorizationDAO = AuthorizationDAOImpl.getInstance(); 
	private UserDAO userDAO = UserDAOImpl.getInstance();
	
	
	private DAOFactory() {
		
	}
	
	public static DAOFactory getInstance() {
		return instance;
	}

	public CourseDAO getCourseDAO() {
		return courseDAO;
	}

	public RegistrationDAO getRegistrationDAO() {
		return registrationDAO;
	}

	public AuthorizationDAO getAuthorizationDAO() {
		return authorizationDAO;
	}

	public UserDAO getUserDAO() {
		return userDAO;
	}

	
	
	

}
