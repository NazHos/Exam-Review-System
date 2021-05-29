package main;

public class User {
	
	// user's group permissions
	public enum role {
		Teaching_Staff,
		External_Moderator,
		Administrator,
		Office_Staff,
		Vetting_Committee
	}
	

	private int staff_id;
	private String firstName, surname, email;
	private role userRole;
	
	public User() {
		
	}
	
	public User(int staff_id, String firstName, String surname, String email, role userRole) {
		setName(firstName, surname);
		setEmail(email);
		setRole(userRole);
		setID(staff_id);
	}

	public int getId()
	{
		return staff_id;
	}
	
	public void setID(int id)
	{
		staff_id = id;
	}
	
	public role getRole()
	{
		return userRole;
	}

	public void setRole(role urole)
	{
		userRole = urole;
	}
	
	public String getName() {
		return firstName + " " + surname;
	}

	public String getfName()
	{
		return firstName;
	}
	
	public String getSName()
	{
		return surname;
	}
	
	public void setName(String fname, String surname) {
		this.firstName = fname;
		this.surname = surname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
