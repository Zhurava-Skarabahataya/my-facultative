package by.epamtc.facultative.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import by.epamtc.facultative.dao.exception.DAOException;
import by.epamtc.facultative.dao.impl.pool.ConnectionPool;
import by.epamtc.facultative.dao.impl.pool.ConnectionPoolException;
import by.epamtc.facultative.bean.Faculty;
import by.epamtc.facultative.bean.RunnedCourse;
import by.epamtc.facultative.bean.DepartmentStaff;
import by.epamtc.facultative.bean.UserInfo;

public class UserDAOImpl {

	private static final UserDAOImpl instance = new UserDAOImpl();

	private static final Logger logger = Logger.getLogger(UserDAOImpl.class);

	private final int DEPARTMENT_COUNT = 8;

	private final String QUERY_SELECT_USER_DATA = "SELECT users.first_name, users.second_name, users.patronymic, "
			+ "users.user_email, users.department_department_id, departments.name, "
			+ "users.user_role_id, user_roles.role_name, " + " user_details.user_adress,"
			+ "user_details.user_mobile_number, user_details.user_date_of_birth, users.user_id, " + "users.status "
			+ " FROM users  JOIN user_details ON users.user_id = user_details.users_user_id "
			+ "JOIN departments ON users.department_department_id = departments.department_id "
			+ "JOIN user_roles ON user_roles.role_id = users.user_role_id" + " where users.user_login = ?";
	
	private final String QUERY_FOR_USER_DATA_BY_ID = "SELECT users.first_name, users.second_name, "
			+ "users.patronymic, users.user_email, users.department_department_id, departments.name,"
			+ " users.user_role_id, user_roles.role_name, user_details.user_adress, "
			+ "user_details.user_mobile_number, user_details.user_date_of_birth, users.status, users.user_login "
			+ "FROM users  "
			+ "JOIN user_details ON users.user_id = user_details.users_user_id "
			+ "JOIN departments ON users.department_department_id = departments.department_id "
			+ "JOIN user_roles ON user_roles.role_id = users.user_role_id "
			+ "WHERE users.user_id = ?" ;

	private final String QUERY_UPDATE_USER_DATA_IN_USERS = "UPDATE users "
			+ "SET users.first_name = ? , users.second_name = ?, users.patronymic = ?, "
			+ " users.department_department_id = ? WHERE users.user_id = ?";

	private final String QUERY_UPDATE_USER_DATA_IN_USER_DETAILS = "UPDATE user_details "
			+ "SET user_adress = ? , user_mobile_number = ?, user_date_of_birth = ?" + " WHERE users_user_id = ?";

	private final String QUERY_FOR_CHANGING_EMPLOYEE_STATUS = "UPDATE users " + "SET status = ? WHERE user_id = ?";

	private final String QUERY_FOR_FINDING_STAFF_OF_DEPARTMENT = "SELECT "
			+ "users.user_id, users.first_name, users.second_name, users.patronymic, users.user_login, "
			+ "users.user_role_id, users.status, user_roles.role_name, "
			+ "user_details.user_mobile_number, user_details.user_adress, " + "user_details.user_date_of_birth "
			+ "FROM users JOIN user_details ON users.user_id = user_details.users_user_id "
			+ " JOIN user_roles ON user_roles.role_id = users.user_role_id "
			+ "WHERE users.user_role_id = 2 AND users.department_department_id = ?";

	private final String QUERY_FOR_FINDING_STAFF_OF_UNIVERSITY = "SELECT "
			+ "users.user_id, users.first_name, users.second_name, users.patronymic, users.user_login, "
			+ "departments.name, departments.department_id, "
			+ "users.user_role_id, users.status, user_roles.role_name, "
			+ "user_details.user_mobile_number, user_details.user_adress, " + "user_details.user_date_of_birth "
			+ "FROM users JOIN user_details ON users.user_id = user_details.users_user_id "
			+ " JOIN user_roles ON user_roles.role_id = users.user_role_id "
			+ "JOIN departments ON departments.department_id = users.department_department_id "
			+ "WHERE users.user_role_id = 2 OR users.user_role_id = 3";

	private UserDAOImpl() {

	}

	public static UserDAOImpl getInstance() {
		return instance;
	}

