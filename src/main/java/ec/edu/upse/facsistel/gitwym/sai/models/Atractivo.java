package ec.edu.upse.facsistel.gitwym.sai.models;

import java.util.ArrayList;

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
public class Atractivo {
	@Id private String id;
	@Field private String nombre;
	@Field private String descripcion;
	@Field private String coordenadas;
	@Field private Boolean estado;
	@Field private ArrayList<String> tipoAtractivosIDS;
	@Field private ArrayList<Imagen> imagenes;
}
