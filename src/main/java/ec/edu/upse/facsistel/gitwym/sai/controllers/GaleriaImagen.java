package ec.edu.upse.facsistel.gitwym.sai.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class GaleriaImagen {

    @FXML private ImageView img_imagen;
    @FXML private JFXTextField txt_nombreImagen;
    @FXML private JFXTextArea txt_descripcionImagen;
    @FXML private JFXTextField txt_autorImagen;
    @FXML private AnchorPane anch_rancking;
    @FXML private JFXButton btn_guardarImagen;
    @FXML private JFXButton btn_salirImagen;

    @FXML
    void guardarImagen(ActionEvent event) {

    }

    @FXML
    void salirImagen(ActionEvent event) {

    }

}
