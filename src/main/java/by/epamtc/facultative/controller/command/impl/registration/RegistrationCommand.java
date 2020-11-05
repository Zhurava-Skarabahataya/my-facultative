package by.epamtc.facultative.controller.command.impl.registration;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import by.epamtc.facultative.bean.UserInfo;
import by.epamtc.facultative.bean.UserRegistrationInfo;
import by.epamtc.facultative.controller.command.Command;
import by.epamtc.facultative.service.UserInfoService;
import by.epamtc.facultative.service.exception.ServiceException;
import by.epamtc.facultative.service.impl.RegistrationServiceImpl;

public class RegistrationCommand implements Command {

	private final String PARAMETER_FIRST_NAME = "firstName";
	private final String PARAMETER_SECOND_NAME = "secondName";
	private final String PARAMETER_PATRONYMIC = "patronymic";
	private final String PARAMETER_USER_LOGIN = "userlogin";
	private final String PARAMETER_PASSWORD = "password";
	private final String PARAMETER_USER_EMAL = "user_email";
	private final String PARAMETER_FACULTY = "faculty";
	private final String PARAMETER_POSITION = "position";
	private final String PARAMETER_MESSAGE_FROM_SERVLET = "messageFromServlet";

	private final String SESSION_ATTRIBUTE_LOGIN = "userLogin";

	private final String COMMAND_GO_TO_SUCCESS_PAGE = "?command=go_to_success_page_command";
	private final String COMMAND_GO_TO_REGISTRATION_PAGE = "?command=go_to_registration_page";
	private final String COMMAND_GO_ERROR_PAGE = "?command=go_to_error_page";
	private final String MESSAGE_GO_TO_SUCCESS_PAGE = "&message=success_registration";
	private final String MESSAGE_TO_REGISTRTAION_PAGE = "&message=";
	private final String MESSAGE_GO_TO_ERROR_PAGE_REGISTRATION = "&message=promblems_with_registration";
	private final String MESSAGE_GO_TO_ERROR_PAGE_ALREADY_AUTH = "&message=user_already_authorized";
	
	private final String REQUEST_ATTRIBUTE_BEAN = "bean";


	private static final Logger logger = Logger.getLogger(RegistrationCommand.class);

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession();
		String userLogin = (String) session.getAttribute(SESSION_ATTRIBUTE_LOGIN);

		if (userLogin != null) {
			// Значит, юзер уже авторизован
			try {
				response.sendRedirect(
						request.getRequestURI() + COMMAND_GO_ERROR_PAGE + MESSAGE_GO_TO_ERROR_PAGE_ALREADY_AUTH);
			} catch (IOException e) {
				// ОБРТАББ
				e.printStackTrace();
			}

		} else {

			UserRegistrationInfo userRegistrationInfo = new UserRegistrationInfo();

			RegistrationServiceImpl registrationService = RegistrationServiceImpl.getInstance();

			userLogin = request.getParameter(PARAMETER_USER_LOGIN);
			String firstName = request.getParameter(PARAMETER_FIRST_NAME);
			String secondName = request.getParameter(PARAMETER_SECOND_NAME);
			String patronymic = request.getParameter(PARAMETER_PATRONYMIC);
			System.out.println("userLoginFromRegForm " + userLogin);
			String password = request.getParameter(PARAMETER_PASSWORD);
			String email = request.getParameter(PARAMETER_USER_EMAL);

			Integer faculty = Integer.parseInt(request.getParameter(PARAMETER_FACULTY));
			Integer position = Integer.parseInt(request.getParameter(PARAMETER_POSITION));

			userRegistrationInfo.setFirstName(firstName);
			userRegistrationInfo.setSecondName(secondName);
			userRegistrationInfo.setPatromynic(patronymic);
			userRegistrationInfo.setUserEmail(email);
			userRegistrationInfo.setUserLogin(userLogin);
			userRegistrationInfo.setUserPassword(password);

			userRegistrationInfo.setDepartmentID(faculty);
			userRegistrationInfo.setUserRoleID(position);

			boolean registrationSuccess = true;

			String messageFromRegistrationService = null;

			try {
				messageFromRegistrationService = registrationService.execute(userRegistrationInfo);

				if (messageFromRegistrationService != null) {
					registrationSuccess = false;
				}

			} catch (ServiceException e1) {
				try {
					e1.printStackTrace();

					response.sendRedirect(
							request.getRequestURI() + COMMAND_GO_ERROR_PAGE + MESSAGE_GO_TO_ERROR_PAGE_REGISTRATION);

				} catch (IOException e) {
					// jnklscl
				}

			}

			if (registrationSuccess) {

				session.setAttribute(SESSION_ATTRIBUTE_LOGIN, userLogin);
				
				System.out.println("userLoginSend To session " + userLogin);

				
				UserInfo userInfo = new UserInfo();
				userInfo.setUserLogin(userLogin);

				UserInfoService userPageInfoProviderService = UserInfoService.getInstance();
				userPageInfoProviderService.findUserInfo(userInfo);

				session.setAttribute(REQUEST_ATTRIBUTE_BEAN, userInfo);

				try {
					response.sendRedirect(
							request.getRequestURI() + COMMAND_GO_TO_SUCCESS_PAGE + MESSAGE_GO_TO_SUCCESS_PAGE);
				} catch (IOException e) {
					e.printStackTrace();
				}

			} else {

				try {
					System.out.println(COMMAND_GO_TO_REGISTRATION_PAGE
							+ MESSAGE_TO_REGISTRTAION_PAGE + messageFromRegistrationService);

					response.sendRedirect(request.getRequestURI() + COMMAND_GO_TO_REGISTRATION_PAGE
							+ MESSAGE_TO_REGISTRTAION_PAGE + messageFromRegistrationService);
//					request.setAttribute(PARAMETER_MESSAGE_FROM_SERVLET, messageFromRegistrationService);
//					request.getRequestDispatcher(REGISTRATION_PAGE_PATH).forward(request, response);

				}  catch (IOException e) {
					// ОБРАБ

					e.printStackTrace();
				}
			}
		}

	}

}
