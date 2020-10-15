package by.epamtc.facultative.service;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordEncryptor {

	private static PasswordEncryptor instance = new PasswordEncryptor();

	private PasswordEncryptor() {

	}

	public static PasswordEncryptor getInstance() {
		return instance;
	}

	public String hashPassword(String unhashedPassword) {

		String hashedPassword;

		hashedPassword = BCrypt.hashpw(unhashedPassword, BCrypt.gensalt());
		return hashedPassword;
	}

}
