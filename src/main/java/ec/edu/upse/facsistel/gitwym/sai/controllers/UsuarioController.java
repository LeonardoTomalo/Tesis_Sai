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

import ec.edu.upse.facsistel.gitwym.sai.models.Rol;
import ec.edu.upse.facsistel.gitwym.sai.models.Usuario;
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
import javafx.scene.control.SelectionMode;

public class UsuarioController {

    @FXML private JFXListView<Usuario> lst_listaUsuarios;
    @FXML private JFXButton btn_Nuevo;
    @FXML private JFXButton btn_Eliminar;
    @FXML private JFXButton btn_Guardar;
    @FXML private JFXTextField txt_nombreUsuario;
    @FXML private JFXListView<Rol> lst_rolesSeleccionados;
    @FXML private JFXListView<Rol> lst_rolesDisponibles;
    @FXML private JFXButton btn_addAllRoles;
    @FXML private JFXButton btn_quitAllRoles;
    @FXML private JFXTextField txt_aliasUsuario;
    @FXML private JFXTextField txt_apellidosUsuario;
    @FXML private JFXTextField txt_contraseñaUsuario;
    @FXML private JFXTextField txt_telefonoUsuario;
    @FXML private JFXTextField txt_correoUsuario;
    @FXML private JFXTextField txt_celularUsuario;
    @FXML private JFXTextField txt_facebookUsuario;
    @FXML private JFXTextField txt_instagramUsuario;
    @FXML private JFXTextField txt_twitterUsuario;

	// CONSUMIR WEB SERVICES
	RestTemplate rest = new RestTemplate();
	String uriUsuario = "http://localhost:8082/usuario";
	String uriRol = "http://localhost:8082/rol";
    
	// DE LA CLASE USUARIOS
	Usuario usuario = new Usuario();
	List<Usuario> listaUsuario = new ArrayList<Usuario>();
	private static ResponseEntity<List<Usuario>> listRespUsuario;
	ObservableList<Usuario> obsListUsuario = FXCollections.observableArrayList();
	
	// DE LA CLASE ROL
	Rol rol = new Rol();
	List<Rol> listaRol = new ArrayList<Rol>();
	private static ResponseEntity<List<Rol>> listRespRol;
	ObservableList<Rol> obsListRolDisp = FXCollections.observableArrayList();
	ObservableList<Rol> obsListRolSelect = FXCollections.observableArrayList();
	
	public void initialize() {
		restoreToController();
		loadRoles();
		loadUsuarios();
	}	
	
    @FXML
    void addNuevoUsuarios(ActionEvent event) {
    	initialize();
    	usuario = new Usuario();
    }

    @FXML
    void eliminarUsuarios(ActionEvent event) {
    	try {
			if (lst_listaUsuarios.getSelectionModel().getSelectedItems().isEmpty()) {
				Message.showWarningNotification("Seleccione el usuario a eliminar.!!");
				return;
			}
			Optional<ButtonType> result = Message.showQuestion("Desea continuar y eliminar los datos del Rol: "
							+ lst_listaUsuarios.getSelectionModel().getSelectedItem().getNombre() + " ?.",
					Context.getInstance().getStage());
			if (result.get() == ButtonType.OK) {
				//Eliminar datos.
				Map<String, String> params = new HashMap<String, String>();
				params.put("c", usuario.getId());
				rest.delete(uriUsuario + "/delete/{c}", params);
				Message.showSuccessNotification("Se eliminaron exitosamente los datos.!!");
				initialize();
			}
		} catch (Exception e) {
			e.printStackTrace();
			Message.showErrorNotification("Ha surgido un error al eliminar datos.!!");
		}
    }

