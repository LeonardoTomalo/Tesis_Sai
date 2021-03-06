package ec.edu.upse.facsistel.gitwym.sai.models;

import java.util.List;

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
public class Rol {
	@Id private String id;
	@Field private String rol;
	@Field private String descripcion;
	@Field private Boolean estado;
	@Field private List<String> menusIds;
}
