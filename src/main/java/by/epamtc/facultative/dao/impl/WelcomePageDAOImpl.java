package by.epamtc.facultative.dao.impl;

import org.apache.log4j.Logger;

import by.epamtc.facultative.dao.WelcomePageDAO;
import by.epamtc.facultative.dao.exception.DAOException;
import by.epamtc.facultative.dao.impl.pool.ConnectionPool;
import by.epamtc.facultative.dao.impl.pool.exception.ConnectionPoolException;

public class WelcomePageDAOImpl implements WelcomePageDAO{
	
	private static final Logger logger = Logger.getLogger(WelcomePageDAOImpl.class);
	
	private static final WelcomePageDAOImpl instance = new WelcomePageDAOImpl();
	
	
	
	private final Object LOGGER_MESSAGE = "";

	private WelcomePageDAOImpl() {
		
	}
	
	public static WelcomePageDAOImpl getInstance() {
		return instance;
	}
	
	
	@Override
	public void getDataFromDatabase() throws DAOException {

		ConnectionPool pool = ConnectionPool.getInstance();
		try {
			pool.initPool();
			
		} catch (ConnectionPoolException e) {
			
			throw new DAOException();
		}
		
	}

	
	
}
