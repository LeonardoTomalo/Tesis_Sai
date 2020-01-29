package ec.edu.upse.facsistel.gitwym.sai.controllers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;

import ec.edu.upse.facsistel.gitwym.sai.models.Menu;
import ec.edu.upse.facsistel.gitwym.sai.utilities.Context;
import ec.edu.upse.facsistel.gitwym.sai.utilities.General;
import ec.edu.upse.facsistel.gitwym.sai.utilities.Message;
import ec.edu.upse.facsistel.gitwym.sai.utilities.PropertyManager;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;

public class BaseMenuController{

	@FXML private JFXListView<Menu> lstv_Menu;
    @FXML private JFXButton btn_actualizar;
	
	// CONSUMIR WEB SERVICES
		RestTemplate rest = new RestTemplate();
		String uriMenu = PropertyManager.getBaseUrl() + "/menu";
		
	// DE LA CLASE MENU
		Menu menu = new Menu();
		List<Menu> listaMenu = new ArrayList<Menu>();
		private static ResponseEntity<List<Menu>> listRespMenu;
		ObservableList<Menu> obsListMenu = FXCollections.observableArrayList();
	
	public void initialize() {
		try {
			loadMenus();			
		} catch (Exception e) {
			e.printStackTrace();
			Message.showWarningNotification("NO SE HA PODIDO OBTENER LOS MENUS, CONTACTESE CON EL ADMINISTRADOR");
		}
	}

    @FXML
    void actualizarListaMenus(ActionEvent event) {
    	loadMenus();
    }
    
	public void loadMenus() {
		obsListMenu.clear();
		listRespMenu = rest.exchange(uriMenu + "/getAll", HttpMethod.GET, null,
				new ParameterizedTypeReference<List<Menu>>() {
				});
		listaMenu = listRespMenu.getBody();
		Collections.sort(listaMenu);
		lstv_Menu.setPlaceholder(new Label("--- No se encontraron datos en la Base. ---"));
		if (!listaMenu.isEmpty()) {
			for (int i = 0; i < listaMenu.size(); i++) {
				obsListMenu.add(listaMenu.get(i));
			}			
			lstv_Menu.setItems(obsListMenu);	
	    	lstv_Menu.setCellFactory(param -> new ListCell<Menu>() {
	    		protected void updateItem(Menu item, boolean empty) {
	    			super.updateItem(item, empty);
	    			setText(empty ? "" : item.getNombre());
	    		};
	    	});   
		}		
    	//
    	lstv_Menu.getSelectionModel().selectedItemProperty()
		.addListener((ObservableValue<? extends Menu> ov, Menu old_val, Menu new_val) -> {			
			if (lstv_Menu.getSelectionModel().getSelectedItem() != null) {
				menu = lstv_Menu.getSelectionModel().getSelectedItem();
				General.setContentParent(menu.getUrl(), Context.getInstance().getAnch_Contenido());
			}			
		});
	}
}
