package gr.aegean.book.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;

import gr.aegean.book.domain.Book;
import gr.aegean.book.utility.DBHandler;

@TestMethodOrder(org.junit.jupiter.api.MethodOrderer.OrderAnnotation.class)
public class DBTest implements DBTestInterface{
	/* Checking multiple positive cases for book creation through its Builder interface
	 * Along with the Storage & Retrieval from DB
	 */
	@ParameterizedTest
	@Order(5)
	@CsvFileSource(resources="/positiveSingleBook.csv")
	void checkPositiveSingleBookWithDB(ArgumentsAccessor accessor) {
		Book book = BookUtility.createPositiveBook(accessor,0);
		DBHandler.createBook(book);
		book = DBHandler.getBook(accessor.getString(0));
		BookUtility.checkBook(book,accessor);
	}
	
	/* Checking book deletion positive cases
	 * 
	 */
	@ParameterizedTest
	@Order(2)
	@CsvSource({
		"ISBNNNN1, Title1, Author1, Publisher1",
		"ISBNNNN2, Title2, Author2, Publisher2",
		"ISBNNNN3, Title3, Author3, Publisher3",
	})
	void checkBookDeletion(String isbn, String title, String author, String publisher) {
		List<String> authors = Arrays.asList(author);
		Book book = new Book.Builder(isbn,title,authors,publisher).build();
		DBHandler.createBook(book);
		DBHandler.deleteBook(isbn);
		book = DBHandler.getBook(isbn);
		assertNull(book);
	}
	
	//Checking book update on obligatory fields
	@ParameterizedTest
	@Order(4)
	@CsvSource({
		"ISBNNNN1, Title1, Author1, Publisher1",
		"ISBNNNN2, Title2, Author2, Publisher2",
		"ISBNNNN3, Title3, Author3, Publisher3",
	})
	void checkBookUpdate(String isbn, String title, String author, String publisher) {
		List<String> authors = Arrays.asList(author);
		Book book = new Book.Builder(isbn,title,authors,publisher).build();
		DBHandler.createBook(book);
		Random r = new Random();
		int choice = r.nextInt(3);
		switch(choice) {
			case 0: book.setTitle("TITLE"); break;
			case 1: book.setAuthors(Arrays.asList("AUTHOR")); break;
			case 2: book.setPublisher("PUBLISHER"); break;
		}
		DBHandler.updateBook(book);
		book = DBHandler.getBook(isbn);
		assertNotNull(book);
		if (choice == 0) {
			assertEquals(book.getPublisher(),publisher);
			assertEquals(book.getAuthors().size(),1);
			assertEquals(book.getAuthors().get(0),author);
			assertEquals(book.getTitle(),"TITLE");
		}
		else if (choice == 1) {
			assertEquals(book.getPublisher(),publisher);
			assertEquals(book.getTitle(),title);
			assertEquals(book.getAuthors().size(),1);
			assertEquals(book.getAuthors().get(0),"AUTHOR");
		}
		else {
			assertEquals(book.getTitle(),title);
			assertEquals(book.getAuthors().size(),1);
			assertEquals(book.getAuthors().get(0),author);
			assertEquals(book.getPublisher(),"PUBLISHER");
		}
	}
	
	//Checking book retrieval (all books or some)
	@Test
	@Order(3)
	void checkBookRetrieval() {
		Book book1 = new Book.Builder("1", "T1", Arrays.asList("Author1","Author2"), "P1").build();
		Book book2 = new Book.Builder("2", "T2", Arrays.asList("Author2","Author3"), "P1").build();
		Book book3 = new Book.Builder("3", "T3", Arrays.asList("Author3","Author1"), "P2").build();
		DBHandler.createBook(book1);
		DBHandler.createBook(book2);
		DBHandler.createBook(book3);
		List<Book> books = DBHandler.getAllBooks();
		
		//Checking if we have 3 books and these are book1, book2 & book3
		assertEquals(books.size(),3);
		int matches = 0;
		for (Book book: books) {
			if (book.equals(book1) || book.equals(book2) || book.equals(book3)) {
				matches++;
			}
		}
		assertEquals(matches,3);
		
		//Checking that with 1 title, we get only one book
		books = DBHandler.getBooks("T1", null, null);
		assertEquals(books.size(),1);
		assertEquals(books.get(0),book1);
		books = DBHandler.getBooks("T2", null, null);
		assertEquals(books.size(),1);
		assertEquals(books.get(0),book2);
		
		//Checking that with one author, we get two books
		books = DBHandler.getBooks(null, Arrays.asList("Author1"), null);
		assertEquals(books.size(),2);
		matches = 0;
		for (Book book: books) {
			if (book.equals(book1) || book.equals(book3)) {
				matches++;
			}
		}
		assertEquals(matches,2);
		
		//Checking that with one publisher, we get two books
		books = DBHandler.getBooks(null, null, "P1");
		assertEquals(books.size(),2);
		matches = 0;
		for (Book book: books) {
			if (book.equals(book1) || book.equals(book2)) {
				matches++;
			}
		}
		assertEquals(matches,2);
	}
}
