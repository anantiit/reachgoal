package com.naidu.parkinglot.fee;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.naidu.parkinglot.parkingspot.ParkingSpotType;

public interface FeeRepository extends JpaRepository<Fee, Integer> {
	List<Fee> findByFeePlanTypeAndFeePlanParkingSpotType(FeeModelType modelType, ParkingSpotType parkingSpotType);
}
