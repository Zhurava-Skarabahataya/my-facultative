package by.epamtc.facultative.dao.impl;

import by.epamtc.facultative.dao.impl.pool.ConnectionPool;
import by.epamtc.facultative.dao.impl.pool.ConnectionPoolException;

public class WelcomePageInfoDAOImpl {

	public void getDataFromDatabase() {

		ConnectionPool pool = ConnectionPool.getInstance();
		try {
			pool.initPool();
		} catch (ConnectionPoolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	
	
}
