package ec.edu.upse.facsistel.gitwym.sai.controllers;

import com.jfoenix.controls.JFXListView;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;

public class BaseMenu{

	@FXML private JFXListView<Label> lstv_Menu;

	public void initialize() {
		for (int i = 0; i < 4; i++) {
			Label lbl = new Label("OPCION " + i);
			lbl.setPadding(new Insets(5, 0, 5, 20));
			lstv_Menu.getItems().add(lbl);
		}
	}
}
