package book;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import book.inface.TestLifecycleLogger;
import gr.aegean.book.domain.Book;
import io.restassured.common.mapper.TypeRef;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

    @DisplayName("Adding Book Testing")
    @TestMethodOrder(OrderAnnotation.class)
    public class SecondIT implements TestLifecycleLogger{
    
	    @Test
	    @Order(1)
	    public void addWrongBook() throws Exception{
	    	Book book = new Book();
	    	given().contentType("application/json").body(book).when().post("/api/books").then().assertThat().statusCode(400);
	    }
	    
	    private Book createBook(String isbn) {
	    	Random r = new Random();
	    	Book book = new Book();
	    	book.setIsbn(isbn);
	    	int pubId = r.nextInt(10) + 1;
	    	book.setPublisher("Pub" + pubId);
	    	int titleId = r.nextInt(10) + 1;
	    	book.setTitle("Title" + titleId);
	    	List<String> authors = new ArrayList<String>();
	    	int auth1 = r.nextInt(10) + 1;
	    	int auth2 = r.nextInt(10) + 1;
	    	authors.add("Auth" + auth1);
	    	authors.add("Auth2" + auth2);
	    	book.setAuthors(authors);
	    	
	    	return book;
	    }
	    
	    @Test
	    @Order(2)
	    public void postCorrectBook() throws Exception{
	    	Book book = createBook("xxx");
	    	given().contentType("application/json").body(book).when().post("/api/books").then().assertThat().statusCode(201);
	    }
	    
	    @Test
	    @Order(3)
	    public void getExistingBook() throws Exception{
	    	given().accept("application/json").get("/api/books/xxx").then().
	    	assertThat().statusCode(200).and().body("isbn", equalTo("xxx"));
	    }
	    
	    @ParameterizedTest
	    @ValueSource(strings = { "1111", "2222", "3333" })
	    @Order(4)
	    void addCorrectBook(String isbn) {
	    	Book book = createBook(isbn);
	    	given().contentType("application/json").body(book).when().post("/api/books/").then().assertThat().statusCode(201);
	    }
	    
	    @Test
	    @Order(5)
	    public void getExistingBooks() throws Exception{
	    	List<Book> books = given().accept("application/json").get("/api/books/").then().assertThat().statusCode(200).
	    			extract().as(new TypeRef<List<Book>>(){});
	    	assertThat(books, hasSize(4));
	    	assertThat(books.get(0).getIsbn(),anyOf(equalTo("xxx"),equalTo("1111"),equalTo("2222"),equalTo("3333")));
	    	assertThat(books.get(1).getIsbn(),anyOf(equalTo("xxx"),equalTo("1111"),equalTo("2222"),equalTo("3333")));
	    	assertThat(books.get(2).getIsbn(),anyOf(equalTo("xxx"),equalTo("1111"),equalTo("2222"),equalTo("3333")));
	    	assertThat(books.get(3).getIsbn(),anyOf(equalTo("xxx"),equalTo("1111"),equalTo("2222"),equalTo("3333")));
	    }
	    
	    @ParameterizedTest
	    @Order(6)
	    @ValueSource(strings = {"11", "12", "15", "19"})
	    public void useWrongPublisher(String publisherId) {
	    	given().accept("application/json").param("publisher","Publisher" + publisherId).get("/api/books").then().
	    	assertThat().statusCode(200).body(equalTo("[]"));
	    }
    }