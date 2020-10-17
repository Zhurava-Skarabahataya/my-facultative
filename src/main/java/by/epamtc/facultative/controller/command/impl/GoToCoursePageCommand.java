package by.epamtc.facultative.controller.command.impl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.epamtc.facultative.controller.command.Command;

public class GoToCoursePageCommand implements Command {

	private final String COURSE_PAGE_PATH = "WEB-INF/jsp/run-course-page.jsp";

	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		
		
		
		try {
			request.getRequestDispatcher(COURSE_PAGE_PATH).forward(request, response);
		} catch (ServletException e) {
			// Я обработаю, честное слово
		} catch (IOException e) {
			// Я обработаю, честное слово
		}
		

	}

}
