package ec.edu.upse.facsistel.gitwym.sai.controllers;

import com.gluonhq.maps.MapPoint;
import com.gluonhq.maps.MapView;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;

import ec.edu.upse.facsistel.gitwym.sai.models.Categoria;
import ec.edu.upse.facsistel.gitwym.sai.models.Idiomas;
import ec.edu.upse.facsistel.gitwym.sai.models.Recurso;
import ec.edu.upse.facsistel.gitwym.sai.utilities.Context;
import ec.edu.upse.facsistel.gitwym.sai.utilities.General;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

public class RecursoPrincipalController {

    @FXML private AnchorPane anch_rp;
    @FXML private JFXButton btn_busqueda;
    @FXML private JFXButton btn_crear;
    @FXML private JFXButton btn_modificar;
    @FXML private JFXButton btn_eliminar;
    @FXML private HBox hbx_busqueda;
    @FXML private AnchorPane anch_busqueda;
    @FXML private JFXTextField txt_buscarRecurso;
    @FXML private JFXButton btn_buscarRecurso;
    @FXML private Label lbl_filtros;
    @FXML private AnchorPane anch_filtros;
    @FXML private JFXComboBox<Categoria> cmb_categoria;
    @FXML private JFXTextField txt_coordenadas;
    @FXML private JFXComboBox<Idiomas> cmb_idioma;
    @FXML private JFXListView<Recurso> lst_listaRecursos;
    @FXML private AnchorPane anch_contImgPrinBusqueda;
    @FXML private Label lbl_descripcionBusqueda;
    @FXML private AnchorPane anch_mapa;
    @FXML private HBox hbx_contenedorInfBasica;
    @FXML private AnchorPane anch_contenedor;
    @FXML private Label lbl_nombreRecurso;
    @FXML private Label lbl_infGeneral;
    @FXML private Label lbl_ubicacionZonal;
    @FXML private AnchorPane anch_contImgPrin;
    @FXML private JFXButton btn_masInformacion;
    @FXML private JFXButton btn_abrirInfBasica;

	MapView map = new MapView();
	MapPoint mapPoint = new MapPoint(-2.206610, -80.692470);
	
	public void initialize() {	
		showInformacionBasica();
		if (Context.getInstance().getMapViewContext() != null) {
			map = Context.getInstance().getMapViewContext();
		}else {
	        map.setCenter(mapPoint);
	        map.setZoom(9.5);
	        map.flyTo(1., mapPoint, 2.);
		}
        General.setMapatoAnchorPane(map, anch_mapa);
	}        

    @FXML
    void crearRecurso(ActionEvent event) {
    	General.setContentParent("/viewRecurso/Recurso.fxml", Context.getInstance().getAnch_Contenido());
    	Context.getInstance().setMapViewContext(map);
    }

    @FXML
    void eliminarRecurso(ActionEvent event) {

    }

    @FXML
    void modificarRecurso(ActionEvent event) {
    	General.setContentParent("/viewRecurso/Recurso.fxml", Context.getInstance().getAnch_Contenido());
    	Context.getInstance().setMapViewContext(map);
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
    void showInformacionBasica() {
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
    	General.setContentParent("/viewRecurso/Recurso.fxml", Context.getInstance().getAnch_Contenido());
    }
    
    
    @FXML
    void buscarRecursoNombre(ActionEvent event) {
    	if (anch_filtros.isVisible()) {
        	anch_filtros.setVisible(false);
    		anch_filtros.setManaged(false);
		}
    }

    @FXML
    void abrirCerrarFiltros() {
    	if (anch_filtros.isVisible()) {
			anch_filtros.setVisible(false);
			anch_filtros.setManaged(false);
		}else {
			anch_filtros.setVisible(true);
			anch_filtros.setManaged(true);
		}	    
    }
	
}
