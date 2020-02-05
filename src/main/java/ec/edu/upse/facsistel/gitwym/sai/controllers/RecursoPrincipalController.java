package ec.edu.upse.facsistel.gitwym.sai.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.gluonhq.maps.MapPoint;
import com.gluonhq.maps.MapView;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;

import ec.edu.upse.facsistel.gitwym.sai.cloud.GoogleCloudStorageWorker;
import ec.edu.upse.facsistel.gitwym.sai.models.Categoria;
import ec.edu.upse.facsistel.gitwym.sai.models.Idiomas;
import ec.edu.upse.facsistel.gitwym.sai.models.MediaCloudResources;
import ec.edu.upse.facsistel.gitwym.sai.models.Recurso;
import ec.edu.upse.facsistel.gitwym.sai.utilities.Context;
import ec.edu.upse.facsistel.gitwym.sai.utilities.General;
import ec.edu.upse.facsistel.gitwym.sai.utilities.Message;
import ec.edu.upse.facsistel.gitwym.sai.utilities.PropertyManager;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

public class RecursoPrincipalController {

    @FXML private AnchorPane anch_rp;
    @FXML private JFXButton btn_busqueda;
    @FXML private JFXButton btn_crear;
    @FXML private JFXButton btn_modificar;
    @FXML private JFXButton btn_eliminar;
    @FXML private HBox hbx_busqueda;
    @FXML private AnchorPane anch_busqueda;
    @FXML private JFXTextField txt_buscarRecurso;
    @FXML private JFXButton btn_buscarRecurso;
    @FXML private Label lbl_filtros;
    @FXML private AnchorPane anch_filtros;
    @FXML private JFXComboBox<Categoria> cmb_categoria;
    @FXML private JFXComboBox<Idiomas> cmb_idioma;
    @FXML private JFXListView<Recurso> lst_listaRecursos;
    @FXML private AnchorPane anch_contImgPrinBusqueda;
    @FXML private AnchorPane anch_mapa;
    @FXML private HBox hbx_contenedorInfBasica;
    @FXML private AnchorPane anch_contenedor;
    @FXML private Label lbl_nombreRecurso;
    @FXML private Label lbl_infGeneral;
    @FXML private Label lbl_ubicacionZonal;
    @FXML private JFXButton btn_masInformacion;
    @FXML private JFXButton btn_abrirInfBasica;

	MapView map = new MapView();
	MapPoint mapPoint = new MapPoint(-2.206610, -80.692470);
	
	// CONSUMIR WEB SERVICES
	RestTemplate rest = new RestTemplate();
	String urlBase = PropertyManager.getBaseUrl();
	String uriRecurso = urlBase + "/recurso";
	String uriCategoria = urlBase + "/categoria";
	String uriIdioma = urlBase + "/idiomas";
 	String uriMediaCloud = urlBase + "/mediaCloudResources";

	// DE LA CLASE RECURSO
	Recurso recurso = new Recurso();
	List<Recurso> listaRecurso = new ArrayList<Recurso>();
	private static ResponseEntity<List<Recurso>> listRespRecurso;
	ObservableList<Recurso> obsListRecurso = FXCollections.observableArrayList();

	// DE LA CLASE CATEGORIA
	Categoria categoria = new Categoria();
	List<Categoria> listaCategoria = new ArrayList<Categoria>();
	private static ResponseEntity<List<Categoria>> listRespCategoria;
	ObservableList<Categoria> obsListCategoria = FXCollections.observableArrayList();

	// DE LA CLASE IDIOMAS
	Idiomas idiomas = new Idiomas();
	List<Idiomas> listaIdiomas = new ArrayList<Idiomas>();
	private static ResponseEntity<List<Idiomas>> listRespIdiomas;
	ObservableList<Idiomas> obsListIdiomas = FXCollections.observableArrayList();

