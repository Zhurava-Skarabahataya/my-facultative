package by.epamtc.facultative.DAO.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import com.sun.crypto.provider.RSACipher;

import by.epamtc.facultative.DAO.impl.pool.ConnectionPool;
import by.epamtc.facultative.bean.InfoAboutRunnedCourse;
import by.epamtc.facultative.bean.UserPageInfo;

public class UserInfoDAOImpl {

	private static final UserInfoDAOImpl instance = new UserInfoDAOImpl();
	
	private static final String QUERY_SELECT_USER_DATA = "SELECT users.first_name, users.second_name, users.patronymic, "
			+ "users.user_email, users.department_department_id, departments.name, "
			+ "users.user_role_id, user_roles.role_name, "
			+ " user_details.user_adress, user_details.user_photo_link,"
			+ "user_details.user_mobile_number, user_details.user_date_of_birth "
			+ " FROM users  JOIN user_details ON users.user_id = user_details.users_user_id "
			+ "JOIN departments ON users.department_department_id = departments.department_id "
			+ "JOIN user_roles ON user_roles.role_id = users.user_role_id" + " where users.user_login = ?";


	private UserInfoDAOImpl() {

	}

	public static UserInfoDAOImpl getInstance() {
		return instance;
	}

	public void provideUserInfo(UserPageInfo userPageInfo) {

		ConnectionPool cp = ConnectionPool.getInstance();
		Connection conn = cp.getFreeConnection();
		
		PreparedStatement ps = null;

		String login = userPageInfo.getUserLogin();

		String userFirstName = null;
		String userSecondName = null;
		String userPatronymic = null;
		String userLogin = login;
		String userEmail = null;

		String userRole = null;
		String userFaculty = null;
		String userAdress = null;
		String userPhone = null;
		String userPhotoLink = null;

		LocalDate userDateOfBirth = null;

		int userRoleId = 0;
		int userFacultyId = 0;

		List<InfoAboutRunnedCourse> courses = null;
		
		ResultSet rs = null;

		try {
			String query = QUERY_SELECT_USER_DATA;

			ps = conn.prepareStatement(query);

			ps.setString(1, login);

			rs = ps.executeQuery();

			if (rs.next()) {
				
				userFirstName = rs.getString(1);
				userSecondName = rs.getString(2);
				userPatronymic = rs.getString(3);
				userEmail = rs.getString(4);

				userFacultyId = rs.getInt(5);
				userFaculty = rs.getString(6);

				userRoleId = rs.getInt(7);
				userRole = rs.getString(8);

				userAdress = rs.getString(9);
				userPhotoLink = rs.getString(10);
				userPhone = rs.getString(11);

				java.sql.Date sqlDate = rs.getDate(12);
				if (sqlDate != null) {
				userDateOfBirth = sqlDate.toLocalDate();}
			}

			userPageInfo.setUserFirstName(userFirstName);
			userPageInfo.setUserSecondName(userSecondName);
			userPageInfo.setUserPatronymic(userPatronymic);
			userPageInfo.setUserEmail(userEmail);

			userPageInfo.setUserRoleId(userRoleId);
			userPageInfo.setUserRole(userRole);

			userPageInfo.setUserFacultyId(userFacultyId);
			userPageInfo.setUserFaculty(userFaculty);

			userPageInfo.setUserAdress(userAdress);
			userPageInfo.setUserPhone(userPhone);
			userPageInfo.setUserPhotoLink(userPhotoLink);
			userPageInfo.setUserDateOfBirth(userDateOfBirth);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		finally {
			
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

	}

}
