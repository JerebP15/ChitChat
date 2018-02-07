import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

public class User {
	private String username;
	private Date lastlogin;
	
	private User() { }
	
	public User(String username, Date lastlogin) {
		this.username = username;
		this.lastlogin = lastlogin;
	}
	
	@Override
	public String toString() {
		return "Uporabnik [username=" + username + ", lastActive=" + lastlogin + "]";
	}

	@JsonProperty("username")
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@JsonProperty("last_active")
	public Date lastlogin() {
		return this.lastlogin;
	}

	public void setLastActive(Date lastlogin) {
		this.lastlogin = lastlogin;
}

}
