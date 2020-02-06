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
import javafx.scene.control.Accordion;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.TitledPane;
import javafx.scene.control.cell.CheckBoxListCell;
import javafx.scene.image.Image;
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
    @FXML private JFXListView<Sendero> lst_listaSenderos;
    @FXML private JFXButton btn_nuevoSendero;
    @FXML private JFXButton btn_EliminarSendero;
    @FXML private JFXButton btn_modificarSendero;
    @FXML private AnchorPane contenedorDeSenderos;
    @FXML private JFXTextArea txt_descripcionSendero;
    @FXML private JFXTextArea txt_instruccionesSendero;
    @FXML private JFXTextField txt_distanciaAproxSendero;
    @FXML private JFXTextField txt_tiempoRecorridoSendero;
    @FXML private AnchorPane anch_comentarios;
    @FXML private Accordion acco_Izq;
    @FXML private Accordion acco_Der;
	
    
	// CONSUMIR WEB SERVICES
	RestTemplate rest = new RestTemplate();
	String urlBase = PropertyManager.getBaseUrl();
	String uriRecurso = urlBase + "/recurso";
	String uriMediaCloud = urlBase + "/mediaCloudResources";
 	String uriTipoMedia = urlBase + "/tipoMedia";
 	String uriCosto = urlBase + "/costo";
 	String uriContacto = urlBase + "/contacto";
 	String uriAccesibilidad = urlBase + "/accesibilidad";
 	String uriCategoria = urlBase + "/categoria";
 	String uriIdiomas = urlBase + "/idiomas";	
 	String uriComodidades = urlBase + "/comodidades";
	String uriTipoAtractivo = urlBase + "/tipoAtractivo";
	String uriAtractivo = urlBase + "/atractivo";
	String uriSendero = urlBase + "/sendero";

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
    
	// DE LA CLASE MEDIA CLOUD senderos
	MediaCloudResources mediaSenderos = new MediaCloudResources();
	List<MediaCloudResources> listaMediaSenderos = new ArrayList<MediaCloudResources>();
	List<MediaCloudResources> listaMediaTemporalSenderos = new ArrayList<MediaCloudResources>();
	private static ResponseEntity<List<MediaCloudResources>> listRespMediaSenderos;
	ObservableList<MediaCloudResources> obsListMediaSenderos = FXCollections.observableArrayList();
	
	// DE LA CLASE TIPOMEDIA
 	TipoMedia tipoMedia = new TipoMedia();
 	List<TipoMedia> listaTipoMedia = new ArrayList<TipoMedia>();
 	private static ResponseEntity<List<TipoMedia>> listRespTipoMedia;
 	ObservableList<TipoMedia> obsListTipoMedia = FXCollections.observableArrayList();

 	//DE LA CLASE COSTO
 	Costo costo = new Costo();
 	List<Costo> listaCosto = new ArrayList<Costo>();
 	
 	//DE LA CLASE CONTACTO
 	Contacto contacto = new Contacto();
 	List<Contacto> listaContacto = new ArrayList<Contacto>();
	
 	//DE LA CLASE ACCESIBILIDADES
 	Accesibilidad accesibilidad = new Accesibilidad();
 	List<Accesibilidad> listaAccesibilidad = new ArrayList<Accesibilidad>();
 	private static ResponseEntity<List<Accesibilidad>> listRespAccesibilidad;
	ObservableList<Accesibilidad> obsListAccesibilidad = FXCollections.observableArrayList();
	
	// DE LA CLASE CATEGORIA
	Categoria categoria = new Categoria();
	List<Categoria> listaCategoria = new ArrayList<Categoria>();
	private static ResponseEntity<List<Categoria>> listRespCategoria;
	ObservableList<Categoria> obsListCategoria = FXCollections.observableArrayList();

	// DE LA CLASE IDIOMAS
	Idiomas idioma = new Idiomas();
	List<Idiomas> listaIdiomas = new ArrayList<Idiomas>();
	private static ResponseEntity<List<Idiomas>> listRespIdiomas;
	ObservableList<Idiomas> obsListIdiomas = FXCollections.observableArrayList();

	// DE LA CLASE COMODIDADES
	Comodidades comodidad = new Comodidades();
	List<Comodidades> listaComodidades = new ArrayList<Comodidades>();
	List<Comodidades> listaComodidadesTemporal = new ArrayList<Comodidades>();
	private static ResponseEntity<List<Comodidades>> listRespComodidades;
	ObservableList<Comodidades> obsListComodidades = FXCollections.observableArrayList();

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
	
	// DE LA CLASE SENDERO
	Sendero sendero = new Sendero();
	List<Sendero> listaSendero = new ArrayList<Sendero>();
	List<Sendero> listaSenderoTemporal = new ArrayList<Sendero>();
	private static ResponseEntity<List<Sendero>> listRespSendero;
	ObservableList<Sendero> obsListSendero = FXCollections.observableArrayList();	


	public void initialize() {	
		gcsw.showMediaInContenedor(new Image("albums.png",250,500,true,false), contenedorDeMedios, (double) 288);
		gcsw.showMediaInContenedor(new Image("albums.png",250,500,true,false), contenedorDeAtractivos, (double) 288);
		gcsw.showMediaInContenedor(new Image("albums.png",250,500,true,false), contenedorDeSenderos, (double) 288);
		listasCellFactory();
		System.out.println("1");
		loadTipoMedios();
		System.out.println("2");
		loadTipoAtractivo();
		System.out.println("3");
		loadAccesibilidades();
		System.out.println("5");
		loadCategorias();
		System.out.println("6");
		loadIdiomas();
		System.out.println("7");
		buscarPorNombre();		
		System.out.println("8");
		buscarComodidadesTextChange();
		System.out.println("9");
		//cargar provincia, canton y parroquia
		acco_Der.setExpandedPane(accd_accesibilidadesRecurso);
		acco_Izq.setExpandedPane(accd_costosRecurso);
		if (Context.getInstance().getRecursoContext() != null) {
			recurso = Context.getInstance().getRecursoContext();
			System.out.println("HOLA PASO");
			cargarDatosRecurso(recurso);
			System.out.println("HOLA PASO2");
			isModificar = true;
			loadMedios();
			loadComodidades();
			System.out.println("12");			
			loadAtractivos();
			System.out.println("13");
			System.out.println("4");
			loadSenderos();
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
    		//guardando recurso con costo
			ArrayList<Costo> auxCosto = new ArrayList<>();
    		if (lst_listaCostosRecurso.getItems().size() > 0) {
    			if(recurso.getCostoServicio() != null)recurso.getCostoServicio().clear();	
				for (Costo mcr : lst_listaCostosRecurso.getItems()) {
//					costo = rest.postForObject(uriCosto + "/saveOrUpdate", mcr, Costo.class);
					auxCosto.add(mcr);
				}    		
    		}
			recurso.setCostoServicio(auxCosto);
			//guardando recurso con costo FIN
			//guardando recurso con contacto
			ArrayList<Contacto> auxContacto = new ArrayList<>();
    		if (lst_listaContactosRecurso.getItems().size() > 0) {
    			if(recurso.getContactos() != null)recurso.getContactos().clear();	
				for (Contacto mcr : lst_listaContactosRecurso.getItems()) {
					auxContacto.add(mcr);
				}    		
    		}
			recurso.setContactos(auxContacto);
			//guardando recurso con costo FIN
			//guardando recurson con accesibilidades
			ArrayList<String> auxAcce = new ArrayList<>();
			if(chklst_accesibilidadesRecurso.getCheckModel().getCheckedItems().size() > 0) {
				if(recurso.getIdsAccesibilidades() != null) recurso.getIdsAccesibilidades().clear();
				for (Accesibilidad ac : chklst_accesibilidadesRecurso.getCheckModel().getCheckedItems()) {
					auxAcce.add(ac.getId());
				}			
			}
			recurso.setIdsAccesibilidades(auxAcce);
			//guardando recurson con accesibilidades FIN
			//guardando recurson con categoria
			ArrayList<String> auxCat = new ArrayList<>();
			if(chklst_categoriasRecurso.getCheckModel().getCheckedItems().size() > 0) {
				if(recurso.getIdsCategoria() != null) recurso.getIdsCategoria().clear();
				for (Categoria ac : chklst_categoriasRecurso.getCheckModel().getCheckedItems()) {
					auxCat.add(ac.getId());
				}			
			}
			recurso.setIdsCategoria(auxCat);
			//guardando recurson con categorias FIN
			//guardando recurson con idiomas
			ArrayList<String> auxidio = new ArrayList<>();
			if(chklst_idiomasRecurso.getCheckModel().getCheckedItems().size() > 0) {
				if(recurso.getIdiomas() != null) recurso.getIdiomas().clear();
				for (Idiomas ac : chklst_idiomasRecurso.getCheckModel().getCheckedItems()) {
					auxidio.add(ac.getId());
				}			
			}
			recurso.setIdiomas(auxidio);
			//guardando recurson con idiomas FIN
			//guardando recurson con sendero
			ArrayList<String> auxse = new ArrayList<>();
			if(lst_listaSenderos.getItems().size() > 0) {
				if(recurso.getIdsSenderos() != null) recurso.getIdsSenderos().clear();
				for (Sendero ac : lst_listaSenderos.getItems()) {
					auxse.add(ac.getId());
				}			
			}
			recurso.setIdsSenderos(auxse);
			//guardando recurson con sendero FIN
			
    		//Guardamos el recurso ****************************************
    		recurso = rest.postForObject(uriRecurso + "/saveOrUpdate", recurso, Recurso.class);
    		//Guardamos el recurso ****************************************
    		
    		//guardando recurso con comodidades
    		if (lst_listaComodidadesRecurso.getItems().size() > 0) {
				for (Comodidades mcr : lst_listaComodidadesRecurso.getItems()) {
					mcr.setIdRecurso(recurso.getId());
					rest.postForObject(uriComodidades + "/saveOrUpdate", mcr, Comodidades.class);
				}    		
    		}    		
    		//guardando recurso con comodidades FIN
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
					mcr.setIdRecurso(recurso.getId());
					rest.postForObject(uriAtractivo + "/saveOrUpdate", mcr, Atractivo.class);
				}    		
    		}    		
    		//guardando recurso con atractivos FIN
    		
    		Context.getInstance().setRecursoContext(recurso);
			Message.showSuccessNotification("Se guardaron exitosamente los datos.!! ");
			General.setContentParent("/viewPrincipal/RecursoPrincipal.fxml", Context.getInstance().getAnch_Contenido());
			
		} catch (Exception e) {
			e.printStackTrace();
			Message.showErrorNotification("Ha surgido un error al guardar datos.!!");			
		}
    }

    @FXML
    void addNuevoSendero(ActionEvent event) {
    	try {
    		Context.getInstance().setSenderoContext(null);
    		General.showModalWithParentSendero("/viewRecurso/ModalSendero.fxml");
    		if (Context.getInstance().getSenderoContext() != null) {
    			listaSenderoTemporal.add(Context.getInstance().getSenderoContext());
    			cargarListaSendero(listaSenderoTemporal);
    		}
    		Context.getInstance().setSenderoContext(null);
		} catch (Exception e) {
			e.printStackTrace();
			Message.showErrorNotification("Surgió un error al agregar sendero.!!");
		}
    }

    @FXML
    void addComodidadesRecurso(ActionEvent event) {
    	try {
    		//abro interfaz para crear un costo
    		Context.getInstance().setComodidadContext(null);
    		General.showModalWithParent("/viewRecurso/ModalComodidad.fxml");
    		if (Context.getInstance().getComodidadContext() != null) {
//    			lst_listaComodidadesRecurso.getItems().add(Context.getInstance().getComodidadContext());
    			listaComodidadesTemporal.add(Context.getInstance().getComodidadContext());
    			cargarListaComodidades(listaComodidadesTemporal);
    		}
    		Context.getInstance().setComodidadContext(null);
		} catch (Exception e) {
			e.printStackTrace();
			Message.showErrorNotification("Surgió un error al agregar comodidad.!!");
		}
    }

    @FXML
    void addContactoRecurso(ActionEvent event) {
    	try {
    		//abro interfaz para crear un costo
    		Context.getInstance().setContactoContext(null);
    		General.showModalWithParent("/viewRecurso/ModalContacto.fxml");
    		if (Context.getInstance().getContactoContext() != null) {
    			lst_listaContactosRecurso.getItems().add(Context.getInstance().getContactoContext());
			}
    		Context.getInstance().setContactoContext(null);
		} catch (Exception e) {
			e.printStackTrace();
			Message.showErrorNotification("Surgió un error al agregar contacto.!!");
		}
    }

    @FXML
    void addCostoRecurso(ActionEvent event) {
    	try {
    		//abro interfaz para crear un costo
    		Context.getInstance().setCostoContext(null);
    		General.showModalWithParent("/viewRecurso/ModalCostos.fxml");
    		if (Context.getInstance().getCostoContext() != null) {
//    			listaCosto.add(Context.getInstance().getCostoContext());
    			lst_listaCostosRecurso.getItems().add(Context.getInstance().getCostoContext());
//    			cargarListaCostos(listaCosto);
			}
    		Context.getInstance().setCostoContext(null);
		} catch (Exception e) {
			e.printStackTrace();
			Message.showErrorNotification("Surgió un error al agregar costos.!!");
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
    void buscarComodidadesTextChange() {
    	txt_buscarComodidadesRecurso.textProperty().addListener((observable, oldValue, newValue) -> {
    	    System.out.println("textfield changed from " + oldValue + " to " + newValue);
    	    
    	    ObservableList<Comodidades> obsAux = FXCollections.observableArrayList();			
    	    for (Comodidades mcr : lst_listaComodidadesRecurso.getItems()) {    	    	
    	    	String nombre = mcr.getDescripcion();
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
    	    if (newValue.isBlank() || newValue.isEmpty()) {
    	    	cargarListaComodidades(listaComodidadesTemporal);
			}else {
	    	    List<Comodidades> auxList = new ArrayList<>();
				auxList.addAll(obsAux);
				lst_listaComodidadesRecurso.getItems().clear();
				cargarListaComodidades(auxList);
			}
    	});
    }

    @FXML
    void buscarRecursoEnMapa(ActionEvent event) {
    	
    }

    @FXML
    void eliminarSendero(ActionEvent event) {
    	try {
    		if (lst_listaSenderos.getSelectionModel().getSelectedItems().isEmpty()) {
    			Message.showWarningNotification("Seleccione el sendero a eliminar.!!");
    			return;
    		}
    		Optional<ButtonType> result = Message.showQuestion("Desea continuar y eliminar los datos del sendero: "
    				+ lst_listaSenderos.getSelectionModel().getSelectedItem().getNombre() + " ?.",
    				Context.getInstance().getStage());
    		if (result.get() == ButtonType.OK) {
    			sendero = lst_listaSenderos.getSelectionModel().getSelectedItem();
    			if (lst_listaSenderos.getSelectionModel().getSelectedItem().getId() != null) {
    				Map<String, String> params = new HashMap<String, String>();
    				params.put("c", lst_listaSenderos.getSelectionModel().getSelectedItem().getId());
    				rest.delete(uriSendero + "/delete/{c}", params);
    				Message.showSuccessNotification("Se eliminaron exitosamente los datos.!!");
    			}
    			if (listaSenderoTemporal.size() > 0) 	
    				listaSenderoTemporal.remove(sendero);
    			if (lst_listaSenderos.getItems().size() > 0){
    				lst_listaSenderos.getItems().remove(sendero);
    			}
    		}
    	}catch (Exception e) {
    		e.printStackTrace();
    		Message.showErrorNotification("Ha surgido un error al eliminar datos.!!");
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
    void eliminarComodidadesRecurso(ActionEvent event) {
    	try {
    		if (lst_listaComodidadesRecurso.getSelectionModel().getSelectedItems().isEmpty()) {
				Message.showWarningNotification("Seleccione la comodidad a eliminar.!!");
				return;
			}
			Optional<ButtonType> result = Message.showQuestion("Desea continuar y eliminar los datos de la comodidad: "
							+ lst_listaComodidadesRecurso.getSelectionModel().getSelectedItem().getDescripcion() + " ?.",
					Context.getInstance().getStage());
			if (result.get() == ButtonType.OK) {
				comodidad = lst_listaComodidadesRecurso.getSelectionModel().getSelectedItem();
				if (lst_listaComodidadesRecurso.getSelectionModel().getSelectedItem().getId() != null) {
					Map<String, String> params = new HashMap<String, String>();
					params.put("c", lst_listaComodidadesRecurso.getSelectionModel().getSelectedItem().getId());
					rest.delete(uriComodidades + "/deletePhysical/{c}", params);
					Message.showSuccessNotification("Se eliminaron exitosamente los datos.!!");
				}
//				lst_listaComodidadesRecurso.getItems().remove(lst_listaComodidadesRecurso.getSelectionModel().getSelectedItem());
				if (listaComodidadesTemporal.size() > 0) 	
					listaComodidadesTemporal.remove(comodidad);
				if (lst_listaComodidadesRecurso.getItems().size() > 0)	{
					lst_listaComodidadesRecurso.getItems().remove(comodidad);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			Message.showErrorNotification("Ha surgido un error al eliminar datos.!!");
		}
    }

    @FXML
    void eliminarContactoRecurso(ActionEvent event) {
    	try {
    		if (lst_listaContactosRecurso.getSelectionModel().getSelectedItems().isEmpty()) {
				Message.showWarningNotification("Seleccione el contacto a eliminar.!!");
				return;
			}
			Optional<ButtonType> result = Message.showQuestion("Desea continuar y eliminar los datos del contacto: "
							+ lst_listaContactosRecurso.getSelectionModel().getSelectedItem().getNombre() + " ?.",
					Context.getInstance().getStage());
			if (result.get() == ButtonType.OK) {
				lst_listaContactosRecurso.getItems().remove(lst_listaContactosRecurso.getSelectionModel().getSelectedItem());
			}
		} catch (Exception e) {
			e.printStackTrace();
			Message.showErrorNotification("Ha surgido un error al eliminar datos.!!");
		}
    }

    @FXML
    void eliminarCostoRecurso(ActionEvent event) {
    	try {
    		if (lst_listaCostosRecurso.getSelectionModel().getSelectedItems().isEmpty()) {
				Message.showWarningNotification("Seleccione el costo a eliminar.!!");
				return;
			}
			Optional<ButtonType> result = Message.showQuestion("Desea continuar y eliminar los datos del costo: "
							+ lst_listaCostosRecurso.getSelectionModel().getSelectedItem().getDescripcion() + " ?.",
					Context.getInstance().getStage());
			if (result.get() == ButtonType.OK) {
				lst_listaCostosRecurso.getItems().remove(lst_listaCostosRecurso.getSelectionModel().getSelectedItem());
			}
		} catch (Exception e) {
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
    void modificarSendero(ActionEvent event) {
    	try {
    		if (lst_listaSenderos.getSelectionModel().getSelectedItems().isEmpty()) {
				Message.showWarningNotification("Seleccione el sendero a modificar.!!");
				return;
			}
    		Context.getInstance().setSenderoContext(lst_listaSenderos.getSelectionModel().getSelectedItem());
    		General.showModalWithParentSendero("/viewRecurso/ModalSendero.fxml");
    		if (Context.getInstance().getSenderoContext() != null) {
    			sendero = Context.getInstance().getSenderoContext();

    			cargarListaSendero(listaSenderoTemporal);
			}
    		Context.getInstance().setSenderoContext(null);     		
		} catch (Exception e) {
			e.printStackTrace();
			Message.showErrorNotification("Ha surgido un error al modificar datos.!!");
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
    void modificarComodidadesRecurso(ActionEvent event) {
    	try {
    		if (lst_listaComodidadesRecurso.getSelectionModel().getSelectedItems().isEmpty()) {
				Message.showWarningNotification("Seleccione la comodidad a modificar.!!");
				return;
			}
    		Context.getInstance().setComodidadContext(lst_listaComodidadesRecurso.getSelectionModel().getSelectedItem());
    		General.showModalWithParent("/viewRecurso/ModalComodidad.fxml");
    		if (Context.getInstance().getComodidadContext() != null) {
    			comodidad = Context.getInstance().getComodidadContext();
//    			cargarListaComodidades(lst_listaComodidadesRecurso.getItems());
    			

    			cargarListaComodidades(listaComodidadesTemporal);
//    			lst_listaMedios.getSelectionModel().clearSelection();
			}
    		Context.getInstance().setComodidadContext(null);    		
    		
		} catch (Exception e) {
			e.printStackTrace();
			Message.showErrorNotification("Ha surgido un error al modificar datos.!!");
		}
    }

    @FXML
    void modificarContactoRecurso(ActionEvent event) {
    	try {
    		if (lst_listaContactosRecurso.getSelectionModel().getSelectedItems().isEmpty()) {
				Message.showWarningNotification("Seleccione el contacto a modificar.!!");
				return;
			}
    		Context.getInstance().setContactoContext(lst_listaContactosRecurso.getSelectionModel().getSelectedItem());
    		General.showModalWithParent("/viewRecurso/ModalContacto.fxml");
    		if (Context.getInstance().getContactoContext() != null) {
    			contacto = Context.getInstance().getContactoContext();
    			cargarListaContactos(lst_listaContactosRecurso.getItems());
			}
    		Context.getInstance().setContactoContext(null);    		
    		
		} catch (Exception e) {
			e.printStackTrace();
			Message.showErrorNotification("Ha surgido un error al modificar datos.!!");
		}
    }

    @FXML
    void modificarCostoRecurso(ActionEvent event) {
    	try {
    		if (lst_listaCostosRecurso.getSelectionModel().getSelectedItems().isEmpty()) {
				Message.showWarningNotification("Seleccione el costo a modificar.!!");
				return;
			}
    		Context.getInstance().setCostoContext(lst_listaCostosRecurso.getSelectionModel().getSelectedItem());
    		General.showModalWithParent("/viewRecurso/ModalCostos.fxml");
    		if (Context.getInstance().getCostoContext() != null) {
    			costo = Context.getInstance().getCostoContext();
    			cargarListaCostos(lst_listaCostosRecurso.getItems());
			}
    		Context.getInstance().setCostoContext(null);    		
    		
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
//						lst_listaMedios.getItems().addAll(obsAux);
					}
				}
			});
		}				
	}

	private void loadAccesibilidades() {
		obsListAccesibilidad.clear();
		listRespAccesibilidad = rest.exchange(uriAccesibilidad + "/getAll", HttpMethod.GET, null,
				new ParameterizedTypeReference<List<Accesibilidad>>() {
				});
		listaAccesibilidad = listRespAccesibilidad.getBody();
		chklst_accesibilidadesRecurso.setPlaceholder(new Label("---  No se encontraron datos en la Base. ---"));
		if (!listaAccesibilidad.isEmpty()) {
			for (int i = 0; i < listaAccesibilidad.size(); i++) {
				obsListAccesibilidad.add(listaAccesibilidad.get(i));
			}			
			chklst_accesibilidadesRecurso.setItems(obsListAccesibilidad);		    	  	
		}
	}

	private void loadCategorias() {
		obsListCategoria.clear();
		listRespCategoria = rest.exchange(uriCategoria + "/getAll", HttpMethod.GET, null,
				new ParameterizedTypeReference<List<Categoria>>() {
				});
		listaCategoria = listRespCategoria.getBody();
		chklst_categoriasRecurso.setPlaceholder(new Label("---  No se encontraron datos en la Base. ---"));
		if (!listaCategoria.isEmpty()) {
			for (int i = 0; i < listaCategoria.size(); i++) {
				obsListCategoria.add(listaCategoria.get(i));
			}			
			chklst_categoriasRecurso.setItems(obsListCategoria);		    		    	
		}		
	}
	
	private void loadIdiomas() {
		obsListIdiomas.clear();
		listRespIdiomas = rest.exchange(uriIdiomas + "/getAll", HttpMethod.GET, null,
				new ParameterizedTypeReference<List<Idiomas>>() {
				});
		listaIdiomas = listRespIdiomas.getBody();
		chklst_idiomasRecurso.setPlaceholder(new Label("---  No se encontraron datos en la Base. ---"));
		if (!listaIdiomas.isEmpty()) {
			for (int i = 0; i < listaIdiomas.size(); i++) {
				obsListIdiomas.add(listaIdiomas.get(i));
			}			
			chklst_idiomasRecurso.setItems(obsListIdiomas);
		}
	}

    private void loadSenderos() {
		obsListSendero.clear();
		listRespSendero = rest.exchange(uriSendero + "/getAll", HttpMethod.GET, null,
				new ParameterizedTypeReference<List<Sendero>>() {
				});
		listaSendero = listRespSendero.getBody();
		if (!listaSendero.isEmpty()) {
			if (recurso.getIdsSenderos() != null) {
				for (Sendero act : listaSendero) {
					if (recurso.getIdsSenderos().contains(act.getId())) {
						listaSenderoTemporal.add(act);
					}
				}
			}
		}
		cargarListaSendero(listaSenderoTemporal);
	}
    
	private void loadComodidades() {
		obsListComodidades.clear();
		listRespComodidades = rest.exchange(uriComodidades + "/getAll", HttpMethod.GET, null,
				new ParameterizedTypeReference<List<Comodidades>>() {
				});
		listaComodidades = listRespComodidades.getBody();
		//filtrar por recurso id
		if (!listaComodidades.isEmpty()) {
			if (recurso.getId() != null) {
				for (Comodidades comodidad : listaComodidades) {
					if(comodidad.getIdRecurso().equals(recurso.getId())) {
						listaComodidadesTemporal.add(comodidad);
					}
				}
			}
		}
		cargarListaComodidades(listaComodidadesTemporal);
	}
	
	private void loadAtractivos() {
		obsListAtractivo.clear();
		listRespAtractivo = rest.exchange(uriAtractivo + "/getAll", HttpMethod.GET, null,
				new ParameterizedTypeReference<List<Atractivo>>() {
				});
		listaAtractivo = listRespAtractivo.getBody();
		//filtrar por recurso id

		if (!listaAtractivo.isEmpty()) {
			if (recurso.getId() != null) {
				for (Atractivo act : listaAtractivo) {
					if (act.getIdRecurso() != null) {
						if(act.getIdRecurso().equals(recurso.getId())) {
							listaAtractivoTemporal.add(act);
						}
					}					
				}
			}
		}
		System.out.println("6" + listaAtractivoTemporal);
		cargarListaAtractivos(listaAtractivoTemporal);
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
    
    private void cargarDatosRecurso(Recurso recurso) {
    	//carga todos los datos del recurso.
    	//recurso
    	txt_nombreRecurso.setText(recurso.getNombre());
    	txt_propietarioRecurso.setText(recurso.getPropietario());
    	txt_coordenadasRecurso.setText(recurso.getCoordenadas());
    	txt_descripcionRecurso.setText(recurso.getDescripcion());
    	txt_infGeneralRecurso.setText(recurso.getInformacionGeneral());
    	txt_direccionRecurso.setText(recurso.getDireccion());
    	if (recurso.getIdLocalizacion() != null) {
			//seleccionar los combos de localizacion  parroquia etc segun recurso
		}
    	//llenar ranking
    	//llenar seguridad
    	//llenar costos.
    	if (recurso.getCostoServicio() != null) {
    		cargarListaCostos(recurso.getCostoServicio());
		}
    	//llenar contactos.
    	if (recurso.getContactos() != null) {
    		cargarListaContactos(recurso.getContactos());
		}
    	//checkear accesibilidad
    	if (recurso.getIdsAccesibilidades() != null) {
			for (Accesibilidad ac : listaAccesibilidad) {
				if (recurso.getIdsAccesibilidades().contains(ac.getId())) {
					chklst_accesibilidadesRecurso.getCheckModel().check(ac);
				}
			}
		}
    	//checkear categorias
    	if (recurso.getIdsCategoria() != null) {
			for (Categoria ac : listaCategoria) {
				if (recurso.getIdsCategoria().contains(ac.getId())) {
					chklst_categoriasRecurso.getCheckModel().check(ac);
				}
			}
		}
    	//checkear idiomas
    	if (recurso.getIdiomas() != null) {
			for (Idiomas ac : listaIdiomas) {
				if (recurso.getIdiomas().contains(ac.getId())) {
					chklst_idiomasRecurso.getCheckModel().check(ac);
				}
			}
		}
    	
    	
    	
	}
    
    private void cargarListaContactos(List<Contacto> lista) {
    	ObservableList<Contacto> obsCostos = FXCollections.observableArrayList();
    	lst_listaContactosRecurso.setPlaceholder(new Label("---  La lista de contacto se encuentra vacia. ---"));
		if (!lista.isEmpty()) {
    		for (int i = 0; i < lista.size(); i++) {
    			obsCostos.add(lista.get(i));
			}
    		lst_listaContactosRecurso.setItems(obsCostos);	
		}	
	}

	private void cargarListaCostos(List<Costo> lista) {
    	ObservableList<Costo> obsCostos = FXCollections.observableArrayList();
    	lst_listaCostosRecurso.setPlaceholder(new Label("---  La lista de costo se encuentra vacia. ---"));
		if (!lista.isEmpty()) {
    		for (int i = 0; i < lista.size(); i++) {
    			obsCostos.add(lista.get(i));
			}
			lst_listaCostosRecurso.setItems(obsCostos);	
		}
		
    }
	
	private void cargarListaComodidades(List<Comodidades> lista) {
    	ObservableList<Comodidades> obsComo = FXCollections.observableArrayList();
		lst_listaComodidadesRecurso.setPlaceholder(new Label("---  No se encontraron datos en la Base. ---"));
		System.out.println("LISTA COMODIDADES: " + lista);
		if (!lista.isEmpty()) {
			for (int i = 0; i < lista.size(); i++) {
				
				obsComo.add(lista.get(i));
			}			
			lst_listaComodidadesRecurso.setItems(obsComo);
		}
	}	

	private void cargarListaSendero(List<Sendero> lista) {
    	ObservableList<Sendero> obsComo = FXCollections.observableArrayList();
		lst_listaSenderos.setPlaceholder(new Label("---  No se encontraron datos en la Base. ---"));
		if (!lista.isEmpty()) {
			for (int i = 0; i < lista.size(); i++) {		
				
				obsComo.add(lista.get(i));
			}			
			lst_listaSenderos.setItems(obsComo);
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
    
	private void listasCellFactory() {
		lst_listaCostosRecurso.setCellFactory(param -> new ListCell<Costo>() {
    		protected void updateItem(Costo item, boolean empty) {
    			super.updateItem(item, empty);
    			setText(empty ? "" : item.getValor().toString().concat(" - ").concat(item.getDescripcion()) );
    		};
    	});  	

		lst_listaContactosRecurso.setCellFactory(param -> new ListCell<Contacto>() {
    		protected void updateItem(Contacto item, boolean empty) {
    			super.updateItem(item, empty);
    			setText(empty ? "" : item.getNombre().concat(" - ").concat(item.getCelular()) );
    		};
    	});  	
		
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
		
		chklst_accesibilidadesRecurso.setCellFactory(lv -> new CheckBoxListCell<Accesibilidad>(chklst_accesibilidadesRecurso::getItemBooleanProperty) {
			@Override
			public void updateItem(Accesibilidad item, boolean empty) {
				super.updateItem(item, empty);
				setText(empty ? "" : item.getDescripcion());
			}
		});	  
		
		chklst_categoriasRecurso.setCellFactory(lv -> new CheckBoxListCell<Categoria>(chklst_categoriasRecurso::getItemBooleanProperty) {
			@Override
			public void updateItem(Categoria item, boolean empty) {
				super.updateItem(item, empty);
				setText(empty ? "" : item.getDescripcion());
			}
		});	  
		
		chklst_idiomasRecurso.setCellFactory(lv -> new CheckBoxListCell<Idiomas>(chklst_idiomasRecurso::getItemBooleanProperty) {
			@Override
			public void updateItem(Idiomas item, boolean empty) {
				super.updateItem(item, empty);
				setText(empty ? "" : item.getDescripcion());
			}
		});	  

		lst_listaComodidadesRecurso.setCellFactory(param -> new ListCell<Comodidades>() {
    		protected void updateItem(Comodidades item, boolean empty) {
    			super.updateItem(item, empty);
    			setText(empty ? "" : item.getDescripcion() );
    		};
    	});  

		lst_listaSenderos.setCellFactory(param -> new ListCell<Sendero>() {
    		protected void updateItem(Sendero item, boolean empty) {
    			super.updateItem(item, empty);
    			setText(empty ? "" : item.getNombre());
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
		lst_listaSenderos.getSelectionModel().selectedItemProperty()
		.addListener((ObservableValue<? extends Sendero> ov, Sendero old_val, Sendero new_val) -> {
			if (lst_listaSenderos.getSelectionModel().getSelectedItem() != null) {
				sendero = lst_listaSenderos.getSelectionModel().getSelectedItem();
				
				txt_descripcionSendero.setText(sendero.getDescripcion());
				txt_instruccionesSendero.setText(sendero.getInstrucciones());
				if (sendero.getTiempoRecorrido() != null) {
		        	txt_tiempoRecorridoSendero.setText(sendero.getTiempoRecorrido().toString());
				}
				if (sendero.getDistanciaAproximada() != null) {
					txt_distanciaAproxSendero.setText(sendero.getDistanciaAproximada().toString());
				}
				
				//cargar las imagenes
				if (sendero.getListaMCR()!= null) {
					for (MediaCloudResources mcr : sendero.getListaMCR()) {
						if (mcr.getFileTemporal() != null) {
							if (mcr.getIsPrincipal()) {
								if (mcr.getTipoMedia().getDescripcion().equals("Imagen")) {
									Image image = new Image("file:" + mcr.getFileTemporal().getAbsolutePath());
									gcsw.showMediaInContenedor(image, contenedorDeSenderos, (double) 288);
								}else if(mcr.getTipoMedia().getDescripcion().equals("Video")) {
									File d = new File(mcr.getFileTemporal().getAbsolutePath().replace("\\","/"));
									Media video = new Media(d.toURI().toString());
									gcsw.showMediaInContenedor(video, contenedorDeSenderos);						        
								}	
							}						    
						}else {
							if (mcr.getIsPrincipal()) {	
								if (mcr.getTipoMedia().getDescripcion().equals("Imagen")) {
									Image img = gcsw.getImageMediaCR(mcr.getNombre().concat(mcr.getCoordenadas()));
									gcsw.showMediaInContenedor(img, contenedorDeSenderos, (double) 288);	
								}else if(mcr.getTipoMedia().getDescripcion().equals("Video")) {
									Media video = gcsw.getMediaFromMediaCR(mcr.getNombre().concat(mcr.getCoordenadas()));
							        gcsw.showMediaInContenedor(video, contenedorDeSenderos);	
								}
							}
						}

					}
				}
				
			}
		});
	}
}
