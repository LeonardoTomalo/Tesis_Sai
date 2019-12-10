package ec.edu.upse.facsistel.gitwym.sai.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class ContactoController {

    @FXML private JFXTextField txt_nombre;
    @FXML private JFXTextArea txt_descripcion;
    @FXML private JFXTextField txt_telefono;
    @FXML private JFXTextField txt_celular;
    @FXML private JFXTextField txt_correo;
    @FXML private JFXTextField txt_facebook;
    @FXML private JFXTextField txt_instagram;
    @FXML private JFXTextField txt_twitter;
    @FXML private JFXButton btn_guardarContacto;
    @FXML private JFXButton btn_salir;

    @FXML
    void guardarContacto(ActionEvent event) {

    }

    @FXML
    void salirContacto(ActionEvent event) {

    }

}
