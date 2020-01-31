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
public class Recurso {
	@Id private String id;
	@Field private String rev;
	@Field private String nombre;
	@Field private String descripcion;
	@Field private String informacionGeneral;
	@Field private String direccion;
	@Field private String coordenadas;
	@Field private String propietario;
	@Field private Boolean estado;
	@Field private String idLocalizacion;//parroquia||provincia||pais
	@Field private ArrayList<Costo> costoServicio;
	@Field private ArrayList<String> idsSenderos;
	@Field private ArrayList<String> idsCategoria;//hacerlo con un checkList
	@Field private ArrayList<String> idsAccesibilidades;
	@Field private Ranking ranking;
	@Field private ArrayList<String> idiomas;
	@Field private Seguridad seguridad;
	@Field private ArrayList<Contacto> contactos;
	@Field private ArrayList<Comentario> comentarios;
	@Field private ArrayList<String> idsMediaCloudResources;	
	
	@Override
	public String toString() {
		return nombre;
	}	
	
}
