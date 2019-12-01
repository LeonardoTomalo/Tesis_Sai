package ec.edu.upse.facsistel.gitwym.sai;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

@SpringBootApplication
public class App extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		try {
			 Parent root = FXMLLoader.load(getClass().getResource("/viewBase/Base.fxml"));
			 Scene scene = new Scene(root);
			 primaryStage.setScene(scene);
			 primaryStage.setMaximized(true);
			 primaryStage.setMinWidth(500);
			 primaryStage.setMinHeight(500);
			 primaryStage.show();						
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

    public static void main(String[] args) {
    	SpringApplication.run(App.class, args);
        launch();
    }

}