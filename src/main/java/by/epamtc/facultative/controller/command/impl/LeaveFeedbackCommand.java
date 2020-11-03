package by.epamtc.facultative.controller.command.impl;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.epamtc.facultative.bean.UserInfo;
import by.epamtc.facultative.controller.command.Command;
import by.epamtc.facultative.service.FeedbackService;

public class LeaveFeedbackCommand implements Command {
	
	private final String SESSION_ATTRIBUTE_LOGIN = "userLogin";
	private final String SESSION_ATTRIBUTE_BEAN = "bean";

	private final String REQUEST_PARAMETER_COURSE_ID = "courseId";
	private final String REQUEST_PARAMETER_COMMENT = "comment";
	
	
	private final String USER_PAGE_PATH = "WEB-INF/jsp/user-page.jsp";
	
	private final String ERROR_PAGE_PATH = "WEB-INF/jsp/error-page.jsp";
	
	private final String COMMAND_GO_TO_SUCCESS_PAGE = "?command=go_to_success_page_command";
	private final String COMMAND_GO_TO_ERROR_PAGE = "?command=go_to_error_page";
	private final String MESSAGE_GO_TO_SUCCESS_PAGE = "&message=success_leaving_feedback";
	private final String MESSAGE_GO_TO_ERROR_PAGE_NOT_AUTHORIZED = "&message=user_not_authorized";


	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		
		HttpSession session = request.getSession();

		String userLogin = (String) session.getAttribute(SESSION_ATTRIBUTE_LOGIN);
		
		if (userLogin != null) {
			
			String comment = request.getParameter(REQUEST_PARAMETER_COMMENT);
			
			int courseId = Integer.parseInt(request.getParameter(REQUEST_PARAMETER_COURSE_ID));
			
			UserInfo user = (UserInfo) session.getAttribute(SESSION_ATTRIBUTE_BEAN);
			
			
			int userId = user.getUserId(); 
			
			FeedbackService feedbackService = FeedbackService.getInstance();
			feedbackService.leaveFeadback(userId, courseId, comment);
			
			
			try {
				response.sendRedirect(request.getRequestURI() + COMMAND_GO_TO_SUCCESS_PAGE + MESSAGE_GO_TO_SUCCESS_PAGE);
			} catch (IOException e) {
				// ОШИБКА _ ПЕРЕНАПРРАВЬ
				e.printStackTrace();
			}
								
			
		}
		
		else {
			try {
				response.sendRedirect(request.getRequestURI() + COMMAND_GO_TO_ERROR_PAGE + MESSAGE_GO_TO_ERROR_PAGE_NOT_AUTHORIZED);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			
		}
		

	}

}
