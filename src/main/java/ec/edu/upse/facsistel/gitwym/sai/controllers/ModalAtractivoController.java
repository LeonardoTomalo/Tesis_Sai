package ec.edu.upse.facsistel.gitwym.sai.controllers;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;

import ec.edu.upse.facsistel.gitwym.sai.cloud.GoogleCloudStorageWorker;
import ec.edu.upse.facsistel.gitwym.sai.models.Atractivo;
import ec.edu.upse.facsistel.gitwym.sai.models.MediaCloudResources;
import ec.edu.upse.facsistel.gitwym.sai.models.TipoAtractivo;
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

public class ModalAtractivoController {

    @FXML private JFXTextField txt_coordenadas;
    @FXML private JFXButton btn_verMapa;
    @FXML private JFXTextField txt_nombre;
    @FXML private JFXComboBox<TipoAtractivo> cmb_tipoAtractivo;
    @FXML private JFXTextArea txt_descripcion;
    @FXML private JFXButton btn_agregarImagen;
    @FXML private JFXButton btn_eliminarImagen;
    @FXML private JFXListView<MediaCloudResources> lst_listaImagenes;
    @FXML private AnchorPane anch_contenedorImagenes;
    @FXML private JFXButton btn_Guardar;
    @FXML private JFXButton btn_Cancelar;
    
    // CONSUMIR WEB SERVICES
   	RestTemplate rest = new RestTemplate();
  	String urlBase = PropertyManager.getBaseUrl();
	String uriTipoAtractivo = urlBase + "/tipoAtractivo";
	String uriAtractivo = urlBase + "/atractivo";
	String uriImagen = urlBase + "/mediaCloudResources";
  	
	// DE LA CLASE ATRACTIVOS
	Atractivo atractivo = new Atractivo();
	
	// DE LA CLASE TIPOATRACTIVO
	TipoAtractivo tipoAtractivo = new TipoAtractivo();
	List<TipoAtractivo> listaTipoAtractivo = new ArrayList<TipoAtractivo>();
	private static ResponseEntity<List<TipoAtractivo>> listRespTipoAtractivo;
	ObservableList<TipoAtractivo> obsListTipoAtractivo = FXCollections.observableArrayList();

	//DE LA CLASE IMAGEN
	MediaCloudResources imagen = new MediaCloudResources();
//	List<MediaCloudResources> listaImagen = new ArrayList<MediaCloudResources>();
//	private static ResponseEntity<List<MediaCloudResources>> listRespImagen;
//	ObservableList<MediaCloudResources> obsListImagen = FXCollections.observableArrayList();
	GoogleCloudStorageWorker gcsw = new GoogleCloudStorageWorker();
    
	
	public void initialize() {
		gcsw.showMediaInContenedor(new Image("albums.png",250,500,true,false), anch_contenedorImagenes, (double) 288);
		listaCellFactory();
		loadTipoAtractivos();
		if (Context.getInstance().getAtractivoContext() != null) {
 			atractivo = Context.getInstance().getAtractivoContext();
 			txt_coordenadas.setText(atractivo.getCoordenadas());
 			txt_descripcion.setText(atractivo.getDescripcion());
 			txt_nombre.setText(atractivo.getNombre());
 			
 			if (atractivo.getIdTipoAtractivo() != null) {
				for (TipoAtractivo tc : listaTipoAtractivo) {
					if (tc.getId().equals(atractivo.getIdTipoAtractivo())) {
			 			cmb_tipoAtractivo.getSelectionModel().select(tc);
					}
				}
			}
 			
 			if (atractivo.getImagenes() != null) {
				cargarListaImagenes(atractivo.getImagenes());
			}
 		}
	}	
	
