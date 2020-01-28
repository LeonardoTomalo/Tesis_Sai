package ec.edu.upse.facsistel.gitwym.sai.controllers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.controlsfx.control.CheckListView;
import org.controlsfx.control.textfield.TextFields;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;

import ec.edu.upse.facsistel.gitwym.sai.models.Menu;
import ec.edu.upse.facsistel.gitwym.sai.models.Rol;
import ec.edu.upse.facsistel.gitwym.sai.utilities.Context;
import ec.edu.upse.facsistel.gitwym.sai.utilities.General;
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
import javafx.scene.control.Spinner;
import javafx.scene.control.cell.CheckBoxListCell;

public class MenuController {

	@FXML private JFXListView<Menu> lst_listaMenus;
	@FXML private JFXButton btn_Nuevo;
	@FXML private JFXButton btn_Eliminar;
	@FXML private JFXButton btn_Guardar;
	@FXML private JFXTextField txt_nombreMenu;
	@FXML private JFXTextField txt_rutaFXML;
	@FXML private JFXTextField txt_rutaLogo;
	@FXML private JFXRadioButton rbtn_menuSecundario;
	@FXML private JFXTextField txt_nombreMenuPadre;
	@FXML private JFXListView<Rol> lst_listaRoles;
	@FXML private Spinner<Integer> spin_orden;
    @FXML private CheckListView<Rol> chklst_listaRoles;
    @FXML private JFXRadioButton rbtn_carpeta;
    @FXML private JFXRadioButton rbtn_Menu;


	// CONSUMIR WEB SERVICES
	RestTemplate rest = new RestTemplate();
	String urlBase = PropertyManager.getBaseUrl();
	String uriMenu = urlBase + "/menu";
	String uriRol = urlBase + "/rol";

	// DE LA CLASE MENU
	Menu menu = new Menu();
	List<Menu> listaMenu = new ArrayList<Menu>();
	private static ResponseEntity<List<Menu>> listRespMenu;
	ObservableList<Menu> obsListMenu = FXCollections.observableArrayList();

	// DE LA CLASE ROL
	Rol rol = new Rol();
	List<Rol> listaRol = new ArrayList<Rol>();
	private static ResponseEntity<List<Rol>> listRespRol;
	ObservableList<Rol> obsListRol = FXCollections.observableArrayList();

	public void initialize() {
		loadMenus();
		loadRoles();
		restoreToController();
		changueTextMenu();
	}

	@FXML
	void addNuevoMenu(ActionEvent event) {
		restoreToController();
		menu = new Menu();
	}

	@FXML
	void eliminarMenu(ActionEvent event) {
		try {
			if(lst_listaMenus.getSelectionModel().getSelectedItems().isEmpty()){
				Message.showWarningNotification("Seleccione el menu a eliminar.!!");
				return;
			}
			
			Optional<ButtonType> result = Message.showQuestion("Desea continuar y eliminar los datos del Menu: " + 
			lst_listaMenus.getSelectionModel().getSelectedItem().getNombre() + " ?.", Context.getInstance().getStage());
			if(result.get() == ButtonType.OK){
				//Eliminar datos.
				Map<String, String> params = new HashMap<String, String>();
				params.put("c", menu.getId());
				rest.delete(uriMenu + "/delete/{c}", params);
				//save ROL
				for (Rol r : listaRol) {
					if (r.getMenusIds().contains(menu.getId())) r.getMenusIds().remove(menu.getId()); 
					rest.postForObject(uriRol + "/saveOrUpdate", r, Rol.class);
				}
				Message.showSuccessNotification("Se eliminaron exitosamente los datos.!!");
				initialize();
			}			
		} catch (Exception e) {
			e.printStackTrace();
			Message.showErrorNotification("Ha surgido un error al eliminar datos.!!");
		}
	}

	@FXML
	void guardarMenu(ActionEvent event) {
		try {
			// validaciones respectivas
			if (txt_nombreMenu.getText().isEmpty() || txt_nombreMenu.getText().isBlank()) {
				Message.showWarningNotification("Debe ingresar un nombre al Menu.!!");
				return;
			}
			
			Optional<ButtonType> result = Message.showQuestion("Desea continuar y grabar los datos?.", Context.getInstance().getStage());
			if(result.get() == ButtonType.OK){
				menu.setNombre(txt_nombreMenu.getText());
				menu.setUrl(txt_rutaFXML.getText());
				menu.setLogoRuta(txt_rutaLogo.getText());
				menu.setOrden(spin_orden.getValue());
				menu.setIdPadre(null);
				if (rbtn_menuSecundario.isSelected()) {
					// Tambien debo validar que el nombre de cada menu no seaigual a otro menu
					for (Menu m : listaMenu) {
						if (txt_nombreMenuPadre.getText().equals(m.getNombre())) {
							menu.setIdPadre(m);							
						}else {
							Message.showErrorNotification("El nombre del Menu Padre no coincide con ningun Menu de la base de datos.!! ");
							return;
						}
					}
				}
				menu.setEstado(true);
				Menu mnew = rest.postForObject(uriMenu + "/saveOrUpdate", menu, Menu.class);
				//SAVE ROL
				for (Rol r : chklst_listaRoles.getItems()) {
					if (chklst_listaRoles.getCheckModel().isChecked(r)) {
						if (!r.getMenusIds().contains(mnew.getId())) r.getMenusIds().add(mnew.getId()); 
					}else {
						if (r.getMenusIds().contains(mnew.getId())) r.getMenusIds().remove(mnew.getId());
					}
					rest.postForObject(uriRol + "/saveOrUpdate", r, Rol.class);
				}
				Message.showSuccessNotification("Se guardaron exitosamente los datos.!! ");
				menu = new Menu();
				initialize();
			}
		} catch (Exception e) {
			e.printStackTrace();
			Message.showErrorNotification("Ha surgido un error al guardar datos.!!");			
		}		
	}

