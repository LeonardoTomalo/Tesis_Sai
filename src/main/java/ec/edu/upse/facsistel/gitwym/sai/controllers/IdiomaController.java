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

import ec.edu.upse.facsistel.gitwym.sai.models.Idiomas;
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

public class IdiomaController {

    @FXML private JFXListView<Idiomas> lst_listaIdiomas;
    @FXML private JFXButton btn_Nuevo;
    @FXML private JFXButton btn_Eliminar;
    @FXML private JFXButton btn_Guardar;
    @FXML private JFXTextField txt_nombreIdioma;

    // CONSUMIR WEB SERVICES
 	RestTemplate rest = new RestTemplate();
 	String uriIdiomas = "http://localhost:8082/idiomas";
    
 	// DE LA CLASE IDIOMAS
 	Idiomas idioma = new Idiomas();
 	List<Idiomas> listaIdiomas = new ArrayList<Idiomas>();
 	private static ResponseEntity<List<Idiomas>> listRespIdiomas;
	ObservableList<Idiomas> obsListIdiomas = FXCollections.observableArrayList();
	

	public void initialize() {
		restoreToController();
		loadIdiomas();
	}	

	@FXML
    void addNuevoIdioma(ActionEvent event) {
		initialize();
		idioma = new Idiomas();
    }

    @FXML
    void eliminarIdioma(ActionEvent event) {
    	try {
			if (lst_listaIdiomas.getSelectionModel().getSelectedItems().isEmpty()) {
				Message.showWarningNotification("Seleccione el idioma a eliminar.!!");
				return;
			}
			Optional<ButtonType> result = Message.showQuestion("Desea continuar y eliminar los datos del Idioma: "
							+ lst_listaIdiomas.getSelectionModel().getSelectedItem().getDescripcion() + " ?.",
					Context.getInstance().getStage());
			if (result.get() == ButtonType.OK) {
				//Eliminar datos.
				Map<String, String> params = new HashMap<String, String>();
				params.put("c", idioma.getId());
				rest.delete(uriIdiomas + "/deletePhysical/{c}", params);
				Message.showSuccessNotification("Se eliminaron exitosamente los datos.!!");
				initialize();
			}
		} catch (Exception e) {
			e.printStackTrace();
			Message.showErrorNotification("Ha surgido un error al eliminar datos.!!");
		}
    }

    @FXML
    void guardarIdioma(ActionEvent event) {
    	try {
			if (txt_nombreIdioma.getText().isEmpty() || txt_nombreIdioma.getText().isBlank()) {
				Message.showWarningNotification("Debe ingresar un nombre de idioma.!!");
				return;
			}
			Optional<ButtonType> result = Message.showQuestion("Desea continuar y grabar los datos?.",
					Context.getInstance().getStage());
			if (result.get() == ButtonType.OK) {
				idioma.setDescripcion(txt_nombreIdioma.getText());
				idioma.setIsSelect(null);
				rest.postForObject(uriIdiomas + "/saveOrUpdate", idioma, Idiomas.class);
				Message.showSuccessNotification("Se guardaron exitosamente los datos.!! ");
				idioma = new Idiomas();
				initialize();
			}
		} catch (Exception e) {
			e.printStackTrace();
			Message.showErrorNotification("Ha surgido un error al guardar datos.!!");
		}
    }

	private void loadIdiomas() {
		obsListIdiomas.clear();
		listRespIdiomas = rest.exchange(uriIdiomas + "/getAll", HttpMethod.GET, null,
				new ParameterizedTypeReference<List<Idiomas>>() {
				});
		listaIdiomas = listRespIdiomas.getBody();
		lst_listaIdiomas.setPlaceholder(new Label("---  No se encontraron datos en la Base. ---"));
		if (!listaIdiomas.isEmpty()) {
			for (int i = 0; i < listaIdiomas.size(); i++) {
				obsListIdiomas.add(listaIdiomas.get(i));
			}			
			lst_listaIdiomas.setItems(obsListIdiomas);	
	    	lst_listaIdiomas.setCellFactory(param -> new ListCell<Idiomas>() {
	    		protected void updateItem(Idiomas item, boolean empty) {
	    			super.updateItem(item, empty);
	    			setText(empty ? "" : item.getDescripcion() );
	    		};
	    	});  	    	
		}
		//
		lst_listaIdiomas.getSelectionModel().selectedItemProperty()
				.addListener((ObservableValue<? extends Idiomas> ov, Idiomas old_val, Idiomas new_val) -> {
					if (lst_listaIdiomas.getSelectionModel().getSelectedItem() != null) {
						idioma = lst_listaIdiomas.getSelectionModel().getSelectedItem();
						txt_nombreIdioma.setText(idioma.getDescripcion());			
					}
				});
	}
	
    private void restoreToController() {
		txt_nombreIdioma.clear();		
	}
}
