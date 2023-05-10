package com.naidu.parkinglot.parkinglot;

import java.util.Map;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Component;

import com.naidu.parkinglot.parkingspot.ParkingSpot;
import com.naidu.parkinglot.parkingspot.ParkingSpotType;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@PersistenceContext
@AllArgsConstructor
@Component
public class ParkingLotDao {
	EntityManager entityManager;

	public ParkingLot getParkingLotById(int id) {
		ParkingLot parkingLot = entityManager.find(ParkingLot.class, id);
		if (parkingLot == null) {
			throw new NoSuchElementException();
		}
		return parkingLot;
	}

	@Transactional
	public void saveParkingLot(ParkingLot parkingLot) {
		entityManager.persist(parkingLot);
		entityManager.flush(); // synchronize with database
		entityManager.refresh(parkingLot);
		addParkingSpots(parkingLot);
	}

	private void addParkingSpots(ParkingLot parkingLot) {
		Map<ParkingSpotType, Integer> parkingspotMap = parkingLot.getParkingSpotMap();
		// System.out.println("parkingspotMap:" + parkingspotMap);
		int parkingSpotCount = 0;
		for (ParkingSpotType type : parkingspotMap.keySet()) {
			int parkingSpotSizePerType = parkingspotMap.get(type);
			ParkingSpot parkingSpot = null;
			for (int i = parkingSpotCount + 1; i <= parkingSpotCount + parkingSpotSizePerType; i++) {
				parkingSpot = new ParkingSpot(i, type, parkingLot.getId(), false);
				// System.out.println(parkingSpot);
				entityManager.persist(parkingSpot);
			}
			parkingSpotCount = parkingSpotCount + parkingSpotSizePerType;
		}
	}

}
