package by.epamtc.facultative.controller.command;

import java.util.HashMap;
import java.util.Map;

import by.epamtc.facultative.controller.command.impl.ApplyForCourseCommand;
import by.epamtc.facultative.controller.command.impl.AuthorizationCommand;
import by.epamtc.facultative.controller.command.impl.ChangeLanguageCommand;
import by.epamtc.facultative.controller.command.impl.CreateCourseCommand;
import by.epamtc.facultative.controller.command.impl.EditUserInfoCommand;
import by.epamtc.facultative.controller.command.impl.GoToAboutUniversityPage;
import by.epamtc.facultative.controller.command.impl.GoToAuthorizationPageCommand;
import by.epamtc.facultative.controller.command.impl.GoToAvailableRunCoursesPage;
import by.epamtc.facultative.controller.command.impl.GoToContactsPageCommand;
import by.epamtc.facultative.controller.command.impl.GoToCoursePageCommand;
import by.epamtc.facultative.controller.command.impl.GoToCreateCoursePage;
import by.epamtc.facultative.controller.command.impl.GoToCurrentDepartmentPageCommand;
import by.epamtc.facultative.controller.command.impl.GoToDepartmentsPageCommand;
import by.epamtc.facultative.controller.command.impl.GoToErrorPageCommand;
import by.epamtc.facultative.controller.command.impl.GoToNewsPage;
import by.epamtc.facultative.controller.command.impl.GoToRegistrationPageCommand;
import by.epamtc.facultative.controller.command.impl.GoToRegistrationSuccessPage;
import by.epamtc.facultative.controller.command.impl.GoToRunCoursePageCommand;
import by.epamtc.facultative.controller.command.impl.GoToUniversityCoursesPageCommand;
import by.epamtc.facultative.controller.command.impl.GoToUserCoursesPage;
import by.epamtc.facultative.controller.command.impl.GoToUserPageCommand;
import by.epamtc.facultative.controller.command.impl.GoToUserPageEditingCommand;
import by.epamtc.facultative.controller.command.impl.GoToUserRatingPage;
import by.epamtc.facultative.controller.command.impl.GoToWelcomePageCommand;
import by.epamtc.facultative.controller.command.impl.LogOutCommand;
import by.epamtc.facultative.controller.command.impl.RegistrationCommand;
import by.epamtc.facultative.controller.command.impl.UploadUserPhotoCommand;

public class CommandProvider {
	
	private static final CommandProvider instance = new CommandProvider();

	private Map<CommandName, Command> commands = new HashMap<>();

	private CommandProvider() {
		
		commands.put(CommandName.GO_TO_WELCOME_PAGE, new GoToWelcomePageCommand());
		commands.put(CommandName.GO_TO_AUTHORIZATION_PAGE, new GoToAuthorizationPageCommand());
		commands.put(CommandName.GO_TO_REGISTRATION_PAGE, new GoToRegistrationPageCommand());
		commands.put(CommandName.GO_TO_USER_PAGE, new GoToUserPageCommand());
		commands.put(CommandName.GO_TO_REGISTRATION_SUCCESS_PAGE, new GoToRegistrationSuccessPage());
		commands.put(CommandName.GO_TO_ERROR_PAGE, new GoToErrorPageCommand());
		commands.put(CommandName.GO_TO_EDIT_USER_INFO_COMMAND, new GoToUserPageEditingCommand());
		commands.put(CommandName.GO_TO_USER_COURSES_PAGE, new GoToUserCoursesPage());
		commands.put(CommandName.GO_TO_USER_RATING_PAGE, new GoToUserRatingPage());
		commands.put(CommandName.GO_TO_CREATE_COURSE_PAGE, new GoToCreateCoursePage());
		commands.put(CommandName.GO_TO_RUN_COURSE_PAGE, new GoToRunCoursePageCommand());
		commands.put(CommandName.GO_TO_COURSE_PAGE, new GoToCoursePageCommand());
		commands.put(CommandName.GO_TO_DEPARTMENTS_PAGE, new GoToDepartmentsPageCommand());
		commands.put(CommandName.GO_TO_UNIVERCITY_COURSES_PAGE, new GoToUniversityCoursesPageCommand());
		commands.put(CommandName.GO_TO_CONTACTS_PAGE, new GoToContactsPageCommand());
		commands.put(CommandName.GO_TO_NEWS_PAGE, new GoToNewsPage());
		commands.put(CommandName.GO_TO_ABOUT_UNIVERSITY_PAGE, new GoToAboutUniversityPage());
		commands.put(CommandName.GO_TO_AVAILABLE_RUN_COURSES_PAGE, new GoToAvailableRunCoursesPage());
		commands.put(CommandName.GO_TO_CURRENT_DEPARTMENT_PAGE_COMMAND, new GoToCurrentDepartmentPageCommand());

		commands.put(CommandName.REGISTRATION_COMMAND, new RegistrationCommand());
		commands.put(CommandName.AUTHORIZATION_COMMAND, new AuthorizationCommand());
		commands.put(CommandName.CHANGE_LANGUAGE, new ChangeLanguageCommand());
		commands.put(CommandName.UPLOAD_USER_PHOTO, new UploadUserPhotoCommand());
		commands.put(CommandName.EDIT_USER_INFO, new EditUserInfoCommand());
		commands.put(CommandName.CREATE_COURSE_COMMAND, new CreateCourseCommand());
		commands.put(CommandName.LOGOUT, new LogOutCommand());
		commands.put(CommandName.APPLY_FOR_COURSE, new ApplyForCourseCommand());
		
	}

	public static CommandProvider getInstance() {
		return instance;
	}
	
	public Command getCommand(String commandName) {
		
		Command command;
		CommandName valueName;

		commandName = commandName.toUpperCase();

		valueName = CommandName.valueOf(commandName);

		command = commands.get(valueName);

		return command;

	}

}
