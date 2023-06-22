package lld.naidu.reservation;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import lld.naidu.account.User;
import lld.naidu.exceptions.ReservationLimitExceededException;
import lld.naidu.inventory.Book;
import lld.naidu.inventory.InventoryService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ReservationService {
	static int EXPECTED_RESERVATION_END_TIME_IN_SECS = 7 * 86400;// 5Days
	static int MAX_RESERVATIONS_PER_USER = 2;
	InventoryService inventoryService;
	static volatile Map<Integer, ArrayList<Reservation>> userReservations = new ConcurrentHashMap<Integer, ArrayList<Reservation>>();
	static volatile Map<String, ArrayList<Reservation>> bookReservations = new ConcurrentHashMap<String, ArrayList<Reservation>>();

	public void reserveBook(Book book, User user) {
		Integer userID = user.getUserId();
		ArrayList<Reservation> userReservationList = userReservations.getOrDefault(userID,
				new ArrayList<Reservation>());
		if (userReservationList != null && userReservationList.size() >= MAX_RESERVATIONS_PER_USER) {
			throw new ReservationLimitExceededException(
					"User : " + user + " already crossed allowed limit of reservations");
		}
		Reservation reservation = new Reservation(book, user);
		userReservationList.add(reservation);
		String bookISBNCode = book.getISBNCode();
		ArrayList<Reservation> bookReservationList = bookReservations.getOrDefault(bookISBNCode,
				new ArrayList<Reservation>());
		bookReservationList.add(reservation);
		userReservations.put(userID, bookReservationList);
		bookReservations.put(bookISBNCode, bookReservationList);
		inventoryService.decrementCountOnReturn(bookISBNCode);

	}

	public void returnBook(Book book, User user) {
		String bookISBNCode = book.getISBNCode();
		ArrayList<Reservation> userReservationsList = userReservations.get(user.getUserId());
		ArrayList<Reservation> bookReservationsList = bookReservations.get(bookISBNCode);
		removeReservationMatchingBookIdAndUserID(userReservationsList, book, user);
		removeReservationMatchingBookIdAndUserID(bookReservationsList, book, user);
		inventoryService.IncrementCountOnReservation(bookISBNCode);
	}

	private void removeReservationMatchingBookIdAndUserID(ArrayList<Reservation> reservationList, Book book,
			User user) {
		Integer bookId = book.getId();
		Integer userId = user.getUserId();
		ListIterator<Reservation> itr = reservationList.listIterator();
		while (itr.hasNext()) {
			Reservation reservation = itr.next();
			Book bookInReservation = reservation.getBook();
			User userInReservation = reservation.getReservedBy();
			if (bookId == bookInReservation.getId() && userId == userInReservation.getUserId()) {
				itr.remove();
			}
		}
	}

	public List<User> getBorrowersOfThisBook(Book book) {
		String bookISBNCode = book.getISBNCode();
		ArrayList<Reservation> bookReservationsList = bookReservations.get(bookISBNCode);
		List<User> users = new ArrayList<User>();
		return bookReservationsList.stream().map(reservation -> reservation.getReservedBy())
				.collect(Collectors.toList());
	}
}