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
public class Ranking {
	@Id private String codigo;
	@Field private String descripcion;
	@Field private long votosFavor;
	@Field private long votosContra;
	@Field private ArrayList<String> usuariosIds;  //*** Para validar que no se repita el voto.
}
