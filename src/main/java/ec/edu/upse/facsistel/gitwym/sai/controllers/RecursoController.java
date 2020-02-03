package ec.edu.upse.facsistel.gitwym.sai.controllers;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.imageio.ImageIO;

import org.controlsfx.control.CheckListView;
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
import ec.edu.upse.facsistel.gitwym.sai.models.Accesibilidad;
import ec.edu.upse.facsistel.gitwym.sai.models.Atractivo;
import ec.edu.upse.facsistel.gitwym.sai.models.Canton;
import ec.edu.upse.facsistel.gitwym.sai.models.Categoria;
import ec.edu.upse.facsistel.gitwym.sai.models.Comodidades;
import ec.edu.upse.facsistel.gitwym.sai.models.Contacto;
import ec.edu.upse.facsistel.gitwym.sai.models.Costo;
import ec.edu.upse.facsistel.gitwym.sai.models.Idiomas;
import ec.edu.upse.facsistel.gitwym.sai.models.MediaCloudResources;
import ec.edu.upse.facsistel.gitwym.sai.models.Parroquia;
import ec.edu.upse.facsistel.gitwym.sai.models.Provincia;
import ec.edu.upse.facsistel.gitwym.sai.models.Recurso;
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
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;

public class RecursoController {

    @FXML private AnchorPane anch_recurso;
    @FXML private JFXButton btn_guardar;
    @FXML private JFXButton btn_atras;
    @FXML private JFXComboBox<Provincia> cmb_provinciaRecurso;
    @FXML private JFXComboBox<Canton> cmb_cantonRecurso;
    @FXML private JFXComboBox<Parroquia> cmb_parroquiaRecurso;
    @FXML private JFXTextField txt_nombreRecurso;
    @FXML private JFXTextArea txt_descripcionRecurso;
    @FXML private JFXTextArea txt_infGeneralRecurso;
    @FXML private JFXTextField txt_coordenadasRecurso;
    @FXML private JFXButton btn_buscarCoordRecurso;
    @FXML private JFXTextField txt_direccionRecurso;
    @FXML private JFXTextField txt_propietarioRecurso;
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
    @FXML private TitledPane accd_costosRecurso;
    @FXML private JFXButton btn_addCosto;
    @FXML private JFXButton btn_eliminarCosto;
    @FXML private JFXButton btn_modificarCosto;
    @FXML private JFXListView<Costo> lst_listaCostosRecurso;
    @FXML private TitledPane accd_ContactosRecurso;
    @FXML private JFXButton btn_addContacto;
    @FXML private JFXButton btn_eliminarContacto;
    @FXML private JFXButton btn_editarContacto;
    @FXML private JFXListView<Contacto> lst_listaContactosRecurso;
    @FXML private JFXButton btn_verComodidadMapa;
    @FXML private JFXTextField txt_buscarComodidadesRecurso;
    @FXML private JFXButton btn_addComodidades;
    @FXML private JFXButton btn_eliminarComodidades;
    @FXML private JFXButton btn_modificarComodidades;
    @FXML private JFXListView<Comodidades> lst_listaComodidadesRecurso;
    @FXML private TitledPane accd_accesibilidadesRecurso;
    @FXML private CheckListView<Accesibilidad> chklst_accesibilidadesRecurso;
    @FXML private TitledPane accd_categoriasRecurso;
    @FXML private CheckListView<Categoria> chklst_categoriasRecurso;
    @FXML private TitledPane acc_idiomasRecurso;
    @FXML private CheckListView<Idiomas> chklst_idiomasRecurso;
    @FXML private AnchorPane anch_comentarios1;
    @FXML private JFXComboBox<TipoAtractivo> cmb_tipoAtractivoBusqueda;
    @FXML private JFXButton btn_verAtractivoMapa;
    @FXML private JFXListView<Atractivo> lst_listaAtractivos;
    @FXML private JFXTextField txt_buscarNombreAtractivos;
    @FXML private JFXButton btn_nuevoAtractivo;
    @FXML private JFXButton btn_EliminarAtractivo;
    @FXML private JFXButton btn_modificarAtractivo;
    @FXML private AnchorPane contenedorDeAtractivos;
    @FXML private AnchorPane anch_senderos;
    @FXML private AnchorPane anch_comentarios;
	
    
	// CONSUMIR WEB SERVICES
	RestTemplate rest = new RestTemplate();
	String urlBase = PropertyManager.getBaseUrl();
	String uriRecurso = urlBase + "/recurso";
	String uriMediaCloud = urlBase + "/mediaCloudResources";
 	String uriTipoMedia = urlBase + "/tipoMedia";

