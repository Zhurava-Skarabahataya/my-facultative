package by.epamtc.facultative.dao;

import by.epamtc.facultative.dao.exception.DAOException;
import by.epamtc.facultative.bean.UserRegistrationInfo;

public interface RegistrationDAO {

	void registrateUser(UserRegistrationInfo userRegistrationInfo) throws DAOException;

	boolean checkEmailIfExists(String email) throws DAOException;

	boolean checkLoginIfExists(String login) throws DAOException;

}
