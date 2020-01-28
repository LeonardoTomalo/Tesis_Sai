package ec.edu.upse.facsistel.gitwym.sai.models;

import org.springframework.data.couchbase.core.mapping.Document;

import com.couchbase.client.java.repository.annotation.Field;
import com.couchbase.client.java.repository.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Leonardo Tomalo.
 **/

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document
public class TipoMedia {
	@Id private String id;
	@Field private String descripcion;
	
	@Override
	public String toString() {
		return descripcion;
	}

	@Override
	public int hashCode() {
	    int hash = 0;
	    hash += (id != null ? id.hashCode() : 0);
	    return hash;
	}
	
	@Override
	public boolean equals(Object object) {
		String otherID = "";
		if (object instanceof TipoMedia) {
			otherID = ((TipoMedia)object).descripcion;
		} else if(object instanceof String){
			otherID = (String)object;
		} else {
			return false;
		}   

		if ((this.descripcion == null && otherID != null) || (this.descripcion != null && !this.descripcion.equals(otherID))) {
			return false;
		}
		return true;
	}    
}
