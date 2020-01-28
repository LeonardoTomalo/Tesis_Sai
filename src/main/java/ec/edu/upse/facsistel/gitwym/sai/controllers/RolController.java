package ec.edu.upse.facsistel.gitwym.sai.controllers;

import java.util.ArrayList;
import java.util.Collections;
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
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;

import ec.edu.upse.facsistel.gitwym.sai.models.Menu;
import ec.edu.upse.facsistel.gitwym.sai.models.Rol;
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
import javafx.scene.control.SelectionMode;

public class RolController {

	@FXML private JFXListView<Rol> lst_listaRoles;
	@FXML private JFXButton btn_Nuevo;
	@FXML private JFXButton btn_Eliminar;
	@FXML private JFXButton btn_Guardar;
	@FXML private JFXTextField txt_nombreRol;
	@FXML private JFXTextArea txt_descripRol;
	@FXML private JFXListView<Menu> lst_menuSeleccionados;
	@FXML private JFXListView<Menu> lst_menuDisponibles;
	@FXML private JFXButton btn_addAllMenus;
	@FXML private JFXButton btn_quitAllMenus;

	// CONSUMIR WEB SERVICES
	RestTemplate rest = new RestTemplate();
	String urlBase = PropertyManager.getBaseUrl();
	String uriMenu = urlBase + "/menu";
	String uriRol = urlBase + "/rol";

	// DE LA CLASE MENU
	Menu menu = new Menu();
	List<Menu> listaMenu = new ArrayList<Menu>();
	private static ResponseEntity<List<Menu>> listRespMenu;
	ObservableList<Menu> obsListMenuDisp = FXCollections.observableArrayList();
	ObservableList<Menu> obsListMenuSelect = FXCollections.observableArrayList();

	// DE LA CLASE ROL
	Rol rol = new Rol();
	List<Rol> listaRol = new ArrayList<Rol>();
	private static ResponseEntity<List<Rol>> listRespRol;
	ObservableList<Rol> obsListRol = FXCollections.observableArrayList();

	public void initialize() {
		restoreToController();
		loadMenus();
		loadRoles();
	}

	@FXML
	void addNuevoRol(ActionEvent event) {
		initialize();
		rol = new Rol();
	}

	@FXML
	void eliminarRol(ActionEvent event) {
		try {
			if (lst_listaRoles.getSelectionModel().getSelectedItems().isEmpty()) {
				Message.showWarningNotification("Seleccione el rol a eliminar.!!");
				return;
			}
			Optional<ButtonType> result = Message.showQuestion("Desea continuar y eliminar los datos del Menu: "
							+ lst_listaRoles.getSelectionModel().getSelectedItem().getRol() + " ?.",
					Context.getInstance().getStage());
			if (result.get() == ButtonType.OK) {
				//Eliminar datos.
				Map<String, String> params = new HashMap<String, String>();
				params.put("c", rol.getId());
				rest.delete(uriRol + "/delete/{c}", params);
				Message.showSuccessNotification("Se eliminaron exitosamente los datos.!!");
				initialize();
			}
		} catch (Exception e) {
			e.printStackTrace();
			Message.showErrorNotification("Ha surgido un error al eliminar datos.!!");
		}
	}

	@FXML
	void guardarRol(ActionEvent event) {
		try {
			if (txt_nombreRol.getText().isEmpty() || txt_nombreRol.getText().isBlank()) {
				Message.showWarningNotification("Debe ingresar un nombre al Menu.!!");
				return;
			}
			Optional<ButtonType> result = Message.showQuestion("Desea continuar y grabar los datos?.",
					Context.getInstance().getStage());
			if (result.get() == ButtonType.OK) {
				rol.setRol(txt_nombreRol.getText());
				rol.setDescripcion(txt_descripRol.getText());
				rol.setEstado(true);
				if (rol.getMenusIds() != null) {
					rol.getMenusIds().clear();
				}
				ArrayList<String> ls = new ArrayList<String>();
				for (Menu menu : lst_menuSeleccionados.getItems()) {
					ls.add(menu.getId());
				}
				rol.setMenusIds(ls);
				rest.postForObject(uriRol + "/saveOrUpdate", rol, Rol.class);
				Message.showSuccessNotification("Se guardaron exitosamente los datos.!! ");
				rol = new Rol();
				initialize();
			}
		} catch (Exception e) {
			e.printStackTrace();
			Message.showErrorNotification("Ha surgido un error al guardar datos.!!");
		}
	}

