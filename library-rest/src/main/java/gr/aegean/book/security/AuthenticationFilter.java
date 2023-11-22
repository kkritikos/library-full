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
        //Access allowed for all
    	boolean permitAllClass = method.getDeclaringClass().isAnnotationPresent(PermitAll.class);
    	boolean permitAllMethod = method.isAnnotationPresent(PermitAll.class);
    	boolean rolesAllowedMethod = method.isAnnotationPresent(RolesAllowed.class);
    	System.out.println(method.getClass() + " " + method.getDeclaringClass() + " " + permitAllClass + " " + permitAllMethod + " " + rolesAllowedMethod);
        if ((!permitAllClass && !permitAllMethod) || (permitAllClass && rolesAllowedMethod)) {
        	System.out.println("Method is: " + method + " ");
        	final List<String> authorization = context.getHeaders().get(AUTHORIZATION_PROPERTY);
            
            //If no authorization information present; block access
            if(authorization == null || authorization.isEmpty())
            {
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
	        
	        if(method.isAnnotationPresent(RolesAllowed.class)){
                RolesAllowed rolesAnnotation = method.getAnnotation(RolesAllowed.class);
                Set<String> rolesSet = new HashSet<String>(Arrays.asList(rolesAnnotation.value()));
       
                if(!isUserAllowed(username, password, rolesSet)){
                    context.abortWith(Response.status(Response.Status.UNAUTHORIZED)
                        .entity("You cannot access this resource").build());
                    return;
                }
            }
	        
	        /*SecurityContext oldContext = context.getSecurityContext();             
	        context.setSecurityContext(new BasicSecurityConext(user, oldContext.isSecure()));*/
        }
    }
    
    private boolean isUserAllowed(final String username, final String password, final Set<String> rolesSet){
        boolean isAllowed = false;
          
        System.out.println(username + " " + password + " " + rolesSet.iterator().next());
        User user = DBHandler.getUser(username);
        System.out.println("user is: " + user);
        if (user != null) {
        	System.out.println("user password is: " + user.getPassword());
        	if (password.equals(user.getPassword())) {
        		System.out.println("" + user.getRoles());
        		for (String role: user.getRoles()) {
        			if (rolesSet.contains(role)) {
        				isAllowed = true;
        				break;
        			}
        		}
        	}	
        }	
        
        return isAllowed;
    }
}
