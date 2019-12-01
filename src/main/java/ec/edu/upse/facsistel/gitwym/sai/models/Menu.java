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
public class Menu {
	@Id private String id;
	@Field private String nombre;
	@Field private String url;
	@Field private String logoRuta;
	@Field private Integer orden;
	@Field private Boolean estado;
	@Field private Menu idPadre;
}
