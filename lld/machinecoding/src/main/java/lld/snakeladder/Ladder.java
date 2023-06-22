package lld.snakeladder;

import lombok.ToString;

@ToString
public class Ladder {
	int start;
	int end;

	Ladder(int start, int end) {
		if (start > end) {
			throw new RuntimeException("Invalid ladder: end of the ladder should be greater than its start. start: "
					+ start + " end:" + end);
		}
		this.start = start;
		this.end = end;
	}

}