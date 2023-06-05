package com.problemsolving.geometry;

import java.util.ArrayList;
import java.util.List;

public class Battleship {

	public static void main(String args[]) {
		Ship ship1 = new Ship(1, 1, 2, 1, 4);
		List<Ship> ships = new ArrayList<Ship>();
		ships.add(ship1);
		System.out.println(hit(1, 3, ships));
		System.out.println(hit(1, 4, ships));
		System.out.println(hit(1, 4, ships));
		System.out.println(hit(2, 4, ships));
		System.out.println(hit(3, 4, ships));
		System.out.println(hit(1, 2, ships));
		System.out.println(hit(1, 2, ships));
		System.out.println(hit(1, 1, ships));
	}

	/*
	 * hit(0,0) -> MISS hit(1,1) -> HIT hit(1,1) -> HIT hit(1,0) -> SUNK hit(1,1) ->
	 * SUNK
	 * 
	 * given ship positions on board of n*n and given x,y to hit method find out
	 * whether it hits ship or not
	 */

	public static String hit(int x, int y, List<Ship> ships) {
		String shipStatus = "MISS";
		for (Ship ship : ships) {
			if (x >= ship.startX && ship.endX > x && y >= ship.startY && ship.endY > y) {
				shipStatus = "HIT";
				if ((ship.isVertical && ship.hitCells[y - ship.startY])
						|| (!ship.isVertical && ship.hitCells[x - ship.startX])) {
					return shipStatus;
				}
				if (ship.isVertical) {
					ship.hitCells[y - ship.startY] = true;
				} else {
					ship.hitCells[x - ship.startX] = true;
				}
				ship.numberOfHitCells++;
			}
			if (ship.numberOfCells == ship.numberOfHitCells) {
				shipStatus = "SUNK";
			}
		}

		return shipStatus;
	}
}

class Ship {
	int shipId;
	int startX;
	int startY;
	int endX;
	int endY;
	boolean[] hitCells;
	int numberOfCells;
	int numberOfHitCells;
	boolean isVertical;

	public Ship(int shipId, int startX, int endX, int startY, int endY) {
		super();
		this.shipId = shipId;
		this.startX = startX;
		this.startY = startY;
		this.endX = endX;
		this.endY = endY;
		this.numberOfCells = endX - startX;
		if (numberOfCells < (endY - startY)) {
			isVertical = true;
			numberOfCells = endY - startY;
		}
		this.hitCells = new boolean[numberOfCells];
		this.numberOfHitCells = 0;
	}

}
