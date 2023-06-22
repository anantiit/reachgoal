package lld.splitwise.account;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public class User {
	private String userId;
	private String name;
	private String emailId;
	private String phoneNum;

}
