package by.epamtc.facultative.service;

import javax.xml.bind.ValidationException;

import by.epamtc.facultative.bean.UserInfo;
import by.epamtc.facultative.service.exception.ServiceException;

public interface UpdateUserInfoService {

	void update(UserInfo userPageInfo) throws ValidationException, ServiceException;
}
