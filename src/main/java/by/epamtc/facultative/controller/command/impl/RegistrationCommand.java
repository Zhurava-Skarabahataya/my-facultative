package by.epamtc.facultative.controller.command.impl;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.epamtc.facultative.bean.UserRegistrationInfo;
import by.epamtc.facultative.controller.command.Command;
import by.epamtc.facultative.service.RegistrationNewUserService;

public class RegistrationCommand implements Command{
	
	private static final String PARAMETER_FIRST_NAME = "firstName";
	private static final String PARAMETER_SECOND_NAME = "secondName";
	private static final String PARAMETER_PATRONYMIC= "patronymic";
	private static final String PARAMETER_USER_LOGIN  = "userlogin";
	private static final String PARAMETER_PASSWORD = "password";
	private static final String PARAMETER_USER_EMAL = "user_email";
	private static final String PARAMETER_FACULTY = "faculty";
	private static final String PARAMETER_POSITION = "position";
	
	private static final String SESSION_ATTRIBUTE_LOGIN = "userLogin";
	private static final String REQUEST_ATTRIBUTE_IS_LOGGED = "isLogged";
	
	private static final String COMMAND_GO_TO_SUCCESS_PAGE = "?command=go_to_registration_success_page";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		
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
		
		rService.execute(userRegistrationInfo);
		
		HttpSession session = request.getSession();
		
		session.setAttribute(SESSION_ATTRIBUTE_LOGIN, userlogin);
		session.setAttribute(REQUEST_ATTRIBUTE_IS_LOGGED, true);
		
		try {
			response.sendRedirect(request.getRequestURI()+ COMMAND_GO_TO_SUCCESS_PAGE);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	
}
