package by.epamtc.facultative.service;

import by.epamtc.facultative.dao.impl.WelcomePageInfoDAOImpl;

public class WelcomePageInfoDealer {
	
	public static WelcomePageInfoDealer instance = new WelcomePageInfoDealer();
	
	private WelcomePageInfoDealer() {
		
	}
	
	public static WelcomePageInfoDealer getInstance() {
		return instance;
	}
	
	public String getInfo() {
		WelcomePageInfoDAOImpl dbi = new WelcomePageInfoDAOImpl();
		dbi.getDataFromDatabase();
		
		return "UIUNWISW";
		
	}

}
