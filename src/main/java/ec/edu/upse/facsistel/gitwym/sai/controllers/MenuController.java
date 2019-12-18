package ec.edu.upse.facsistel.gitwym.sai.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;

import ec.edu.upse.facsistel.gitwym.sai.models.Menu;
import ec.edu.upse.facsistel.gitwym.sai.models.Rol;
import ec.edu.upse.facsistel.gitwym.sai.utilities.ConsumeWS;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;

public class MenuController {

    @FXML private JFXListView<Menu> lst_listaMenus;
    @FXML private JFXButton btn_Nuevo;
    @FXML private JFXButton btn_Eliminar;
    @FXML private JFXButton btn_Guardar;
    @FXML private JFXTextField txt_nombreMenu;
    @FXML private JFXTextField txt_rutaFXML;
    @FXML private JFXTextField txt_rutaLogo;
    @FXML private JFXTextField txt_ordenMenu;
    @FXML private JFXRadioButton rbtn_menuSecundario;
    @FXML private JFXTextField txt_nombreMenuPadre;
    @FXML private JFXListView<Rol> lst_listaRoles;
    
    public void initialize(){    	
    	ObservableList<Menu> m = FXCollections.observableList(ConsumeWS.getAll(Menu.class));
    	lst_listaMenus.getItems().clear();
    	lst_listaMenus.setItems(m);
    	lst_listaMenus.setCellFactory(param -> new ListCell<Menu>() {
    		protected void updateItem(Menu item, boolean empty) {
    			super.updateItem(item, empty);
    			if (empty || item.getNombre() == null) {
    		        setText("");
    		    } else {
    		        setText(item.getNombre());
    		    }
    		};
    	});
    }

    @FXML
    void addNuevoMenu(ActionEvent event) {

    }

    @FXML
    void eliminarMenu(ActionEvent event) {

    }

    @FXML
    void guardarMenu(ActionEvent event) {

    }

    @FXML
    void selectedMenuSecundario(ActionEvent event) {

    }
}
