package com.naidu.parkinglot.fee;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class Fee {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;
	@Embedded
	FeePlan feePlan;
	int startSecs;
	int endSecs;
	boolean isFlatFee;
	boolean isFlatPerHour;
	boolean isFlatPerDay;
	boolean isIntervalFee;
	boolean isEndInclusive;
	double amount;

	@Override
	public String toString() {
		return "Fee [id=" + id + ", feePlan=" + feePlan + ", startSecs=" + startSecs + ", endSecs=" + endSecs
				+ ", amount=" + amount + "]";
	}

}
