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

import ec.edu.upse.facsistel.gitwym.sai.models.TipoMedia;
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

public class TipoMedioController {

    @FXML private JFXListView<TipoMedia> lst_listaTipoMedio;
    @FXML private JFXButton btn_Nuevo;
    @FXML private JFXButton btn_Eliminar;
    @FXML private JFXButton btn_Guardar;
    @FXML private JFXTextField txt_nombreTipoMedio;

    // CONSUMIR WEB SERVICES
 	RestTemplate rest = new RestTemplate();
	String urlBase = PropertyManager.getBaseUrl();
 	String uriTipoMedia = urlBase + "/tipoMedia";
    
 	// DE LA CLASE IDIOMAS
 	TipoMedia tipoMedia = new TipoMedia();
 	List<TipoMedia> listaTipoMedia = new ArrayList<TipoMedia>();
 	private static ResponseEntity<List<TipoMedia>> listRespTipoMedia;
	ObservableList<TipoMedia> obsListTipoMedia = FXCollections.observableArrayList();
	

	public void initialize() {
		restoreToController();
		loadTipoComodidades();
	}	

    @FXML
    void addNuevoTipoMedio(ActionEvent event) {
    	initialize();
		tipoMedia = new TipoMedia();
    }

    @FXML
    void eliminarTipoMedio(ActionEvent event) {
    	try {
			if (lst_listaTipoMedio.getSelectionModel().getSelectedItems().isEmpty()) {
				Message.showWarningNotification("Seleccione la tipo de medio a eliminar.!!");
				return;
			}
			Optional<ButtonType> result = Message.showQuestion("Desea continuar y eliminar los datos de tipo de medio: "
							+ lst_listaTipoMedio.getSelectionModel().getSelectedItem().getDescripcion() + " ?.",
					Context.getInstance().getStage());
			if (result.get() == ButtonType.OK) {
				//Eliminar datos.
				Map<String, String> params = new HashMap<String, String>();
				params.put("c", tipoMedia.getId());
				rest.delete(uriTipoMedia + "/deletePhysical/{c}", params);
				Message.showSuccessNotification("Se eliminaron exitosamente los datos.!!");
				initialize();
			}
		} catch (Exception e) {
			e.printStackTrace();
			Message.showErrorNotification("Ha surgido un error al eliminar datos.!!");
		}
    }

    @FXML
    void guardarTipoMedio(ActionEvent event) {
    	try {
			if (txt_nombreTipoMedio.getText().isEmpty() || txt_nombreTipoMedio.getText().isBlank()) {
				Message.showWarningNotification("Debe ingresar un nombre de tipo de medio.!!");
				return;
			}
			Optional<ButtonType> result = Message.showQuestion("Desea continuar y grabar los datos?.",
					Context.getInstance().getStage());
			if (result.get() == ButtonType.OK) {
				tipoMedia.setDescripcion(txt_nombreTipoMedio.getText());
				rest.postForObject(uriTipoMedia + "/saveOrUpdate", tipoMedia, TipoMedia.class);
				Message.showSuccessNotification("Se guardaron exitosamente los datos.!! ");
				tipoMedia = new TipoMedia();
				initialize();
			}
		} catch (Exception e) {
			e.printStackTrace();
			Message.showErrorNotification("Ha surgido un error al guardar datos.!!");
		}
    }

	private void loadTipoComodidades() {
		obsListTipoMedia.clear();
		listRespTipoMedia = rest.exchange(uriTipoMedia + "/getAll", HttpMethod.GET, null,
				new ParameterizedTypeReference<List<TipoMedia>>() {
				});
		listaTipoMedia = listRespTipoMedia.getBody();
		lst_listaTipoMedio.setPlaceholder(new Label("---  No se encontraron datos en la Base. ---"));
		if (!listaTipoMedia.isEmpty()) {
			for (int i = 0; i < listaTipoMedia.size(); i++) {
				obsListTipoMedia.add(listaTipoMedia.get(i));
			}			
			lst_listaTipoMedio.setItems(obsListTipoMedia);	
	    	lst_listaTipoMedio.setCellFactory(param -> new ListCell<TipoMedia>() {
	    		protected void updateItem(TipoMedia item, boolean empty) {
	    			super.updateItem(item, empty);
	    			setText(empty ? "" : item.getDescripcion() );
	    		};
	    	});  	    	
		}
		//
		lst_listaTipoMedio.getSelectionModel().selectedItemProperty()
				.addListener((ObservableValue<? extends TipoMedia> ov, TipoMedia old_val, TipoMedia new_val) -> {
					if (lst_listaTipoMedio.getSelectionModel().getSelectedItem() != null) {
						tipoMedia = lst_listaTipoMedio.getSelectionModel().getSelectedItem();
						txt_nombreTipoMedio.setText(tipoMedia.getDescripcion());			
					}
				});
	}
	
    private void restoreToController() {
		txt_nombreTipoMedio.clear();		
	}
}