	// DE LA CLASE MEDIA CLOUD
//	MediaCloudResources media = new MediaCloudResources();
	List<MediaCloudResources> listaMedia = new ArrayList<MediaCloudResources>();
	private static ResponseEntity<List<MediaCloudResources>> listRespMedia;
	GoogleCloudStorageWorker gcsw = new GoogleCloudStorageWorker();
    
	
	public void initialize() {	
		showInformacionBasica();
		if (Context.getInstance().getMapViewContext() != null) {
			map = Context.getInstance().getMapViewContext();
		}else {
	        map.setCenter(mapPoint);
	        map.setZoom(9.5);
	        map.flyTo(1., mapPoint, 2.);
		}
        General.setMapatoAnchorPane(map, anch_mapa);
        gcsw.showMediaInContenedor(new Image("albums.png",140,300,true,false), anch_contImgPrinBusqueda, (double) 150);
		restoreToController();
		loadIdiomas();
		loadCategorias();
		loadRecursos();
		buscarPorNombre();
	}        

    @FXML
    void crearRecurso(ActionEvent event) {
    	Context.getInstance().setRecursoContext(null);
    	General.setContentParent("/viewRecurso/Recurso.fxml", Context.getInstance().getAnch_Contenido());
    	Context.getInstance().setMapViewContext(map);
    }

    @FXML
    void eliminarRecurso(ActionEvent event) {

    }

    @FXML
    void modificarRecurso(ActionEvent event) {
    		if (lst_listaRecursos.getSelectionModel().getSelectedItems().isEmpty()) {
    			Message.showWarningNotification("Seleccione el recurso a modificar.!!");
    			return;
    		}
    		Context.getInstance().setRecursoContext(lst_listaRecursos.getSelectionModel().getSelectedItem());
//        	Context.getInstance().setMapViewContext(map);	
        	General.setContentParent("/viewRecurso/Recurso.fxml", Context.getInstance().getAnch_Contenido());
        	
    }

    @FXML
    void showBusqueda() {
    	if (hbx_busqueda.isVisible()) {
			hbx_busqueda.setVisible(false);
			hbx_busqueda.setManaged(false);
		}else {
			hbx_busqueda.setVisible(true);
			hbx_busqueda.setManaged(true);
		}	    	
    }

    @FXML
    void showInformacionBasica() {
    	if (hbx_contenedorInfBasica.isVisible()) {
			hbx_contenedorInfBasica.setVisible(false);
			hbx_contenedorInfBasica.setManaged(false);
		}else {
			hbx_contenedorInfBasica.setVisible(true);
			hbx_contenedorInfBasica.setManaged(true);
		}	    	
    }

    @FXML
    void showMasInformacion(ActionEvent event) {
    	if (lst_listaRecursos.getSelectionModel().getSelectedItems().isEmpty()) {
			Message.showWarningNotification("Seleccione el recurso a modificar.!!");
			return;
		}
    	Context.getInstance().setRecursoContext(lst_listaRecursos.getSelectionModel().getSelectedItem());
//    	Context.getInstance().setMapViewContext(map);	
    	General.setContentParent("/viewRecurso/Recurso.fxml", Context.getInstance().getAnch_Contenido());
    }    
    
    @FXML
    void buscarRecursoNombre(ActionEvent event) {
    	if (anch_filtros.isVisible()) {
        	anch_filtros.setVisible(false);
    		anch_filtros.setManaged(false);
		}
    }

    @FXML
    void abrirCerrarFiltros() {
    	if (anch_filtros.isVisible()) {
			anch_filtros.setVisible(false);
			anch_filtros.setManaged(false);
		}else {
			anch_filtros.setVisible(true);
			anch_filtros.setManaged(true);
		}	    
    }
    
