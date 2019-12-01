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
public class Imagen {
	@Id private String codigo;
	@Field private String nombre;
	@Field private String descripcion;
	@Field private String autor;
	@Field private Usuario usuarioIngresa;
	@Field private String coordenadas;
	@Field private Date fecha;
	@Field private Boolean isReportado;
	@Field private String url;
	@Field private Boolean isPrincipal;
	@Field private Boolean estado;
	@Field private ArrayList<String> etiquetasIds;
	@Field private Ranking ranking;
}
