package lld.naidu.inventory;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Book {
	private int id;
	private String bookName;
	private String ISBNCode;
	private String author;
	private String genre;
	private String numOfPages;
	private BookType bookType;

}
