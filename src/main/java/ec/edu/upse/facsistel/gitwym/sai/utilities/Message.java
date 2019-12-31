package ec.edu.upse.facsistel.gitwym.sai.utilities;

import java.util.Optional;

import org.controlsfx.control.Notifications;

import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

/**
 * @author Leonardo Tomalo.
 **/
public class Message {

	/**
	 * Muestra notificacion satisfactoria por 5 segundos
	 * @param texto
	 */
	public static void showSuccessNotification(String texto) {
		Image i = new Image("success.png",60,60,false,false);
		Notifications notificacion = Notifications.create()
				.title("Proceso Satisfactorio")
				.text(texto)
				.graphic(new ImageView(i))
				.hideAfter(Duration.seconds(5))
				.position(Pos.BOTTOM_RIGHT);
		notificacion.darkStyle();
		notificacion.show();
	}	
	
	/**
	 * Muestra notificacion que ha sufrido un error por 5 segundos
	 * @param texto
	 */
	public static void showErrorNotification(String texto) {
		Image i = new Image("error.png",60,60,false,false);
		Notifications notificacion = Notifications.create()
				.title("Proceso Desfavorable")
				.text(texto)
				.graphic(new ImageView(i))
				.hideAfter(Duration.seconds(5))
				.position(Pos.BOTTOM_RIGHT);
		notificacion.darkStyle();
		notificacion.show();
	}	
	/**
	 * Muestra notificacion de advertencia por 5 segundos
	 * @param texto
	 */
	public static void showWarningNotification(String texto) {
		Image i = new Image("warning.png",60,60,false,false);
		Notifications notificacion = Notifications.create()
				.title("Advertencia")
				.text(texto)
				.graphic(new ImageView(i))
				.hideAfter(Duration.seconds(5))
				.position(Pos.BOTTOM_RIGHT);
		notificacion.darkStyle();
		notificacion.show();
	}	
	
	/**
	 * Muestra cuadro de dialogo con pregunta enviada por parametros.
	 * @param mensaje
	 * @param stage
	 * @return
	 */
	public static Optional<ButtonType> showQuestion(String mensaje,Stage stage){
		Image i = new Image("info.png",60,60,false,false);
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Pregunta");
		alert.setHeaderText(null);
		alert.setGraphic(new ImageView(i));
		alert.initStyle(StageStyle.UTILITY);
		alert.setContentText(mensaje);
		alert.initOwner(stage);
		return alert.showAndWait();
	}
	
}
