package ec.edu.upse.facsistel.gitwym.sai.controllers;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.web.client.RestTemplate;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;

import ec.edu.upse.facsistel.gitwym.sai.models.Contacto;
import ec.edu.upse.facsistel.gitwym.sai.utilities.Context;
import ec.edu.upse.facsistel.gitwym.sai.utilities.Message;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonType;

public class ContactoController {
	
    @FXML private JFXTextField txt_nombre;
    @FXML private JFXTextArea txt_descripcion;
    @FXML private JFXTextField txt_telefono;
    @FXML private JFXTextField txt_celular;
    @FXML private JFXTextField txt_correo;
    @FXML private JFXTextField txt_facebook;
    @FXML private JFXTextField txt_instagram;
    @FXML private JFXTextField txt_twitter;
    @FXML private JFXButton btn_guardar;
    @FXML private JFXButton btn_salir;
    @FXML private JFXButton btn_eliminar;
    
    // CONSUMIR WEB SERVICES
 	RestTemplate rest = new RestTemplate();
 	String uriContacto = "http://localhost:8082/contacto";
    
 	// DE LA CLASE IDIOMAS
 	Contacto contacto = new Contacto();
 	
    
    public void initialize() {
    	btn_eliminar.setDisable(false);
    	if (Context.getInstance().getContactoP() != null) {
			contacto = Context.getInstance().getContactoP();
			txt_celular.setText(contacto.getCelular());
			txt_correo.setText(contacto.getCorreo());
			txt_descripcion.setText(contacto.getDescripcion());
			txt_facebook.setText(contacto.getFacebook());
			txt_instagram.setText(contacto.getInstagram());
			txt_nombre.setText(contacto.getNombre());
			txt_telefono.setText(contacto.getTelefono());
			txt_twitter.setText(contacto.getTwitter());
	    	btn_eliminar.setDisable(true);
		}
	}
    
    @FXML
    void eliminarContacto(ActionEvent event) {
    	try {
			Optional<ButtonType> result = Message.showQuestion("Desea continuar y eliminar los datos del contacto: "
							+ contacto.getNombre() + " ?.",
					Context.getInstance().getStage());
			if (result.get() == ButtonType.OK) {
				//Eliminar datos.
				Map<String, String> params = new HashMap<String, String>();
				params.put("c", contacto.getId());
				rest.delete(uriContacto + "/delete/{c}", params);
				Message.showSuccessNotification("Se eliminaron exitosamente los datos.!!");
				salirContacto();
			}
		} catch (Exception e) {
			e.printStackTrace();
			Message.showErrorNotification("Ha surgido un error al eliminar datos.!!");
		}
    }
    
    @FXML
    void guardarContacto(ActionEvent event) {
    	try {
			if (txt_nombre.getText().isEmpty() || txt_nombre.getText().isBlank()) {
				Message.showWarningNotification("Debe ingresar un nombre de contacto.!!");
				return;
			}
			Optional<ButtonType> result = Message.showQuestion("Desea continuar y grabar los datos?.",
					Context.getInstance().getStage());
			if (result.get() == ButtonType.OK) {
				contacto.setCelular(txt_celular.getText());
				contacto.setCorreo(txt_correo.getText());
				contacto.setDescripcion(txt_descripcion.getText());
				contacto.setEstado(true);
				contacto.setFacebook(txt_facebook.getText());
				contacto.setInstagram(txt_instagram.getText());
				contacto.setNombre(txt_nombre.getText());
				contacto.setTelefono(txt_telefono.getText());
				contacto.setTwitter(txt_twitter.getText());
				rest.postForObject(uriContacto + "/saveOrUpdate", contacto, Contacto.class);
				Message.showSuccessNotification("Se guardaron exitosamente los datos.!! ");
				contacto = new Contacto();
				salirContacto();
			}
		} catch (Exception e) {
			e.printStackTrace();
			Message.showErrorNotification("Ha surgido un error al guardar datos.!!");
		}
    }

    @FXML
    void salirContacto() {
    	Context.getInstance().setContactoP(null);
    	//sale del contacto.
//    	General.setContentParent("/viewPrincipal/RecursoPrincipal.fxml", Context.getInstance().getAnch_Contenido());    	
    }
}
