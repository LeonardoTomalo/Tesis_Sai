package ec.edu.upse.facsistel.gitwym.sai.utilities;

import org.controlsfx.control.PopOver;

import com.gluonhq.maps.MapLayer;
import com.gluonhq.maps.MapPoint;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXButton.ButtonType;

import ec.edu.upse.facsistel.gitwym.sai.models.Recurso;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Pair;

public class PoiLayer extends MapLayer {

	private final ObservableList<Pair<MapPoint, Node>> points = FXCollections.observableArrayList();

	private final ObservableList<HelpClass<MapPoint, Node, Recurso>> point = FXCollections.observableArrayList();
//	private final Line line;
	
	public PoiLayer() {
	}

	public void addPoint(MapPoint p, Node icon) {
	    final Pair<MapPoint, Node>  pair = new Pair<>(p, icon);
		icon.setOnMouseClicked(e -> {
	        e.consume();
	        showPopup(pair);
	    });
		points.add(pair);
		this.getChildren().add(icon);
		this.markDirty();
	}
	
	public void addPoint(MapPoint p, Node icon, Recurso recurso) {
	    final HelpClass<MapPoint, Node, Recurso>  pair = new HelpClass<>(p, icon, recurso);
		icon.setOnMouseClicked(e -> {
	        e.consume();
	        showPopover(pair, e);
	    });
		point.add(pair);
		this.getChildren().add(icon);
		this.markDirty();
	}

	@Override
	protected void layoutLayer() {
		for (Pair<MapPoint, Node> candidate : points) {
			MapPoint point = candidate.getKey();
			Node icon = candidate.getValue();
			Point2D mapPoint = getMapPoint(point.getLatitude(), point.getLongitude());
			icon.setVisible(true);
			icon.setTranslateX(mapPoint.getX());
			icon.setTranslateY(mapPoint.getY());
		}
		for (HelpClass<MapPoint, Node, Recurso> candidate : point) {
			MapPoint point = candidate.getKey();
			Node icon = candidate.getValue();
			Point2D mapPoint = getMapPoint(point.getLatitude(), point.getLongitude());
			icon.setVisible(true);
			icon.setTranslateX(mapPoint.getX());
			icon.setTranslateY(mapPoint.getY());
		}
	}
	

	public static void showPopup(Pair<MapPoint, Node>  pair) {		
		Node icon = pair.getValue();
		MapPoint point = pair.getKey();
		//		
		final Stage primaryStage = (Stage) icon.getScene().getWindow();
		final Stage popupStage = new Stage();
		popupStage.initStyle(StageStyle.UNDECORATED);
		popupStage.initModality(Modality.APPLICATION_MODAL);
		popupStage.initOwner(primaryStage);

		VBox box = new VBox(5);
		box.setPadding(new Insets(5));
		box.getChildren().addAll(new Label(""),
				new Label("Lat: " + String.format("%2.6fº", point.getLatitude())),
				new Label("Lon: " + String.format("%2.6fº", point.getLongitude())));
		Label close = new Label("X");
		
		close.setOnMouseClicked(a -> popupStage.close());

		StackPane.setAlignment(close, Pos.TOP_RIGHT);
		final StackPane stackPane = new StackPane(box, close);
		stackPane.setPadding(new Insets(5));
		stackPane.setStyle("-fx-background-color: lightgreen;");

		Scene scene = new Scene(stackPane, 150, 100);
		Bounds iconBounds = icon.localToScene(icon.getBoundsInLocal());
		popupStage.setX(primaryStage.getX() + primaryStage.getScene().getX() + iconBounds.getMaxX() );
		popupStage.setY(primaryStage.getY() + primaryStage.getScene().getY() + iconBounds.getMaxY());
		popupStage.setScene(scene);
		popupStage.show();
	}
	
	private void showPopover(HelpClass<MapPoint, Node, Recurso> hc, MouseEvent e) {
		Node icon = hc.getValue();
		Recurso r = hc.getConstant();		
		//
		PopOver po = new PopOver();
		//
		JFXButton btn = new JFXButton(" más información ");
		btn.setButtonType(ButtonType.RAISED);
		btn.maxWidthProperty();
		VBox vb = new VBox(2);
		vb.setPadding(new Insets(2));
		vb.getChildren().addAll(new Label(""), 
				new Label(r.getNombre()),
				new Label(r.getCoordenadas()),
				new Label(r.getDireccion()),
				btn);
		vb.setAlignment(Pos.TOP_CENTER);
		vb.setFillWidth(true);		
		po.setContentNode(vb);
		btn.setOnMouseClicked(a-> General.setContentParent("/viewRecurso/Recurso.fxml", Context.getInstance().getAnch_Contenido()));
		//		
		final Stage primaryStage = (Stage) icon.getScene().getWindow();
		Bounds iconBounds = icon.localToScene(icon.getBoundsInLocal());
		po.show(primaryStage, iconBounds.getMaxX(), iconBounds.getMaxY());
	}

}
