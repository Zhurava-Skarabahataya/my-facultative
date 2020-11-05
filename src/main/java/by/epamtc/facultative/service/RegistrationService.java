package by.epamtc.facultative.service;

import by.epamtc.facultative.bean.UserRegistrationInfo;
import by.epamtc.facultative.service.exception.ServiceException;

public interface RegistrationService {
	
	String execute(UserRegistrationInfo userInfo) throws ServiceException;

}
