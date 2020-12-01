package by.epamtc.facultative.controller.command.impl.redirection;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import by.epamtc.facultative.bean.UserInfo;
import by.epamtc.facultative.controller.command.Command;
import by.epamtc.facultative.service.ServiceProvider;
import by.epamtc.facultative.service.UserInfoService;
import by.epamtc.facultative.service.exception.ServiceException;

public class GoToAnotherUserPageCommand implements Command {
	
	private static final Logger logger = Logger.getLogger(GoToAnotherUserPageCommand.class);

	private final String SESSION_ATTRIBUTE_LOGIN = "userLogin";
	private final String SESSION_ATTRIBUTE_BEAN = "bean";
	private final String REQUEST_ATTRIBUTE_USER = "user";
	private final String REQUEST_ATTRIBUTE_USER_ID = "userId";
	
	private final String USER_PAGE_PATH = "WEB-INF/jsp/guest-user-page.jsp";
	
	private final String COMMAND_GO_TO_ERROR_PAGE = "?command=go_to_error_page";
	private final String MESSAGE_GO_TO_ERROR_PAGE_INTERNAL_SERVER_ERROR = "&message=server_error";
	private final String MESSAGE_TO_ERROR_PAGE_NOT_RIGHTS = "&message=not_enough_rights_for_procedure";
	private final String MESSAGE_TO_ERROR_PAGE_NOT_AUTHORIZED = "&message=user_not_authorized";


	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
			
		HttpSession session = request.getSession();

		String userLogin;
		userLogin = (String) session.getAttribute(SESSION_ATTRIBUTE_LOGIN);

		UserInfo loggedUserInfo = (UserInfo) session.getAttribute(SESSION_ATTRIBUTE_BEAN);
		int loggedUserRole = loggedUserInfo.getUserRoleId();

		if (userLogin != null && loggedUserRole > 1) {
			int userId = Integer.parseInt(request.getParameter(REQUEST_ATTRIBUTE_USER_ID));
			try {
				
				UserInfo userInfo = new UserInfo();
				userInfo.setUserId(userId);
				
				UserInfoService userService = ServiceProvider.getInstance().getUserInfoService();
				userService.findUserInfoById(userInfo);
				
				request.setAttribute(REQUEST_ATTRIBUTE_USER, userInfo);
				
				request.getRequestDispatcher(USER_PAGE_PATH).forward(request, response);
				
			} catch (ServletException | IOException | ServiceException e) {
				response.sendRedirect(request.getRequestURI() + COMMAND_GO_TO_ERROR_PAGE
						+ MESSAGE_GO_TO_ERROR_PAGE_INTERNAL_SERVER_ERROR);
			}
			
		} else if (userLogin != null){
			response.sendRedirect(
					request.getRequestURI() + COMMAND_GO_TO_ERROR_PAGE + MESSAGE_TO_ERROR_PAGE_NOT_RIGHTS);
		}
		else {
			try {
				response.sendRedirect(
						request.getRequestURI() + COMMAND_GO_TO_ERROR_PAGE + MESSAGE_TO_ERROR_PAGE_NOT_AUTHORIZED);
			} catch (IOException e) {
				logger.error(e);
				response.sendRedirect(request.getRequestURI() + COMMAND_GO_TO_ERROR_PAGE
						+ MESSAGE_GO_TO_ERROR_PAGE_INTERNAL_SERVER_ERROR);
			}
		}
		

	}

}