    @FXML
    void guardarUsuarios(ActionEvent event) {
    	try {
			if (txt_nombreUsuario.getText().isEmpty() || txt_nombreUsuario.getText().isBlank()) {
				Message.showWarningNotification("Debe ingresar un nombre de usuario.!!");
				return;
			}
			if (txt_apellidosUsuario.getText().isEmpty() || txt_apellidosUsuario.getText().isBlank()) {
				Message.showWarningNotification("Debe ingresar un apellido de usuario.!!");
				return;
			}
			if (txt_aliasUsuario.getText().isEmpty() || txt_aliasUsuario.getText().isBlank()) {
				Message.showWarningNotification("Debe ingresar un alias de usuario.!!");
				return;
			}
			Optional<ButtonType> result = Message.showQuestion("Desea continuar y grabar los datos?.",
					Context.getInstance().getStage());
			if (result.get() == ButtonType.OK) {
				usuario.setApellidos(txt_apellidosUsuario.getText());
				usuario.setCelular(txt_celularUsuario.getText());
				usuario.setContrasena(txt_contraseñaUsuario.getText());
				usuario.setCorreo(txt_correoUsuario.getText());
				usuario.setEstado(true);
				usuario.setFacebook(txt_facebookUsuario.getText());
				usuario.setFotoRutaImagen(null);
				usuario.setInstagram(txt_instagramUsuario.getText());
				usuario.setNombre(txt_nombreUsuario.getText());
				usuario.setTelefono(txt_telefonoUsuario.getText());
				usuario.setTwitter(txt_twitterUsuario.getText());
				usuario.setUsuario(txt_aliasUsuario.getText());
				if (usuario.getRolesIds() != null) {
					usuario.getRolesIds().clear();
				}
				ArrayList<String> ls = new ArrayList<String>();
				for (Rol rol : lst_rolesSeleccionados.getItems()) {
					ls.add(rol.getId());
				}
				usuario.setRolesIds(ls);
				rest.postForObject(uriUsuario + "/saveOrUpdate", usuario, Usuario.class);
				Message.showSuccessNotification("Se guardaron exitosamente los datos.!! ");
				usuario = new Usuario();
				initialize();
			}
		} catch (Exception e) {
			e.printStackTrace();
			Message.showErrorNotification("Ha surgido un error al guardar datos.!!");
		}
    }

    @FXML
    void addAllRoles(ActionEvent event) {
    	if (lst_rolesDisponibles.getSelectionModel().getSelectedItems().isEmpty()) {
			Message.showWarningNotification("Debe seleccionar al menos un rol de la lista disponible.!!");
			return;
		}
		for (Rol m : lst_rolesDisponibles.getSelectionModel().getSelectedItems()) {
			lst_rolesSeleccionados.getItems().add(m);
			lst_rolesDisponibles.getItems().remove(m);
		}
    }

    @FXML
    void quitAllRoles(ActionEvent event) {
    	if (lst_rolesSeleccionados.getSelectionModel().getSelectedItems().isEmpty()) {
			Message.showWarningNotification("Debe seleccionar al menos un rol de la lista de roles seleccionados.!!");
			return;
		}
		for (Rol m : lst_rolesSeleccionados.getSelectionModel().getSelectedItems()) {
			lst_rolesDisponibles.getItems().add(m);
			lst_rolesSeleccionados.getItems().remove(m);
		}
    }

