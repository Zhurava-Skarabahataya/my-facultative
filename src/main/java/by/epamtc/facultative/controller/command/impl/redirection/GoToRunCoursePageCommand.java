package by.epamtc.facultative.controller.command.impl.redirection;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import by.epamtc.facultative.bean.RunnedCourse;
import by.epamtc.facultative.bean.UserInfo;
import by.epamtc.facultative.controller.command.Command;
import by.epamtc.facultative.service.CourseInfoService;
import by.epamtc.facultative.service.ServiceProvider;
import by.epamtc.facultative.service.exception.ServiceException;

public class GoToRunCoursePageCommand implements Command {

	private static final Logger logger = Logger.getLogger(GoToRunCoursePageCommand.class);

	private final String COMMAND_GO_TO_ERROR_PAGE = "?command=go_to_error_page";
	private final String MESSAGE_GO_TO_ERROR_PAGE_INTERNAL_SERVER_ERROR = "&message=server_error";
	private final String RUN_COURSE_PAGE_PATH = "WEB-INF/jsp/run-course-page.jsp";

	private final String SESSION_PARAMETER_USER_LOGIN = "userLogin";
	private final String SESSION_PARAMETER_BEAN = "bean";
	private final String REQUEST_PARAMETER_RUN_COURSE_ID = "run_course_id";
	private final String REQUEST_PARAMETER_RUN_COURSE = "run_course";
	private final String REQUEST_PARAMETER_APPROVAL_STATUS_ID = "user_approval_status_id";
	private final String REQUEST_PARAMETER_MARK = "user_mark";

	private final int USER_ROLE_STUDENT = 1;

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession();
		String userLogin;
		userLogin = (String) session.getAttribute(SESSION_PARAMETER_USER_LOGIN);

		int runCourseId;
		RunnedCourse runCourse;

		runCourseId = Integer.parseInt(request.getParameter(REQUEST_PARAMETER_RUN_COURSE_ID));

		runCourse = null;

		CourseInfoService courseInfoProvider = ServiceProvider.getInstance().getCourseInfoService();
		try {
			runCourse = courseInfoProvider.findRunCourseById(runCourseId);
			request.setAttribute(REQUEST_PARAMETER_RUN_COURSE, runCourse);

			if (userLogin != null) {

				UserInfo userInfo;
				int userId;
				int userRoleId;

				userInfo = (UserInfo) session.getAttribute(SESSION_PARAMETER_BEAN);
				userId = userInfo.getUserId();
				userRoleId = userInfo.getUserRoleId();
				if (userRoleId == USER_ROLE_STUDENT) {

					int userStatusOnCourse;
					int studentMarkOnCourse;

					userStatusOnCourse = courseInfoProvider.getUserOnCourseApprovalStatusId(userId, runCourse);

					studentMarkOnCourse = courseInfoProvider.getUserMarkOnCourse(userId, runCourse);

					request.setAttribute(REQUEST_PARAMETER_APPROVAL_STATUS_ID, userStatusOnCourse);
					request.setAttribute(REQUEST_PARAMETER_MARK, studentMarkOnCourse);
				}
			}

			request.getRequestDispatcher(RUN_COURSE_PAGE_PATH).forward(request, response);
			
		} catch (ServiceException | ServletException e) {
			logger.error(e);
			response.sendRedirect(request.getRequestURI() + COMMAND_GO_TO_ERROR_PAGE
					+ MESSAGE_GO_TO_ERROR_PAGE_INTERNAL_SERVER_ERROR);
		}

	}

}
