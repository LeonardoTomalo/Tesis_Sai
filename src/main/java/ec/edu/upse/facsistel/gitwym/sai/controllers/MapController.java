package ec.edu.upse.facsistel.gitwym.sai.controllers;

import com.gluonhq.maps.MapPoint;
import com.gluonhq.maps.MapView;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

public class MapController {

    @FXML private AnchorPane anch_mapa;
    
    public void initialize() {
    	MapView map = new MapView();		
    	MapPoint mapPoint = new MapPoint(-2.206610, -80.692470);
    	map.setCenter(mapPoint);
        map.setZoom(10);
        map.flyTo(1., mapPoint, 2.);      
        
        if(!anch_mapa.equals(null))anch_mapa.getChildren().removeAll();	
		AnchorPane.setBottomAnchor(map, 00.0);
		AnchorPane.setLeftAnchor(map, 00.0);
		AnchorPane.setTopAnchor(map, 00.0);
		AnchorPane.setRightAnchor(map, 00.0);
		anch_mapa.getChildren().setAll(map);

	}

}
