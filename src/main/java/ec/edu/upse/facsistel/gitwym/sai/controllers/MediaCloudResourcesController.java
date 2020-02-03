package ec.edu.upse.facsistel.gitwym.sai.controllers;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.imageio.ImageIO;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXChipView;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;

import ec.edu.upse.facsistel.gitwym.sai.cloud.GoogleCloudStorageWorker;
import ec.edu.upse.facsistel.gitwym.sai.models.Etiquetas;
import ec.edu.upse.facsistel.gitwym.sai.models.MediaCloudResources;
import ec.edu.upse.facsistel.gitwym.sai.models.TipoMedia;
import ec.edu.upse.facsistel.gitwym.sai.utilities.Context;
import ec.edu.upse.facsistel.gitwym.sai.utilities.General;
import ec.edu.upse.facsistel.gitwym.sai.utilities.Message;
import ec.edu.upse.facsistel.gitwym.sai.utilities.PropertyManager;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.stage.FileChooser;

public class MediaCloudResourcesController {

    @FXML private JFXComboBox<TipoMedia> cmb_tipoMediaBusqueda;
    @FXML private JFXTextField txt_buscarNombre;
    @FXML private JFXButton btn_buscarNombre;
    @FXML private JFXButton btn_verAllMapa;
    @FXML private JFXListView<MediaCloudResources> lst_listaMedios;
    @FXML private JFXButton btn_Nuevo;
    @FXML private JFXButton btn_Eliminar;
    @FXML private JFXButton btn_Guardar;
    @FXML private JFXTextField txt_coordenadasMedio;
    @FXML private JFXButton btn_buscarMapa;
    @FXML private JFXChipView<Etiquetas> chpv_etiquetasEscogidas;
    @FXML private JFXDatePicker dtp_fechaMedio;
    @FXML private JFXRadioButton rbtn_esReportado;
    @FXML private JFXRadioButton rbtn_esPrincipal;
    @FXML private JFXTextField txt_urlAlmacenamiento;
    @FXML private JFXTextField txt_nombreMedio;
    @FXML private JFXButton btn_cargarMedio;
    @FXML private JFXTextArea txt_descripcionMedio;
    @FXML private JFXTextField txt_autorMedio;
    @FXML private AnchorPane contenedorDeMedios;
    @FXML private JFXComboBox<TipoMedia> cmb_tipoMedia;

    // DE GOOGLE CLOUD IMAGEN
    GoogleCloudStorageWorker gcsw = new GoogleCloudStorageWorker();
    BufferedImage bufferedImageToExchage;  	
    File medioFile = null;
    
    //VARIABLES GENERALES
    Boolean fileTraer = false;
    
    // CONSUMIR WEB SERVICES
 	RestTemplate rest = new RestTemplate();
	String urlBase = PropertyManager.getBaseUrl();
 	String uriEtiquetas = urlBase + "/etiquetas";
 	String uriMediaCloud = urlBase + "/mediaCloudResources";
 	String uriTipoMedia = urlBase + "/tipoMedia";

	// DE LA CLASE MEDIA CLOUD
 	MediaCloudResources media = new MediaCloudResources();
	List<MediaCloudResources> listaMedia = new ArrayList<MediaCloudResources>();
	private static ResponseEntity<List<MediaCloudResources>> listRespMedia;
	ObservableList<MediaCloudResources> obsListMedia = FXCollections.observableArrayList();
	
	// DE LA CLASE ETIQUETA
	Etiquetas etiquetas = new Etiquetas();
	List<Etiquetas> listaEtiquetas = new ArrayList<Etiquetas>();
	private static ResponseEntity<List<Etiquetas>> listRespEtiquetas;
	ObservableList<Etiquetas> obsListEtiquetas = FXCollections.observableArrayList();

 	// DE LA CLASE TIPOMEDIA
 	TipoMedia tipoMedia = new TipoMedia();
 	List<TipoMedia> listaTipoMedia = new ArrayList<TipoMedia>();
 	private static ResponseEntity<List<TipoMedia>> listRespTipoMedia;
 	ObservableList<TipoMedia> obsListTipoMedia = FXCollections.observableArrayList();
 	
	
	public void initialize() {
		gcsw.showMediaInContenedor(new Image("albums.png",250,500,true,false), contenedorDeMedios, (double) 288);
		restoreToController();
		loadTipoMedios();
		loadEtiquetas();
		loadMedios();
		buscarPorNombre();
	}	
    @FXML
    void addNuevaMedia(ActionEvent event) {
    	initialize();
    	media = new MediaCloudResources();
    }

