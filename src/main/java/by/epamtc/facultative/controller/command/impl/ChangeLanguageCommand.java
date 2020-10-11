package by.epamtc.facultative.controller.command.impl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.epamtc.facultative.controller.command.Command;

public class ChangeLanguageCommand implements Command{
	
	public static final String PARAMETER_URI = "uri";
	public static final String PARAMETER_LOCAL = "local";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		
		String uri = (String) request.getParameter(PARAMETER_URI);
		
		request.getSession(true).setAttribute(PARAMETER_LOCAL, request.getParameter(PARAMETER_LOCAL));
		
		try {
			request.getRequestDispatcher(uri).forward(request, response);
		} catch (ServletException | IOException e) {
			e.printStackTrace();
			//Я обработаю, честное слово
		}
	}

	
}
