package by.epamtc.facultative.controller.command.impl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.epamtc.facultative.controller.command.Command;

public class GoToRunCoursePageCommand implements Command {
	
	private final String RUN_COURSE_PAGE_PATH = "WEB-INF/jsp/run-course-page.jsp";
	private final String ERROR_PAGE_PATH = "WEB-INF/jsp/error-page.jsp";


	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		String userLogin = (String) session.getAttribute("userLogin");
		
		if (userLogin != null) {

			try {
				request.getRequestDispatcher(RUN_COURSE_PAGE_PATH).forward(request, response);
			} catch (ServletException e) {
				// Я обработаю, честное слово
			} catch (IOException e) {
				// Я обработаю, честное слово
			}
		} else {
			try {
				session.setAttribute("errorMessage", "Необходимо войти в систему.");
				request.getRequestDispatcher(ERROR_PAGE_PATH).forward(request, response);
			} catch (ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}
