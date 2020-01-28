package ec.edu.upse.facsistel.gitwym.sai.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;

import ec.edu.upse.facsistel.gitwym.sai.models.TipoComodidad;
import ec.edu.upse.facsistel.gitwym.sai.utilities.Context;
import ec.edu.upse.facsistel.gitwym.sai.utilities.Message;
import ec.edu.upse.facsistel.gitwym.sai.utilities.PropertyManager;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;

public class TipoComodidadController {

    @FXML private JFXListView<TipoComodidad> lst_listaTipoComodidad;
    @FXML private JFXButton btn_Nuevo;
    @FXML private JFXButton btn_Eliminar;
    @FXML private JFXButton btn_Guardar;
    @FXML private JFXTextField txt_nombreTipoComodidad;

    // CONSUMIR WEB SERVICES
 	RestTemplate rest = new RestTemplate();
	String urlBase = PropertyManager.getBaseUrl();
 	String uriTipoComodidad = urlBase + "/tipoComodidad";
    
 	// DE LA CLASE IDIOMAS
 	TipoComodidad tipoComodidad = new TipoComodidad();
 	List<TipoComodidad> listaTipoComodidad = new ArrayList<TipoComodidad>();
 	private static ResponseEntity<List<TipoComodidad>> listRespTipoComodidad;
	ObservableList<TipoComodidad> obsListTipoComodidad = FXCollections.observableArrayList();
	

	public void initialize() {
		restoreToController();
		loadTipoComodidades();
	}	

    @FXML
    void addNuevoTipoComodidad(ActionEvent event) {
    	initialize();
		tipoComodidad = new TipoComodidad();
    }

    @FXML
    void eliminarTipoComodidad(ActionEvent event) {
    	try {
			if (lst_listaTipoComodidad.getSelectionModel().getSelectedItems().isEmpty()) {
				Message.showWarningNotification("Seleccione la tipo de comodidad a eliminar.!!");
				return;
			}
			Optional<ButtonType> result = Message.showQuestion("Desea continuar y eliminar los datos de tipo de comodidad: "
							+ lst_listaTipoComodidad.getSelectionModel().getSelectedItem().getDescripcion() + " ?.",
					Context.getInstance().getStage());
			if (result.get() == ButtonType.OK) {
				//Eliminar datos.
				Map<String, String> params = new HashMap<String, String>();
				params.put("c", tipoComodidad.getId());
				rest.delete(uriTipoComodidad + "/deletePhysical/{c}", params);
				Message.showSuccessNotification("Se eliminaron exitosamente los datos.!!");
				initialize();
			}
		} catch (Exception e) {
			e.printStackTrace();
			Message.showErrorNotification("Ha surgido un error al eliminar datos.!!");
		}
    }

    @FXML
    void guardarTipoComodidad(ActionEvent event) {
    	try {
			if (txt_nombreTipoComodidad.getText().isEmpty() || txt_nombreTipoComodidad.getText().isBlank()) {
				Message.showWarningNotification("Debe ingresar un nombre de tipo de comodidad.!!");
				return;
			}
			Optional<ButtonType> result = Message.showQuestion("Desea continuar y grabar los datos?.",
					Context.getInstance().getStage());
			if (result.get() == ButtonType.OK) {
				tipoComodidad.setDescripcion(txt_nombreTipoComodidad.getText());
				rest.postForObject(uriTipoComodidad + "/saveOrUpdate", tipoComodidad, TipoComodidad.class);
				Message.showSuccessNotification("Se guardaron exitosamente los datos.!! ");
				tipoComodidad = new TipoComodidad();
				initialize();
			}
		} catch (Exception e) {
			e.printStackTrace();
			Message.showErrorNotification("Ha surgido un error al guardar datos.!!");
		}
    }

	private void loadTipoComodidades() {
		obsListTipoComodidad.clear();
		listRespTipoComodidad = rest.exchange(uriTipoComodidad + "/getAll", HttpMethod.GET, null,
				new ParameterizedTypeReference<List<TipoComodidad>>() {
				});
		listaTipoComodidad = listRespTipoComodidad.getBody();
		lst_listaTipoComodidad.setPlaceholder(new Label("---  No se encontraron datos en la Base. ---"));
		if (!listaTipoComodidad.isEmpty()) {
			for (int i = 0; i < listaTipoComodidad.size(); i++) {
				obsListTipoComodidad.add(listaTipoComodidad.get(i));
			}			
			lst_listaTipoComodidad.setItems(obsListTipoComodidad);	
	    	lst_listaTipoComodidad.setCellFactory(param -> new ListCell<TipoComodidad>() {
	    		protected void updateItem(TipoComodidad item, boolean empty) {
	    			super.updateItem(item, empty);
	    			setText(empty ? "" : item.getDescripcion() );
	    		};
	    	});  	    	
		}
		//
		lst_listaTipoComodidad.getSelectionModel().selectedItemProperty()
				.addListener((ObservableValue<? extends TipoComodidad> ov, TipoComodidad old_val, TipoComodidad new_val) -> {
					if (lst_listaTipoComodidad.getSelectionModel().getSelectedItem() != null) {
						tipoComodidad = lst_listaTipoComodidad.getSelectionModel().getSelectedItem();
						txt_nombreTipoComodidad.setText(tipoComodidad.getDescripcion());			
					}
				});
	}
	
    private void restoreToController() {
		txt_nombreTipoComodidad.clear();		
	}
}
