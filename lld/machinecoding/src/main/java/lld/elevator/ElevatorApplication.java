package lld.elevator;

import java.util.ArrayList;
import java.util.List;

import lld.elevator.button.InternalButton;
import lld.elevator.dispatcher.DispatcherService;
import lld.elevator.elevatorcar.ElevatorCar;
import lld.elevator.elevatorcar.ElevatorController;

public class ElevatorApplication {

	public static void main(String args[]) throws InterruptedException {
		List<Floor> floorList = new ArrayList<>();
		List<InternalButton> buttonList = new ArrayList<>();
		for (int i = 0; i < 5; i++) {
			Floor floor = new Floor(i, false, false);
			floorList.add(floor);
			buttonList.add(new InternalButton(i, false));
		}
		List<ElevatorCar> elevators = new ArrayList<ElevatorCar>();
		for (int i = 0; i < 2; i++) {
			ElevatorCar elevatorCar = new ElevatorCar(i, buttonList, 200, 5);
			elevators.add(elevatorCar);
		}
		boolean[] floorScanArrray = new boolean[floorList.size()];
		Building building = new Building(floorList, elevators, 5, floorScanArrray);
		DispatcherService dispatcherService = new DispatcherService(building);
		ElevatorController elevatorController = new ElevatorController(building);
		elevatorController.launchElevatorServices();
		dispatcherService.processFloorRequest(0);
		Thread.sleep(3000);
		dispatcherService.processFloorRequest(2);
		buttonList.get(4).setPressed(true);
		dispatcherService.processInternalRequest(elevators.get(0), buttonList.get(4));
		buttonList.get(2).setPressed(true);
		dispatcherService.processInternalRequest(elevators.get(1), buttonList.get(2));
		dispatcherService.processFloorRequest(3);
		Thread.sleep(3000);
		dispatcherService.processFloorRequest(2);
		buttonList.get(4).setPressed(true);
		dispatcherService.processInternalRequest(elevators.get(0), buttonList.get(4));
		Thread.sleep(3000);
		dispatcherService.processFloorRequest(4);
		Thread.sleep(3000);
		dispatcherService.processFloorRequest(0);

		buttonList.get(3).setPressed(true);
		dispatcherService.processInternalRequest(elevators.get(0), buttonList.get(4));
		// elevatorController.shutDownElevators();
	}
}
