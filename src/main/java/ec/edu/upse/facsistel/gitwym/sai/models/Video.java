package ec.edu.upse.facsistel.gitwym.sai.models;

import java.util.Date;

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
public class Video {
	@Id private String codigo;
	@Field private String nombre;
	@Field private String descripcion;
	@Field private String autor;
	@Field private Date fecha;
	@Field private String url;
	@Field private Boolean estado;
	@Field private Ranking ranking;	
}
