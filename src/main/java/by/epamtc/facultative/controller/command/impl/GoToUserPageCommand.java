package by.epamtc.facultative.controller.command.impl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.epamtc.facultative.bean.UserInfo;
import by.epamtc.facultative.controller.command.Command;
import by.epamtc.facultative.service.UserInfoService;

public class GoToUserPageCommand implements Command {

	private final String SESSION_ATTRIBUTE_LOGIN = "userLogin";

	private final String REQUEST_ATTRIBUTE_BEAN = "bean";
	private final String USER_PAGE_PATH = "WEB-INF/jsp/user-page.jsp";
	
	private final String ERROR_PAGE_PATH = "WEB-INF/jsp/error-page.jsp";
	
	private final String COMMAND_GO_TO_USER_PAGE = "?command=go_to_user_page";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession();

		String userLogin = (String) session.getAttribute(SESSION_ATTRIBUTE_LOGIN);

		if (userLogin != null) {

			try {
				//response.sendRedirect(request.getRequestURI() + COMMAND_GO_TO_USER_PAGE );
				request.getRequestDispatcher(USER_PAGE_PATH).forward(request, response);
			} catch (ServletException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
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
