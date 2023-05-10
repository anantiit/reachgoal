package com.naidu.parkinglot.fee;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.naidu.parkinglot.parkingspot.ParkingSpotType;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.AllArgsConstructor;

@Repository
@AllArgsConstructor
public class FeeDao {
	@PersistenceContext
	EntityManager entityManager;
	FeeRepository feeRepository;

	public void saveAll(List<Fee> feeModel) {
		System.out.println(feeModel);
		feeRepository.saveAll(feeModel);
	}

	public List<Fee> getFeeListByModelTypeAndParkingSpotType(FeeModelType modelType, ParkingSpotType parkingSpotType) {
		List<Fee> feeList = feeRepository.findByFeePlanTypeAndFeePlanParkingSpotType(modelType, parkingSpotType);
		return feeList;
	}
}
