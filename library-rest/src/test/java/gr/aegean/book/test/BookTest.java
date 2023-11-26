package gr.aegean.book.test;

import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

import gr.aegean.book.domain.Book;

public class BookTest implements TestInterface{
	@Test
	void checkEmptyConstructor() {
		Book book = new Book();
		assertNull(book.getIsbn());
		assertNull(book.getTitle());
		assertNull(book.getAuthors());
		assertNull(book.getCategory());
		assertNull(book.getPublisher());
		assertNull(book.getSummary());
		assertNull(book.getLanguage());
		assertNull(book.getDate());
	}

}
