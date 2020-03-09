package ec.edu.upse.facsistel.gitwym.sai.utilities;

import org.controlsfx.control.PopOver;

import com.gluonhq.maps.MapLayer;
import com.gluonhq.maps.MapPoint;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polyline;
import javafx.stage.Stage;
import javafx.util.Pair;
import lombok.Getter;
import lombok.Setter;

public class PolylineLayer extends MapLayer{
	
	@Getter @Setter private ObservableList<Pair<MapPoint, Node>> points = FXCollections.observableArrayList();
	@Getter @Setter private Polyline line;	
	@Getter @Setter Node auxIcon;
	
	public PolylineLayer() {
		line = new Polyline();
		line.setStrokeWidth(6);
		line.setStroke(Color.CHARTREUSE);		
		this.getChildren().add(line);
	}
	
	@Override
	protected void layoutLayer() {
		line.getPoints().clear();
		for (Pair<MapPoint, Node> candidate : points) {
            MapPoint point = candidate.getKey();
            Node icon = candidate.getValue();
            Point2D mapPoint = baseMap.getMapPoint(point.getLatitude(), point.getLongitude());
            icon.setTranslateX(mapPoint.getX());
            icon.setTranslateY(mapPoint.getY());

            line.getPoints().addAll(mapPoint.getX(), mapPoint.getY());
        }
	}
	
	public void addPoint(MapPoint p, Node icon) {
		final Pair<MapPoint, Node>  pair = new Pair<>(p, icon);
		icon.setOnMouseClicked(e -> {
	        e.consume();
	        showPopup(pair);
			auxIcon = icon;
	    });
		points.add(pair);
		this.getChildren().add(icon);
		this.markDirty();
	}
	
	public void updatePoint(double lat, double lon) {
		for (Pair<MapPoint, Node> candidate : points) {
			String a = candidate.getKey().getLatitude() + ", " + candidate.getKey().getLongitude();
			if (Context.getInstance().getCoordenadas() != null) {
				if (a.equals(Context.getInstance().getCoordenadas())) {
					candidate.getKey().update(lat, lon);
					System.out.println("update: " + lat +", "+ lon);
					this.markDirty();
//					break;
				}
			}
			if (auxIcon != null) {
				if(auxIcon.equals(candidate.getValue())){
					candidate.getKey().update(lat, lon);
					System.out.println("update: " + lat +", "+ lon);
					this.markDirty();
//					break;
				}
			}
			
		}
	}
	
	public void deletePoint(String point) {
		ObservableList<Pair<MapPoint, Node>> pointsNEW = FXCollections.observableArrayList();
		for (Pair<MapPoint, Node> candidate : points) {
			String p = candidate.getKey().getLatitude() + ", " + candidate.getKey().getLongitude();
			if (auxIcon != null) {
				if(!auxIcon.equals(candidate.getValue())){	
					pointsNEW.add(candidate);
				}else {
					this.getChildren().remove(candidate.getValue());
				}
			}else if (!point.isEmpty()){
				if (!p.equals(point)) {
					pointsNEW.add(candidate);
				}else {
					this.getChildren().remove(candidate.getValue());
				}
			}
		}
		if(!points.isEmpty()) points.clear();
		points = pointsNEW;
		this.markDirty();
	}

	public static void showPopup(Pair<MapPoint, Node>  pair) {		
		Node icon = pair.getValue();
		MapPoint point = pair.getKey();
		//		
		PopOver po = new PopOver();
		//
		VBox box = new VBox(2);
		box.setPadding(new Insets(2));
		box.getChildren().addAll(new Label(""),
				new Label("Lat: " + String.format("%2.6fº", point.getLatitude())),
				new Label("Lon: " + String.format("%2.6fº", point.getLongitude())));
		box.setAlignment(Pos.TOP_CENTER);
		box.setFillWidth(true);
		po.setContentNode(box);
		
		final Stage primaryStage = (Stage) icon.getScene().getWindow();
		Bounds iconBounds = icon.localToScene(icon.getBoundsInLocal());
		po.show(primaryStage, iconBounds.getMinX(), iconBounds.getMinY());
	}
}
