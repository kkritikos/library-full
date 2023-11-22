package gr.aegean.book.security;

import java.security.Principal;

import javax.ws.rs.core.SecurityContext;

import gr.aegean.book.domain.User;

public class BasicSecurityContext implements SecurityContext {
	   private final User user;
	   private final boolean secure;
	   
	   public BasicSecurityContext(User user, boolean secure) {
	       this.user = user;
	       this.secure = secure;
	   }

	   @Override
	   public Principal getUserPrincipal() {
	       return new Principal() {
	           @Override
	           public String getName() {
	                return user.getUsername();
	           }
	       };
	   }

	   @Override
	   public String getAuthenticationScheme() {
	       return SecurityContext.BASIC_AUTH;
	   }

	   @Override
	   public boolean isSecure() { return secure; }

	   @Override
	   public boolean isUserInRole(String role) {
		   System.out.println("role is: " + role);
	       return user.getRoles().contains(role);
	   }
	}