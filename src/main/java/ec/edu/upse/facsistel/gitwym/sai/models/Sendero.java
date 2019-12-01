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
public class Sendero {
	@Id private String id;
	@Field private String nombre;
	@Field private String descripcion;
	@Field private Float distanciaAproximada;
	@Field private Float tiempoRecorrido; 
	@Field private String instrucciones;
	@Field private Boolean estado;
	@Field private String dificultadRecorridoIds;
	@Field private Seguridad seguridad;
	@Field private String senalCelularIds;
	@Field private ArrayList<Costo> costos;
	@Field private ArrayList<Recorrido> recorridoRuta;
	@Field private ArrayList<String> equipamientoIds;
	@Field private ArrayList<String> transportesIds;
	@Field private ArrayList<String> atractivosIds;
	@Field private ArrayList<Imagen> imagenes;
	@Field private ArrayList<Video> videos;
	@Field private ArrayList<Animacion3D> animaciones3d;
	@Field private ArrayList<Comentario> comentarios;
}
