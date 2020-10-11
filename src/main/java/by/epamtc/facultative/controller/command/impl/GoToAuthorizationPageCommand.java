package by.epamtc.facultative.controller.command.impl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.epamtc.facultative.controller.command.Command;

public class GoToAuthorizationPageCommand implements Command{

	private static final String AUTHORIZATION_PAGE_PATH = "WEB-INF/jsp/authorization-page.jsp";
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {

		try {
			request.getRequestDispatcher(AUTHORIZATION_PAGE_PATH).forward(request, response);
			
		} catch (ServletException e) {
			e.printStackTrace();
			//ОБРАБООООООТАЙ			//Я обработаю, честное слово


		} catch (IOException e) {
			e.printStackTrace();
			//ОБРАБООООООТАЙ			//Я обработаю, честное слово


		}
		
		
	}

}
