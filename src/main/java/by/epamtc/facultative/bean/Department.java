package by.epamtc.facultative.bean;

import java.io.Serializable;
import java.util.List;

public class Department implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String departmentName;
	private String departmentDescription;
	private String deanName;
	private String deanLogin;
	private String deanImagePath;
	
	private int departmentID;
	private int deanId;
	
	private List<UserInfo> lecturers;
	private List<Course> courses;
	
	
	
	public Department() {
		
	}

	
	
	public String getDeanImagePath() {
		return deanImagePath;
	}



	public void setDeanImagePath(String deanImagePath) {
		this.deanImagePath = deanImagePath;
	}



	public List<UserInfo> getLecturers() {
		return lecturers;
	}



	public List<Course> getCourses() {
		return courses;
	}



	public void setLecturers(List<UserInfo> lecturers) {
		this.lecturers = lecturers;
	}



	public void setCourses(List<Course> courses) {
		this.courses = courses;
	}



	public String getDepartmentName() {
		return departmentName;
	}

	public String getDepartmentDescription() {
		return departmentDescription;
	}

	public String getDeanName() {
		return deanName;
	}

	public String getDeanLogin() {
		return deanLogin;
	}

	public int getDepartmentID() {
		return departmentID;
	}

	public int getDeanId() {
		return deanId;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public void setDepartmentDescription(String departmentDescription) {
		this.departmentDescription = departmentDescription;
	}

	public void setDeanName(String deanName) {
		this.deanName = deanName;
	}

	public void setDeanLogin(String deanLogin) {
		this.deanLogin = deanLogin;
	}

	public void setDepartmentID(int departmentID) {
		this.departmentID = departmentID;
	}

	public void setDeanId(int deanId) {
		this.deanId = deanId;
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((courses == null) ? 0 : courses.hashCode());
		result = prime * result + deanId;
		result = prime * result + ((deanImagePath == null) ? 0 : deanImagePath.hashCode());
		result = prime * result + ((deanLogin == null) ? 0 : deanLogin.hashCode());
		result = prime * result + ((deanName == null) ? 0 : deanName.hashCode());
		result = prime * result + ((departmentDescription == null) ? 0 : departmentDescription.hashCode());
		result = prime * result + departmentID;
		result = prime * result + ((departmentName == null) ? 0 : departmentName.hashCode());
		result = prime * result + ((lecturers == null) ? 0 : lecturers.hashCode());
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
		Department other = (Department) obj;
		if (courses == null) {
			if (other.courses != null)
				return false;
		} else if (!courses.equals(other.courses))
			return false;
		if (deanId != other.deanId)
			return false;
		if (deanImagePath == null) {
			if (other.deanImagePath != null)
				return false;
		} else if (!deanImagePath.equals(other.deanImagePath))
			return false;
		if (deanLogin == null) {
			if (other.deanLogin != null)
				return false;
		} else if (!deanLogin.equals(other.deanLogin))
			return false;
		if (deanName == null) {
			if (other.deanName != null)
				return false;
		} else if (!deanName.equals(other.deanName))
			return false;
		if (departmentDescription == null) {
			if (other.departmentDescription != null)
				return false;
		} else if (!departmentDescription.equals(other.departmentDescription))
			return false;
		if (departmentID != other.departmentID)
			return false;
		if (departmentName == null) {
			if (other.departmentName != null)
				return false;
		} else if (!departmentName.equals(other.departmentName))
			return false;
		if (lecturers == null) {
			if (other.lecturers != null)
				return false;
		} else if (!lecturers.equals(other.lecturers))
			return false;
		return true;
	}



	@Override
	public String toString() {
		return "Department [departmentName=" + departmentName + ", departmentDescription=" + departmentDescription
				+ ", deanName=" + deanName + ", deanLogin=" + deanLogin + ", deanImagePath=" + deanImagePath
				+ ", departmentID=" + departmentID + ", deanId=" + deanId + ", lecturers=" + lecturers + ", courses="
				+ courses + "]";
	}

	
	
}
