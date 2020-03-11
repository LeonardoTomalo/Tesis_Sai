package ec.edu.upse.facsistel.gitwym.sai.controllers;

import ec.edu.upse.facsistel.gitwym.sai.utilities.Context;
import ec.edu.upse.facsistel.gitwym.sai.utilities.General;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class BaseController {

    @FXML private ImageView img_logoIzq;
    @FXML private ImageView img_logoDer;
    @FXML private Label lbl_empresa;
    @FXML private AnchorPane anch_Inicial;
    
    static String nombreEmpresa = "entidad administradora de turismo";

	public void initialize() {
		General.setImageView("Upse.png", img_logoIzq);
		General.setImageView("Upse.png", img_logoDer);
		General.setTexttoLabel(lbl_empresa, nombreEmpresa);
		Context.getInstance().setAnch_Inicial(anch_Inicial);
		//General.setContentParent("/viewAcceso/Menu.fxml", anch_contenido);
		General.setContentParent("/viewInicial/Login.fxml", anch_Inicial);		
	}
	
}
