package lld.elevator.elevatorcar;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import lld.elevator.Building;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ElevatorController {
	Building building;
	List<ElevatorCarService> elevatorCarServices;

	public ElevatorController(Building building) {
		this.building = building;
		this.elevatorCarServices = new ArrayList<ElevatorCarService>();
	}

	ExecutorService executorService = Executors.newFixedThreadPool(5);

	public void launchElevatorServices() {
		List<ElevatorCar> elevatorCars = building.getElevatorCars();

		for (ElevatorCar elevatorCar : elevatorCars) {
			ElevatorCarService elevatorCarService = new ElevatorCarService(building, elevatorCar);
			elevatorCarServices.add(elevatorCarService);
		}
		elevatorCarServices.stream().forEach(executorService::submit);

	}

	public void shutDownElevators() {
		executorService.shutdown();
	}

}
