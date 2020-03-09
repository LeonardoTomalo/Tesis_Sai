package ec.edu.upse.facsistel.gitwym.sai.controllers;

import com.gluonhq.maps.MapPoint;
import com.gluonhq.maps.MapView;

import ec.edu.upse.facsistel.gitwym.sai.models.Atractivo;
import ec.edu.upse.facsistel.gitwym.sai.models.Comodidades;
import ec.edu.upse.facsistel.gitwym.sai.models.MediaCloudResources;
import ec.edu.upse.facsistel.gitwym.sai.utilities.Context;
import ec.edu.upse.facsistel.gitwym.sai.utilities.General;
import ec.edu.upse.facsistel.gitwym.sai.utilities.PoiLayerAll;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class MapShowPointsController {

    @FXML private AnchorPane anch_Mapa;    

	//Definimos el mapa de Gluon Maps
	MapView map = new MapView();
	PoiLayerAll poi = new PoiLayerAll();
	
	//Clases
	MediaCloudResources media = new MediaCloudResources();
	Atractivo atractivo = new Atractivo();
	Comodidades comodidad = new Comodidades();
	
    public void initialize() {
		General.setMapatoAnchorPane(map, anch_Mapa);
		if (Context.getInstance().getListaMediaContext() != null) {
			for (MediaCloudResources m : Context.getInstance().getListaMediaContext()) {
				String coord = m.getCoordenadas();
				String[] array = coord.substring(coord.indexOf(""), coord.lastIndexOf("")).split(",");
				double lat = Double.parseDouble(array[0]);
				double lon = Double.parseDouble(array[1]);
				poi.addPoint(new MapPoint(lat, lon), new Circle(6, Color.RED), m);				

				MapPoint mapPoint = new MapPoint(lat,lon);
				map.setCenter(mapPoint);
				map.setZoom(16);
				map.flyTo(1., mapPoint, 2.);
			}
			map.addLayer(poi);
		}else if(Context.getInstance().getListaAtractContext() != null) {
			for (Atractivo m : Context.getInstance().getListaAtractContext()) {
				String coord = m.getCoordenadas();
				String[] array = coord.substring(coord.indexOf(""), coord.lastIndexOf("")).split(",");
				double lat = Double.parseDouble(array[0]);
				double lon = Double.parseDouble(array[1]);
				poi.addPoint(new MapPoint(lat, lon), new Circle(6, Color.RED), m);				

				MapPoint mapPoint = new MapPoint(lat,lon);
				map.setCenter(mapPoint);
				map.setZoom(14);
				map.flyTo(1., mapPoint, 2.);
			}
			map.addLayer(poi);
		}else if(Context.getInstance().getListaComoContext() != null) {
			for (Comodidades m : Context.getInstance().getListaComoContext()) {
				String coord = m.getCoordenadas();
				String[] array = coord.substring(coord.indexOf(""), coord.lastIndexOf("")).split(",");
				double lat = Double.parseDouble(array[0]);
				double lon = Double.parseDouble(array[1]);
				poi.addPoint(new MapPoint(lat, lon), new Circle(6, Color.RED), m);				

				MapPoint mapPoint = new MapPoint(lat,lon);
				map.setCenter(mapPoint);
				map.setZoom(14);
				map.flyTo(1., mapPoint, 2.);
			}
			map.addLayer(poi);
		}
    }
}
