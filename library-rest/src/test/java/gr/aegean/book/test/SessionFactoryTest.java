package gr.aegean.book.test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import gr.aegean.book.utility.HibernateBootstrap;

public class SessionFactoryTest implements TestInterface{
	@Test
	public void checkSessionFactor() {
		SessionFactory sf = HibernateBootstrap.getSessionFactory();
		
		assertNotNull(sf);
	}
	
	@AfterAll
	public static void afterAll() {
        HibernateBootstrap.destroy();
    }
}
