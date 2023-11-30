package gr.aegean.book.utility;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;

import gr.aegean.book.domain.Book;

public final class DBHandler {
	public static List<Book> getBooks(String title, List<String> authors, String publisher){
		Session session = HibernateBootstrap.getSessionFactory().getCurrentSession();
		String filter = null;
		if (title != null && !title.trim().equals("")) filter = "b.title=:title";
		if (publisher != null && !publisher.trim().equals("")) 
			if (filter == null) filter = "b.publisher=:publisher";
			else filter += " and b.publisher=:publisher";
		if (authors != null) {
			for (int i = 0; i < authors.size(); i++) {
				if (i == 0) {
					if (filter == null) filter = ":author" + i + " member of b.authors";
					else filter += " and :author" + i + " member of b.authors";
				}
				else {
					filter += " and :author" + i + " member of b.authors";
				}
			}
		}
		System.out.println("Filter is: " + filter);
		session.beginTransaction();
		Query query = session.createQuery("select b from Book b where " + filter);
		if (title != null && !title.trim().equals("")) query.setParameter("title", title);
		if (publisher != null && !publisher.trim().equals("")) query.setParameter("publisher", publisher);
		if (authors != null) {
			for (int i = 0; i < authors.size(); i++)
				query.setParameter("author" + i, authors.get(i));
		}
		List<Book> books = query.getResultList();
		session.getTransaction().commit();
		
		return books;
	}
	
	public static List<Book> getAllBooks(){
		Session session = HibernateBootstrap.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		List<Book> books = session.createQuery("select b from Book b").getResultList();
		session.getTransaction().commit();
		
		return books;
	}
	
	public static Book getBook(String isbn) {
		Session session = HibernateBootstrap.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		Book book = session.get(gr.aegean.book.domain.Book.class,isbn);
		session.getTransaction().commit();
		
		return book;
	}
	
	public static boolean existsBook(String isbn) {
		Session session = HibernateBootstrap.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		Book book = session.get(gr.aegean.book.domain.Book.class, isbn);
		session.getTransaction().commit();
		if (book != null) return true;
		else return false;
	}
	
	public static void createBook(Book book) {
		Session session = HibernateBootstrap.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		session.persist(book);
		session.getTransaction().commit();
	}
	
	public static boolean updateBook(Book book) {
		Session session = HibernateBootstrap.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		try {
			session.merge(book);
			session.getTransaction().commit();
		}
		catch(Exception e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
		
	}
	
	public static boolean deleteBook(String isbn) {
		Session session = HibernateBootstrap.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		Book book = session.get(gr.aegean.book.domain.Book.class, isbn);
		boolean found = false;
		if (book != null) {
			session.remove(book);
			found = true;
		}
		session.getTransaction().commit();
		
		return found;
	}
}
