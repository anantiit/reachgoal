package com.naidu.parkinglot.parkingspot;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Repository
public class ParkingSpotDao {
	@PersistenceContext
	EntityManager entityManager;
	private ParkingSpotRepository parkingSpotRepository;

	@Transactional
	public ParkingSpot getRandomParkingSpotWhichIsNotOccupied(int parkingLotId, ParkingSpotType parkingSpotType) {
		Optional<ParkingSpot> parkingSpot = parkingSpotRepository.findFirstUnoccupiedByParkingLotIdAndType(parkingLotId,
				parkingSpotType);
		ParkingSpot vacantParking = parkingSpot.orElseThrow();
		vacantParking.setCurrentlyOccupied(true);
		entityManager.merge(vacantParking);
		return vacantParking;
	}

	public ParkingSpot findByParkingSpotIdAndParkingLotId(int parkingSpotId, int parkingLotId) {
		return parkingSpotRepository.findByParkingSpotIdAndParkingLotId(parkingSpotId, parkingLotId);
	}

	@Transactional
	public void update(ParkingSpot parkingSpot) {
		// TODO Auto-generated method stub
		entityManager.merge(parkingSpot);

	}
}
