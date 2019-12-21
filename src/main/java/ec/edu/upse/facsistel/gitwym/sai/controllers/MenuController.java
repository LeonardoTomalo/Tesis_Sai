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
import ec.edu.upse.facsistel.gitwym.sai.utilities.ConsumeWS;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;

public class MenuController extends ConsumeWS<Menu>{

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
    
    Menu menu = new Menu();
    private static ResponseEntity<List<Menu>> listResponse;
	List<Menu> lista = new ArrayList<Menu>();
	RestTemplate rest = new RestTemplate();
	String uri = "http://localhost:8082/menu/getAll";
    
	public void initialize(){    		
    	listResponse = rest.exchange(uri, HttpMethod.GET, null, new ParameterizedTypeReference<List<Menu>>() {	});
    	lista= listResponse.getBody();
    	
    	ObservableList<Menu> data = FXCollections.observableArrayList();
		for(int i = 0 ; i < lista.size() ; i++ ){
			data.add(lista.get(i));
		}
		lst_listaMenus.setItems(data);		
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
    	emptyToNew();
    	menu = new Menu();
    }

    @FXML
    void eliminarMenu(ActionEvent event) {

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
    	menu.setOrden(Integer.parseInt(txt_ordenMenu.getText()));
    	menu.setIdPadre(null);
    	menu.setEstado(true);
    	saveOrUpdate(menu, Menu.class);
    	
    }

    @FXML
    void selectedMenuSecundario(ActionEvent event) {

    }
    
    public void emptyToNew() {
    	txt_nombreMenu.clear();
    	txt_rutaFXML.clear();
    	txt_rutaLogo.clear();
    	txt_ordenMenu.clear();
    	txt_nombreMenuPadre.clear();
    	rbtn_menuSecundario.setSelected(false);    	
    }
    
}
