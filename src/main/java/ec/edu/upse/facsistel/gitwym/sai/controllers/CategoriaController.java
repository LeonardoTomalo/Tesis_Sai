package ec.edu.upse.facsistel.gitwym.sai.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;

import ec.edu.upse.facsistel.gitwym.sai.models.Categoria;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;

public class CategoriaController{

    @FXML private JFXButton btn_agregarCategoria;
    @FXML private JFXButton btn_eliminarCategoria;
    @FXML private JFXListView<Categoria> lst_listaCategorias;

    //Para consumir web services.
    private static ResponseEntity<List<Categoria>> listResponse;
	RestTemplate rest = new RestTemplate();
	String uri = "http://localhost:8082/categoria";

	//DE LA CLASE 
    Categoria categoria = new Categoria();
	List<Categoria> lista = new ArrayList<Categoria>();
	ObservableList<Categoria> data = FXCollections.observableArrayList();
			
    
    public void initialize() {
    	getCategorias();
//    	for (int i = 0; i < getCategorias().size(); i++) {
//			Label lbl = new Label("OPCION " + i);
//			lbl.setPadding(new Insets(5, 0, 5, 20));
//			lst_listaCategorias.getItems().add(lbl);
//		}
    }

    @FXML
    void agregarCategoria(ActionEvent event) {
    	Categoria c = new Categoria();
    	lst_listaCategorias.getItems().add(c);
    }

    @FXML
    void eliminarCategoria(ActionEvent event) {

    }
    
    public void getCategorias(){
    	data.clear();
    	listResponse = rest.exchange(uri + "/getAll", HttpMethod.GET, null, new ParameterizedTypeReference<List<Categoria>>() {	});
    	lista= listResponse.getBody();
		for(int i = 0 ; i < lista.size() ; i++ ){
			data.add(lista.get(i));
		}
		lst_listaCategorias.setItems(data);	
    	lst_listaCategorias.setCellFactory(param -> new ListCell<Categoria>() {
    		protected void updateItem(Categoria item, boolean empty) {
    			super.updateItem(item, empty);
    			if (empty || item.getDescripcion() == null) {
    		        setText("");
    		    } else {
    		        setText(item.getDescripcion());
    		    }
    		};
    	});
    }
}
