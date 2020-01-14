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

import ec.edu.upse.facsistel.gitwym.sai.models.Comodidades;
import ec.edu.upse.facsistel.gitwym.sai.models.Recurso;
import ec.edu.upse.facsistel.gitwym.sai.models.TipoComodidad;
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

public class ComodidadesController {

    @FXML private JFXComboBox<TipoComodidad> cmb_tipoComodidad;
    @FXML private JFXButton btn_verAllMapa;
    @FXML private JFXListView<Comodidades> lst_listaComodidades;
    @FXML private JFXButton btn_Nuevo;
    @FXML private JFXButton btn_Eliminar;
    @FXML private JFXButton btn_Guardar;
    @FXML private JFXTextField txt_descripcionComodidad;
    @FXML private JFXTextField txt_coordenadasComodidades;
    @FXML private JFXButton btn_buscarMapa;
    @FXML private JFXComboBox<TipoComodidad> cmb_tipoComodidadDetalle;
    @FXML private JFXComboBox<Recurso> cmb_recursos;

 // CONSUMIR WEB SERVICES
 	RestTemplate rest = new RestTemplate();
 	String uriTipoComodidad = "http://localhost:8082/tipoComodidad";
 	String uriComodidades = "http://localhost:8082/comodidades";
 	String uriRecurso = "http://localhost:8082/recurso";
     
 	// DE LA CLASE COMODIDADES
 	Comodidades comodidad = new Comodidades();
 	List<Comodidades> listaComodidades = new ArrayList<Comodidades>();
 	private static ResponseEntity<List<Comodidades>> listRespComodidades;
 	ObservableList<Comodidades> obsListComodidades = FXCollections.observableArrayList();
 	
 	// DE LA CLASE TIPOCOMODIDAD
 	TipoComodidad tipoComodidad = new TipoComodidad();
 	List<TipoComodidad> listaTipoComodidad = new ArrayList<TipoComodidad>();
 	private static ResponseEntity<List<TipoComodidad>> listRespTipoComodidad;
 	ObservableList<TipoComodidad> obsListTipoComodidad = FXCollections.observableArrayList();
 	
 	//DE LA CLASE RECURSO
 	Recurso recurso = new Recurso();
 	List<Recurso> listaRecurso = new ArrayList<Recurso>();
 	private static ResponseEntity<List<Recurso>> listRespRecurso;
 	ObservableList<Recurso> obsListRecurso = FXCollections.observableArrayList();
 
    
    public void initialize() {
		restoreToController();
		loadTipoComodidades();
		loadRecursos();
		loadComodidades();
	}	    

	@FXML
    void addNuevaComodidad(ActionEvent event) {
    	initialize();
    	comodidad = new Comodidades();
    }

    @FXML
    void eliminarComodidad(ActionEvent event) {
    	try {
			if (lst_listaComodidades.getSelectionModel().getSelectedItems().isEmpty()) {
				Message.showWarningNotification("Seleccione la comodidad a eliminar.!!");
				return;
			}
			Optional<ButtonType> result = Message.showQuestion("Desea continuar y eliminar los datos de la comodidad: "
							+ lst_listaComodidades.getSelectionModel().getSelectedItem().getDescripcion() + " ?.",
					Context.getInstance().getStage());
			if (result.get() == ButtonType.OK) {
				//Eliminar datos.
				Map<String, String> params = new HashMap<String, String>();
				params.put("c", comodidad.getId());
				rest.delete(uriComodidades + "/deletePhysical/{c}", params);
				Message.showSuccessNotification("Se eliminaron exitosamente los datos.!!");
				initialize();
			}
		} catch (Exception e) {
			e.printStackTrace();
			Message.showErrorNotification("Ha surgido un error al eliminar datos.!!");
		}
    }

