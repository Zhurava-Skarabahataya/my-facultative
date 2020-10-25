package by.epamtc.facultative.controller.command.impl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.epamtc.facultative.controller.command.Command;

public class GoToSuccessPageCommand implements Command {
	
	private final String APPLICATION_SUCCESS_PAGE = "WEB-INF/jsp/success-page.jsp";

	

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		
		String message = request.getParameter("message");
		System.out.println(message);
		
		try {
			request.setAttribute("message", message);
			request.getRequestDispatcher(APPLICATION_SUCCESS_PAGE).forward(request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
