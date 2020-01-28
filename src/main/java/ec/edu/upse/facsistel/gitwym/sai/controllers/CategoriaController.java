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

import ec.edu.upse.facsistel.gitwym.sai.models.Categoria;
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

public class CategoriaController{

    @FXML private JFXListView<Categoria> lst_listaCategorias;
    @FXML private JFXButton btn_Nuevo;
    @FXML private JFXButton btn_Eliminar;
    @FXML private JFXButton btn_Guardar;
    @FXML private JFXTextField txt_nombreCategoria;

    // CONSUMIR WEB SERVICES
 	RestTemplate rest = new RestTemplate();
	String urlBase = PropertyManager.getBaseUrl();
 	String uriCategoria = urlBase + "/categoria";
    
 	// DE LA CLASE IDIOMAS
 	Categoria categoria = new Categoria();
 	List<Categoria> listaCategoria = new ArrayList<Categoria>();
 	private static ResponseEntity<List<Categoria>> listRespCategoria;
	ObservableList<Categoria> obsListCategoria = FXCollections.observableArrayList();
	

	public void initialize() {
		restoreToController();
		loadCategoria();
	}	
	
    @FXML
    void addNuevoCategoria(ActionEvent event) {
    	initialize();
		categoria = new Categoria();
    }

    @FXML
    void eliminarCategoria(ActionEvent event) {
    	try {
			if (lst_listaCategorias.getSelectionModel().getSelectedItems().isEmpty()) {
				Message.showWarningNotification("Seleccione la categoria a eliminar.!!");
				return;
			}
			Optional<ButtonType> result = Message.showQuestion("Desea continuar y eliminar los datos de tipo de categoria: "
							+ lst_listaCategorias.getSelectionModel().getSelectedItem().getDescripcion() + " ?.",
					Context.getInstance().getStage());
			if (result.get() == ButtonType.OK) {
				//Eliminar datos.
				Map<String, String> params = new HashMap<String, String>();
				params.put("c", categoria.getId());
				rest.delete(uriCategoria + "/deletePhysical/{c}", params);
				Message.showSuccessNotification("Se eliminaron exitosamente los datos.!!");
				initialize();
			}
		} catch (Exception e) {
			e.printStackTrace();
			Message.showErrorNotification("Ha surgido un error al eliminar datos.!!");
		}
    }

    @FXML
    void guardarCategoria(ActionEvent event) {
    	try {
			if (txt_nombreCategoria.getText().isEmpty() || txt_nombreCategoria.getText().isBlank()) {
				Message.showWarningNotification("Debe ingresar un nombre de categoria.!!");
				return;
			}
			Optional<ButtonType> result = Message.showQuestion("Desea continuar y grabar los datos?.",
					Context.getInstance().getStage());
			if (result.get() == ButtonType.OK) {
				categoria.setDescripcion(txt_nombreCategoria.getText());
				rest.postForObject(uriCategoria + "/saveOrUpdate", categoria, Categoria.class);
				Message.showSuccessNotification("Se guardaron exitosamente los datos.!! ");
				categoria = new Categoria();
				initialize();
			}
		} catch (Exception e) {
			e.printStackTrace();
			Message.showErrorNotification("Ha surgido un error al guardar datos.!!");
		}
    }

	private void loadCategoria() {
		obsListCategoria.clear();
		listRespCategoria = rest.exchange(uriCategoria + "/getAll", HttpMethod.GET, null,
				new ParameterizedTypeReference<List<Categoria>>() {
				});
		listaCategoria = listRespCategoria.getBody();
		lst_listaCategorias.setPlaceholder(new Label("---  No se encontraron datos en la Base. ---"));
		if (!listaCategoria.isEmpty()) {
			for (int i = 0; i < listaCategoria.size(); i++) {
				obsListCategoria.add(listaCategoria.get(i));
			}			
			lst_listaCategorias.setItems(obsListCategoria);	
	    	lst_listaCategorias.setCellFactory(param -> new ListCell<Categoria>() {
	    		protected void updateItem(Categoria item, boolean empty) {
	    			super.updateItem(item, empty);
	    			setText(empty ? "" : item.getDescripcion() );
	    		};
	    	});  	    	
		}
		//
		lst_listaCategorias.getSelectionModel().selectedItemProperty()
				.addListener((ObservableValue<? extends Categoria> ov, Categoria old_val, Categoria new_val) -> {
					if (lst_listaCategorias.getSelectionModel().getSelectedItem() != null) {
						categoria = lst_listaCategorias.getSelectionModel().getSelectedItem();
						txt_nombreCategoria.setText(categoria.getDescripcion());			
					}
				});
	}
	
    private void restoreToController() {
		txt_nombreCategoria.clear();		
	}
}
