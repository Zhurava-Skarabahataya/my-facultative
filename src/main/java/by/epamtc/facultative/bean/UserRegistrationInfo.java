package by.epamtc.facultative.bean;

import java.io.Serializable;

public class UserRegistrationInfo implements UserInfo, Serializable {

	private static final long serialVersionUID = 1L;

	private String userLogin;
	private String userPassword;
	private String firstName;
	private String secondName;
	private String patromynic;
	private String userEmail;

	private int userRoleID;
	private int departmentID;

	public UserRegistrationInfo() {
		super();
	}

	public String getUserLogin() {
		return userLogin;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getSecondName() {
		return secondName;
	}

	public String getPatromynic() {
		return patromynic;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public int getUserRoleID() {
		return userRoleID;
	}

	public int getDepartmentID() {
		return departmentID;
	}

	public void setUserLogin(String userLogin) {
		this.userLogin = userLogin;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setSecondName(String secondName) {
		this.secondName = secondName;
	}

	public void setPatromynic(String patromynic) {
		this.patromynic = patromynic;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public void setUserRoleID(int userRoleID) {
		this.userRoleID = userRoleID;
	}

	public void setDepartmentID(int departmentID) {
		this.departmentID = departmentID;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + departmentID;
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + ((patromynic == null) ? 0 : patromynic.hashCode());
		result = prime * result + ((secondName == null) ? 0 : secondName.hashCode());
		result = prime * result + ((userEmail == null) ? 0 : userEmail.hashCode());
		result = prime * result + ((userLogin == null) ? 0 : userLogin.hashCode());
		result = prime * result + ((userPassword == null) ? 0 : userPassword.hashCode());
		result = prime * result + userRoleID;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserRegistrationInfo other = (UserRegistrationInfo) obj;
		if (departmentID != other.departmentID)
			return false;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (patromynic == null) {
			if (other.patromynic != null)
				return false;
		} else if (!patromynic.equals(other.patromynic))
			return false;
		if (secondName == null) {
			if (other.secondName != null)
				return false;
		} else if (!secondName.equals(other.secondName))
			return false;
		if (userEmail == null) {
			if (other.userEmail != null)
				return false;
		} else if (!userEmail.equals(other.userEmail))
			return false;
		if (userLogin == null) {
			if (other.userLogin != null)
				return false;
		} else if (!userLogin.equals(other.userLogin))
			return false;
		if (userPassword == null) {
			if (other.userPassword != null)
				return false;
		} else if (!userPassword.equals(other.userPassword))
			return false;
		if (userRoleID != other.userRoleID)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "UserRegistrationInfo [userLogin=" + userLogin + ", userPassword=" + userPassword + ", firstName="
				+ firstName + ", secondName=" + secondName + ", patromynic=" + patromynic + ", userEmail=" + userEmail
				+ ", userRoleID=" + userRoleID + ", departmentID=" + departmentID + "]";
	}

}
