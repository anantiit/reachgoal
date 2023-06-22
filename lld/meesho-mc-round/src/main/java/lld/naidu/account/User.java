package lld.naidu.account;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public class User {
	private int userId;
	private String userName;
	private String password; // salted hash would be stored
	private String name;
	private String emailId;
	private String phoneNum;
	private UserRole userRole;

	public boolean validateUser(User user) {
		// we can check for valid emailid , phone num etc
		return true;

	}

	@Override
	public boolean equals(Object prev) {
		User prevUser = (User) prev;
		return (this.emailId.equalsIgnoreCase(prevUser.emailId));

	}
}
