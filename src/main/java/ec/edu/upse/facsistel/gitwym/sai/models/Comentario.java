package ec.edu.upse.facsistel.gitwym.sai.models;

import java.util.ArrayList;
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
public class Comentario {
	@Id private String codigo;
	@Field private String descripcion;
	@Field private Usuario autor;
	@Field private Date fecha;
	@Field private Boolean isReportado;
	@Field private Boolean estado;
	@Field private ArrayList<Comentario> comentarios;
	@Field private Ranking ranking;	
}
