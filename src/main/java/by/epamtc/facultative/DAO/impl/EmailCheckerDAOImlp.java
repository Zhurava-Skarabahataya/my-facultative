package by.epamtc.facultative.DAO.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import by.epamtc.facultative.DAO.InfoCheckerDAO;
import by.epamtc.facultative.DAO.impl.pool.ConnectionPool;
import javafx.css.PseudoClass;

public class EmailCheckerDAOImlp implements InfoCheckerDAO {

	private static final EmailCheckerDAOImlp instance = new EmailCheckerDAOImlp();
	
	private static final String QUERY_FIND_EMAIL_IN_DATABASE = "SELECT * FROM users WHERE user_email = ?";

	private EmailCheckerDAOImlp() {

	}

	public static EmailCheckerDAOImlp getInstance() {
		return instance;
	}

	@Override
	public boolean checkInfoIfExists(String email) {

		String emailToCkeck;
		emailToCkeck = email;

		// ПОЗЖЕ ПРОВЕРЬ, СООТВЕТСТВУЕТ ЛИ НОРМАМ НАПИСНИЯ

		ConnectionPool cp = ConnectionPool.getInstance();
		Connection conn = cp.getFreeConnection();

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
