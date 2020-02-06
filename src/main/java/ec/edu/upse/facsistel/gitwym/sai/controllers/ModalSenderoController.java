package ec.edu.upse.facsistel.gitwym.sai.controllers;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
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
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;

import ec.edu.upse.facsistel.gitwym.sai.cloud.GoogleCloudStorageWorker;
import ec.edu.upse.facsistel.gitwym.sai.models.Atractivo;
import ec.edu.upse.facsistel.gitwym.sai.models.Costo;
import ec.edu.upse.facsistel.gitwym.sai.models.DificultadRecorrido;
import ec.edu.upse.facsistel.gitwym.sai.models.DisponibilidadCelular;
import ec.edu.upse.facsistel.gitwym.sai.models.MediaCloudResources;
import ec.edu.upse.facsistel.gitwym.sai.models.Sendero;
import ec.edu.upse.facsistel.gitwym.sai.models.TipoAtractivo;
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

public class ModalSenderoController {

    @FXML private JFXButton btn_guardar;
    @FXML private JFXButton btn_atras;
    @FXML private JFXTextField txt_nombreSendero;
    @FXML private JFXTextArea txt_descripcionSendero;
    @FXML private JFXTextArea txt_instruccionesSendero;
    @FXML private JFXTextField txt_distanciaSendero;
    @FXML private JFXTextField txt_tiempoSendero;
    @FXML private JFXComboBox<DisponibilidadCelular> cmb_disponibilidadSendero;
    @FXML private JFXComboBox<DificultadRecorrido> cmb_dificultadSendero;
    @FXML private AnchorPane anch_tabs;
    @FXML private JFXTabPane tbp_recurso;
    @FXML private AnchorPane anch_galeria;
    @FXML private JFXComboBox<TipoMedia> cmb_tipoMediaBusqueda;
    @FXML private JFXButton btn_verMediaMapa;
    @FXML private JFXListView<MediaCloudResources> lst_listaMedios;
    @FXML private JFXTextField txt_buscarNombreMedio;
    @FXML private JFXButton btn_nuevoMedio;
    @FXML private JFXButton btn_EliminarMedio;
    @FXML private JFXButton btn_modificarMedio;
    @FXML private AnchorPane contenedorDeMedios;
    @FXML private AnchorPane anch_facilidades;
    @FXML private AnchorPane anch_video;
    @FXML private JFXComboBox<TipoAtractivo> cmb_tipoAtractivoBusqueda;
    @FXML private JFXButton btn_verAtractivoMapa;
    @FXML private JFXListView<Atractivo> lst_listaAtractivos;
    @FXML private JFXTextField txt_buscarNombreAtractivos;
    @FXML private JFXButton btn_nuevoAtractivo;
    @FXML private JFXButton btn_EliminarAtractivo;
    @FXML private JFXButton btn_modificarAtractivo;
    @FXML private AnchorPane contenedorDeAtractivos;
    @FXML private AnchorPane anch_comentarios;

    // CONSUMIR WEB SERVICES
   	RestTemplate rest = new RestTemplate();
   	String urlBase = PropertyManager.getBaseUrl();
	String uriSendero = urlBase + "/sendero";
 	String uriTipoMedia = urlBase + "/tipoMedia";
	String uriMediaCloud = urlBase + "/mediaCloudResources";
 	String uriCosto = urlBase + "/costo";
	String uriTipoAtractivo = urlBase + "/tipoAtractivo";
	String uriAtractivo = urlBase + "/atractivo";
    
	//DE LA CLASE SENDERO
	Sendero sendero = new Sendero();
	Boolean isModificar = false;
	Boolean exitPopup = false;	

	// DE LA CLASE MEDIA CLOUD
 	MediaCloudResources media = new MediaCloudResources();
	List<MediaCloudResources> listaMedia = new ArrayList<MediaCloudResources>();
	List<MediaCloudResources> listaMediaTemporal = new ArrayList<MediaCloudResources>();
	private static ResponseEntity<List<MediaCloudResources>> listRespMedia;
	ObservableList<MediaCloudResources> obsListMedia = FXCollections.observableArrayList();
	GoogleCloudStorageWorker gcsw = new GoogleCloudStorageWorker();

