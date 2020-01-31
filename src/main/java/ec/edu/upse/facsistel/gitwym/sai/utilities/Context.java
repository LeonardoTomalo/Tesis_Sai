package ec.edu.upse.facsistel.gitwym.sai.utilities;

import com.gluonhq.maps.MapView;

import ec.edu.upse.facsistel.gitwym.sai.models.Contacto;
import ec.edu.upse.facsistel.gitwym.sai.models.Recurso;
import ec.edu.upse.facsistel.gitwym.sai.models.Usuario;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lombok.Data;

@Data
public class Context {
	
	private final static Context instance = new Context();	
	private AnchorPane anch_Contenido;	
	private Stage stage;
	private Contacto contactoP;
	private Usuario userLogged;
	private Recurso recursoContext;
	private MapView mapViewContext;
	
	public static Context getInstance() {
		return instance;
	}	
}
