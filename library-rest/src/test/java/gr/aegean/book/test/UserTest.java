package gr.aegean.book.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import gr.aegean.book.domain.User;

public class UserTest implements TestInterface{
	@Test
	void checkConstructor() {
		User u = new User();
		assertNull(u.getUsername());
		assertNull(u.getPassword());
		assertEquals(u.getRoles(),new ArrayList<String>());
	}
	
	@ParameterizedTest
	@NullAndEmptySource
	void checkSetGetNullEmptPassword(String pwd) {
		User user = new User();
		user.setPassword(pwd);
		
		assertEquals(user.getPassword(),pwd);
	}
	
	@ParameterizedTest
	@ValueSource(strings={"Pwd1","P","dsfds!!!"})
	void checkSetGetNormalPassword(String pwd) {
		User user = new User();
		user.setPassword(pwd);
		
		assertEquals(user.getPassword(),pwd);
	}
	
	@ParameterizedTest
	@NullAndEmptySource
	void checkSetGetNullEmptUserName(String username) {
		User user = new User();
		user.setUsername(username);
		
		assertEquals(user.getUsername(),username);
	}
	
	@ParameterizedTest
	@ValueSource(strings={"U","U1","User!!!"})
	void checkSetGetNormalUsername(String pwd) {
		User user = new User();
		user.setUsername(pwd);
		
		assertEquals(user.getUsername(),pwd);
	}
	
	@ParameterizedTest
	@MethodSource("listProvider")
	void checkList(List<String> roles) {
		User u = new User();
		if (roles != null)
			u.setRoles(new ArrayList<String>(roles));
		else u.setRoles(null);
		
		assertEquals(u.getRoles(), roles);
	}
	
	static Stream<List<String>> listProvider() {
		return Stream.of(
			Arrays.asList("r1", "r2"),
			null,
			new ArrayList<String>()
		);
	}
}
