package uk.ac.ncl.rbac.entityApi;

import java.util.List;

public class JsonRooms {
	private List<String> admin;
	private List<String> researcher;
	private List<String> student;
	private List<String> publicUser;
	public List<String> getAdmin() {
		return admin;
	}
	public void setAdmin(List<String> admin) {
		this.admin = admin;
	}
	public List<String> getResearcher() {
		return researcher;
	}
	public void setResearcher(List<String> researcher) {
		this.researcher = researcher;
	}
	public List<String> getStudent() {
		return student;
	}
	public void setStudent(List<String> student) {
		this.student = student;
	}
	public List<String> getPublicUser() {
		return publicUser;
	}
	public void setPublicUser(List<String> publicUser) {
		this.publicUser = publicUser;
	}
	
	
}
