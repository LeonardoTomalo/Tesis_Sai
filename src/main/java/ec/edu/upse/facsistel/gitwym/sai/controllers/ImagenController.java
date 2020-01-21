package ec.edu.upse.facsistel.gitwym.sai.controllers;

import java.awt.image.BufferedImage;
import java.io.File;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXChipView;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;

import ec.edu.upse.facsistel.gitwym.sai.cloud.GoogleCloudStorageWorker;
import ec.edu.upse.facsistel.gitwym.sai.models.Etiquetas;
import ec.edu.upse.facsistel.gitwym.sai.models.Imagen;
import ec.edu.upse.facsistel.gitwym.sai.utilities.Context;
import ec.edu.upse.facsistel.gitwym.sai.utilities.General;
import ec.edu.upse.facsistel.gitwym.sai.utilities.Message;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

public class ImagenController {

    @FXML private JFXButton btn_verAllMapa;
    @FXML private JFXListView<Imagen> lst_listaImagenes;
    @FXML private JFXButton btn_Nuevo;
    @FXML private JFXButton btn_Eliminar;
    @FXML private JFXButton btn_Guardar;
    @FXML private JFXTextField txt_coordenadasImagen;
    @FXML private JFXButton btn_buscarMapa;
    @FXML private JFXChipView<Etiquetas> chpv_etiquetasEscogidas;
    @FXML private JFXDatePicker dtp_fechaImagen;
    @FXML private JFXRadioButton rbtn_esReportado;
    @FXML private JFXRadioButton rbtn_esPrincipal;
    @FXML private JFXTextField txt_urlAlmacenamiento;
    @FXML private JFXTextField txt_nombreImagen;
    @FXML private JFXTextField txt_autorImagen;
    @FXML private JFXButton btn_cargarImagen;
    @FXML private JFXTextArea txt_descripcionImagen;
    @FXML private ImageView img_imagen;

    //GOOGLE CLOUD 
    GoogleCloudStorageWorker gcsw = new GoogleCloudStorageWorker();
    
    //BufferedImage para tener la imagen en memoria y poder procesarla correctamente. No borrar HP!
  	BufferedImage bufferedImageToExchage;
  	
    
    // CONSUMIR WEB SERVICES
 	RestTemplate rest = new RestTemplate();
 	String uriEtiquetas = "http://localhost:8082/etiquetas";
 	String uriImagen = "http://localhost:8082/imagen";

	// DE LA CLASE IMAGE
	Imagen imagen = new Imagen();
	List<Imagen> listaImagen = new ArrayList<Imagen>();
	private static ResponseEntity<List<Imagen>> listRespImagen;
	ObservableList<Imagen> obsListImagen = FXCollections.observableArrayList();
	
	// DE LA CLASE ETIQUETA
	Etiquetas etiquetas = new Etiquetas();
	List<Etiquetas> listaEtiquetas = new ArrayList<Etiquetas>();
	private static ResponseEntity<List<Etiquetas>> listRespEtiquetas;
	ObservableList<Etiquetas> obsListEtiquetas = FXCollections.observableArrayList();
	
	
	public void initialize() {
		twelveMonkeyLibraryTestForImage();
		General.setImageView("picture.png", img_imagen);
		restoreToController();
		loadEtiquetas();
		loadImagen();
	}	
	
	private void twelveMonkeyLibraryTestForImage() {
		ImageIO.scanForPlugins();
		Iterator<ImageReader> readers = ImageIO.getImageReadersByFormatName("JPEG");
		while (readers.hasNext()) {
		    System.out.println("reader: " + readers.next());
		}
	}
	
    @FXML
    void addNuevaImagenes(ActionEvent event) {
    	initialize();
    	imagen = new Imagen();
    }

