package ec.edu.upse.facsistel.gitwym.sai.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

public class SenderoDetallesController {

    @FXML private JFXButton btn_guardar;
    @FXML private JFXButton btn_atras;
    @FXML private JFXTextArea txt_descripcionSendero;
    @FXML private JFXTextArea txt_instruccionesSendero;
    @FXML private JFXTextField txt_nombreSendero;
    @FXML private JFXTextField txt_distanciaSendero;
    @FXML private JFXTextField txt_costosSendero;
    @FXML private JFXTextField txt_tiempoSendero;
    @FXML private JFXTabPane tbp_recurso;
    @FXML private AnchorPane anch_tabs;
    @FXML private AnchorPane anch_galeria;
    @FXML private AnchorPane anch_facilidades;
    @FXML private AnchorPane anch_video;
    @FXML private AnchorPane anch_animaciones3d;
    @FXML private AnchorPane anch_comentarios;

    @FXML
    void atras(ActionEvent event) {

    }

    @FXML
    void guardar(ActionEvent event) {

    }
}
