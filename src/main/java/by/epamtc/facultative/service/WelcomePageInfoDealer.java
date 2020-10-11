package by.epamtc.facultative.service;

import by.epamtc.facultative.DAO.impl.DatabaseDAOImpl;

public class WelcomePageInfoDealer {
	
	public static WelcomePageInfoDealer instance = new WelcomePageInfoDealer();
	
	private WelcomePageInfoDealer() {
		
	}
	
	public static WelcomePageInfoDealer getInstance() {
		return instance;
	}
	
	public String getInfo() {
		DatabaseDAOImpl dbi = new DatabaseDAOImpl();
		dbi.getDataFromDatabase();
		
		return "UIUNWISW";
		
	}

}
