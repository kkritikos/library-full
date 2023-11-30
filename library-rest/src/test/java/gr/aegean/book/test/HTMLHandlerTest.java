package gr.aegean.book.test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.NullSource;

import gr.aegean.book.domain.Book;
import gr.aegean.book.utility.HTMLHandler;

public class HTMLHandlerTest implements TestInterface{
	
	//Checking we do not get any book result in HTML table when book is null
	@ParameterizedTest
	@NullSource
	void checkNullCreateHtmlBook(Book book) {
		String result = assertDoesNotThrow(()-> HTMLHandler.createHtmlBook(book));
		assertAll("no book row", 
				()-> assertFalse(result.contains("<h1>")),
				()-> assertFalse(result.contains("<tr><td>"))
				);
	}
	
	//As before but this time isbn is null, not the book object
	@Test
	void checkNullIsbnCreateHtmlBook() {
		Book book = new Book();
		String result = assertDoesNotThrow(()-> HTMLHandler.createHtmlBook(book));
		assertAll("no book row", 
				()-> assertFalse(result.contains("<h1>")),
				()-> assertFalse(result.contains("<tr><td>"))
		);
	}
	
	//Checking that no result is in HTML table is the list of books is null or empty
	@ParameterizedTest
	@NullAndEmptySource
	void checkNullEmptyCreateHtmlBooks(List<Book> books) {
		String result = assertDoesNotThrow(()-> HTMLHandler.createHtmlBooks(books));
		assertFalse(result.contains("<tr><td>"));
	}
	
	//Checking many positive cases for presenting the right HTML table content for a book
	@ParameterizedTest
	@CsvFileSource(resources="/positiveSingleBookHtml.csv")
	void checkNormalCreateHtmlBook(ArgumentsAccessor accessor) {
		Book book = BookUtility.createPositiveBook(accessor,0);
		assertEquals(HTMLHandler.createHtmlBook(book),accessor.getString(9));
	}
	
	//Checking many positive cases for presenting the right HTML table content for a pair of books
	@ParameterizedTest
	@CsvFileSource(resources="/positiveMultipleBooksHtml.csv")
	void checkNormalCreateHtmlBooks(ArgumentsAccessor accessor) {
		Book book1 = BookUtility.createPositiveBook(accessor, 0);
		Book book2 = BookUtility.createPositiveBook(accessor, 9);
		List<Book> books = new ArrayList<Book>();
		books.add(book1);
		books.add(book2);
		assertEquals(accessor.getString(18), HTMLHandler.createHtmlBooks(books));
	}
}
