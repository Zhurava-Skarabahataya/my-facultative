package by.epamtc.facultative.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import by.epamtc.facultative.dao.exception.DAOException;
import by.epamtc.facultative.dao.impl.pool.ConnectionPool;
import by.epamtc.facultative.dao.impl.pool.ConnectionPoolException;
import by.epamtc.facultative.dao.InfoCheckerDAO;

public class EmailCheckerDAOImlp implements InfoCheckerDAO {

	private static final EmailCheckerDAOImlp instance = new EmailCheckerDAOImlp();
	
	private static final Logger logger = Logger.getLogger(EmailCheckerDAOImlp.class);
	
	private static final String QUERY_FIND_EMAIL_IN_DATABASE = "SELECT * FROM users WHERE user_email = ?";

	private EmailCheckerDAOImlp() {

	}

	public static EmailCheckerDAOImlp getInstance() {
		return instance;
	}

	@Override
	public boolean checkInfoIfExists(String email) throws DAOException {

		String emailToCkeck;
		emailToCkeck = email;

		ConnectionPool cp = ConnectionPool.getInstance();
		Connection conn = null;
		
		try {
			conn = cp.getFreeConnection();
			
		} catch (ConnectionPoolException e) {

			throw new DAOException(e);
		}

		ResultSet rs = null;
		PreparedStatement ps = null;

		try {
			ps = conn.prepareStatement(QUERY_FIND_EMAIL_IN_DATABASE);

			ps.setString(1, emailToCkeck);
			rs = ps.executeQuery();
			
			if (rs.next()) {
				return true;
			}

		} catch (SQLException e) {
			
			String message = "Проблема с выполнением запроса в базу данных.";
			logger.error(message, e);
			throw new DAOException(message , e);
			
		} finally {
			
			try {
				cp.closeConnection(rs, ps, conn);
			} catch (ConnectionPoolException e) {

				throw new DAOException(e);
			}

		}
		return false;

	}

}
