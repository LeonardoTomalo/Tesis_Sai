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

import ec.edu.upse.facsistel.gitwym.sai.models.Accesibilidad;
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

public class AccesibilidadController {

    @FXML private JFXListView<Accesibilidad> lst_listaAccesibilidad;
    @FXML private JFXButton btn_Nuevo;
    @FXML private JFXButton btn_Eliminar;
    @FXML private JFXButton btn_Guardar;
    @FXML private JFXTextField txt_nombreAccesibilidad;

    // CONSUMIR WEB SERVICES
 	RestTemplate rest = new RestTemplate();
	String urlBase = PropertyManager.getBaseUrl();
 	String uriAccesibilidad = urlBase + "/accesibilidad";
    
 	// DE LA CLASE IDIOMAS
 	Accesibilidad accesibilidad = new Accesibilidad();
 	List<Accesibilidad> listaAccesibilidad = new ArrayList<Accesibilidad>();
 	private static ResponseEntity<List<Accesibilidad>> listRespAccesibilidad;
	ObservableList<Accesibilidad> obsListAccesibilidad = FXCollections.observableArrayList();
	

	public void initialize() {
		restoreToController();
		loadAccesibilidad();
	}	

    @FXML
    void addNuevoAccesibilidad(ActionEvent event) {
		initialize();
		accesibilidad = new Accesibilidad();
    }

    @FXML
    void eliminarAccesibilidad(ActionEvent event) {
    	try {
			if (lst_listaAccesibilidad.getSelectionModel().getSelectedItems().isEmpty()) {
				Message.showWarningNotification("Seleccione la accesibilidad a eliminar.!!");
				return;
			}
			Optional<ButtonType> result = Message.showQuestion("Desea continuar y eliminar los datos de Accesibilidad: "
							+ lst_listaAccesibilidad.getSelectionModel().getSelectedItem().getDescripcion() + " ?.",
					Context.getInstance().getStage());
			if (result.get() == ButtonType.OK) {
				//Eliminar datos.
				Map<String, String> params = new HashMap<String, String>();
				params.put("c", accesibilidad.getId());
				rest.delete(uriAccesibilidad + "/deletePhysical/{c}", params);
				Message.showSuccessNotification("Se eliminaron exitosamente los datos.!!");
				initialize();
			}
		} catch (Exception e) {
			e.printStackTrace();
			Message.showErrorNotification("Ha surgido un error al eliminar datos.!!");
		}
    }

    @FXML
    void guardarAccesibilidad(ActionEvent event) {
    	try {
			if (txt_nombreAccesibilidad.getText().isEmpty() || txt_nombreAccesibilidad.getText().isBlank()) {
				Message.showWarningNotification("Debe ingresar un nombre de accesibilidad.!!");
				return;
			}
			Optional<ButtonType> result = Message.showQuestion("Desea continuar y grabar los datos?.",
					Context.getInstance().getStage());
			if (result.get() == ButtonType.OK) {
				accesibilidad.setDescripcion(txt_nombreAccesibilidad.getText());
				accesibilidad.setIsSelect(null);
				rest.postForObject(uriAccesibilidad + "/saveOrUpdate", accesibilidad, Accesibilidad.class);
				Message.showSuccessNotification("Se guardaron exitosamente los datos.!! ");
				accesibilidad = new Accesibilidad();
				initialize();
			}
		} catch (Exception e) {
			e.printStackTrace();
			Message.showErrorNotification("Ha surgido un error al guardar datos.!!");
		}
    }
    

	private void loadAccesibilidad() {
		obsListAccesibilidad.clear();
		listRespAccesibilidad = rest.exchange(uriAccesibilidad + "/getAll", HttpMethod.GET, null,
				new ParameterizedTypeReference<List<Accesibilidad>>() {
				});
		listaAccesibilidad = listRespAccesibilidad.getBody();
		lst_listaAccesibilidad.setPlaceholder(new Label("---  No se encontraron datos en la Base. ---"));
		if (!listaAccesibilidad.isEmpty()) {
			for (int i = 0; i < listaAccesibilidad.size(); i++) {
				obsListAccesibilidad.add(listaAccesibilidad.get(i));
			}			
			lst_listaAccesibilidad.setItems(obsListAccesibilidad);	
	    	lst_listaAccesibilidad.setCellFactory(param -> new ListCell<Accesibilidad>() {
	    		protected void updateItem(Accesibilidad item, boolean empty) {
	    			super.updateItem(item, empty);
	    			setText(empty ? "" : item.getDescripcion() );
	    		};
	    	});  	    	
		}
		//
		lst_listaAccesibilidad.getSelectionModel().selectedItemProperty()
				.addListener((ObservableValue<? extends Accesibilidad> ov, Accesibilidad old_val, Accesibilidad new_val) -> {
					if (lst_listaAccesibilidad.getSelectionModel().getSelectedItem() != null) {
						accesibilidad = lst_listaAccesibilidad.getSelectionModel().getSelectedItem();
						txt_nombreAccesibilidad.setText(accesibilidad.getDescripcion());			
					}
				});
	}
	
    private void restoreToController() {
		txt_nombreAccesibilidad.clear();		
	}
}
