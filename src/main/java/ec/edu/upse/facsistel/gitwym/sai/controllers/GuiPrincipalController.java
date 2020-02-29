package ec.edu.upse.facsistel.gitwym.sai.controllers;

import java.util.Optional;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;

import ec.edu.upse.facsistel.gitwym.sai.utilities.Context;
import ec.edu.upse.facsistel.gitwym.sai.utilities.General;
import ec.edu.upse.facsistel.gitwym.sai.utilities.Message;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class GuiPrincipalController {

    @FXML private JFXDrawer drw_drawer;
    @FXML private JFXHamburger hmb_menu;
    @FXML private Label lbl_nombreUsuario;
    @FXML private JFXButton btn_salir;
    @FXML private AnchorPane anch_contenido;    
    
    static String nombreUsuario = "Don Teo";    

    public void initialize() {
		General.setTexttoLabel(lbl_nombreUsuario, Context.getInstance().getUserLogged().getUsuario());
    	General.setContentParent("/viewPrincipal/RecursoPrincipal.fxml", anch_contenido);
		General.setContentToJFXDrawer("/viewBase/BaseMenu.fxml", drw_drawer);//
		Context.getInstance().setAnch_Contenido(anch_contenido);
		
		HamburgerBackArrowBasicTransition hmb1 = new HamburgerBackArrowBasicTransition(hmb_menu);
		hmb1.setRate(-1);		
		hmb_menu.addEventHandler(MouseEvent.MOUSE_PRESSED, (e) ->{
			hmb1.setRate(hmb1.getRate() * - 1);
			hmb1.play();
			General.setAbriCerrarDRW(drw_drawer);
		});
    }
    
    @FXML
    void salir_CerrarCesion(ActionEvent event) {
    	Optional<ButtonType> result = Message.showQuestion("Esta seguro de cerrar sesión?.", Context.getInstance().getStage());
		if(result.get() == ButtonType.OK) General.setContentParent("/viewInicial/Login.fxml", Context.getInstance().getAnch_Inicial());
    }
}