    @FXML
    void eliminarImagenes(ActionEvent event) {
    	try {
			if (lst_listaImagenes.getSelectionModel().getSelectedItems().isEmpty()) {
				Message.showWarningNotification("Seleccione la imagen a eliminar.!!");
				return;
			}
			Optional<ButtonType> result = Message.showQuestion("Desea continuar y eliminar los datos de imagen: "
							+ lst_listaImagenes.getSelectionModel().getSelectedItem().getNombre() + " ?.",
					Context.getInstance().getStage());
			if (result.get() == ButtonType.OK) {
				//Eliminar datos.
				Map<String, String> params = new HashMap<String, String>();
				params.put("c", imagen.getId());
				rest.delete(uriImagen + "/delete/{c}", params);
				Message.showSuccessNotification("Se eliminaron exitosamente los datos.!!");
				initialize();
			}
		} catch (Exception e) {
			e.printStackTrace();
			Message.showErrorNotification("Ha surgido un error al eliminar datos.!!");
		}
    }

	@FXML
    void guardarImagenes(ActionEvent event) {
    	try {
			if (img_imagen.getImage().getUrl().equals("file:/D:/WSPC_TEO_11/TESIS_SAI/target/classes/picture.png")) {
				Message.showWarningNotification("Debe agregar una imagen.!!");
				return;
			}
			if (txt_nombreImagen.getText().isEmpty() || txt_nombreImagen.getText().isBlank()) {
				Message.showWarningNotification("Debe agregar el nombre de la imagen.!!");
				return;
			}
			Optional<ButtonType> result = Message.showQuestion("Desea continuar y grabar los datos?.",
					Context.getInstance().getStage());
			if (result.get() == ButtonType.OK) {
				imagen.setAutor(txt_autorImagen.getText());
				imagen.setCoordenadas(txt_coordenadasImagen.getText());
				imagen.setDescripcion(txt_descripcionImagen.getText());
				imagen.setEstado(true);
				LocalDate ld = dtp_fechaImagen.getValue();
				if(ld != null) {
					Instant instant = Instant.from(ld.atStartOfDay(ZoneId.systemDefault()));
					Date date = Date.from(instant);
					imagen.setFecha(date);
				}
				imagen.setIsPrincipal(rbtn_esPrincipal.isSelected());
				imagen.setIsReportado(rbtn_esReportado.isSelected());
				imagen.setNombre(txt_nombreImagen.getText());
//				imagen.setRanking();
				//REALIZAR LO REFERENTE A GOOGLE CLOUD PARA TRAER URL DE ALMACENAMIENTO
				imagen.setUrl(txt_urlAlmacenamiento.getText());
				imagen.setUsuarioIngresa(Context.getInstance().getUserLogged());
				
				ArrayList<String> ls = new ArrayList<String>();
            	List<Etiquetas> le = new ArrayList<Etiquetas>();

				System.out.println(chpv_etiquetasEscogidas.getChips().toString());
				for(int i=0;i<chpv_etiquetasEscogidas.getChips().size();i++) {
	            	Boolean t = false;
					System.out.println("AHOORAA: " + chpv_etiquetasEscogidas.getChips().get(i));
					for (Etiquetas e : listaEtiquetas) {
						System.out.println(e.toString() + " === " + chpv_etiquetasEscogidas.getChips().get(i));
						if(e == chpv_etiquetasEscogidas.getChips().get(i)) {
							System.out.println("ENTROOOOOO!!!!");
							t = true;
							le.add(e);
						}
					}
					if(t == false) {
						System.out.println("ENTRO AL FALSOOOO!!! ");
//						le.add(new Etiquetas(null, chpv_etiquetasEscogidas.getChips().get(i).getDescripcion()));
					}
					
				}
				
				for (Etiquetas e : le) {
					if(e.getId().isEmpty() || e.getId().isBlank()) {	
						Etiquetas er = rest.postForObject(uriEtiquetas + "/saveOrUpdate", e, Etiquetas.class);
						ls.add(er.getId());
					}else {
						ls.add(e.getId());	
					}
				}				
				imagen.setEtiquetasIds(ls);				
//				rest.postForObject(uriImagen + "/saveOrUpdate", imagen, Imagen.class);
				Message.showSuccessNotification("Se guardaron exitosamente los datos.!! ");
				imagen = new Imagen();
				initialize();
			}
		} catch (Exception e) {
			e.printStackTrace();
			Message.showErrorNotification("Ha surgido un error al guardar datos.!!");
		}
    }

