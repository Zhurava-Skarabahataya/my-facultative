package by.epamtc.facultative.DAO.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


import by.epamtc.facultative.DAO.impl.pool.ConnectionPool;
import by.epamtc.facultative.bean.UserAuthorizationInfo;

public class AuthorizationDAOImpl {

	private static final AuthorizationDAOImpl instance = new AuthorizationDAOImpl();
	
	private static final String QUERY_FIND_USER_IN_DATABASE = "SELECT * FROM users WHERE user_login = ? AND user_password = ?";

	private AuthorizationDAOImpl() {

	}

	public static AuthorizationDAOImpl getInstance() {
		return instance;
	}

	public boolean authorizeUser(UserAuthorizationInfo info) {

		String userLogin = info.getLogin();
		String userPassword = info.getPassword();

		ConnectionPool cp = ConnectionPool.getInstance();
		Connection conn = cp.getFreeConnection();

		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = conn.prepareStatement(QUERY_FIND_USER_IN_DATABASE);
			
			ps.setString(1, userLogin);
			ps.setString(2, userPassword);

			rs = ps.executeQuery();

			if (rs.next()) {
				return true;
			}

		} catch (SQLException e) {
			e.printStackTrace();

		} finally {
			
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
			if (conn != null) {
				cp.releaseConnection(conn);
			}
		}

		return false;

	}

}
