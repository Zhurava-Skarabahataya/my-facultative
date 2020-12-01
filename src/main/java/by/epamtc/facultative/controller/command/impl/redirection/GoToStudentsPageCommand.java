package by.epamtc.facultative.controller.command.impl.redirection;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import by.epamtc.facultative.bean.Department;
import by.epamtc.facultative.bean.UserInfo;
import by.epamtc.facultative.controller.command.Command;
import by.epamtc.facultative.service.DepartmentInfoService;
import by.epamtc.facultative.service.ServiceProvider;
import by.epamtc.facultative.service.exception.ServiceException;

public class GoToStudentsPageCommand implements Command {

	private static final Logger logger = Logger.getLogger(GoToStudentsPageCommand.class);

	private final String COMMAND_GO_TO_ERROR_PAGE = "?command=go_to_error_page";
	private final String MESSAGE_GO_TO_ERROR_PAGE_INTERNAL_SERVER_ERROR = "&message=server_error";
	private final String MESSAGE_TO_ERROR_PAGE_NOT_AUTHORIZED = "&message=user_not_authorized";

	private final String STUDENTS_PAGE_PATH = "WEB-INF/jsp/students-page.jsp";
	private final String SESSION_PARAMETER_USER_LOGIN = "userLogin";
	private final String SESSION_PARAMETER_BEAN = "bean";
	private final String REQUEST_PARAMETER_STUDENTS = "students";
	private final String REQUEST_PARAMETER_ALL_STUDENTS = "allStudents";

	private final String ERROR_PAGE_PATH = "WEB-INF/jsp/error-page.jsp";
	private final String MESSAGE_TO_ERROR_PAGE = "errorMessage";
	private final String ERROR_CAUSE_NOT_APPROVED = "user_not_approved";

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

				DepartmentInfoService userService = ServiceProvider.getInstance().getDepartmentInfoService();

				// ЕСЛИ ДЕКАН
				if (userRoleId == 3) {
					try {
						int departmentId;
						List<UserInfo> students;

						departmentId = userInfo.getUserFacultyId();
						students = userService.findStudentsOfDepartment(departmentId);

						request.setAttribute(REQUEST_PARAMETER_STUDENTS, students);

						request.getRequestDispatcher(STUDENTS_PAGE_PATH).forward(request, response);

					} catch (ServiceException | ServletException | IOException e) {
						logger.error(e);

						response.sendRedirect(request.getRequestURI() + COMMAND_GO_TO_ERROR_PAGE
								+ MESSAGE_GO_TO_ERROR_PAGE_INTERNAL_SERVER_ERROR);
					}
				}

				// ЕСЛИ РЕКТОР
				else {
					try {
						List<Department> studentsInDepartments;
						studentsInDepartments = userService.findStudentsOfAllDepartments();

						request.setAttribute(REQUEST_PARAMETER_ALL_STUDENTS, studentsInDepartments);

						request.getRequestDispatcher(STUDENTS_PAGE_PATH).forward(request, response);

					} catch (ServiceException | ServletException | IOException e) {
						logger.error(e);

						response.sendRedirect(request.getRequestURI() + COMMAND_GO_TO_ERROR_PAGE
								+ MESSAGE_GO_TO_ERROR_PAGE_INTERNAL_SERVER_ERROR);
					}
				}
			}

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
