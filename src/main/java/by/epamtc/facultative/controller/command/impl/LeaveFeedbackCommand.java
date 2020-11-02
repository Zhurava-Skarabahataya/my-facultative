package by.epamtc.facultative.controller.command.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.epamtc.facultative.controller.command.Command;

public class LeaveFeedbackCommand implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		
		System.out.println(request.getParameter("comment"));

	}

}
