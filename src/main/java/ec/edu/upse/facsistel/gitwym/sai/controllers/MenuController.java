package ec.edu.upse.facsistel.gitwym.sai.controllers;

import java.util.ArrayList;
import java.util.List;

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
import ec.edu.upse.facsistel.gitwym.sai.utilities.General;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.Spinner;
import javafx.scene.input.MouseEvent;

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
    
    //CONSUMIR WEB SERVICES
	RestTemplate rest = new RestTemplate();
	String uriMenu = "http://localhost:8082/menu";
	String uriRol = "http://localhost:8082/rol";

	//DE LA CLASE MENU
    Menu menu = new Menu();
	List<Menu> listaMenu = new ArrayList<Menu>();
    private static ResponseEntity<List<Menu>> listRespMenu;
	ObservableList<Menu> obsListMenu = FXCollections.observableArrayList();
	
	//DE LA CLASE ROL
    Rol rol = new Rol();
	List<Rol> listaRol = new ArrayList<Rol>();
    private static ResponseEntity<List<Rol>> listRespRol;
	ObservableList<Rol> obsListRol = FXCollections.observableArrayList();
			
	public void initialize(){   
		loadMenus();
		loadRoles();
		restoreToController();
    }

    @FXML
    void addNuevoMenu(ActionEvent event) {
    	restoreToController();
    	menu = new Menu();
    }

    @FXML
    void eliminarMenu(ActionEvent event) {
    	loadMenus();
    }

    @FXML
    void guardarMenu(ActionEvent event) {
    	//validaciones respectivas
    	//validacion de si desea grabar 
//    	for (Iterator iterator = listaZonas.iterator(); iterator.hasNext();) {
//			CatZona reg = (CatZona) iterator.next();
//			//daoZonas.merge(reg);
//			daoZonas.attachDirty(reg);
//		}
//		GestorTransaccion.commit();
//		actualizaContenido(lstZonas);
//		Mensaje.transaccion_ok();
    	menu.setNombre(txt_nombreMenu.getText());
    	menu.setUrl(txt_rutaFXML.getText());
    	menu.setLogoRuta(txt_rutaLogo.getText());
//    	menu.setOrden(Integer.parseInt(txt_ordenMenu.getText()));
    	menu.setOrden(spin_orden.getValue());
    	if (rbtn_menuSecundario.isSelected()) {
    		//debo buscar en ws el menu padre de acuerdo al nombre. 
    		//Tambien debo validar que el nombre de cada menu no seaigual a otro menu
    		//tambien debo cargarlos menus con sus respectivos hijos.
//        	menu.setIdPadre(txt_nombreMenuPadre.getText());
		} else {
			menu.setIdPadre(null);
		}
    	menu.setEstado(true);
    	rest.postForObject(uriMenu + "/saveOrUpdate", menu, Menu.class);
    	loadMenus();
    	restoreToController();
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
    void selectedItemMenu(MouseEvent event) {
    	lst_listaMenus.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends Menu> ov, Menu old_val, Menu new_val) -> {
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
    	    });
    	//validar lo de los roles.
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
    
    public void loadMenus(){
    	obsListMenu.clear();
    	listRespMenu = rest.exchange(uriMenu + "/getAll", HttpMethod.GET, null, new ParameterizedTypeReference<List<Menu>>() {	});
    	listaMenu= listRespMenu.getBody();
		for(int i = 0 ; i < listaMenu.size() ; i++ ){
			obsListMenu.add(listaMenu.get(i));
		}
		lst_listaMenus.setItems(obsListMenu);	
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
    
    public void loadRoles(){
    	obsListRol.clear();
    	listRespRol = rest.exchange(uriRol + "/getAll", HttpMethod.GET, null, new ParameterizedTypeReference<List<Rol>>() {	});
    	listaRol= listRespRol.getBody();
		for(int i = 0 ; i < listaRol.size() ; i++ ){
			obsListRol.add(listaRol.get(i));
		}
		//VALIDAR SI NO EXISTEN ROLES... DEBE MOSTRAR QUE NO EXISTEN ROLES. no solamente vacio
		lst_listaRoles.setItems(obsListRol);	
    	lst_listaRoles.setCellFactory(param -> new ListCell<Rol>() {
    		protected void updateItem(Rol item, boolean empty) {
    			super.updateItem(item, empty);
    			if (empty || item.getRol() == null) {
    		        setText("");
    		    } else {
    		        setText(item.getRol());
    		        
    		    }
    		};    		
    	});   
    }
}