	// DE LA CLASE RECURSO
	Recurso recurso = new Recurso();
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
 	

	public void initialize() {	
		gcsw.showMediaInContenedor(new Image("albums.png",250,500,true,false), contenedorDeMedios);
		//cargar provincia, canton y parroquia
		
		//cargar Media Cloud
		loadTipoMedios();
		buscarPorNombre();
		
		if (Context.getInstance().getRecursoContext() != null) {
			recurso = Context.getInstance().getRecursoContext();
			cargarDatosRecurso(recurso);
			isModificar = true;
			loadMedios();	
		}
		
	}    

	@FXML
    void atras(ActionEvent event) {
		try {
    		Context.getInstance().setRecursoContext(null);
    		General.setContentParent("/viewPrincipal/RecursoPrincipal.fxml", Context.getInstance().getAnch_Contenido());
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

    @FXML
    void guardarRecurso(ActionEvent event) {
    	try {
    		// validaciones respectivas
    		if (txt_nombreRecurso.getText().isEmpty() || txt_nombreRecurso.getText().isBlank()) {
    			Message.showWarningNotification("Debe ingresar un nombre al recurso.!!");
    			return;
    		}
    		if (txt_coordenadasRecurso.getText().isEmpty() || txt_coordenadasRecurso.getText().isBlank()) {
    			Message.showWarningNotification("Debe agregar las coordenadas del recurso.!!");
    			return;
    		}
    		//guardando recurso simple
    		recurso.setNombre(txt_nombreRecurso.getText());
    		recurso.setPropietario(txt_propietarioRecurso.getText());
    		recurso.setCoordenadas(txt_coordenadasRecurso.getText());
    		recurso.setDescripcion(txt_descripcionRecurso.getText());
    		recurso.setInformacionGeneral(txt_infGeneralRecurso.getText());
    		recurso.setDireccion(txt_direccionRecurso.getText());
    		recurso.setEstado(true);    		
    		if (cmb_parroquiaRecurso.getSelectionModel().getSelectedItem() != null) {
				recurso.setIdLocalizacion(cmb_parroquiaRecurso.getSelectionModel().getSelectedItem().getId());
			} else if(cmb_cantonRecurso.getSelectionModel().getSelectedItem() != null){
				recurso.setIdLocalizacion(cmb_cantonRecurso.getSelectionModel().getSelectedItem().getId());
			}else if(cmb_provinciaRecurso.getSelectionModel().getSelectedItem() != null){				
				recurso.setIdLocalizacion(cmb_provinciaRecurso.getSelectionModel().getSelectedItem().getId());
			}
    		//guardando recurso con Contenido Media
    		ArrayList<String> auxIdsMedia = new ArrayList<>();
    		if (listaMediaTemporal.size() > 0) {
    			if(recurso.getIdsMediaCloudResources() != null)recurso.getIdsMediaCloudResources().clear();
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
			recurso.setIdsMediaCloudResources(auxIdsMedia);
    		//guardando recurso con Contenido Media+++FIN    		
    		
			
			
    		//Guardamos el recurso
    		rest.postForObject(uriRecurso + "/saveOrUpdate", recurso, Recurso.class);
    		Context.getInstance().setRecursoContext(recurso);
			Message.showSuccessNotification("Se guardaron exitosamente los datos.!! ");
			General.setContentParent("/viewPrincipal/RecursoPrincipal.fxml", Context.getInstance().getAnch_Contenido());
			
		} catch (Exception e) {
			e.printStackTrace();
			Message.showErrorNotification("Ha surgido un error al guardar datos.!!");			
		}
    }

    @FXML
    void addComodidadesRecurso(ActionEvent event) {

    }

    @FXML
    void addContactoRecurso(ActionEvent event) {

    }

    @FXML
    void addCostoRecurso(ActionEvent event) {

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

    }

    @FXML
    void buscarComodidadesTextChange(InputMethodEvent event) {

    }

    @FXML
    void buscarRecursoEnMapa(ActionEvent event) {

    }

    @FXML
    void eliminarAtractivo(ActionEvent event) {

    }

    @FXML
    void eliminarComodidadesRecurso(ActionEvent event) {

    }

    @FXML
    void eliminarContactoRecurso(ActionEvent event) {

    }

    @FXML
    void eliminarCostoRecurso(ActionEvent event) {

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

    }

    @FXML
    void modificarComodidadesRecurso(ActionEvent event) {

    }

    @FXML
    void modificarContactoRecurso(ActionEvent event) {

    }

    @FXML
    void modificarCostoRecurso(ActionEvent event) {

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
			}
    		Context.getInstance().setMediaContext(null);
    		exitPopup = false;
    		System.out.println("¨********************* SALIO DEL POPUP *****************");
	
    		
    		Message.showSuccessNotification("Se modificaron exitosamente los datos.!!");	
    	} catch (Exception e) {
    		e.printStackTrace();
			Message.showErrorNotification("Ha surgido un error al modificar datos.!!");
		}
    }

    @FXML
    void showAtractivoMapa(ActionEvent event) {

    }

    @FXML
    void showComodidadMapa(ActionEvent event) {

    }

    @FXML
    void showMediaMapa(ActionEvent event) {

    }

    /*
     * Empiezan los metodos  
     */
    
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
    	    
    	    //enviamos la lista.
//    	    lst_listaMedios.setItems(obsAux);
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
    }    

