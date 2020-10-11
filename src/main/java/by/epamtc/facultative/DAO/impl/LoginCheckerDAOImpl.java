package by.epamtc.facultative.DAO.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import by.epamtc.facultative.DAO.InfoCheckerDAO;
import by.epamtc.facultative.DAO.impl.pool.ConnectionPool;

public class LoginCheckerDAOImpl implements InfoCheckerDAO {

	private static final LoginCheckerDAOImpl instance = new LoginCheckerDAOImpl();

	private static final String QUERY_FIND_USER_BY_LOGIN = "SELECT * FROM users WHERE user_login = ?";

	private LoginCheckerDAOImpl() {

	}

	public static LoginCheckerDAOImpl getInstance() {
		return instance;
	}

	@Override
	public boolean checkInfoIfExists(String info) {
		String loginToCheck;
		loginToCheck = info;

		// ПОЗЖЕ ПРОВЕРЬ, СООТВЕТСТВУЕТ ЛИ НОРМАМ НАПИСНИЯ

		ConnectionPool cp = ConnectionPool.getInstance();
		Connection conn = cp.getFreeConnection();
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
