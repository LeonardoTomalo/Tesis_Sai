package ec.edu.upse.facsistel.gitwym.sai.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;

import ec.edu.upse.facsistel.gitwym.sai.utilities.General;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

public class Recurso {

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
    	
    }

    @FXML
    void buscarCoordenadasEnMapa(ActionEvent event) {
    	
    }

}
