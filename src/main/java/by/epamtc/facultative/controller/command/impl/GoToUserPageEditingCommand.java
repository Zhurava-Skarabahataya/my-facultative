package by.epamtc.facultative.controller.command.impl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.epamtc.facultative.bean.UserPageInfo;
import by.epamtc.facultative.controller.command.Command;

public class GoToUserPageEditingCommand implements Command{
	
	private static final String SESSION_ATTRIBUTE_LOGIN = "userLogin";
	
	private static final String ERROR_PAGE_PATH = "WEB-INF/jsp/error-page.jsp";
	private static final String EDITING_PAGE_PATH = "WEB-INF/jsp/user-page-editing.jsp";


	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		
		HttpSession session = request.getSession();

		String userLogin = (String) session.getAttribute(SESSION_ATTRIBUTE_LOGIN);
				
		if (userLogin != null) {
			try {
				request.getRequestDispatcher(EDITING_PAGE_PATH).forward(request, response);
			} catch (ServletException e) {
				// аваививи
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else {
			try {
				session.setAttribute("errorMessage", "Необходимо войти в систему.");
				request.getRequestDispatcher(ERROR_PAGE_PATH).forward(request, response);
			} catch (ServletException e) {
				// ОБРАБОТЬ
				e.printStackTrace();
			} catch (IOException e) {
				// ОБРАБОТЬ
				e.printStackTrace();
			}
		}
	}

}