	public void provideUserInfo(UserInfo userPageInfo) throws DAOException {

		ConnectionPool cp = ConnectionPool.getInstance();
		Connection conn = null;

		try {
			conn = cp.getFreeConnection();
		} catch (ConnectionPoolException e) {
			throw new DAOException(e);
		}

		PreparedStatement ps = null;

		String login = userPageInfo.getUserLogin();

		String userFirstName = null;
		String userSecondName = null;
		String userPatronymic = null;
		String userEmail = null;

		String userRole = null;
		String userFaculty = null;
		String userAdress = null;
		String userPhone = null;
		int userId = 0;
		int userStatusId = 0;

		LocalDate userDateOfBirth = null;

		int userRoleId = 0;
		int userFacultyId = 0;


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

				userRoleId = rs.getInt("users.user_role_id");
				userRole = rs.getString("user_roles.role_name");
				userStatusId = rs.getInt("users.status");

				userAdress = rs.getString(9);
				userPhone = rs.getString(10);

				java.sql.Date sqlDate = rs.getDate(11);
				if (sqlDate != null) {
					userDateOfBirth = sqlDate.toLocalDate();
				}

				userId = rs.getInt(12);
			}

			userPageInfo.setUserId(userId);

			userPageInfo.setUserFirstName(userFirstName);
			userPageInfo.setUserSecondName(userSecondName);
			userPageInfo.setUserPatronymic(userPatronymic);
			userPageInfo.setUserEmail(userEmail);

			userPageInfo.setUserRoleId(userRoleId);
			userPageInfo.setUserRole(userRole);
			userPageInfo.setUserStatusId(userStatusId);

			userPageInfo.setUserFacultyId(userFacultyId);
			userPageInfo.setUserFaculty(userFaculty);

