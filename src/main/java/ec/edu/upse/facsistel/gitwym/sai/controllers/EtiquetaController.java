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

import ec.edu.upse.facsistel.gitwym.sai.models.Etiquetas;
import ec.edu.upse.facsistel.gitwym.sai.utilities.Context;
import ec.edu.upse.facsistel.gitwym.sai.utilities.Message;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;

public class EtiquetaController {

    @FXML private JFXListView<Etiquetas> lst_listaEtiquetas;
    @FXML private JFXButton btn_Nuevo;
    @FXML private JFXButton btn_Eliminar;
    @FXML private JFXButton btn_Guardar;
    @FXML private JFXTextField txt_nombreEtiqueta;

    // CONSUMIR WEB SERVICES
 	RestTemplate rest = new RestTemplate();
 	String uriEtiquetas = "http://localhost:8082/etiquetas";
    
 	// DE LA CLASE IDIOMAS
 	Etiquetas etiquetas = new Etiquetas();
 	List<Etiquetas> listaEtiquetas = new ArrayList<Etiquetas>();
 	private static ResponseEntity<List<Etiquetas>> listRespEtiquetas;
	ObservableList<Etiquetas> obsListEtiquetas = FXCollections.observableArrayList();
	

	public void initialize() {
		restoreToController();
		loadEtiquetas();
	}	
	
    @FXML
    void addNuevoEtiquetas(ActionEvent event) {
    	initialize();
		etiquetas = new Etiquetas();
    }

    @FXML
    void eliminarEtiquetas(ActionEvent event) {
    	try {
			if (lst_listaEtiquetas.getSelectionModel().getSelectedItems().isEmpty()) {
				Message.showWarningNotification("Seleccione la etiqueta a eliminar.!!");
				return;
			}
			Optional<ButtonType> result = Message.showQuestion("Desea continuar y eliminar los datos de etiqueta: "
							+ lst_listaEtiquetas.getSelectionModel().getSelectedItem().getDescripcion() + " ?.",
					Context.getInstance().getStage());
			if (result.get() == ButtonType.OK) {
				//Eliminar datos.
				Map<String, String> params = new HashMap<String, String>();
				params.put("c", etiquetas.getId());
				rest.delete(uriEtiquetas + "/deletePhysical/{c}", params);
				Message.showSuccessNotification("Se eliminaron exitosamente los datos.!!");
				initialize();
			}
		} catch (Exception e) {
			e.printStackTrace();
			Message.showErrorNotification("Ha surgido un error al eliminar datos.!!");
		}
    }

    @FXML
    void guardarEtiqueta(ActionEvent event) {
    	try {
			if (txt_nombreEtiqueta.getText().isEmpty() || txt_nombreEtiqueta.getText().isBlank()) {
				Message.showWarningNotification("Debe ingresar un nombre de etiqueta.!!");
				return;
			}
			Optional<ButtonType> result = Message.showQuestion("Desea continuar y grabar los datos?.",
					Context.getInstance().getStage());
			if (result.get() == ButtonType.OK) {
				etiquetas.setDescripcion(txt_nombreEtiqueta.getText());
				rest.postForObject(uriEtiquetas + "/saveOrUpdate", etiquetas, Etiquetas.class);
				Message.showSuccessNotification("Se guardaron exitosamente los datos.!! ");
				etiquetas = new Etiquetas();
				initialize();
			}
		} catch (Exception e) {
			e.printStackTrace();
			Message.showErrorNotification("Ha surgido un error al guardar datos.!!");
		}
    }

	private void loadEtiquetas() {
		obsListEtiquetas.clear();
		listRespEtiquetas = rest.exchange(uriEtiquetas + "/getAll", HttpMethod.GET, null,
				new ParameterizedTypeReference<List<Etiquetas>>() {
				});
		listaEtiquetas = listRespEtiquetas.getBody();
		lst_listaEtiquetas.setPlaceholder(new Label("---  No se encontraron datos en la Base. ---"));
		if (!listaEtiquetas.isEmpty()) {
			for (int i = 0; i < listaEtiquetas.size(); i++) {
				obsListEtiquetas.add(listaEtiquetas.get(i));
			}			
			lst_listaEtiquetas.setItems(obsListEtiquetas);	
	    	lst_listaEtiquetas.setCellFactory(param -> new ListCell<Etiquetas>() {
	    		protected void updateItem(Etiquetas item, boolean empty) {
	    			super.updateItem(item, empty);
	    			setText(empty ? "" : item.getDescripcion() );
	    		};
	    	});  	    	
		}
		//
		lst_listaEtiquetas.getSelectionModel().selectedItemProperty()
				.addListener((ObservableValue<? extends Etiquetas> ov, Etiquetas old_val, Etiquetas new_val) -> {
					if (lst_listaEtiquetas.getSelectionModel().getSelectedItem() != null) {
						etiquetas = lst_listaEtiquetas.getSelectionModel().getSelectedItem();
						txt_nombreEtiqueta.setText(etiquetas.getDescripcion());			
					}
				});
	}
	
    private void restoreToController() {
		txt_nombreEtiqueta.clear();		
	}
}
