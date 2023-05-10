package com.naidu.parkinglot.entrygate;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.naidu.parkinglot.vehicle.Vehicle;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class EntryGateController {

	EntryGateService entryGateService;

	@PostMapping("/parkinglot/park/{parkingLotId}")
	public Ticket parkVehicle(@PathVariable int parkingLotId, @RequestBody Vehicle vehicle,
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime) {
		return entryGateService.parkVehicle(parkingLotId, vehicle, startTime);
	}
}
