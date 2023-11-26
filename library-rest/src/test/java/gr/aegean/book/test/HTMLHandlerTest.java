package gr.aegean.book.test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.List;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.NullSource;

import gr.aegean.book.domain.Book;
import gr.aegean.book.utility.HTMLHandler;

public class HTMLHandlerTest implements TestInterface{
	
	@ParameterizedTest
	@NullSource
	void checkNullCreateHtmlBook(Book book) {
		String result = assertDoesNotThrow(()-> HTMLHandler.createHtmlBook(book));
		assertAll("no book row", 
				()-> assertFalse(result.contains("<h1>")),
				()-> assertFalse(result.contains("<tr><td>"))
				);
	}
	
	@ParameterizedTest
	@NullAndEmptySource
	void checkNullIsbnCreateHtmlBook(String isbn) {
		Book book = new Book();
		book.setIsbn(isbn);
		String result = assertDoesNotThrow(()-> HTMLHandler.createHtmlBook(book));
		assertAll("no book row", 
				()-> assertFalse(result.contains("<h1>")),
				()-> assertFalse(result.contains("<tr><td>"))
		);
	}
	
	@ParameterizedTest
	@NullAndEmptySource
	void checkNullEmptyCreateHtmlBooks(List<Book> books) {
		String result = assertDoesNotThrow(()-> HTMLHandler.createHtmlBooks(books));
		assertFalse(result.contains("<tr><td>"));
	}
}
