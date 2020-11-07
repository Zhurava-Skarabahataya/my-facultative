package by.epamtc.facultative.controller.command.impl;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import by.epamtc.facultative.controller.command.Command;
import by.epamtc.facultative.service.ServiceProvider;
import by.epamtc.facultative.service.StudentStatusService;
import by.epamtc.facultative.service.exception.ServiceException;

public class ApproveStudentForCourseCommand implements Command {

	private static final Logger logger = Logger.getLogger(ApproveStudentForCourseCommand.class);
	
	private final String SESSION_PARAMETER_USER_LOGIN = "userLogin";
	private final String REQUEST_PARAMETER_RUN_COURSE_ID = "runCourseId";
	private final String REQUEST_PARAMETER_STUDENT_ID = "studentId";

	private final String COMMAND_GO_TO_SUCCESS_PAGE = "?command=go_to_success_page_command";
	private final String COMMAND_GO_TO_ERROR_PAGE = "?command=go_to_error_page";

	private final String MESSAGE_GO_TO_SUCCESS_PAGE = "&message=success_student_approved";
	private final String MESSAGE_GO_TO_ERROR_PAGE_NOT_AUTHORIZED = "&message=user_not_authorized";
	private final String MESSAGE_GO_TO_ERROR_PAGE_INTERNAL_SERVER_ERROR = "&message=server_error";


	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {

		HttpSession session = request.getSession();
		String userLogin;
		userLogin = (String) session.getAttribute(SESSION_PARAMETER_USER_LOGIN);

		if (userLogin != null) {
			int runCourseId;
			int studentId ;

			runCourseId = Integer.parseInt(request.getParameter(REQUEST_PARAMETER_RUN_COURSE_ID));
			studentId = Integer.parseInt(request.getParameter(REQUEST_PARAMETER_STUDENT_ID));

			StudentStatusService studentStatusService = ServiceProvider.getInstance().getStudentStatusService();

			try {
				studentStatusService.approveStudentOnCourse(studentId, runCourseId);

				response.sendRedirect(
						request.getRequestURI() + COMMAND_GO_TO_SUCCESS_PAGE + MESSAGE_GO_TO_SUCCESS_PAGE);

			} catch (IOException | ServiceException e) {
				logger.error(e);
				response.sendRedirect(request.getRequestURI() + COMMAND_GO_TO_ERROR_PAGE
						+ MESSAGE_GO_TO_ERROR_PAGE_INTERNAL_SERVER_ERROR);
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
