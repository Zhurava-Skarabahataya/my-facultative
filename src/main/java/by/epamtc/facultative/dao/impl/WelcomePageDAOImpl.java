package by.epamtc.facultative.dao.impl;

import org.apache.log4j.Logger;

import by.epamtc.facultative.dao.WelcomePageDAO;
import by.epamtc.facultative.dao.exception.DAOException;
import by.epamtc.facultative.dao.impl.pool.ConnectionPool;
import by.epamtc.facultative.dao.impl.pool.exception.ConnectionPoolException;

/**
 * Implementation of {@link WelcomePageDAO}. Uses {@link ConnectionPool} gor
 * connecting to database and getting information for welcome page.
 *
 */
public class WelcomePageDAOImpl implements WelcomePageDAO {

	/** Logger of the class */
	private static final Logger logger = Logger.getLogger(WelcomePageDAOImpl.class);

	/** A single instance of the class (pattern Singleton) */
	private static final WelcomePageDAOImpl instance = new WelcomePageDAOImpl();

	/** Private constructor for singleton */
	private WelcomePageDAOImpl() {
	}

	/**
	 * Method returns single instance of the class
	 * 
	 * @return single instance of the class
	 */
	public static WelcomePageDAOImpl getInstance() {
		return instance;
	}

	/**
	 * Method inits {@link ConnectionPool} pool. Further realization will appear in
	 * later versions.
	 * 
	 * @throws DAOException when connection to database is unavailable.
	 */
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
