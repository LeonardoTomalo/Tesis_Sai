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
import java.util.List;

import javax.imageio.ImageIO;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXChipView;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.stage.FileChooser;


public class PopoverMediaCloudController {

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
    @FXML private JFXComboBox<TipoMedia> cmb_tipoMedia;
    @FXML private AnchorPane contenedorDeMedios;
    @FXML private JFXButton btn_Guardar;
    @FXML private JFXButton btn_Cancelar;

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
		gcsw.showMediaInContenedor(new Image("albums.png",250,500,true,false), contenedorDeMedios);		
//		restoreToController();
		loadTipoMedios();
		loadEtiquetas();
		if (Context.getInstance().getMediaContext() != null) {
			media = Context.getInstance().getMediaContext();
			cargarDatosMedia(media);
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
			        gcsw.showMediaInContenedor(image, contenedorDeMedios);
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
    void guardarMedia(ActionEvent event) {
    	//cierra popover y envia el medio a la lista de medios del recurso
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
    		media.setAutor(txt_autorMedio.getText());//
    		media.setCoordenadas(txt_coordenadasMedio.getText());//
    		media.setDescripcion(txt_descripcionMedio.getText());//
    		if (media.getId() != null) {}else {
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
    		//media.setUsuarioIngresa(Context.getInstance().getUserLogged());//				

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

    		//GUARDAMOS EL FILE TEMPORALMENTE
			String url = "";
    		if (media.getId() != null) {
    			System.out.println("Guardando contenido multimedia en GOOGLE CLOUD.");
				if (media.getExtensionFile().equals(".mp4") || media.getExtensionFile().equals(".wt3")) {
					url = gcsw.updateMediaCR(media.getId(), gcsw.fileToByteArray(medioFile));
				}else {
					url = gcsw.updateMediaCR(media.getId(), General.converterImageToByteArray(bufferedImageToExchage));						
				}			
				media.setUrlAlmacenamiento(url);
    			rest.postForObject(uriMediaCloud + "/saveOrUpdate", media, MediaCloudResources.class);
        	} else {
	    		media.setFileTemporal(medioFile);
			}

    		Context.getInstance().setMediaContext(media);
    		System.out.println("*************** TERMINO DE GUARDAR *****************");
    		Message.showSuccessNotification("Se agregó el contenido.!! ");

    		Context.getInstance().getStageModalBase().close();

		} catch (Exception e) {
			e.printStackTrace();
			Message.showErrorNotification("Ha surgido un error al guardar datos.!!");
		}
    }

    @FXML
    void cancelarMedia(ActionEvent event) {
    	try {
    		Context.getInstance().setMediaContext(null);
    		Context.getInstance().getStageModalBase().close();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    @FXML
    void showMapaUbicacion(ActionEvent event) {

    }
    
    private void loadTipoMedios() {
    	listRespTipoMedia = rest.exchange(uriTipoMedia + "/getAll", HttpMethod.GET, null,
				new ParameterizedTypeReference<List<TipoMedia>>() {
				});
		listaTipoMedia = listRespTipoMedia.getBody();
		if (!listaTipoMedia.isEmpty()) {
			TipoMedia auxTipo = new TipoMedia("", "Todos los medios");
			cmb_tipoMedia.getItems().add(auxTipo);
			for (int i = 0; i < listaTipoMedia.size(); i++) {
				obsListTipoMedia.add(listaTipoMedia.get(i));
			}			
			cmb_tipoMedia.setItems(obsListTipoMedia);	
	    	cmb_tipoMedia.setCellFactory(param -> new ListCell<TipoMedia>() {
	    		protected void updateItem(TipoMedia item, boolean empty) {
	    			super.updateItem(item, empty);
	    			setText(empty ? "" : item.getDescripcion());
	    		};
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
    
    public static String getExtensionOfFile(File file)	{
		String fileExtension="";
		String fileName=file.getName();		
		if(fileName.contains(".") && fileName.lastIndexOf(".")!= 0)	{
			fileExtension=fileName.substring(fileName.lastIndexOf(".")+1);
		}
		return fileExtension;
	}
    
    private void cargarDatosMedia(MediaCloudResources media) {
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
		if (media.getId() != null) {
			if (media.getTipoMedia() != null) {
				if (media.getTipoMedia().getDescripcion().equals("Imagen")) {
					Image img = gcsw.getImageMediaCR(media.getId());
					gcsw.showMediaInContenedor(img, contenedorDeMedios);	
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
		}else {//cargar con el file
			if (media.getTipoMedia().getDescripcion().equals("Imagen")) {
			    if (media.getFileTemporal() != null) {
			        Image image = new Image("file:" + media.getFileTemporal().getAbsolutePath());
			        gcsw.showMediaInContenedor(image, contenedorDeMedios);
			    }
			}else if(media.getTipoMedia().getDescripcion().equals("Video")) {
				if (media.getFileTemporal() != null) {
					File d = new File(media.getFileTemporal().getAbsolutePath().replace("\\","/"));
			        Media video = new Media(d.toURI().toString());
			        gcsw.showMediaInContenedor(video, contenedorDeMedios);						        
				}
			}else {
				//CONTENIDO 3D
			}	
		}
					
		
		//ranking
		if(media.getFecha() != null) {
			dtp_fechaMedio.setValue(media.getFecha().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
		}
	}

}