	// DE LA CLASE TIPOMEDIA
 	TipoMedia tipoMedia = new TipoMedia();
 	List<TipoMedia> listaTipoMedia = new ArrayList<TipoMedia>();
 	private static ResponseEntity<List<TipoMedia>> listRespTipoMedia;
 	ObservableList<TipoMedia> obsListTipoMedia = FXCollections.observableArrayList();

 	//DE LA CLASE COSTO
 	Costo costo = new Costo();
 	List<Costo> listaCosto = new ArrayList<Costo>();

	// DE LA CLASE ATRACTIVOS
	Atractivo atractivo = new Atractivo();
	List<Atractivo> listaAtractivo = new ArrayList<Atractivo>();
	List<Atractivo> listaAtractivoTemporal = new ArrayList<Atractivo>();
	private static ResponseEntity<List<Atractivo>> listRespAtractivo;
	ObservableList<Atractivo> obsListAtractivo = FXCollections.observableArrayList();

	// DE LA CLASE TIPOATRACTIVO
	TipoAtractivo tipoAtractivo = new TipoAtractivo();
	List<TipoAtractivo> listaTipoAtractivo = new ArrayList<TipoAtractivo>();
	private static ResponseEntity<List<TipoAtractivo>> listRespTipoAtractivo;
	ObservableList<TipoAtractivo> obsListTipoAtractivo = FXCollections.observableArrayList();
	
	public void initialize() {
		gcsw.showMediaInContenedor(new Image("albums.png",250,500,true,false), contenedorDeMedios, (double) 288);
		gcsw.showMediaInContenedor(new Image("albums.png",250,500,true,false), contenedorDeAtractivos, (double) 288);
		
		listasCellFactory();
		loadTipoMedios();
		loadTipoAtractivo();
		buscarPorNombre();
				
		if (Context.getInstance().getSenderoContext() != null) {
			sendero = Context.getInstance().getSenderoContext();
			cargarDatosSendero(sendero);
			isModificar = true;
			loadMedios();
			loadAtractivos();
		}
		
	}

