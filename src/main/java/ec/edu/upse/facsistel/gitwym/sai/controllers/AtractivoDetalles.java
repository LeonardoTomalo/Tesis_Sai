package ec.edu.upse.facsistel.gitwym.sai.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;

import ec.edu.upse.facsistel.gitwym.sai.utilities.General;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

public class AtractivoDetalles {

    @FXML private JFXTextField txt_nombre;
    @FXML private JFXTextField txt_coordenadas;
    @FXML private JFXTextArea txt_descripcion;
    @FXML private JFXButton btn_verMapa;
    @FXML private JFXButton btn_agregarTipoAtractivo;
    @FXML private JFXButton btn_eliminarTipoAtractivo;
    @FXML private JFXButton btn_guardarAtractivo;
    @FXML private JFXButton btn_salir;
    @FXML private JFXListView<?> lst_listaTiposAtractivos;
    @FXML private AnchorPane anch_galeria;
    
    public void initialize() {	
		General.setContentParent("/viewGaleria/Galeria.fxml", anch_galeria);
		
	}     
    @FXML
    void agregarTipoAtractivo(ActionEvent event) {

    }

    @FXML
    void eliminarTipoAtractivo(ActionEvent event) {

    }

    @FXML
    void guardarAtractivo(ActionEvent event) {

    }

    @FXML
    void salirAtractivo(ActionEvent event) {

    }

    @FXML
    void verMapa(ActionEvent event) {

    }

}
