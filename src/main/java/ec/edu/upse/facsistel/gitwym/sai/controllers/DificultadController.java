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

import ec.edu.upse.facsistel.gitwym.sai.models.DificultadRecorrido;
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

public class DificultadController {

    @FXML private JFXListView<DificultadRecorrido> lst_lista;
    @FXML private JFXButton btn_Nuevo;
    @FXML private JFXButton btn_Eliminar;
    @FXML private JFXButton btn_Guardar;
    @FXML private JFXTextField txt_descripcion;

    // CONSUMIR WEB SERVICES
 	RestTemplate rest = new RestTemplate();
	String urlBase = PropertyManager.getBaseUrl();
 	String uriDificultad = urlBase + "/dificultadRecorrido";
    
 	// DE LA CLASE IDIOMAS
 	DificultadRecorrido dispCelular = new DificultadRecorrido();
 	List<DificultadRecorrido> listadispCelular = new ArrayList<DificultadRecorrido>();
 	private static ResponseEntity<List<DificultadRecorrido>> listRespdispCelular;
	ObservableList<DificultadRecorrido> obsListdispCelular = FXCollections.observableArrayList();
	
	public void initialize() {
		restoreToController();
		loaddispCelular();
	}	

    @FXML
    void addNuevo(ActionEvent event) {
		initialize();
		dispCelular = new DificultadRecorrido();
    }

    @FXML
    void eliminar(ActionEvent event) {
    	try {
			if (lst_lista.getSelectionModel().getSelectedItems().isEmpty()) {
				Message.showWarningNotification("Seleccione la dificultad a eliminar.!!");
				return;
			}
			Optional<ButtonType> result = Message.showQuestion("Desea continuar y eliminar los datos de dificultad: "
							+ lst_lista.getSelectionModel().getSelectedItem().getDescripcion() + " ?.",
					Context.getInstance().getStage());
			if (result.get() == ButtonType.OK) {
				//Eliminar datos.
				Map<String, String> params = new HashMap<String, String>();
				params.put("c", dispCelular.getId());
				rest.delete(uriDificultad + "/deletePhysical/{c}", params);
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
				Message.showWarningNotification("Debe ingresar un nombre de dificultad.!!");
				return;
			}
			Optional<ButtonType> result = Message.showQuestion("Desea continuar y grabar los datos?.",
					Context.getInstance().getStage());
			if (result.get() == ButtonType.OK) {
				dispCelular.setDescripcion(txt_descripcion.getText());
				rest.postForObject(uriDificultad + "/saveOrUpdate", dispCelular, DificultadRecorrido.class);
				Message.showSuccessNotification("Se guardaron exitosamente los datos.!! ");
				dispCelular = new DificultadRecorrido();
				initialize();
			}
		} catch (Exception e) {
			e.printStackTrace();
			Message.showErrorNotification("Ha surgido un error al guardar datos.!!");
		}
    }
    

	private void loaddispCelular() {
		obsListdispCelular.clear();
		listRespdispCelular = rest.exchange(uriDificultad + "/getAll", HttpMethod.GET, null,
				new ParameterizedTypeReference<List<DificultadRecorrido>>() {
				});
		listadispCelular = listRespdispCelular.getBody();
		lst_lista.setPlaceholder(new Label("---  No se encontraron datos en la Base. ---"));
		if (!listadispCelular.isEmpty()) {
			for (int i = 0; i < listadispCelular.size(); i++) {
				obsListdispCelular.add(listadispCelular.get(i));
			}			
			lst_lista.setItems(obsListdispCelular);	
	    	lst_lista.setCellFactory(param -> new ListCell<DificultadRecorrido>() {
	    		protected void updateItem(DificultadRecorrido item, boolean empty) {
	    			super.updateItem(item, empty);
	    			setText(empty ? "" : item.getDescripcion() );
	    		};
	    	});  	    	
		}
		//
		lst_lista.getSelectionModel().selectedItemProperty()
				.addListener((ObservableValue<? extends DificultadRecorrido> ov, DificultadRecorrido old_val, DificultadRecorrido new_val) -> {
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