    @FXML
    void findImageFile(ActionEvent event) {
    	FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Buscar Imagen");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Images", "*.*"),
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG", "*.png"),
                new FileChooser.ExtensionFilter("JPEG", "*.jpeg")                
        );

        // Obtener la imagen seleccionada
        File imgFile = fileChooser.showOpenDialog(Context.getInstance().getStage());

        // Mostar la imagen
        if (imgFile != null) {
            Image image = new Image("file:" + imgFile.getAbsolutePath());
            img_imagen.setImage(image);
        }
    }

    @FXML
    void showAllImagenesMapa(ActionEvent event) {

    }

    @FXML
    void showMapaUbicacion(ActionEvent event) {

    }

	private void loadImagen() {
		obsListImagen.clear();
		listRespImagen = rest.exchange(uriImagen + "/getAll", HttpMethod.GET, null,
				new ParameterizedTypeReference<List<Imagen>>() {
				});
		listaImagen = listRespImagen.getBody();
		lst_listaImagenes.setPlaceholder(new Label("---  No se encontraron datos en la Base. ---"));
		if (!listaImagen.isEmpty()) {
			for (int i = 0; i < listaImagen.size(); i++) {
				obsListImagen.add(listaImagen.get(i));
			}			
			lst_listaImagenes.setItems(obsListImagen);	
	    	lst_listaImagenes.setCellFactory(param -> new ListCell<Imagen>() {
	    		protected void updateItem(Imagen item, boolean empty) {
	    			super.updateItem(item, empty);
	    			setText(empty ? "" : item.getNombre() );
	    		};
	    	});  	    	
		}
		//
		lst_listaImagenes.getSelectionModel().selectedItemProperty()
				.addListener((ObservableValue<? extends Imagen> ov, Imagen old_val, Imagen new_val) -> {
					if (lst_listaImagenes.getSelectionModel().getSelectedItem() != null) {
						imagen = lst_listaImagenes.getSelectionModel().getSelectedItem();
						txt_autorImagen.setText(imagen.getAutor());
						txt_coordenadasImagen.setText(imagen.getCoordenadas());
						txt_descripcionImagen.setText(imagen.getDescripcion());
						txt_nombreImagen.setText(imagen.getNombre());
						txt_urlAlmacenamiento.setText(imagen.getUrl());
						if(!imagen.getEtiquetasIds().isEmpty()) {
							List<Etiquetas> auxEt = listaEtiquetas;
							for (String idEt : imagen.getEtiquetasIds()) {
								for (Etiquetas e : auxEt) {
									if (idEt.equals(e.getId())) {
										chpv_etiquetasEscogidas.getChips().add(e);
									}
								}
							}
						}
						//traer la imagen con la url y presentar en el ImageView.
						//ranking
						rbtn_esPrincipal.setSelected(imagen.getIsPrincipal());
						rbtn_esReportado.setSelected(imagen.getIsReportado());
						dtp_fechaImagen.setValue(imagen.getFecha().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
					}
				});
	}


    private void loadEtiquetas() {
    	listRespEtiquetas = rest.exchange(uriEtiquetas + "/getAll", HttpMethod.GET, null,
				new ParameterizedTypeReference<List<Etiquetas>>() {
				});
		listaEtiquetas = listRespEtiquetas.getBody();
		if (!listaEtiquetas.isEmpty()) {
			for (int i = 0; i < listaEtiquetas.size(); i++) {
				obsListEtiquetas.add(listaEtiquetas.get(i));
			}			
			chpv_etiquetasEscogidas.getSuggestions().addAll(obsListEtiquetas);			
		}				
	}

    private void restoreToController() {
    	lst_listaImagenes.getSelectionModel().getSelectedItems().clear();
    	obsListEtiquetas.clear();
    	chpv_etiquetasEscogidas.getChips().clear();
    	dtp_fechaImagen.setValue(null);
    	txt_autorImagen.clear();
    	txt_coordenadasImagen.clear();
    	txt_descripcionImagen.clear();
    	txt_nombreImagen.clear();
    	txt_urlAlmacenamiento.clear();
	}
}
