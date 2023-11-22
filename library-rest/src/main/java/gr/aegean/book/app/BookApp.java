package gr.aegean.book.app;

import java.net.URI;

import javax.ws.rs.core.UriBuilder;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.glassfish.jersey.jetty.JettyHttpContainerFactory;
import org.glassfish.jersey.server.ResourceConfig;
//import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;

import gr.aegean.book.utility.DBHandler;

public class BookApp extends ResourceConfig{
	private Server server;
	
	public BookApp() {
		packages("gr.aegean.book.service");
		//register(RolesAllowedDynamicFeature.class);
		register(gr.aegean.book.security.AuthenticationFilter.class);
		
		DBHandler.addAdminUser();
		
		URI baseUri = UriBuilder.fromUri("http://localhost/").port(8080).build();
		server = JettyHttpContainerFactory.createServer(baseUri, this);
		Connector connector = new ServerConnector(server);
		server.addConnector(connector);
	}
	
	private void startServer() {
		try {
			server.start();
			server.join();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		BookApp app = new BookApp();
		app.startServer();
	}
}
