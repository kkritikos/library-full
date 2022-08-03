package gr.aegean.book.client;

import java.io.InputStream;
import java.util.Properties;

import gr.aegean.book.client.PropertyReader;

public class PropertyReader {
    private static String ip;
    private static String port;
	
    static {
    	getProperties();
    }
    
    private static Properties loadPropertyFile()
    {
    	InputStream input = PropertyReader.class.getClassLoader().getResourceAsStream("utility-client.properties");
        Properties props = new Properties();
        try {
            props.load(input);
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
        return props;
    }
    
    private static String getDefaultValueIfNull(String s, String defaultVal) {
    	if (s == null || s.trim().equals("")) return defaultVal;
    	else return s;
    }

	private static void getProperties(){
		Properties props = loadPropertyFile();
		ip = getDefaultValueIfNull(props.getProperty("ip"),"localhost");
		port = getDefaultValueIfNull(props.getProperty("port"),"8080");
	}
	
	public static String getIp() {
		return ip;
	}
	
	public static String getPort() {
		return port;
	}
	
}
