package by.epamtc.facultative.controller.command.impl;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.epamtc.facultative.controller.command.Command;

public class LogOutCommand implements Command {
	
	private static final String COMMAND_GO_TO_WELCOME_PAGE = "?command=go_to_welcome_page";


	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		
		HttpSession session = request.getSession();
		session.setAttribute("userLogin", null);
		session.setAttribute("bean", null);
		
		try {
			response.sendRedirect(request.getRequestURI() + COMMAND_GO_TO_WELCOME_PAGE);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
