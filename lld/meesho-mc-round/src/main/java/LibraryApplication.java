import lld.naidu.account.User;
import lld.naidu.account.UserRole;
import lld.naidu.account.UserService;
import lld.naidu.inventory.Book;
import lld.naidu.inventory.BookType;
import lld.naidu.inventory.InventoryService;
import lld.naidu.reservation.ReservationService;

public class LibraryApplication {

	public static void main(String args[]) {
		InventoryService inventoryService = new InventoryService();
		ReservationService reservationService = new ReservationService(inventoryService);
		UserService userService = new UserService();
		User u1 = new User(1, "anant@gmail.com", "anant@gmail.com", "1234", "Anant", "4225235345345", UserRole.Student);
		User u2 = new User(2, "anant@gmail.com", "anant@gmail.com", "1234", "Anant", "4225235345345", UserRole.Student);
		User u3 = new User(3, "anant@gmail.com", "anant@gmail.com", "1234", "Anant", "4225235345345", UserRole.Student);
		userService.createUser(u1);
		userService.createUser(u2);
		userService.createUser(u3);
		Book b1 = new Book(1, "System Design", "ISBN00012", "X", "IT", "500", BookType.PaperBack);
		Book b2 = new Book(2, "System Design1", "ISBN00013", "X", "IT", "500", BookType.PaperBack);
		Book b3 = new Book(3, "System Design2", "ISBN00014", "X", "IT", "500", BookType.PaperBack);
		inventoryService.addBook(b1, 3);
		inventoryService.addBook(b2, 1);
		inventoryService.addBook(b3, 2);
		System.out.println(inventoryService.getCount(b1));
		reservationService.reserveBook(b1, u1);
		System.out.println(inventoryService.getCount(b1));
		System.out.println(reservationService.getBorrowersOfThisBook(b1));
		reservationService.returnBook(b1, u1);
		System.out.println(inventoryService.getCount(b1));
		System.out.println(reservationService.getBorrowersOfThisBook(b1));

	}

}
