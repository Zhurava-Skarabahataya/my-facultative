package by.epamtc.facultative.controller.command.impl;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.epamtc.facultative.bean.UserInfo;
import by.epamtc.facultative.controller.command.Command;
import by.epamtc.facultative.service.UpdateUserInfoService;

public class EditUserInfoCommand implements Command {

	private static final String PARAMETER_FIRST_NAME = "userFirstName";
	private static final String PARAMETER_SECOND_NAME = "userSecondName";
	private static final String PARAMETER_PATRONYMIC = "userPatronymic";
	private static final String PARAMETER_USER_LOGIN = "userlogin";
	private static final String PARAMETER_PASSWORD = "password";
	private static final String PARAMETER_USER_EMAL = "user_email";
	private static final String PARAMETER_FACULTY = "faculty";
	private static final String PARAMETER_ROLE = "role";

	private static final String PARAMETER_COMMAND_GO_TO_USER_PAGE = "?command=go_to_user_page";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();

		UserInfo userPageInfo = (UserInfo) session.getAttribute("bean");

		if (request.getParameter(PARAMETER_FIRST_NAME) != null) {
			String userFirstName = request.getParameter(PARAMETER_FIRST_NAME);
			userPageInfo.setUserFirstName(userFirstName);
		}
		if (request.getParameter(PARAMETER_SECOND_NAME) != null) {
			String userSecondName = request.getParameter(PARAMETER_SECOND_NAME);
			userPageInfo.setUserSecondName(userSecondName);
		}

		if (request.getParameter(PARAMETER_PATRONYMIC) != null) {
			String userPatronymic = request.getParameter(PARAMETER_PATRONYMIC);
			userPageInfo.setUserPatronymic(userPatronymic);
		}
		
		if (request.getParameter(PARAMETER_FACULTY) != null) {
			Integer userFacultyId = Integer.parseInt(request.getParameter(PARAMETER_FACULTY));
			userPageInfo.setUserFacultyId(userFacultyId);
		}
		if (request.getParameter("userAdress") != null) {
			String userAdress = request.getParameter("userAdress");
			userPageInfo.setUserAdress(userAdress);
		}
		if (request.getParameter("userPhone") != null) {
			String userPhone = request.getParameter("userPhone");
			userPageInfo.setUserPhone(userPhone);
		}
		if (request.getParameter("userDateOfBirth") != null) {
			try {
				LocalDate userDateOfBirth = LocalDate.parse(request.getParameter("userDateOfBirth"));
				userPageInfo.setUserDateOfBirth(userDateOfBirth);
			} catch (DateTimeParseException e) {
				e.printStackTrace();
			}
		}

		UpdateUserInfoService updateUserInfoService = UpdateUserInfoService.getInstance();
		updateUserInfoService.update(userPageInfo);
		try {
			response.sendRedirect(request.getRequestURI() + PARAMETER_COMMAND_GO_TO_USER_PAGE);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
