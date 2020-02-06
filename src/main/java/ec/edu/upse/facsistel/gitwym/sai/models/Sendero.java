package ec.edu.upse.facsistel.gitwym.sai.models;

import java.util.ArrayList;

import org.springframework.data.annotation.Transient;
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
public class Sendero {
	@Id private String id;
	@Field private String nombre;
	@Field private String descripcion;
	@Field private Float distanciaAproximada;
	@Field private Float tiempoRecorrido; 
	@Field private String instrucciones;
	@Field private Boolean estado;
	@Field private String idDificultadRecorrido;
	@Field private Seguridad seguridad;
	@Field private String idSenalCelular;
	@Field private ArrayList<Costo> costoServicio;
	@Field private ArrayList<Recorrido> recorridoRuta;
	@Field private ArrayList<String> idsEquipamiento;
	@Field private ArrayList<String> idsTransporte;
	@Field private ArrayList<String> idsMediaCloudResources;
	@Field private ArrayList<Comentario> comentarios;
	
	@Transient private ArrayList<MediaCloudResources> listaMCR;
}