	private void loadMedios() {
		if (isModificar) {
			if(!obsListMedia.isEmpty())	obsListMedia.clear();
			listRespMedia = rest.exchange(uriMediaCloud + "/getAll", HttpMethod.GET, null,
					new ParameterizedTypeReference<List<MediaCloudResources>>() {
					});
			listaMedia = listRespMedia.getBody();
			if (!listaMedia.isEmpty()) {//todos los medios de todos los recursos
				if (!recurso.getIdsMediaCloudResources().isEmpty()) {
					//filtramos los medios que pertenecen a este recurso
					for (int i = 0; i < listaMedia.size(); i++) {
						if (recurso.getIdsMediaCloudResources().contains(listaMedia.get(i).getId())) {
							if (!listaMediaTemporal.contains(listaMedia.get(i))) {
								listaMediaTemporal.add(listaMedia.get(i));
							}		
						}					
					}
//					obsListMedia.addAll(listaMediaTemporal);
//					lst_listaMedios.setItems(obsListMedia);	
				}					    	
			}
		}//else {//es nuevo recurso la lista de medios esta vacia.
			//al crear un media se agrega a la lista temporal.
			cargarListaMedios(listaMediaTemporal);
//		}		
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
//						lst_listaMedios.getItems().addAll(obsAux);
					}
				}
			});
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
					media = lst_listaMedios.getSelectionModel().getSelectedItem();
					gcsw.showMediaInContenedor(new Image("albums.png",250,500,true,false), contenedorDeMedios);
					
					if (exitPopup) {
						return;
					}
					if (media.getId() != null) {
						//traer la imagen con la url y presentar en el ImageView.
						if (media.getTipoMedia() != null) {
							if (media.getTipoMedia().getDescripcion().equals("Imagen")) {
								Image img = gcsw.getImageMediaCR(media.getId());
								gcsw.showMediaInContenedor(img, contenedorDeMedios);	
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
				});
    }
    
    private void cargarDatosRecurso(Recurso recurso) {
    	//carga todos los datos del recurso.
    	//recurso
    	txt_nombreRecurso.setText(recurso.getNombre());
    	txt_propietarioRecurso.setText(recurso.getPropietario());
    	txt_coordenadasRecurso.setText(recurso.getCoordenadas());
    	txt_descripcionRecurso.setText(recurso.getDescripcion());
    	txt_infGeneralRecurso.setText(recurso.getInformacionGeneral());
    	txt_direccionRecurso.setText(recurso.getDireccion());
    	if (!recurso.getIdLocalizacion().isBlank() || !recurso.getIdLocalizacion().isEmpty()) {
			//seleccionar los combos de localizacion segun recurso
		}
    	//llenar ranking
    	//llenar seguridad
    	
    	
    	
	}
    
}
