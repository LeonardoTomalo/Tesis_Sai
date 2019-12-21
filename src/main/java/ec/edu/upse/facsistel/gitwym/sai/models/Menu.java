package ec.edu.upse.facsistel.gitwym.sai.models;

import org.springframework.data.couchbase.core.mapping.Document;

import com.couchbase.client.deps.com.fasterxml.jackson.annotation.JsonProperty;
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
	@JsonProperty @Id private String id;
	@JsonProperty @Field private String nombre;
	@JsonProperty @Field private String url;
	@JsonProperty @Field private String logoRuta;
	@JsonProperty @Field private Integer orden;
	@JsonProperty @Field private Boolean estado;
	@JsonProperty @Field private Menu idPadre;
}
