package by.epamtc.facultative.controller.command.impl;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.bind.ValidationException;

import org.apache.log4j.Logger;

import by.epamtc.facultative.bean.UserInfo;
import by.epamtc.facultative.controller.command.Command;
import by.epamtc.facultative.service.ServiceProvider;
import by.epamtc.facultative.service.UpdateUserInfoService;
import by.epamtc.facultative.service.exception.ServiceException;

public class EditUserInfoCommand implements Command {

	private static final Logger logger = Logger.getLogger(EditUserInfoCommand.class);

	private final String SESSION_PARAMETER_USER_LOGIN = "userLogin";
	private final String SESSION_PARAMETER_BEAN = "bean";

	private final String PARAMETER_FIRST_NAME = "userFirstName";
	private final String PARAMETER_SECOND_NAME = "userSecondName";
	private final String PARAMETER_PATRONYMIC = "userPatronymic";
	private final String PARAMETER_FACULTY = "faculty";
	private final String PARAMETER_ADRESS = "userAdress";
	private final String PARAMETER_PHONE = "userPhone";
	private final String PARAMETER_DATE_OF_BIRTH = "userDateOfBirth";

	private final String PARAMETER_COMMAND_GO_TO_USER_PAGE = "?command=go_to_user_page";
	private final String COMMAND_GO_TO_ERROR_PAGE = "?command=go_to_error_page";
	private final String MESSAGE_GO_TO_ERROR_PAGE_NOT_AUTHORIZED = "&message=user_not_authorized";

	private final String COMMAND_GO_TO_USER_PAGE = "?command=go_to_user_page";
	private final String MESSAGE_GO_TO_ERROR_PAGE_INTERNAL_SERVER_ERROR = "&message=server_error";
	private final String MESSAGE_TO_ERROR_PAGE_WRONG_DATE_FORMAT = "&message=wrong_date_format";
	private final String MESSAGE_TO_ERROR_PAGE_INVALID_USER_DATA = "&message=invalid_user_data";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {

		HttpSession session = request.getSession();

		String userLogin;
		userLogin = (String) session.getAttribute(SESSION_PARAMETER_USER_LOGIN);

		if (userLogin != null) {

			UserInfo userPageInfo;
			userPageInfo = (UserInfo) session.getAttribute(SESSION_PARAMETER_BEAN);

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
			if (request.getParameter(PARAMETER_ADRESS) != null) {
				String userAdress = request.getParameter(PARAMETER_ADRESS);
				userPageInfo.setUserAdress(userAdress);
			}
			if (request.getParameter(PARAMETER_PHONE) != null) {
				String userPhone = request.getParameter(PARAMETER_PHONE);
				userPageInfo.setUserPhone(userPhone);
			}
			if (!request.getParameter(PARAMETER_DATE_OF_BIRTH).isEmpty()) {
				try {
					LocalDate userDateOfBirth = LocalDate.parse(request.getParameter(PARAMETER_DATE_OF_BIRTH));
					userPageInfo.setUserDateOfBirth(userDateOfBirth);
				} catch (DateTimeParseException e) {

					response.sendRedirect(request.getRequestURI() + COMMAND_GO_TO_ERROR_PAGE
							+ MESSAGE_TO_ERROR_PAGE_WRONG_DATE_FORMAT);
				}
			}

			UpdateUserInfoService updateUserInfoService = ServiceProvider.getInstance().getUpdateUserInfoService();

			try {
				updateUserInfoService.update(userPageInfo);

				response.sendRedirect(request.getRequestURI() + PARAMETER_COMMAND_GO_TO_USER_PAGE);
				
			} catch (ServiceException | IOException e) {
				logger.error(e);
				response.sendRedirect(request.getRequestURI() + COMMAND_GO_TO_ERROR_PAGE
						+ MESSAGE_GO_TO_ERROR_PAGE_INTERNAL_SERVER_ERROR);

			} catch (ValidationException e) {
				logger.error(e);
				response.sendRedirect(
						request.getRequestURI() + COMMAND_GO_TO_ERROR_PAGE + MESSAGE_TO_ERROR_PAGE_INVALID_USER_DATA);

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
