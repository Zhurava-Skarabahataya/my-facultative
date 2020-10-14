package by.epamtc.facultative.controller.command.impl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.epamtc.facultative.controller.command.Command;

public class ChangeLanguageCommand implements Command{
	
	public static final String PARAMETER_COMMAND = "commandToLanguageChanger";
	public static final String PARAMETER_LOCAL = "local";
	public static final String COMMAND_PREFIX = "?command=";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		
		HttpSession session = request.getSession();
		String uri = (String) session.getAttribute(PARAMETER_COMMAND);

		request.getSession(true).setAttribute(PARAMETER_LOCAL, request.getParameter(PARAMETER_LOCAL));
		
		try {
			response.sendRedirect(request.getRequestURI() + COMMAND_PREFIX + uri);
			//request.getRequestDispatcher(uri).forward(request, response);
		} catch (IOException e) {
			e.printStackTrace();
			//Я обработаю, честное слово
		}
	}

	
}
