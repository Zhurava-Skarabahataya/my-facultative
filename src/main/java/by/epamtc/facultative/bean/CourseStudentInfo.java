package by.epamtc.facultative.bean;

import java.io.Serializable;

public class CourseStudentInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	private int userId;
	private String userLogin;
	private String userFirstName;
	private String userSecondName;
	private String userPatronymic;
	private int result;
	private int userApprovalStatusId;
	private String userApprovalStatusName;

	public int getUserId() {
		return userId;
	}

	public String getUserLogin() {
		return userLogin;
	}

	public String getUserFirstName() {
		return userFirstName;
	}

	public String getUserSecondName() {
		return userSecondName;
	}

	public String getUserPatronymic() {
		return userPatronymic;
	}

	public int getResult() {
		return result;
	}

	public int getUserApprovalStatusId() {
		return userApprovalStatusId;
	}

	public String getUserApprovalStatusName() {
		return userApprovalStatusName;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public void setUserLogin(String userLogin) {
		this.userLogin = userLogin;
	}

	public void setUserFirstName(String userFirstName) {
		this.userFirstName = userFirstName;
	}

	public void setUserSecondName(String userSecondName) {
		this.userSecondName = userSecondName;
	}

	public void setUserPatronymic(String userPatronymic) {
		this.userPatronymic = userPatronymic;
	}

	public void setResult(int result) {
		this.result = result;
	}

	public void setUserApprovalStatusId(int userApprovalStatusId) {
		this.userApprovalStatusId = userApprovalStatusId;
	}

	public void setUserApprovalStatusName(String userApprovalStatusName) {
		this.userApprovalStatusName = userApprovalStatusName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + this.result;
		result = prime * result + userApprovalStatusId;
		result = prime * result + ((userApprovalStatusName == null) ? 0 : userApprovalStatusName.hashCode());
		result = prime * result + ((userFirstName == null) ? 0 : userFirstName.hashCode());
		result = prime * result + userId;
		result = prime * result + ((userLogin == null) ? 0 : userLogin.hashCode());
		result = prime * result + ((userPatronymic == null) ? 0 : userPatronymic.hashCode());
		result = prime * result + ((userSecondName == null) ? 0 : userSecondName.hashCode());
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
		CourseStudentInfo other = (CourseStudentInfo) obj;
		if (result != other.result)
			return false;
		if (userApprovalStatusId != other.userApprovalStatusId)
			return false;
		if (userApprovalStatusName == null) {
			if (other.userApprovalStatusName != null)
				return false;
		} else if (!userApprovalStatusName.equals(other.userApprovalStatusName))
			return false;
		if (userFirstName == null) {
			if (other.userFirstName != null)
				return false;
		} else if (!userFirstName.equals(other.userFirstName))
			return false;
		if (userId != other.userId)
			return false;
		if (userLogin == null) {
			if (other.userLogin != null)
				return false;
		} else if (!userLogin.equals(other.userLogin))
			return false;
		if (userPatronymic == null) {
			if (other.userPatronymic != null)
				return false;
		} else if (!userPatronymic.equals(other.userPatronymic))
			return false;
		if (userSecondName == null) {
			if (other.userSecondName != null)
				return false;
		} else if (!userSecondName.equals(other.userSecondName))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "CourseStudentInfo [userId=" + userId + ", userLogin=" + userLogin + ", userFirstName=" + userFirstName
				+ ", userSecondName=" + userSecondName + ", userPatronymic=" + userPatronymic + ", result=" + result
				+ ", userApprovalStatusId=" + userApprovalStatusId + ", userApprovalStatusName="
				+ userApprovalStatusName + "]";
	}

}
