package lld.naidu.reservation;

import java.time.LocalDate;

import lld.naidu.account.User;
import lld.naidu.inventory.Book;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Reservation {
	Book book;
	User reservedBy;
	LocalDate createdTime;
	LocalDate endTime;
	long supposedEndTime;
	double fineForNotReturningOnTime;
	ReservationStatus reservationStatus;

	Reservation(Book book, User reservedBy) {
		this.book = book;
		this.reservedBy = reservedBy;
		this.createdTime = LocalDate.now();
		this.supposedEndTime = System.currentTimeMillis()
				+ ReservationService.EXPECTED_RESERVATION_END_TIME_IN_SECS * 1000;
		this.fineForNotReturningOnTime = 0d;
	}

	public void endReservation() {
		this.endTime = LocalDate.now();
		this.reservationStatus = ReservationStatus.COMPLETED;
	}

}
