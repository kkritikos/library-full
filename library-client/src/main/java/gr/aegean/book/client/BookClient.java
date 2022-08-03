package gr.aegean.book.client;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;

import gr.aegean.book.domain.Book;

public class BookClient {
	private String ip;
    private int port;
    private String basePath;
    private static int id = 1;
    
    public BookClient() {
    	this.ip = PropertyReader.getIp();
    	this.port = Integer.parseInt(PropertyReader.getPort());
    	basePath = "http://" + ip + ":" + port + "/library-rest/api/books"; 
    }
    
    public BookClient(String ip, int port) {
    	this.ip = ip;
    	this.port = port;
    	basePath = "http://" + ip + ":" + port + "/library-rest/api/books"; 
    }
    
    private WebTarget getTarget(String methodName) {
    	ClientConfig cc = new ClientConfig();
    	Client c = ClientBuilder.newClient(cc);
    	HttpAuthenticationFeature feature = HttpAuthenticationFeature.basic("admin", "admin");
    	c.register(feature);
    	WebTarget target = c.target(basePath + methodName);

		return target;
    }
    
    public void getBooks(MediaType type) {
    	System.out.println("Invoking GET /books with no query parameters");
    	WebTarget r = getTarget("");
    	Invocation.Builder builder = r.request(type);
 	    Response response = builder.get();
 	    int status = response.getStatus();
 	    if (status >= 300){
 	    	System.out.println("Something wrong happened when calling getBooks");
 	    	System.out.println(response.readEntity(String.class));
 	    }
 	    else{
 	    	System.out.println("Got successful result from invocation");
 	    	System.out.println(response.readEntity(String.class));
 	    }
    }
    
    public void getBooksWithParams(String title, String publisher, List<String> authors, MediaType type) {
    	System.out.println("Invoking GET /books with query parameters");
    	WebTarget r = getTarget("");
    	if (title != null) r = r.queryParam("title",title);
    	if (publisher != null) r = r.queryParam("publisher",publisher);
    	if (authors != null && !authors.isEmpty()) {
    		for (String s: authors) r.queryParam("author",s); 
    	}
    	Invocation.Builder builder = r.request(type);
 	    Response response = builder.get();
 	    int status = response.getStatus();
 	    if (status >= 300){
 	    	System.out.println("Something wrong happened when calling getBooks with parameters");
 	    	System.out.println(response.readEntity(String.class));
 	    }
 	    else{
 	    	System.out.println("Got successful result from invocation");
 	    	System.out.println(response.readEntity(String.class));
 	    }
    }
    
    public void getBook(String isbn, MediaType type) {
    	System.out.println("Invoking GET /books/" + isbn);
    	WebTarget r = getTarget("/" + isbn);
    	Invocation.Builder builder = r.request(type);
 	    Response response = builder.get();
 	    int status = response.getStatus();
 	    if (status >= 300){
 	    	System.out.println("Something wrong happened when calling getBook");
 	    	System.out.println(response.readEntity(String.class));
 	    }
 	    else{
 	    	System.out.println("Got successful result from invocation");
 	    	if (type != MediaType.TEXT_HTML_TYPE) {
 	    		Book book = response.readEntity(Book.class);
 	    		System.out.println("Got book: " + book);
 	    	}
 	    	else System.out.println(response.readEntity(String.class));
 	    }
    }
    
    private Book createBook(String isbn) {
    	Book book = new Book();
    	book.setIsbn(isbn);
    	book.setTitle("Title" + id);
    	book.setPublisher("Publisher" + id);
    	List<String> authors = new ArrayList<String>();
    	authors.add("Auth1");
    	authors.add("Auth2");
    	book.setAuthors(authors);
    	id++;
    	
    	return book;
    }
    
    public void addBook(String isbn, MediaType type) {
    	System.out.println("Invoking PUT /books");
    	WebTarget r = getTarget("");
    	Book book = createBook(isbn);
    	Invocation.Builder builder = r.request();
 	    Response response = builder.post(Entity.entity(book,type),Response.class);
 	    int status = response.getStatus();
 	    if (status >= 300){
 	    	System.out.println("Something wrong happened when calling addBook");
 	    	System.out.println(response.readEntity(String.class));
 	    }
 	    else{
 	    	System.out.println("Got successful result from invocation");
 	    	System.out.println(response.readEntity(String.class));
 	    }
    }
    
    public void updateBook(String isbn, MediaType type) {
    	System.out.println("Invoking PUT /books/" + isbn);
    	WebTarget r = getTarget("/" + isbn);
    	Book book = createBook(isbn);
    	book.setTitle("Title3");
    	Invocation.Builder builder = r.request();
 	    Response response = builder.put(Entity.entity(book,type),Response.class);
 	    int status = response.getStatus();
 	    if (status >= 300){
 	    	System.out.println("Something wrong happened when calling updateBook");
 	    	System.out.println(response.readEntity(String.class));
 	    }
 	    else{
 	    	System.out.println("Got successful result from invocation");
 	    	System.out.println(response.readEntity(String.class));
 	    }
    }
    
    public void deleteBook(String isbn) {
    	System.out.println("Invoking DELETE /books/" + isbn);
    	WebTarget r = getTarget("/" + isbn);
    	Invocation.Builder builder = r.request();
 	    Response response = builder.delete();
 	    int status = response.getStatus();
 	    if (status >= 300){
 	    	System.out.println("Something wrong happened when calling deleteBook");
 	    	System.out.println(response.readEntity(String.class));
 	    }
 	    else{
 	    	System.out.println("Got successful result from invocation");
 	    	System.out.println(response.readEntity(String.class));
 	    }
    }
    
    public static void main(String[] args) {
    	MediaType html = MediaType.TEXT_HTML_TYPE;
    	MediaType jaxb = MediaType.APPLICATION_JSON_TYPE;
    	BookClient bc = new BookClient();
    	//Adding two books
    	bc.addBook("isbn1",jaxb);
    	bc.addBook("isbn2",jaxb);
    	bc.addBook("isbn1",jaxb);
    	
    	//Getting all books in different media types
    	bc.getBooks(html);
    	bc.getBooks(jaxb);
    	
    	//Getting filtered books in different media types
    	bc.getBooksWithParams("Title1",null,null,html);
    	bc.getBooksWithParams(null,"Publisher2",null,jaxb);
    	
    	//Getting one book in different media types
    	bc.getBook("isbn1",html);
    	bc.getBook("isbn1",jaxb);
    	
    	//Updating one book & checking the update
    	bc.updateBook("isbn1",jaxb);
    	bc.getBook("isbn1",jaxb);
    	
    	//Deleting first book & checking the deletion
    	bc.deleteBook("isbn1");
    	bc.deleteBook("isbn1");
    	bc.getBooks(jaxb);
    }

}
