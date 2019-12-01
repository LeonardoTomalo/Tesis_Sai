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
public class Transporte {
	@Id private String id;
	@Field private String descripcion;
	@Field private Float distanciaAproximada;
	@Field private Float tiempoRecorrido; 
	@Field private Boolean estado;
	@Field private TipoTransporte tipoTransporte;
}
