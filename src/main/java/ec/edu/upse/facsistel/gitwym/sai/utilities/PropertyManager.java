package ec.edu.upse.facsistel.gitwym.sai.utilities;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan
@PropertySource("application.properties")
public class PropertyManager { 
	
	public static String getBaseUrl() {
		String resourceName = "application.properties";
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		Properties config = new Properties();
		try(InputStream resourceStream = loader.getResourceAsStream(resourceName)) {
		    config.load(resourceStream);
		} catch (IOException e){
			System.err.println("Error cargando propiedades de application.properties. Asegurese de que el archivo existe y que se encuentre en el build path.");
			e.printStackTrace();
			System.exit(1);
		}
		
		return "http://" + config.getProperty("server.hostname") + ":" + config.getProperty("server.port");
	}
}
