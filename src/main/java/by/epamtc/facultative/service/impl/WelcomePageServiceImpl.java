package by.epamtc.facultative.service.impl;

import by.epamtc.facultative.dao.DAOFactory;
import by.epamtc.facultative.dao.WelcomePageDAO;
import by.epamtc.facultative.dao.exception.DAOException;
import by.epamtc.facultative.service.WelcomePageService;
import by.epamtc.facultative.service.exception.ServiceException;

public class WelcomePageServiceImpl implements WelcomePageService{
	
	public static WelcomePageServiceImpl instance = new WelcomePageServiceImpl();
	
	private WelcomePageServiceImpl() {
		
	}
	
	public static WelcomePageServiceImpl getInstance() {
		return instance;
	}
	
	@Override
	public String getInfo() throws ServiceException {
		
		DAOFactory daoFactory = DAOFactory.getInstance();
		WelcomePageDAO welcomePageDAO = daoFactory.getWelcomePageDAO();
		
		try {
			welcomePageDAO.getDataFromDatabase();
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
		
		return "UIUNWISW";
		
	}

}
