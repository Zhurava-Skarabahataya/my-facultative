package by.epamtc.facultative.controller.command.impl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.epamtc.facultative.bean.Mark;
import by.epamtc.facultative.bean.UserInfo;
import by.epamtc.facultative.controller.command.Command;
import by.epamtc.facultative.service.impl.UserInfoServiceImpl;

public class ViewStudentRatingPageCommand implements Command {
	
	private final String SESSION_ATTRIBUTE_LOGIN = "userLogin";

	private final String SESSION_ATTRIBUTE_BEAN = "bean";
	private final String USER_RATING_PAGE_PATH = "WEB-INF/jsp/user-rating-page.jsp";
	
	private final String ERROR_PAGE_PATH = "WEB-INF/jsp/error-page.jsp";
	
	private final String COMMAND_GO_TO_USER_PAGE = "?command=go_to_user_page";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		
		HttpSession session = request.getSession();

		String userLogin = (String) session.getAttribute(SESSION_ATTRIBUTE_LOGIN);
		
		if (userLogin != null) {
		
		String userLoginFromRequest = request.getParameter("userLogin");
		UserInfo loggedUserInfo = (UserInfo) session.getAttribute("bean");

		
		if (userLoginFromRequest != null && userLoginFromRequest.equals(userLogin)) {
			
			UserInfoServiceImpl userService = UserInfoServiceImpl.getInstance();
			userService.findUserRating(loggedUserInfo);
			
			request.setAttribute("student", loggedUserInfo);
			
			for (Mark m: loggedUserInfo.getStudentMarks()) {
				System.out.println(m);
			}
			
			try {
				request.getRequestDispatcher(USER_RATING_PAGE_PATH).forward(request, response);
			} catch (ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//значи, для самого студня
		}
		else {
			//для других
			int userRoleId = loggedUserInfo.getUserRoleId();
			
			
			
			
		}
		
		
		
		}
		else {
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
