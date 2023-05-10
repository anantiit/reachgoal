package com.naidu.parkinglot.fee;

import java.time.Duration;
import java.util.List;

import com.naidu.parkinglot.Constants;
import com.naidu.parkinglot.entrygate.Ticket;

public class NonCumulativeIntervalAndFlatModel extends FeeModel {

	public NonCumulativeIntervalAndFlatModel(boolean isCumulative, FeeDao feeDao) {
		super(feeDao, isCumulative);
	}

	@Override
	public double calculateCost(Ticket ticket) {
		FeeModelType type = ticket.getFeeModelType();
		List<Fee> feeList = feeDao.getFeeListByModelTypeAndParkingSpotType(type, ticket.getParkingSpotType());
		Duration duration = Duration.between(ticket.getStartTime(), ticket.getEndTime());
		long durationInSeconds = duration.getSeconds();
		if (durationInSeconds < 0) {
			throw new RuntimeException("StartTime should be less than the endTime");
		} else if (durationInSeconds == 0) {
			return 0;
		}
		for (Fee fee : feeList) {
			if (fee.startSecs <= durationInSeconds && fee.endSecs >= durationInSeconds) {
				return fee.amount;
			} else if (fee.startSecs <= durationInSeconds && fee.isFlatFee()) {
				if (fee.isFlatPerHour()) {
					int numHours = (int) Math.ceil((double) durationInSeconds / Constants.SECS_IN_HOUR);
					return numHours * fee.amount;
				} else if (fee.isFlatPerDay()) {
					int numDays = (int) Math.ceil((double) durationInSeconds / Constants.SECS_IN_DAY);
					return numDays * fee.amount;
				}
			}
		}
		throw new RuntimeException("FeeModel mapping is wrong");
	}

}