    private void loadUsuarios() {
    	obsListUsuario.clear();
		listRespUsuario = rest.exchange(uriUsuario + "/getAll", HttpMethod.GET, null,
				new ParameterizedTypeReference<List<Usuario>>() {
				});
		listaUsuario = listRespUsuario.getBody();
		lst_listaUsuarios.setPlaceholder(new Label("---  No se encontraron datos en la Base. ---"));
		if (!listaUsuario.isEmpty()) {
			for (int i = 0; i < listaUsuario.size(); i++) {
				obsListUsuario.add(listaUsuario.get(i));
			}			
			lst_listaUsuarios.setItems(obsListUsuario);	
	    	lst_listaUsuarios.setCellFactory(param -> new ListCell<Usuario>() {
	    		protected void updateItem(Usuario item, boolean empty) {
	    			super.updateItem(item, empty);
	    			setText(empty ? "" : item.getNombre().concat(" ").concat(item.getApellidos()) );
	    		};
	    	});  	    	
		}
		//
		lst_listaUsuarios.getSelectionModel().selectedItemProperty()
				.addListener((ObservableValue<? extends Usuario> ov, Usuario old_val, Usuario new_val) -> {
					if (lst_listaUsuarios.getSelectionModel().getSelectedItem() != null) {
						obsListRolDisp.clear();
						obsListRolSelect.clear();
						usuario = lst_listaUsuarios.getSelectionModel().getSelectedItem();
						txt_aliasUsuario.setText(usuario.getUsuario());
						txt_apellidosUsuario.setText(usuario.getApellidos());
						txt_celularUsuario.setText(usuario.getCelular());
						txt_contraseñaUsuario.setText(usuario.getContrasena());
						txt_correoUsuario.setText(usuario.getCorreo());
						txt_facebookUsuario.setText(usuario.getFacebook());
						txt_instagramUsuario.setText(usuario.getInstagram());
						txt_nombreUsuario.setText(usuario.getNombre());
						txt_telefonoUsuario.setText(usuario.getTelefono());
						txt_twitterUsuario.setText(usuario.getTwitter());
						if (!usuario.getRolesIds().isEmpty()) {
							List<Rol> auxListMenu = listaRol;
							obsListRolDisp.addAll(auxListMenu);
							for (String idRol : usuario.getRolesIds()) {
								for (Rol rol : auxListMenu) {
									if (idRol.equals(rol.getId())) {
										obsListRolSelect.add(rol);
										obsListRolDisp.remove(rol);
									}
								}
							}
							lst_rolesDisponibles.setItems(obsListRolDisp);
							lst_rolesSeleccionados.setItems(obsListRolSelect);
						}
					}
				});
	}
    
    private void loadRoles() {
		listRespRol = rest.exchange(uriRol + "/getAll", HttpMethod.GET, null,
				new ParameterizedTypeReference<List<Rol>>() {
				});
		listaRol = listRespRol.getBody();
		lst_rolesDisponibles.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		lst_rolesSeleccionados.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		lst_rolesDisponibles.setPlaceholder(new Label("--- Roles disponibles se encuentra vacio. ---"));
		lst_rolesSeleccionados.setPlaceholder(new Label("--- Roles seleccionados se encuentra vacio. ---"));
		if (!listaRol.isEmpty()) {
			for (int i = 0; i < listaRol.size(); i++) {
				obsListRolDisp.add(listaRol.get(i));
			}			
			lst_rolesDisponibles.setItems(obsListRolDisp);	
	    	lst_rolesDisponibles.setCellFactory(param -> new ListCell<Rol>() {
	    		protected void updateItem(Rol item, boolean empty) {
	    			super.updateItem(item, empty);
	    			setText(empty ? "" : item.getRol());
	    		};
	    	});   
	    	lst_rolesSeleccionados.setCellFactory(param -> new ListCell<Rol>() {
	    		protected void updateItem(Rol item, boolean empty) {
	    			super.updateItem(item, empty);
	    			setText(empty ? "" : item.getRol());
	    		};
	    	});   
		}		
	}
    
    private void restoreToController() {
    	txt_aliasUsuario.clear();
    	txt_apellidosUsuario.clear();
    	txt_celularUsuario.clear();
    	txt_contraseñaUsuario.clear();
    	txt_correoUsuario.clear();
    	txt_facebookUsuario.clear();
    	txt_instagramUsuario.clear();
    	txt_nombreUsuario.clear();
    	txt_telefonoUsuario.clear();
    	txt_twitterUsuario.clear();
    	lst_rolesDisponibles.getItems().clear();
    	lst_rolesSeleccionados.getItems().clear();
	}
    
}
