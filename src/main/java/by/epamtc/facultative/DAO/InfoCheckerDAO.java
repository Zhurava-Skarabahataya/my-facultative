package by.epamtc.facultative.DAO;

import by.epamtc.facultative.DAO.exception.DAOException;

public interface InfoCheckerDAO {
	
	boolean checkInfoIfExists(String info) throws DAOException;

}
