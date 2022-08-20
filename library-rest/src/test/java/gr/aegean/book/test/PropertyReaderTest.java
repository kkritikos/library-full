package gr.aegean.book.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import gr.aegean.book.configuration.PropertyReader;

public class PropertyReaderTest implements TestInterface{
	@Test
	void checkDbName() {
		assertEquals("book",PropertyReader.getDbName());
	}
	
	@Test
	void checkDbHost() {
		assertEquals("127.0.0.1",PropertyReader.getDbHost());
	}
	
	@Test
	void checkDbPort() {
		assertEquals("3307",PropertyReader.getDbPort());
	}
	
	@Test
	void checklogin() {
		assertEquals("xxx",PropertyReader.getLogin());
	}
	
	@Test
	void checkPwd() {
		assertEquals("pwd_xxx",PropertyReader.getPwd());
	}
}
