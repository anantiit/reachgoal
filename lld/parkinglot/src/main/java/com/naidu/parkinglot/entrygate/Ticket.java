package com.naidu.parkinglot.entrygate;

import java.time.LocalDateTime;

import com.naidu.parkinglot.fee.FeeModelType;
import com.naidu.parkinglot.parkingspot.ParkingSpotType;
import com.naidu.parkinglot.payment.PaymentMode;
import com.naidu.parkinglot.vehicle.VehicleType;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Ticket {
	public Ticket(String vehicleRegNo, LocalDateTime startTime, VehicleType vehicleType, int parkingSpotId,
			int parkingLotId, ParkingSpotType parkingSpotType, FeeModelType feeModelType) {
		this.vehicleRegNo = vehicleRegNo;
		this.startTime = startTime;
		this.vehicleType = vehicleType;
		this.parkingSpotId = parkingSpotId;
		this.parkingLotId = parkingLotId;
		this.parkingSpotType = parkingSpotType;
		this.feeModelType = feeModelType;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	int id;
	String vehicleRegNo;
	@Enumerated(EnumType.STRING)
	VehicleType vehicleType;
	@Enumerated(EnumType.STRING)
	ParkingSpotType parkingSpotType;
	FeeModelType feeModelType;
	LocalDateTime startTime;
	int parkingSpotId;
	int parkingLotId;
	LocalDateTime endTime;
	double parkingFee;
	@Enumerated(EnumType.STRING)
	PaymentMode paymentMode;
}
