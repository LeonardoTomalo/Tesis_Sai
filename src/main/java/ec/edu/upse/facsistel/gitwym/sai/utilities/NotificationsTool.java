package ec.edu.upse.facsistel.gitwym.sai.utilities;

import org.controlsfx.control.Notifications;

import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.util.Duration;

public class NotificationsTool {

	
	/**
	 * Configuracion estandar de las notificaciones, se envia por parametros el titulo y una descripcion de la notificacion.
	 * @param titulo
	 * @param texto
	 */
	public void showNotificationPush(String titulo, String texto) {
//		ImageView i = new ImageView("ruta de imagen");
		Notifications notificacion = Notifications.create()
				.title(titulo)
				.text(texto)
				.graphic(null)
//				.graphic(new ImageView(i))
				.hideAfter(Duration.seconds(4))
				.position(Pos.BOTTOM_RIGHT)
				.onAction(new javafx.event.EventHandler<ActionEvent>() {					
					@Override
					public void handle(ActionEvent event) {
						//Hacer algo cuando se accione la notificacion.
					}
				});
		notificacion.darkStyle();
		notificacion.showConfirm();
	}
	
}