    @FXML
    void atras(ActionEvent event) {
    	try {
    		Context.getInstance().setSenderoContext(null);
    		Context.getInstance().getStageModalBaseSendero().close();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

    @FXML
    void guardar(ActionEvent event) {
    	try {
    		// validaciones respectivas
    		if (txt_nombreSendero.getText().isEmpty() || txt_nombreSendero.getText().isBlank()) {
    			Message.showWarningNotification("Debe ingresar un nombre al sendero.!!");
    			return;
    		}
    		
    		sendero.setNombre(txt_nombreSendero.getText());
    		if (!txt_distanciaSendero.getText().isBlank() || !txt_distanciaSendero.getText().isEmpty()) {
        		sendero.setTiempoRecorrido(Float.parseFloat(txt_tiempoSendero.getText()));
    		}            	
    		sendero.setDescripcion(txt_descripcionSendero.getText());
    		sendero.setInstrucciones(txt_instruccionesSendero.getText());
    		if (!txt_distanciaSendero.getText().isBlank() || !txt_distanciaSendero.getText().isEmpty()) {
        		sendero.setDistanciaAproximada(Float.parseFloat(txt_distanciaSendero.getText()));
			}
    		if (cmb_dificultadSendero.getSelectionModel().getSelectedItem() != null) {
				sendero.setIdDificultadRecorrido(cmb_dificultadSendero.getValue().getId());
			}
    		if (cmb_disponibilidadSendero.getSelectionModel().getSelectedItem() != null ) {
				sendero.setIdSenalCelular(cmb_disponibilidadSendero.getValue().getId());
			}    		
    		//guardando recurso con Contenido Media
    		ArrayList<String> auxIdsMedia = new ArrayList<>();
    		if (listaMediaTemporal.size() > 0) {
    			if(sendero.getIdsMediaCloudResources() != null)sendero.getIdsMediaCloudResources().clear();
				String url = "";
				System.out.println("Lista Media Temporal: " + listaMediaTemporal);
				for (MediaCloudResources mcr : listaMediaTemporal) {
					if (mcr.getFileTemporal() != null) {
						if(mcr.getId() != null) {
							gcsw.updateMediaCR(mcr.getId(), gcsw.fileToByteArray(mcr.getFileTemporal()));
						}else {
							media = rest.postForObject(uriMediaCloud + "/saveOrUpdate", mcr, MediaCloudResources.class);
							System.out.println("Guardando contenido multimedia en GOOGLE CLOUD.");
							if (mcr.getExtensionFile().equals(".mp4") || mcr.getExtensionFile().equals(".wt3")) {
								url = gcsw.saveMediaCR(media.getId(), gcsw.fileToByteArray(mcr.getFileTemporal()));
							}else {
								BufferedImage bufferedImage = ImageIO.read(mcr.getFileTemporal());
								bufferedImage = gcsw.redimensionarImagen(bufferedImage);
								url = gcsw.saveMediaCR(media.getId(), General.converterImageToByteArray(bufferedImage));						
							}			
							media.setUrlAlmacenamiento(url);
							mcr = media;
						}
					}
					System.out.println("MEDIO A GUARDAR: " + mcr);
					media = rest.postForObject(uriMediaCloud + "/saveOrUpdate", mcr, MediaCloudResources.class);
					auxIdsMedia.add(media.getId());
				}    		
    		}
			sendero.setIdsMediaCloudResources(auxIdsMedia);
    		//guardando recurso con Contenido Media+++FIN        		

			//guardando el sendero
			sendero.setEstado(true);
			sendero = rest.postForObject(uriSendero + "/saveOrUpdate", sendero, Sendero.class);
    		//guardando el sendero fin
    		
			//guardando recurso con atractivos
    		if (lst_listaAtractivos.getItems().size() > 0) {
				for (Atractivo mcr : lst_listaAtractivos.getItems()) {
					if (mcr.getImagenes().size() > 0) {
						for (MediaCloudResources s : mcr.getImagenes()) {
//							String url = "";
							if (s.getFileTemporal() != null) {
								BufferedImage bufferedImage = ImageIO.read(s.getFileTemporal());
								bufferedImage = gcsw.redimensionarImagen(bufferedImage);
								gcsw.saveMediaCR(s.getNombre().concat(s.getCoordenadas()), General.converterImageToByteArray(bufferedImage));						
							}
							
						}
					}
					
					mcr.setEstado(true);
					mcr.setIdSendero(sendero.getId());
					rest.postForObject(uriAtractivo + "/saveOrUpdate", mcr, Atractivo.class);
				}    		
    		}    		
    		//guardando recurso con atractivos FIN
    		

    		
    		Context.getInstance().setSenderoContext(sendero);
			Message.showSuccessNotification("Se guardaron exitosamente los datos.!! ");
			Context.getInstance().getStageModalBaseSendero().close();
			
			
		} catch (Exception e) {
			e.printStackTrace();
			Message.showErrorNotification("Ha surgido un error al guardar datos.!!");			
		}
    }
    
    @FXML
    void addNuevaMedia(ActionEvent event) {
    	try {
    		//abro interfaz para crear un medio.
    		General.showModalWithParent("/viewRecurso/PopoverMediaCloud.fxml");
    		//obtengo el medio recien creado sin ID    		
    		if (Context.getInstance().getMediaContext() != null) {
//        		lst_listaMedios.getItems().add(Context.getInstance().getMediaContext());
    			listaMediaTemporal.add(Context.getInstance().getMediaContext());
    			exitPopup = true;
    			//cargar datos de lista al listview
    			cargarListaMedios(listaMediaTemporal);    			
			}
    		Context.getInstance().setMediaContext(null);
    		exitPopup = false;
    		System.out.println("¨********************* SALIO DEL POPUP *****************");
		} catch (Exception e) {
			e.printStackTrace();
			Message.showErrorNotification("Ha surgido un error !! ");
		}
    }

    @FXML
    void addNuevoAtractivo(ActionEvent event) {
    	try {
    		//abro interfaz para crear un costo
    		Context.getInstance().setAtractivoContext(null);
    		General.showModalWithParentAtractivo("/viewRecurso/ModalAtractivo.fxml");
    		if (Context.getInstance().getAtractivoContext() != null) {
    			listaAtractivoTemporal.add(Context.getInstance().getAtractivoContext());
    			cargarListaAtractivos(listaAtractivoTemporal);
    		}
    		Context.getInstance().setAtractivoContext(null);
		} catch (Exception e) {
			e.printStackTrace();
			Message.showErrorNotification("Surgió un error al agregar atractivos.!!");
		}
    }

    @FXML
    void eliminarAtractivo(ActionEvent event) {
    	try {
    		if (lst_listaAtractivos.getSelectionModel().getSelectedItems().isEmpty()) {
    			Message.showWarningNotification("Seleccione el atractivo a eliminar.!!");
    			return;
    		}
    		Optional<ButtonType> result = Message.showQuestion("Desea continuar y eliminar los datos del atractivo: "
    				+ lst_listaAtractivos.getSelectionModel().getSelectedItem().getDescripcion() + " ?.",
    				Context.getInstance().getStage());
    		if (result.get() == ButtonType.OK) {
    			atractivo = lst_listaAtractivos.getSelectionModel().getSelectedItem();
    			if (lst_listaAtractivos.getSelectionModel().getSelectedItem().getId() != null) {
    				Map<String, String> params = new HashMap<String, String>();
    				params.put("c", lst_listaAtractivos.getSelectionModel().getSelectedItem().getId());
    				rest.delete(uriAtractivo + "/delete/{c}", params);
    				Message.showSuccessNotification("Se eliminaron exitosamente los datos.!!");
    			}
    			//				lst_listaComodidadesRecurso.getItems().remove(lst_listaComodidadesRecurso.getSelectionModel().getSelectedItem());
    			if (listaAtractivoTemporal.size() > 0) 	
    				listaAtractivoTemporal.remove(atractivo);
    			if (lst_listaAtractivos.getItems().size() > 0)	{
    				lst_listaAtractivos.getItems().remove(atractivo);
    			}
    		}
    	}catch (Exception e) {
    		e.printStackTrace();
    		Message.showErrorNotification("Ha surgido un error al eliminar datos.!!");
    	}
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
				if (lst_listaMedios.getSelectionModel().getSelectedItem().getId() != null) {
					//eliminar de la base de datos.
					Map<String, String> params = new HashMap<String, String>();
					params.put("c", media.getId());
					rest.delete(uriMediaCloud + "/delete/{c}", params);
				}//eliminar de lista temporal.
				if (listaMediaTemporal.size() > 0) 	
					listaMediaTemporal.remove(media);
				if (lst_listaMedios.getItems().size() > 0)	{
					exitPopup = true;
					lst_listaMedios.getItems().remove(media);
					exitPopup = false;
				}
				Message.showSuccessNotification("Se eliminaron exitosamente los datos.!!");				
			}
		} catch (Exception e) {
			e.printStackTrace();
			Message.showErrorNotification("Ha surgido un error al eliminar datos.!!");
		}
    }

