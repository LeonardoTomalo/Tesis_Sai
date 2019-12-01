package ec.edu.upse.facsistel.gitwym.sai.utilities;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import lombok.Getter;
import lombok.Setter;

public class PropertyManager {
    @Getter @Setter private String serverHostname;    
    @Getter @Setter private String serverPort;    
    @Getter @Setter private String servicesIndexPath;    
    @Getter @Setter public static String baseURL;	
    @Getter @Setter private String recursosPath;
    @Getter @Setter private String senderosPath;
	
	public Map<String, String> mapClaseURLRelativo;
	
	public PropertyManager() {		
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
		
		serverHostname = config.getProperty("server.hostname");
		serverPort = config.getProperty("server.port");
		servicesIndexPath = config.getProperty("services.index.path");
		recursosPath = config.getProperty("recursos.path");
		senderosPath = config.getProperty("senderos.path");
		baseURL = "http://"+serverHostname+":"+serverPort+"/"+servicesIndexPath; 
		mensajeCargaCorrectaPropiedades();
		mapearClasesAPropiedadURLRelativo();
	}
	
	private void mapearClasesAPropiedadURLRelativo(){
		mapClaseURLRelativo = new HashMap<String, String>();
//		mapClaseURLRelativo.put(Recurso.class.getName(), recursosPath);
//		mapClaseURLRelativo.put(Sendero.class.getName(), senderosPath);
	}
	
	public String getUrlRelativoDesdeClase(String nombreClase){
		String urlRelativo = mapClaseURLRelativo.get(nombreClase);
		if(urlRelativo.equals(null) || urlRelativo.equals("")){
			System.err.println("No se encontro URL relativo para este nombre de clase. Revisar mapeo");
		}
		
		return urlRelativo;
	}

	private void mensajeCargaCorrectaPropiedades() {
		System.out.println("**********Cargando Propiedades**********");
		System.out.println("Server IP - IP del Servidor: " + serverHostname);
		System.out.println("ServerPort - Puerto del Servicio: " + serverPort);
		System.out.println("ServiceIndexPath - URL Relativo de la Raiz del Servicio: " +servicesIndexPath);
		
		System.out.println("URL Servicio - URL absoluto de la Raiz del Servicio segun archivo de configuracio: " + baseURL);
		System.out.println("**********Carga de propiedades Exitosa**********\n\n\n");
	}
	
	public static String getURLBase(){
		if(PropertyManager.baseURL.equals(null) || PropertyManager.baseURL.equals("")){
//			PropertyManager pm = new PropertyManager();
		}		
		return PropertyManager.baseURL;
	}
}
