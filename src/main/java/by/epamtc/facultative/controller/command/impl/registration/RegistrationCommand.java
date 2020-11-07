package by.epamtc.facultative.controller.command.impl.registration;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import by.epamtc.facultative.bean.UserInfo;
import by.epamtc.facultative.bean.UserRegistrationInfo;
import by.epamtc.facultative.controller.command.Command;
import by.epamtc.facultative.service.RegistrationService;
import by.epamtc.facultative.service.ServiceProvider;
import by.epamtc.facultative.service.UserInfoService;
import by.epamtc.facultative.service.exception.ServiceException;

public class RegistrationCommand implements Command {

	private final String PARAMETER_FIRST_NAME = "firstName";
	private final String PARAMETER_SECOND_NAME = "secondName";
	private final String PARAMETER_PATRONYMIC = "patronymic";
	private final String PARAMETER_USER_LOGIN = "userlogin";
	private final String PARAMETER_PASSWORD = "password";
	private final String PARAMETER_USER_EMAL = "user_email";
	private final String PARAMETER_FACULTY = "faculty";
	private final String PARAMETER_POSITION = "possition";

	private final String SESSION_ATTRIBUTE_LOGIN = "userLogin";

	private final String COMMAND_GO_TO_SUCCESS_PAGE = "?command=go_to_success_page_command";
	private final String COMMAND_GO_TO_REGISTRATION_PAGE = "?command=go_to_registration_page";
	private final String COMMAND_GO_ERROR_PAGE = "?command=go_to_error_page";
	private final String MESSAGE_GO_TO_SUCCESS_PAGE = "&message=success_registration";
	private final String MESSAGE_TO_REGISTRTAION_PAGE = "&message=";
	private final String MESSAGE_GO_TO_ERROR_PAGE_REGISTRATION = "&message=promblems_with_registration";
	private final String MESSAGE_GO_TO_ERROR_PAGE_ALREADY_AUTH = "&message=user_already_authorized";
	private final String MESSAGE_GO_TO_ERROR_PAGE_INTERNAL_SERVER_ERROR = "&message=server_error";

	private final String REQUEST_ATTRIBUTE_BEAN = "bean";

	private static final Logger logger = Logger.getLogger(RegistrationCommand.class);

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {

		HttpSession session = request.getSession();
		String userLogin = (String) session.getAttribute(SESSION_ATTRIBUTE_LOGIN);

		if (userLogin != null) {
			try {
				response.sendRedirect(
						request.getRequestURI() + COMMAND_GO_ERROR_PAGE + MESSAGE_GO_TO_ERROR_PAGE_ALREADY_AUTH);

			} catch (IOException e) {

				logger.error(e);
				response.sendRedirect(request.getRequestURI() + COMMAND_GO_ERROR_PAGE
						+ MESSAGE_GO_TO_ERROR_PAGE_INTERNAL_SERVER_ERROR);
			}

		} else {

			UserRegistrationInfo userRegistrationInfo = new UserRegistrationInfo();

			RegistrationService registrationService = ServiceProvider.getInstance().getRegistrationService();

			userLogin = request.getParameter(PARAMETER_USER_LOGIN);

			String firstName;
			String secondName;
			String patronymic;
			String password;
			String email;

			int faculty;
			int position;

			firstName = request.getParameter(PARAMETER_FIRST_NAME);
			secondName = request.getParameter(PARAMETER_SECOND_NAME);
			patronymic = request.getParameter(PARAMETER_PATRONYMIC);
			password = request.getParameter(PARAMETER_PASSWORD);
			email = request.getParameter(PARAMETER_USER_EMAL);

			faculty = Integer.parseInt(request.getParameter(PARAMETER_FACULTY));
			position = Integer.parseInt(request.getParameter(PARAMETER_POSITION));

			userRegistrationInfo.setFirstName(firstName);
			userRegistrationInfo.setSecondName(secondName);
			userRegistrationInfo.setPatromynic(patronymic);
			userRegistrationInfo.setUserEmail(email);
			userRegistrationInfo.setUserLogin(userLogin);
			userRegistrationInfo.setUserPassword(password);

			userRegistrationInfo.setDepartmentID(faculty);
			userRegistrationInfo.setUserRoleID(position);

			boolean registrationSuccess = false;

			String messageFromRegistrationService = null;

			try {
				messageFromRegistrationService = registrationService.execute(userRegistrationInfo);

				if (messageFromRegistrationService != null) {
					registrationSuccess = false;
				} else {
					registrationSuccess = true;
				}

			} catch (ServiceException e) {

				response.sendRedirect(
						request.getRequestURI() + COMMAND_GO_ERROR_PAGE + MESSAGE_GO_TO_ERROR_PAGE_REGISTRATION);
			}

			if (registrationSuccess) {

				UserInfo userInfo = new UserInfo();
				userInfo.setUserLogin(userLogin);

				UserInfoService userInforService = ServiceProvider.getInstance().getUserInfoService();
				try {
					userInforService.findUserInfo(userInfo);
				} catch (ServiceException e1) {

				}

				session.setAttribute(SESSION_ATTRIBUTE_LOGIN, userLogin);
				session.setAttribute(REQUEST_ATTRIBUTE_BEAN, userInfo);

				response.sendRedirect(
						request.getRequestURI() + COMMAND_GO_TO_SUCCESS_PAGE + MESSAGE_GO_TO_SUCCESS_PAGE);

			} else {
				response.sendRedirect(request.getRequestURI() + COMMAND_GO_TO_REGISTRATION_PAGE
						+ MESSAGE_TO_REGISTRTAION_PAGE + messageFromRegistrationService);

			}
		}

	}

}