    @FXML
    void modificarAtractivo(ActionEvent event) {
    	try {
    		if (lst_listaAtractivos.getSelectionModel().getSelectedItems().isEmpty()) {
				Message.showWarningNotification("Seleccione el atractivo a modificar.!!");
				return;
			}
    		Context.getInstance().setAtractivoContext(lst_listaAtractivos.getSelectionModel().getSelectedItem());
    		General.showModalWithParentAtractivo("/viewRecurso/ModalAtractivo.fxml");
    		if (Context.getInstance().getAtractivoContext() != null) {
    			atractivo = Context.getInstance().getAtractivoContext();

    			cargarListaAtractivos(listaAtractivoTemporal);
			}
    		Context.getInstance().setAtractivoContext(null);     		
		} catch (Exception e) {
			e.printStackTrace();
			Message.showErrorNotification("Ha surgido un error al modificar datos.!!");
		}
    }

    @FXML
    void modificarMedio(ActionEvent event) {
    	try {
    		if (lst_listaMedios.getSelectionModel().getSelectedItems().isEmpty()) {
    			Message.showWarningNotification("Seleccione el medio a modificar.!!");
    			return;
    		}
    		Context.getInstance().setMediaContext(media);
    		General.showModalWithParent("/viewRecurso/PopoverMediaCloud.fxml");
    		//obtengo el medio modificado    		
    		if (Context.getInstance().getMediaContext() != null) {
//        		lst_listaMedios.getItems().add(Context.getInstance().getMediaContext());
    			media = Context.getInstance().getMediaContext();
    			exitPopup = true;
    			//cargar datos de lista al listview
    			cargarListaMedios(listaMediaTemporal);    	
    			lst_listaMedios.getSelectionModel().clearSelection();
			}
    		Context.getInstance().setMediaContext(null);
    		exitPopup = false;
    		System.out.println("¨********************* SALIO DEL POPUP *****************");
	
    		
//    		Message.showSuccessNotification("Se modificaron exitosamente los datos.!!");	
    	} catch (Exception e) {
    		e.printStackTrace();
			Message.showErrorNotification("Ha surgido un error al modificar datos.!!");
		}
    }