			userPageInfo.setUserAdress(userAdress);
			userPageInfo.setUserPhone(userPhone);
			userPageInfo.setUserDateOfBirth(userDateOfBirth);

		} catch (SQLException e) {

			String message = "Проблема с выполнением запроса в базу данных.";
			logger.error(message, e);
			throw new DAOException(message, e);
		}

		finally {

			try {
				cp.closeConnection(rs, ps, conn);
			} catch (ConnectionPoolException e) {
				throw new DAOException(e);
			}
		}

	}

	public void updateUserInfo(UserInfo userPageInfo) throws DAOException {
		ConnectionPool cp = ConnectionPool.getInstance();
		Connection conn = null;

		try {
			conn = cp.getFreeConnection();
		} catch (ConnectionPoolException e) {
			throw new DAOException(e);
		}

		PreparedStatement statementFirst = null;
		PreparedStatement statementSecond = null;

		try {
			statementFirst = conn.prepareStatement(QUERY_UPDATE_USER_DATA_IN_USERS);
			statementSecond = conn.prepareStatement(QUERY_UPDATE_USER_DATA_IN_USER_DETAILS);

			statementFirst.setString(1, userPageInfo.getUserFirstName());
			statementFirst.setString(2, userPageInfo.getUserSecondName());
			statementFirst.setString(3, userPageInfo.getUserPatronymic());
			statementFirst.setInt(4, userPageInfo.getUserFacultyId());

			statementSecond.setString(1, userPageInfo.getUserAdress());
			statementSecond.setString(2, userPageInfo.getUserPhone());
			if (userPageInfo.getUserDateOfBirth() != null) {
				Date sqlDate = Date.valueOf(userPageInfo.getUserDateOfBirth());
				statementSecond.setDate(3, sqlDate);
			} else {
				statementSecond.setDate(3, null);
			}

			statementFirst.setInt(5, userPageInfo.getUserId());
			statementSecond.setInt(4, userPageInfo.getUserId());
			statementFirst.executeUpdate();
			statementSecond.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				statementFirst.close();
				statementSecond.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			cp.releaseConnection(conn);
		}

	}

	public List<Faculty> findStaffFromDepartment(int userDepartment) throws DAOException {

		List<Faculty> staff = new ArrayList<Faculty>();

		ConnectionPool cp = ConnectionPool.getInstance();
		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		try {
			conn = cp.getFreeConnection();
			statement = conn.prepareStatement(QUERY_FOR_FINDING_STAFF_OF_DEPARTMENT);

			statement.setInt(1, userDepartment);

			resultSet = statement.executeQuery();

			while (resultSet.next()) {

				int userId = resultSet.getInt("users.user_id");
				int userRoleId = resultSet.getInt("users.user_role_id");
				int userStatus = resultSet.getInt("users.status");
				String userFirstName = resultSet.getString("users.first_name");
				String userSecondName = resultSet.getString("users.second_name");
				String userPatronymic = resultSet.getString("users.patronymic");
				String userLogin = resultSet.getString("users.user_login");
				String userRoleName = resultSet.getString("user_roles.role_name");
				String userMobile = resultSet.getString("user_details.user_mobile_number");
				String userAdress = resultSet.getString("user_details.user_adress");
				LocalDate userDateOfBirth = null;
				java.sql.Date sqlDate = resultSet.getDate("user_details.user_date_of_birth");
				if (sqlDate != null) {
					userDateOfBirth = sqlDate.toLocalDate();
				}

				UserInfo employee = new UserInfo();
				employee.setUserId(userId);
				employee.setUserRoleId(userRoleId);
				employee.setUserStatusId(userStatus);
				employee.setUserFirstName(userFirstName);
				employee.setUserSecondName(userSecondName);
				employee.setUserPatronymic(userPatronymic);
				employee.setUserLogin(userLogin);
				employee.setUserRole(userRoleName);
				employee.setUserPhone(userMobile);
				employee.setUserAdress(userAdress);
				employee.setUserDateOfBirth(userDateOfBirth);

				if (userRoleId == 3) {

				}

//				
//				staff.add(employee);
			}

		} catch (ConnectionPoolException e) {
			throw new DAOException(e);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				cp.closeConnection(resultSet, statement, conn);
			} catch (ConnectionPoolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return staff;
	}

	public DepartmentStaff findFacultyStaffInfo(int departmentId) {

		DepartmentStaff facultyStaffInfo = new DepartmentStaff();

		List<UserInfo> workingLecturers = new ArrayList<UserInfo>();
		List<UserInfo> notApprovedLecturers = new ArrayList<UserInfo>();
		List<UserInfo> firedLecturers = new ArrayList<UserInfo>();

		ConnectionPool cp = ConnectionPool.getInstance();
		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		try {
			conn = cp.getFreeConnection();
			statement = conn.prepareStatement(QUERY_FOR_FINDING_STAFF_OF_DEPARTMENT);
			statement.setInt(1, departmentId);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {

				int userId = resultSet.getInt("users.user_id");
				int userRoleId = resultSet.getInt("users.user_role_id");
				int userStatus = resultSet.getInt("users.status");
				String userFirstName = resultSet.getString("users.first_name");
				String userSecondName = resultSet.getString("users.second_name");
				String userPatronymic = resultSet.getString("users.patronymic");
				String userLogin = resultSet.getString("users.user_login");
				String userRoleName = resultSet.getString("user_roles.role_name");
				String userMobile = resultSet.getString("user_details.user_mobile_number");
				String userAdress = resultSet.getString("user_details.user_adress");
				LocalDate userDateOfBirth = null;
				java.sql.Date sqlDate = resultSet.getDate("user_details.user_date_of_birth");
				if (sqlDate != null) {
					userDateOfBirth = sqlDate.toLocalDate();
				}

				UserInfo employee = new UserInfo();
				employee.setUserId(userId);
				employee.setUserRoleId(userRoleId);
				employee.setUserStatusId(userStatus);
				employee.setUserFirstName(userFirstName);
				employee.setUserSecondName(userSecondName);
				employee.setUserPatronymic(userPatronymic);
				employee.setUserLogin(userLogin);
				employee.setUserRole(userRoleName);
				employee.setUserPhone(userMobile);
				employee.setUserAdress(userAdress);
				employee.setUserDateOfBirth(userDateOfBirth);

				if (userStatus == 1) {
					notApprovedLecturers.add(employee);
				} else if (userStatus == 2) {
					workingLecturers.add(employee);

				} else {
					firedLecturers.add(employee);
				}

			}

		} catch (ConnectionPoolException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				cp.closeConnection(resultSet, statement, conn);
			} catch (ConnectionPoolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		facultyStaffInfo.setWorkingStaff(workingLecturers);
		facultyStaffInfo.setNotApprovedStaff(notApprovedLecturers);

		facultyStaffInfo.setFiredStaff(firedLecturers);

		return facultyStaffInfo;
	}

	public DepartmentStaff findAllFacultiesStaffInfo() {

		DepartmentStaff allFacultiesStaffInfo = new DepartmentStaff();

		List<UserInfo> allStaff = new ArrayList<UserInfo>();

		ConnectionPool cp = ConnectionPool.getInstance();
		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		try {
			conn = cp.getFreeConnection();
			statement = conn.prepareStatement(QUERY_FOR_FINDING_STAFF_OF_UNIVERSITY);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				int userId = resultSet.getInt("users.user_id");
				int userRoleId = resultSet.getInt("users.user_role_id");
				int userStatus = resultSet.getInt("users.status");
				String userFirstName = resultSet.getString("users.first_name");
				String userSecondName = resultSet.getString("users.second_name");
				String userPatronymic = resultSet.getString("users.patronymic");
				String userLogin = resultSet.getString("users.user_login");
				String userRoleName = resultSet.getString("user_roles.role_name");
				String userMobile = resultSet.getString("user_details.user_mobile_number");
				String userAdress = resultSet.getString("user_details.user_adress");
				LocalDate userDateOfBirth = null;
				java.sql.Date sqlDate = resultSet.getDate("user_details.user_date_of_birth");
				if (sqlDate != null) {
					userDateOfBirth = sqlDate.toLocalDate();
				}
				int departmentId = resultSet.getInt("departments.department_id");
				String departmentName = resultSet.getString("departments.name");

				UserInfo employee = new UserInfo();
				employee.setUserId(userId);
				employee.setUserRoleId(userRoleId);
				employee.setUserStatusId(userStatus);
				employee.setUserFirstName(userFirstName);
				employee.setUserSecondName(userSecondName);
				employee.setUserPatronymic(userPatronymic);
				employee.setUserLogin(userLogin);
				employee.setUserRole(userRoleName);
				employee.setUserPhone(userMobile);
				employee.setUserAdress(userAdress);
				employee.setUserDateOfBirth(userDateOfBirth);
				employee.setUserFacultyId(departmentId);
				employee.setUserFaculty(departmentName);

				allStaff.add(employee);

			}

			allFacultiesStaffInfo.setAllStaff(allStaff);

		} catch (ConnectionPoolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				cp.closeConnection(resultSet, statement, conn);
			} catch (ConnectionPoolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return allFacultiesStaffInfo;
	}

	public void changeEmployeeStatus(int employeeId, int status) throws DAOException {

		ConnectionPool cp = ConnectionPool.getInstance();
		Connection conn = null;
		PreparedStatement statement = null;

		try {
			
			conn = cp.getFreeConnection();
			statement = conn.prepareStatement(QUERY_FOR_CHANGING_EMPLOYEE_STATUS);

			statement.setInt(1, status);
			statement.setInt(2, employeeId);
			
			statement.executeUpdate();

		} catch (SQLException e) {
			throw new DAOException();
			
			//НЕ УДАЛОСЬ, ПЕРЕДАЙ НАВЕРХ 
			//e.printStackTrace();
		} catch (ConnectionPoolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		finally {
			
			try {
				cp.closeConnection(statement, conn);
			} catch (ConnectionPoolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}

	}

	public UserInfo findUserInfoById(int userId) throws DAOException {
		
		UserInfo userInfo = new UserInfo();
		userInfo.setUserId(userId);
		
		ConnectionPool cp = ConnectionPool.getInstance();
		Connection conn = null;

		try {
			conn = cp.getFreeConnection();
		} catch (ConnectionPoolException e) {
			throw new DAOException(e);
		}

		PreparedStatement ps = null;

		String userFirstName = null;
		String userSecondName = null;
		String userPatronymic = null;
		String userEmail = null;

		String userRole = null;
		String userFaculty = null;
		String userAdress = null;
		String userPhone = null;
		String userLogin = null;

		int userStatusId = 0;

		LocalDate userDateOfBirth = null;

		int userRoleId = 0;
		int userFacultyId = 0;


		ResultSet rs = null;

		try {
			String query = QUERY_FOR_USER_DATA_BY_ID;

			ps = conn.prepareStatement(query);

			ps.setInt(1, userId);

			rs = ps.executeQuery();

			if (rs.next()) {
		
				userFirstName = rs.getString("users.first_name");
				userSecondName = rs.getString("users.second_name");
				userPatronymic = rs.getString("users.patronymic");
				userEmail = rs.getString("users.user_email");
				userLogin = rs.getString("users.user_login");

				userFacultyId = rs.getInt("users.department_department_id");
				userFaculty = rs.getString("departments.name");

				userRoleId = rs.getInt("users.user_role_id");
				userRole = rs.getString("user_roles.role_name");
				userStatusId = rs.getInt("users.status");

				userAdress = rs.getString("user_details.user_adress");
				userPhone = rs.getString("user_details.user_mobile_number");

				java.sql.Date sqlDate = rs.getDate("user_details.user_date_of_birth");
				if (sqlDate != null) {
					userDateOfBirth = sqlDate.toLocalDate();
				}

			}


			userInfo.setUserFirstName(userFirstName);
			userInfo.setUserSecondName(userSecondName);
			userInfo.setUserPatronymic(userPatronymic);
			userInfo.setUserEmail(userEmail);
			userInfo.setUserLogin(userLogin);

			userInfo.setUserRoleId(userRoleId);
			userInfo.setUserRole(userRole);
			userInfo.setUserStatusId(userStatusId);

			userInfo.setUserFacultyId(userFacultyId);
			userInfo.setUserFaculty(userFaculty);

			userInfo.setUserAdress(userAdress);
			userInfo.setUserPhone(userPhone);
			userInfo.setUserDateOfBirth(userDateOfBirth);

		} catch (SQLException e) {

			String message = "Проблема с выполнением запроса в базу данных.";
			logger.error(message, e);
			throw new DAOException(message, e);
		}

		finally {

			try {
				cp.closeConnection(rs, ps, conn);
			} catch (ConnectionPoolException e) {
				throw new DAOException(e);
			}
		}

		
		
		return userInfo;
	}

}
