package lld.snakeladder;

import java.util.List;

public class SnakeLadderApplication {
	public static void main(String args[]) {
		SnakeLadderService snakeLadderService = new SnakeLadderService();
		List<Player> players = List.of(new Player(1, "player1"), new Player(2, "player2"), new Player(3, "player3"),
				new Player(4, "player4"), new Player(5, "player5"));
		int[] snakeStart = { 20, 45, 54, 67 };
		int[] snakeEnd = { 3, 20, 34, 11 };
		int[] ladderStart = { 11, 35, 65, 78 };
		int[] ladderEnd = { 21, 56, 76, 84 };

		Game game = snakeLadderService.initializeGame(100, 6, snakeStart, snakeEnd, ladderStart, ladderEnd, players);
		snakeLadderService.startGame(game);

	}

}
