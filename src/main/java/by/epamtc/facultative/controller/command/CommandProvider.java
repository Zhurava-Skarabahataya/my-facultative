package by.epamtc.facultative.controller.command;

import java.util.HashMap;
import java.util.Map;

import by.epamtc.facultative.controller.command.impl.ApplyForCourseCommand;
import by.epamtc.facultative.controller.command.impl.ApproveEmployeeCommand;
import by.epamtc.facultative.controller.command.impl.ApproveStudentForCourseCommand;
import by.epamtc.facultative.controller.command.impl.CreateRunCourseCommand;
import by.epamtc.facultative.controller.command.impl.DisapproveEmployeeCommand;
import by.epamtc.facultative.controller.command.impl.DisapproveStudentForCourseCommand;
import by.epamtc.facultative.controller.command.impl.DropOutStudentFromCourseCommand;
import by.epamtc.facultative.controller.command.impl.EditUserInfoCommand;
import by.epamtc.facultative.controller.command.impl.ExpellStudentCommand;
import by.epamtc.facultative.controller.command.impl.FireEmployeeCommand;
import by.epamtc.facultative.controller.command.impl.GiveStudentGradeCommand;
import by.epamtc.facultative.controller.command.impl.LeaveFeedbackCommand;
import by.epamtc.facultative.controller.command.impl.RemoveApplicationForCourseCommand;
import by.epamtc.facultative.controller.command.impl.UploadUserPhotoCommand;
import by.epamtc.facultative.controller.command.impl.ViewAnotherUserRunCoursesCommand;
import by.epamtc.facultative.controller.command.impl.ViewStudentRatingPageCommand;
import by.epamtc.facultative.controller.command.impl.authorization.AuthorizationCommand;
import by.epamtc.facultative.controller.command.impl.authorization.GoToAuthorizationPageCommand;
import by.epamtc.facultative.controller.command.impl.authorization.LogOutCommand;
import by.epamtc.facultative.controller.command.impl.localization.ChangeLanguageCommand;
import by.epamtc.facultative.controller.command.impl.redirection.GoToAboutUniversityPage;
import by.epamtc.facultative.controller.command.impl.redirection.GoToAnotherUserPageCommand;
import by.epamtc.facultative.controller.command.impl.redirection.GoToAvailableRunCoursesPage;
import by.epamtc.facultative.controller.command.impl.redirection.GoToContactsPageCommand;
import by.epamtc.facultative.controller.command.impl.redirection.GoToCoursePageCommand;
import by.epamtc.facultative.controller.command.impl.redirection.GoToCreateCoursePage;
import by.epamtc.facultative.controller.command.impl.redirection.GoToCurrentDepartmentPageCommand;
import by.epamtc.facultative.controller.command.impl.redirection.GoToDepartmentsPageCommand;
import by.epamtc.facultative.controller.command.impl.redirection.GoToErrorPageCommand;
import by.epamtc.facultative.controller.command.impl.redirection.GoToNewsPage;
import by.epamtc.facultative.controller.command.impl.redirection.GoToRunCoursePageCommand;
import by.epamtc.facultative.controller.command.impl.redirection.GoToStaffPageCommand;
import by.epamtc.facultative.controller.command.impl.redirection.GoToStudentsPageCommand;
import by.epamtc.facultative.controller.command.impl.redirection.GoToSuccessPageCommand;
import by.epamtc.facultative.controller.command.impl.redirection.GoToUserCoursesPage;
import by.epamtc.facultative.controller.command.impl.redirection.GoToUserPageCommand;
import by.epamtc.facultative.controller.command.impl.redirection.GoToUserPageEditingCommand;
import by.epamtc.facultative.controller.command.impl.redirection.GoToWelcomePageCommand;
import by.epamtc.facultative.controller.command.impl.registration.GoToRegistrationPageCommand;
import by.epamtc.facultative.controller.command.impl.registration.RegistrationCommand;

public class CommandProvider {
	
	private static final CommandProvider instance = new CommandProvider();

	private Map<CommandName, Command> commands = new HashMap<>();

