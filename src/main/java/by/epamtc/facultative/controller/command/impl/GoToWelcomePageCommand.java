package by.epamtc.facultative.controller.command.impl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.epamtc.facultative.controller.command.Command;
import by.epamtc.facultative.service.WelcomePageInfoDealer;

public class GoToWelcomePageCommand implements Command{
	
	private static final String WELCOME_PAGE_PATH = "WEB-INF/jsp/welcome-page.jsp";
	
	private static final String SESSION_ATTRIBUTE_LOGIN = "userLogin";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		
		HttpSession session = request.getSession();

		if (session.getAttribute(SESSION_ATTRIBUTE_LOGIN) == null){
			session.setAttribute(SESSION_ATTRIBUTE_LOGIN, null);
		}
		
		
		try {
			WelcomePageInfoDealer wcid = WelcomePageInfoDealer.getInstance();
			String hfh = wcid.getInfo();
			request.setAttribute("popularCourses", hfh);			
			request.getRequestDispatcher(WELCOME_PAGE_PATH).forward(request, response);
			
		} catch (ServletException e) {
			e.printStackTrace();
			//ОБРАБООООООТАЙ

		} catch (IOException e) {
			e.printStackTrace();
			//ОБРАБООООООТАЙ

		}
		
	}

}
