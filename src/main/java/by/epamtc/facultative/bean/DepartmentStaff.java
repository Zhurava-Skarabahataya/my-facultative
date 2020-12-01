package by.epamtc.facultative.bean;

import java.io.Serializable;
import java.util.List;

public class DepartmentStaff implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<UserInfo> workingStaff;
	private List<UserInfo> notApprovedStaff;
	private List<UserInfo> firedStaff;
	private List<UserInfo> allStaff;

	private int departmentId;
	private String departmentName;

	public DepartmentStaff() {

	}

	public List<UserInfo> getWorkingStaff() {
		return workingStaff;
	}

	public List<UserInfo> getNotApprovedStaff() {
		return notApprovedStaff;
	}

	public List<UserInfo> getFiredStaff() {
		return firedStaff;
	}

	public List<UserInfo> getAllStaff() {
		return allStaff;
	}

	public int getDepartmentId() {
		return departmentId;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setWorkingStaff(List<UserInfo> workingStaff) {
		this.workingStaff = workingStaff;
	}

	public void setNotApprovedStaff(List<UserInfo> notApprovedStaff) {
		this.notApprovedStaff = notApprovedStaff;
	}

	public void setFiredStaff(List<UserInfo> firedStaff) {
		this.firedStaff = firedStaff;
	}

	public void setAllStaff(List<UserInfo> allStaff) {
		this.allStaff = allStaff;
	}

	public void setDepartmentId(int departmentId) {
		this.departmentId = departmentId;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((allStaff == null) ? 0 : allStaff.hashCode());
		result = prime * result + departmentId;
		result = prime * result + ((departmentName == null) ? 0 : departmentName.hashCode());
		result = prime * result + ((firedStaff == null) ? 0 : firedStaff.hashCode());
		result = prime * result + ((notApprovedStaff == null) ? 0 : notApprovedStaff.hashCode());
		result = prime * result + ((workingStaff == null) ? 0 : workingStaff.hashCode());
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
		DepartmentStaff other = (DepartmentStaff) obj;
		if (allStaff == null) {
			if (other.allStaff != null)
				return false;
		} else if (!allStaff.equals(other.allStaff))
			return false;
		if (departmentId != other.departmentId)
			return false;
		if (departmentName == null) {
			if (other.departmentName != null)
				return false;
		} else if (!departmentName.equals(other.departmentName))
			return false;
		if (firedStaff == null) {
			if (other.firedStaff != null)
				return false;
		} else if (!firedStaff.equals(other.firedStaff))
			return false;
		if (notApprovedStaff == null) {
			if (other.notApprovedStaff != null)
				return false;
		} else if (!notApprovedStaff.equals(other.notApprovedStaff))
			return false;
		if (workingStaff == null) {
			if (other.workingStaff != null)
				return false;
		} else if (!workingStaff.equals(other.workingStaff))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "DepartmentStaff [workingStaff=" + workingStaff + ", notApprovedStaff=" + notApprovedStaff
				+ ", firedStaff=" + firedStaff + ", allStaff=" + allStaff + ", departmentId=" + departmentId
				+ ", departmentName=" + departmentName + "]";
	}

}
