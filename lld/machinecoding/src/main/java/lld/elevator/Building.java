package lld.elevator;

import java.util.List;

import lld.elevator.elevatorcar.ElevatorCar;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Building {
	List<Floor> floors;
	List<ElevatorCar> elevatorCars;
	int numFloors;
	boolean[] floorScanArrray;
}
