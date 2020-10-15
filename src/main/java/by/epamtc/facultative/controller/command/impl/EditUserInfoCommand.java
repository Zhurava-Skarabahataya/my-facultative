package by.epamtc.facultative.controller.command.impl;

import java.time.LocalDate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.epamtc.facultative.bean.UserPageInfo;
import by.epamtc.facultative.controller.command.Command;

public class EditUserInfoCommand implements Command{
	
	private static final String PARAMETER_FIRST_NAME = "userFirstName";
	private static final String PARAMETER_SECOND_NAME = "userSecondName";
	private static final String PARAMETER_PATRONYMIC = "userPatronymic";
	private static final String PARAMETER_USER_LOGIN = "userlogin";
	private static final String PARAMETER_PASSWORD = "password";
	private static final String PARAMETER_USER_EMAL = "user_email";
	private static final String PARAMETER_FACULTY = "faculty";
	private static final String PARAMETER_ROLE = "role";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		
		HttpSession session = request.getSession();

		
		UserPageInfo userPageInfo = (UserPageInfo) session.getAttribute("bean");
		
		String userFirstName = request.getParameter(PARAMETER_FIRST_NAME);
		String userSecondName = request.getParameter(PARAMETER_SECOND_NAME);
		String userPatronymic = request.getParameter(PARAMETER_PATRONYMIC);
		Integer userRoleId = Integer.parseInt(request.getParameter(PARAMETER_ROLE));
		Integer userFacultyId = Integer.parseInt(request.getParameter(PARAMETER_FACULTY));
		String userAdress = request.getParameter("userAdress");
		String userPhone = request.getParameter("userPhone");
		
		LocalDate userDateOfBirth = LocalDate.parse(request.getParameter("userDateOfBirth"));
		
		
	}

}
