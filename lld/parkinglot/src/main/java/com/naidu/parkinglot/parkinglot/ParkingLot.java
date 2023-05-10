package com.naidu.parkinglot.parkinglot;

import java.util.Map;

import com.naidu.parkinglot.fee.FeeModelType;
import com.naidu.parkinglot.parkingspot.ParkingSpotType;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class ParkingLot {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;
	@Enumerated(EnumType.STRING)
	ParkingLotType parkingLotType;
	@Enumerated(EnumType.STRING)
	FeeModelType feeType;
	int totalSize;
	@Transient
	Map<ParkingSpotType, Integer> parkingSpotMap;

	public ParkingLot(ParkingLotType parkingLotType, int totalSize, FeeModelType feeType,
			Map<ParkingSpotType, Integer> parkingSpotMap) {
		this.parkingLotType = parkingLotType;
		this.totalSize = totalSize;
		this.parkingSpotMap = parkingSpotMap;
		this.feeType = feeType;
	}
}
