package ec.edu.upse.facsistel.gitwym.sai.controllers;
import org.springframework.web.client.RestTemplate;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;

import ec.edu.upse.facsistel.gitwym.sai.models.Costo;
import ec.edu.upse.facsistel.gitwym.sai.utilities.Context;
import ec.edu.upse.facsistel.gitwym.sai.utilities.Message;
import ec.edu.upse.facsistel.gitwym.sai.utilities.PropertyManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class ModalCostosController {

    @FXML private JFXTextArea txt_descripcion;
    @FXML private JFXTextField txt_costo;
    @FXML private JFXButton btn_Guardar;
    @FXML private JFXButton btn_Cancelar;

	// CONSUMIR WEB SERVICES
  	RestTemplate rest = new RestTemplate();
 	String urlBase = PropertyManager.getBaseUrl();
  	String uriCosto = urlBase + "/costo";
    
  	//DE LA CLASE COSTO
  	Costo costo = new Costo();
    
 	public void initialize() {
		if (Context.getInstance().getCostoContext() != null) {
			costo = Context.getInstance().getCostoContext();
			txt_costo.setText(costo.getValor().toString());
			txt_descripcion.setText(costo.getDescripcion());
		}
	}	
    
    @FXML
    void cancelar(ActionEvent event) {
    	try {
			Context.getInstance().setCostoContext(null);
			Context.getInstance().getStageModalBase().close();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

    @FXML
    void guardar(ActionEvent event) {
    	try {
    		if (txt_costo.getText().isEmpty() || txt_costo.getText().isBlank()) {
    			Message.showWarningNotification("Debe agregar el costo del servicio.!!");
    			return;
    		}
    		if (txt_descripcion.getText().isEmpty() || txt_descripcion.getText().isBlank()) {
    			Message.showWarningNotification("Debe agregar la descripcion del servicio.!!");
    			return;
    		}
    		
    		costo.setDescripcion(txt_descripcion.getText());
    		costo.setValor(Float.parseFloat(txt_costo.getText()));
    		if (costo.getId() != null) {
    			rest.postForObject(uriCosto + "/saveOrUpdate", costo, Costo.class);
            }

    		Context.getInstance().setCostoContext(costo);
    		Message.showSuccessNotification("Se agregó el contenido.!! ");
    		Context.getInstance().getStageModalBase().close();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
}
