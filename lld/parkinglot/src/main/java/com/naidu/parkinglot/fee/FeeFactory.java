package com.naidu.parkinglot.fee;

public class FeeFactory {
	public static FeeModel getInstance(FeeModelType type, FeeDao feeDao) {
		switch (type) {
		case MALL: // Some malls can have different fee model so this should be given as input
					// instead of deciding at factory
			return new FixedFlatFeeModel(true, feeDao); // MALL
		case STADIUM:
			return new CumulativeIntervalAndFlatModel(true, feeDao); // Stadium
		case AIRPORT:
			return new NonCumulativeIntervalAndFlatModel(false, feeDao); // Airport
		default:
			return new FixedFlatFeeModel(true, feeDao);
		}
	}
}
