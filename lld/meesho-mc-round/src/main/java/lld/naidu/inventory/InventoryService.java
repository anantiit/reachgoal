package lld.naidu.inventory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import lombok.NonNull;

public class InventoryService {
	static Map<String, Integer> booksCount = new ConcurrentHashMap<String, Integer>();
	static Map<String, Book> bookDetails = new ConcurrentHashMap<String, Book>();

	public void addBook(@NonNull Book book, int count) {
		String bookISBNCode = book.getISBNCode();
		bookDetails.putIfAbsent(bookISBNCode, book);
		booksCount.compute(bookISBNCode, (k, v) -> (v == null) ? count : v + count);
	}

	public int getCount(Book book) {
		return booksCount.getOrDefault(book.getISBNCode(), 0);
	}

	public void decrementCountOnReturn(String bookISBNCode) {
		booksCount.computeIfPresent(bookISBNCode, (k, v) -> v - 1);
	}

	public void IncrementCountOnReservation(String bookISBNCode) {
		booksCount.computeIfPresent(bookISBNCode, (k, v) -> v + 1);
	}

}
