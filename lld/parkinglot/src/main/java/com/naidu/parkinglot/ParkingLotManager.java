package com.naidu.parkinglot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.naidu.parkinglot.fee.Fee;
import com.naidu.parkinglot.fee.FeeDao;
import com.naidu.parkinglot.fee.FeeModelType;
import com.naidu.parkinglot.fee.FeePlan;
import com.naidu.parkinglot.parkinglot.ParkingLot;
import com.naidu.parkinglot.parkinglot.ParkingLotDao;
import com.naidu.parkinglot.parkinglot.ParkingLotType;
import com.naidu.parkinglot.parkingspot.ParkingSpotType;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class ParkingLotManager implements CommandLineRunner {
	ParkingLotDao parkingLotDao;
	FeeDao feeDao;

	public void addParkingLot(ParkingLot parkingLot) {
		parkingLotDao.saveParkingLot(parkingLot);

	}

	public void loadParkingLots() {
		Map<ParkingSpotType, Integer> parkingSpotMap1 = new HashMap<ParkingSpotType, Integer>();
		parkingSpotMap1.put(ParkingSpotType.TWO_WHEELER, 2);
		ParkingLot parkingLot1 = new ParkingLot(ParkingLotType.MALL, 2, FeeModelType.MALL, parkingSpotMap1);
		addParkingLot(parkingLot1);

		Map<ParkingSpotType, Integer> parkingSpotMap2 = new HashMap<ParkingSpotType, Integer>();
		parkingSpotMap2.put(ParkingSpotType.TWO_WHEELER, 100);
		parkingSpotMap2.put(ParkingSpotType.FOUR_WHEELER, 80);
		parkingSpotMap2.put(ParkingSpotType.HEAVY, 10);
		ParkingLot parkingLot2 = new ParkingLot(ParkingLotType.MALL, 190, FeeModelType.MALL, parkingSpotMap2);
		addParkingLot(parkingLot2);

		Map<ParkingSpotType, Integer> parkingSpotMap3 = new HashMap<ParkingSpotType, Integer>();
		parkingSpotMap3.put(ParkingSpotType.TWO_WHEELER, 1000);
		parkingSpotMap3.put(ParkingSpotType.FOUR_WHEELER, 1500);
		ParkingLot parkingLot3 = new ParkingLot(ParkingLotType.STADIUM, 2500, FeeModelType.STADIUM, parkingSpotMap3);
		addParkingLot(parkingLot3);

		Map<ParkingSpotType, Integer> parkingSpotMap4 = new HashMap<ParkingSpotType, Integer>();
		parkingSpotMap4.put(ParkingSpotType.TWO_WHEELER, 200);
		parkingSpotMap4.put(ParkingSpotType.FOUR_WHEELER, 500);
		parkingSpotMap4.put(ParkingSpotType.HEAVY, 100);
		ParkingLot parkingLot4 = new ParkingLot(ParkingLotType.AIRPORT, 800, FeeModelType.AIRPORT, parkingSpotMap4);
		addParkingLot(parkingLot4);
	}

	private void loadFeeModels() {
		List<Fee> feeModel1 = new ArrayList<Fee>();
		FeePlan mallFeePlan1_1 = new FeePlan(1, FeeModelType.MALL, ParkingSpotType.TWO_WHEELER);
		FeePlan mallFeePlan1_2 = new FeePlan(1, FeeModelType.MALL, ParkingSpotType.FOUR_WHEELER);
		FeePlan mallFeePlan1_3 = new FeePlan(1, FeeModelType.MALL, ParkingSpotType.HEAVY);
		Fee mallFee1 = Fee.builder().feePlan(mallFeePlan1_1).startSecs(0).isFlatFee(true).isFlatPerHour(true).amount(10)
				.build();
		Fee mallFee2 = Fee.builder().feePlan(mallFeePlan1_2).startSecs(0).isFlatFee(true).isFlatPerHour(true).amount(20)
				.build();
		Fee mallFee3 = Fee.builder().feePlan(mallFeePlan1_3).startSecs(0).isFlatFee(true).isFlatPerHour(true).amount(50)
				.build();
		feeModel1.add(mallFee1);
		feeModel1.add(mallFee2);
		feeModel1.add(mallFee3);
		addFeeModel(feeModel1);
		List<Fee> feeModel2 = new ArrayList<Fee>();
		FeePlan stadiumFeePlan1_1 = new FeePlan(2, FeeModelType.STADIUM, ParkingSpotType.TWO_WHEELER);
		FeePlan stadiumFeePlan1_2 = new FeePlan(2, FeeModelType.STADIUM, ParkingSpotType.TWO_WHEELER);
		FeePlan stadiumFeePlan1_3 = new FeePlan(2, FeeModelType.STADIUM, ParkingSpotType.TWO_WHEELER);
		FeePlan stadiumFeePlan2_1 = new FeePlan(2, FeeModelType.STADIUM, ParkingSpotType.FOUR_WHEELER);
		FeePlan stadiumFeePlan2_2 = new FeePlan(2, FeeModelType.STADIUM, ParkingSpotType.FOUR_WHEELER);
		FeePlan stadiumFeePlan2_3 = new FeePlan(2, FeeModelType.STADIUM, ParkingSpotType.FOUR_WHEELER);

		Fee stadiumFee1_1 = Fee.builder().feePlan(stadiumFeePlan1_1).startSecs(0).endSecs(4 * 3600 - 1)
				.isIntervalFee(true).amount(30).build();
		Fee stadiumFee1_2 = Fee.builder().feePlan(stadiumFeePlan1_2).startSecs(4 * 3600).endSecs(12 * 3600 - 1)
				.isIntervalFee(true).amount(60).build();
		Fee stadiumFee1_3 = Fee.builder().feePlan(stadiumFeePlan1_3).startSecs(12 * 3600).isFlatFee(true)
				.isFlatPerHour(true).amount(100).build();
		Fee stadiumFee2_1 = Fee.builder().feePlan(stadiumFeePlan2_1).startSecs(0).endSecs(4 * 3600 - 1)
				.isIntervalFee(true).amount(60).build();
		Fee stadiumFee2_2 = Fee.builder().feePlan(stadiumFeePlan2_2).startSecs(4 * 3600).endSecs(12 * 3600 - 1)
				.isIntervalFee(true).amount(120).build();
		Fee stadiumFee2_3 = Fee.builder().feePlan(stadiumFeePlan2_3).startSecs(12 * 3600).isFlatFee(true)
				.isFlatPerHour(true).amount(200).build();

		feeModel2.add(stadiumFee1_1);
		feeModel2.add(stadiumFee1_2);
		feeModel2.add(stadiumFee1_3);
		feeModel2.add(stadiumFee2_1);
		feeModel2.add(stadiumFee2_2);
		feeModel2.add(stadiumFee2_3);
		addFeeModel(feeModel2);
		List<Fee> feeModel3 = new ArrayList<Fee>();

		FeePlan airportFeePlan1_1 = new FeePlan(3, FeeModelType.AIRPORT, ParkingSpotType.TWO_WHEELER);
		FeePlan airportFeePlan1_2 = new FeePlan(3, FeeModelType.AIRPORT, ParkingSpotType.TWO_WHEELER);
		FeePlan airportFeePlan1_3 = new FeePlan(3, FeeModelType.AIRPORT, ParkingSpotType.TWO_WHEELER);
		FeePlan airportFeePlan1_4 = new FeePlan(3, FeeModelType.AIRPORT, ParkingSpotType.TWO_WHEELER);
		FeePlan airportFeePlan2_1 = new FeePlan(3, FeeModelType.AIRPORT, ParkingSpotType.FOUR_WHEELER);
		FeePlan airportFeePlan2_2 = new FeePlan(3, FeeModelType.AIRPORT, ParkingSpotType.FOUR_WHEELER);
		FeePlan airportFeePlan2_3 = new FeePlan(3, FeeModelType.AIRPORT, ParkingSpotType.FOUR_WHEELER);
		Fee airportFee1_1 = Fee.builder().feePlan(airportFeePlan1_1).startSecs(0).isIntervalFee(true).amount(0).build();
		Fee airportFee1_2 = Fee.builder().feePlan(airportFeePlan1_2).startSecs(3600).endSecs(8 * 3600 - 1)
				.isIntervalFee(true).amount(40).build();
		Fee airportFee1_3 = Fee.builder().feePlan(airportFeePlan1_3).startSecs(8 * 3600).endSecs(24 * 3600 - 1)
				.isIntervalFee(true).amount(60).build();
		Fee airportFee1_4 = Fee.builder().feePlan(airportFeePlan1_4).startSecs(24 * 3600).isFlatFee(true)
				.isFlatPerDay(true).amount(80).build();
		Fee airportFee2_1 = Fee.builder().feePlan(airportFeePlan2_1).startSecs(0).endSecs(12 * 3600 - 1)
				.isIntervalFee(true).amount(60).build();
		Fee airportFee2_2 = Fee.builder().feePlan(airportFeePlan2_2).startSecs(12 * 3600).endSecs(24 * 3600 - 1)
				.isIntervalFee(true).amount(80).build();
		Fee airportFee2_3 = Fee.builder().feePlan(airportFeePlan2_3).startSecs(24 * 3600).isFlatFee(true)
				.isFlatPerDay(true).amount(100).build();

		feeModel3.add(airportFee1_1);
		feeModel3.add(airportFee1_2);
		feeModel3.add(airportFee1_3);
		feeModel3.add(airportFee1_4);
		feeModel3.add(airportFee2_1);
		feeModel3.add(airportFee2_2);
		feeModel3.add(airportFee2_3);

		addFeeModel(feeModel3);

	}

	private void addFeeModel(List<Fee> feeModel) {
		feeDao.saveAll(feeModel);

	}

	@Override
	public void run(String... args) throws Exception {
		loadParkingLots();
		loadFeeModels();

	}

}