    @FXML
    void guardarComodidad(ActionEvent event) {
    	try {
    		Boolean a = false;
    		Boolean b = false;
    		if(cmb_tipoComodidadDetalle.getValue() != null) a = true;
    		if(cmb_recursos.getValue() != null) b = true;
    		
			if (a.equals(false)) {
				Message.showWarningNotification("Debe seleccionar un tipo de comodidad.!!");
				return;
			}
			if (b.equals(false)) {
				Message.showWarningNotification("Debe seleccionar un recurso al que pertenece la comodidad.!!");
				return;
			}
//			if (txt_coordenadasComodidades.getText().isEmpty() || txt_coordenadasComodidades.getText().isBlank()) {
//				Message.showWarningNotification("Debe ingresar las coordenadas de la comodidad.!!");
//				return;
//			}
			Optional<ButtonType> result = Message.showQuestion("Desea continuar y grabar los datos?.",
					Context.getInstance().getStage());
			if (result.get() == ButtonType.OK) {
				comodidad.setDescripcion(txt_descripcionComodidad.getText());
				comodidad.setCoordenadas(txt_coordenadasComodidades.getText());
				comodidad.setIdTipoComodidad(cmb_tipoComodidadDetalle.getValue().getId());
				comodidad.setIdRecurso(cmb_recursos.getValue().getId());
				rest.postForObject(uriComodidades + "/saveOrUpdate", comodidad, Comodidades.class);
				Message.showSuccessNotification("Se guardaron exitosamente los datos.!! ");
				comodidad = new Comodidades();
				initialize();
			}
		} catch (Exception e) {
			e.printStackTrace();
			Message.showErrorNotification("Ha surgido un error al guardar datos.!!");
		}
    }

    @FXML
    void showAllComodidadesMapa(ActionEvent event) {
    	
    }

    @FXML
    void showMapaUbicacion(ActionEvent event) {
    	
    }

	private void loadComodidades() {
		obsListComodidades.clear();
		listRespComodidades = rest.exchange(uriComodidades + "/getAll", HttpMethod.GET, null,
				new ParameterizedTypeReference<List<Comodidades>>() {
				});
		listaComodidades = listRespComodidades.getBody();
		lst_listaComodidades.setPlaceholder(new Label("---  No se encontraron datos en la Base. ---"));
		if (!listaComodidades.isEmpty()) {
			for (int i = 0; i < listaComodidades.size(); i++) {
				obsListComodidades.add(listaComodidades.get(i));
			}			
			lst_listaComodidades.setItems(obsListComodidades);	
	    	lst_listaComodidades.setCellFactory(param -> new ListCell<Comodidades>() {
	    		protected void updateItem(Comodidades item, boolean empty) {
	    			super.updateItem(item, empty);
	    			setText(empty ? "" : item.getDescripcion() );
	    		};
	    	});  	    	
		}
		//
		lst_listaComodidades.getSelectionModel().selectedItemProperty()
				.addListener((ObservableValue<? extends Comodidades> ov, Comodidades old_val, Comodidades new_val) -> {
					if (lst_listaComodidades.getSelectionModel().getSelectedItem() != null) {
						comodidad = lst_listaComodidades.getSelectionModel().getSelectedItem();
						txt_coordenadasComodidades.setText(comodidad.getCoordenadas());
						txt_descripcionComodidad.setText(comodidad.getDescripcion());
						for (TipoComodidad tc : listaTipoComodidad) {
							if (tc.getId().equals(comodidad.getIdTipoComodidad())) 
								cmb_tipoComodidadDetalle.getSelectionModel().select(tc);
						}
						for (Recurso r : listaRecurso) {
							if (r.getId().equals(comodidad.getIdRecurso())) 
								cmb_recursos.getSelectionModel().select(r);
						}
					}
				});
	}

    private void loadTipoComodidades() {
    	listRespTipoComodidad = rest.exchange(uriTipoComodidad + "/getAll", HttpMethod.GET, null,
				new ParameterizedTypeReference<List<TipoComodidad>>() {
				});
		listaTipoComodidad = listRespTipoComodidad.getBody();
		if (!listaTipoComodidad.isEmpty()) {
			for (int i = 0; i < listaTipoComodidad.size(); i++) {
				obsListTipoComodidad.add(listaTipoComodidad.get(i));
			}			
			cmb_tipoComodidad.setItems(obsListTipoComodidad);	
			cmb_tipoComodidadDetalle.setItems(obsListTipoComodidad);	
	    	cmb_tipoComodidad.setCellFactory(param -> new ListCell<TipoComodidad>() {
	    		protected void updateItem(TipoComodidad item, boolean empty) {
	    			super.updateItem(item, empty);
	    			setText(empty ? "" : item.getDescripcion());
	    		};
	    	});   
	    	cmb_tipoComodidadDetalle.setCellFactory(param -> new ListCell<TipoComodidad>() {
	    		protected void updateItem(TipoComodidad item, boolean empty) {
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
    	cmb_tipoComodidadDetalle.getItems().clear();
    	cmb_tipoComodidad.getItems().clear();
    	txt_coordenadasComodidades.clear();
    	txt_descripcionComodidad.clear();
	}
}
