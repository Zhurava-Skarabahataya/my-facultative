package by.epamtc.facultative.DAO.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import by.epamtc.facultative.DAO.RegistrationDAO;
import by.epamtc.facultative.DAO.exception.DAOException;
import by.epamtc.facultative.DAO.impl.pool.ConnectionPool;
import by.epamtc.facultative.DAO.impl.pool.ConnectionPoolException;
import by.epamtc.facultative.bean.UserRegistrationInfo;

public class RegistrationDAOImpl implements RegistrationDAO {

	private static final RegistrationDAOImpl instance = new RegistrationDAOImpl();

	private static final Logger logger = Logger.getLogger(RegistrationDAOImpl.class);

	private static final String QUERY_INSERT_NEW_USER_INTO_USERS = "INSERT into USERS(user_login, user_password, user_email,"
			+ "first_name, second_name, patronymic, user_role_id, department_department_id"
			+ ") VALUES (?, ?,?,?,?,?,?,?)";

	private static final String QUERY_FIND_USER_ID_BY_LOGIN = "SELECT user_id FROM users WHERE user_login = ?";

	private static final String QUERY_INSERT_NEW_USER_IN_USER_DETAILS = "INSERT INTO user_details (users_user_id) VALUES (?)";

	private RegistrationDAOImpl() {

	}

	public static RegistrationDAOImpl getInstance() {
		return instance;
	}

	@Override
	public void registrateUser(UserRegistrationInfo userRegistrationInfo) throws DAOException {

		ConnectionPool cp = ConnectionPool.getInstance();
		Connection conn = null;
		try {
			conn = cp.getFreeConnection();
			
		} catch (ConnectionPoolException e) {
			throw new DAOException(e);
		}

		PreparedStatement preparedStatementFirst = null;
		PreparedStatement preparedStatementSecond = null;
		PreparedStatement preparedStatementThird = null;

		ResultSet rs = null;

		String userLogin = userRegistrationInfo.getUserLogin();
		String userPassword = userRegistrationInfo.getUserPassword();
		String userEmail = userRegistrationInfo.getUserEmail();
		String userFirstName = userRegistrationInfo.getFirstName();
		String userSecondName = userRegistrationInfo.getSecondName();
		String userPatronymic = userRegistrationInfo.getPatromynic();
		Integer userRole = userRegistrationInfo.getUserRoleID();
		Integer departmentID = userRegistrationInfo.getDepartmentID();

		int userID = 0;

		try {
			conn.setAutoCommit(false);

			preparedStatementFirst = conn.prepareStatement(QUERY_INSERT_NEW_USER_INTO_USERS);

			preparedStatementFirst.setString(1, userLogin);
			preparedStatementFirst.setString(2, userPassword);
			preparedStatementFirst.setString(3, userEmail);
			preparedStatementFirst.setString(4, userFirstName);
			preparedStatementFirst.setString(5, userSecondName);
			preparedStatementFirst.setString(6, userPatronymic);
			preparedStatementFirst.setInt(7, userRole);
			preparedStatementFirst.setInt(8, departmentID);

			preparedStatementFirst.executeUpdate();

			preparedStatementSecond = conn.prepareStatement(QUERY_FIND_USER_ID_BY_LOGIN);
			preparedStatementSecond.setString(1, userLogin);

			rs = preparedStatementSecond.executeQuery();

			if (rs.first()) {
				userID = rs.getInt(1);
			}

			preparedStatementThird = conn.prepareStatement(QUERY_INSERT_NEW_USER_IN_USER_DETAILS);

			preparedStatementThird.setInt(1, userID);

			preparedStatementThird.executeUpdate();

			conn.commit();
			conn.setAutoCommit(true);

		} catch (SQLException e) {
			
			String message = "Проблема с выполнением запроса в базу данных.";
			logger.error(message, e);
			throw new DAOException(message , e);

		} finally {

			if (rs != null) {
				
				try {
					rs.close();
				} catch (SQLException e) {
					
					String message = "Проблема с закрытием результатов запроса в базу данных.";
					logger.error(message, e);
					throw new DAOException(message , e);
				}
			}

			try {
				if (preparedStatementFirst != null) {
					preparedStatementFirst.close();
				}

				if (preparedStatementSecond != null) {
					preparedStatementSecond.close();
				}

				if (preparedStatementThird != null) {
					preparedStatementThird.close();
				}

			} catch (SQLException e) {
				
				String message = "Проблема с закрытием запроса в базу данных.";
				logger.error(message, e);
				throw new DAOException(message , e);
			}

			if (conn != null) {
				cp.releaseConnection(conn);
			}

		}

	}

}
