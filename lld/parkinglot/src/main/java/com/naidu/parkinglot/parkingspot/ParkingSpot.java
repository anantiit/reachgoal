package com.naidu.parkinglot.parkingspot;

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
public class ParkingSpot {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;
	int parkingSpotId;
	@Enumerated(EnumType.STRING)
	ParkingSpotType type;
	int parkingLotId;
	boolean isCurrentlyOccupied;

	public ParkingSpot(int parkingSpotId, ParkingSpotType type, int parkingLotId, boolean isCurrentlyOccupied) {
		super();
		this.parkingSpotId = parkingSpotId;
		this.type = type;
		this.parkingLotId = parkingLotId;
		this.isCurrentlyOccupied = isCurrentlyOccupied;
	}

}
