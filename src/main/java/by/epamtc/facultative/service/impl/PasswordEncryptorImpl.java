package by.epamtc.facultative.service.impl;

import org.mindrot.jbcrypt.BCrypt;

import by.epamtc.facultative.service.PasswordEncryptor;

public class PasswordEncryptorImpl implements PasswordEncryptor{

	private static PasswordEncryptorImpl instance = new PasswordEncryptorImpl();

	private PasswordEncryptorImpl() {

	}

	public static PasswordEncryptorImpl getInstance() {
		return instance;
	}

	@Override
	public String hashPassword(String unhashedPassword) {

		String hashedPassword;
		hashedPassword = BCrypt.hashpw(unhashedPassword, BCrypt.gensalt());
		
		return hashedPassword;
	}

}
