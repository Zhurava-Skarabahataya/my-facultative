package by.epamtc.facultative.DAO.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import by.epamtc.facultative.DAO.DatabaseDAO;
import by.epamtc.facultative.DAO.impl.pool.ConnectionPool;

public class DatabaseDAOImpl implements DatabaseDAO{

	@Override
	public String getDataFromDatabase() {
		
		ConnectionPool cp = ConnectionPool.getInstance();
		cp.initPool();
		System.out.println("Pool inited in datbase DAO");
		Connection conn = cp.getFreeConnection();
		
		PreparedStatement ps = null;
		
		return "hey";
	}

}
