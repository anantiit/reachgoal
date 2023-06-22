package lld.snakeladder;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
@Setter
public class Player {
	int id;
	String name;
	int currentPosition;

	public Player(int id, String name) {
		this.id = id;
		this.name = name;
		this.currentPosition = 1;
	}
}
