package lld.snakeladder;

import java.util.Random;

public class Dice {

	Random random;
	int diceSize;

	Dice(int diceSize) {
		this.random = new Random();
		this.diceSize = diceSize;
	}

	public int getNextMove() {
		return random.nextInt(1, diceSize + 1);
	}

}
