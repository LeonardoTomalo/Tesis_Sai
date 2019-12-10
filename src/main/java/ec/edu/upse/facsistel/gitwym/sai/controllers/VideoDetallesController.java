package ec.edu.upse.facsistel.gitwym.sai.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

public class VideoDetallesController {

    @FXML private AnchorPane anch_video;
    @FXML private JFXTextField txt_nombreVideo;
    @FXML private JFXTextArea txt_descripcionVideo;
    @FXML private JFXTextField txt_autorVideo;
    @FXML private AnchorPane anch_rancking;
    @FXML private JFXButton btn_guardarVideo;
    @FXML private JFXButton btn_salirVideo;

    @FXML
    void guardarVideo(ActionEvent event) {

    }

    @FXML
    void salirVideo(ActionEvent event) {

    }
}
