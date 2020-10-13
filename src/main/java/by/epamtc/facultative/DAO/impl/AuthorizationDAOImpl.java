package by.epamtc.facultative.DAO.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import by.epamtc.facultative.DAO.exception.DAOException;
import by.epamtc.facultative.DAO.impl.pool.ConnectionPool;
import by.epamtc.facultative.DAO.impl.pool.ConnectionPoolException;
import by.epamtc.facultative.bean.UserAuthorizationInfo;

public class AuthorizationDAOImpl {

	private static final AuthorizationDAOImpl instance = new AuthorizationDAOImpl();

	private static final Logger logger = Logger.getLogger(AuthorizationDAOImpl.class);

	private static final String QUERY_FIND_USER_IN_DATABASE = "SELECT * FROM users WHERE user_login = ? AND user_password = ?";

	private AuthorizationDAOImpl() {

	}

	public static AuthorizationDAOImpl getInstance() {
		return instance;
	}

	public boolean authorizeUser(UserAuthorizationInfo info) throws DAOException {

		String userLogin = info.getLogin();
		String userPassword = info.getPassword();

		ConnectionPool cp = ConnectionPool.getInstance();
		Connection conn = null;

		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			conn = cp.getFreeConnection();

			ps = conn.prepareStatement(QUERY_FIND_USER_IN_DATABASE);

			ps.setString(1, userLogin);
			ps.setString(2, userPassword);

			rs = ps.executeQuery();

			if (rs.next()) {
				return true;
			}
			
		} catch (ConnectionPoolException e) {

			String message = "Не удалось получить соединение.";
			logger.error(message);
			throw new DAOException(message, e);
		}

		catch (SQLException e) {

			String message = "Проблема с выполнением запроса в базу данных.";
			logger.error(message, e);
			throw new DAOException(message, e);

		} finally {

			try {
				cp.closeConnection(rs, ps, conn);
				
			} catch (ConnectionPoolException e) {

				String message = "Не удалось закрыть соединение.";
				logger.error(message);
				throw new DAOException(message, e);
			}
		}

		return false;

	}

}
