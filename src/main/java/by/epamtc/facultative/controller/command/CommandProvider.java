package by.epamtc.facultative.controller.command;

import java.util.HashMap;
import java.util.Map;

import by.epamtc.facultative.controller.command.impl.AuthorizationCommand;
import by.epamtc.facultative.controller.command.impl.ChangeLanguageCommand;
import by.epamtc.facultative.controller.command.impl.EditUserInfoCommand;
import by.epamtc.facultative.controller.command.impl.GoToAuthorizationPageCommand;
import by.epamtc.facultative.controller.command.impl.GoToErrorPageCommand;
import by.epamtc.facultative.controller.command.impl.GoToRegistrationPageCommand;
import by.epamtc.facultative.controller.command.impl.GoToRegistrationSuccessPage;
import by.epamtc.facultative.controller.command.impl.GoToUserPageCommand;
import by.epamtc.facultative.controller.command.impl.GoToUserPageEditingCommand;
import by.epamtc.facultative.controller.command.impl.GoToWelcomePageCommand;
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
		commands.put(CommandName.AUTHORIZATION_COMMAND, new AuthorizationCommand());
		commands.put(CommandName.REGISTRATION_COMMAND, new RegistrationCommand());
		commands.put(CommandName.CHANGE_LANGUAGE, new ChangeLanguageCommand());
		commands.put(CommandName.GO_TO_REGISTRATION_SUCCESS_PAGE, new GoToRegistrationSuccessPage());
		commands.put(CommandName.UPLOAD_USER_PHOTO, new UploadUserPhotoCommand());
		commands.put(CommandName.GO_TO_ERROR_PAGE, new GoToErrorPageCommand());
		commands.put(CommandName.EDIT_USER_INFO, new EditUserInfoCommand());
		commands.put(CommandName.GO_TO_EDIT_USER_INFO_COMMAND, new GoToUserPageEditingCommand());
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
