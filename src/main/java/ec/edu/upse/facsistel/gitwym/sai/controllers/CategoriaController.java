package ec.edu.upse.facsistel.gitwym.sai.controllers;

import java.util.List;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;

import ec.edu.upse.facsistel.gitwym.sai.models.Categoria;
import ec.edu.upse.facsistel.gitwym.sai.utilities.ConsumeWS;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class CategoriaController extends ConsumeWS<Categoria>{

    @FXML private JFXButton btn_agregarCategoria;
    @FXML private JFXButton btn_eliminarCategoria;
    @FXML private JFXListView<Categoria> lst_listaCategorias;
    
    public void initialize() {
    	lst_listaCategorias.getItems().clear();
    	lst_listaCategorias.getItems().addAll(getCategorias());
//    	getCategorias();
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
    
    public List<Categoria> getCategorias(){
    	return getAll(Categoria.class);
    }
}
