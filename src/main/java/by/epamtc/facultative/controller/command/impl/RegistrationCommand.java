package by.epamtc.facultative.controller.command.impl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import by.epamtc.facultative.bean.UserRegistrationInfo;
import by.epamtc.facultative.controller.command.Command;
import by.epamtc.facultative.service.RegistrationNewUserService;
import by.epamtc.facultative.service.exception.ServiceException;

public class RegistrationCommand implements Command {

	private static final String PARAMETER_FIRST_NAME = "firstName";
	private static final String PARAMETER_SECOND_NAME = "secondName";
	private static final String PARAMETER_PATRONYMIC = "patronymic";
	private static final String PARAMETER_USER_LOGIN = "userlogin";
	private static final String PARAMETER_PASSWORD = "password";
	private static final String PARAMETER_USER_EMAL = "user_email";
	private static final String PARAMETER_FACULTY = "faculty";
	private static final String PARAMETER_POSITION = "position";

	private static final String SESSION_ATTRIBUTE_LOGIN = "userLogin";
	private static final String REQUEST_ATTRIBUTE_IS_LOGGED = "isLogged";

	private static final String COMMAND_GO_TO_SUCCESS_PAGE = "?command=go_to_registration_success_page";
	private static final String COMMAND_GO_ERROR_PAGE = "?command=go_to_error_page";

	private static final String REGISTRATION_PAGE_PATH = "WEB-INF/jsp/registration-page.jsp";

	private static final Logger logger = Logger.getLogger(RegistrationCommand.class);

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {

		System.out.println("МЫ В КОНТРЛЛЛЕРЕ");

		
		UserRegistrationInfo userRegistrationInfo = new UserRegistrationInfo();

		RegistrationNewUserService rService = RegistrationNewUserService.getInstance();

		String firstName = request.getParameter(PARAMETER_FIRST_NAME);
		String secondName = request.getParameter(PARAMETER_SECOND_NAME);
		String patronymic = request.getParameter(PARAMETER_PATRONYMIC);
		String userlogin = request.getParameter(PARAMETER_USER_LOGIN);
		String password = request.getParameter(PARAMETER_PASSWORD);
		String email = request.getParameter(PARAMETER_USER_EMAL);

		Integer faculty = Integer.parseInt(request.getParameter(PARAMETER_FACULTY));
		Integer position = Integer.parseInt(request.getParameter(PARAMETER_POSITION));

		userRegistrationInfo.setFirstName(firstName);
		userRegistrationInfo.setSecondName(secondName);
		userRegistrationInfo.setPatromynic(patronymic);
		userRegistrationInfo.setUserEmail(email);
		userRegistrationInfo.setUserLogin(userlogin);
		userRegistrationInfo.setUserPassword(password);

		userRegistrationInfo.setDepartmentID(faculty);
		userRegistrationInfo.setUserRoleID(position);

		boolean registrationSuccess = true;
		String message = null;

		try {
			message = rService.execute(userRegistrationInfo);

			if (message != null) {
				registrationSuccess = false;
			}

		} catch (ServiceException e1) {
			try {
				response.sendRedirect(request.getRequestURI() + COMMAND_GO_ERROR_PAGE);

			} catch (IOException e) {
				// jnklscl
			}

		}
		if (registrationSuccess) {
			HttpSession session = request.getSession();

			session.setAttribute(SESSION_ATTRIBUTE_LOGIN, userlogin);
			session.setAttribute(REQUEST_ATTRIBUTE_IS_LOGGED, true);
			
			System.out.println("userlogin " + userlogin);
			System.out.println("setted REG" + SESSION_ATTRIBUTE_LOGIN + " " + session.getAttribute(SESSION_ATTRIBUTE_LOGIN));
			System.out.println("setted REG " + REQUEST_ATTRIBUTE_IS_LOGGED +  " " + session.getAttribute(REQUEST_ATTRIBUTE_IS_LOGGED));
			try {
				response.sendRedirect(request.getRequestURI() + COMMAND_GO_TO_SUCCESS_PAGE);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			try {
				request.setAttribute("messageFromServlet", message);
				System.out.println(message);
				request.getRequestDispatcher(REGISTRATION_PAGE_PATH).forward(request, response);
			} catch (ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}
