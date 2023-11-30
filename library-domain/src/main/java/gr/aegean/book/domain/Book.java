package gr.aegean.book.domain;

import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Kyriakos Kritikos
 */

@XmlRootElement
@Entity
public class Book {

	@Id
	private String isbn = null;
	@Basic(optional = false)
    private String title = null;
    private String category = null;
    @Column(nullable = false)
    @ElementCollection(targetClass=String.class, fetch = FetchType.EAGER)
    private List<String> authors = null;
    @Basic(optional = false)
    private String publisher = null;
    private String language = null;
    private String summary = null;
    private String date = null;
    
    public Book() {}
    
    private Book(Builder builder) {
    	this.isbn = builder.isbn;
    	this.title = builder.title;
    	this.authors = builder.authors;
    	this.publisher = builder.publisher;
    	setCategory(builder.category);
    	setLanguage(builder.language);
    	setSummary(builder.summary);
    	setDate(builder.date);
    }
    
    public static class Builder{
    	private String isbn = null;
        private String title = null;
        private String category = null;
        private List<String> authors = null;
        private String publisher = null;
        private String language = null;
        private String summary = null;
        private String date = null;
        
        private static void checkSingleValue(String value, String message) throws IllegalArgumentException{
        	if (value == null || value.trim().equals("")) throw new IllegalArgumentException(message + " cannot be null or empty");
        }
        
        private static void checkListString(List<String> list, String message) throws IllegalArgumentException{
        	if (list == null || list.isEmpty()) throw new IllegalArgumentException(message + " cannot be null or empty");
        }
        
        public Builder(String isbn, String title, List<String> authors, String publisher) throws IllegalArgumentException{
        	checkSingleValue(isbn, "ISBN");
        	checkSingleValue(title, "Title");
        	checkSingleValue(publisher, "Publisher");
        	checkListString(authors, "The list of authors");
        	
        	this.isbn = isbn;
        	this.title = title;
        	this.authors = authors;
        	this.publisher = publisher;
        }
        
        public Builder category(String value) {
        	this.category = value;
        	return this;
        }
        
        public Builder language(String value) {
        	this.language = value;
        	return this;
        }
        
        public Builder summary(String value) {
        	this.summary = value;
        	return this;
        }
        
        public Builder date(String value) {
        	this.date = value;
        	return this;
        }
        
        public Book build() {
        	return new Book(this);
        }
    }
    
    public String toString(){
    	return "Book(" + isbn + ", " + title + ", " + authors + ")";
    }

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) throws IllegalArgumentException{
		Builder.checkSingleValue(isbn, "ISBN");
		this.isbn = isbn;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) throws IllegalArgumentException{
		Builder.checkSingleValue(title, "Title");
		this.title = title;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		if (category == null || category.trim().equals("")) this.category = null;
		else this.category = category.trim();
	}

	public List<String> getAuthors() {
		return authors;
	}

	public void setAuthors(List<String> authors) throws IllegalArgumentException{
		Builder.checkListString(authors, "The list of authors");
		this.authors = authors;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) throws IllegalArgumentException{
		Builder.checkSingleValue(publisher, "Publisher");
		this.publisher = publisher;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		if (language == null || language.trim().equals("")) this.language = null;
		else this.language = language.trim();
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		if (summary == null || summary.trim().equals("")) this.summary = null;
		else this.summary = summary.trim();
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		if (date == null || date.trim().equals("")) this.date = null;
		else this.date = date.trim();
	}
	
	public boolean equals(Object o) {
		if (o instanceof Book) {
			Book b = (Book)o;
			if (b.getIsbn().equals(isbn)) return true;
		}
		
		return false;
	}
}