package by.epamtc.facultative.controller.command.impl;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import by.epamtc.facultative.bean.DepartmentStaff;
import by.epamtc.facultative.bean.UserInfo;
import by.epamtc.facultative.controller.command.Command;
import by.epamtc.facultative.service.ServiceProvider;
import by.epamtc.facultative.service.UserInfoService;
import by.epamtc.facultative.service.exception.ServiceException;

public class GoToStaffPageCommand implements Command {

	private static final Logger logger = Logger.getLogger(GoToStaffPageCommand.class);

	private final String COMMAND_GO_TO_ERROR_PAGE = "?command=go_to_error_page";
	private final String MESSAGE_GO_TO_ERROR_PAGE_INTERNAL_SERVER_ERROR = "&message=server_error";
	private final String STAFF_PAGE_PATH = "WEB-INF/jsp/staff-page.jsp";
	private final String MESSAGE_TO_ERROR_PAGE_NOT_AUTHORIZED = "&message=user_not_authorized";

	private final String ERROR_PAGE_PATH = "WEB-INF/jsp/error-page.jsp";
	private final String MESSAGE_TO_ERROR_PAGE = "errorMessage";
	private final String ERROR_CAUSE_NOT_APPROVED = "user_not_approved";

	private final String SESSION_PARAMETER_USER_LOGIN = "userLogin";
	private final String SESSION_PARAMETER_BEAN = "bean";
	private final String REQUEST_PARAMETER_STAFF = "staff";
	private final String REQUEST_PARAMETER_ALL_STAFF = "allStaff";

	private final int STATUS_APPROVED = 2;

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {

		HttpSession session = request.getSession();
		String userLogin;
		userLogin = (String) session.getAttribute(SESSION_PARAMETER_USER_LOGIN);

		if (userLogin != null) {

			UserInfo userInfo;
			int userInfoStatusId;
			int userRoleId;

			userInfo = (UserInfo) session.getAttribute(SESSION_PARAMETER_BEAN);
			userInfoStatusId = userInfo.getUserStatusId();
			userRoleId = userInfo.getUserRoleId();

			if (userInfoStatusId != STATUS_APPROVED) {

				request.setAttribute(MESSAGE_TO_ERROR_PAGE, ERROR_CAUSE_NOT_APPROVED);
				try {
					request.getRequestDispatcher(ERROR_PAGE_PATH).forward(request, response);

				} catch (ServletException | IOException e) {
					logger.error(e);
					response.sendRedirect(request.getRequestURI() + COMMAND_GO_TO_ERROR_PAGE
							+ MESSAGE_GO_TO_ERROR_PAGE_INTERNAL_SERVER_ERROR);
				}

			} else {
				UserInfoService userService = ServiceProvider.getInstance().getUserInfoService();

				// ЕСЛИ ДЕКАН
				if (userRoleId == 3) {

					try {
						DepartmentStaff departmentStaff;
						departmentStaff = userService.findFacultyStaffForDean(userInfo);

						request.setAttribute(REQUEST_PARAMETER_STAFF, departmentStaff);
						
						request.getRequestDispatcher(STAFF_PAGE_PATH).forward(request, response);

					} catch (ServiceException | ServletException e) {
						logger.error(e);
						response.sendRedirect(request.getRequestURI() + COMMAND_GO_TO_ERROR_PAGE
								+ MESSAGE_GO_TO_ERROR_PAGE_INTERNAL_SERVER_ERROR);
					}
				}

				// ЕСЛИ РЕКТОР
				else {
					try {
						List<DepartmentStaff> allStaff;
						allStaff = userService.findUnivercityStaffForRector(userInfo);

						request.setAttribute(REQUEST_PARAMETER_ALL_STAFF, allStaff);

						request.getRequestDispatcher(STAFF_PAGE_PATH).forward(request, response);

					} catch (ServiceException | ServletException e) {
						logger.error(e);
						response.sendRedirect(request.getRequestURI() + COMMAND_GO_TO_ERROR_PAGE
								+ MESSAGE_GO_TO_ERROR_PAGE_INTERNAL_SERVER_ERROR);
					}
				}
			}

		} else {

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
