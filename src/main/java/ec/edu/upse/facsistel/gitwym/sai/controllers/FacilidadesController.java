package ec.edu.upse.facsistel.gitwym.sai.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TitledPane;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.layout.AnchorPane;

public class FacilidadesController {

    @FXML private TitledPane acrd_idiomas;
    @FXML private TitledPane acrd_contactos;
    @FXML private TitledPane acrd_Accesibilidad;
    @FXML private JFXButton btn_agregarIdiomas;
    @FXML private JFXButton btn_eliminarIdiomas;
    @FXML private JFXButton btn_agregarAccesibilidad;
    @FXML private JFXButton btn_eliminarAccesibilidad;
    @FXML private JFXButton btn_agregarContacto;
    @FXML private JFXButton btn_eliminarContacto;
    @FXML private JFXButton btn_editarContacto;
    @FXML private JFXListView<?> lst_listaAccesibilidad;
    @FXML private JFXListView<?> lst_listaIdiomas;
    @FXML private JFXListView<?> lst_listaContactos;
    @FXML private TableView<?> tbl_listaComodidades;
    @FXML private JFXButton btn_BuscarComodidades;
    @FXML private JFXButton btn_AgregarComodidades;
    @FXML private JFXButton btn_eliminarComodidades;
    @FXML private JFXButton btn_editarComodidades;
    @FXML private JFXTextField txt_buscarComodidades;
    @FXML private AnchorPane anch_atractivos;

	public void initialize() {	
//		General.setContentParent("/viewAtractivos/Atractivos.fxml", anch_atractivos);
		
	}     
        
    @FXML
    void agregarAccesibilidad(ActionEvent event) {

    }

    @FXML
    void agregarComodidades(ActionEvent event) {

    }

    @FXML
    void agregarContacto(ActionEvent event) {

    }

    @FXML
    void agregarIdiomas(ActionEvent event) {

    }

    @FXML
    void buscarComodidades(ActionEvent event) {

    }

    @FXML
    void buscarComodidadesTextChange(InputMethodEvent event) {

    }

    @FXML
    void editarComodidades(ActionEvent event) {

    }

    @FXML
    void editarContacto(ActionEvent event) {

    }

    @FXML
    void eliminarAccesibilidad(ActionEvent event) {

    }

    @FXML
    void eliminarComodidades(ActionEvent event) {

    }

    @FXML
    void eliminarContacto(ActionEvent event) {

    }

    @FXML
    void eliminarIdiomas(ActionEvent event) {

    }

}
