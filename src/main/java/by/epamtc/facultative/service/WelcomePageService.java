package by.epamtc.facultative.service;

import by.epamtc.facultative.bean.InfoForWelcomePage;
import by.epamtc.facultative.service.exception.ServiceException;

public interface WelcomePageService {

	void getInfo(InfoForWelcomePage infoForWelcomePage) throws ServiceException;

}
