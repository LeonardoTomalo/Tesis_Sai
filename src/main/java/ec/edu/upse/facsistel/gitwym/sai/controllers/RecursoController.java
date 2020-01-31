package ec.edu.upse.facsistel.gitwym.sai.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TitledPane;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.layout.AnchorPane;
import org.controlsfx.control.CheckListView;

public class RecursoController {

    @FXML private AnchorPane anch_recurso;
    @FXML private JFXButton btn_guardar;
    @FXML private JFXButton btn_atras;
    @FXML private JFXComboBox<?> cmb_provinciaRecurso;
    @FXML private JFXComboBox<?> cmb_cantonRecurso;
    @FXML private JFXComboBox<?> cmb_parroquiaRecurso;
    @FXML private JFXTextField txt_nombreRecurso;
    @FXML private JFXTextArea txt_descripcionRecurso;
    @FXML private JFXTextArea txt_infGeneralRecurso;
    @FXML private JFXTextField txt_coordenadasRecurso;
    @FXML private JFXButton btn_buscarCoordRecurso;
    @FXML private JFXTextField txt_direccionRecurso;
    @FXML private JFXTextField txt_propietarioRecurso;
    @FXML private AnchorPane anch_tabs;
    @FXML private JFXTabPane tbp_recurso;
    @FXML private AnchorPane anch_galeria;
    @FXML private JFXComboBox<?> cmb_tipoMediaBusqueda;
    @FXML private JFXButton btn_verMediaMapa;
    @FXML private JFXListView<?> lst_listaMedios;
    @FXML private JFXTextField txt_buscarNombreMedio;
    @FXML private JFXButton btn_nuevoMedio;
    @FXML private JFXButton btn_EliminarMedio;
    @FXML private JFXButton btn_modificarMedio;
    @FXML private AnchorPane contenedorDeMedios;
    @FXML private AnchorPane anch_facilidades;
    @FXML private TitledPane accd_costosRecurso;
    @FXML private JFXButton btn_addCosto;
    @FXML private JFXButton btn_eliminarCosto;
    @FXML private JFXButton btn_modificarCosto;
    @FXML private JFXListView<?> lst_listaCostosRecurso;
    @FXML private TitledPane accd_ContactosRecurso;
    @FXML private JFXButton btn_addContacto;
    @FXML private JFXButton btn_eliminarContacto;
    @FXML private JFXButton btn_editarContacto;
    @FXML private JFXListView<?> lst_listaContactosRecurso;
    @FXML private JFXButton btn_verComodidadMapa;
    @FXML private JFXTextField txt_buscarComodidadesRecurso;
    @FXML private JFXButton btn_addComodidades;
    @FXML private JFXButton btn_eliminarComodidades;
    @FXML private JFXButton btn_modificarComodidades;
    @FXML private JFXListView<?> lst_listaComodidadesRecurso;
    @FXML private TitledPane accd_accesibilidadesRecurso;
    @FXML private CheckListView<?> chklst_accesibilidadesRecurso;
    @FXML private TitledPane accd_categoriasRecurso;
    @FXML private CheckListView<?> chklst_categoriasRecurso;
    @FXML private TitledPane acc_idiomasRecurso;
    @FXML private CheckListView<?> chklst_idiomasRecurso;
    @FXML private AnchorPane anch_comentarios1;
    @FXML private JFXComboBox<?> cmb_tipoAtractivoBusqueda;
    @FXML private JFXButton btn_verAtractivoMapa;
    @FXML private JFXListView<?> lst_listaAtractivos;
    @FXML private JFXTextField txt_buscarNombreAtractivos;
    @FXML private JFXButton btn_nuevoAtractivo;
    @FXML private JFXButton btn_EliminarAtractivo;
    @FXML private JFXButton btn_modificarAtractivo;
    @FXML private AnchorPane contenedorDeAtractivos;
    @FXML private AnchorPane anch_senderos;
    @FXML private AnchorPane anch_comentarios;
	
	public void initialize() {	
		
	}     

    @FXML
    void atras(ActionEvent event) {

    }

    @FXML
    void guardarRecurso(ActionEvent event) {

    }

    @FXML
    void addComodidadesRecurso(ActionEvent event) {

    }

    @FXML
    void addContactoRecurso(ActionEvent event) {

    }

    @FXML
    void addCostoRecurso(ActionEvent event) {

    }

    @FXML
    void addNuevaMedia(ActionEvent event) {

    }

    @FXML
    void addNuevoAtractivo(ActionEvent event) {

    }

    @FXML
    void buscarComodidadesTextChange(InputMethodEvent event) {

    }

    @FXML
    void buscarRecursoEnMapa(ActionEvent event) {

    }

    @FXML
    void eliminarAtractivo(ActionEvent event) {

    }

    @FXML
    void eliminarComodidadesRecurso(ActionEvent event) {

    }

    @FXML
    void eliminarContactoRecurso(ActionEvent event) {

    }

    @FXML
    void eliminarCostoRecurso(ActionEvent event) {

    }

    @FXML
    void eliminarMedia(ActionEvent event) {

    }

    @FXML
    void modificarAtractivo(ActionEvent event) {

    }

    @FXML
    void modificarComodidadesRecurso(ActionEvent event) {

    }

    @FXML
    void modificarContactoRecurso(ActionEvent event) {

    }

    @FXML
    void modificarCostoRecurso(ActionEvent event) {

    }

    @FXML
    void modificarMedio(ActionEvent event) {

    }

    @FXML
    void showAtractivoMapa(ActionEvent event) {

    }

    @FXML
    void showComodidadMapa(ActionEvent event) {

    }

    @FXML
    void showMediaMapa(ActionEvent event) {

    }

}
