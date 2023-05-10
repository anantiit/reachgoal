package com.naidu.parkinglot.exitgate;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.naidu.parkinglot.entrygate.Ticket;
import com.naidu.parkinglot.payment.PaymentMode;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class ExitGateController {
	ExitGateService exitGateService;

	@PostMapping("/parkinglot/unpark")
	public Ticket unparkVehicle(@RequestBody Ticket ticket, @RequestParam(required = false) PaymentMode paymentMode) {
		return exitGateService.unParkVehicle(ticket, paymentMode);
	}
}
