package ec.edu.upse.facsistel.gitwym.sai;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import ec.edu.upse.facsistel.gitwym.sai.utilities.Context;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

@SpringBootApplication
public class App extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		try {
			Context.getInstance().setStage(primaryStage);
			Parent root = FXMLLoader.load(getClass().getResource("/viewBase/Base.fxml"));
			Scene scene = new Scene(root);
//			root.se
			scene.setFill(Color.TRANSPARENT);
			primaryStage.initStyle(StageStyle.TRANSPARENT);
			scene.getStylesheets().add("/styles.css");
			primaryStage.setScene(scene);
			primaryStage.setMaximized(true);
			primaryStage.setMinWidth(500);
			primaryStage.setMinHeight(500);
			primaryStage.getIcons().add(new Image("route.png"));
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch();
	}

}