    @FXML
    void showAtractivoMapa(ActionEvent event) {

    }

    @FXML
    void showMediaMapa(ActionEvent event) {

    }
    

    void buscarPorNombre() {
		txt_buscarNombreMedio.textProperty().addListener((observable, oldValue, newValue) -> {
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
    	    if (newValue.isBlank() || newValue.isEmpty()) {
				cargarListaMedios(listaMediaTemporal);
				obsListTipoMedia.clear();
				loadTipoMedios();
				
			}else {
	    	    List<MediaCloudResources> auxList = new ArrayList<>();
				auxList.addAll(obsAux);
				exitPopup = true;
				lst_listaMedios.getItems().clear();
				cargarListaMedios(auxList);
				exitPopup = false;
			}
    	});
		txt_buscarNombreAtractivos.textProperty().addListener((observable, oldValue, newValue) -> {
    	    System.out.println("textfield changed from " + oldValue + " to " + newValue);    	    
    	    ObservableList<Atractivo> obsAuxAtrac = FXCollections.observableArrayList();			
    	    for (Atractivo mcr : lst_listaAtractivos.getItems()) {    	    	
    	    	String nombre = mcr.getNombre();
    	    	if (newValue.length() >0) {
    	    		for (int i = 0; i < newValue.length(); i++) {
    	    			if (nombre.trim().toLowerCase().charAt(i) == newValue.trim().toLowerCase().charAt(i)) {
    	    				if (!obsAuxAtrac.isEmpty()) {
    	    					if (!obsAuxAtrac.contains(mcr)) {
    	    						obsAuxAtrac.add(mcr);
    	    					}
    	    				}else {
    	    					obsAuxAtrac.add(mcr);
    	    				}
    	    			} 
    				}
				}
			}
    	    if (newValue.isBlank() || newValue.isEmpty()) {
				cargarListaAtractivos(listaAtractivoTemporal);				
			}else {
				List<Atractivo> auxList = new ArrayList<>();
				auxList.addAll(obsAuxAtrac);
				lst_listaAtractivos.getItems().clear();
				cargarListaAtractivos(auxList);
			}
    	});
    }    
        
