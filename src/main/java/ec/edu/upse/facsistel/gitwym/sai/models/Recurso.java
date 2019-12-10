package ec.edu.upse.facsistel.gitwym.sai.models;

import java.util.ArrayList;

import org.springframework.data.couchbase.core.mapping.Document;
import org.springframework.data.couchbase.core.mapping.id.GeneratedValue;
import org.springframework.data.couchbase.core.mapping.id.GenerationStrategy;

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
	@Id @GeneratedValue(strategy = GenerationStrategy.UNIQUE) private String id;
	@Field private String rev;
	@Field private String nombre;
	@Field private String descripcion;
	@Field private String informacionGeneral;
	@Field private String direccion;
	@Field private String coordenadas;
	@Field private String documentClass;
	@Field private String propietario;
	@Field private Boolean estado;
	@Field private Seguridad seguridad;
	@Field private ArrayList<String> categoriasIds;
	@Field private Ranking ranking;
	@Field private ArrayList<Costo> costos;
	@Field private ArrayList<String> senderosIds;
	@Field private ArrayList<String> comodidadesIds;
	@Field private ArrayList<String> accesibilidadesIds;
	@Field private ArrayList<String> atractivosIds;
	@Field private ArrayList<String> idiomasIds;
	@Field private ArrayList<Imagen> imagenes;
	@Field private ArrayList<Contacto> contactos;
	@Field private ArrayList<Video> videos;
	@Field private ArrayList<Animacion3D> animaciones3d;
	@Field private ArrayList<Comentario> comentarios;	
}
