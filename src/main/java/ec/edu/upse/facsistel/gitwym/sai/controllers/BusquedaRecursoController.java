package ec.edu.upse.facsistel.gitwym.sai.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class BusquedaRecursoController{

    @FXML private JFXTextField txt_buscarRecurso;
    @FXML private JFXButton btn_buscar;    
    @FXML private AnchorPane anch_contenedorBusqueda;
    @FXML private AnchorPane anch_filtros;
    @FXML private ImageView img_imagenPrincipal;
    @FXML private Label lbl_descripcion;
    @FXML private Label lbl_filtros;
    @FXML private JFXComboBox<?> cmb_provincia;
    @FXML private JFXComboBox<?> cmb_canton;
    @FXML private JFXComboBox<?> cmb_parroquia;
    @FXML private JFXComboBox<?> cmb_categoria;
    @FXML private JFXComboBox<?> cmb_atractivo;
    @FXML private JFXComboBox<?> cmb_idioma;
    @FXML private TableView<?> tbl_listaRecurso;
    
	public void initialize() {
		
	}
    
    @FXML
    void buscarRecursoBTN(ActionEvent event) {
    	if (anch_filtros.isVisible()) {
        	anch_filtros.setVisible(false);
    		anch_filtros.setManaged(false);
		}
    }

    @FXML
    void abrirCerrarFiltros(MouseEvent event) {
    	if (anch_filtros.isVisible()) {
			anch_filtros.setVisible(false);
			anch_filtros.setManaged(false);
		}else {
			anch_filtros.setVisible(true);
			anch_filtros.setManaged(true);
		}	    
    }
	
}
