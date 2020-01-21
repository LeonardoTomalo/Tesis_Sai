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

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;

import ec.edu.upse.facsistel.gitwym.sai.models.Atractivo;
import ec.edu.upse.facsistel.gitwym.sai.models.Imagen;
import ec.edu.upse.facsistel.gitwym.sai.models.Recurso;
import ec.edu.upse.facsistel.gitwym.sai.models.TipoAtractivo;
import ec.edu.upse.facsistel.gitwym.sai.utilities.Context;
import ec.edu.upse.facsistel.gitwym.sai.utilities.Message;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.ImageView;

public class AtractivosController {

    @FXML private JFXComboBox<TipoAtractivo> cmb_tipoAtractivos;
    @FXML private JFXComboBox<TipoAtractivo> cmb_tipoAtractivoDetalles;
    @FXML private JFXButton btn_verAllMapa;
    @FXML private JFXListView<Atractivo> lst_listaAtractivos;
    @FXML private JFXButton btn_Nuevo;
    @FXML private JFXButton btn_Eliminar;
    @FXML private JFXButton btn_Guardar;
    @FXML private JFXTextField txt_descripcionAtractivo;
    @FXML private JFXTextField txt_coordenadasAtractivos;
    @FXML private JFXButton btn_buscarMapa;
    @FXML private JFXComboBox<Recurso> cmb_recursos;
    @FXML private JFXTextField txt_nombreAtractivo;
    @FXML private JFXListView<Imagen> lst_listaImagenes;
    @FXML private ImageView img_imagenAtractivo;
    @FXML private JFXButton btn_addImagen;
    @FXML private JFXButton btn_modificarImagen;
    @FXML private JFXButton btn_eliminarImagen;

    // CONSUMIR WEB SERVICES
	RestTemplate rest = new RestTemplate();
	String uriTipoAtractivo = "http://localhost:8082/tipoAtractivo";
	String uriAtractivo = "http://localhost:8082/atractivo";
	String uriRecurso = "http://localhost:8082/recurso";
	String uriImagen = "http://localhost:8082/imagen";
    
	// DE LA CLASE ATRACTIVOS
	Atractivo atractivo = new Atractivo();
	List<Atractivo> listaAtractivo = new ArrayList<Atractivo>();
	private static ResponseEntity<List<Atractivo>> listRespAtractivo;
	ObservableList<Atractivo> obsListAtractivo = FXCollections.observableArrayList();
	
	// DE LA CLASE TIPOATRACTIVO
	TipoAtractivo tipoAtractivo = new TipoAtractivo();
	List<TipoAtractivo> listaTipoAtractivo = new ArrayList<TipoAtractivo>();
	private static ResponseEntity<List<TipoAtractivo>> listRespTipoAtractivo;
	ObservableList<TipoAtractivo> obsListTipoAtractivo = FXCollections.observableArrayList();
	
	//DE LA CLASE RECURSO
	Recurso recurso = new Recurso();
	List<Recurso> listaRecurso = new ArrayList<Recurso>();
	private static ResponseEntity<List<Recurso>> listRespRecurso;
	ObservableList<Recurso> obsListRecurso = FXCollections.observableArrayList();

	//DE LA CLASE IMAGEN
	Imagen imagen = new Imagen();
	List<Imagen> listaImagen = new ArrayList<Imagen>();
	private static ResponseEntity<List<Imagen>> listRespImagen;
	ObservableList<Imagen> obsListImagen = FXCollections.observableArrayList();
		
		
	public void initialize() {
		restoreToController();
		loadTipoAtractivos();
		loadRecursos();
		loadAtractivos();
	}	    

    @FXML
    void addNuevaAtractivos(ActionEvent event) {
    	initialize();
    	atractivo = new Atractivo();
    }

    @FXML
    void eliminarAtractivos(ActionEvent event) {
    	try {
			if (lst_listaAtractivos.getSelectionModel().getSelectedItems().isEmpty()) {
				Message.showWarningNotification("Seleccione el atractivo a eliminar.!!");
				return;
			}
			Optional<ButtonType> result = Message.showQuestion("Desea continuar y eliminar los datos del atractivo: "
							+ lst_listaAtractivos.getSelectionModel().getSelectedItem().getNombre() + " ?.",
					Context.getInstance().getStage());
			if (result.get() == ButtonType.OK) {
				//Eliminar datos.
				Map<String, String> params = new HashMap<String, String>();
				params.put("c", atractivo.getId());
				rest.delete(uriAtractivo + "/delete/{c}", params);
				Message.showSuccessNotification("Se eliminaron exitosamente los datos.!!");
				initialize();
			}
		} catch (Exception e) {
			e.printStackTrace();
			Message.showErrorNotification("Ha surgido un error al eliminar datos.!!");
		}
    }

