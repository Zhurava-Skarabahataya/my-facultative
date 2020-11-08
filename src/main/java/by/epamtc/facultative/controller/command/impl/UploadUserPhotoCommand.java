package by.epamtc.facultative.controller.command.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import org.apache.log4j.Logger;

import by.epamtc.facultative.controller.command.Command;

public class UploadUserPhotoCommand implements Command {

	private static final Logger logger = Logger.getLogger(UploadUserPhotoCommand.class);

	private final String COMMAND_GO_TO_ERROR_PAGE = "?command=go_to_error_page";
	private final String PARAMETER_COMMAND_GO_TO_USER_PAGE = "?command=go_to_user_page";
	private final String MESSAGE_GO_TO_ERROR_PAGE_INTERNAL_SERVER_ERROR = "&message=server_error";
	
	private final String REQUEST_PRAMETER_PART = "file";
	private final String SESSION_ATTRIBUTE_LOGIN = "userLogin";

	private final String FILENAME_EXTENSION = ".jpg";
	private final String PROJECT_PATH = "D:/Java";
	private final String FOLDER_PATH = "/user_photos/";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {

		Part file;
		InputStream inputStream = null;
		OutputStream outStream = null;

		try {

			file = request.getPart(REQUEST_PRAMETER_PART);
			inputStream = file.getInputStream();

			byte[] buffer = new byte[inputStream.available()];
			inputStream.read(buffer);

			HttpSession session = request.getSession();
			String userLogin;
			userLogin = (String) session.getAttribute(SESSION_ATTRIBUTE_LOGIN);

			File targetFile = new File(
					PROJECT_PATH + request.getContextPath() + FOLDER_PATH + userLogin + FILENAME_EXTENSION);

			if (!targetFile.exists()) {
				targetFile.createNewFile();
			}

			outStream = new FileOutputStream(targetFile);
			outStream.write(buffer);

			response.sendRedirect(request.getRequestURI() + PARAMETER_COMMAND_GO_TO_USER_PAGE);

		} catch (IOException | ServletException e) {
			logger.error(e);
			response.sendRedirect(request.getRequestURI() + COMMAND_GO_TO_ERROR_PAGE
					+ MESSAGE_GO_TO_ERROR_PAGE_INTERNAL_SERVER_ERROR);
		}

	}
}