    @FXML
    void cancelar(ActionEvent event) {
    	try {
    		Context.getInstance().setAtractivoTipo(null);
			Context.getInstance().setAtractivoContext(null);
			Context.getInstance().getStageModalBaseAtractivo().close();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

    @FXML
    void eliminarImagen(ActionEvent event) {
    	try {
    		if (lst_listaImagenes.getSelectionModel().getSelectedItems().isEmpty()) {
				Message.showWarningNotification("Seleccione la imagen a eliminar.!!");
				return;
			}
			Optional<ButtonType> result = Message.showQuestion("Desea continuar y eliminar los datos de la imagen: "
							+ lst_listaImagenes.getSelectionModel().getSelectedItem().getNombre() + " ?.",
					Context.getInstance().getStage());
			if (result.get() == ButtonType.OK) {
				lst_listaImagenes.getItems().remove(lst_listaImagenes.getSelectionModel().getSelectedItem());
			}
		} catch (Exception e) {
			e.printStackTrace();
			Message.showErrorNotification("Ha surgido un error al eliminar datos.!!");
		}
    }

    @FXML
    void findToDirectory(ActionEvent event) {
    	try {
    		//abro interfaz para crear un costo
    		Context.getInstance().setMediaContext(null);
    		Context.getInstance().setAtractivoTipo(true);
    		General.showModalWithParent("/viewRecurso/PopoverMediaCloud.fxml");
    		if (Context.getInstance().getMediaContext() != null) {
    			lst_listaImagenes.getItems().add(Context.getInstance().getMediaContext());
			}
    		Context.getInstance().setMediaContext(null);
    		Context.getInstance().setAtractivoTipo(null);
		} catch (Exception e) {
			e.printStackTrace();
			Message.showErrorNotification("Surgió un error al agregar imagen.!!");
		}
    }

    @FXML
    void guardar(ActionEvent event) {
    	try {
    		if (txt_nombre.getText().isEmpty() || txt_nombre.getText().isBlank()) {
    			Message.showWarningNotification("Debe agregar el nombre.!!");
    			return;
    		}
    		Boolean a = false;
    		if(cmb_tipoAtractivo.getValue() != null) a = true;    		
			if (a.equals(false)) {
				Message.showWarningNotification("Debe seleccionar un tipo de atractivo.!!");
				return;
			}

			atractivo.setDescripcion(txt_descripcion.getText());
			atractivo.setCoordenadas(txt_coordenadas.getText());
			atractivo.setIdTipoAtractivo(cmb_tipoAtractivo.getValue().getId());
			atractivo.setNombre(txt_nombre.getText());
			
			//guardando iamgen
			List<MediaCloudResources> aux = new ArrayList<>();
    		if (lst_listaImagenes.getItems().size() > 0) {
    			if(atractivo.getImagenes() != null)atractivo.getImagenes().clear();	
				for (MediaCloudResources mcr : lst_listaImagenes.getItems()) {
					aux.add(mcr);
				}    		
    		}
			atractivo.setImagenes(aux);
			//guardando imagen FIN
			
    		if (atractivo.getId() != null) {
    			rest.postForObject(uriAtractivo + "/saveOrUpdate", atractivo, Atractivo.class);
			}

    		Context.getInstance().setAtractivoContext(atractivo);
    		Message.showSuccessNotification("Se agregó el contenido.!! ");
    		Context.getInstance().getStageModalBaseAtractivo().close();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

    @FXML
    void mostrarMapa(ActionEvent event) {

    }

    private void loadTipoAtractivos() {
    	listRespTipoAtractivo = rest.exchange(uriTipoAtractivo + "/getAll", HttpMethod.GET, null,
				new ParameterizedTypeReference<List<TipoAtractivo>>() {
				});
		listaTipoAtractivo = listRespTipoAtractivo.getBody();
		if (!listaTipoAtractivo.isEmpty()) {
			for (int i = 0; i < listaTipoAtractivo.size(); i++) {
				obsListTipoAtractivo.add(listaTipoAtractivo.get(i));
			}			
			cmb_tipoAtractivo.setItems(obsListTipoAtractivo);	
		}		
	}
    
    private void cargarListaImagenes(List<MediaCloudResources> lista) {
    	ObservableList<MediaCloudResources> obsCostos = FXCollections.observableArrayList();
    	lst_listaImagenes.setPlaceholder(new Label("---  La lista de imagenes se encuentra vacia. ---"));
		if (!lista.isEmpty()) {
    		for (int i = 0; i < lista.size(); i++) {
    			obsCostos.add(lista.get(i));
			}
			lst_listaImagenes.setItems(obsCostos);	
		}				
    }
    
    private void listaCellFactory() {
		lst_listaImagenes.setCellFactory(param -> new ListCell<MediaCloudResources>() {
    		protected void updateItem(MediaCloudResources item, boolean empty) {
    			super.updateItem(item, empty);
    			setText(empty ? "" : item.getNombre() );
    		};
    	}); 
		
		cmb_tipoAtractivo.setCellFactory(param -> new ListCell<TipoAtractivo>() {
    		protected void updateItem(TipoAtractivo item, boolean empty) {
    			super.updateItem(item, empty);
    			setText(empty ? "" : item.getDescripcion());
    		};
    	});  	
		

		lst_listaImagenes.getSelectionModel().selectedItemProperty()
		.addListener((ObservableValue<? extends MediaCloudResources> ov, MediaCloudResources old_val, MediaCloudResources new_val) -> {
			imagen = lst_listaImagenes.getSelectionModel().getSelectedItem();
			gcsw.showMediaInContenedor(new Image("albums.png",250,500,true,false), anch_contenedorImagenes, (double) 288);
			System.out.println("IMAGEN: " + imagen);
			if (imagen.getFileTemporal() != null) {//nuevo
				//traer la imagen con la url y presentar en el ImageView.
				if (imagen.getTipoMedia().getDescripcion().equals("Imagen")) {
				    if (imagen.getFileTemporal() != null) {
				        Image image = new Image("file:" + imagen.getFileTemporal().getAbsolutePath());
				        gcsw.showMediaInContenedor(image, anch_contenedorImagenes, (double) 288);
				    }
				}else if(imagen.getTipoMedia().getDescripcion().equals("Video")) {
					if (imagen.getFileTemporal() != null) {
						File d = new File(imagen.getFileTemporal().getAbsolutePath().replace("\\","/"));
				        Media video = new Media(d.toURI().toString());
				        gcsw.showMediaInContenedor(video, anch_contenedorImagenes);						        
					}
				}				
			}else {//cargar con el file
				if (imagen.getTipoMedia() != null) {
					if (imagen.getTipoMedia().getDescripcion().equals("Imagen")) {
						Image img = gcsw.getImageMediaCR(imagen.getNombre().concat(imagen.getCoordenadas()));
						gcsw.showMediaInContenedor(img, anch_contenedorImagenes, (double) 288);	
					}else if(imagen.getTipoMedia().getDescripcion().equals("Video")) {
						Media video = gcsw.getMediaFromMediaCR(imagen.getNombre().concat(imagen.getCoordenadas()));
				        gcsw.showMediaInContenedor(video, anch_contenedorImagenes);	
					}
				}
			}
		});
    }
    
    
    
}
