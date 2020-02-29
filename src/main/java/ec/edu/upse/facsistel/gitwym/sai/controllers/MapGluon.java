package ec.edu.upse.facsistel.gitwym.sai.controllers;

import com.gluonhq.maps.MapPoint;
import com.gluonhq.maps.MapView;

import ec.edu.upse.facsistel.gitwym.sai.utilities.General;
import ec.edu.upse.facsistel.gitwym.sai.utilities.PoiLayer;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class MapGluon {

    @FXML
    private AnchorPane anch_Map;

	//Definimos el mapa de Gluon Maps
	MapView map = new MapView();
	PoiLayer poi = new PoiLayer();
	
	public void initialize() {	
		General.setMapatoAnchorPane(map, anch_Map);
		MapPoint mapPoint = new MapPoint(-2.206610, -80.692470);
		map.setCenter(mapPoint);
		map.setZoom(9.5);
		map.flyTo(1., mapPoint, 2.);
		
		map.setOnMouseClicked(e -> {
	        MapPoint mapPosition = map.getMapPosition(e.getSceneX(), e.getSceneY());
	        System.out.println("mapPosition: " + mapPosition.getLatitude()+ ", " + mapPosition.getLongitude());
	        setPointMap(mapPosition.getLatitude(), mapPosition.getLongitude());
	    });
		
	}
	
	private void setPointMap(Double lat, Double lon) {
		Image icon = new Image("placeholder-3.png", 32, 32, true, true);
		Node node = new ImageView(icon);
		//
		poi.addPoint(new MapPoint(lat, lon), node);
		map.addLayer(poi);
	}
	
	
}
