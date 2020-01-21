package ec.edu.upse.facsistel.gitwym.sai.controllers;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;

import ec.edu.upse.facsistel.gitwym.sai.models.Etiquetas;
import ec.edu.upse.facsistel.gitwym.sai.models.Imagen;
import ec.edu.upse.facsistel.gitwym.sai.models.Video;
import ec.edu.upse.facsistel.gitwym.sai.utilities.General;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.media.MediaView;

public class VideoController {

    @FXML private JFXButton btn_findVideo;
    @FXML private JFXListView<Video> lst_listaVideos;
    @FXML private JFXButton btn_Nuevo;
    @FXML private JFXButton btn_Eliminar;
    @FXML private JFXButton btn_Guardar;
    @FXML private JFXDatePicker dtp_fechaVideo;
    @FXML private JFXTextField txt_urlAlmacenamiento;
    @FXML private JFXTextField txt_nombreVideo;
    @FXML private JFXButton btn_cargarVideo;
    @FXML private JFXTextArea txt_descripcionVideo;
    @FXML private JFXTextField txt_autorVideo;
    @FXML private MediaView mdv_video;

    // CONSUMIR WEB SERVICES
	RestTemplate rest = new RestTemplate();
	String uriEtiquetas = "http://localhost:8082/etiquetas";
	String uriImagen = "http://localhost:8082/imagen";

   	// DE LA CLASE IMAGE
   	Imagen imagen = new Imagen();
   	List<Imagen> listaImagen = new ArrayList<Imagen>();
   	private static ResponseEntity<List<Imagen>> listRespImagen;
   	ObservableList<Imagen> obsListImagen = FXCollections.observableArrayList();
   	

	public void initialize() {
//		General.setImageView("picture.png", img_imagen);
		restoreToController();
		loadImagen();
	}	
	
    @FXML
    void addNuevoVideo(ActionEvent event) {

    }

    @FXML
    void eliminarVideos(ActionEvent event) {

    }

    @FXML
    void findVideoByNombre(ActionEvent event) {

    }

    @FXML
    void findVideoFile(ActionEvent event) {

    }

    @FXML
    void guardarVideos(ActionEvent event) {

    }

	private void loadImagen() {
//		obsListImagen.clear();
//		listRespImagen = rest.exchange(uriImagen + "/getAll", HttpMethod.GET, null,
//				new ParameterizedTypeReference<List<Imagen>>() {
//				});
//		listaImagen = listRespImagen.getBody();
//		lst_listaImagenes.setPlaceholder(new Label("---  No se encontraron datos en la Base. ---"));
//		if (!listaImagen.isEmpty()) {
//			for (int i = 0; i < listaImagen.size(); i++) {
//				obsListImagen.add(listaImagen.get(i));
//			}			
//			lst_listaImagenes.setItems(obsListImagen);	
//	    	lst_listaImagenes.setCellFactory(param -> new ListCell<Imagen>() {
//	    		protected void updateItem(Imagen item, boolean empty) {
//	    			super.updateItem(item, empty);
//	    			setText(empty ? "" : item.getNombre() );
//	    		};
//	    	});  	    	
//		}
//		//
//		lst_listaImagenes.getSelectionModel().selectedItemProperty()
//				.addListener((ObservableValue<? extends Imagen> ov, Imagen old_val, Imagen new_val) -> {
//					if (lst_listaImagenes.getSelectionModel().getSelectedItem() != null) {
//						imagen = lst_listaImagenes.getSelectionModel().getSelectedItem();
//						txt_autorImagen.setText(imagen.getAutor());
//						txt_coordenadasImagen.setText(imagen.getCoordenadas());
//						txt_descripcionImagen.setText(imagen.getDescripcion());
//						txt_nombreImagen.setText(imagen.getNombre());
//						txt_urlAlmacenamiento.setText(imagen.getUrl());
//						if(!imagen.getEtiquetasIds().isEmpty()) {
//							List<Etiquetas> auxEt = listaEtiquetas;
//							for (String idEt : imagen.getEtiquetasIds()) {
//								for (Etiquetas e : auxEt) {
//									if (idEt.equals(e.getId())) {
//										chpv_etiquetasEscogidas.getChips().add(e);
//									}
//								}
//							}
//						}
//						//traer la imagen con la url y presentar en el ImageView.
//						//ranking
//						rbtn_esPrincipal.setSelected(imagen.getIsPrincipal());
//						rbtn_esReportado.setSelected(imagen.getIsReportado());
//						dtp_fechaImagen.setValue(imagen.getFecha().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
//					}
//				});
	}

    private void restoreToController() {
//    	lst_listaImagenes.getSelectionModel().getSelectedItems().clear();
//    	obsListEtiquetas.clear();
//    	chpv_etiquetasEscogidas.getChips().clear();
//    	dtp_fechaImagen.setValue(null);
//    	txt_autorImagen.clear();
//    	txt_coordenadasImagen.clear();
//    	txt_descripcionImagen.clear();
//    	txt_nombreImagen.clear();
//    	txt_urlAlmacenamiento.clear();
	}
}
