package ec.edu.upse.facsistel.gitwym.sai.controllers;

import org.springframework.web.client.RestTemplate;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;

import ec.edu.upse.facsistel.gitwym.sai.models.Recurso;
import ec.edu.upse.facsistel.gitwym.sai.utilities.General;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

public class RecursoController {

    @FXML private JFXButton btn_guardar;
    @FXML private JFXButton btn_atras;
    @FXML private JFXButton btn_buscarCoordenadas;
    @FXML private JFXComboBox<?> cmb_provinciaRecurso;
    @FXML private JFXComboBox<?> cmb_cantonRecurso;
    @FXML private JFXComboBox<?> cmb_parroquiaRecurso;
    @FXML private JFXComboBox<?> cmb_categoriaRecurso;
    @FXML private JFXTextArea txt_descripcionRecurso;
    @FXML private JFXTextArea txt_infGeneralRecurso;
    @FXML private JFXTextField txt_nombreRecurso;
    @FXML private JFXTextField txt_coordenadasRecurso;
    @FXML private JFXTextField txt_direccionRecurso;
    @FXML private JFXTabPane tbp_recurso;
    @FXML private AnchorPane anch_recurso;
    @FXML private AnchorPane anch_tabs;
    @FXML private AnchorPane anch_galeria;
    @FXML private AnchorPane anch_facilidades;
    @FXML private AnchorPane anch_senderos;
    @FXML private AnchorPane anch_video;
    @FXML private AnchorPane anch_animaciones3d;
    @FXML private AnchorPane anch_comentarios;

	public void initialize() {	
		General.setContentParent("/viewGaleria/Galeria.fxml", anch_galeria);
		General.setContentParent("/viewFacilidades/Facilidades.fxml", anch_facilidades);
		General.setContentParent("/viewVideos/Videos.fxml", anch_video);
		General.setContentParent("/viewAnimacion3D/Animacion3D.fxml", anch_animaciones3d);
		General.setContentParent("/viewSenderos/Senderos.fxml", anch_senderos);		
	}     
    
    @FXML
    void atras(ActionEvent event) {
    	General.setContentParent("/viewPrincipal/RecursoPrincipal.fxml", (AnchorPane) anch_recurso.getParent());
    }

    @FXML
    void guardar(ActionEvent event) {
    	Recurso r = savePojoRecurso();
    	
    	//esto se debe poner en utilities haciendo generico para tener mejor codigo
    	RestTemplate rest = new RestTemplate();
    	final String uri = "http://localhost:8082/recurso/saveOrUpdate";
    	Recurso p = rest.postForObject(uri, r, Recurso.class);
    	System.out.println("Aqui empieza el POST FOR OBJECT" + p.toString());
    	
//    	RestTemplate rest = new RestTemplate();
//    	final String uri = "http://localhost:8080/persons1";
//    	ResponseEntity<List<Person>> rateResponse =
//    	        rest.exchange(uri, HttpMethod.GET, null, new ParameterizedTypeReference<List<Person>>() { });
//    	List<Person> persons = rateResponse.getBody();
//    	
//    	lst_personas.getItems().addAll(persons);
    }

    @FXML
    void buscarCoordenadasEnMapa(ActionEvent event) {
    	
    }

    private Recurso savePojoRecurso() {
    	Recurso r = new Recurso();
    	System.out.println("PRESENTO LOS VALORES EN FXML: " + txt_nombreRecurso.getText());
    	r.setNombre(txt_nombreRecurso.getText());
    	r.setDescripcion(txt_descripcionRecurso.getText());
    	r.setInformacionGeneral(txt_infGeneralRecurso.getText());
    	r.setCoordenadas(txt_coordenadasRecurso.getText());
    	r.setDireccion(txt_direccionRecurso.getText());
		return r;
	}
}
