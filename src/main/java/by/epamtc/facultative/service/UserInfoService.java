package by.epamtc.facultative.service;

import java.util.List;

import by.epamtc.facultative.bean.DepartmentStaff;
import by.epamtc.facultative.bean.UserInfo;
import by.epamtc.facultative.service.exception.ServiceException;

public interface UserInfoService {

	void findUserInfo(UserInfo userInfo) throws ServiceException;

	void findUserRating(UserInfo loggedUserInfo) throws ServiceException;

	void findUserInfoById(UserInfo userInfo) throws ServiceException;

	DepartmentStaff findFacultyStaffForDean(UserInfo userInfo) throws ServiceException;

	List<DepartmentStaff> findUnivercityStaffForRector(UserInfo userInfo) throws ServiceException;

}
