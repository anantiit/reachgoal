package lld.splitwise.account;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public class Group {
	private String name;
	private String id;
	private Set<User> members;
}
