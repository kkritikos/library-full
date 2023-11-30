package gr.aegean.book.test;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

import gr.aegean.book.utility.HibernateBootstrap;

public interface DBTestInterface extends TestInterface{
	@BeforeAll
    static void beforeAllTests() {
        HibernateBootstrap.getSessionFactory();
    }
	
	@AfterAll
    static void afterAllTest() {
        HibernateBootstrap.destroy();
    }
}
