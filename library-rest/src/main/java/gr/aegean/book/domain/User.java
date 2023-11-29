package gr.aegean.book.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.Basic;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.NaturalId;

@Entity
@Table(name="users")
public class User {
	@Id
	@GeneratedValue
	private UUID id;
	@NaturalId  
	private String username;
	@Basic(optional=false)
	private String password;
	@ElementCollection(fetch=FetchType.EAGER)
	private List<String> roles = new ArrayList<String>();
	  
	public User() {	  
	}

	public String getUsername() { return username; }
	public void setUsername(String username) throws IllegalArgumentException{
		if (username == null || username.trim().equals("")) throw new IllegalArgumentException("Username cannot be null or empty");
		this.username = username;
	}
	public UUID getId() {return id;}
	public List<String> getRoles() { return roles; }
	public void setRoles(List<String> roles) { this.roles = roles; }
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) throws IllegalArgumentException{
		if (password == null || password.trim().equals("")) throw new IllegalArgumentException("Password cannot be null or empty");
		this.password = password;
	}
	
	public boolean equals(Object o) {
		if (o instanceof User) {
			User u = (User)o;
			if (u.username.equals(username)) return true;
		}
		
		return false;
	}
	
	public int hashCode() {
		return username.hashCode();
	}
}

