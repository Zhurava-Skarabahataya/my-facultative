package by.epamtc.facultative.dao;

import by.epamtc.facultative.dao.exception.DAOException;
import by.epamtc.facultative.dao.impl.pool.ConnectionPool;

/**
 * Interface sends SQL requests concerning info for welcom page.
 *
 */
public interface WelcomePageDAO {
	
	/**
	 * Method inits {@link ConnectionPool} pool. Further realization will appear in
	 * later versions.
	 * 
	 * @throws DAOException when connection to database is unavailable.
	 */
	void getDataFromDatabase() throws DAOException;

}
