package ec.edu.upse.facsistel.gitwym.sai.utilities;

import org.controlsfx.control.PopOver;

import com.gluonhq.maps.MapLayer;
import com.gluonhq.maps.MapPoint;

import ec.edu.upse.facsistel.gitwym.sai.models.Atractivo;
import ec.edu.upse.facsistel.gitwym.sai.models.Comodidades;
import ec.edu.upse.facsistel.gitwym.sai.models.MediaCloudResources;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class PoiLayerAll extends MapLayer{

	private final ObservableList<HelpClass<MapPoint, Node, MediaCloudResources>> poiMedia = FXCollections.observableArrayList();
	private final ObservableList<HelpClass<MapPoint, Node, Atractivo>> poiAtrac = FXCollections.observableArrayList();
	private final ObservableList<HelpClass<MapPoint, Node, Comodidades>> poiComod = FXCollections.observableArrayList();

	public PoiLayerAll() {}	

	@Override
	protected void layoutLayer() {
		for (HelpClass<MapPoint, Node, MediaCloudResources> candidate : poiMedia) {
			MapPoint point = candidate.getKey();
			Node icon = candidate.getValue();
			Point2D mapPoint = getMapPoint(point.getLatitude(), point.getLongitude());
			icon.setVisible(true);
			icon.setTranslateX(mapPoint.getX());
			icon.setTranslateY(mapPoint.getY());
		}
		for (HelpClass<MapPoint, Node, Atractivo> candidate : poiAtrac) {
			MapPoint point = candidate.getKey();
			Node icon = candidate.getValue();
			Point2D mapPoint = getMapPoint(point.getLatitude(), point.getLongitude());
			icon.setVisible(true);
			icon.setTranslateX(mapPoint.getX());
			icon.setTranslateY(mapPoint.getY());
		}
		for (HelpClass<MapPoint, Node, Comodidades> candidate : poiComod) {
			MapPoint point = candidate.getKey();
			Node icon = candidate.getValue();
			Point2D mapPoint = getMapPoint(point.getLatitude(), point.getLongitude());
			icon.setVisible(true);
			icon.setTranslateX(mapPoint.getX());
			icon.setTranslateY(mapPoint.getY());
		}
	}	

	public void addPoint(MapPoint p, Node icon, MediaCloudResources media) {
	    final HelpClass<MapPoint, Node, MediaCloudResources>  pair = new HelpClass<>(p, icon, media);
		icon.setOnMouseClicked(e -> {
	        e.consume();
	        //showPopover(pair);
	        Node icone = pair.getValue();
			MediaCloudResources r = pair.getConstant();		
			//
			PopOver po = new PopOver();
			//
			VBox vb = new VBox(2);
			vb.setPadding(new Insets(2));
			vb.getChildren().addAll(new Label("INFORMACIÓN"), 
					new Label(r.getNombre()),
					new Label(r.getCoordenadas()),
					new Label(r.getAutor()));
			vb.setAlignment(Pos.TOP_CENTER);
			vb.setFillWidth(true);
			po.setContentNode(vb);
			
//			final Stage primaryStage = (Stage) icone.getScene().getWindow();
			Bounds iconBounds = icone.localToScene(icone.getBoundsInLocal());
			po.show(Context.getInstance().getStageModalBaseMAPAPOINT(), iconBounds.getMaxX(), iconBounds.getMaxY());
	    });
		poiMedia.add(pair);
		this.getChildren().add(icon);
		this.markDirty();
	}
	
	public void addPoint(MapPoint p, Node icon, Atractivo atrac) {
	    final HelpClass<MapPoint, Node, Atractivo>  pair = new HelpClass<>(p, icon, atrac);
		icon.setOnMouseClicked(e -> {
	        e.consume();
//	        showPopover(pair);
	        Node icone = pair.getValue();
			Atractivo r = pair.getConstant();		
			//
			PopOver po = new PopOver();
			//
			VBox vb = new VBox(2);
			vb.setPadding(new Insets(2));
			vb.getChildren().addAll(new Label("INFORMACIÓN"), 
					new Label(r.getNombre()),
					new Label(r.getCoordenadas())
//					new Label(r.getDescripcion())
					);
			vb.setAlignment(Pos.TOP_CENTER);
			vb.setFillWidth(true);
			po.setContentNode(vb);
			
			final Stage primaryStage = (Stage) icone.getScene().getWindow();
			Bounds iconBounds = icone.localToScene(icone.getBoundsInLocal());
			po.show(Context.getInstance().getStageModalBaseMAPAPOINT(), iconBounds.getMaxX(), iconBounds.getMaxY());
	    });
		poiAtrac.add(pair);
		this.getChildren().add(icon);
		this.markDirty();
	}
	
	public void addPoint(MapPoint p, Node icon, Comodidades como) {
	    final HelpClass<MapPoint, Node, Comodidades>  pair = new HelpClass<>(p, icon, como);
		icon.setOnMouseClicked(e -> {
	        e.consume();
//	        showPopover(pair);
	        Node icone = pair.getValue();
			Comodidades r = pair.getConstant();		
			//
			PopOver po = new PopOver();
			//
			VBox vb = new VBox(2);
			vb.setPadding(new Insets(2));
			vb.getChildren().addAll(new Label("INFORMACIÓN"), 
					new Label(r.getDescripcion()),
					new Label(r.getCoordenadas())
//					new Label(r.getIdTipoComodidad())
					);
			vb.setAlignment(Pos.TOP_CENTER);
			vb.setFillWidth(true);
			po.setContentNode(vb);
			
			final Stage primaryStage = (Stage) icone.getScene().getWindow();
			Bounds iconBounds = icone.localToScene(icone.getBoundsInLocal());
			po.show(Context.getInstance().getStageModalBaseMAPAPOINT(), iconBounds.getMaxX(), iconBounds.getMaxY());
	    });
		poiComod.add(pair);
		this.getChildren().add(icon);
		this.markDirty();
	}	
	
}
