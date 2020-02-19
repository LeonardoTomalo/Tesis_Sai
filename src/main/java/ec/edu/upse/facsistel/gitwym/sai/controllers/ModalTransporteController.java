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

import ec.edu.upse.facsistel.gitwym.sai.models.TipoTransporte;
import ec.edu.upse.facsistel.gitwym.sai.models.Transporte;
import ec.edu.upse.facsistel.gitwym.sai.utilities.Context;
import ec.edu.upse.facsistel.gitwym.sai.utilities.Message;
import ec.edu.upse.facsistel.gitwym.sai.utilities.PropertyManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;

public class ModalTransporteController {

    @FXML private JFXComboBox<TipoTransporte> cmb_tipoTransporte;
    @FXML private JFXTextArea txt_descripcion;
    @FXML private JFXTextField txt_distancia;
    @FXML private JFXTextField txt_tiempo;
    @FXML private JFXButton btn_Guardar;
    @FXML private JFXButton btn_Cancelar;

	// CONSUMIR WEB SERVICES
  	RestTemplate rest = new RestTemplate();
 	String urlBase = PropertyManager.getBaseUrl();
  	String uriTransporte = urlBase + "/transporte";
  	String uriTipoTransporte = urlBase + "/tipoTransporte";
    
  	//DE LA CLASE COSTO
  	Transporte transporte = new Transporte();
	  	
	// DE LA CLASE TIPOMEDIA	
  	TipoTransporte tipoTransporte = new TipoTransporte();
  	List<TipoTransporte> listaTipoTransporte = new ArrayList<TipoTransporte>();
  	private static ResponseEntity<List<TipoTransporte>> listRespTipoTransporte;
  	ObservableList<TipoTransporte> obsListTipoTransporte = FXCollections.observableArrayList();
  	
    
 	public void initialize() {
 		loadTipoTransporte();
		if (Context.getInstance().getContactoContext() != null) {
			transporte = Context.getInstance().getTransporteContext();
			txt_descripcion.setText(transporte.getDescripcion());
			if(transporte.getDistanciaAproximada() != null)
				txt_distancia.setText(transporte.getDistanciaAproximada().toString());
			if(transporte.getTiempoRecorrido() != null)
				txt_tiempo.setText(transporte.getTiempoRecorrido().toString());
			
			transporte.getIdTipoTransporte();
			if (transporte.getIdTipoTransporte() != null) {
				for (TipoTransporte tipoTransporte : listaTipoTransporte) {
					if (tipoTransporte.getId().equals(transporte.getIdTipoTransporte())) {
						cmb_tipoTransporte.getSelectionModel().select(tipoTransporte);
					}
				}
			}
			
		}
	}	
    @FXML
    void cancelar(ActionEvent event) {
    	try {
			Context.getInstance().setTransporteContext(null);
			Context.getInstance().getStageModalBase().close();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

    @FXML
    void guardar(ActionEvent event) {
    	try {
    		if (txt_descripcion.getText().isEmpty() || txt_descripcion.getText().isBlank()) {
    			Message.showWarningNotification("Debe agregar la descripcion del transporte.!!");
    			return;
    		}
//    		
    		Boolean a = false;
    		if(cmb_tipoTransporte.getValue() != null) a = true;    		
			if (a.equals(false)) {
				Message.showWarningNotification("Debe seleccionar un tipo de transporte.!!");
				return;
			}
			
			transporte.setDescripcion(txt_descripcion.getText());
			transporte.setEstado(true);
			transporte.setIdTipoTransporte(cmb_tipoTransporte.getValue().getId());
			transporte.setDistanciaAproximada(Float.parseFloat(txt_distancia.getText()));
			transporte.setTiempoRecorrido(Float.parseFloat(txt_tiempo.getText()));
			
    		Context.getInstance().setTransporteContext(transporte);
    		Message.showSuccessNotification("Se agregó el contenido.!! ");
    		Context.getInstance().getStageModalBase().close();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

    private void loadTipoTransporte() {
    	listRespTipoTransporte = rest.exchange(uriTipoTransporte + "/getAll", HttpMethod.GET, null,
				new ParameterizedTypeReference<List<TipoTransporte>>() {
				});
		listaTipoTransporte = listRespTipoTransporte.getBody();
		if (!listaTipoTransporte.isEmpty()) {
			for (int i = 0; i < listaTipoTransporte.size(); i++) {
				obsListTipoTransporte.add(listaTipoTransporte.get(i));
			}			
			cmb_tipoTransporte.setItems(obsListTipoTransporte);	
	    	cmb_tipoTransporte.setCellFactory(param -> new ListCell<TipoTransporte>() {
	    		protected void updateItem(TipoTransporte item, boolean empty) {
	    			super.updateItem(item, empty);
	    			setText(empty ? "" : item.getDescripcion());
	    		};
	    	});
		}				
	}

}
