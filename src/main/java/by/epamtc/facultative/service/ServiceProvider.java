package by.epamtc.facultative.service;

import by.epamtc.facultative.service.impl.FullNameServiceImpl;

public class ServiceProvider {
	
	private static final ServiceProvider instance = new ServiceProvider();
	
	private final FullNameService fullNameService = FullNameServiceImpl.getInstance();
	
	private ServiceProvider() {
		
	}
	
	public static ServiceProvider getInstance() {
		return instance;
	}

	public FullNameService getFullNameService() {
		return fullNameService;
	}

	
	
}
