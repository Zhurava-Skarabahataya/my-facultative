package by.epamtc.facultative.service;

import by.epamtc.facultative.dao.impl.WelcomePageDAOImpl;

public class WelcomePageInfoDealer {
	
	public static WelcomePageInfoDealer instance = new WelcomePageInfoDealer();
	
	private WelcomePageInfoDealer() {
		
	}
	
	public static WelcomePageInfoDealer getInstance() {
		return instance;
	}
	
	public String getInfo() {
		WelcomePageDAOImpl dbi = new WelcomePageDAOImpl();
		dbi.getDataFromDatabase();
		
		return "UIUNWISW";
		
	}

}
