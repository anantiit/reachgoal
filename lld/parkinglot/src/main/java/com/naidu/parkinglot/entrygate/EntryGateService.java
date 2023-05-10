package com.naidu.parkinglot.entrygate;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.naidu.parkinglot.fee.FeeModel;
import com.naidu.parkinglot.parkinglot.ParkingLot;
import com.naidu.parkinglot.parkinglot.ParkingLotDao;
import com.naidu.parkinglot.parkingspot.ParkingSpot;
import com.naidu.parkinglot.parkingspot.ParkingSpotDao;
import com.naidu.parkinglot.parkingspot.ParkingSpotType;
import com.naidu.parkinglot.vehicle.Vehicle;
import com.naidu.parkinglot.vehicle.VehicleType;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class EntryGateService {
	ParkingSpotDao parkingSpotDao;
	ParkingLotDao parkingLotDao;
	TicketDao ticketDao;

	public Ticket parkVehicle(int parkingLotId, Vehicle vehicle, LocalDateTime startTime) {
		ParkingLot parkingLot = parkingLotDao.getParkingLotById(parkingLotId);
		ParkingSpot parkingSpot = searchVacantParkingSpot(parkingLotId, vehicle.getVehicleType());

		if (startTime == null) {
			startTime = LocalDateTime.now();
		}
		Ticket ticket = new Ticket(vehicle.getRegNum(), startTime, vehicle.getVehicleType(),
				parkingSpot.getParkingSpotId(), parkingLotId, parkingSpot.getType(), parkingLot.getFeeType());
		ticketDao.save(ticket);
		return ticket;

	}

	private ParkingSpot searchVacantParkingSpot(int parkingLotId, VehicleType vehicleType) {
		ParkingSpotType parkingSpotType = FeeModel.vehicleSpotMapping.get(vehicleType);
		ParkingSpot parkingSpot = parkingSpotDao.getRandomParkingSpotWhichIsNotOccupied(parkingLotId, parkingSpotType);
		return parkingSpot;
	}

}
