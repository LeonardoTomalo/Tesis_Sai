package ec.edu.upse.facsistel.gitwym.sai.utilities;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

import org.springframework.web.client.RestTemplate;

public class ConsumeWS<T> {
	
	public static <T> T saveOrUpdate(T pojo, Class<T> t) {
		try {
			RestTemplate rest = new RestTemplate();
	    	String uri = "http://localhost:8082/" + t.getName().toLowerCase() + "/saveOrUpdate";
	    	return rest.postForObject(uri, pojo, t);					
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@SuppressWarnings({"unchecked" })
	public static <T> List<T> getAll(Class<T> t){
		try {
			RestTemplate rest = new RestTemplate();
	    	String uri = "http://localhost:8082/" + t.getName().toLowerCase() + "/getAll";
	    	T[] lt = (T[]) Array.newInstance(t, 1000);
	    	return (List<T>) Arrays.asList(rest.getForObject(uri, lt.getClass()));		
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
