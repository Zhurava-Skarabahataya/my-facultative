package by.epamtc.facultative.controller.command.impl;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.epamtc.facultative.bean.UserInfo;
import by.epamtc.facultative.controller.command.Command;
import by.epamtc.facultative.service.UserInfoService;

public class GoToStaffPageCommand implements Command {
	
	private static final String STAFF_PAGE_PATH = "WEB-INF/jsp/staff-page.jsp";

	private final String ERROR_PAGE_PATH = "WEB-INF/jsp/error-page.jsp";
	private final String MESSAGE_TO_ERROR_PAGE = "errorMessage";
	private final String ERROR_CAUSE_NOT_APPROVED = "user_not_approved";
	private final String ERROR_CAUSE_NOT_AUTHORIZED = "user_not_authorized";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		
		HttpSession session = request.getSession();
		String userLogin = (String) session.getAttribute("userLogin");
		
		if (userLogin != null) {
			
			UserInfo userInfo = (UserInfo) session.getAttribute("bean");
			int userInfoStatusId = userInfo.getUserStatusId();
			int userRoleId = userInfo.getUserRoleId();
			
			if(userInfoStatusId != 2) {
				
				request.setAttribute(MESSAGE_TO_ERROR_PAGE, ERROR_CAUSE_NOT_APPROVED);
				try {
					request.getRequestDispatcher(ERROR_PAGE_PATH).forward(request, response);
				} catch (ServletException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else {
				
				UserInfoService userService = UserInfoService.getInstance();
			//	List<UserInfo> staff = userService.findStaffForUser(userInfo);
				
			}
			
			
		}

	}

}
