package lld.elevator.elevatorcar;

import java.util.List;

import lld.elevator.button.InternalButton;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ElevatorCar {
	int id;
	volatile ElevatorCarState elevatorCarState;
	int currentFloorNum;
	List<InternalButton> buttonList;
	int maxCapacity;
	int numFloors; // In a building which has more than 50 floors it is efficient serve some floors
					// with one elevator and serve other using another. For that offset also should
					// be added for each elevator car
	int maxFloor;
	boolean[] scanArrray;
	int currentMaxFloorToServe;
	int currentMinFloorToServe;

	public ElevatorCar(int id, List<InternalButton> buttonList, int maxCapacity, int numFloors) {
		super();
		this.id = id;
		this.buttonList = buttonList;
		this.maxCapacity = maxCapacity;
		this.numFloors = numFloors;
		this.elevatorCarState = ElevatorCarState.IDLE;
		this.currentFloorNum = 0;
		this.currentMaxFloorToServe = Integer.MIN_VALUE;
		this.currentMinFloorToServe = Integer.MAX_VALUE;
		this.scanArrray = new boolean[numFloors];
		this.maxFloor = numFloors;
	}

}
