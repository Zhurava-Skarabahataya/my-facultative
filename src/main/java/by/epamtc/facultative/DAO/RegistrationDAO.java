package by.epamtc.facultative.dao;

import by.epamtc.facultative.dao.exception.DAOException;
import by.epamtc.facultative.bean.UserRegistrationInfo;

public interface RegistrationDAO {

	public void registrateUser(UserRegistrationInfo userRegistrationInfo) throws DAOException;

	public boolean checkEmailIfExists(String email) throws DAOException;
	public boolean checkLoginIfExists(String login) throws DAOException;

}
