package by.epamtc.facultative.DAO;

import by.epamtc.facultative.DAO.exception.DAOException;
import by.epamtc.facultative.bean.UserRegistrationInfo;

public interface RegistrationDAO {

	void registrateUser(UserRegistrationInfo userRegistrationInfo) throws DAOException;

	

}
