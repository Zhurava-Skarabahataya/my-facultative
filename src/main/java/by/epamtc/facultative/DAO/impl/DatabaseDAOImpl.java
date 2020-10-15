package by.epamtc.facultative.DAO.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;

import org.apache.log4j.Logger;

import by.epamtc.facultative.DAO.DatabaseDAO;
import by.epamtc.facultative.DAO.impl.pool.ConnectionPool;
import by.epamtc.facultative.DAO.impl.pool.ConnectionPoolException;

public class DatabaseDAOImpl implements DatabaseDAO{
	
	private static final Logger logger = Logger.getLogger(DatabaseDAOImpl.class);


	@Override
	public String getDataFromDatabase() {
		
		ConnectionPool cp = ConnectionPool.getInstance();
		try {
			cp.initPool();
		} catch (ConnectionPoolException e) {
			e.printStackTrace();
		}
//		try {
//			//Connection conn = cp.getFreeConnection();
//		} catch (ConnectionPoolException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		PreparedStatement ps = null;
		
		return "hey";
	}

}
