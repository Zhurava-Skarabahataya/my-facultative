package by.epamtc.facultative.bean;

import java.io.Serializable;
import java.util.List;

public class Faculty implements Serializable{
	
	
	private static final long serialVersionUID = 1L;
	
	private String facultyName;
	private String facultyDescription;
	private String deanName;
	
	
	private int faculteId;
	private int dean_id;
	
	private List <UserInfo> workingStaff;
	private List <UserInfo> NotApprovedStaff;
	private List <UserInfo> firedStaff;
	
	public Faculty() {
		
	}
	
	

	public String getDeanName() {
		return deanName;
	}



	public List<UserInfo> getWorkingStaff() {
		return workingStaff;
	}



	public List<UserInfo> getNotApprovedStaff() {
		return NotApprovedStaff;
	}



	public List<UserInfo> getFiredStaff() {
		return firedStaff;
	}



	public void setDeanName(String deanName) {
		this.deanName = deanName;
	}



	public void setWorkingStaff(List<UserInfo> workingStaff) {
		this.workingStaff = workingStaff;
	}



	public void setNotApprovedStaff(List<UserInfo> notApprovedStaff) {
		NotApprovedStaff = notApprovedStaff;
	}



	public void setFiredStaff(List<UserInfo> firedStaff) {
		this.firedStaff = firedStaff;
	}



	public String getFacultyName() {
		return facultyName;
	}

	public String getFacultyDescription() {
		return facultyDescription;
	}

	public int getFaculteId() {
		return faculteId;
	}

	public int getDean_id() {
		return dean_id;
	}

	public void setFacultyName(String facultyName) {
		this.facultyName = facultyName;
	}

	public void setFacultyDescription(String facultyDescription) {
		this.facultyDescription = facultyDescription;
	}

	public void setFaculteId(int faculteId) {
		this.faculteId = faculteId;
	}

	public void setDean_id(int dean_id) {
		this.dean_id = dean_id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + dean_id;
		result = prime * result + faculteId;
		result = prime * result + ((facultyDescription == null) ? 0 : facultyDescription.hashCode());
		result = prime * result + ((facultyName == null) ? 0 : facultyName.hashCode());
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
		Faculty other = (Faculty) obj;
		if (dean_id != other.dean_id)
			return false;
		if (faculteId != other.faculteId)
			return false;
		if (facultyDescription == null) {
			if (other.facultyDescription != null)
				return false;
		} else if (!facultyDescription.equals(other.facultyDescription))
			return false;
		if (facultyName == null) {
			if (other.facultyName != null)
				return false;
		} else if (!facultyName.equals(other.facultyName))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "FacultyInfo [facultyName=" + facultyName + ", facultyDescription=" + facultyDescription + ", faculteId="
				+ faculteId + ", dean_id=" + dean_id + "]";
	}
	
	
}
