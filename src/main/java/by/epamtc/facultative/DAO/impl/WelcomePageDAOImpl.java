package by.epamtc.facultative.dao.impl;

import org.apache.log4j.Logger;

import by.epamtc.facultative.dao.exception.DAOException;
import by.epamtc.facultative.dao.impl.pool.ConnectionPool;
import by.epamtc.facultative.dao.impl.pool.exception.ConnectionPoolException;

public class WelcomePageDAOImpl {
	
	private static final Logger logger = Logger.getLogger(WelcomePageDAOImpl.class);
	
	private final Object LOGGER_MESSAGE = "";

	public void getDataFromDatabase() throws DAOException {

		ConnectionPool pool = ConnectionPool.getInstance();
		try {
			pool.initPool();
		} catch (ConnectionPoolException e) {
			
			logger.error(LOGGER_MESSAGE, e);

			throw new DAOException();
		}
		
	}

	
	
}