    void buscarPorNombre() {
		txt_buscarRecurso.textProperty().addListener((observable, oldValue, newValue) -> {
    	    System.out.println("textfield changed from " + oldValue + " to " + newValue);
    	    
    	    ObservableList<Recurso> obsAux = FXCollections.observableArrayList();			
    	    for (Recurso mcr : lst_listaRecursos.getItems()) {    	    	
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
    	    lst_listaRecursos.setItems(obsAux);
    	    if (newValue.isBlank() || newValue.isEmpty()) {
				loadRecursos();
				obsListCategoria.clear();
				obsListIdiomas.clear();
				loadIdiomas();
				loadCategorias();
				
			}
    	});
    }    

	private void loadRecursos() {
		if(!obsListRecurso.isEmpty())	obsListRecurso.clear();
		listRespRecurso = rest.exchange(uriRecurso + "/getAll", HttpMethod.GET, null,
				new ParameterizedTypeReference<List<Recurso>>() {
				});
		listaRecurso = listRespRecurso.getBody();
		cargarListaRecursos(listaRecurso);
		//
	}

	private void loadIdiomas() {
		listRespIdiomas = rest.exchange(uriIdioma + "/getAll", HttpMethod.GET, null,
				new ParameterizedTypeReference<List<Idiomas>>() {
		});
		listaIdiomas = listRespIdiomas.getBody();
		if (!listaIdiomas.isEmpty()) {
			Idiomas auxTipo = new Idiomas("", "Todos los Idiomas", false);
			obsListIdiomas.add(auxTipo);
			for (int i = 0; i < listaIdiomas.size(); i++) {
				obsListIdiomas.add(listaIdiomas.get(i));
			}			
			cmb_idioma.setItems(obsListIdiomas);	
			cmb_idioma.getSelectionModel().select(auxTipo);
			cmb_idioma.setCellFactory(param -> new ListCell<Idiomas>() {
				protected void updateItem(Idiomas item, boolean empty) {
					super.updateItem(item, empty);
					setText(empty ? "" : item.getDescripcion());
				};
			}); 

			cmb_idioma.getSelectionModel().selectedItemProperty()
			.addListener((ObservableValue<? extends Idiomas> ov, Idiomas old_val, Idiomas new_val) -> {
				if (cmb_idioma.getSelectionModel().getSelectedItem() != null) {
					idiomas = cmb_idioma.getSelectionModel().getSelectedItem();
					ObservableList<Recurso> obsAux = FXCollections.observableArrayList();
					//utilizar solo valores del obstemporal					
					if (!listaRecurso.isEmpty()) {
						System.out.println("TIPO MEDIA SELECT: " + idiomas);
						if (idiomas.getDescripcion().equals(auxTipo.getDescripcion())) {
							//muestra todos los valores de la lista temporal que existen para el recurso
							for (int i = 0; i < listaRecurso.size(); i++) {
								obsAux.add(listaRecurso.get(i));
							}	
						}else {
							//mostrar lista de MEDIOS por el tipo.
							for (Recurso rec : listaRecurso) {	
								if(rec.getIdiomas().contains(idiomas.getDescripcion()))
									obsAux.add(rec);
							}
						}
						List<Recurso> auxList = new ArrayList<>();
						auxList.addAll(obsAux);
//						exitPopup = true;
						lst_listaRecursos.getItems().clear();
						cargarListaRecursos(auxList);
//						exitPopup = false;
						//						lst_listaMedios.getItems().addAll(obsAux);
					}
				}
			});
		}		
	}

    private void loadCategorias() {
    	listRespCategoria = rest.exchange(uriCategoria + "/getAll", HttpMethod.GET, null,
				new ParameterizedTypeReference<List<Categoria>>() {
				});
		listaCategoria = listRespCategoria.getBody();
		if (!listaCategoria.isEmpty()) {
			Categoria cat = new Categoria("", "Todas las Categorias", false);
			obsListCategoria.add(cat);
			for (int i = 0; i < listaCategoria.size(); i++) {
				obsListCategoria.add(listaCategoria.get(i));
			}
			cmb_categoria.setItems(obsListCategoria);	
			cmb_categoria.getSelectionModel().select(cat);
	    	cmb_categoria.setCellFactory(param -> new ListCell<Categoria>() {
	    		protected void updateItem(Categoria item, boolean empty) {
	    			super.updateItem(item, empty);
	    			setText(empty ? "" : item.getDescripcion());
	    		};
	    	});   	 
	    	cmb_categoria.getSelectionModel().selectedItemProperty()
			.addListener((ObservableValue<? extends Categoria> ov, Categoria old_val, Categoria new_val) -> {
				if (cmb_categoria.getSelectionModel().getSelectedItem() != null) {
					categoria = cmb_categoria.getSelectionModel().getSelectedItem();
					ObservableList<Recurso> obsAux = FXCollections.observableArrayList();
					//utilizar solo valores del obstemporal					
					if (!listaRecurso.isEmpty()) {
						System.out.println("CATEGORIA SELECT: " + categoria);
						if (categoria.getDescripcion().equals(cat.getDescripcion())) {
							//muestra todos los valores de la lista temporal que existen para el recurso
							for (int i = 0; i < listaRecurso.size(); i++) {
								obsAux.add(listaRecurso.get(i));
							}	
						}else {
							//mostrar lista de MEDIOS por el tipo.
							for (Recurso rec : listaRecurso) {	
								if(rec.getIdsCategoria().contains(categoria.getDescripcion()))
									obsAux.add(rec);
							}
						}
						List<Recurso> auxList = new ArrayList<>();
						auxList.addAll(obsAux);
//						exitPopup = true;
						lst_listaRecursos.getItems().clear();
						cargarListaRecursos(auxList);
//						exitPopup = false;
					}
				}
			});
		}				
	}
    
    private void cargarListaRecursos(List<Recurso> lista) {
    	obsListRecurso = FXCollections.observableArrayList();
    	lst_listaRecursos.setPlaceholder(new Label("---  La lista de recursos se encuentra vacia. ---"));
		if (!lista.isEmpty()) {
    		for (int i = 0; i < lista.size(); i++) {
				obsListRecurso.add(lista.get(i));
			}			
			lst_listaRecursos.setItems(obsListRecurso);	
    		//
	    	lst_listaRecursos.setCellFactory(param -> new ListCell<Recurso>() {
	    		protected void updateItem(Recurso item, boolean empty) {
	    			super.updateItem(item, empty);
	    			setText(empty ? "" : item.getNombre() );
	    		};
	    	});  			    	
		}
		//
		lst_listaRecursos.getSelectionModel().selectedItemProperty()
			.addListener((ObservableValue<? extends Recurso> ov, Recurso old_val, Recurso new_val) -> {
				recurso = lst_listaRecursos.getSelectionModel().getSelectedItem();
				
//					if (exitPopup) {
//						return;
//					}
				
				//resaltar el punto en el mapa.
				
				lbl_nombreRecurso.setText(recurso.getNombre());
				lbl_infGeneral.setText(recurso.getInformacionGeneral());
				lbl_ubicacionZonal.setText(recurso.getDireccion());
				
				if(recurso.getIdsMediaCloudResources() != null) {
					listRespMedia = rest.exchange(uriMediaCloud + "/getAll", HttpMethod.GET, null,
							new ParameterizedTypeReference<List<MediaCloudResources>>() {
							});
					listaMedia = listRespMedia.getBody();
					for (MediaCloudResources mcr : listaMedia) {
						if(recurso.getIdsMediaCloudResources().contains(mcr.getId())) {
							if (mcr.getIsPrincipal()) {
								Image img = gcsw.getImageMediaCR(mcr.getId());
								gcsw.showMediaInContenedor(img, anch_contImgPrinBusqueda, (double) 150);
								return;
							}
						}
					}
				}
			});		
	}

    private void restoreToController() {
    	
	}
	
}
