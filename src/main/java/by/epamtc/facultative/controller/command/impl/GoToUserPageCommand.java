package by.epamtc.facultative.controller.command.impl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.epamtc.facultative.bean.UserPageInfo;
import by.epamtc.facultative.controller.command.Command;
import by.epamtc.facultative.service.UserPageInfoProviderService;

public class GoToUserPageCommand implements Command {
	
	private static final String SESSION_ATTRIBUTE_LOGIN = "userLogin";
	private static final String REQUEST_ATTRIBUTE_BEAN = "bean";
	private static final String USER_PAGE_PATH = "WEB-INF/jsp/user-page.jsp";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		
		HttpSession session = request.getSession();

		String userLogin = (String) session.getAttribute(SESSION_ATTRIBUTE_LOGIN); 
		
		UserPageInfo userInfo = new UserPageInfo();
		userInfo.setUserLogin(userLogin);
		
		UserPageInfoProviderService userPageInfoProviderService = UserPageInfoProviderService.getInstance();
		userPageInfoProviderService.execute(userInfo);

		System.out.println("user login in gotouserpagecommand "+ userLogin);
		
		request.setAttribute(REQUEST_ATTRIBUTE_BEAN, userInfo);
		
		
		try {
			request.getRequestDispatcher(USER_PAGE_PATH).forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