  	private void listasCellFactory() {
//  		lst_listaCostosRecurso.setCellFactory(param -> new ListCell<Costo>() {
//      		protected void updateItem(Costo item, boolean empty) {
//      			super.updateItem(item, empty);
//      			setText(empty ? "" : item.getValor().toString().concat(" - ").concat(item.getDescripcion()) );
//      		};
//      	});  

		lst_listaMedios.setCellFactory(param -> new ListCell<MediaCloudResources>() {
    		protected void updateItem(MediaCloudResources item, boolean empty) {
    			super.updateItem(item, empty);
    			setText(empty ? "" : item.getNombre() );
    		};
    	});  	
		
		cmb_tipoMediaBusqueda.setCellFactory(param -> new ListCell<TipoMedia>() {
    		protected void updateItem(TipoMedia item, boolean empty) {
    			super.updateItem(item, empty);
    			setText(empty ? "" : item.getDescripcion());
    		};
    	}); 

		cmb_tipoAtractivoBusqueda.setCellFactory(param -> new ListCell<TipoAtractivo>() {
			protected void updateItem(TipoAtractivo item, boolean empty) {
				super.updateItem(item, empty);
				setText(empty ? "" : item.getDescripcion());
			};
		});   
		lst_listaAtractivos.setCellFactory(param -> new ListCell<Atractivo>() {
    		protected void updateItem(Atractivo item, boolean empty) {
    			super.updateItem(item, empty);
    			setText(empty ? "" : item.getNombre() );
    		};
    	});  
		lst_listaAtractivos.getSelectionModel().selectedItemProperty()
		.addListener((ObservableValue<? extends Atractivo> ov, Atractivo old_val, Atractivo new_val) -> {
			if (lst_listaAtractivos.getSelectionModel().getSelectedItem() != null) {
				atractivo = lst_listaAtractivos.getSelectionModel().getSelectedItem();
				if (atractivo.getImagenes() != null) {
					for (MediaCloudResources mcr : atractivo.getImagenes()) {
						if (mcr.getFileTemporal() != null) {
							if (mcr.getIsPrincipal()) {
								 Image image = new Image("file:" + mcr.getFileTemporal().getAbsolutePath());
							     gcsw.showMediaInContenedor(image, contenedorDeAtractivos, (double) 288);
							}						    
						}else {
							if (mcr.getIsPrincipal()) {								
								Image img = gcsw.getImageMediaCR(mcr.getNombre().concat(mcr.getCoordenadas()));
								gcsw.showMediaInContenedor(img, contenedorDeAtractivos, (double) 288);	
								return;
							}
						}
						
					}
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
 			TipoMedia auxTipo = new TipoMedia("", "Todos los medios");
 			obsListTipoMedia.add(auxTipo);
 			for (int i = 0; i < listaTipoMedia.size(); i++) {
 				obsListTipoMedia.add(listaTipoMedia.get(i));
 			}			
 			cmb_tipoMediaBusqueda.setItems(obsListTipoMedia);	
 			cmb_tipoMediaBusqueda.getSelectionModel().select(auxTipo);
 	    	
 	    	
 	    	cmb_tipoMediaBusqueda.getSelectionModel().selectedItemProperty()
 			.addListener((ObservableValue<? extends TipoMedia> ov, TipoMedia old_val, TipoMedia new_val) -> {
 				if (cmb_tipoMediaBusqueda.getSelectionModel().getSelectedItem() != null) {
 					tipoMedia = cmb_tipoMediaBusqueda.getSelectionModel().getSelectedItem();
 					ObservableList<MediaCloudResources> obsAux = FXCollections.observableArrayList();
 					//utilizar solo valores del obstemporal					
 					if (!listaMediaTemporal.isEmpty()) {
 						System.out.println("TIPO MEDIA SELECT: " + tipoMedia);
 						if (tipoMedia.getDescripcion().equals(auxTipo.getDescripcion())) {
 							//muestra todos los valores de la lista temporal que existen para el recurso
 							for (int i = 0; i < listaMediaTemporal.size(); i++) {
 								obsAux.add(listaMediaTemporal.get(i));
 							}	
 						}else {
 							//mostrar lista de MEDIOS por el tipo.
 							for (MediaCloudResources mcr : listaMediaTemporal) {						
 								if (mcr.getTipoMedia().getDescripcion().equals(tipoMedia.getDescripcion())) {
 									obsAux.add(mcr);									
 								}
 							}
 						}
 						List<MediaCloudResources> auxList = new ArrayList<>();
 						auxList.addAll(obsAux);
 						exitPopup = true;
 						lst_listaMedios.getItems().clear();
 						cargarListaMedios(auxList);
 						exitPopup = false;
// 						lst_listaMedios.getItems().addAll(obsAux);
 					}
 				}
 			});
 		}				
 	}
  	 
  	private void loadTipoAtractivo() {
		listRespTipoAtractivo = rest.exchange(uriTipoAtractivo + "/getAll", HttpMethod.GET, null,
				new ParameterizedTypeReference<List<TipoAtractivo>>() {
		});
		listaTipoAtractivo = listRespTipoAtractivo.getBody();
		if (!listaTipoAtractivo.isEmpty()) {
			TipoAtractivo auxtipo = new TipoAtractivo("", "Todos los tipos");
			obsListTipoAtractivo.add(auxtipo);
			for (int i = 0; i < listaTipoAtractivo.size(); i++) {
				obsListTipoAtractivo.add(listaTipoAtractivo.get(i));
			}			
			cmb_tipoAtractivoBusqueda.setItems(obsListTipoAtractivo);	
			cmb_tipoAtractivoBusqueda.getSelectionModel().select(auxtipo);
			
			cmb_tipoAtractivoBusqueda.getSelectionModel().selectedItemProperty()
			.addListener((ObservableValue<? extends TipoAtractivo> ov, TipoAtractivo old_val, TipoAtractivo new_val) -> {
				if (cmb_tipoAtractivoBusqueda.getSelectionModel().getSelectedItem() != null) {
					tipoAtractivo = cmb_tipoAtractivoBusqueda.getSelectionModel().getSelectedItem();
					
					ObservableList<Atractivo> obsAux = FXCollections.observableArrayList();
					//utilizar solo valores del obstemporal					
					if (!listaAtractivoTemporal.isEmpty()) {
						if (tipoAtractivo.getDescripcion().equals(auxtipo.getDescripcion())) {
							//muestra todos los valores de la lista temporal que existen para el recurso
							for (int i = 0; i < listaAtractivoTemporal.size(); i++) {
								obsAux.add(listaAtractivoTemporal.get(i));
							}	
						}else {
							//mostrar lista de MEDIOS por el tipo.
							for (Atractivo mcr : listaAtractivoTemporal) {						
								if (mcr.getIdTipoAtractivo().equals(tipoAtractivo.getId())) {
									obsAux.add(mcr);
								}
							}
						}
						List<Atractivo> auxList = new ArrayList<>();
						auxList.addAll(obsAux);
						lst_listaAtractivos.getItems().clear();
						cargarListaAtractivos(auxList);
					}
				}
			});
		}				
	}

	private void cargarListaAtractivos(List<Atractivo> lista) {
    	ObservableList<Atractivo> obsComo = FXCollections.observableArrayList();
		lst_listaAtractivos.setPlaceholder(new Label("---  No se encontraron datos en la Base. ---"));
		if (!lista.isEmpty()) {
			for (int i = 0; i < lista.size(); i++) {				
				obsComo.add(lista.get(i));
			}			
			lst_listaAtractivos.setItems(obsComo);
		}
	}

    private void cargarListaMedios(List<MediaCloudResources> lista) {
    	obsListMedia = FXCollections.observableArrayList();
    	lst_listaMedios.setPlaceholder(new Label("---  La lista de medios se encuentra vacia. ---"));
		if (!lista.isEmpty()) {
    		for (int i = 0; i < lista.size(); i++) {
				obsListMedia.add(lista.get(i));
			}			
			lst_listaMedios.setItems(obsListMedia);	
    		//
	    			    	
		}
		//
		lst_listaMedios.getSelectionModel().selectedItemProperty()
				.addListener((ObservableValue<? extends MediaCloudResources> ov, MediaCloudResources old_val, MediaCloudResources new_val) -> {
					media = lst_listaMedios.getSelectionModel().getSelectedItem();
					gcsw.showMediaInContenedor(new Image("albums.png",250,500,true,false), contenedorDeMedios, (double) 288);
					
					if (exitPopup) {
						return;
					}
					if (media.getId() != null) {
						//traer la imagen con la url y presentar en el ImageView.
						if (media.getTipoMedia() != null) {
							if (media.getTipoMedia().getDescripcion().equals("Imagen")) {
								Image img = gcsw.getImageMediaCR(media.getId());
								gcsw.showMediaInContenedor(img, contenedorDeMedios, (double) 288);	
							}else if(media.getTipoMedia().getDescripcion().equals("Video")) {
								Media video = gcsw.getMediaFromMediaCR(media.getId());
						        gcsw.showMediaInContenedor(video, contenedorDeMedios);	
							}else {
								//CONTENIDO 3D
							}	
						}
					}else {//cargar con el file
						if (media.getTipoMedia().getDescripcion().equals("Imagen")) {
						    if (media.getFileTemporal() != null) {
						        Image image = new Image("file:" + media.getFileTemporal().getAbsolutePath());
						        gcsw.showMediaInContenedor(image, contenedorDeMedios, (double) 288);
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
				});
    }
  	
    private void cargarDatosSendero(Sendero sendero) {
    	txt_descripcionSendero.setText(sendero.getDescripcion());
    	if (sendero.getDistanciaAproximada() != null) {
        	txt_distanciaSendero.setText(sendero.getDistanciaAproximada().toString());
		}
    	txt_instruccionesSendero.setText(sendero.getInstrucciones());
    	txt_nombreSendero.setText(sendero.getNombre());
    	if (sendero.getTiempoRecorrido() != null) {
        	txt_tiempoSendero.setText(sendero.getTiempoRecorrido().toString());
		}
    	
//    	cmb_dificultadSendero.getSelectionModel().sele
    	
    	
    	
    }    

	private void loadMedios() {
		if (isModificar) {
			if(!obsListMedia.isEmpty())	obsListMedia.clear();
			listRespMedia = rest.exchange(uriMediaCloud + "/getAll", HttpMethod.GET, null,
					new ParameterizedTypeReference<List<MediaCloudResources>>() {
					});
			listaMedia = listRespMedia.getBody();
			if (!listaMedia.isEmpty()) {//todos los medios de todos los recursos
				if (!sendero.getIdsMediaCloudResources().isEmpty()) {
					//filtramos los medios que pertenecen a este recurso
					for (int i = 0; i < listaMedia.size(); i++) {
						if (sendero.getIdsMediaCloudResources().contains(listaMedia.get(i).getId())) {
							if (!listaMediaTemporal.contains(listaMedia.get(i))) {
								listaMediaTemporal.add(listaMedia.get(i));
							}		
						}					
					}
				}
			}
		}
		cargarListaMedios(listaMediaTemporal);	
	}	

	private void loadAtractivos() {
		obsListAtractivo.clear();
		listRespAtractivo = rest.exchange(uriAtractivo + "/getAll", HttpMethod.GET, null,
				new ParameterizedTypeReference<List<Atractivo>>() {
				});
		listaAtractivo = listRespAtractivo.getBody();
		//filtrar por recurso id
		if (!listaAtractivo.isEmpty()) {
			if (sendero.getId() != null) {
				for (Atractivo act : listaAtractivo) {
					if (act.getIdSendero() != null) {
						if(act.getIdSendero().equals(sendero.getId())) {
							listaAtractivoTemporal.add(act);
						}
					}
				}
			}
		}
		cargarListaAtractivos(listaAtractivoTemporal);
	}
    

}
