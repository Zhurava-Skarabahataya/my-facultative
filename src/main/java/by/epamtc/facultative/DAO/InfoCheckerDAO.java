package by.epamtc.facultative.dao;

import by.epamtc.facultative.dao.exception.DAOException;

public interface InfoCheckerDAO {
	
	boolean checkInfoIfExists(String info) throws DAOException;

}