	private CommandProvider() {
		
		commands.put(CommandName.GO_TO_WELCOME_PAGE, new GoToWelcomePageCommand());
		commands.put(CommandName.GO_TO_AUTHORIZATION_PAGE, new GoToAuthorizationPageCommand());
		commands.put(CommandName.GO_TO_REGISTRATION_PAGE, new GoToRegistrationPageCommand());
		commands.put(CommandName.GO_TO_USER_PAGE, new GoToUserPageCommand());
		commands.put(CommandName.GO_TO_ERROR_PAGE, new GoToErrorPageCommand());
		commands.put(CommandName.GO_TO_EDIT_USER_INFO_COMMAND, new GoToUserPageEditingCommand());
		commands.put(CommandName.GO_TO_USER_COURSES_PAGE, new GoToUserCoursesPage());
		commands.put(CommandName.GO_TO_CREATE_RUN_COURSE_PAGE, new GoToCreateCoursePage());
		commands.put(CommandName.GO_TO_RUN_COURSE_PAGE, new GoToRunCoursePageCommand());
		commands.put(CommandName.GO_TO_COURSE_PAGE_COMMAND, new GoToCoursePageCommand());
		commands.put(CommandName.GO_TO_DEPARTMENTS_PAGE, new GoToDepartmentsPageCommand());
		commands.put(CommandName.GO_TO_CONTACTS_PAGE, new GoToContactsPageCommand());
		commands.put(CommandName.GO_TO_NEWS_PAGE, new GoToNewsPage());
		commands.put(CommandName.GO_TO_ABOUT_UNIVERSITY_PAGE, new GoToAboutUniversityPage());
		commands.put(CommandName.GO_TO_AVAILABLE_RUN_COURSES_PAGE, new GoToAvailableRunCoursesPage());
		commands.put(CommandName.GO_TO_CURRENT_DEPARTMENT_PAGE_COMMAND, new GoToCurrentDepartmentPageCommand());
		commands.put(CommandName.GO_TO_SUCCESS_PAGE_COMMAND, new GoToSuccessPageCommand());
		commands.put(CommandName.GO_TO_STAFF_PAGE, new GoToStaffPageCommand());
		commands.put(CommandName.GO_TO_STUDENTS_PAGE, new GoToStudentsPageCommand());

		commands.put(CommandName.REGISTRATION_COMMAND, new RegistrationCommand());
		commands.put(CommandName.AUTHORIZATION_COMMAND, new AuthorizationCommand());
		commands.put(CommandName.CHANGE_LANGUAGE, new ChangeLanguageCommand());
		commands.put(CommandName.UPLOAD_USER_PHOTO, new UploadUserPhotoCommand());
		commands.put(CommandName.EDIT_USER_INFO, new EditUserInfoCommand());
		commands.put(CommandName.CREATE_COURSE_COMMAND, new CreateRunCourseCommand());
		commands.put(CommandName.LOGOUT, new LogOutCommand());
		commands.put(CommandName.APPLY_FOR_COURSE, new ApplyForCourseCommand());
		commands.put(CommandName.REMOVE_APPLICATION_FOR_COURSE, new RemoveApplicationForCourseCommand());
		commands.put(CommandName.APPROVE_STUDENT_ON_COURSE, new ApproveStudentForCourseCommand());
		commands.put(CommandName.DISAPPROVE_STUDENT_ON_COURSE, new DisapproveStudentForCourseCommand());
		commands.put(CommandName.DROP_OUT_STUDENT_FROM_COURSE, new DropOutStudentFromCourseCommand());
		commands.put(CommandName.APPROVE_EMPLOYEE, new ApproveEmployeeCommand());
		commands.put(CommandName.DISAPPROVE_EMPLOYEE, new DisapproveEmployeeCommand());
		commands.put(CommandName.GO_TO_ANOTHER_USER_PAGE, new GoToAnotherUserPageCommand());
	
		commands.put(CommandName.GIVE_THE_GRADE, new GiveStudentGradeCommand());
		commands.put(CommandName.VIEW_ANOTHER_USER_RUN_COURSES, new ViewAnotherUserRunCoursesCommand());
		commands.put(CommandName.VIEW_STUDENT_RATING_PAGE, new ViewStudentRatingPageCommand());
		commands.put(CommandName.EXPEL_STUDENT, new ExpellStudentCommand());
		commands.put(CommandName.FIRE_EMPLOYEE, new FireEmployeeCommand());
		commands.put(CommandName.LEAVE_FEEDBACK, new LeaveFeedbackCommand());
		
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
