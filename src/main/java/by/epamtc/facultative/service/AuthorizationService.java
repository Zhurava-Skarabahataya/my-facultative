package by.epamtc.facultative.service;

import by.epamtc.facultative.bean.UserAuthorizationInfo;
import by.epamtc.facultative.service.exception.ServiceException;

public interface AuthorizationService {
	
	 boolean execute(UserAuthorizationInfo info) throws ServiceException;

}
