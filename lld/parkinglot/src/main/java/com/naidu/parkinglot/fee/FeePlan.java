package com.naidu.parkinglot.fee;

import java.io.Serializable;

import com.naidu.parkinglot.parkingspot.ParkingSpotType;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Embeddable
public class FeePlan implements Serializable {
	@Column(name = "fee_plan_id", nullable = false)
	int id;
	@Enumerated(EnumType.STRING)
	FeeModelType type;
	@Enumerated(EnumType.STRING)
	ParkingSpotType parkingSpotType;

	@Override
	public String toString() {
		return "FeePlan [id=" + id + ", type=" + type + ", parkingSpotType=" + parkingSpotType + "]";
	}

}
