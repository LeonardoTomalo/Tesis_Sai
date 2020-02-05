package ec.edu.upse.facsistel.gitwym.sai.controllers;
import org.springframework.web.client.RestTemplate;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;

import ec.edu.upse.facsistel.gitwym.sai.models.Contacto;
import ec.edu.upse.facsistel.gitwym.sai.utilities.Context;
import ec.edu.upse.facsistel.gitwym.sai.utilities.Message;
import ec.edu.upse.facsistel.gitwym.sai.utilities.PropertyManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class ModalContactoController {

    @FXML private JFXTextField txt_nombre;
    @FXML private JFXTextField txt_telefono;
    @FXML private JFXTextField txt_correo;
    @FXML private JFXTextField txt_celular;
    @FXML private JFXTextField txt_facebook;
    @FXML private JFXTextField txt_instagram;
    @FXML private JFXTextField txt_twitter;
    @FXML private JFXTextArea txt_descripcion;
    @FXML private JFXButton btn_Guardar;
    @FXML private JFXButton btn_Cancelar;

	// CONSUMIR WEB SERVICES
  	RestTemplate rest = new RestTemplate();
 	String urlBase = PropertyManager.getBaseUrl();
  	String uriContacto = urlBase + "/contacto";
    
  	//DE LA CLASE COSTO
  	Contacto contacto = new Contacto();
    
 	public void initialize() {
		if (Context.getInstance().getContactoContext() != null) {
			contacto = Context.getInstance().getContactoContext();
			txt_celular.setText(contacto.getCelular());
			txt_correo.setText(contacto.getCorreo());
			txt_descripcion.setText(contacto.getDescripcion());
			txt_facebook.setText(contacto.getFacebook());
			txt_instagram.setText(contacto.getInstagram());
			txt_nombre.setText(contacto.getNombre());
			txt_telefono.setText(contacto.getTelefono());
			txt_twitter.setText(contacto.getTwitter());	
		}
	}	
    
    @FXML
    void cancelar(ActionEvent event) {
    	try {
			Context.getInstance().setContactoContext(null);
			Context.getInstance().getStageModalBase().close();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

    @FXML
    void guardar(ActionEvent event) {
    	try {
    		if (txt_nombre.getText().isEmpty() || txt_nombre.getText().isBlank()) {
    			Message.showWarningNotification("Debe agregar el nombre de contacto.!!");
    			return;
    		}
    		if (txt_celular.getText().isEmpty() || txt_celular.getText().isBlank()) {
    			Message.showWarningNotification("Debe agregar el celular de contacto.!!");
    			return;
    		}

    		contacto.setCelular(txt_celular.getText());
    		contacto.setCorreo(txt_correo.getText());
    		contacto.setDescripcion(txt_descripcion.getText());
    		contacto.setEstado(true);
    		contacto.setFacebook(txt_facebook.getText());
    		contacto.setInstagram(txt_instagram.getText());
    		contacto.setNombre(txt_nombre.getText());
    		contacto.setTelefono(txt_telefono.getText());
    		contacto.setTwitter(txt_twitter.getText());
    		
    		if (contacto.getId() != null) {
    			rest.postForObject(uriContacto + "/saveOrUpdate", contacto, Contacto.class);
            }

    		Context.getInstance().setContactoContext(contacto);
    		Message.showSuccessNotification("Se agregó el contenido.!! ");
    		Context.getInstance().getStageModalBase().close();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
}
