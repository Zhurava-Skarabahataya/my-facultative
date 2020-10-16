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

public class LoginCheckerDAOImpl implements InfoCheckerDAO {

	private static final LoginCheckerDAOImpl instance = new LoginCheckerDAOImpl();

	private static final Logger logger = Logger.getLogger(LoginCheckerDAOImpl.class);

	private static final String QUERY_FIND_USER_BY_LOGIN = "SELECT * FROM users WHERE user_login = ?";

	private LoginCheckerDAOImpl() {

	}

	public static LoginCheckerDAOImpl getInstance() {
		return instance;
	}

	@Override
	public boolean checkInfoIfExists(String info) throws DAOException {

		String loginToCheck;
		loginToCheck = info;

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
			ps = conn.prepareStatement(QUERY_FIND_USER_BY_LOGIN);

			ps.setString(1, loginToCheck);
			rs = ps.executeQuery();

			if (rs.next()) {
				return true;
			}

		} catch (SQLException e) {

			String message = "Проблема с выполнением запроса в базу данных.";
			logger.error(message, e);
			throw new DAOException(message, e);

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
