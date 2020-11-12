package by.epamtc.facultative.service.impl;

import java.util.List;

import by.epamtc.facultative.bean.InfoForWelcomePage;
import by.epamtc.facultative.bean.News;
import by.epamtc.facultative.dao.DAOFactory;
import by.epamtc.facultative.dao.WelcomePageDAO;
import by.epamtc.facultative.dao.exception.DAOException;
import by.epamtc.facultative.service.NewsService;
import by.epamtc.facultative.service.ServiceProvider;
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
	public void getInfo(InfoForWelcomePage infoForWelcomePage) throws ServiceException {
		
		DAOFactory daoFactory = DAOFactory.getInstance();
		WelcomePageDAO welcomePageDAO = daoFactory.getWelcomePageDAO();
		
		try {
			welcomePageDAO.getDataFromDatabase();
			
			NewsService newsService = ServiceProvider.getInstance().getNewsService();
			List <News> news = newsService.findNews();
			
			infoForWelcomePage.setNews(news);
			
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
		
	}

}
