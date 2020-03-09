package ec.edu.upse.facsistel.gitwym.sai.controllers;
import java.util.ArrayList;
import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;

import ec.edu.upse.facsistel.gitwym.sai.models.Comodidades;
import ec.edu.upse.facsistel.gitwym.sai.models.TipoComodidad;
import ec.edu.upse.facsistel.gitwym.sai.utilities.Context;
import ec.edu.upse.facsistel.gitwym.sai.utilities.General;
import ec.edu.upse.facsistel.gitwym.sai.utilities.Message;
import ec.edu.upse.facsistel.gitwym.sai.utilities.PropertyManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;

public class ModalComodidadController {

    @FXML private JFXTextField txt_coordenadas;
    @FXML private JFXButton btn_verMapa;
    @FXML private JFXComboBox<TipoComodidad> cmb_tipoComodidad;
    @FXML private JFXTextArea txt_descripcion;
    @FXML private JFXButton btn_Guardar;
    @FXML private JFXButton btn_Cancelar;

    // CONSUMIR WEB SERVICES
   	RestTemplate rest = new RestTemplate();
  	String urlBase = PropertyManager.getBaseUrl();
   	String uriComodidad = urlBase + "/comodidades";
 	String uriTipoComodidad = urlBase + "/tipoComodidad";
     
   	//DE LA CLASE COSTO
 	Comodidades comodidad = new Comodidades();
 	
 	// DE LA CLASE TIPOCOMODIDAD
  	TipoComodidad tipoComodidad = new TipoComodidad();
  	List<TipoComodidad> listaTipoComodidad = new ArrayList<TipoComodidad>();
  	private static ResponseEntity<List<TipoComodidad>> listRespTipoComodidad;
  	ObservableList<TipoComodidad> obsListTipoComodidad = FXCollections.observableArrayList();
  	
     
  	public void initialize() {
  		loadTipoComodidad();
 		if (Context.getInstance().getComodidadContext() != null) {
 			comodidad = Context.getInstance().getComodidadContext();
 			txt_coordenadas.setText(comodidad.getCoordenadas());
 			txt_descripcion.setText(comodidad.getDescripcion());
 			
 			if (comodidad.getIdTipoComodidad() != null) {
				for (TipoComodidad tc : listaTipoComodidad) {
					if (tc.getId().equals(comodidad.getIdTipoComodidad())) {
			 			cmb_tipoComodidad.getSelectionModel().select(tc);
					}
				}
			} 
 		}
 	}	
  	
    @FXML
    void cancelar(ActionEvent event) {
    	try {
			Context.getInstance().setComodidadContext(null);
			Context.getInstance().getStageModalBase().close();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

    @FXML
    void guardar(ActionEvent event) {
    	try {
    		if (txt_descripcion.getText().isEmpty() || txt_descripcion.getText().isBlank()) {
    			Message.showWarningNotification("Debe agregar el nombre de contacto.!!");
    			return;
    		}
    		if (txt_coordenadas.getText().isEmpty() || txt_coordenadas.getText().isBlank()) {
    			Message.showWarningNotification("Debe agregar las coordenadas del recurso.!!");
    			return;
    		}
    		if (!txt_coordenadas.getText().matches("^[-+]?([1-8]?\\d(\\.\\d+)?|90(\\.0+)?),\\s*[-+]?(180(\\.0+)?|((1[0-7]\\d)|([1-9]?\\d))(\\.\\d+)?)$")) {
				Message.showErrorNotification("Ingrese una coordenada valida, por favor.!! \nEjmplo: -2.2222, -80.3333");
				return;
			}
    		Boolean a = false;
    		if(cmb_tipoComodidad.getValue() != null) a = true;    		
			if (a.equals(false)) {
				Message.showWarningNotification("Debe seleccionar un tipo de comodidad.!!");
				return;
			}

			comodidad.setDescripcion(txt_descripcion.getText());
			comodidad.setCoordenadas(txt_coordenadas.getText());
			comodidad.setIdTipoComodidad(cmb_tipoComodidad.getValue().getId());
//			comodidad.setIdRecurso(cmb_recursos.getValue().getId());
			
    		
    		if (comodidad.getId() != null) {
    			rest.postForObject(uriComodidad + "/saveOrUpdate", comodidad, Comodidades.class);
			}

    		Context.getInstance().setComodidadContext(comodidad);
    		Message.showSuccessNotification("Se agregó el contenido.!! ");
    		Context.getInstance().getStageModalBase().close();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

    @FXML
    void mostrarMapa(ActionEvent event) {
    	try {
    		if (!txt_coordenadas.getText().isBlank() || !txt_coordenadas.getText().isEmpty()) {
    			if (!txt_coordenadas.getText().matches("^[-+]?([1-8]?\\d(\\.\\d+)?|90(\\.0+)?),\\s*[-+]?(180(\\.0+)?|((1[0-7]\\d)|([1-9]?\\d))(\\.\\d+)?)$")) {
    				Message.showErrorNotification("Ingrese una coordenada valida, por favor.!! \nEjmplo: -2.2222, -80.3333");
    				return;
    			}else {
    				Context.getInstance().setCoordDeMapa(txt_coordenadas.getText());
    	    	}
			}
    		
    		General.showModalWithParentMAPA("/viewMaps/MapGluon.fxml");
    		if (Context.getInstance().getCoordDeMapa() != null) {
    			txt_coordenadas.setText(Context.getInstance().getCoordDeMapa());
    		}
    		Context.getInstance().setCoordDeMapa(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    private void loadTipoComodidad() {
    	listRespTipoComodidad = rest.exchange(uriTipoComodidad + "/getAll", HttpMethod.GET, null,
				new ParameterizedTypeReference<List<TipoComodidad>>() {
				});
		listaTipoComodidad = listRespTipoComodidad.getBody();
		if (!listaTipoComodidad.isEmpty()) {
			for (int i = 0; i < listaTipoComodidad.size(); i++) {
				obsListTipoComodidad.add(listaTipoComodidad.get(i));
			}			
			cmb_tipoComodidad.setItems(obsListTipoComodidad);
		}					
    	cmb_tipoComodidad.setCellFactory(param -> new ListCell<TipoComodidad>() {
    		protected void updateItem(TipoComodidad item, boolean empty) {
    			super.updateItem(item, empty);
    			setText(empty ? "" : item.getDescripcion());
    		};
    	});  
	}

}
