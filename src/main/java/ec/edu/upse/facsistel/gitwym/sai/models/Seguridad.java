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
public class Seguridad {
	@Id private String id;
	@Field private String descripcion;
	@Field private long votosBaja;
	@Field private long votosMedia;
	@Field private long votosAlta;
	@Field private long votosMuyAlta;
	@Field private ArrayList<String> idsUsuarioBaja;  //*** Para validar que el voto no se repita.
	@Field private ArrayList<String> idsUsuarioMedia;  //*** Para validar que el voto no se repita.
	@Field private ArrayList<String> idsUsuarioAlta;  //*** Para validar que el voto no se repita.
	@Field private ArrayList<String> idsUsuarioMuyAlta;  //*** Para validar que el voto no se repita.
}