    @FXML
    void eliminarMedia(ActionEvent event) {
    	try {//Con eliminar el medio ya no traera el id de google cloud, asi que no importa por el momento eliminar tambien de la nube.
			if (lst_listaMedios.getSelectionModel().getSelectedItems().isEmpty()) {
				Message.showWarningNotification("Seleccione el medio a eliminar.!!");
				return;
			}
			Optional<ButtonType> result = Message.showQuestion("Desea continuar y eliminar los datos del medio: "
							+ lst_listaMedios.getSelectionModel().getSelectedItem().getNombre() + " ?.",
					Context.getInstance().getStage());
			if (result.get() == ButtonType.OK) {
				//Eliminar datos.
				Map<String, String> params = new HashMap<String, String>();
				params.put("c", media.getId());
				rest.delete(uriMediaCloud + "/delete/{c}", params);
				Message.showSuccessNotification("Se eliminaron exitosamente los datos.!!");
				initialize();
			}
		} catch (Exception e) {
			e.printStackTrace();
			Message.showErrorNotification("Ha surgido un error al eliminar datos.!!");
		}
    }

    @FXML
    void guardarMedia(ActionEvent event) {
    	try {			
    		Boolean a = false;
    		if(cmb_tipoMedia.getValue() != null) a = true;    		
			if (a.equals(false)) {
				Message.showWarningNotification("Debe seleccionar un tipo de medio.!!");
				return;
			}
			
			if (!fileTraer){
				Message.showWarningNotification("Debe agregar un contenido multimedia.!!");
				return;
			}
    		
			if (txt_nombreMedio.getText().isEmpty() || txt_nombreMedio.getText().isBlank()) {
				Message.showWarningNotification("Debe agregar el nombre del medio.!!");
				return;
			}
			if (txt_coordenadasMedio.getText().isEmpty() || txt_coordenadasMedio.getText().isBlank()) {
				Message.showWarningNotification("Debe agregar las coordenadas del medio.!!");
				return;
			}
			Optional<ButtonType> result = Message.showQuestion("Desea continuar y grabar los datos?.",
					Context.getInstance().getStage());
			if (result.get() == ButtonType.OK) {
				media.setAutor(txt_autorMedio.getText());//
				media.setCoordenadas(txt_coordenadasMedio.getText());//
				media.setDescripcion(txt_descripcionMedio.getText());//
				if (media.getId() != null) {
//					media.setExtensionFile(extensionFile);
				}else {
					media.setExtensionFile("." + getExtensionOfFile(medioFile));//					
				}
				media.setEstado(true);//
				LocalDate ld = dtp_fechaMedio.getValue();
				if(ld != null) {
					Instant instant = Instant.from(ld.atStartOfDay(ZoneId.systemDefault()));
					Date date = Date.from(instant);
					media.setFecha(date);//
				}
				media.setIsPrincipal(rbtn_esPrincipal.isSelected());//
				media.setIsReportado(rbtn_esReportado.isSelected());//
				media.setNombre(txt_nombreMedio.getText());//
				media.setTipoMedia(cmb_tipoMedia.getSelectionModel().getSelectedItem());
//				media.setUsuarioIngresa(Context.getInstance().getUserLogged());//				
				
				//Para guardar los ids de etiquetas.
				ArrayList<String> ls = new ArrayList<String>();
            	List<Etiquetas> le = new ArrayList<Etiquetas>();
				System.out.println(chpv_etiquetasEscogidas.getChips().toString());
				for(int i=0;i<chpv_etiquetasEscogidas.getChips().size();i++) {
	            	Boolean t = false;
					System.out.println("Recorrer Etiqueta: " + chpv_etiquetasEscogidas.getChips().get(i));
					for (Etiquetas e : listaEtiquetas) {
						System.out.println(e.toString() + " === " + chpv_etiquetasEscogidas.getChips().get(i));
						if(e.equals(chpv_etiquetasEscogidas.getChips().get(i))) {
							System.out.println("LA ETIQUETA EXISTE!!!!");
							t = true;
							le.add(e);
						}
					}
					if(t == false) {
						System.out.println("LA ETIQUETA NOOO EXISTE, DEBE AGREGARSE.!!! ");
//						Etiquetas n = chpv_etiquetasEscogidas.getChips().get(i);
						
//						System.out.println(chpv_etiquetasEscogidas.getChips().get(i).toString());
//						le.add(new Etiquetas("", n.getDescripcion()));
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

				if (media.getIdsEtiqueta() != null) {
					media.getIdsEtiqueta().clear();
				}
				media.setIdsEtiqueta(ls);	
				//FIN DE GUARDAR IDS DE ETIQUETAS.

				//GUARDAR LOS RANKINGS
				
				String url = "";
				if(media.getId() != null) {	}else {
					System.out.println("EL MEDIACLOUD ES NUEVO.");
					media = rest.postForObject(uriMediaCloud + "/saveOrUpdate", media, MediaCloudResources.class);
					//guardar el medio en la nube
					System.out.println("Guardando contenido multimedia en GOOGLE CLOUD.");
					if (media.getExtensionFile().equals(".mp4") || media.getExtensionFile().equals(".wt3")) {
						url = gcsw.saveMediaCR(media.getId(), gcsw.fileToByteArray(medioFile));
					}else {
						url = gcsw.saveMediaCR(media.getId(), General.converterImageToByteArray(bufferedImageToExchage));						
					}			
					media.setUrlAlmacenamiento(url);
				}				
				rest.postForObject(uriMediaCloud + "/saveOrUpdate", media, MediaCloudResources.class);
				Message.showSuccessNotification("Se guardaron exitosamente los datos.!! ");
				media = new MediaCloudResources();
				initialize();
			}
		} catch (Exception e) {
			e.printStackTrace();
			Message.showErrorNotification("Ha surgido un error al guardar datos.!!");
		}
    }

    @FXML
    void findMedioFile(ActionEvent event) {
    	try {
			FileChooser fileChooser = new FileChooser();
		    fileChooser.setTitle("Buscar Contenido Multimedia");
		   
    		Boolean a = false;
    		if(cmb_tipoMedia.getValue() != null) a = true;    		
			if (a.equals(false)) {
				Message.showWarningNotification("Debe seleccionar un tipo de medio.!!");
				return;
			}
		    
			if (cmb_tipoMedia.getValue().getDescripcion().equals("Imagen")) {
			    fileChooser.getExtensionFilters().addAll(
			            new FileChooser.ExtensionFilter("JPG", "*.jpg"),
			            new FileChooser.ExtensionFilter("PNG", "*.png")             
			    );
			    // Obtener la imagen seleccionada
			    medioFile = fileChooser.showOpenDialog(Context.getInstance().getStage());
			    long longitud = medioFile.length();
			    float valor = 25;
			    if ((longitud/1024000) >= valor) {
			    	Message.showWarningNotification("El contenido a subir no debe exceder de " + valor + " Mb..!!");
					return;
				}
			    //Almacenar en memoria.
			    BufferedImage bufferedImage = ImageIO.read(medioFile);
				bufferedImageToExchage = gcsw.redimensionarImagen(bufferedImage);
				DecimalFormat df = new DecimalFormat("#.00");
				
				System.out.println("Primer Archivo: " + df.format(longitud/1024) + " kb");				
//			    gcsw.saveImageInDirectorio(bufferedImageToExchage, "D:\\test.jpg");
			    
			    //Mostar la imagen
			    if (medioFile != null) {
			        Image image = new Image("file:" + medioFile.getAbsolutePath());
			        gcsw.showMediaInContenedor(image, contenedorDeMedios, (double) 288);
			        fileTraer = true;
			    }
			}else if (cmb_tipoMedia.getValue().getDescripcion().equals("Video")) {
			    fileChooser.getExtensionFilters().addAll(
			            new FileChooser.ExtensionFilter("MP4", "*.mp4")          
			    );
			    medioFile = fileChooser.showOpenDialog(Context.getInstance().getStage());
			    long longitud = medioFile.length();
			    float valor = 25;
			    if ((longitud/1024000) >= valor) {
			    	Message.showWarningNotification("El contenido a subir no debe exceder de " + valor + " Mb..!!");
					return;
				}
			    //Almacenar en memoria
			    
			    //redimensionar
			    
			    //Mostrar el video
			    if (medioFile != null) {
			    	File d = new File(medioFile.getAbsolutePath().replace("\\","/"));
			        Media video = new Media(d.toURI().toString());
			        gcsw.showMediaInContenedor(video, contenedorDeMedios);
			        fileTraer = true;
			    }
			}else {
			    fileChooser.getExtensionFilters().addAll(
			            new FileChooser.ExtensionFilter("WT3", "*.wt3")          
			    );			
			    medioFile = fileChooser.showOpenDialog(Context.getInstance().getStage());
			    long longitud = medioFile.length();
			    float valor = 25;
			    if ((longitud/1024000) >= valor) {
			    	Message.showWarningNotification("El contenido a subir no debe exceder de " + valor + " Mb..!!");
					return;
				}
			}		    
		} catch (IOException e) {
			e.printStackTrace();
			Message.showErrorNotification("Ha surgido un error al cargar medio.!!");
		}
    }

    @FXML
    void buscarPorNombre() {
		txt_buscarNombre.textProperty().addListener((observable, oldValue, newValue) -> {
    	    System.out.println("textfield changed from " + oldValue + " to " + newValue);
    	    
    	    ObservableList<MediaCloudResources> obsAux = FXCollections.observableArrayList();			
    	    for (MediaCloudResources mcr : lst_listaMedios.getItems()) {    	    	
    	    	String nombre = mcr.getNombre();
    	    	if (newValue.length() >0) {
    	    		for (int i = 0; i < newValue.length(); i++) {
    	    			if (nombre.trim().toLowerCase().charAt(i) == newValue.trim().toLowerCase().charAt(i)) {
    	    				if (!obsAux.isEmpty()) {
    	    					if (!obsAux.contains(mcr)) {
    	    						obsAux.add(mcr);
    	    					}
    	    				}else {
    	    					obsAux.add(mcr);
    	    				}
    	    			} 
    				}
				}
			}
    	    //enviamos la lista.
    	    lst_listaMedios.setItems(obsAux);
    	    if (newValue.isBlank() || newValue.isEmpty()) {
				loadMedios();
				obsListTipoMedia.clear();
				loadTipoMedios();
				
			}
    	});
    }

    @FXML
    void showAllMediaMapa(ActionEvent event) {

    }

    @FXML
    void showMapaUbicacion(ActionEvent event) {

    }

	private void loadMedios() {
		if(!obsListMedia.isEmpty())	obsListMedia.clear();
		listRespMedia = rest.exchange(uriMediaCloud + "/getAll", HttpMethod.GET, null,
				new ParameterizedTypeReference<List<MediaCloudResources>>() {
				});
		listaMedia = listRespMedia.getBody();
		lst_listaMedios.setPlaceholder(new Label("---  No se encontraron datos en la Base. ---"));
		if (!listaMedia.isEmpty()) {
			for (int i = 0; i < listaMedia.size(); i++) {
				obsListMedia.add(listaMedia.get(i));
			}			
			lst_listaMedios.setItems(obsListMedia);	
	    	lst_listaMedios.setCellFactory(param -> new ListCell<MediaCloudResources>() {
	    		protected void updateItem(MediaCloudResources item, boolean empty) {
	    			super.updateItem(item, empty);
	    			setText(empty ? "" : item.getNombre() );
	    		};
	    	});  	    	
		}
		//
		lst_listaMedios.getSelectionModel().selectedItemProperty()
				.addListener((ObservableValue<? extends MediaCloudResources> ov, MediaCloudResources old_val, MediaCloudResources new_val) -> {
					if (lst_listaMedios.getSelectionModel().getSelectedItem() != null) {
						media = lst_listaMedios.getSelectionModel().getSelectedItem();
						txt_autorMedio.setText(media.getAutor());
						txt_coordenadasMedio.setText(media.getCoordenadas());
						txt_descripcionMedio.setText(media.getDescripcion());
						txt_nombreMedio.setText(media.getNombre());
						txt_urlAlmacenamiento.setText(media.getUrlAlmacenamiento());
						if(!media.getIdsEtiqueta().isEmpty()) {
							List<Etiquetas> auxEt = listaEtiquetas;
							for (String idEt : media.getIdsEtiqueta()) {
								for (Etiquetas e : auxEt) {
									if (idEt.equals(e.getId())) {
										chpv_etiquetasEscogidas.getChips().add(e);
									}
								}
							}
						}
						rbtn_esPrincipal.setSelected(media.getIsPrincipal());
						rbtn_esReportado.setSelected(media.getIsReportado());
						cmb_tipoMedia.getSelectionModel().select(media.getTipoMedia());
						
						//traer la imagen con la url y presentar en el ImageView.
						if (media.getTipoMedia() != null) {
							if (media.getTipoMedia().getDescripcion().equals("Imagen")) {
								Image img = gcsw.getImageMediaCR(media.getId());
								gcsw.showMediaInContenedor(img, contenedorDeMedios, (double) 288);	
								fileTraer = true;
							}else if(media.getTipoMedia().getDescripcion().equals("Video")) {
								Media video = gcsw.getMediaFromMediaCR(media.getId());
						        gcsw.showMediaInContenedor(video, contenedorDeMedios);		
						        fileTraer = true;
							}else {
								//CONTENIDO 3D
								fileTraer = true;
							}	
						}					
						
						//ranking
						if(media.getFecha() != null) {
							dtp_fechaMedio.setValue(media.getFecha().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
						}
					}
				});
	}

    private void loadTipoMedios() {
    	listRespTipoMedia = rest.exchange(uriTipoMedia + "/getAll", HttpMethod.GET, null,
				new ParameterizedTypeReference<List<TipoMedia>>() {
				});
		listaTipoMedia = listRespTipoMedia.getBody();
		if (!listaTipoMedia.isEmpty()) {
			for (int i = 0; i < listaTipoMedia.size(); i++) {
				obsListTipoMedia.add(listaTipoMedia.get(i));
			}			
			TipoMedia auxTipo = new TipoMedia("", "Todos los medios");
//			obsListTipoMedia.add(auxTipo);
			ObservableList<TipoMedia> obsTMAux = obsListTipoMedia;
			cmb_tipoMedia.setItems(obsListTipoMedia);	
			cmb_tipoMediaBusqueda.setItems(obsTMAux);	
			cmb_tipoMediaBusqueda.getItems().add(auxTipo);
			cmb_tipoMediaBusqueda.getSelectionModel().select(auxTipo);
	    	cmb_tipoMedia.setCellFactory(param -> new ListCell<TipoMedia>() {
	    		protected void updateItem(TipoMedia item, boolean empty) {
	    			super.updateItem(item, empty);
	    			setText(empty ? "" : item.getDescripcion());
	    		};
	    	});   
	    	cmb_tipoMediaBusqueda.setCellFactory(param -> new ListCell<TipoMedia>() {
	    		protected void updateItem(TipoMedia item, boolean empty) {
	    			super.updateItem(item, empty);
	    			setText(empty ? "" : item.getDescripcion());
	    		};
	    	}); 
	    	
	    	cmb_tipoMediaBusqueda.getSelectionModel().selectedItemProperty()
			.addListener((ObservableValue<? extends TipoMedia> ov, TipoMedia old_val, TipoMedia new_val) -> {
				if (cmb_tipoMediaBusqueda.getSelectionModel().getSelectedItem() != null) {
					tipoMedia = cmb_tipoMediaBusqueda.getSelectionModel().getSelectedItem();
					lst_listaMedios.getItems().clear();
					

					ObservableList<MediaCloudResources> obsAux = FXCollections.observableArrayList();
					if (tipoMedia.getDescripcion().equals(auxTipo.getDescripcion())) {
						for (int i = 0; i < listRespMedia.getBody().size(); i++) {
							obsAux.add(listRespMedia.getBody().get(i));
						}	
					}else {
						//mostrar lista de MEDIOS por el tipo.
						for (MediaCloudResources mcr : listRespMedia.getBody()) {						
							if (mcr.getTipoMedia().getDescripcion().equals(tipoMedia.getDescripcion())) {
								obsAux.add(mcr);
							}
						}
					}
					lst_listaMedios.getItems().addAll(obsAux);
					
				}
			});
		}				
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
    	obsListEtiquetas.clear();
    	obsListTipoMedia.clear();
    	if (!chpv_etiquetasEscogidas.getSuggestions().isEmpty()) {
			chpv_etiquetasEscogidas.getSuggestions().clear();
		}
    	chpv_etiquetasEscogidas.getChips().clear();
    	dtp_fechaMedio.setValue(null);
    	txt_autorMedio.clear();
    	txt_coordenadasMedio.clear();
    	txt_descripcionMedio.clear();
    	txt_nombreMedio.clear();
    	txt_urlAlmacenamiento.clear();
    	rbtn_esPrincipal.setSelected(false);
    	rbtn_esReportado.setSelected(false);
    	fileTraer = false;
	}
    
    public static String getExtensionOfFile(File file)	{
		String fileExtension="";
		String fileName=file.getName();		
		if(fileName.contains(".") && fileName.lastIndexOf(".")!= 0)	{
			fileExtension=fileName.substring(fileName.lastIndexOf(".")+1);
		}
		return fileExtension;
	}
}
