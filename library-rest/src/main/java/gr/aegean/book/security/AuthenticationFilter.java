package gr.aegean.book.security;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

import javax.annotation.Priority;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.ext.Provider;
import javax.ws.rs.Priorities;

import gr.aegean.book.domain.User;
import gr.aegean.book.utility.DBHandler;

@Provider
@Priority(Priorities.AUTHENTICATION)  // needs to happen before authorization
public class AuthenticationFilter implements ContainerRequestFilter {
	
	private static final String AUTHORIZATION_PROPERTY = "Authorization";
	private static final String AUTHENTICATION_SCHEME = "Basic";
	
	@Context
    private ResourceInfo resourceInfo;
	
    @Override
    public void filter(ContainerRequestContext context) {
    	Method method = resourceInfo.getResourceMethod();
    	boolean allowMethod = method.isAnnotationPresent(PermitAll.class);
    	boolean rolesMethod = method.isAnnotationPresent(RolesAllowed.class);
    	boolean allowClass = method.getDeclaringClass().isAnnotationPresent(PermitAll.class);
    	if (allowMethod || (allowClass && !rolesMethod)) return;
    	
        final List<String> authorization = context.getHeaders().get(AUTHORIZATION_PROPERTY);
            
        //If no authorization information present; block access
        if(authorization == null || authorization.isEmpty()){
                context.abortWith(Response.status(Response.Status.UNAUTHORIZED)
                    .entity("You cannot access this resource").build());
                return;
        }
              
        //Get encoded username and password
        final String encodedUserPassword = authorization.get(0).replaceFirst(AUTHENTICATION_SCHEME + " ", "");
              
        //Decode username and password
        String usernameAndPassword = new String(Base64.getDecoder().decode(encodedUserPassword.getBytes()));;
  
        //Split username and password tokens
        final StringTokenizer tokenizer = new StringTokenizer(usernameAndPassword, ":");
        final String username = tokenizer.nextToken();
        final String password = tokenizer.nextToken();
            
        System.out.println(username + " " + password);
        User user = DBHandler.getUser(username); 
	    if (!authenticate(user,password)) {    
                    context.abortWith(Response.status(Response.Status.UNAUTHORIZED)
                        .entity("You cannot access this resource").build());
                    return;
        }
	        
	    SecurityContext oldContext = context.getSecurityContext();             
	    context.setSecurityContext(new BasicSecurityContext(user, oldContext.isSecure()));
    }
    
    private boolean authenticate(final User user, final String password){
        boolean auth = false;
          
        System.out.println("user is: " + user);
        if (user != null) {
        	System.out.println("user password is: " + user.getPassword());
        	if (password.equals(user.getPassword())) {
        		auth = true;
        	}	
        }	
        
        return auth;
    }
}