	//
	@FXML
	void addAllMenus(ActionEvent event) {
		if (lst_menuDisponibles.getSelectionModel().getSelectedItems().isEmpty()) {
			Message.showWarningNotification("Debe seleccionar al menos un menu de la lista disponible.!!");
			return;
		}
		for (Menu m : lst_menuDisponibles.getSelectionModel().getSelectedItems()) {
			lst_menuSeleccionados.getItems().add(m);
			lst_menuDisponibles.getItems().remove(m);
		}
	}

	@FXML
	void quitAllMenus(ActionEvent event) {
		if (lst_menuSeleccionados.getSelectionModel().getSelectedItems().isEmpty()) {
			Message.showWarningNotification("Debe seleccionar al menos un menu de la lista de menus seleccionados.!!");
			return;
		}
		for (Menu m : lst_menuSeleccionados.getSelectionModel().getSelectedItems()) {
			lst_menuDisponibles.getItems().add(m);
			lst_menuSeleccionados.getItems().remove(m);
		}
	}

	private void loadRoles() {
		obsListRol.clear();
		listRespRol = rest.exchange(uriRol + "/getAll", HttpMethod.GET, null,
				new ParameterizedTypeReference<List<Rol>>() {
				});
		listaRol = listRespRol.getBody();
		lst_listaRoles.setPlaceholder(new Label("--- No se encontraron datos en la Base. ---"));
		if (!listaRol.isEmpty()) {
			for (int i = 0; i < listaRol.size(); i++) {
				obsListRol.add(listaRol.get(i));
			}
			lst_listaRoles.setItems(obsListRol);
			lst_listaRoles.setCellFactory(param -> new ListCell<Rol>() {
				protected void updateItem(Rol item, boolean empty) {
					super.updateItem(item, empty);
					setText(empty ? "" : item.getRol());
				};
			});
		}
		//
		lst_listaRoles.getSelectionModel().selectedItemProperty()
				.addListener((ObservableValue<? extends Rol> ov, Rol old_val, Rol new_val) -> {
					if (lst_listaRoles.getSelectionModel().getSelectedItem() != null) {
						obsListMenuDisp.clear();
						obsListMenuSelect.clear();
						rol = lst_listaRoles.getSelectionModel().getSelectedItem();
						txt_nombreRol.setText(rol.getRol());
						txt_descripRol.setText(rol.getDescripcion());
						if (!rol.getMenusIds().isEmpty()) {
							List<Menu> auxListMenu = listaMenu;
							obsListMenuDisp.addAll(auxListMenu);
							for (String idMenu : rol.getMenusIds()) {
								for (Menu menu : auxListMenu) {
									if (idMenu.equals(menu.getId())) {
										obsListMenuSelect.add(menu);
										obsListMenuDisp.remove(menu);
									}
								}
							}
							lst_menuDisponibles.setItems(obsListMenuDisp);
							lst_menuSeleccionados.setItems(obsListMenuSelect);
						}
					}
				});
	}

	private void loadMenus() {
		listRespMenu = rest.exchange(uriMenu + "/getAll", HttpMethod.GET, null,
				new ParameterizedTypeReference<List<Menu>>() {
				});
		listaMenu = listRespMenu.getBody();
		Collections.sort(listaMenu);
		lst_menuDisponibles.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		lst_menuSeleccionados.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		lst_menuDisponibles.setPlaceholder(new Label("--- Menus disponibles se encuentra vacio. ---"));
		lst_menuSeleccionados.setPlaceholder(new Label("--- Menus seleccionados se encuentra vacio. ---"));
		if (!listaMenu.isEmpty()) {
			for (int i = 0; i < listaMenu.size(); i++) {
				obsListMenuDisp.add(listaMenu.get(i));
			}			
			lst_menuDisponibles.setItems(obsListMenuDisp);	
	    	lst_menuDisponibles.setCellFactory(param -> new ListCell<Menu>() {
	    		protected void updateItem(Menu item, boolean empty) {
	    			super.updateItem(item, empty);
	    			setText(empty ? "" : item.getNombre());
	    		};
	    	});   
	    	lst_menuSeleccionados.setCellFactory(param -> new ListCell<Menu>() {
	    		protected void updateItem(Menu item, boolean empty) {
	    			super.updateItem(item, empty);
	    			setText(empty ? "" : item.getNombre());
	    		};
	    	});   
		}		
	}

	private void restoreToController() {
		txt_descripRol.clear();
		txt_nombreRol.clear();
		lst_menuDisponibles.getItems().clear();
		lst_menuSeleccionados.getItems().clear();
	}
}
