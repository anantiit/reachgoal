package lld.snakeladder;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Board {
	private int size;
	private List<Snake> snakes;
	private List<Ladder> ladders;
}
