package ec.edu.upse.facsistel.gitwym.sai.controllers;

import com.gluonhq.maps.MapPoint;
import com.gluonhq.maps.MapView;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import ec.edu.upse.facsistel.gitwym.sai.utilities.Context;
import ec.edu.upse.facsistel.gitwym.sai.utilities.General;
import ec.edu.upse.facsistel.gitwym.sai.utilities.Message;
import ec.edu.upse.facsistel.gitwym.sai.utilities.PolylineLayer;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Pair;

public class MapGluon {

    @FXML private JFXTextField txt_coordenadas;
    @FXML private JFXButton btn_graficar;
    @FXML private JFXButton btn_Guardar;
    @FXML private AnchorPane anch_Map;

	//Definimos el mapa de Gluon Maps
	MapView map = new MapView();
//	PoiLayer poi = new PoiLayer();
	PolylineLayer poi = new PolylineLayer();
	String coordenadas = "";
	
	public void initialize() {
		General.setMapatoAnchorPane(map, anch_Map);
		
		if (Context.getInstance().getCoordDeMapa() != null) {
			txt_coordenadas.setText(Context.getInstance().getCoordDeMapa());
			if (!txt_coordenadas.getText().matches("^[-+]?([1-8]?\\d(\\.\\d+)?|90(\\.0+)?),\\s*[-+]?(180(\\.0+)?|((1[0-7]\\d)|([1-9]?\\d))(\\.\\d+)?)$")) {
				Message.showErrorNotification("Ingrese una coordenada valida, por favor.!! \nEjmplo: -2.2222, -80.3333");
				return;
			}
			String coord = txt_coordenadas.getText();
			String[] array = coord.substring(coord.indexOf(""), coord.lastIndexOf("")).split(",");
			double lat = Double.parseDouble(array[0]);
			double lon = Double.parseDouble(array[1]);

			if (poi.getPoints().size() > 0) {//modifica
				poi.updatePoint(lat,lon);
			}else {//crea
				try {
					setPointMap(lat,lon);
					ObservableList<Pair<MapPoint, Node>> pair = poi.getPoints();
					for (Pair<MapPoint, Node> p : pair) {
						poi.setAuxIcon(p.getValue());
					}					
				} catch (Exception e2) {
				}
			}	
			MapPoint mapp =new MapPoint(lat, lon);
			map.setCenter(mapp);
			map.setZoom(9.5);
			map.flyTo(1., mapp, 2.);
		}else {
			MapPoint mapPoint = new MapPoint(-2.206610, -80.692470);
			map.setCenter(mapPoint);
			map.setZoom(9.5);
			map.flyTo(1., mapPoint, 2.);
		}
		map.setOnMouseClicked(e -> {
			MapPoint mapPosition = map.getMapPosition(e.getX(), e.getY());
			coordenadas = mapPosition.getLatitude() + ", " + mapPosition.getLongitude();
			Context.getInstance().setCoordenadas(coordenadas);
			if (poi.getPoints().size() > 0) {//modifica
				poi.updatePoint(mapPosition.getLatitude(), mapPosition.getLongitude());
			}else {//crea
				try {
					setPointMap(mapPosition.getLatitude(), mapPosition.getLongitude());
					ObservableList<Pair<MapPoint, Node>> pair = poi.getPoints();
					for (Pair<MapPoint, Node> p : pair) {
						poi.setAuxIcon(p.getValue());
					}					
				} catch (Exception e2) {
				}
			}
			txt_coordenadas.setText(coordenadas);	
			Context.getInstance().setCoordenadas(null);
	    });
		txt_coordenadas.setOnKeyPressed(new EventHandler<KeyEvent>(){
			@Override
			public void handle(KeyEvent ke){
				if (ke.getCode().equals(KeyCode.ENTER)){
					//validar que sea coordenada.
	    			if (!txt_coordenadas.getText().matches("^[-+]?([1-8]?\\d(\\.\\d+)?|90(\\.0+)?),\\s*[-+]?(180(\\.0+)?|((1[0-7]\\d)|([1-9]?\\d))(\\.\\d+)?)$")) {
	    				Message.showErrorNotification("Ingrese una coordenada valida, por favor.!! \nEjmplo: -2.2222, -80.3333");
	    				return;
	    			}
	    			String coord = txt_coordenadas.getText();
	    			String[] array = coord.substring(coord.indexOf(""), coord.lastIndexOf("")).split(",");
	    			double lat = Double.parseDouble(array[0]);
	    			double lon = Double.parseDouble(array[1]);

	    			if (poi.getPoints().size() > 0) {//modifica
	    				poi.updatePoint(lat,lon);
	    			}else {//crea
	    				try {
	    					setPointMap(lat,lon);
	    					ObservableList<Pair<MapPoint, Node>> pair = poi.getPoints();
	    					for (Pair<MapPoint, Node> p : pair) {
	    						poi.setAuxIcon(p.getValue());
	    					}					
	    				} catch (Exception e2) {
	    				}
	    			}			
				}
			}
		});
		
	}
	
	private void setPointMap(Double lat, Double lon) {
		poi.addPoint(new MapPoint(lat, lon), new Circle(4, Color.RED));
		map.addLayer(poi);
	}

    @FXML
    void graficarPuntoEnMapa(ActionEvent event) {
    	try {
    		if (!txt_coordenadas.getText().matches("^[-+]?([1-8]?\\d(\\.\\d+)?|90(\\.0+)?),\\s*[-+]?(180(\\.0+)?|((1[0-7]\\d)|([1-9]?\\d))(\\.\\d+)?)$")) {
				Message.showErrorNotification("Ingrese una coordenada valida, por favor.!! \nEjmplo: -2.2222, -80.3333");
				return;
			}
    		String coord = txt_coordenadas.getText();
			String[] array = coord.substring(coord.indexOf(""), coord.lastIndexOf("")).split(",");
			double lat = Double.parseDouble(array[0]);
			double lon = Double.parseDouble(array[1]);
			
			if (poi.getPoints().size() > 0) {//modifica
				poi.updatePoint(lat,lon);
			}else {//crea
				try {
					setPointMap(lat,lon);
					ObservableList<Pair<MapPoint, Node>> pair = poi.getPoints();
					for (Pair<MapPoint, Node> p : pair) {
						poi.setAuxIcon(p.getValue());
					}					
				} catch (Exception e2) {
				}
			}			
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

    @FXML
    void guardarCoordenadas(ActionEvent event) {
    	try {
    		if (txt_coordenadas.getText().isBlank() || txt_coordenadas.getText().isEmpty()) {
    			Message.showErrorNotification("Ingrese una coordenada, por favor.!!");
				return;
			}
    		if (!txt_coordenadas.getText().matches("^[-+]?([1-8]?\\d(\\.\\d+)?|90(\\.0+)?),\\s*[-+]?(180(\\.0+)?|((1[0-7]\\d)|([1-9]?\\d))(\\.\\d+)?)$")) {
				Message.showErrorNotification("Ingrese una coordenada valida, por favor.!! \nEjmplo: -2.2222, -80.3333");
				return;
			}
    		
    		Context.getInstance().setCoordDeMapa(txt_coordenadas.getText());
    		Context.getInstance().getStageModalBaseMAPA().close();
    		
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

}
