package gr.aegean.book.app;

import java.net.URI;

import javax.ws.rs.core.UriBuilder;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.util.resource.ResourceFactory;
import org.glassfish.jersey.jetty.JettyHttpContainer;
import org.glassfish.jersey.jetty.JettyHttpContainerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;

public class BookApp extends ResourceConfig{
	private Server server;
	
	public BookApp() {
		packages("gr.aegean.book.service");
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
