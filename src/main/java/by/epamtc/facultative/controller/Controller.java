package by.epamtc.facultative.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.epamtc.facultative.controller.command.Command;
import by.epamtc.facultative.controller.command.CommandProvider;

/**
 * Servlet implementation class Controller
 */
@MultipartConfig
@WebServlet("/Controller")
public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private final String REQUEST_PARAMETER_COMMAND = "command";

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Controller() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String commandName;

		commandName = request.getParameter(REQUEST_PARAMETER_COMMAND);
		Command currentCommand = CommandProvider.getInstance().getCommand(commandName.toUpperCase());
		currentCommand.execute(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String commandName;

		commandName = request.getParameter(REQUEST_PARAMETER_COMMAND);
		Command currentCommand = CommandProvider.getInstance().getCommand(commandName.toUpperCase());

		try{currentCommand.execute(request, response);}
		catch (ServletException| IOException e) {
			e.printStackTrace();
		}
	}

}
