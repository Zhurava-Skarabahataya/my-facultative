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
import by.epamtc.facultative.service.impl.UpdateUserInfoServiceImpl;

public class EditUserInfoCommand implements Command {

	private  final String PARAMETER_FIRST_NAME = "userFirstName";
	private  final String PARAMETER_SECOND_NAME = "userSecondName";
	private  final String PARAMETER_PATRONYMIC = "userPatronymic";
	private  final String PARAMETER_FACULTY = "faculty";

	private  final String PARAMETER_COMMAND_GO_TO_USER_PAGE = "?command=go_to_user_page";
	private final String COMMAND_GO_TO_ERROR_PAGE = "?command=go_to_error_page";
	private final String MESSAGE_GO_TO_ERROR_PAGE_NOT_AUTHORIZED = "&message=user_not_authorized";

	private final String COMMAND_GO_TO_USER_PAGE = "?command=go_to_user_page";
	private final String MESSAGE_GO_TO_ERROR_PAGE_INTERNAL_SERVER_ERROR = "&message=server_error";
	
	private static final Logger logger = Logger.getLogger(EditUserInfoCommand.class);


	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();

		String userLogin = (String) session.getAttribute("userLogin");
		// проверь, залогинен ли

		if (userLogin != null) {
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
			if (!request.getParameter("userDateOfBirth").isEmpty()) {
				try {
					LocalDate userDateOfBirth = LocalDate.parse(request.getParameter("userDateOfBirth"));
					userPageInfo.setUserDateOfBirth(userDateOfBirth);
				} catch (DateTimeParseException e) {
					e.printStackTrace();
					//ccообщить юзеру, что ввел фигню
				}
			}

			try {

				UpdateUserInfoServiceImpl updateUserInfoService = UpdateUserInfoServiceImpl.getInstance();
				updateUserInfoService.update(userPageInfo);
			} catch (ValidationException e1) {
				//на страницу ошибки с мессаджем, добавить текста
			}
			
			try {
				response.sendRedirect(request.getRequestURI() + PARAMETER_COMMAND_GO_TO_USER_PAGE);
			} catch (IOException e) {
				e.printStackTrace();
			}

			
			
		}else{
			try {
				response.sendRedirect(
						request.getRequestURI() + COMMAND_GO_TO_ERROR_PAGE + MESSAGE_GO_TO_ERROR_PAGE_NOT_AUTHORIZED);

			} catch (IOException e) {

				logger.error(e);
				response.sendRedirect(request.getRequestURI() + COMMAND_GO_ERROR_PAGE
						+ MESSAGE_GO_TO_ERROR_PAGE_INTERNAL_SERVER_ERROR);
			}
			
		}
		
		
	}

}