    @FXML
    void guardarAtractivos(ActionEvent event) {
    	try {
    		Boolean a = false;
    		Boolean b = false;
    		if(cmb_tipoAtractivoDetalles.getValue() != null) a = true;
    		if(cmb_recursos.getValue() != null) b = true;
    		
			if (a.equals(false)) {
				Message.showWarningNotification("Debe seleccionar un tipo de atractivo.!!");
				return;
			}
			if (b.equals(false)) {
				Message.showWarningNotification("Debe seleccionar un recurso al que pertenece el atractivo.!!");
				return;
			}
//			if (txt_coordenadasComodidades.getText().isEmpty() || txt_coordenadasComodidades.getText().isBlank()) {
//				Message.showWarningNotification("Debe ingresar las coordenadas de la comodidad.!!");
//				return;
//			}
			Optional<ButtonType> result = Message.showQuestion("Desea continuar y grabar los datos?.",
					Context.getInstance().getStage());
			if (result.get() == ButtonType.OK) {
				atractivo.setDescripcion(txt_descripcionAtractivo.getText());
				atractivo.setCoordenadas(txt_coordenadasAtractivos.getText());
				atractivo.setNombre(txt_nombreAtractivo.getText());
				atractivo.setIdTipoAtractivo(cmb_tipoAtractivoDetalles.getValue().getId());
				atractivo.setIdRecurso(cmb_recursos.getValue().getId());
				atractivo.setEstado(true);
//				atractivo.setImagenes(lst_listaImagenes.getItems());						
				rest.postForObject(uriAtractivo + "/saveOrUpdate", atractivo, Atractivo.class);
				Message.showSuccessNotification("Se guardaron exitosamente los datos.!! ");
				atractivo = new Atractivo();
				initialize();
			}
		} catch (Exception e) {
			e.printStackTrace();
			Message.showErrorNotification("Ha surgido un error al guardar datos.!!");
		}
    }

    @FXML
    void agregarImagen(ActionEvent event) {

    }

    @FXML
    void eliminarImagen(ActionEvent event) {

    }

    @FXML
    void modificarImagen(ActionEvent event) {

    }

    @FXML
    void showAllAtractivosMapa(ActionEvent event) {

    }

    @FXML
    void showMapaUbicacion(ActionEvent event) {

    }

	private void loadAtractivos() {
		obsListAtractivo.clear();
		listRespAtractivo = rest.exchange(uriAtractivo + "/getAll", HttpMethod.GET, null,
				new ParameterizedTypeReference<List<Atractivo>>() {
				});
		listaAtractivo = listRespAtractivo.getBody();
		lst_listaAtractivos.setPlaceholder(new Label("---  No se encontraron datos en la Base. ---"));
		if (!listaAtractivo.isEmpty()) {
			for (int i = 0; i < listaAtractivo.size(); i++) {
				obsListAtractivo.add(listaAtractivo.get(i));
			}			
			lst_listaAtractivos.setItems(obsListAtractivo);	
	    	lst_listaAtractivos.setCellFactory(param -> new ListCell<Atractivo>() {
	    		protected void updateItem(Atractivo item, boolean empty) {
	    			super.updateItem(item, empty);
	    			setText(empty ? "" : item.getNombre() );
	    		};
	    	});  	    	
		}
		//
		lst_listaAtractivos.getSelectionModel().selectedItemProperty()
				.addListener((ObservableValue<? extends Atractivo> ov, Atractivo old_val, Atractivo new_val) -> {
					if (lst_listaAtractivos.getSelectionModel().getSelectedItem() != null) {
						atractivo = lst_listaAtractivos.getSelectionModel().getSelectedItem();
						txt_coordenadasAtractivos.setText(atractivo.getCoordenadas());
						txt_descripcionAtractivo.setText(atractivo.getDescripcion());
						txt_nombreAtractivo.setText(atractivo.getNombre());
						for (TipoAtractivo tc : listaTipoAtractivo) {
							if (tc.getId().equals(atractivo.getIdTipoAtractivo())) 
								cmb_tipoAtractivoDetalles.getSelectionModel().select(tc);
						}
						for (Recurso r : listaRecurso) {
							if (r.getId().equals(atractivo.getIdRecurso())) 
								cmb_recursos.getSelectionModel().select(r);
						}
						//cargar las imagenes.
					}
				});
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
			cmb_tipoAtractivos.setItems(obsListTipoAtractivo);	
			cmb_tipoAtractivoDetalles.setItems(obsListTipoAtractivo);	
	    	cmb_tipoAtractivos.setCellFactory(param -> new ListCell<TipoAtractivo>() {
	    		protected void updateItem(TipoAtractivo item, boolean empty) {
	    			super.updateItem(item, empty);
	    			setText(empty ? "" : item.getDescripcion());
	    		};
	    	});   
	    	cmb_tipoAtractivoDetalles.setCellFactory(param -> new ListCell<TipoAtractivo>() {
	    		protected void updateItem(TipoAtractivo item, boolean empty) {
	    			super.updateItem(item, empty);
	    			setText(empty ? "" : item.getDescripcion());
	    		};
	    	}); 
		}				
	}

	private void loadRecursos() {
		listRespRecurso = rest.exchange(uriRecurso + "/getAll", HttpMethod.GET, null,
				new ParameterizedTypeReference<List<Recurso>>() {
				});
		listaRecurso = listRespRecurso.getBody();
		if (!listaRecurso.isEmpty()) {
			for (int i = 0; i < listaRecurso.size(); i++) {
				obsListRecurso.add(listaRecurso.get(i));
			}			
			cmb_recursos.setItems(obsListRecurso);	
	    	cmb_recursos.setCellFactory(param -> new ListCell<Recurso>() {
	    		protected void updateItem(Recurso item, boolean empty) {
	    			super.updateItem(item, empty);
	    			setText(empty ? "" : item.getNombre());
	    		};
	    	});   
		}		
		
	}
    
    private void restoreToController() {
    	cmb_recursos.getItems().clear();
    	cmb_tipoAtractivoDetalles.getItems().clear();
    	cmb_tipoAtractivos.getItems().clear();
    	txt_coordenadasAtractivos.clear();
    	txt_descripcionAtractivo.clear();
    	txt_nombreAtractivo.clear();
	}
}
