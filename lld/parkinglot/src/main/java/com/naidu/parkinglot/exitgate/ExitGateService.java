package com.naidu.parkinglot.exitgate;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.naidu.parkinglot.entrygate.Ticket;
import com.naidu.parkinglot.entrygate.TicketDao;
import com.naidu.parkinglot.fee.FeeDao;
import com.naidu.parkinglot.fee.FeeFactory;
import com.naidu.parkinglot.fee.FeeModel;
import com.naidu.parkinglot.parkingspot.ParkingSpot;
import com.naidu.parkinglot.parkingspot.ParkingSpotDao;
import com.naidu.parkinglot.payment.PaymentMode;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class ExitGateService {
	FeeDao feeDao;
	TicketDao ticketDao;
	ParkingSpotDao parkingSpotDao;

	public Ticket unParkVehicle(Ticket ticket, PaymentMode paymentMode) {
		ParkingSpot parkingSpot = parkingSpotDao.findByParkingSpotIdAndParkingLotId(ticket.getParkingSpotId(),
				ticket.getParkingLotId());
		parkingSpot.setCurrentlyOccupied(false);
		parkingSpotDao.update(parkingSpot);
		FeeModel feeModel = FeeFactory.getInstance(ticket.getFeeModelType(), feeDao);
		LocalDateTime endTime = ticket.getEndTime();
		if (endTime == null) {
			ticket.setEndTime(LocalDateTime.now());
		}
		double parkingFee = feeModel.calculateCost(ticket);
		ticket.setParkingFee(parkingFee);
		ticket.setPaymentMode(paymentMode);
		ticketDao.update(ticket);
		return ticket;
	}

}
