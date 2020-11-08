package by.epamtc.facultative.controller.command.impl;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import by.epamtc.facultative.bean.Course;
import by.epamtc.facultative.bean.UserInfo;
import by.epamtc.facultative.controller.command.Command;
import by.epamtc.facultative.service.CourseInfoService;
import by.epamtc.facultative.service.ServiceProvider;
import by.epamtc.facultative.service.exception.ServiceException;

public class GoToCreateCoursePage implements Command {

	private static final Logger logger = Logger.getLogger(GoToCreateCoursePage.class);

	private final String COMMAND_GO_TO_ERROR_PAGE = "?command=go_to_error_page";
	private final String MESSAGE_GO_TO_ERROR_PAGE_INTERNAL_SERVER_ERROR = "&message=server_error";
	private final String MESSAGE_GO_TO_ERROR_PAGE_NOT_AUTHORIZED = "&message=user_not_authorized";

	private final String SESSION_ATTRIBUTE_LOGIN = "userLogin";
	private final String SESSION_ATTRIBUTE_BEAN = "bean";
	private final String REQUEST_ATTRIBUTE_LIST_COURSES = "listOfCourses";

	private final int LECTURER_STATUS_APPROVED = 2;

	private final String CREATE_COURSE_PAGE_PATH = "WEB-INF/jsp/create-new-run-course-page.jsp";
	private final String ERROR_PAGE_PATH = "WEB-INF/jsp/error-page.jsp";
	private final String ERROR_CAUSE_NOT_APPROVED = "user_not_approved";
	private final String MESSAGE_TO_ERROR_PAGE = "errorMessage";
	private final String MESSAGE_TO_ERROR_PAGE_NOT_RIGHTS = "&message=not_enough_rights_for_procedure";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {

		HttpSession session = request.getSession();
		String login = (String) session.getAttribute(SESSION_ATTRIBUTE_LOGIN);

		if (login != null) {

			UserInfo userPageInfo;
			int lecturerStatusId;

			userPageInfo = (UserInfo) session.getAttribute(SESSION_ATTRIBUTE_BEAN);

			lecturerStatusId = userPageInfo.getUserStatusId();

			if (lecturerStatusId != LECTURER_STATUS_APPROVED) {

				request.setAttribute(MESSAGE_TO_ERROR_PAGE, ERROR_CAUSE_NOT_APPROVED);

				try {
					request.getRequestDispatcher(ERROR_PAGE_PATH).forward(request, response);

				} catch (ServletException | IOException e) {
					response.sendRedirect(
							request.getRequestURI() + COMMAND_GO_TO_ERROR_PAGE + MESSAGE_TO_ERROR_PAGE_NOT_RIGHTS);
				}
			} else {

				int userDeparttmentId;
				List<Course> availableCoursesForDepartment;

				userDeparttmentId = userPageInfo.getUserFacultyId();
				availableCoursesForDepartment = null;

				CourseInfoService courseInfoProvider = ServiceProvider.getInstance().getCourseInfoService();

				try {
					availableCoursesForDepartment = courseInfoProvider
							.findAvailableCoursesForDepartment(userDeparttmentId);

					request.setAttribute(REQUEST_ATTRIBUTE_LIST_COURSES, availableCoursesForDepartment);
					request.getRequestDispatcher(CREATE_COURSE_PAGE_PATH).forward(request, response);

				} catch (ServletException | IOException | ServiceException e) {

					logger.error(e);
					response.sendRedirect(request.getRequestURI() + COMMAND_GO_TO_ERROR_PAGE
							+ MESSAGE_GO_TO_ERROR_PAGE_INTERNAL_SERVER_ERROR);
				}
			}
		} else {
			try {
				response.sendRedirect(
						request.getRequestURI() + COMMAND_GO_TO_ERROR_PAGE + MESSAGE_GO_TO_ERROR_PAGE_NOT_AUTHORIZED);

			} catch (IOException e) {

				logger.error(e);
				response.sendRedirect(request.getRequestURI() + COMMAND_GO_TO_ERROR_PAGE
						+ MESSAGE_GO_TO_ERROR_PAGE_INTERNAL_SERVER_ERROR);
			}

		}

	}

}
