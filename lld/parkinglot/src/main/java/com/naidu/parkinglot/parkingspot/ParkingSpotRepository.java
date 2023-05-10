package com.naidu.parkinglot.parkingspot;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ParkingSpotRepository extends JpaRepository<ParkingSpot, Integer> {
	@Query("SELECT p FROM ParkingSpot p WHERE p.isCurrentlyOccupied = false AND p.parkingLotId = :parkingLotId AND p.type = :parkingSpotType ORDER BY p.parkingSpotId ASC LIMIT 1")
	Optional<ParkingSpot> findFirstUnoccupiedByParkingLotIdAndType(@Param("parkingLotId") Integer parkingLotId,
			@Param("parkingSpotType") ParkingSpotType parkingSpotType);

	ParkingSpot findByParkingSpotIdAndParkingLotId(int parkingSpotId, int parkingLotId);

//	@Query(value = "SELECT * FROM parking_spot p WHERE p.is_currently_occupied = false AND p.parking_lot_id = :parkingLotId AND p.type = :parkingSpotType ORDER BY p.id ASC LIMIT 1", nativeQuery = true)
//	Optional<ParkingSpot> findFirstUnoccupiedByParkingLotIdAndType(@Param("parkingLotId") Integer parkingLotId,
//			@Param("parkingSpotType") ParkingSpotType parkingSpotType);

}
