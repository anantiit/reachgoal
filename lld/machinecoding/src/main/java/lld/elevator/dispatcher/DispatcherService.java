package lld.elevator.dispatcher;

import java.util.List;

import lld.elevator.Building;
import lld.elevator.Floor;
import lld.elevator.button.InternalButton;
import lld.elevator.elevatorcar.ElevatorCar;
import lld.elevator.elevatorcar.ElevatorCarService;
import lld.elevator.elevatorcar.ElevatorCarState;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class DispatcherService {
	Building building;

	public void processInternalRequest(ElevatorCar elevator, InternalButton button) throws InterruptedException {
		if (button.isPressed()) {
			int k = button.getButtonNum();
			if (k == elevator.getCurrentFloorNum()) {
				ElevatorCarService.serveFloor(button, building.getFloors().get(k), elevator, building);
				return;
			}
			setElevatorMovingState(elevator, k);
		}
	}

	private void setElevatorMovingState(ElevatorCar elevator, int floorNum) {
		elevator.getScanArrray()[floorNum] = true;
		if (elevator.getCurrentMaxFloorToServe() < floorNum) {
			elevator.setCurrentMaxFloorToServe(floorNum);
		}
		if (elevator.getCurrentMinFloorToServe() > floorNum) {
			elevator.setCurrentMinFloorToServe(floorNum);
		}
		if (floorNum > elevator.getCurrentFloorNum()) {
			elevator.setElevatorCarState(ElevatorCarState.MOVING_UP);
		} else if (floorNum < elevator.getCurrentFloorNum()) {
			elevator.setElevatorCarState(ElevatorCarState.MOVING_DOWN);
		} else {
			elevator.setElevatorCarState(ElevatorCarState.NOT_IDLE);
		}
	}

	public void processFloorRequest(int floorNum) throws InterruptedException {
		Floor floor = building.getFloors().get(floorNum);
		List<ElevatorCar> elevators = building.getElevatorCars();
		if (!serveFromInFloorElevators(elevators, floor)) {
			elevators.stream().forEach(elevator -> setElevatorMovingState(elevator, floorNum));
		}
	}

	private boolean serveFromInFloorElevators(List<ElevatorCar> elevators, Floor floor) throws InterruptedException {
//		List<ElevatorCar> inFloorElevators = elevators.stream()
//				.filter(elevator -> elevator.getCurrentFloorNum() == floor.getFloorNum()).collect(Collectors.toList());
		for (ElevatorCar elevator : elevators) {
			if (elevator.getCurrentFloorNum() == floor.getFloorNum()) {
				ElevatorCarService.serveFloor(null, floor, elevator, building);
				return true;
			}
		}
		return false;
	}

}
