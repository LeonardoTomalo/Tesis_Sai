package ec.edu.upse.facsistel.gitwym.sai.controllers;

import com.gluonhq.maps.MapPoint;
import com.gluonhq.maps.MapView;
import com.jfoenix.controls.JFXButton;

import ec.edu.upse.facsistel.gitwym.sai.utilities.Context;
import ec.edu.upse.facsistel.gitwym.sai.utilities.General;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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

	MapView map = new MapView();		
	MapPoint mapPoint = new MapPoint(-2.206610, -80.692470);
	
	public void initialize() {	
		General.setContentParent("/viewPrincipal/RecursoInformacionBasica.fxml", anch_contenedor);
		General.setContentParent("/viewBusqueda/BusquedaRecurso.fxml", anch_busqueda);
		showBusqueda();		
        map.setCenter(mapPoint);
        map.setZoom(10);
        map.flyTo(1., mapPoint, 2.);      
        General.setMapatoAnchorPane(map, anch_mapa);
	}        

    @FXML
    void crearRecurso(ActionEvent event) {
    	Context.getInstance().setAnch_Contenido(anch_rp);
    	General.setContentParent("/viewRecurso/Recurso.fxml", (AnchorPane) anch_rp.getParent());
    }

    @FXML
    void eliminarRecurso(ActionEvent event) {

    }

    @FXML
    void modificarRecurso(ActionEvent event) {
//    	NotificationsTool.showNotificationPush();
    	
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
    	Context.getInstance().setAnch_Contenido(anch_rp); 
    	General.setContentParent("/viewRecurso/Recurso.fxml", (AnchorPane) anch_rp.getParent());
    }
	
}
