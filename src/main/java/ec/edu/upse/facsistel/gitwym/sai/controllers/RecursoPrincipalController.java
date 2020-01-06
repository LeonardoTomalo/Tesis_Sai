package ec.edu.upse.facsistel.gitwym.sai.controllers;

import com.gluonhq.maps.MapPoint;
import com.gluonhq.maps.MapView;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;

import ec.edu.upse.facsistel.gitwym.sai.utilities.Context;
import ec.edu.upse.facsistel.gitwym.sai.utilities.General;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

public class RecursoPrincipalController {

    @FXML private JFXButton btn_busqueda;
    @FXML private JFXButton btn_crear;
    @FXML private JFXButton btn_modificar;
    @FXML private JFXButton btn_eliminar;
    @FXML private JFXButton btn_masInformacion;
    @FXML private JFXButton btn_abrirInfBasica;
    @FXML private HBox hbx_busqueda;
    @FXML private HBox hbx_contenedorInfBasica;
    @FXML private AnchorPane anch_busqueda;
    @FXML private AnchorPane anch_mapa;
    @FXML private AnchorPane anch_contenedor;
    @FXML private AnchorPane anch_rp;
    

    @FXML private Label lbl_nombreRecurso;
    @FXML private Label lbl_descripcion;
    @FXML private Label lbl_ubicacionZonal;
    @FXML private ImageView img_imagenPrincipal;
    

    @FXML private JFXTextField txt_buscarRecurso;
    @FXML private JFXButton btn_buscar;    
    @FXML private AnchorPane anch_contenedorBusqueda;
    @FXML private AnchorPane anch_filtros;
    @FXML private ImageView img_imagenPrincipalB;
    @FXML private Label lbl_descripcionB;
    @FXML private Label lbl_filtros;
    @FXML private JFXComboBox<?> cmb_provincia;
    @FXML private JFXComboBox<?> cmb_canton;
    @FXML private JFXComboBox<?> cmb_parroquia;
    @FXML private JFXComboBox<?> cmb_categoria;
    @FXML private JFXComboBox<?> cmb_atractivo;
    @FXML private JFXComboBox<?> cmb_idioma;
    @FXML private TableView<?> tbl_listaRecurso;

	MapView map = new MapView();		
	MapPoint mapPoint = new MapPoint(-2.206610, -80.692470);
	
	public void initialize() {	
		showBusqueda();		
        map.setCenter(mapPoint);
        map.setZoom(10);
        map.flyTo(1., mapPoint, 2.);      
        General.setMapatoAnchorPane(map, anch_mapa);
	}        

    @FXML
    void crearRecurso(ActionEvent event) {
    	General.setContentParent("/viewRecurso/Recurso.fxml", Context.getInstance().getAnch_Contenido());
    }

    @FXML
    void eliminarRecurso(ActionEvent event) {

    }

    @FXML
    void modificarRecurso(ActionEvent event) {
    	
    }

    @FXML
    void showBusqueda() {
    	if (hbx_busqueda.isVisible()) {
			hbx_busqueda.setVisible(false);
			hbx_busqueda.setManaged(false);
		}else {
			hbx_busqueda.setVisible(true);
			hbx_busqueda.setManaged(true);
		}	    	
    }

    @FXML
    void showInformacionBasica(ActionEvent event) {
    	if (hbx_contenedorInfBasica.isVisible()) {
			hbx_contenedorInfBasica.setVisible(false);
			hbx_contenedorInfBasica.setManaged(false);
		}else {
			hbx_contenedorInfBasica.setVisible(true);
			hbx_contenedorInfBasica.setManaged(true);
		}	    	
    }

    @FXML
    void showMasInformacion(ActionEvent event) {
//    	Context.getInstance().setAnch_Contenido(anch_rp); 
    	General.setContentParent("/viewRecurso/Recurso.fxml", Context.getInstance().getAnch_Contenido());
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
