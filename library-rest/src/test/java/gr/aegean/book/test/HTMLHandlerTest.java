package gr.aegean.book.test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.NullSource;

import gr.aegean.book.domain.Book;
import gr.aegean.book.utility.HTMLHandler;

public class HTMLHandlerTest implements TestInterface{
	private Book createBook(ArgumentsAccessor accessor, int start) {
		String isbn = accessor.getString(start + 0);
		String title = accessor.getString(start + 1);
		String author1 = accessor.getString(start + 2);
		String author2 = accessor.getString(start + 3);
		List<String> authors = new ArrayList<String>();
		authors.add(author1);
		if (author2 != null && !author2.trim().equals("")) authors.add(author2);
		String publisher = accessor.getString(start + 4);
		String category = accessor.getString(start + 5);
		String summary = accessor.getString(start + 6);
		String language = accessor.getString(start + 7);
		String date = accessor.getString(start + 8);
		
		return new Book.Builder(isbn, title, authors, publisher).
				category(category).summary(summary).language(language).date(date).build();
	}
	
	@ParameterizedTest
	@CsvFileSource(resources="/positiveSingleBook.csv")
	void checkNormalCreateHtmlBook(ArgumentsAccessor accessor) {
		Book book = createBook(accessor, 0);
		assertEquals(accessor.getString(9), HTMLHandler.createHtmlBook(book));
	}
	
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
	
	@ParameterizedTest
	@CsvFileSource(resources="/positiveMultipleBooks.csv")
	void checkNormalCreateHtmlBooks(ArgumentsAccessor accessor) {
		Book book1 = createBook(accessor, 0);
		Book book2 = createBook(accessor, 9);
		List<Book> books = new ArrayList<Book>();
		books.add(book1);
		books.add(book2);
		assertEquals(accessor.getString(18), HTMLHandler.createHtmlBooks(books));
	}
}