	@FXML
	void selectedMenuSecundario() {
		if (rbtn_menuSecundario.isSelected()) {
			txt_nombreMenuPadre.setDisable(false);
		} else {
			txt_nombreMenuPadre.setDisable(true);
		}
	}

	@FXML
	void changueTextMenu() {
		List<String> listAuto = new ArrayList<String>();
		for (Menu menu : listaMenu) {
			listAuto.add(menu.getNombre());
		}
		TextFields.bindAutoCompletion(txt_nombreMenuPadre, listAuto);
	}

	public void restoreToController() {
		txt_nombreMenu.clear();
		txt_rutaFXML.clear();
		txt_rutaLogo.clear();
		txt_nombreMenuPadre.clear();
		rbtn_menuSecundario.setSelected(false);
		selectedMenuSecundario();
		General.spinnerNumerico(spin_orden);
	}

	public void loadMenus() {
		obsListMenu.clear();
		listRespMenu = rest.exchange(uriMenu + "/getAll", HttpMethod.GET, null,
				new ParameterizedTypeReference<List<Menu>>() {
				});
		listaMenu = listRespMenu.getBody();
		Collections.sort(listaMenu);
		lst_listaMenus.setPlaceholder(new Label("--- No se encontraron datos en la Base. ---"));
		if (!listaMenu.isEmpty()) {
			for (int i = 0; i < listaMenu.size(); i++) {
				obsListMenu.add(listaMenu.get(i));
			}			
			lst_listaMenus.setItems(obsListMenu);	
	    	lst_listaMenus.setCellFactory(param -> new ListCell<Menu>() {
	    		protected void updateItem(Menu item, boolean empty) {
	    			super.updateItem(item, empty);
	    			setText(empty ? "" : item.getNombre());
	    		};
	    	});   
		}		
    	//
    	lst_listaMenus.getSelectionModel().selectedItemProperty()
		.addListener((ObservableValue<? extends Menu> ov, Menu old_val, Menu new_val) -> {			
			if (lst_listaMenus.getSelectionModel().getSelectedItem() != null) {
				menu = lst_listaMenus.getSelectionModel().getSelectedItem();
				txt_nombreMenu.setText(menu.getNombre());
				txt_rutaFXML.setText(menu.getUrl());
				txt_rutaLogo.setText(menu.getLogoRuta());
				spin_orden.getValueFactory().setValue(menu.getOrden());
				if (menu.getIdPadre() == null || menu.getIdPadre().getId().isEmpty()) {
					rbtn_menuSecundario.setSelected(false);
					txt_nombreMenuPadre.clear();
				} else {
					rbtn_menuSecundario.setSelected(true);
					txt_nombreMenuPadre.setText(menu.getIdPadre().getNombre());
				}
				//si este menu se encuentra en este rol checkea
				chklst_listaRoles.getCheckModel().clearChecks();
				for (Rol rol : chklst_listaRoles.getItems()) {
					if (rol.getMenusIds().contains(menu.getId())) 
						chklst_listaRoles.getCheckModel().check(rol);
				}
			}			
		});
	}

	public void loadRoles() {
		obsListRol.clear();
		listRespRol = rest.exchange(uriRol + "/getAll", HttpMethod.GET, null,
				new ParameterizedTypeReference<List<Rol>>() {});
		listaRol = listRespRol.getBody();
		chklst_listaRoles.setPlaceholder(new Label("--- No se encontraron datos en la Base. ---"));
		if (!listaRol.isEmpty()) {
			for (int i = 0; i < listaRol.size(); i++) {
				obsListRol.add(listaRol.get(i));
			}
			chklst_listaRoles.setItems(obsListRol);
			chklst_listaRoles.setCellFactory(lv -> new CheckBoxListCell<Rol>(chklst_listaRoles::getItemBooleanProperty) {
				@Override
				public void updateItem(Rol item, boolean empty) {
					super.updateItem(item, empty);
					setText(empty ? "" : item.getRol());
				}
			});
		}
	}
}
