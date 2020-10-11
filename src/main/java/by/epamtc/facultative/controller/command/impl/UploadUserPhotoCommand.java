package by.epamtc.facultative.controller.command.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Paths;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import by.epamtc.facultative.controller.command.Command;

public class UploadUserPhotoCommand implements Command {

	private static final String REQUEST_PRAMETER_PART = "file";
	private static final String SESSION_ATTRIBUTE_LOGIN = "userLogin";
	private static final String FILENAME_EXTENSION = ".jpg";
	private static final String PARAMETER_COMMAND_GO_TO_USER_PAGE = "?command=go_to_student_page";
	private static final String PROJECT_PATH = "D:/Java/JavaWorkspace";
	private static final String FOLDER_PATH = "/src/main/webapp/user_photos/";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {

		Part file;
		InputStream inputStream = null;
		OutputStream outStream = null;

		try {

			file = request.getPart(REQUEST_PRAMETER_PART);
			inputStream = file.getInputStream();

			byte[] buffer = new byte[inputStream.available()];
			inputStream.read(buffer);

			HttpSession session = request.getSession();
			String userLogin = (String) session.getAttribute(SESSION_ATTRIBUTE_LOGIN);

			File targetFile = new File(
					PROJECT_PATH + request.getContextPath() + FOLDER_PATH + userLogin + FILENAME_EXTENSION);

			if (!targetFile.exists()) {
				targetFile.createNewFile();
			}

			outStream = new FileOutputStream(targetFile);
			outStream.write(buffer);

		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (ServletException e1) {
			e1.printStackTrace();
		}

		try {
			response.sendRedirect(request.getRequestURI() + PARAMETER_COMMAND_GO_TO_USER_PAGE);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
