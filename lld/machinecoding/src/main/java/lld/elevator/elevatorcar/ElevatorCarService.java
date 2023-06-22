package lld.elevator.elevatorcar;

import java.util.List;

import lld.elevator.Building;
import lld.elevator.Floor;
import lld.elevator.button.InternalButton;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ElevatorCarService implements Runnable {
	Building building;
	ElevatorCar elevatorCar;

	@Override
	public void run() {
		System.out.println("Elevator:" + elevatorCar.getId() + " is launched.");
		boolean[] scanArray = elevatorCar.getScanArrray();
		boolean[] floorScanArrray = building.getFloorScanArrray();
		List<InternalButton> buttonList = elevatorCar.getButtonList();
		List<Floor> floors = building.getFloors();
		while (true) {
			loopAndCheckUntilElevatorState();
			int i = 0;
			try {
				serveUpStreamFloors(scanArray, floorScanArrray, i, floors, buttonList);
				serveDownStreamFloors(scanArray, floorScanArrray, i, floors, buttonList);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

	private void serveDownStreamFloors(boolean[] scanArray, boolean[] floorScanArrray, int i, List<Floor> floors,
			List<InternalButton> buttonList) throws InterruptedException {
		while (i >= 0 && elevatorCar.elevatorCarState == ElevatorCarState.MOVING_DOWN) {
			if (scanArray[i] || floorScanArrray[i]) {
				ElevatorCarService.serveFloor(buttonList.get(i), floors.get(i), elevatorCar, building);
			}
			if (i >= elevatorCar.getCurrentMinFloorToServe()) {
				if (anyFloorSetInScanArray(scanArray, floorScanArrray)) {
					elevatorCar.elevatorCarState = ElevatorCarState.MOVING_UP;
					System.out.println(
							"Elevator " + elevatorCar.getId() + "Direction changed to " + elevatorCar.elevatorCarState);
				} else {
					elevatorCar.elevatorCarState = ElevatorCarState.IDLE;
				}
			}
			i = i - 1;
		}
	}

	private void serveUpStreamFloors(boolean[] scanArray, boolean[] floorScanArrray, int i, List<Floor> floors,
			List<InternalButton> buttonList) throws InterruptedException {
		while (i <= elevatorCar.maxFloor && elevatorCar.elevatorCarState == ElevatorCarState.MOVING_UP) {
			if (scanArray[i] || floorScanArrray[i]) {
				ElevatorCarService.serveFloor(buttonList.get(i), floors.get(i), elevatorCar, building);
			}
			if (i >= elevatorCar.getCurrentMaxFloorToServe()) {
				if (anyFloorSetInScanArray(scanArray, floorScanArrray)) {
					elevatorCar.elevatorCarState = ElevatorCarState.MOVING_DOWN;
					System.out.println(
							"Elevator " + elevatorCar.getId() + "Direction changed to " + elevatorCar.elevatorCarState);
				} else {
					elevatorCar.elevatorCarState = ElevatorCarState.IDLE;
				}
			}
			i = i + 1;
		}
	}

	private boolean anyFloorSetInScanArray(boolean[] scanArray, boolean[] floorScanArrray) {
		for (int i = 0; i < scanArray.length; i++) {
			if (scanArray[i] || floorScanArrray[i])
				return true;
		}
		return false;
	}

	public static void serveFloor(InternalButton button, Floor floor, ElevatorCar elevator, Building building)
			throws InterruptedException {
		int floorNum = floor.getFloorNum();
		boolean[] scanArray = elevator.getScanArrray();
		boolean[] floorScanArray = building.getFloorScanArrray();
		if (elevator.elevatorCarState == ElevatorCarState.MOVING_DOWN) {
			floor.setDownButtonPressed(false);
		}
		if (elevator.elevatorCarState == ElevatorCarState.MOVING_UP) {
			floor.setUpButtonPressed(false);
		}
		scanArray[floorNum] = false;
		floorScanArray[floorNum] = false;
		// TODO Floor scan array does not belong to elevator it
		// belongs to building, one floor scan array for all
		// elevators. So we need get lock before changing it
		if (button != null) {
			button.setPressed(false);
		}
		elevator.setCurrentFloorNum(floorNum);
		Thread.sleep(5000);
		System.out.println("Elevator " + elevator.getId() + " serving floor: " + floorNum + ". Doors opened & closed");
	}

	private void loopAndCheckUntilElevatorState() {
		while (elevatorCar.elevatorCarState == ElevatorCarState.IDLE) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
