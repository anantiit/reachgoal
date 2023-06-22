package lld.snakeladder;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class SnakeLadderService {

	public Game initializeGame(int boardSize, int diceSize, int[] snakeStart, int[] snakeEnd, int[] ladderStart,
			int[] ladderEnd, List<Player> players) throws IllegalArgumentException {
		Dice dice = new Dice(diceSize);
		Board board = initializeBoard(boardSize, snakeStart, snakeEnd, ladderStart, ladderEnd);
		Queue<Player> playerQueue = new LinkedList<Player>();
		playerQueue.addAll(players);
		return new Game(playerQueue, board, dice, players);
	}

	private Board initializeBoard(int boardSize, int[] snakeStart, int[] snakeEnd, int[] ladderStart, int[] ladderEnd) {
		List<Snake> snakes = validateAndInitializeSnakes(boardSize, snakeStart, snakeEnd);
		List<Ladder> ladders = validateAndInitializeLadders(boardSize, snakeStart, ladderStart, ladderEnd);
		Board board = new Board(boardSize, snakes, ladders);
		return board;
	}

	private List<Ladder> validateAndInitializeLadders(int boardSize, int[] snakeStart, int[] ladderStart,
			int[] ladderEnd) {
		int m = ladderStart.length;
		List<Ladder> ladders = new ArrayList<Ladder>();
		for (int i = 0; i < m; i++) {
			ladders.add(new Ladder(ladderStart[i], ladderEnd[i]));
			if (boardSize < ladderStart[i] || boardSize < ladderEnd[i]) {
				throw new IllegalArgumentException(
						"ladders cannot cross board size. snake: " + ladderStart[i] + "-" + ladderEnd[i]);
			}
		}
		return ladders;
	}

	private List<Snake> validateAndInitializeSnakes(int boardSize, int[] snakeStart, int[] snakeEnd) {
		int n = snakeStart.length;
		List<Snake> snakes = new ArrayList<Snake>();
		for (int i = 0; i < n; i++) {
			if (boardSize < snakeStart[i] || boardSize < snakeEnd[i]) {
				throw new IllegalArgumentException(
						"snakes cannot cross board size. snake: " + snakeStart[i] + "-" + snakeEnd[i]);
			}
			snakes.add(new Snake(snakeStart[i], snakeEnd[i]));
		}
		return snakes;
	}

	public void startGame(Game game) {
		Queue<Player> playerQueue = game.getPlayerQueue();
		Dice dice = game.getDice();
		Board board = game.getBoard();
		int boardSize = game.board.getSize();
		List<Ladder> ladders = board.getLadders();
		List<Snake> snakes = board.getSnakes();
		while (playerQueue.size() != 1) {
			Player player = playerQueue.poll();
			int playerCurrentposition = player.getCurrentPosition();
			int diceValue = dice.getNextMove();
			int nextPosition = playerCurrentposition + diceValue;
			nextPosition = checForSnakesAndLaddersModifyNextPosition(ladders, snakes, player, nextPosition);
			if (nextPosition == game.board.getSize()) {
				System.out.println(player + " has won and exititing the match");
				System.out.println("players left in queue:" + playerQueue);
				continue;
			} else if (nextPosition < boardSize) {
				System.out.println(player + " moved to : " + nextPosition);
				player.setCurrentPosition(nextPosition);
			}
			playerQueue.offer(player);
		}
	}

	private int checForSnakesAndLaddersModifyNextPosition(List<Ladder> ladders, List<Snake> snakes, Player player,
			int nextPosition) {
		for (Snake snake : snakes) {
			if (snake.start == nextPosition) {
				nextPosition = snake.end;
				System.out.println(player + " got bitten by snake " + snake);
				break;
			}
		}
		for (Ladder ladder : ladders) {
			if (ladder.start == nextPosition) {
				nextPosition = ladder.end;
				System.out.println(player + " got the ladder " + ladder);
				break;
			}
		}
		return nextPosition;
	}
}
