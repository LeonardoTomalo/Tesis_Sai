package ec.edu.upse.facsistel.gitwym.sai.utilities;

import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class ConsumeWS<T> {

	RestTemplate rest = new RestTemplate();

	public T saveOrUpdate(T pojo, Class<T> t) {
		try {
	    	String uri = "http://localhost:8082/" + t.getSimpleName().toLowerCase() + "/saveOrUpdate";
	    	return rest.postForObject(uri, pojo, t);	
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<T> getAll(Class<T> t){
		try {
	    	String uri = "http://localhost:8082/" + t.getSimpleName().toLowerCase() + "/getAll";
	    	ResponseEntity<List<T>> list = rest.exchange(uri, HttpMethod.GET, null, new ParameterizedTypeReference<List<T>>() { });	    		    	
	    	return list.getBody();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
