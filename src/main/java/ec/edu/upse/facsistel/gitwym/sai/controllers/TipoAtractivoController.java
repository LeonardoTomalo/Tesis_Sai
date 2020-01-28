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

import ec.edu.upse.facsistel.gitwym.sai.models.TipoAtractivo;
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

public class TipoAtractivoController {

    @FXML private JFXListView<TipoAtractivo> lst_listaTipoAtractivos;
    @FXML private JFXButton btn_Nuevo;
    @FXML private JFXButton btn_Eliminar;
    @FXML private JFXButton btn_Guardar;
    @FXML private JFXTextField txt_nombreTipoAtractivo;

    // CONSUMIR WEB SERVICES
 	RestTemplate rest = new RestTemplate();
	String urlBase = PropertyManager.getBaseUrl();
 	String uriTipoAtractivo = urlBase + "/tipoAtractivo";
    
 	// DE LA CLASE IDIOMAS
 	TipoAtractivo tipoAtractivo = new TipoAtractivo();
 	List<TipoAtractivo> listaTipoAtractivo = new ArrayList<TipoAtractivo>();
 	private static ResponseEntity<List<TipoAtractivo>> listRespTipoAtractivo;
	ObservableList<TipoAtractivo> obsListTipoAtractivo = FXCollections.observableArrayList();
	

	public void initialize() {
		restoreToController();
		loadTipoAtractivos();
	}	

    @FXML
    void addNuevoTipoAtractivos(ActionEvent event) {
    	initialize();
		tipoAtractivo = new TipoAtractivo();
    }

    @FXML
    void eliminarTipoAtractivos(ActionEvent event) {
    	try {
			if (lst_listaTipoAtractivos.getSelectionModel().getSelectedItems().isEmpty()) {
				Message.showWarningNotification("Seleccione la tipo de atractivo a eliminar.!!");
				return;
			}
			Optional<ButtonType> result = Message.showQuestion("Desea continuar y eliminar los datos de tipo de atractivo: "
							+ lst_listaTipoAtractivos.getSelectionModel().getSelectedItem().getDescripcion() + " ?.",
					Context.getInstance().getStage());
			if (result.get() == ButtonType.OK) {
				//Eliminar datos.
				Map<String, String> params = new HashMap<String, String>();
				params.put("c", tipoAtractivo.getId());
				rest.delete(uriTipoAtractivo + "/deletePhysical/{c}", params);
				Message.showSuccessNotification("Se eliminaron exitosamente los datos.!!");
				initialize();
			}
		} catch (Exception e) {
			e.printStackTrace();
			Message.showErrorNotification("Ha surgido un error al eliminar datos.!!");
		}
    }

    @FXML
    void guardarTipoAtractivos(ActionEvent event) {
    	try {
			if (txt_nombreTipoAtractivo.getText().isEmpty() || txt_nombreTipoAtractivo.getText().isBlank()) {
				Message.showWarningNotification("Debe ingresar un nombre de tipo de atractivo.!!");
				return;
			}
			Optional<ButtonType> result = Message.showQuestion("Desea continuar y grabar los datos?.",
					Context.getInstance().getStage());
			if (result.get() == ButtonType.OK) {
				tipoAtractivo.setDescripcion(txt_nombreTipoAtractivo.getText());
				rest.postForObject(uriTipoAtractivo + "/saveOrUpdate", tipoAtractivo, TipoAtractivo.class);
				Message.showSuccessNotification("Se guardaron exitosamente los datos.!! ");
				tipoAtractivo = new TipoAtractivo();
				initialize();
			}
		} catch (Exception e) {
			e.printStackTrace();
			Message.showErrorNotification("Ha surgido un error al guardar datos.!!");
		}
    }

	private void loadTipoAtractivos() {
		obsListTipoAtractivo.clear();
		listRespTipoAtractivo = rest.exchange(uriTipoAtractivo + "/getAll", HttpMethod.GET, null,
				new ParameterizedTypeReference<List<TipoAtractivo>>() {
				});
		listaTipoAtractivo = listRespTipoAtractivo.getBody();
		lst_listaTipoAtractivos.setPlaceholder(new Label("---  No se encontraron datos en la Base. ---"));
		if (!listaTipoAtractivo.isEmpty()) {
			for (int i = 0; i < listaTipoAtractivo.size(); i++) {
				obsListTipoAtractivo.add(listaTipoAtractivo.get(i));
			}			
			lst_listaTipoAtractivos.setItems(obsListTipoAtractivo);	
	    	lst_listaTipoAtractivos.setCellFactory(param -> new ListCell<TipoAtractivo>() {
	    		protected void updateItem(TipoAtractivo item, boolean empty) {
	    			super.updateItem(item, empty);
	    			setText(empty ? "" : item.getDescripcion() );
	    		};
	    	});  	    	
		}
		//
		lst_listaTipoAtractivos.getSelectionModel().selectedItemProperty()
				.addListener((ObservableValue<? extends TipoAtractivo> ov, TipoAtractivo old_val, TipoAtractivo new_val) -> {
					if (lst_listaTipoAtractivos.getSelectionModel().getSelectedItem() != null) {
						tipoAtractivo = lst_listaTipoAtractivos.getSelectionModel().getSelectedItem();
						txt_nombreTipoAtractivo.setText(tipoAtractivo.getDescripcion());			
					}
				});
	}
	
    private void restoreToController() {
		txt_nombreTipoAtractivo.clear();		
	}
}
