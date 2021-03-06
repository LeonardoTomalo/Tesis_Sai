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

import ec.edu.upse.facsistel.gitwym.sai.models.DisponibilidadCelular;
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

public class DisponibilidadController {

    @FXML private JFXListView<DisponibilidadCelular> lst_lista;
    @FXML private JFXButton btn_Nuevo;
    @FXML private JFXButton btn_Eliminar;
    @FXML private JFXButton btn_Guardar;
    @FXML private JFXTextField txt_descripcion;

    // CONSUMIR WEB SERVICES
 	RestTemplate rest = new RestTemplate();
	String urlBase = PropertyManager.getBaseUrl();
 	String uriDisponibilidad = urlBase + "/disponibilidadCelular";
    
 	// DE LA CLASE IDIOMAS
 	DisponibilidadCelular dispCelular = new DisponibilidadCelular();
 	List<DisponibilidadCelular> listadispCelular = new ArrayList<DisponibilidadCelular>();
 	private static ResponseEntity<List<DisponibilidadCelular>> listRespdispCelular;
	ObservableList<DisponibilidadCelular> obsListdispCelular = FXCollections.observableArrayList();
	
	public void initialize() {
		restoreToController();
		loaddispCelular();
	}	

    @FXML
    void addNuevo(ActionEvent event) {
		initialize();
		dispCelular = new DisponibilidadCelular();
    }

    @FXML
    void eliminar(ActionEvent event) {
    	try {
			if (lst_lista.getSelectionModel().getSelectedItems().isEmpty()) {
				Message.showWarningNotification("Seleccione la disponibilidad a eliminar.!!");
				return;
			}
			Optional<ButtonType> result = Message.showQuestion("Desea continuar y eliminar los datos de disponibilidad: "
							+ lst_lista.getSelectionModel().getSelectedItem().getDescripcion() + " ?.",
					Context.getInstance().getStage());
			if (result.get() == ButtonType.OK) {
				//Eliminar datos.
				Map<String, String> params = new HashMap<String, String>();
				params.put("c", dispCelular.getId());
				rest.delete(uriDisponibilidad + "/deletePhysical/{c}", params);
				Message.showSuccessNotification("Se eliminaron exitosamente los datos.!!");
				initialize();
			}
		} catch (Exception e) {
			e.printStackTrace();
			Message.showErrorNotification("Ha surgido un error al eliminar datos.!!");
		}
    }

    @FXML
    void guardar(ActionEvent event) {
    	try {
			if (txt_descripcion.getText().isEmpty() || txt_descripcion.getText().isBlank()) {
				Message.showWarningNotification("Debe ingresar un nombre de disponibilidad.!!");
				return;
			}
			Optional<ButtonType> result = Message.showQuestion("Desea continuar y grabar los datos?.",
					Context.getInstance().getStage());
			if (result.get() == ButtonType.OK) {
				dispCelular.setDescripcion(txt_descripcion.getText());
				rest.postForObject(uriDisponibilidad + "/saveOrUpdate", dispCelular, DisponibilidadCelular.class);
				Message.showSuccessNotification("Se guardaron exitosamente los datos.!! ");
				dispCelular = new DisponibilidadCelular();
				initialize();
			}
		} catch (Exception e) {
			e.printStackTrace();
			Message.showErrorNotification("Ha surgido un error al guardar datos.!!");
		}
    }
    

	private void loaddispCelular() {
		obsListdispCelular.clear();
		listRespdispCelular = rest.exchange(uriDisponibilidad + "/getAll", HttpMethod.GET, null,
				new ParameterizedTypeReference<List<DisponibilidadCelular>>() {
				});
		listadispCelular = listRespdispCelular.getBody();
		lst_lista.setPlaceholder(new Label("---  No se encontraron datos en la Base. ---"));
		if (!listadispCelular.isEmpty()) {
			for (int i = 0; i < listadispCelular.size(); i++) {
				obsListdispCelular.add(listadispCelular.get(i));
			}			
			lst_lista.setItems(obsListdispCelular);	
	    	lst_lista.setCellFactory(param -> new ListCell<DisponibilidadCelular>() {
	    		protected void updateItem(DisponibilidadCelular item, boolean empty) {
	    			super.updateItem(item, empty);
	    			setText(empty ? "" : item.getDescripcion() );
	    		};
	    	});  	    	
		}
		//
		lst_lista.getSelectionModel().selectedItemProperty()
				.addListener((ObservableValue<? extends DisponibilidadCelular> ov, DisponibilidadCelular old_val, DisponibilidadCelular new_val) -> {
					if (lst_lista.getSelectionModel().getSelectedItem() != null) {
						dispCelular = lst_lista.getSelectionModel().getSelectedItem();
						txt_descripcion.setText(dispCelular.getDescripcion());			
					}
				});
	}
	
    private void restoreToController() {
		txt_descripcion.clear();		
	}
}
