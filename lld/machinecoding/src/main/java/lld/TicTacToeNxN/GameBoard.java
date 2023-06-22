package lld.TicTacToeNxN;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class GameBoard {

	char[][] board;
	int boardSize;
	Queue<Player> playerQueue;
	Scanner input;

	public GameBoard(int boardSize, Player[] players) {
		this.boardSize = boardSize;
		this.board = new char[boardSize][boardSize];
		playerQueue = new LinkedList<>();
		playerQueue.offer(players[0]);
		playerQueue.offer(players[1]);
		input = new Scanner(System.in);
	}

	private void printBoard() {
		for (int i = 0; i < 2 * board.length - 1; i++) {
			for (int j = 0; j < 2 * board[0].length - 1; j++) {
				if (i % 2 == 0 && j % 2 != 0)
					System.out.print('|');
				else if (i % 2 != 0 && j % 2 == 0)
					System.out.print('-');
				else if (i % 2 != 0 && j % 2 != 0)
					System.out.print('+');
				else
					System.out.print(board[i / 2][j / 2]);
			}
			System.out.println();
		}
		/*
		 * | | -+-+- | | -+-+- | |
		 */
	}

	public void startGame() {
		int count = 0;
		while (true) {
			count++;
			if (count == ((boardSize * boardSize) + 1)) {
				System.out.println("Match draw");
				break;
			}
			Player p = playerQueue.poll();
			int position = getUserInput(p);
			int row = (position - 1) / boardSize;
			int col = (position - 1) % boardSize;

			board[row][col] = p.getPlayerSymbol();
			System.out.println("Board after the move");
			if (count >= boardSize && checkEndGame(p, row, col))
				break;
			playerQueue.offer(p);
		}
	}

	private int getUserInput(Player p) {
		printBoard();
		System.out.println(p.getPlayerName() + " Please Enter a number between 1 - " + (boardSize * boardSize));
		int val = input.nextInt();
		while (!validateInput(val)) {
			printBoard();
			System.out.println("Wrong Position - " + p.getPlayerName() + " Please Enter a number between 1 - "
					+ (boardSize * boardSize));
			val = input.nextInt();
		}
		return val;
	}

	private boolean validateInput(int val) {

		if (val < 1 || val > (boardSize * boardSize))
			return false;
		int row = (val - 1) / boardSize;
		int col = (val - 1) % boardSize;
		if ((int) board[row][col] != 0)
			return false;

		return true;
	}

	private boolean checkEndGame(Player p, int row, int col) {
		String winString = "";
		for (int i = 0; i < boardSize; i++) {
			winString += String.valueOf(p.getPlayerSymbol());
		}

		String rowString = "";
		String colString = "";
		String diagonalString = "";
		String reverseDiagonalString = "";
		for (int i = 0; i < board.length; i = i++) {
			rowString += board[row][i];
			colString += board[i][col];
			if (row == col) {
				diagonalString += board[i][i];
			}
			if ((row + col) == board.length - 1) {
				reverseDiagonalString += board[board.length - 1 - i][i];
			}
		}

		if (winString.equals(rowString) || winString.equals(colString) || winString.equals(diagonalString)
				|| winString.equals(reverseDiagonalString)) {
			System.out.println(p.getPlayerName() + " has won the Game");
			return true;
		}

		return false;
	}

}
