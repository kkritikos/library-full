package gr.aegean.book.domain;

import java.util.List;

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
    private String title = null;
    private String category = null;
    @Column()
    @ElementCollection(targetClass=String.class, fetch = FetchType.EAGER)
    private List<String> authors = null;
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
    	this.category = builder.category;
    	this.language = builder.language;
    	this.summary = builder.summary;
    	this.date = builder.date;
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
        
        public Builder(String isbn, String title, List<String> authors, String publisher) {
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

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public List<String> getAuthors() {
		return authors;
	}

	public void setAuthors(List<String> authors) {
		this.authors = authors;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
}