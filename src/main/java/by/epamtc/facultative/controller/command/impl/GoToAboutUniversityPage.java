package by.epamtc.facultative.controller.command.impl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.epamtc.facultative.controller.command.Command;

public class GoToAboutUniversityPage implements Command {
private final String ABOUT_UNI_PAGE_PATH = "WEB-INF/jsp/abous-us-page.jsp";

	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		
		try {
			request.getRequestDispatcher(ABOUT_UNI_PAGE_PATH).forward(request, response);
		} catch (ServletException e) {
			// Я обработаю, честное слово
		} catch (IOException e) {
			// Я обработаю, честное слово
		}
		

	}

}
