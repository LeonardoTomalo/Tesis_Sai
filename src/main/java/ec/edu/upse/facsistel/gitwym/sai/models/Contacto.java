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
public class Contacto {
	@Id private String codigo;
	@Field private String nombre;
	@Field private String descripcion;
	@Field private String telefono;
	@Field private String celular;
	@Field private String correo;
	@Field private String facebook;
	@Field private String instagram;
	@Field private String twitter;
	@Field private Boolean estado;
}
