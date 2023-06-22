package lld.snakeladder;

import lombok.ToString;

@ToString
public class Snake {
	int start;
	int end;

	Snake(int start, int end) {
		if (start < end) {
			throw new RuntimeException("Invalid snake: Start of the snake should be greater than its end.");
		}
		this.start = start;
		this.end = end;
	}

}
