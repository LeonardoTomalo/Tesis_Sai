package ec.edu.upse.facsistel.gitwym.sai.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
import ec.edu.upse.facsistel.gitwym.sai.utilities.HelpClass;
import ec.edu.upse.facsistel.gitwym.sai.utilities.Message;
import ec.edu.upse.facsistel.gitwym.sai.utilities.PoiLayer;
import ec.edu.upse.facsistel.gitwym.sai.utilities.PropertyManager;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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

	//Definimos el mapa de Gluon Maps
	MapView map = new MapView();
	PoiLayer poi = new PoiLayer();

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
	List<MediaCloudResources> listaMedia = new ArrayList<MediaCloudResources>();
	private static ResponseEntity<List<MediaCloudResources>> listRespMedia;
	GoogleCloudStorageWorker gcsw = new GoogleCloudStorageWorker();


	public void initialize() {	
		gcsw.showMediaInContenedor(new Image("albums.png",140,300,true,false), anch_contImgPrinBusqueda, (double) 150);
		listasCellFactory();
		General.setMapatoAnchorPane(map, anch_mapa);
		//
		MapPoint mapPoint = new MapPoint(-2.206610, -80.692470);
		map.setCenter(mapPoint);
		map.setZoom(9.5);
		map.flyTo(1., mapPoint, 2.);
		//
		restoreToController();
		buscarPorNombre();
		loadIdiomas();
		loadCategorias();
		loadRecursos();
	}        

	@FXML
	void crearRecurso(ActionEvent event) {
		Context.getInstance().setRecursoContext(null);
		General.setContentParent("/viewRecurso/Recurso.fxml", Context.getInstance().getAnch_Contenido());
		Context.getInstance().setMapViewContext(map);
	}

	@FXML
	void eliminarRecurso(ActionEvent event) {
		try {
			if (lst_listaRecursos.getSelectionModel().getSelectedItems().isEmpty()) {
				Message.showWarningNotification("Seleccione el recurso a eliminar.!!");
				return;
			}
			Optional<ButtonType> result = Message.showQuestion("Desea continuar y eliminar los datos del recurso: "
					+ lst_listaRecursos.getSelectionModel().getSelectedItem().getNombre() + " ?.",
					Context.getInstance().getStage());
			if (result.get() == ButtonType.OK) {
				recurso = lst_listaRecursos.getSelectionModel().getSelectedItem();
				if (lst_listaRecursos.getSelectionModel().getSelectedItem().getId() != null) {
					Map<String, String> params = new HashMap<String, String>();
					params.put("c", lst_listaRecursos.getSelectionModel().getSelectedItem().getId());
					rest.delete(uriRecurso + "/delete/{c}", params);
				}
				if (listaRecurso.size() > 0) 	
					listaRecurso.remove(recurso);
				if (lst_listaRecursos.getItems().size() > 0)	{
					lst_listaRecursos.getItems().remove(recurso);
				}				
				Message.showSuccessNotification("Se eliminaron exitosamente los datos.!!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	void modificarRecurso(ActionEvent event) {
		try {
			if (lst_listaRecursos.getSelectionModel().getSelectedItems().isEmpty()) {
				Message.showWarningNotification("Seleccione el recurso a modificar.!!");
				return;
			}
			Context.getInstance().setRecursoContext(lst_listaRecursos.getSelectionModel().getSelectedItem());
			General.setContentParent("/viewRecurso/Recurso.fxml", Context.getInstance().getAnch_Contenido()); 
		} catch (Exception e) {
			e.printStackTrace();			
		}		       	
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
			cargarListaRecursos(obsAux);
			gcsw.showMediaInContenedor(new Image("albums.png",140,300,true,false), anch_contImgPrinBusqueda, (double) 150);
			if (newValue.isBlank() || newValue.isEmpty()) {
				initialize();
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
			setPointGraphMap(obsListRecurso);
		}		
	}

	private void restoreToController() {
		if (cmb_categoria.getItems() != null) cmb_categoria.getItems().clear();
		if (cmb_idioma.getItems() != null) cmb_idioma.getItems().clear();	
		gcsw.showMediaInContenedor(new Image("albums.png",140,300,true,false), anch_contImgPrinBusqueda, (double) 150);
	}

	private void setPointGraphMap(List<Recurso> lista) {
		poi = new PoiLayer();
		System.out.println("1 " + lista);
		for (Recurso r : lista) {
			if (r.getCoordenadas() != null) {
				//convert coord
				String coord = r.getCoordenadas();
				String[] array = coord.substring(coord.indexOf(""), coord.lastIndexOf("")).split(",");
				double lat = Double.parseDouble(array[0]);
				double lon = Double.parseDouble(array[1]);
				//Image Point
				Image icon = new Image("placeholder-3.png", 32, 32, true, true);
				Node node = new ImageView(icon);
				//
				poi.addPoint(new MapPoint(lat, lon), node, r);
				//
			}
		}
		System.out.println("1");
		map.addLayer(poi);
	}

	private void listasCellFactory() {
		//RECURSOS
		lst_listaRecursos.setCellFactory(param -> new ListCell<Recurso>() {
			protected void updateItem(Recurso item, boolean empty) {
				super.updateItem(item, empty);
				setText(empty ? "" : item.getNombre() );
			};
		});  	
		//
		lst_listaRecursos.getSelectionModel().selectedItemProperty()
		.addListener((ObservableValue<? extends Recurso> ov, Recurso old_val, Recurso new_val) -> {
			recurso = lst_listaRecursos.getSelectionModel().getSelectedItem();
			//crear popover mostrando contenido de recurso sobre coordenadas
			for (HelpClass<MapPoint, Node, Recurso> hc : poi.getPoint()) {
				if (hc.getConstant().equals(recurso)) {
					poi.showPopover(hc);
					break;
				}
			}
			//
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
		//RECURSOS FIN
		//IDIOMAS
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
					if (idiomas.getDescripcion().equals("Todos los Idiomas")) {
						//muestra todos los valores de la lista temporal que existen para el recurso
						for (int i = 0; i < listaRecurso.size(); i++) {
							obsAux.add(listaRecurso.get(i));
						}	
					}else {
						//mostrar lista de MEDIOS por el tipo.
						for (Recurso rec : listaRecurso) {	
							if(rec.getIdiomas().contains(idiomas.getId()))
								obsAux.add(rec);
						}
					}
					List<Recurso> auxList = new ArrayList<>();
					auxList.addAll(obsAux);
					lst_listaRecursos.getItems().clear();
					cargarListaRecursos(auxList);
				}
			}
		});
		//IDIOMAS FIN
		//CATEGORIAS 
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
					if (categoria.getDescripcion().equals("Todas las Categorias")) {
						//muestra todos los valores de la lista temporal que existen para el recurso
						for (int i = 0; i < listaRecurso.size(); i++) {
							obsAux.add(listaRecurso.get(i));
						}	
					}else {
						//mostrar lista de MEDIOS por el tipo.
						for (Recurso rec : listaRecurso) {	
							if(rec.getIdsCategoria().contains(categoria.getId()))
								obsAux.add(rec);
						}
					}
					List<Recurso> auxList = new ArrayList<>();
					auxList.addAll(obsAux);
					lst_listaRecursos.getItems().clear();
					cargarListaRecursos(auxList);
				}
			}
		});
		//CATEGORIAS FIN
	}
}
