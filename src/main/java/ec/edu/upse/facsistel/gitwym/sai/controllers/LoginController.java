package ec.edu.upse.facsistel.gitwym.sai.controllers;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import ec.edu.upse.facsistel.gitwym.sai.models.Usuario;
import ec.edu.upse.facsistel.gitwym.sai.utilities.Context;
import ec.edu.upse.facsistel.gitwym.sai.utilities.General;
import ec.edu.upse.facsistel.gitwym.sai.utilities.Message;
import ec.edu.upse.facsistel.gitwym.sai.utilities.PropertyManager;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonType;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class LoginController {

    @FXML private JFXTextField txt_userName;
    @FXML private JFXPasswordField txt_password;
    @FXML private JFXButton btn_entrar;
    @FXML private JFXButton btn_salir;
    @FXML private ImageView img_login;

    // CONSUMIR WEB SERVICES
   	RestTemplate rest = new RestTemplate();
   	String urlBase = PropertyManager.getBaseUrl();
	String uriUsuario = urlBase + "/usuario";
	
	//DE LA CLASE USUARIO
	Usuario usuario = new Usuario();
	List<Usuario> listaUsuario = new ArrayList<Usuario>();
	private static ResponseEntity<List<Usuario>> listRespUsuario;
	
    public void initialize() {
		General.setImageView("login.png", img_login);
    	txt_userName.requestFocus();
    	txt_userName.setText("DON TEO");
    	txt_password.setText("sa");
    	txt_userName.setOnKeyPressed(new EventHandler<KeyEvent>(){
			@Override
			public void handle(KeyEvent ke){
				if (ke.getCode().equals(KeyCode.ENTER)){
					entrar();
				}
			}
		});
		txt_password.setOnKeyPressed(new EventHandler<KeyEvent>(){
			@Override
			public void handle(KeyEvent ke){
				if (ke.getCode().equals(KeyCode.ENTER)){
					entrar();
				}
			}
		});
    }
    
    @FXML
    void entrar() {
    	try {
    		if (txt_userName.getText().isBlank() || txt_userName.getText().isEmpty()) {
				Message.showWarningNotification("Debe ingresar nombre de usuario.!!");
				txt_userName.requestFocus();
				return;
			}
    		if (txt_password.getText().isBlank() || txt_password.getText().isEmpty()) {
				Message.showWarningNotification("Debe ingresar contraseña de acceso.!!");
				txt_password.requestFocus();
				return;
			}
    		//
    		listRespUsuario = rest.exchange(uriUsuario + "/getAll", HttpMethod.GET, null,
    				new ParameterizedTypeReference<List<Usuario>>() {
    				});
    		listaUsuario = listRespUsuario.getBody();
    		Boolean existe = false;
    		for (Usuario u : listaUsuario) {
				if (u.getUsuario().equals(txt_userName.getText().trim()) && u.getContrasena().equals(txt_password.getText().trim())) {
					Context.getInstance().setUserLogged(u);
					General.setContentParent("/viewBase/GuiPrincipal.fxml", Context.getInstance().getAnch_Inicial());
					existe = true;
				}
			}
    		if (!existe) {
				Message.showWarningNotification("Clave/Usuario Incorrecto...!!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Message.showErrorNotification("Surgió un error al ingresar al sistema.!!!");
		}
    }

    @FXML
    void salir(ActionEvent event) {
    	Optional<ButtonType> result = Message.showQuestion("Desea continuar y salir del sistema?.", Context.getInstance().getStage());
		if(result.get() == ButtonType.OK) System.exit(0);	
    }
}
