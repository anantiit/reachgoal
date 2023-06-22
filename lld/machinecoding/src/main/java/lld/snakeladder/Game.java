package lld.snakeladder;

import java.util.List;
import java.util.Queue;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Game {
	Queue<Player> playerQueue;
	Board board;
	Dice dice;
	List<Player> players;
}
