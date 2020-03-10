package ec.edu.upse.facsistel.gitwym.sai.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.jfoenix.controls.JFXListView;

import ec.edu.upse.facsistel.gitwym.sai.models.Recurso;
import ec.edu.upse.facsistel.gitwym.sai.utilities.PropertyManager;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Accordion;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.TitledPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class ReporteGeneralController {

    @FXML private JFXListView<Recurso> lst_listaRecursos;
    @FXML private Accordion accd_agre;
    @FXML private TitledPane tlp_a;
    @FXML private JFXListView<String> lst_agregados;
    @FXML private Accordion accd_fal;
    @FXML private TitledPane tlp_F;
    @FXML private JFXListView<String> lst_faltantes;
    
    // CONSUMIR WEB SERVICES
 	RestTemplate rest = new RestTemplate();
 	String urlBase = PropertyManager.getBaseUrl();
 	String uriRecurso = urlBase + "/recurso";
 	
 	// DE LA CLASE RECURSO
 	Recurso recurso = new Recurso();
 	List<Recurso> listaRecurso = new ArrayList<Recurso>();
 	private static ResponseEntity<List<Recurso>> listRespRecurso;
 	ObservableList<Recurso> obsListRecurso = FXCollections.observableArrayList();

 	
    public void initialize() {
    	listasCellFactory();
    	loadRecursos();
    	accd_agre.setExpandedPane(tlp_a);
    	accd_fal.setExpandedPane(tlp_F);
    }
    
    private void loadRecursos() {
		if(!obsListRecurso.isEmpty()) obsListRecurso.clear();
		listRespRecurso = rest.exchange(uriRecurso + "/getAll", HttpMethod.GET, null,
				new ParameterizedTypeReference<List<Recurso>>() {});
		listaRecurso = listRespRecurso.getBody();
		cargarListaRecursos(listaRecurso);
	}
    
    private void cargarListaRecursos(List<Recurso> lista) {
		obsListRecurso = FXCollections.observableArrayList();
		lst_listaRecursos.setPlaceholder(new Label("---  La lista de recursos se encuentra vacia. ---"));
		if (!lista.isEmpty()) {
			for (int i = 0; i < lista.size(); i++) {
				obsListRecurso.add(lista.get(i));
			}			
			lst_listaRecursos.setItems(obsListRecurso);
		}		
	}
    private void listasCellFactory() {
    	lst_listaRecursos.setPlaceholder(new Label("---  La lista de recursos se encuentra vacia. ---"));
    	lst_agregados.setPlaceholder(new Label("---  La lista de datos se encuentra vacia. ---"));
    	lst_faltantes.setPlaceholder(new Label("---  La lista de datos se encuentra vacia. ---"));
		
    	lst_listaRecursos.setCellFactory(param -> new ListCell<Recurso>() {		
    		@Override
    		protected void updateItem(Recurso item, boolean empty) {
				super.updateItem(item, empty);
				setText(empty ? "" : item.getNombre() );
				Circle cir = new Circle(5);
				
				if (item != null) {				
					if (isRecursoCompleto(item)) cir.setFill(Color.LIME);
					else cir.setFill(Color.DARKORANGE);	
					setGraphic(cir);		
				} else {
					setGraphic(null);
				}				
			};			
		});  	
    	
    	lst_listaRecursos.getSelectionModel().selectedItemProperty()
		.addListener((ObservableValue<? extends Recurso> ov, Recurso old_val, Recurso new_val) -> {
			recurso = lst_listaRecursos.getSelectionModel().getSelectedItem();
			
			ObservableList<String> obsAgg = FXCollections.observableArrayList();
			ObservableList<String> obsFal = FXCollections.observableArrayList();			
			
			if(lst_agregados.getItems() != null) lst_agregados.getItems().clear();
			if(lst_faltantes.getItems() != null) lst_faltantes.getItems().clear();
			
			if (recurso.getNombre().isEmpty() || recurso.getNombre().isBlank())	obsFal.add("Nombre de Recurso");
			else obsAgg.add("Nombre de Recurso");
			if (recurso.getInformacionGeneral().isEmpty() || recurso.getInformacionGeneral().isBlank())	obsFal.add("Informacion General");
			else obsAgg.add("Informacion General");
			if (recurso.getDescripcion().isEmpty() || recurso.getDescripcion().isBlank())	obsFal.add("Descripción de Recurso");
			else obsAgg.add("Descripción de Recurso");
			if (recurso.getDireccion().isEmpty() || recurso.getDireccion().isBlank())	obsFal.add("Dirección de Recurso");
			else obsAgg.add("Dirección de Recurso");
			if (recurso.getCoordenadas().isEmpty() || recurso.getCoordenadas().isBlank())	obsFal.add("Coordenadas de Recurso");
			else obsAgg.add("Coordenadas de Recurso");
			if (recurso.getPropietario().isEmpty() || recurso.getPropietario().isBlank())	obsFal.add("Propietario de Recurso");
			else obsAgg.add("Propietario de Recurso");
			
			if (recurso.getCostoServicio() != null) {
				if (recurso.getCostoServicio().size() <= 0) obsFal.add("Servicios con Costo");
				else obsAgg.add("Servicios con Costo : " + recurso.getCostoServicio().size());
			}else {	obsFal.add("Servicios con Costo");}
			if (recurso.getIdsSenderos() != null) {
				if (recurso.getIdsSenderos().size() <= 0) obsFal.add("Senderos de Recurso");
				else obsAgg.add("Senderos de Recurso : " + recurso.getIdsSenderos().size());
			}else {	obsFal.add("Senderos de Recurso");}
			if (recurso.getIdsCategoria() != null) {
				if (recurso.getIdsCategoria().size() <= 0) obsFal.add("Categorias del Recurso");
				else obsAgg.add("Categorias del Recurso : " + recurso.getIdsCategoria().size());
			}else {	obsFal.add("Categorias del Recurso");}
			if (recurso.getIdsAccesibilidades() != null) {
				if (recurso.getIdsAccesibilidades().size() <= 0) obsFal.add("Accesibilidad al Recuro");
				else obsAgg.add("Accesibilidad al Recuro: " + recurso.getIdsAccesibilidades().size());
			}else {	obsFal.add("Accesibilidad al Recuro");}
			if (recurso.getIdiomas() != null) {
				if (recurso.getIdiomas().size() <= 0)obsFal.add("Idiomas del Recurso");
				else obsAgg.add("Idiomas del Recurso : " + recurso.getIdiomas().size());
			}else {	obsFal.add("Idiomas del Recurso");}
			if (recurso.getContactos() != null) {
				if (recurso.getContactos().size() <= 0)obsFal.add("Contactos del Recurso ");
				else obsAgg.add("Contactos del Recurso : " + recurso.getContactos().size());
			}else {	obsFal.add("Contactos del Recurso ");}
			if (recurso.getIdsMediaCloudResources() != null) {
				if (recurso.getIdsMediaCloudResources().size() <= 0) obsFal.add("Contenido Multimedia");
				else obsAgg.add("Contenido Multimedia : " + recurso.getIdsMediaCloudResources().size());
			}else {	obsFal.add("Contenido Multimedia");}
			
			lst_agregados.setItems(obsAgg);
			lst_faltantes.setItems(obsFal);
			
		});
    }
    
    /**Metodo que devuelve true si el recurso esta completo todos los datos y false si es lo contrario.
     * @param Recurso
     * @return true or false segun el recurso esté completo
     */
    public Boolean isRecursoCompleto(Recurso r) {
    	if (r != null) {			
    		if (r.getNombre().isEmpty() || r.getNombre().isBlank())	return false;
			if (r.getInformacionGeneral().isEmpty() || r.getInformacionGeneral().isBlank())	return false;
    		if (r.getDescripcion().isEmpty() || r.getDescripcion().isBlank())	return false;
    		if (r.getDireccion().isEmpty() || r.getDireccion().isBlank())	return false;
    		if (r.getCoordenadas().isEmpty() || r.getCoordenadas().isBlank())	return false;
    		if (r.getPropietario().isEmpty() || r.getPropietario().isBlank())	return false;
//    		if (r.getIdLocalizacion().isEmpty() || r.getIdLocalizacion().isBlank())	return false;
    		if (r.getCostoServicio() != null) {}else {	return false;}
			if (r.getCostoServicio().size() <= 0) return false;
			if (r.getIdsSenderos() != null) {}else {	return false;}
			if (r.getIdsSenderos().size() <= 0) return false;
			if (r.getIdsCategoria() != null) {}else {	return false;}
			if (r.getIdsCategoria().size() <= 0) return false;
			if (r.getIdsAccesibilidades() != null) {}else {	return false;}
			if (r.getIdsAccesibilidades().size() <= 0) return false;
			if (r.getIdiomas() != null) {}else {	return false;}
			if (r.getIdiomas().size() <= 0) return false;
			if (r.getContactos() != null) {}else {	return false;}
			if (r.getContactos().size() <= 0) return false;
			if (r.getIdsMediaCloudResources() != null) {}else {	return false;}
			if (r.getIdsMediaCloudResources().size() <= 0) return false;
		}
    	return true;
    }

}
